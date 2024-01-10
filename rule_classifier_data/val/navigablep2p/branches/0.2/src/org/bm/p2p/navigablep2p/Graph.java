package org.bm.p2p.navigablep2p;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Graph {
	private ArrayList<Node> nodes ;
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	private static final int MAXMESSAGES = Node.BUCKETS * Node.BUCKETS ;

	//初始化网络，节点个数为0
	public Graph() {
		super();
		nodes = new ArrayList<Node> ();
	}
	
	//初始化网络，节点个数为size，节点之间没有边进行连接
	public Graph(int size) {
		super();
		nodes = new ArrayList<Node> ();
		for(int i = 0; i<size; i++) {
			System.out.print(i+": ");
			nodes.add(new Node());
		}
	}

	//模拟BootStrap机制，从网络中均匀随机选择一个节点返回
	public Node bootstrap() {
		Random random = new Random();
		if (nodes.size()>0) {
			int chosedNodeIdx = random.nextInt(nodes.size());
			return nodes.get(chosedNodeIdx);
		}
		return null;
	}
	
	//初始化网络的第一个节点
	public void initializeTheFirstNode(Node node) {
		nodes.add(node);
	}
	
	// 加入节点node，返回所需发送的消息个数
	public int addNode2(Node node) {
		int messageNum = 0; //增加一个节点所需发送的消息个数
		Node bootstrapNode2 = bootstrap(); 
		for (int i = 0; i < Node.BUCKETS; i++) {
			// 随机生成一个和节点node的距离为i的新节点randomDestNode
			// Node randomDestNode = new Node(node,i);
			// 将节点randomDestNode做为目标节点，从bootstrapNode开始路由到randomDestNode
			// 所经历的中间节点加入加入节点node的出邻居，将节点node加入这些中间节点的入邻居
			// 如果中间节点为空？如果中间节点的入邻居已经存在？
			// 如果已经知道某节点的入邻居和出邻居，就可以算出节点入邻居和出邻居的距离
			
			Node bootstrapNode = bootstrap(); // 获得bootstrap节点
			int maxDestNodeNum = 2; // 每次发现目标节点的最大个数
			//从联系节点bootstrapNode开始，发现所有与加入节点node的距离为i的节点
			//如果联系节点bootstrapNode和加入节点的距离大于i
			while ((bootstrapNode!=null) && (node.getDistanceFrom(bootstrapNode)> i)) {
				bootstrapNode = bootstrapNode.getOutNeighbor(node.getDistanceFrom(bootstrapNode));
				messageNum ++;
			}
			if ((bootstrapNode!=null)&& (node.getDistanceFrom(bootstrapNode)<i)) {
				bootstrapNode = bootstrapNode.getOutNeighbor(i);
				messageNum ++;
			}
			if ((bootstrapNode!=null)&&(node.getDistanceFrom(bootstrapNode) == i)) { //如果联系节点和加入节点的距离=i
				//查找所有和联系节点的距离小于i的节点，这些节点和加入节点的距离都等于i
				//这是一个广度优先的树遍历算法
				Queue<Node> visitedNodes = new LinkedList<Node> ();
				visitedNodes.add(bootstrapNode); // 把联系节点插入已访问节点队列
				int destNodeNum = 0; // 已访问的节点个数
				// 只要已访问节点个数还没有达到maxDestNodeNum，而且，已访问节点队列不为空
				while ((destNodeNum<=maxDestNodeNum)&&(visitedNodes.size()>0)) {
					//从已访问节点队列中取出一个节点
					Node hasVisitedNode = visitedNodes.remove();
					//如果该节点的距离为i的出邻居为空，就把加入节点node作为该节点距离为i的出邻居
					if (hasVisitedNode.getOutNeighbor(i) == null) {
						hasVisitedNode.setOutNeighbor(node, i);
						node.setInNeighbor(hasVisitedNode, i);
						//maxDestNodeNum = 10000;
					}
					// 该节点作为加入节点node的出邻居
					node.setOutNeighbor(hasVisitedNode, i);
					hasVisitedNode.setInNeighbor(node, i);
					
					// 执行stolen link，将hasVisitedNode的一个入邻居借给加入节点，解决“节点加入网络时间越长，入邻居越多”的问题
//					for (int k = i+1; k < Node.BUCKETS; k++) {
//						if (hasVisitedNode.getInNeighbor(k)!=null) {
//							hasVisitedNode.getInNeighbor(k).setOutNeighbor(node, k);
//							node.setInNeighbor(hasVisitedNode.getInNeighbor(k), k);
//						}
//					}
					
					destNodeNum ++;
					messageNum ++;
					// 访问和节点hasVisitedNode的距离小于i的所有节点
					for (int j = i - 1; j >= 0; j--) {
						if (hasVisitedNode.getOutNeighbor(j) != null) {
							visitedNodes.add(hasVisitedNode.getOutNeighbor(j));
						}
					}
					
				}
				
			}
		}
		nodes.add(node);
		return messageNum;
	}
	
	// 删除节点，返回所需发送的消息个数
	// 将被删除节点的入邻居节点的出邻居用被删除节点的出邻居替代
	public int delNode(Node node) {
		int messageNum = 0;
		// 对于所删除节点的入邻居节点列表中的所有入邻居
		int j = 0;
		for (int i = 0; i <= Node.BUCKETS - 1; i++) {
			if (node.getInNeighbor(i) != null) {
				for (int k = j; k <= i; k++) {
					if (node.getOutNeighbor(k) != null) {
						node.getInNeighbor(i).setOutNeighbor(node.getOutNeighbor(k),i);
						messageNum ++;
						break;
					}
				}
				j = i;
			}
		}
		return messageNum;
	}
	
	public void delNodes() {
		for (int i = 0; i < nodes.size(); i ++) {
			Random random = new Random();
			int chosenNodeIdx = random.nextInt(nodes.size());
			int messageNum = delNode(nodes.get(chosenNodeIdx));
			System.out.println("delNode messageNum: " + messageNum);
			nodes.remove(chosenNodeIdx);
			testRoute();
		}
	}
	/**
	 * 将节点node加入网络，使用Random Walk方法为节点node增加边
	 * 
	 * 如果节点a有一个邻居节点b，节点c是节点b的某个邻居节点，那么：
	 * (1) If D(b,c)=D(a,b), Then D(a,c)<D(a,b)
	 * (2) If D(b,c)>D(a,b), Then D(a,c)=D(b,c)>D(a,b)
	 * (3) If D(b,c)<D(a,b), Then D(a,c)=D(a,b)
	 * 
	 * 通过如下机制获得NextHops:
	 * (1): 以节点c为NextHop，
	 * (2): 均匀随机选择节点c作为NextHop
	 * (3): 将本节点加入，停止
	 */
	/*
	public void addNode(Node node) {
		Node bootstrapNode = bootstrap(); // 获得bootstrap节点
		//int maxSteps = Node.BUCKETS; // ?最大步骤
		int maxDistance = Node.BUCKETS-1, 
		minDistance = 0;
		int messageNumber = randomWalk(maxDistance, minDistance, node, bootstrapNode, 0);
		System.out.println("messages: " + messageNumber);
		nodes.add(node);
	}
	
	private int randomWalk(int maxDistance, int minDistance, Node joinNode, Node concactNode, int messageNumber) {
		if (messageNumber++ > MAXMESSAGES) return messageNumber;
		
		// 获得加入节点和联系节点的距离
		int curDistance = joinNode.getDistanceFrom(concactNode);
		// 存储 联系节点邻居中 与加入节点的距离 和联系节点与加入节点的距离 相等的节点
		ArrayList<Node> distanceEqualsNodes = new ArrayList<Node> ();
		// 把联系节点存入
		distanceEqualsNodes.add(concactNode);
		// 把联系节点邻居中的同距离节点加入
		for (int i = curDistance - 1; i>=0;i--) {
			if (concactNode.getOutNeighbor(i) != null) 
				distanceEqualsNodes.add(concactNode.getOutNeighbor(i));
		}
		// 从同距离节点列表中随机选择一个节点
		Random random = new Random();
		int chosedNodeIdx = random.nextInt(distanceEqualsNodes.size());
		//将该节点加入到加入节点的邻居节点
		joinNode.setOutNeighbor(distanceEqualsNodes.get(chosedNodeIdx), curDistance);
		//（要不要将加入节点加入该节点的邻居呢？）
		distanceEqualsNodes.get(chosedNodeIdx).setOutNeighbor(joinNode, curDistance);
		
		//选择下一跳节点，与加入节点距离小于curDistance的节点（最多只有一个）
		Node distanceLessNode = concactNode.getOutNeighbor(curDistance);
		if (distanceLessNode!=null && !distanceLessNode.equals(joinNode)) { // && joinNode.getDistanceFrom(distanceLessNode) >= minDistance
			messageNumber = randomWalk(maxDistance, minDistance, joinNode, distanceLessNode, messageNumber); //curDistance-1
		}
		
		//选择下一跳节点，与加入节点距离大于curDistance的节点
		ArrayList<Node> distanceLargerNodes = new ArrayList<Node> ();
		for (int i = curDistance + 1; i <= maxDistance;i++) {
			if (concactNode.getOutNeighbor(i) != null) 
				distanceLargerNodes.add(concactNode.getOutNeighbor(i));
		}
		if (distanceLargerNodes.size()>0) {
			random = new Random();
			chosedNodeIdx = random.nextInt(distanceLargerNodes.size());
			messageNumber = randomWalk(maxDistance,minDistance , joinNode, distanceLargerNodes.get(chosedNodeIdx), messageNumber); //curDistance+1
		}
		return messageNumber;
	}
	
	private int randomWalk2(int maxDistance, int minDistance, Node joinNode, Node concactNode, int messageNumber) {
		if (messageNumber++ > MAXMESSAGES) return messageNumber;

		// 获得加入节点和联系节点的距离
		int curDistance = joinNode.getDistanceFrom(concactNode);
		// 存储 联系节点邻居中 与加入节点的距离 和联系节点与加入节点的距离 相等的节点
		ArrayList<Node> distanceEqualsNodes = new ArrayList<Node> ();
		// 把联系节点存入
		distanceEqualsNodes.add(concactNode);
		// 把联系节点邻居中的同距离节点加入
		for (int i = curDistance - 1; i>=0;i--) {
			for (int j = 0; j < Node.K; j++) {
				if (concactNode.getOutNeighborByIdx(i,j) != null) 
					distanceEqualsNodes.add(concactNode.getOutNeighborByIdx(i,j));
			}
		}
		// 从同距离节点列表中随机选择K个节点
		if (distanceEqualsNodes.size()>Node.K) {
			for (int i = 0; i < Node.K; i++) {
				Random random = new Random();
				int chosedNodeIdx = random.nextInt(distanceEqualsNodes.size());
				//将该节点加入到加入节点的邻居节点(可能出现相同的节点!)
				joinNode.setOutNeighbor(distanceEqualsNodes.get(chosedNodeIdx), curDistance);
				//（要不要将加入节点加入该节点的邻居呢？）
				distanceEqualsNodes.get(chosedNodeIdx).setOutNeighbor(joinNode, curDistance);
			}
		}
		else {
			for (int i = 0; i < distanceEqualsNodes.size(); i++) {
				//将该节点加入到加入节点的邻居节点(可能出现相同的节点!)
				if (!joinNode.equals(distanceEqualsNodes.get(i))) {
					joinNode.setOutNeighbor(distanceEqualsNodes.get(i), curDistance);
					//（要不要将加入节点加入该节点的邻居呢？）
					distanceEqualsNodes.get(i).setOutNeighbor(joinNode, curDistance);
					
				}
			}
		}
		
		//选择下一跳节点，与加入节点距离小于curDistance的节点（最多只有一个）
		for (int i = 0; i < Node.K; i++) {
			Node distanceLessNode = concactNode.getOutNeighborByIdx(curDistance,i);
			if (distanceLessNode!=null && !distanceLessNode.equals(joinNode)) { // && joinNode.getDistanceFrom(distanceLessNode) >= minDistance
				messageNumber = randomWalk2(maxDistance, minDistance, joinNode, distanceLessNode, messageNumber); //curDistance-1
			}
		}
		
		//选择下一跳节点，与加入节点距离大于curDistance的节点
			
		ArrayList<Node> distanceLargerNodes = new ArrayList<Node> ();
		for (int i = curDistance + 1; i <= maxDistance;i++) {
			for (int j = 0; j < Node.K; j++) {
				if (concactNode.getOutNeighborByIdx(i,j) != null) 
					distanceLargerNodes.add(concactNode.getOutNeighborByIdx(i,j));
				}
		}
		if (distanceLargerNodes.size()>0) {
			//Random random = new Random();
			//chosedNodeIdx = random.nextInt(distanceLargerNodes.size());
			for (int i = 0; i < distanceLargerNodes.size() ;i++) {
				messageNumber = randomWalk2(maxDistance,minDistance , joinNode, distanceLargerNodes.get(i), messageNumber); //curDistance+1
			}
		}
		return messageNumber;
	}
*/
	
	//为网络增加边，根据Klernberg的Navigable Network模型(集中式算法)
	public void addEdgesAccordingToDistance() {
		ArrayList<Node> someNodes = new ArrayList<Node> ();
		for(Node node : nodes) { //对于每个节点
			for(int i = 0; i<Node.BUCKETS;i++) { // 为该节点的每个桶加入一个邻居节点
				someNodes.clear();
				for(Node node2:nodes) { // 从所有其它节点中选取跟该节点距离为i的节点集合
					//如果两个节点不相等，而且节点间距离为i
					if (!node.equals(node2)&&(node.getDistanceFrom(node2)==i)) {
						someNodes.add(node2);
					}
				}
				if (someNodes.size() > 0)	{ //如果节点集合不为空
					System.out.print(i + " ");
					// 从距离为i的节点集合中随机选取一个节点(?按照模型概率应该为1/2^i)
					Random random = new Random();
					int chosedNodeIdx = random.nextInt(someNodes.size());
					// 把随机选取的节点加入本节点的邻居节点列表
					node.setOutNeighbor(someNodes.get(chosedNodeIdx), i);// ？ (此处也可以把someNodes中某个确定节点加入邻居节点，但可能会造成路径长度的方差增大)
				}
				System.out.println();
								
			}
		}
	}
	
	// 多径路由,如何实现?	
	public int multiPathRoute(Node srcNode, Node destNode, int maxSteps) {
		int steps = 0;
		
		return  steps;
	}
	
	// 从网络的节点srcNode路由到节点destNode，路由方法是所谓贪婪路由(Greed Routing)
	// steps>0表示路由成功，steps的大小就是路径长度,steps=-1表示中断,steps=0表示超过maxSteps
	// 路由类型：找不到最近下一跳节点时，1表示失败，2表示选择第二近的下一跳节点
	public int route(Node srcNode, Node destNode, int maxSteps, int routeType) {
		int steps = 0;
		// 如果跳数还小于maxSteps，而且源节点还没有到达目标节点，则：
		for(steps=0; steps <= maxSteps && ! srcNode.equals(destNode); steps++) {
			//获取源节点srcNode到目标节点的距离
			int distance = srcNode.getDistanceFrom(destNode);
			//如果srcNode的邻居节点中有该距离的邻居，则：
			if (srcNode.getOutNeighbor(distance) != null) {
				//前进到该邻居节点
				srcNode = srcNode.getOutNeighbor(distance);
			}
			else { //如果srcNode的邻居节点中没有该距离的邻居，则：
				if (routeType==1) // 如果路由类型为1
					return -1; // 返回-1，表示路由失败
				else if (routeType==2) { // 如果路由类型为2
					// 从与srcNode的距离小于distance的节点中随机选取一个，做为下一跳节点
					ArrayList<Node> distanceLessNodes = new ArrayList<Node> ();
					for (int i = distance-1; i>=0; i--) {
						if (srcNode.getOutNeighbor(i) != null)
							distanceLessNodes.add(srcNode.getOutNeighbor(i));
					}
					if (distanceLessNodes.size() > 0) { // 如果找到
						Random random = new Random ();
						int nextNodeIdx = random.nextInt(distanceLessNodes.size());
						srcNode = distanceLessNodes.get(nextNodeIdx);
					}
					else { // 如果没有找到，从与srcNode的距离大于distance的节点中选择距离最小的节点，作为下一跳节点
						int i;
						for (i = distance+1; i < Node.BUCKETS; i++) {
							if (srcNode.getOutNeighbor(i) != null)
								srcNode = srcNode.getOutNeighbor(i);
							break;
						}
						if (i == Node.BUCKETS) // 如果还是没有找到，则路由失败 
							return -1;
					}
				}
			}
		}
		if (steps > maxSteps) return 0; // 如果超过最大路径长度，返回0，表示路由失败
		return steps; // 否则，返回路径长度
	}
	
	// 测试网络的路由性能，两个指标
	// 1。路由路径长度 
	// 2。路由失败比率
	public void testRoute() {
		int steps = 0;
		double avgSteps = 0.0; // 平均路径长度
		double successPercent = 0.0; // 路由成功率
		
		int totalSteps = 0, //总路径长度 
			successNumber = 0, // 路由成功次数
			totalNumber = 0; // 总路由次数
		for (Node srcNode : nodes) { // 对于网络中的每个节点
			for (Node destNode : nodes) { // 计算到其它所有节点的路径长度
				if (srcNode.equals(destNode)) continue; //如果源节点和目标节点是同一个节点，就进行下轮循环
				steps = route(srcNode,destNode,Node.BUCKETS*Node.BUCKETS,1);
				System.out.print(steps+" ");
				
				totalNumber ++; // 总路由次数加1
				if (steps==0 || steps==-1) { // 如果路由失败
				}
				else { // 如果路由成功
					totalSteps += steps; // 增加总路径长度
					successNumber ++; // 路由成功次数加1
				}
			}
			System.out.println();
		}
		avgSteps = (double)totalSteps / successNumber; // 平均路径长度 = 总路径长度 / 路由成功次数
		successPercent = (double)successNumber / totalNumber * 100; // 路由成功率 = 路由成功次数 / 总路由次数
		System.out.println("平均路径长度 = " + avgSteps);
		System.out.println("路由成功率 = " + successPercent + "%");

	}
	
	// 输出网络结构到文件中
	public void output(String fileName) {
		PrintWriter fileOutput;
		try {
			fileOutput = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			// 对于网络中所有节点
			for (int i = 0; i < nodes.size(); i++) {
				Node tmpNode = nodes.get(i);
				fileOutput.print(tmpNode.hashCode() + ", "+ tmpNode.getNodePosition().toString() + ": ");
				for (int j = 0; j < Node.BUCKETS; j++) {
					if (tmpNode.getOutNeighbor(j) != null) {
						//fileOutput.print(tmpNode.getOutNeighbor(j).hashCode()+", ");
						fileOutput.print(tmpNode.getOutNeighbor(j).getDistanceFrom(tmpNode)+", ");
						
					}
				}
				fileOutput.println();
			}
			fileOutput.close(); 
		} catch (IOException e) {
			System.err.println("Open graph.out ERROR!");
			e.printStackTrace();
		}

	}
	
	// 判断网络结点的入邻居和出邻居的个数是否相等
	public boolean isOutNeighborNumEqualsInNeighborNum() {
		boolean isEquals = true;
		
		for (int i = 0; i < nodes.size(); i++) {
			int inNeighborNum = 0, outNeighborNum = 0;
			Node itNode = nodes.get(i);
			for (int j = 0; j < Node.BUCKETS; j++) {
				if (itNode.getInNeighbor(j) != null) inNeighborNum ++;
				if (itNode.getOutNeighbor(j) != null) outNeighborNum ++;
			}
			System.out.println("inNeighborNum: " + inNeighborNum + " outNeighborNum: " + outNeighborNum);
			if (inNeighborNum > outNeighborNum) return false;
		}
		
		return isEquals;
	}
	
	public static void main(String[] argv) {
//		Graph graph = new Graph(1024);
//		graph.addEdgesAccordingToDistance();

		Graph graph = new Graph();
		graph.initializeTheFirstNode(new Node());
		for (int i =0;i<1024;i++) {
			int messageNum = graph.addNode2(new Node());
			System.out.println("messages: " + messageNum);
		}
		graph.testRoute();
		//graph.delNodes();
		graph.output("graph.out2");
		//System.out.println(graph.isOutNeighborNumEqualsInNeighborNum());
		
	}
}
