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
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class Graph {
	static Logger logger = Logger.getLogger(Graph.class.getName());
	private ArrayList<Node> nodes;

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	// 初始化网络，节点个数为0
	public Graph() {
		super();
		nodes = new ArrayList<Node>();
	}

	// 初始化网络，节点个数为size，节点之间没有边进行连接
	public Graph(int size) {
		super();
		nodes = new ArrayList<Node>();
		int k = 9; //(int)Math.ceil( Math.log(size)/Math.log(2)/1) ; // k = log2(n)/1
		for (int i = 0; i < size; i++) {
			Node newNode = new Node(k);
			boolean isDuplicate = false;
			for (int j = 0; j < nodes.size(); j++) {
				if (newNode.equals(nodes.get(j))) { 
					logger.info("duplicate node");
					isDuplicate = true;
					break;
				}
			}
			if (isDuplicate) {
				i--;
				continue;
			}
			else nodes.add(newNode);
			
		}
	}

	// 模拟BootStrap机制，从网络中均匀随机选择一个节点返回
	public Node bootstrap() {
		Random random = new Random();
		if (nodes.size() > 0) {
			int chosedNodeIdx = random.nextInt(nodes.size());
			return nodes.get(chosedNodeIdx);
		}
		return null;
	}

	// 初始化网络的第一个节点
	public void initializeTheFirstNode(Node node) {
		nodes.add(node);
	}


	// 从node开始查找和节点node的距离小于等于distance的所有节点(广度优先)
	public int routingToAllNodesLessthanDistance(Node srcNode, Node node,
			int distance) {
		class NodeAndDistance {
			Node node;
			int distance;

			NodeAndDistance(Node node, int distance) {
				this.node = node;
				this.distance = distance;
			}
		} ;
		int numth = 0;
		Queue<NodeAndDistance> visitedNodes = new LinkedList<NodeAndDistance>();
		NodeAndDistance visitedNode = new NodeAndDistance(node, distance);
		visitedNodes.add(visitedNode);
		while (visitedNodes.size() > 0) {
			visitedNode = visitedNodes.remove();
			numth++;
			// visit node
			//addNeighbors(srcNode, visitedNode.node);
			visitedNode.node.setNeighbor(srcNode, distance + 1);
			//logger.info(numth + "th node, position = "
			//		+ visitedNode.node.getNodePosition());
			for (int i = 0; i <= visitedNode.distance; i++) {
				if (visitedNode.node.getNeighbor(i) != null) {
					NodeAndDistance nextNode = new NodeAndDistance(
							visitedNode.node.getNeighbor(i), i - 1);

					visitedNodes.add(nextNode);
				}
			}
		}
		return numth;
	}

	// 把当前节点nowNode及其所有邻居节点加入加入节点joiningNode的邻居
	public void addNeighbors(Node joiningNode, Node nowNode) {
		
		// 把nowNode节点加入到加入节点joiningNode的邻居中
		int distance = joiningNode.getDistanceFrom(nowNode);
		joiningNode.setNeighbor(nowNode, distance);
		//bootstrapNode.setNeighbor(node, distance);

		// 把nowNode节点的所有邻居加入到加入节点joiningNode的邻居中
		for (int j = 0; j < Node.BUCKETS; j++) {
			if (nowNode.getNeighbors(j).size() > 0) {
				for (int k = 0; k < nowNode.getNeighbors(j).size(); k++) {
					Node neighborNode = nowNode.getNeighbors(j).get(k);
					distance = joiningNode.getDistanceFrom(neighborNode);
					joiningNode.setNeighbor(neighborNode, distance);
				}
			}
		}
	}
	
	// 将节点joiningNode加入网络中
	// type: 0-BUCKETS-1, 均匀随机选择下一跳，每次选择距离最远的，最近的，中间的，按照比率非均匀
	// 
	public int addNode2(Node joiningNode, int type) {
		int messageNum = 0;

		Node bootstrapNode = bootstrap(); // 获得bootstrap节点
		int distance = joiningNode.getDistanceFrom(bootstrapNode);
		int i = type;
		Node srcNode = null;
		if (i>=-1&&i<=Node.BUCKETS-1) {
			srcNode = bootstrapNode;
			// 随机生成一个和节点node的距离为i的新节点randomDestNode
			Node randomDestNode = new Node(joiningNode, i);
			// 向该节点路由，如果没有到达目的节点并且没有失败
			while (srcNode != randomDestNode && srcNode != null) {
				// 查找下一跳节点
				Node nextNode = srcNode.getNeighbor(srcNode.getDistanceFrom(randomDestNode));
				//Node nextNode = srcNode.getNeighbor(randomDestNode);
				messageNum++;
				
				distance = joiningNode.getDistanceFrom(srcNode);
				if (distance >= 0) {
					addNeighbors(joiningNode, srcNode);
					if (srcNode.getNeighbor(distance)==null) {
						messageNum += routingToAllNodesLessthanDistance(joiningNode,srcNode,distance-1);
					}
					srcNode.setNeighbor(joiningNode, distance);
				}
				srcNode = nextNode;
			}
//
//			srcNode = bootstrapNode;
//			// 随机生成一个和节点node的距离为i的新节点randomDestNode
//			randomDestNode = new Node(joiningNode, Node.BUCKETS-i-1-1);
//			// 向该节点路由，如果没有到达目的节点并且没有失败
//			while (srcNode != randomDestNode && srcNode != null) {
//				// 查找下一跳节点
//				Node nextNode = srcNode.getNeighbor(srcNode.getDistanceFrom(randomDestNode));
//				messageNum++;
//				
//				distance = joiningNode.getDistanceFrom(srcNode);
//				if (distance >= 0) {
//					addNeighbors(joiningNode, srcNode);
//					srcNode.setNeighbor(joiningNode, distance);
//				}
//				srcNode = nextNode;
//			}
		}
		else if (i==-2) { // 均匀随机选择
			int MaxSteps = Node.BUCKETS;
			int steps = 0;
			srcNode = bootstrapNode;
			while (steps<=MaxSteps&&srcNode!=null) {
				ArrayList<Node> allNeighbors = srcNode.getNeighbors();
				Node nextNode=null;
				if (allNeighbors.size()>0) {
					Random random = new Random();
					int randomNum = random.nextInt(allNeighbors.size());
					nextNode = allNeighbors.get(randomNum);
					messageNum++;
				}
				distance = joiningNode.getDistanceFrom(srcNode);
				if (distance >= 0) {
					addNeighbors(joiningNode, srcNode);
					srcNode.setNeighbor(joiningNode, distance);
				}
				srcNode = nextNode;
				steps++;
			}
		}
		else if (i==-3) { // 选择距离最大的
			int MaxSteps = Node.BUCKETS;
			int steps = 0;
			srcNode = bootstrapNode;
			while (steps<=MaxSteps&&srcNode!=null) {
				Node nextNode = srcNode.getNeighbor(srcNode.getMaxDistance());
				messageNum++;
				distance = joiningNode.getDistanceFrom(srcNode);
				if (distance >= 0) {
					addNeighbors(joiningNode, srcNode);
					srcNode.setNeighbor(joiningNode, distance);
				}
				srcNode = nextNode;
					
				steps++;
			}
			
		}
		else if (i==-4) {
			int MaxSteps = Node.BUCKETS;
			int steps = 0;
			srcNode = bootstrapNode;
			while (steps<=MaxSteps&&srcNode!=null) {
				Node nextNode=null;
				Random random = new Random();
				int randomNum = random.nextInt((int) Math.pow(2, (Node.BUCKETS
						- distance + 1)) - 3);
				if (randomNum < 1) { // 大于
					ArrayList<Node> nextNodes = new ArrayList<Node>();
					for ( i = distance + 1; i < Node.BUCKETS; i++) {
						if (srcNode.getNeighbor(i) != null
								&& srcNode.getNeighbor(i) != joiningNode)
							nextNodes.add(srcNode.getNeighbor(i));
					}
					if (nextNodes.size() > 0) {
						Random random2 = new Random();
						int chosedIdx = random2.nextInt(nextNodes.size());
						nextNode = nextNodes.get(chosedIdx);
					}

				} else if (randomNum < (int) Math.pow(2,
						(Node.BUCKETS - distance) - 1)) { // 等于
					if (srcNode.getNeighbor(distance) != null
							&& srcNode.getNeighbor(distance) != joiningNode) {
						nextNode = srcNode.getNeighbor(distance);
					}

				} else { // 小于
					ArrayList<Node> nextNodes = new ArrayList<Node>();
					for ( i = distance - 1; i >= 0; i--) {
						if (srcNode.getNeighbor(i) != null
								&& srcNode.getNeighbor(i) != joiningNode)
							nextNodes.add(srcNode.getNeighbor(i));
					}
					if (nextNodes.size() > 0) {
						Random random2 = new Random();
						int chosedIdx = random2.nextInt(nextNodes.size());
						nextNode = nextNodes.get(chosedIdx);
					}
				}
				messageNum++;
				distance = joiningNode.getDistanceFrom(srcNode);
				if (distance >= 0) {
					addNeighbors(joiningNode, srcNode);
					srcNode.setNeighbor(joiningNode, distance);
				}
				srcNode = nextNode;
					
				steps++;

			}
		}
			
		distance = joiningNode.getDistanceFrom(bootstrapNode);
		if (distance >= 0) {
			// 将bootstrap节点及其所有邻居加入加入节点joiningNode的邻居
			addNeighbors(joiningNode, bootstrapNode);
			// 将joiningNode加入bootstrapNode节点的邻居
			bootstrapNode.setNeighbor(joiningNode, distance);
		}
		messageNum++;
		
		nodes.add(joiningNode);
		return messageNum;
	}

	// 将节点joiningNode加入网络中
	public int addNode(Node joiningNode, int type) {
		int messageNum = 0;

		Node bootstrapNode = bootstrap(); // 获得bootstrap节点
		int distance = joiningNode.getDistanceFrom(bootstrapNode);
		int numbers = type;
		if (type == -1)
			numbers = bootstrapNode.getMinDistance()+1;
		else if (type == -2)
			numbers = distance;
		// 从bootstrapNode开始，分别查找和加入节点的距离为[-1，Node.BUCKETS-1]的随机节点
		for (int i = -1; i < numbers; i++) { // bootstrapNode.getMinDistance(), Node.BUCKETS, distance 
			Node srcNode = bootstrapNode;
			// 随机生成一个和节点node的距离为i的新节点randomDestNode
			Node randomDestNode = new Node(joiningNode, i);
			// 向该节点路由，如果没有到达目的节点并且没有失败
			while (srcNode != randomDestNode && srcNode != null) {
				// 查找下一跳节点
				Node nextNode = srcNode.getNeighbor(srcNode.getDistanceFrom(randomDestNode));
				messageNum++;
				
				distance = joiningNode.getDistanceFrom(srcNode);
				if (distance >= 0) {
					addNeighbors(joiningNode, srcNode);
					srcNode.setNeighbor(joiningNode, distance);
				}
				srcNode = nextNode;
			}
		}

		distance = joiningNode.getDistanceFrom(bootstrapNode);
		if (distance >= 0) {
			// 将bootstrap节点及其所有邻居加入加入节点joiningNode的邻居
			addNeighbors(joiningNode, bootstrapNode);
			// 将joiningNode加入bootstrapNode节点的邻居
			bootstrapNode.setNeighbor(joiningNode, distance);
		}
		messageNum++;
		
		nodes.add(joiningNode);
		return messageNum;
	}

	// 删除节点，返回所需发送的消息个数
	// 将被删除节点的入邻居节点的出邻居用被删除节点的出邻居替代
	public int delNode(Node node) {
		int messageNum = 0;
		// 对于所删除节点的入邻居节点列表中的所有入邻居
		int j = 0;
		for (int i = 0; i <= Node.BUCKETS - 1; i++) {
			if (node.getNeighbor(i) != null) {
				for (int k = j; k <= i; k++) {
					if (node.getNeighbor(k) != null) {
						node.getNeighbor(i).setNeighbor(node.getNeighbor(k), i);
						messageNum++;
						break;
					}
				}
				j = i;
			}
		}
		return messageNum;
	}

	public void delNodes() {
		for (int i = 0; i < nodes.size(); i++) {
			Random random = new Random();
			int chosenNodeIdx = random.nextInt(nodes.size());
			int messageNum = delNode(nodes.get(chosenNodeIdx));
			//System.out.println("delNode messageNum: " + messageNum);
			Node deletedNode = nodes.remove(chosenNodeIdx);
			if (i%16==0)
				testRoute();
		}
	}


	// 为网络增加边，根据Klernberg的Navigable Network模型(集中式算法)
	public void addEdgesAccordingToDistance() {
		ArrayList<Node> someNodes = new ArrayList<Node>();
		int k = 9;//(int)Math.ceil( Math.log(nodes.size())/Math.log(2)/1 ) ; // k = log2(n)/1
		for (Node node : nodes) { // 对于每个节点
			for (int i = 0; i < Node.BUCKETS; i++) { // 为该节点的每个桶加入一个邻居节点
				someNodes.clear();
				for (Node node2 : nodes) { // 从所有其它节点中选取跟该节点距离为i的节点集合
					// 如果两个节点不相等，而且节点间距离为i
					if (!node.equals(node2)
							&& (node.getDistanceFrom(node2) == i)) {
						someNodes.add(node2);
					}
				}
				int KK = (int) Math.max(1,Math.pow(2, k-(Node.BUCKETS-1-i)));
				if (someNodes.size() > 0) { // 如果节点集合不为空
					// 从距离为i的节点集合中随机选取一个节点(?按照模型概率应该为1/2^i)
					int l = 0;
					while (l<someNodes.size()&&l<KK) {
						Random random = new Random();
						int chosedNodeIdx = random.nextInt(someNodes.size());
						// 把随机选取的节点加入本节点的邻居节点列表
						node.setNeighbor(someNodes.get(l), i);// ？
						l++;													// (此处也可以把someNodes中某个确定节点加入邻居节点，但可能会造成路径长度的方差增大)
					}
				}
				// System.out.println();

			}
		}
	}

	// 多径路由,如何实现?
	public int multiPathRoute(Node srcNode, Node destNode, int maxSteps) {
		int steps = 0;

		return steps;
	}

	// 从网络的节点srcNode路由到节点destNode，路由方法是所谓贪婪路由(Greed Routing)
	// steps>0表示路由成功，steps的大小就是路径长度,steps=-1表示中断,steps=0表示超过maxSteps
	// 路由类型：找不到最近下一跳节点时，1表示失败，2表示选择第二近的下一跳节点
	public int route(Node srcNode, Node destNode, int maxSteps, int routeType) {
		int steps = 0;
		// 如果跳数还小于maxSteps，而且源节点还没有到达目标节点，则：
		for (steps = 0; steps <= maxSteps && !srcNode.equals(destNode); steps++) {
			// 获取源节点srcNode到目标节点的距离
			int distance = srcNode.getDistanceFrom(destNode);
			// 如果srcNode的邻居节点中有该距离的邻居，则：
			if (srcNode.getNeighbor(destNode) != null) {
				// 前进到该邻居节点
				srcNode = srcNode.getNeighbor(destNode);
			} else { // 如果srcNode的邻居节点中没有该距离的邻居，则：
				if (routeType == 1) { // 如果路由类型为1
					// logger.info("route fail, from "+srcNode.getNodePosition()+" to "+
					// destNode.getNodePosition());
					// srcNode.outputNeighbors();
					return -1; // 返回-1，表示路由失败
				} else if (routeType == 2) { // 如果路由类型为2
					// 从与srcNode的距离小于distance的节点中随机选取一个，做为下一跳节点
					ArrayList<Node> distanceLessNodes = new ArrayList<Node>();
					for (int i = distance - 1; i >= 0; i--) {
						if (srcNode.getNeighbors(i).size() > 0)
							for (int j =0;j<srcNode.getNeighbors(i).size();j++)
								distanceLessNodes.add(srcNode.getNeighbors(i).get(j));
					}
					if (distanceLessNodes.size() > 0) { // 如果找到
						Random random = new Random();
						int nextNodeIdx = random.nextInt(distanceLessNodes
								.size());
						srcNode = distanceLessNodes.get(nextNodeIdx);
					} else { // 如果没有找到，从与srcNode的距离大于distance的节点中选择距离最小的节点，作为下一跳节点
						int i;
						for (i = distance + 1; i < Node.BUCKETS; i++) {
							if (srcNode.getNeighbor(i) != null)
								srcNode = srcNode.getNeighbor(i);
							break;
						}
						if (i == Node.BUCKETS) // 如果还是没有找到，则路由失败
							return -1;
					}
				}
			}
		}
		if (steps == -1 || steps > maxSteps) { // 如果路由失败打印log
			//logger.info("route fail, from " + srcNode + " to " + destNode);
			//srcNode.outputNeighbors();
		}
		if (steps > maxSteps)
			return 0; // 如果超过最大路径长度，返回0，表示路由失败
		return steps; // 否则，返回路径长度
	}

	// 测试网络的路由性能，两个指标
	// 1。路由路径长度
	// 2。路由失败比率
	public void testRoute() {
		int steps = 0;
		double avgSteps = 0.0; // 平均路径长度
		double successPercent = 0.0; // 路由成功率

		int totalSteps = 0, // 总路径长度
		successNumber = 0, // 路由成功次数
		totalNumber = 0; // 总路由次数
		int someNodeSteps = 0, // 某节点到其它所有节点的路径长度均值
		failNum = 0; // 某节点到其它所有节点路由时的失败次数
		for (int i = 0; i < nodes.size(); i++) { // 对于网络中的每个节点  另一种用法：Node srcNode : nodes
			Node srcNode = nodes.get(i);
			someNodeSteps = 0;
			failNum = 0;
			for (Node destNode : nodes) { // 计算到其它所有节点的路径长度
				if (srcNode.equals(destNode))
					continue; // 如果源节点和目标节点是同一个节点，就进行下轮循环
				steps = route(srcNode, destNode, Node.BUCKETS * Node.BUCKETS, 1);
				// logger.info(steps+" ");

				totalNumber++; // 总路由次数加1
				if (steps == 0 || steps == -1) { // 如果路由失败
					failNum ++;
				} else { // 如果路由成功
					totalSteps += steps; // 增加总路径长度
					successNumber++; // 路由成功次数加1
					someNodeSteps += steps;
				}
			}
			//logger.info(i+"th node,routing path length=\t"+someNodeSteps/((nodes.size()-1-failNum)));
		}
		avgSteps = (double) totalSteps / successNumber; // 平均路径长度 = 总路径长度 /
														// 路由成功次数
		successPercent = (double) successNumber / totalNumber * 100; // 路由成功率 =
																		// 路由成功次数
																		// /
																		// 总路由次数
		logger.info("平均路径长度 = \t" + avgSteps);
		logger.info("路由成功率 = \t" + successPercent + "%");
		logger.info("总路由次数 = \t" + totalNumber);

	}

	// 输出网络结构到文件中
	public void output(String fileName) {
		PrintWriter fileOutput;
		try {
			fileOutput = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName)));
			// 对于网络中所有节点
			for (int i = 0; i < nodes.size(); i++) {
				Node tmpNode = nodes.get(i);
				fileOutput.print(tmpNode.hashCode() + ", "
						+ tmpNode.getNodePosition().toString() + ": ");
				for (int j = 0; j < Node.BUCKETS; j++) {
					if (tmpNode.getNeighbor(j) != null) {
						// fileOutput.print(tmpNode.getOutNeighbor(j).hashCode()+", ");
						fileOutput.print(tmpNode.getNeighbor(j)
								.getDistanceFrom(tmpNode)
								+ ", ");

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

	// 网络的统计信息：
	//1。节点的邻居个数。度数
	//2。节点查找邻居时的有效度
	public void stat() {
		int avgNeighborNum = 0, sumNeighborNum = 0, sumFailPercent = 0, avgFailPercent = 0;
		// 对于网络中每个节点
		for (int i = 0; i < nodes.size(); i++) {
			//获得节点邻居个数
			sumNeighborNum += nodes.get(i).getNeighborNum();
			logger.info(i + "th node: "+nodes.get(i).getNeighborNumByDistance()[15]+", "+nodes.get(i).getNeighborNumByDistance()[14]+", "+nodes.get(i).getNeighborNumByDistance()[13]+", "+nodes.get(i).getNeighborNumByDistance()[12]+", "+nodes.get(i).getNeighborNumByDistance()[11]+", "+nodes.get(i).getNeighborNumByDistance()[10]+", "+nodes.get(i).getNeighborNumByDistance()[9]+", "+nodes.get(i).getNeighborNumByDistance()[8]+", "+nodes.get(i).getNeighborNumByDistance()[7]+", "+nodes.get(i).getNeighborNumByDistance()[6]+", "+nodes.get(i).getNeighborNumByDistance()[5]+", "+nodes.get(i).getNeighborNumByDistance()[4]+", "+nodes.get(i).getNeighborNumByDistance()[3]+", "+nodes.get(i).getNeighborNumByDistance()[2]+", "+nodes.get(i).getNeighborNumByDistance()[1]+", "+nodes.get(i).getNeighborNumByDistance()[0]+", ");
			//logger.info(i + "th node, neighbor number = \t"
			//		+ nodes.get(i).getNeighborNum() + "\t, durty degree = \t"
			//		+ nodes.get(i).getFailNum());
			//获得失效节点个数
			sumFailPercent += nodes.get(i).getFailNum();
			//输出所有节点
//			for (int j = 0; j < Node.BUCKETS; j++) {
//				if (nodes.get(i).getNeighbor(j) != null)
//					logger.info(j + "th neighbor, position = "
//							+ nodes.get(i).getNeighbor(j).getNodePosition());
//			}
		}
		logger.info("平均节点度数 = \t" + sumNeighborNum / nodes.size());
		logger.info("平均邻居失效率 = \t" + sumFailPercent / nodes.size());

	}

	public static void main(String[] argv) {
		PropertyConfigurator.configure("sort2.properties");
		
		Graph graph = new Graph(1024);
		graph.addEdgesAccordingToDistance();
		//graph.routingToAllNodesLessthanDistance(graph.nodes.get(32), graph.bootstrap(),Node.BUCKETS-1);
		graph.testRoute();
		graph.stat();

//		int times = 5;
//		int base = 8;
//		for (int i = 0; i < times; i++) { // 运行times次
//			logger.info("第"+i+"次运行, 节点个数=\t"+Math.pow(2, base+i));
//			for (int k = -1; k <= -1;k++ ) { // 每次加入节点方式不同
//				logger.info("节点加入方式, k=\t"+k);
//				Graph graph = new Graph();
//				graph.initializeTheFirstNode(new Node((base+i)/2));
//				int totalMessageNum = 0;
//				for (int j = 0;j < Math.pow(2, base+i)-1;j++) { // 每次节点个数
//					Node node = new Node((base+i)/2);
//					boolean isDuplicate = false;
//					for (int l = 0; l < graph.nodes.size(); l++) {
//						if (node.equals(graph.nodes.get(l))) { 
//							logger.info("duplicate node");
//							isDuplicate = true;
//							break;
//						}
//					}
//					if (isDuplicate) {
//						j--;
//						continue;
//					}
//					else {
//						int messageNum = graph.addNode2(node,k);
//						//logger.info(j + "th node," + "messages: \t" + messageNum);
//						totalMessageNum += messageNum;
//					}
//				}
//				logger.info("节点个数 =\t" + graph.nodes.size());
//				logger.info("平均加入消息个数 =\t" + totalMessageNum/(Math.pow(2, base+i)));
//				graph.testRoute();
//				graph.stat();
//			}
//		}
		
		
		//graph.delNodes();
		// graph.output("graph.out2");
		// System.out.println(graph.isOutNeighborNumEqualsInNeighborNum());

	}
}
