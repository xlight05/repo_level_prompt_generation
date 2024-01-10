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

	// ��ʼ�����磬�ڵ����Ϊ0
	public Graph() {
		super();
		nodes = new ArrayList<Node>();
	}

	// ��ʼ�����磬�ڵ����Ϊsize���ڵ�֮��û�б߽�������
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

	// ģ��BootStrap���ƣ��������о������ѡ��һ���ڵ㷵��
	public Node bootstrap() {
		Random random = new Random();
		if (nodes.size() > 0) {
			int chosedNodeIdx = random.nextInt(nodes.size());
			return nodes.get(chosedNodeIdx);
		}
		return null;
	}

	// ��ʼ������ĵ�һ���ڵ�
	public void initializeTheFirstNode(Node node) {
		nodes.add(node);
	}


	// ��node��ʼ���Һͽڵ�node�ľ���С�ڵ���distance�����нڵ�(�������)
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

	// �ѵ�ǰ�ڵ�nowNode���������ھӽڵ�������ڵ�joiningNode���ھ�
	public void addNeighbors(Node joiningNode, Node nowNode) {
		
		// ��nowNode�ڵ���뵽����ڵ�joiningNode���ھ���
		int distance = joiningNode.getDistanceFrom(nowNode);
		joiningNode.setNeighbor(nowNode, distance);
		//bootstrapNode.setNeighbor(node, distance);

		// ��nowNode�ڵ�������ھӼ��뵽����ڵ�joiningNode���ھ���
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
	
	// ���ڵ�joiningNode����������
	// type: 0-BUCKETS-1, �������ѡ����һ����ÿ��ѡ�������Զ�ģ�����ģ��м�ģ����ձ��ʷǾ���
	// 
	public int addNode2(Node joiningNode, int type) {
		int messageNum = 0;

		Node bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
		int distance = joiningNode.getDistanceFrom(bootstrapNode);
		int i = type;
		Node srcNode = null;
		if (i>=-1&&i<=Node.BUCKETS-1) {
			srcNode = bootstrapNode;
			// �������һ���ͽڵ�node�ľ���Ϊi���½ڵ�randomDestNode
			Node randomDestNode = new Node(joiningNode, i);
			// ��ýڵ�·�ɣ����û�е���Ŀ�Ľڵ㲢��û��ʧ��
			while (srcNode != randomDestNode && srcNode != null) {
				// ������һ���ڵ�
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
//			// �������һ���ͽڵ�node�ľ���Ϊi���½ڵ�randomDestNode
//			randomDestNode = new Node(joiningNode, Node.BUCKETS-i-1-1);
//			// ��ýڵ�·�ɣ����û�е���Ŀ�Ľڵ㲢��û��ʧ��
//			while (srcNode != randomDestNode && srcNode != null) {
//				// ������һ���ڵ�
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
		else if (i==-2) { // �������ѡ��
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
		else if (i==-3) { // ѡ���������
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
				if (randomNum < 1) { // ����
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
						(Node.BUCKETS - distance) - 1)) { // ����
					if (srcNode.getNeighbor(distance) != null
							&& srcNode.getNeighbor(distance) != joiningNode) {
						nextNode = srcNode.getNeighbor(distance);
					}

				} else { // С��
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
			// ��bootstrap�ڵ㼰�������ھӼ������ڵ�joiningNode���ھ�
			addNeighbors(joiningNode, bootstrapNode);
			// ��joiningNode����bootstrapNode�ڵ���ھ�
			bootstrapNode.setNeighbor(joiningNode, distance);
		}
		messageNum++;
		
		nodes.add(joiningNode);
		return messageNum;
	}

	// ���ڵ�joiningNode����������
	public int addNode(Node joiningNode, int type) {
		int messageNum = 0;

		Node bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
		int distance = joiningNode.getDistanceFrom(bootstrapNode);
		int numbers = type;
		if (type == -1)
			numbers = bootstrapNode.getMinDistance()+1;
		else if (type == -2)
			numbers = distance;
		// ��bootstrapNode��ʼ���ֱ���Һͼ���ڵ�ľ���Ϊ[-1��Node.BUCKETS-1]������ڵ�
		for (int i = -1; i < numbers; i++) { // bootstrapNode.getMinDistance(), Node.BUCKETS, distance 
			Node srcNode = bootstrapNode;
			// �������һ���ͽڵ�node�ľ���Ϊi���½ڵ�randomDestNode
			Node randomDestNode = new Node(joiningNode, i);
			// ��ýڵ�·�ɣ����û�е���Ŀ�Ľڵ㲢��û��ʧ��
			while (srcNode != randomDestNode && srcNode != null) {
				// ������һ���ڵ�
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
			// ��bootstrap�ڵ㼰�������ھӼ������ڵ�joiningNode���ھ�
			addNeighbors(joiningNode, bootstrapNode);
			// ��joiningNode����bootstrapNode�ڵ���ھ�
			bootstrapNode.setNeighbor(joiningNode, distance);
		}
		messageNum++;
		
		nodes.add(joiningNode);
		return messageNum;
	}

	// ɾ���ڵ㣬�������跢�͵���Ϣ����
	// ����ɾ���ڵ�����ھӽڵ�ĳ��ھ��ñ�ɾ���ڵ�ĳ��ھ����
	public int delNode(Node node) {
		int messageNum = 0;
		// ������ɾ���ڵ�����ھӽڵ��б��е��������ھ�
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


	// Ϊ�������ӱߣ�����Klernberg��Navigable Networkģ��(����ʽ�㷨)
	public void addEdgesAccordingToDistance() {
		ArrayList<Node> someNodes = new ArrayList<Node>();
		int k = 9;//(int)Math.ceil( Math.log(nodes.size())/Math.log(2)/1 ) ; // k = log2(n)/1
		for (Node node : nodes) { // ����ÿ���ڵ�
			for (int i = 0; i < Node.BUCKETS; i++) { // Ϊ�ýڵ��ÿ��Ͱ����һ���ھӽڵ�
				someNodes.clear();
				for (Node node2 : nodes) { // �����������ڵ���ѡȡ���ýڵ����Ϊi�Ľڵ㼯��
					// ��������ڵ㲻��ȣ����ҽڵ�����Ϊi
					if (!node.equals(node2)
							&& (node.getDistanceFrom(node2) == i)) {
						someNodes.add(node2);
					}
				}
				int KK = (int) Math.max(1,Math.pow(2, k-(Node.BUCKETS-1-i)));
				if (someNodes.size() > 0) { // ����ڵ㼯�ϲ�Ϊ��
					// �Ӿ���Ϊi�Ľڵ㼯�������ѡȡһ���ڵ�(?����ģ�͸���Ӧ��Ϊ1/2^i)
					int l = 0;
					while (l<someNodes.size()&&l<KK) {
						Random random = new Random();
						int chosedNodeIdx = random.nextInt(someNodes.size());
						// �����ѡȡ�Ľڵ���뱾�ڵ���ھӽڵ��б�
						node.setNeighbor(someNodes.get(l), i);// ��
						l++;													// (�˴�Ҳ���԰�someNodes��ĳ��ȷ���ڵ�����ھӽڵ㣬�����ܻ����·�����ȵķ�������)
					}
				}
				// System.out.println();

			}
		}
	}

	// �ྶ·��,���ʵ��?
	public int multiPathRoute(Node srcNode, Node destNode, int maxSteps) {
		int steps = 0;

		return steps;
	}

	// ������Ľڵ�srcNode·�ɵ��ڵ�destNode��·�ɷ�������ν̰��·��(Greed Routing)
	// steps>0��ʾ·�ɳɹ���steps�Ĵ�С����·������,steps=-1��ʾ�ж�,steps=0��ʾ����maxSteps
	// ·�����ͣ��Ҳ��������һ���ڵ�ʱ��1��ʾʧ�ܣ�2��ʾѡ��ڶ�������һ���ڵ�
	public int route(Node srcNode, Node destNode, int maxSteps, int routeType) {
		int steps = 0;
		// ���������С��maxSteps������Դ�ڵ㻹û�е���Ŀ��ڵ㣬��
		for (steps = 0; steps <= maxSteps && !srcNode.equals(destNode); steps++) {
			// ��ȡԴ�ڵ�srcNode��Ŀ��ڵ�ľ���
			int distance = srcNode.getDistanceFrom(destNode);
			// ���srcNode���ھӽڵ����иþ�����ھӣ���
			if (srcNode.getNeighbor(destNode) != null) {
				// ǰ�������ھӽڵ�
				srcNode = srcNode.getNeighbor(destNode);
			} else { // ���srcNode���ھӽڵ���û�иþ�����ھӣ���
				if (routeType == 1) { // ���·������Ϊ1
					// logger.info("route fail, from "+srcNode.getNodePosition()+" to "+
					// destNode.getNodePosition());
					// srcNode.outputNeighbors();
					return -1; // ����-1����ʾ·��ʧ��
				} else if (routeType == 2) { // ���·������Ϊ2
					// ����srcNode�ľ���С��distance�Ľڵ������ѡȡһ������Ϊ��һ���ڵ�
					ArrayList<Node> distanceLessNodes = new ArrayList<Node>();
					for (int i = distance - 1; i >= 0; i--) {
						if (srcNode.getNeighbors(i).size() > 0)
							for (int j =0;j<srcNode.getNeighbors(i).size();j++)
								distanceLessNodes.add(srcNode.getNeighbors(i).get(j));
					}
					if (distanceLessNodes.size() > 0) { // ����ҵ�
						Random random = new Random();
						int nextNodeIdx = random.nextInt(distanceLessNodes
								.size());
						srcNode = distanceLessNodes.get(nextNodeIdx);
					} else { // ���û���ҵ�������srcNode�ľ������distance�Ľڵ���ѡ�������С�Ľڵ㣬��Ϊ��һ���ڵ�
						int i;
						for (i = distance + 1; i < Node.BUCKETS; i++) {
							if (srcNode.getNeighbor(i) != null)
								srcNode = srcNode.getNeighbor(i);
							break;
						}
						if (i == Node.BUCKETS) // �������û���ҵ�����·��ʧ��
							return -1;
					}
				}
			}
		}
		if (steps == -1 || steps > maxSteps) { // ���·��ʧ�ܴ�ӡlog
			//logger.info("route fail, from " + srcNode + " to " + destNode);
			//srcNode.outputNeighbors();
		}
		if (steps > maxSteps)
			return 0; // ����������·�����ȣ�����0����ʾ·��ʧ��
		return steps; // ���򣬷���·������
	}

	// ���������·�����ܣ�����ָ��
	// 1��·��·������
	// 2��·��ʧ�ܱ���
	public void testRoute() {
		int steps = 0;
		double avgSteps = 0.0; // ƽ��·������
		double successPercent = 0.0; // ·�ɳɹ���

		int totalSteps = 0, // ��·������
		successNumber = 0, // ·�ɳɹ�����
		totalNumber = 0; // ��·�ɴ���
		int someNodeSteps = 0, // ĳ�ڵ㵽�������нڵ��·�����Ⱦ�ֵ
		failNum = 0; // ĳ�ڵ㵽�������нڵ�·��ʱ��ʧ�ܴ���
		for (int i = 0; i < nodes.size(); i++) { // ���������е�ÿ���ڵ�  ��һ���÷���Node srcNode : nodes
			Node srcNode = nodes.get(i);
			someNodeSteps = 0;
			failNum = 0;
			for (Node destNode : nodes) { // ���㵽�������нڵ��·������
				if (srcNode.equals(destNode))
					continue; // ���Դ�ڵ��Ŀ��ڵ���ͬһ���ڵ㣬�ͽ�������ѭ��
				steps = route(srcNode, destNode, Node.BUCKETS * Node.BUCKETS, 1);
				// logger.info(steps+" ");

				totalNumber++; // ��·�ɴ�����1
				if (steps == 0 || steps == -1) { // ���·��ʧ��
					failNum ++;
				} else { // ���·�ɳɹ�
					totalSteps += steps; // ������·������
					successNumber++; // ·�ɳɹ�������1
					someNodeSteps += steps;
				}
			}
			//logger.info(i+"th node,routing path length=\t"+someNodeSteps/((nodes.size()-1-failNum)));
		}
		avgSteps = (double) totalSteps / successNumber; // ƽ��·������ = ��·������ /
														// ·�ɳɹ�����
		successPercent = (double) successNumber / totalNumber * 100; // ·�ɳɹ��� =
																		// ·�ɳɹ�����
																		// /
																		// ��·�ɴ���
		logger.info("ƽ��·������ = \t" + avgSteps);
		logger.info("·�ɳɹ��� = \t" + successPercent + "%");
		logger.info("��·�ɴ��� = \t" + totalNumber);

	}

	// �������ṹ���ļ���
	public void output(String fileName) {
		PrintWriter fileOutput;
		try {
			fileOutput = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName)));
			// �������������нڵ�
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

	// �����ͳ����Ϣ��
	//1���ڵ���ھӸ���������
	//2���ڵ�����ھ�ʱ����Ч��
	public void stat() {
		int avgNeighborNum = 0, sumNeighborNum = 0, sumFailPercent = 0, avgFailPercent = 0;
		// ����������ÿ���ڵ�
		for (int i = 0; i < nodes.size(); i++) {
			//��ýڵ��ھӸ���
			sumNeighborNum += nodes.get(i).getNeighborNum();
			logger.info(i + "th node: "+nodes.get(i).getNeighborNumByDistance()[15]+", "+nodes.get(i).getNeighborNumByDistance()[14]+", "+nodes.get(i).getNeighborNumByDistance()[13]+", "+nodes.get(i).getNeighborNumByDistance()[12]+", "+nodes.get(i).getNeighborNumByDistance()[11]+", "+nodes.get(i).getNeighborNumByDistance()[10]+", "+nodes.get(i).getNeighborNumByDistance()[9]+", "+nodes.get(i).getNeighborNumByDistance()[8]+", "+nodes.get(i).getNeighborNumByDistance()[7]+", "+nodes.get(i).getNeighborNumByDistance()[6]+", "+nodes.get(i).getNeighborNumByDistance()[5]+", "+nodes.get(i).getNeighborNumByDistance()[4]+", "+nodes.get(i).getNeighborNumByDistance()[3]+", "+nodes.get(i).getNeighborNumByDistance()[2]+", "+nodes.get(i).getNeighborNumByDistance()[1]+", "+nodes.get(i).getNeighborNumByDistance()[0]+", ");
			//logger.info(i + "th node, neighbor number = \t"
			//		+ nodes.get(i).getNeighborNum() + "\t, durty degree = \t"
			//		+ nodes.get(i).getFailNum());
			//���ʧЧ�ڵ����
			sumFailPercent += nodes.get(i).getFailNum();
			//������нڵ�
//			for (int j = 0; j < Node.BUCKETS; j++) {
//				if (nodes.get(i).getNeighbor(j) != null)
//					logger.info(j + "th neighbor, position = "
//							+ nodes.get(i).getNeighbor(j).getNodePosition());
//			}
		}
		logger.info("ƽ���ڵ���� = \t" + sumNeighborNum / nodes.size());
		logger.info("ƽ���ھ�ʧЧ�� = \t" + sumFailPercent / nodes.size());

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
//		for (int i = 0; i < times; i++) { // ����times��
//			logger.info("��"+i+"������, �ڵ����=\t"+Math.pow(2, base+i));
//			for (int k = -1; k <= -1;k++ ) { // ÿ�μ���ڵ㷽ʽ��ͬ
//				logger.info("�ڵ���뷽ʽ, k=\t"+k);
//				Graph graph = new Graph();
//				graph.initializeTheFirstNode(new Node((base+i)/2));
//				int totalMessageNum = 0;
//				for (int j = 0;j < Math.pow(2, base+i)-1;j++) { // ÿ�νڵ����
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
//				logger.info("�ڵ���� =\t" + graph.nodes.size());
//				logger.info("ƽ��������Ϣ���� =\t" + totalMessageNum/(Math.pow(2, base+i)));
//				graph.testRoute();
//				graph.stat();
//			}
//		}
		
		
		//graph.delNodes();
		// graph.output("graph.out2");
		// System.out.println(graph.isOutNeighborNumEqualsInNeighborNum());

	}
}
