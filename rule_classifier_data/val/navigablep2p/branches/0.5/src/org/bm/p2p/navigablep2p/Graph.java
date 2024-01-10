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
import org.bm.p2p.navigablep2p.test.Log4jExample;

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
		for (int i = 0; i < size; i++) {
			System.out.print(i + ": ");
			nodes.add(new Node());
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

	// OnlyInWalks
	// ����inNeighbors����random walk
	public int onlyInWalks(Node joiningNode, Node nowNode) {
		int messageNum = 0;
		int i;
		while (true) {
			// ������ھӸ���
			int inNeighborNum = nowNode.getInNeighborNum();
			int distance = joiningNode.getDistanceFrom(nowNode);
			int minInDistance = nowNode.getInMinDistance(); //������ھӵ���С����
			// ��1/�����ھӸ���+1���ĸ��ʽ���ǰ�ڵ�������ڵ�ĳ��ھ�
			Random random = new Random();
			int randomNum = random.nextInt(inNeighborNum+1);
			
			// �������ǰ�ڵ����������ڵ�ĳ��ھ�
			if (randomNum==inNeighborNum) {
				// �����ڵ㷢����Ϣ������ǰ�ڵ�������ڵ�ĳ��ھ�
				messageNum++;
				joiningNode.setOutNeighbor(nowNode, distance);
				// stolen link
				// ���distanceͰ�����ھӲ�Ϊ�գ�
				//if (nowNode.getInNeighbor(distance)!=null) {
				//	Node thirdNode = nowNode.getInNeighbor(distance);
				//	messageNum++;
				//	Node forthNode = thirdNode.getOutNeighbor(distance);
					//if (!=null) {
						
					//}
				//}
				// ������ڵ���뵱ǰ�ڵ�����ھ�
				nowNode.setInNeighbor(joiningNode, distance);
				break;
			}
			else { // ���ת��
				// ������ڵ���뵱ǰ�ڵ��˳���ھ�
				nowNode.setIncidentalNeighbor(joiningNode, distance);
				joiningNode.setIncidentalNeighbor(nowNode, distance);
				// ����ָ���ֲ��ĵ��ͷֲ���ѡ����һ���ڵ�
				do {
					random = new Random();
					randomNum = random.nextInt((int)Math.pow(2, Node.BUCKETS-minInDistance)-1)+1;
					for (i = 0;i<Node.BUCKETS-minInDistance;i++) {
						if (randomNum>=Math.pow(2, i)&&randomNum<Math.pow(2, i+1)) break;
					}
					
				} while (nowNode.getInNeighbor(minInDistance+i)==null);
				
			}
			//�����һ���ڵ�
			nowNode = nowNode.getInNeighbor(minInDistance+i);
			messageNum++;
			
		}
		
		return messageNum;
	}
	

	// OnlyOutWalks
	// ����outNeighbors����random walk
	public int onlyOutWalks(Node joiningNode, Node nowNode) {
		int messageNum = 0;
		int i;
		while (true) {
			// ������ھӸ���
			int outNeighborNum = nowNode.getOutNeighborNum();
			int distance = joiningNode.getDistanceFrom(nowNode);
			int minOutDistance = nowNode.getOutMinDistance(); //������ھӵ���С����
			// ��1/�����ھӸ���+1���ĸ��ʽ���ǰ�ڵ�������ڵ�ĳ��ھ�
			Random random = new Random();
			int randomNum = random.nextInt(outNeighborNum+1);
			
			// �������ǰ�ڵ����������ڵ�ĳ��ھ�
			if (randomNum==outNeighborNum) {
				// �����ڵ㷢����Ϣ������ǰ�ڵ�������ڵ�ĳ��ھ�
				messageNum++;
				joiningNode.setInNeighbor(nowNode, distance);
				// stolen link
				// ���distanceͰ�����ھӲ�Ϊ�գ�
				//if (nowNode.getInNeighbor(distance)!=null) {
				//	Node thirdNode = nowNode.getInNeighbor(distance);
				//	messageNum++;
				//	Node forthNode = thirdNode.getOutNeighbor(distance);
					//if (!=null) {
						
					//}
				//}
				// ������ڵ���뵱ǰ�ڵ�����ھ�
				nowNode.setOutNeighbor(joiningNode, distance);
				break;
			}
			else { // ���ת��
				// ������ڵ���뵱ǰ�ڵ��˳���ھ�
				nowNode.setIncidentalNeighbor(joiningNode, distance);
				// ����ָ���ֲ��ĵ��ͷֲ���ѡ����һ���ڵ�
				do {
					random = new Random();
					randomNum = random.nextInt((int)Math.pow(2, Node.BUCKETS-minOutDistance)-1)+1;
					for (i = 0;i<Node.BUCKETS-minOutDistance;i++) {
						if (randomNum>=Math.pow(2, i)&&randomNum<Math.pow(2, i+1)) break;
					}
					
				} while (nowNode.getOutNeighbor(minOutDistance+i)==null);
				
			}
			//�����һ���ڵ�
			nowNode = nowNode.getOutNeighbor(minOutDistance+i);
			messageNum++;
			
		}
		
		return messageNum;
	}
	
	public int addNode6(Node node, int type) {
		int messageNum = 0;
		Node nowNode;
		ArrayList<Node> iNeighbors;
		// �����ϵ�ڵ�
		Node bootstrapNode = bootstrap();
		for (int i = 0; i < Node.BUCKETS; i++) {
			iNeighbors = bootstrapNode.getInNeighbors(i);
			// �����ϵ�ڵ�ľ���Ϊi���ھӴ���
			if (iNeighbors.size()>0)
				for (int j=0;j<iNeighbors.size();j++){
					// ׼�����ھӷ�����Ϣ
					nowNode = iNeighbors.get(j);
					if (nowNode !=null)
					messageNum += this.onlyInWalks(node, nowNode);
				}
		}
		for (int i = 0; i < Node.BUCKETS; i++) {
			iNeighbors = bootstrapNode.getOutNeighbors(i);
			// �����ϵ�ڵ�ľ���Ϊi���ھӴ���
			if (iNeighbors.size()>0)
				for (int j=0;j<iNeighbors.size();j++){
					// ׼�����ھӷ�����Ϣ
					nowNode = iNeighbors.get(j);
					if (nowNode !=null)
					messageNum += this.onlyOutWalks(node, nowNode);
				}
		}
		// ����ϵ�ڵ�������ڵ���ھ�
		node.setOutNeighbor(bootstrapNode, node.getDistanceFrom(bootstrapNode));
		bootstrapNode.setInNeighbor(node, node.getDistanceFrom(bootstrapNode));

		nodes.add(node);
		return messageNum;
	}
	
	
	// ����ڵ�node, ���������͵���Ϣ����
	// type=1��ʾȱʡ��ʽ
	// ���������Ż���ʽ��1��stolen link�������ҵ�����Ϊdistance��ĳ�ڵ�ʱ�����ͬ����ڵ㻹�У������ѡȡһ��
	// ������ѡȡ������ڵ��ͬ����ڵ㻹�У��ͰѸ����ѡ��ڵ�������ڵ���ھӣ�����������ѡ��С����ڵ�ĸ���
	// 2�����ҵ�����Ϊdistance��ĳ�ڵ�ʱ�����ͬ����ڵ㻹û�У��Ͳ��Һͼ���ڵ�������distance�����нڵ㣬�Ѽ���ڵ�������ǵ��ھ�
	// ˳���ڵ�Ҫ��Ҫ��Ϊ�������ʱ���ʵ�ѡ��Ԫ�ء�
	public int addNode5(Node node, int type) {
		
		int maxDepth = Node.BUCKETS;
		int messageNum = 0;
		// �����ϵ�ڵ�
		Node bootstrapNode = bootstrap();

		// ģ�������͵���Ϣ
		class Message {
			Node srcNode; // ����ڵ�
			Node nowNode; // ��ǰ�ڵ�
			int depth;    // ��ǰ���

			Message(Node srcNode, Node nowNode, int depth) {
				this.srcNode = srcNode;
				this.nowNode = nowNode;
				this.depth = depth;
			}
		}
		
		// ��ʼ��������Ϣ������ϵ�ڵ�������ھӽڵ㷢����Ϣ
		Queue<Message> visitedNodes = new LinkedList<Message>();
		Message visitedNode;
		for (int i = 0; i < Node.BUCKETS; i++) {
			ArrayList<Node> iNeighbors = bootstrapNode.getOutNeighbors(i);
			// �����ϵ�ڵ�ľ���Ϊi���ھӴ���
			if (iNeighbors.size()>0)
				for (int j=0;j<iNeighbors.size();j++){
					// ׼�����ھӷ�����Ϣ
					visitedNode = new Message(node, iNeighbors.get(j), 0);
					visitedNodes.add(visitedNode);
				}
		}
		
		// ����ϵ�ڵ�������ڵ���ھ�
		node.setOutNeighbor(bootstrapNode, node.getDistanceFrom(bootstrapNode));
		bootstrapNode.setOutNeighbor(node, node.getDistanceFrom(bootstrapNode));
		
		
		int distance;
		// ���׼�����͵���Ϣ��Ϊ��
		while (visitedNodes.size() > 0) {
			// ȡ����һ��������Ϣ
			visitedNode = visitedNodes.remove();
			// ������Ϣ
			messageNum++;
			Node srcNode = visitedNode.srcNode;
			Node nowNode = visitedNode.nowNode;
			
			// ��õ�ǰ�ڵ�ͼ���ڵ�ľ���
			distance = srcNode.getDistanceFrom(nowNode);
			// logger.info(messageNum+"th node, position = "+visitedNode.nowNode.getNodePosition());
			
			// �����Ϣ���С�����ֵ
			if (visitedNode.depth <= maxDepth) {
				/**
				 * �������Ϊi����
				 * ����С��i�Ľڵ����N(<i)=2^i
				 * �������i�Ľڵ����N(=i)=2^i
				 * �������i�Ľڵ����N(>i)=2^i-2^(i+1)
				 * 
				 * �����(<i,=i,>i)����һ��ת���ĸ�����þ���Ľڵ�����ɷ���
				 * 1/2^i, 1/2^i, 1/(2^n-2^(i+1)) ==>  1, 1, 1/(2^(n-i)-2) ==> 2^(n-i)-2, 2^(n-i)-2, 1
				 */
				Random random = new Random();
				//��[0, 2^(n-i+1)-3)�����ѡ��һ����
				int randomNum = random.nextInt((int) Math.pow(2, (Node.BUCKETS
						- distance + 1)) - 3);
				// ��������С��1����������i���ھ�ת��
				if (randomNum < 1) { // ����
					ArrayList<Node> nextNodes = new ArrayList<Node>();
					//��þ������distance�������ھ�
					for (int i = distance + 1; i < Node.BUCKETS; i++) {
						ArrayList<Node> iNeighbors = nowNode.getOutNeighbors(i);
						if (iNeighbors.size()>0)
							for (int j=0;j<iNeighbors.size();j++){
								if (iNeighbors.get(j)!=srcNode) nextNodes.add(iNeighbors.get(j));
							}
					}
					if (nextNodes.size() > 0) {
						Random random2 = new Random();
						int chosedIdx = random2.nextInt(nextNodes.size());
						Message message = new Message(srcNode, nextNodes
								.get(chosedIdx), visitedNode.depth + 1);
						visitedNodes.add(message);
					}

				} else if (randomNum < (int) Math.pow(2,
						(Node.BUCKETS - distance) - 1)) { // ����
					Node nextNode = nowNode.getOutNeighbor(distance);
					if (nextNode != null
							&& nextNode != srcNode) {
						Message message = new Message(srcNode, nextNode, visitedNode.depth + 1);
						visitedNodes.add(message);
					}

				} else { // С��
					ArrayList<Node> nextNodes = new ArrayList<Node>();
					for (int i = distance - 1; i >= 0; i--) {
						ArrayList<Node> iNeighbors = nowNode.getOutNeighbors(i);
						if (iNeighbors.size()>0)
							for (int j=0;j<iNeighbors.size();j++){
								if (iNeighbors.get(j)!=srcNode) nextNodes.add(iNeighbors.get(j));
							}
					}
					if (nextNodes.size() > 0) {
						Random random2 = new Random();
						int chosedIdx = random2.nextInt(nextNodes.size());
						Message message = new Message(srcNode, nextNodes
								.get(chosedIdx), visitedNode.depth + 1);
						visitedNodes.add(message);
					}

				
				}
			}
			nowNode.setOutNeighbor(srcNode, distance);
			
			//��1/maxStepth���������ھ�
			Random random = new Random();
			int chosedIdx = random.nextInt(5);
			if (chosedIdx==1) {
			//������ڵ���Ϊ��ǰ�ڵ���ھ�
			if (nowNode.getOutNeighbor(distance)!=null) {
				srcNode.setOutNeighbor(nowNode, distance);
			}
			else {
				routingToAllNodesLessthanDistance2(srcNode,nowNode,distance-1);
			}
			// ���ھ�Ҳ�ӽ�ȥ��
			}
		}

		nodes.add(node);
		return messageNum;
	}

	// ���Һͽڵ�node�ľ���С�ڵ���distance�����нڵ�(�������)
	public int routingToAllNodesLessthanDistance2(Node srcNode, Node node,
			int distance) {
		class NodeAndDistance {
			Node node;
			int distance;

			NodeAndDistance(Node node, int distance) {
				this.node = node;
				this.distance = distance;
			}
		}
		;
		int numth = 0;
		Queue<NodeAndDistance> visitedNodes = new LinkedList<NodeAndDistance>();
		NodeAndDistance visitedNode = new NodeAndDistance(node, distance);
		visitedNodes.add(visitedNode);
		while (visitedNodes.size() > 0) {
			visitedNode = visitedNodes.remove();
			numth++;
			// visit node
			//srcNode.setNeighbor(visitedNode.node, distance + 1);
			visitedNode.node.setOutNeighbor(srcNode, distance + 1);
			logger.info(numth + "th node, position = "
					+ visitedNode.node.getNodePosition());
			for (int i = 0; i <= visitedNode.distance; i++) {
				if (visitedNode.node.getOutNeighbor(i) != null) {
					NodeAndDistance nextNode = new NodeAndDistance(
							visitedNode.node.getOutNeighbor(i), i - 1);

					visitedNodes.add(nextNode);
				}
			}
		}
		return numth;
	}

	// ���Һͽڵ�node�ľ���С�ڵ���distance�����нڵ�
	public int routingToAllNodesLessthanDistance(Node node, int distance,
			int numth) {
		// �������
		int i;
		for (i = 0; i < Node.BUCKETS; i++)
			if (node.getOutNeighbor(i) != null)
				break;
		if (distance < i)
			return numth - 1;
		logger.info(numth + "th node, distance = " + distance + " position = "
				+ node.getNodePosition());
		Node rightNode = node.getOutNeighbor(distance);
		if (rightNode != null && rightNode != node)
			numth += routingToAllNodesLessthanDistance(rightNode, distance - 1,
					numth + 1);
		// Node leftNode = node.getNeighbor(distance-1);
		// if (leftNode != null&&leftNode!=node)

		numth += routingToAllNodesLessthanDistance(node, distance - 1,
				numth + 1);
		return numth;
	}

	// �ѽڵ�bootstrap���������ھӽڵ����ڵ�node
	public void addNodeNeighbors(Node node, Node bootstrapNode) {
		// ��bootstrap�ڵ�������ھӼ��뵽����ڵ���ھ���
		int distance = node.getDistanceFrom(bootstrapNode);
		node.setOutNeighbor(bootstrapNode, distance);
		bootstrapNode.setOutNeighbor(node, distance);

		// ����distance���ھ�
		for (int j = distance + 1; j < Node.BUCKETS; j++) {
			if (bootstrapNode.getOutNeighbors(j).size() > 0) {
				for (int k = 0; k < bootstrapNode.getOutNeighbors(j).size(); k++) {
					node.setOutNeighbor(bootstrapNode.getOutNeighbors(j).get(k), j);
					//bootstrapNode.getOutNeighbors(j).get(k).setOutNeighbor(node, j);
				}
			}
		}
		// С��distance���ھ�
		for (int j = distance - 1; j >= 0; j--) {
			if (bootstrapNode.getOutNeighbors(j).size() > 0) {
				for (int k = 0; k < bootstrapNode.getOutNeighbors(j).size(); k++) {
					node.setOutNeighbor(bootstrapNode.getOutNeighbors(j).get(k),
							distance);
					//bootstrapNode.getOutNeighbors(j).get(k).setOutNeighbor(node,
					//		distance);
				}
			}
		}
		// ����distance���ھ�
		if (bootstrapNode.getOutNeighbors(distance).size() > 0) {
			for (int k = 0; k < bootstrapNode.getOutNeighbors(distance).size(); k++) {
				if (node.getDistanceFrom(bootstrapNode.getOutNeighbors(distance)
						.get(k)) >= 0) {
					node.setOutNeighbor(bootstrapNode.getOutNeighbors(distance)
							.get(k), node.getDistanceFrom(bootstrapNode
							.getOutNeighbors(distance).get(k)));
					//bootstrapNode.getOutNeighbors(distance).get(k).setOutNeighbor(
					//		node,
					//		node.getDistanceFrom(bootstrapNode.getOutNeighbors(
					//				distance).get(k)));
				}
			}
		}
	}

	// ����ڵ㣺���ѡȡBUCKETS���ڵ㣬�����ǵ������ھӼ��뵽����ڵ���ھ���
	public int addNode3(Node node) {
		Node bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
		for (int i = 0; i < Node.BUCKETS; i++) {
			addNodeNeighbors(node, bootstrapNode);
			Random random = new Random();
			int chosedIdx = random.nextInt(bootstrapNode.BUCKETS);
			while (bootstrapNode.getOutNeighbor(chosedIdx) == null)
				chosedIdx = random.nextInt(bootstrapNode.BUCKETS);
			bootstrapNode = bootstrapNode.getOutNeighbor(chosedIdx);
		}
		nodes.add(node);
		return 1;
	}

	public int addNode4(Node node) {
		int messageNum = 0;

		Node bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
		int distance = node.getDistanceFrom(bootstrapNode);
		if (distance >= 0) {
			node.setOutNeighbor(bootstrapNode, distance);
			bootstrapNode.setOutNeighbor(node, distance);
		}
		for (int i = 0; i < Node.BUCKETS; i++) {
			// bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
			// �������һ���ͽڵ�node�ľ���Ϊi���½ڵ�randomDestNode
			Node srcNode = bootstrapNode;
			Node randomDestNode = new Node(node, i);
			// logger.info("node: "+node.getNodePosition()+
			// " randomdestNode: "+randomDestNode.getNodePosition()+" distance = "+i);
			while (srcNode != randomDestNode && srcNode != null) {
				srcNode = srcNode.getOutNeighbor(srcNode
						.getDistanceFrom(randomDestNode));
				if (srcNode != null) {
					distance = node.getDistanceFrom(srcNode);
					if (distance >= 0) {
						addNodeNeighbors(node, srcNode);
						node.setOutNeighbor(srcNode, distance);
						srcNode.setOutNeighbor(node, distance);
					}
				}
			}
		}
		nodes.add(node);
		return messageNum;
	}

	// ����ڵ�node���������跢�͵���Ϣ����
	public int addNode2(Node node) {
		int messageNum = 0; // ����һ���ڵ����跢�͵���Ϣ����
		for (int i = 0; i < Node.BUCKETS; i++) {
			// �������һ���ͽڵ�node�ľ���Ϊi���½ڵ�randomDestNode
			// Node randomDestNode = new Node(node,i);
			// ���ڵ�randomDestNode��ΪĿ��ڵ㣬��bootstrapNode��ʼ·�ɵ�randomDestNode
			// ���������м�ڵ�������ڵ�node�ĳ��ھӣ����ڵ�node������Щ�м�ڵ�����ھ�
			// ����м�ڵ�Ϊ�գ�����м�ڵ�����ھ��Ѿ����ڣ�
			// ����Ѿ�֪��ĳ�ڵ�����ھӺͳ��ھӣ��Ϳ�������ڵ����ھӺͳ��ھӵľ���

			Node bootstrapNode = bootstrap(); // ���bootstrap�ڵ�
			int maxDestNodeNum = (int) Math.pow(2, Node.BUCKETS / (i + 1)); // ÿ�η���Ŀ��ڵ��������
			// ����ϵ�ڵ�bootstrapNode��ʼ���������������ڵ�node�ľ���Ϊi�Ľڵ�
			// �����ϵ�ڵ�bootstrapNode�ͼ���ڵ�ľ������i
			while ((bootstrapNode != null)
					&& (node.getDistanceFrom(bootstrapNode) > i)) {
				// node.setNeighbor(bootstrapNode,
				// node.getDistanceFrom(bootstrapNode));

				bootstrapNode = bootstrapNode.getOutNeighbor(node
						.getDistanceFrom(bootstrapNode));
				// bootstrapNode.setNeighbor(node,
				// node.getDistanceFrom(bootstrapNode));
				messageNum++;
			}
			if ((bootstrapNode != null)
					&& (node.getDistanceFrom(bootstrapNode) < i)) {
				// node.setNeighbor(bootstrapNode,
				// node.getDistanceFrom(bootstrapNode));
				// bootstrapNode.setNeighbor(node,
				// node.getDistanceFrom(bootstrapNode));
				bootstrapNode = bootstrapNode.getOutNeighbor(i);
				messageNum++;
			}
			if ((bootstrapNode != null)
					&& (node.getDistanceFrom(bootstrapNode) == i)) { // �����ϵ�ڵ�ͼ���ڵ�ľ���=i
				// �������к���ϵ�ڵ�ľ���С��i�Ľڵ㣬��Щ�ڵ�ͼ���ڵ�ľ��붼����i
				// ����һ��������ȵ��������㷨
				Queue<Node> visitedNodes = new LinkedList<Node>();
				visitedNodes.add(bootstrapNode); // ����ϵ�ڵ�����ѷ��ʽڵ����
				int destNodeNum = 0; // �ѷ��ʵĽڵ����
				// ֻҪ�ѷ��ʽڵ������û�дﵽmaxDestNodeNum�����ң��ѷ��ʽڵ���в�Ϊ��
				while ((destNodeNum <= maxDestNodeNum)
						&& (visitedNodes.size() > 0)) {
					// ���ѷ��ʽڵ������ȡ��һ���ڵ�
					Node hasVisitedNode = visitedNodes.remove();
					// ����ýڵ�ľ���Ϊi�ĳ��ھ�Ϊ�գ��ͰѼ���ڵ�node��Ϊ�ýڵ����Ϊi�ĳ��ھ�
					if (hasVisitedNode.getOutNeighbor(i) == null) {
						hasVisitedNode.setOutNeighbor(node, i);
						node.setOutNeighbor(hasVisitedNode, i);
						// maxDestNodeNum = 10000;
					}
					// �ýڵ���Ϊ����ڵ�node�ĳ��ھ�
					node.setOutNeighbor(hasVisitedNode, i);
					hasVisitedNode.setOutNeighbor(node, i);

					// ִ��stolen
					// link����hasVisitedNode��һ�����ھӽ������ڵ㣬������ڵ��������ʱ��Խ�������ھ�Խ�ࡱ������
					// for (int k = i+1; k < Node.BUCKETS; k++) {
					// if (hasVisitedNode.getInNeighbor(k)!=null) {
					// hasVisitedNode.getInNeighbor(k).setOutNeighbor(node, k);
					// node.setInNeighbor(hasVisitedNode.getInNeighbor(k), k);
					// }
					// }

					destNodeNum++;
					messageNum++;
					// ���ʺͽڵ�hasVisitedNode�ľ���С��i�����нڵ�
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

	// ɾ���ڵ㣬�������跢�͵���Ϣ����
	// ����ɾ���ڵ�����ھӽڵ�ĳ��ھ��ñ�ɾ���ڵ�ĳ��ھ����
	public int delNode(Node node) {
		int messageNum = 0;
		// ������ɾ���ڵ�����ھӽڵ��б��е��������ھ�
		int j = 0;
		for (int i = 0; i <= Node.BUCKETS - 1; i++) {
			if (node.getOutNeighbor(i) != null) {
				for (int k = j; k <= i; k++) {
					if (node.getOutNeighbor(k) != null) {
						node.getOutNeighbor(i).setOutNeighbor(node.getOutNeighbor(k), i);
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
			System.out.println("delNode messageNum: " + messageNum);
			nodes.remove(chosenNodeIdx);
			testRoute();
		}
	}

	/**
	 * ���ڵ�node�������磬ʹ��Random Walk����Ϊ�ڵ�node���ӱ�
	 * 
	 * ����ڵ�a��һ���ھӽڵ�b���ڵ�c�ǽڵ�b��ĳ���ھӽڵ㣬��ô�� (1) If D(b,c)=D(a,b), Then D(a,c)<D(a,b)
	 * (2) If D(b,c)>D(a,b), Then D(a,c)=D(b,c)>D(a,b) (3) If D(b,c)<D(a,b),
	 * Then D(a,c)=D(a,b)
	 * 
	 * ͨ�����»��ƻ��NextHops: (1): �Խڵ�cΪNextHop�� (2): �������ѡ��ڵ�c��ΪNextHop (3):
	 * �����ڵ���룬ֹͣ
	 */
	/*
	 * public void addNode(Node node) { Node bootstrapNode = bootstrap(); //
	 * ���bootstrap�ڵ� //int maxSteps = Node.BUCKETS; // ?����� int maxDistance =
	 * Node.BUCKETS-1, minDistance = 0; int messageNumber =
	 * randomWalk(maxDistance, minDistance, node, bootstrapNode, 0);
	 * System.out.println("messages: " + messageNumber); nodes.add(node); }
	 * 
	 * private int randomWalk(int maxDistance, int minDistance, Node joinNode,
	 * Node concactNode, int messageNumber) { if (messageNumber++ > MAXMESSAGES)
	 * return messageNumber;
	 * 
	 * // ��ü���ڵ����ϵ�ڵ�ľ��� int curDistance =
	 * joinNode.getDistanceFrom(concactNode); // �洢 ��ϵ�ڵ��ھ��� �����ڵ�ľ���
	 * ����ϵ�ڵ������ڵ�ľ��� ��ȵĽڵ� ArrayList<Node> distanceEqualsNodes = new
	 * ArrayList<Node> (); // ����ϵ�ڵ���� distanceEqualsNodes.add(concactNode); //
	 * ����ϵ�ڵ��ھ��е�ͬ����ڵ���� for (int i = curDistance - 1; i>=0;i--) { if
	 * (concactNode.getOutNeighbor(i) != null)
	 * distanceEqualsNodes.add(concactNode.getOutNeighbor(i)); } //
	 * ��ͬ����ڵ��б������ѡ��һ���ڵ� Random random = new Random(); int chosedNodeIdx =
	 * random.nextInt(distanceEqualsNodes.size()); //���ýڵ���뵽����ڵ���ھӽڵ�
	 * joinNode.setOutNeighbor(distanceEqualsNodes.get(chosedNodeIdx),
	 * curDistance); //��Ҫ��Ҫ������ڵ����ýڵ���ھ��أ���
	 * distanceEqualsNodes.get(chosedNodeIdx).setOutNeighbor(joinNode,
	 * curDistance);
	 * 
	 * //ѡ����һ���ڵ㣬�����ڵ����С��curDistance�Ľڵ㣨���ֻ��һ���� Node distanceLessNode =
	 * concactNode.getOutNeighbor(curDistance); if (distanceLessNode!=null &&
	 * !distanceLessNode.equals(joinNode)) { // &&
	 * joinNode.getDistanceFrom(distanceLessNode) >= minDistance messageNumber =
	 * randomWalk(maxDistance, minDistance, joinNode, distanceLessNode,
	 * messageNumber); //curDistance-1 }
	 * 
	 * //ѡ����һ���ڵ㣬�����ڵ�������curDistance�Ľڵ� ArrayList<Node> distanceLargerNodes =
	 * new ArrayList<Node> (); for (int i = curDistance + 1; i <=
	 * maxDistance;i++) { if (concactNode.getOutNeighbor(i) != null)
	 * distanceLargerNodes.add(concactNode.getOutNeighbor(i)); } if
	 * (distanceLargerNodes.size()>0) { random = new Random(); chosedNodeIdx =
	 * random.nextInt(distanceLargerNodes.size()); messageNumber =
	 * randomWalk(maxDistance,minDistance , joinNode,
	 * distanceLargerNodes.get(chosedNodeIdx), messageNumber); //curDistance+1 }
	 * return messageNumber; }
	 * 
	 * private int randomWalk2(int maxDistance, int minDistance, Node joinNode,
	 * Node concactNode, int messageNumber) { if (messageNumber++ > MAXMESSAGES)
	 * return messageNumber;
	 * 
	 * // ��ü���ڵ����ϵ�ڵ�ľ��� int curDistance =
	 * joinNode.getDistanceFrom(concactNode); // �洢 ��ϵ�ڵ��ھ��� �����ڵ�ľ���
	 * ����ϵ�ڵ������ڵ�ľ��� ��ȵĽڵ� ArrayList<Node> distanceEqualsNodes = new
	 * ArrayList<Node> (); // ����ϵ�ڵ���� distanceEqualsNodes.add(concactNode); //
	 * ����ϵ�ڵ��ھ��е�ͬ����ڵ���� for (int i = curDistance - 1; i>=0;i--) { for (int j =
	 * 0; j < Node.K; j++) { if (concactNode.getOutNeighborByIdx(i,j) != null)
	 * distanceEqualsNodes.add(concactNode.getOutNeighborByIdx(i,j)); } } //
	 * ��ͬ����ڵ��б������ѡ��K���ڵ� if (distanceEqualsNodes.size()>Node.K) { for (int i =
	 * 0; i < Node.K; i++) { Random random = new Random(); int chosedNodeIdx =
	 * random.nextInt(distanceEqualsNodes.size());
	 * //���ýڵ���뵽����ڵ���ھӽڵ�(���ܳ�����ͬ�Ľڵ�!)
	 * joinNode.setOutNeighbor(distanceEqualsNodes.get(chosedNodeIdx),
	 * curDistance); //��Ҫ��Ҫ������ڵ����ýڵ���ھ��أ���
	 * distanceEqualsNodes.get(chosedNodeIdx).setOutNeighbor(joinNode,
	 * curDistance); } } else { for (int i = 0; i < distanceEqualsNodes.size();
	 * i++) { //���ýڵ���뵽����ڵ���ھӽڵ�(���ܳ�����ͬ�Ľڵ�!) if
	 * (!joinNode.equals(distanceEqualsNodes.get(i))) {
	 * joinNode.setOutNeighbor(distanceEqualsNodes.get(i), curDistance);
	 * //��Ҫ��Ҫ������ڵ����ýڵ���ھ��أ���
	 * distanceEqualsNodes.get(i).setOutNeighbor(joinNode, curDistance);
	 * 
	 * } } }
	 * 
	 * //ѡ����һ���ڵ㣬�����ڵ����С��curDistance�Ľڵ㣨���ֻ��һ���� for (int i = 0; i < Node.K;
	 * i++) { Node distanceLessNode =
	 * concactNode.getOutNeighborByIdx(curDistance,i); if
	 * (distanceLessNode!=null && !distanceLessNode.equals(joinNode)) { // &&
	 * joinNode.getDistanceFrom(distanceLessNode) >= minDistance messageNumber =
	 * randomWalk2(maxDistance, minDistance, joinNode, distanceLessNode,
	 * messageNumber); //curDistance-1 } }
	 * 
	 * //ѡ����һ���ڵ㣬�����ڵ�������curDistance�Ľڵ�
	 * 
	 * ArrayList<Node> distanceLargerNodes = new ArrayList<Node> (); for (int i
	 * = curDistance + 1; i <= maxDistance;i++) { for (int j = 0; j < Node.K;
	 * j++) { if (concactNode.getOutNeighborByIdx(i,j) != null)
	 * distanceLargerNodes.add(concactNode.getOutNeighborByIdx(i,j)); } } if
	 * (distanceLargerNodes.size()>0) { //Random random = new Random();
	 * //chosedNodeIdx = random.nextInt(distanceLargerNodes.size()); for (int i
	 * = 0; i < distanceLargerNodes.size() ;i++) { messageNumber =
	 * randomWalk2(maxDistance,minDistance , joinNode,
	 * distanceLargerNodes.get(i), messageNumber); //curDistance+1 } } return
	 * messageNumber; }
	 */

	// Ϊ�������ӱߣ�����Klernberg��Navigable Networkģ��(����ʽ�㷨)
	public void addEdgesAccordingToDistance() {
		ArrayList<Node> someNodes = new ArrayList<Node>();
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
				if (someNodes.size() > 0) { // ����ڵ㼯�ϲ�Ϊ��
					// System.out.print(i + " ");
					// �Ӿ���Ϊi�Ľڵ㼯�������ѡȡһ���ڵ�(?����ģ�͸���Ӧ��Ϊ1/2^i)
					Random random = new Random();
					int chosedNodeIdx = random.nextInt(someNodes.size());
					// �����ѡȡ�Ľڵ���뱾�ڵ���ھӽڵ��б�
					node.setOutNeighbor(someNodes.get(chosedNodeIdx), i);// ��
																		// (�˴�Ҳ���԰�someNodes��ĳ��ȷ���ڵ�����ھӽڵ㣬�����ܻ����·�����ȵķ�������)
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
						if (srcNode.getOutNeighbor(i) != null)
							distanceLessNodes.add(srcNode.getOutNeighbor(i));
					}
					if (distanceLessNodes.size() > 0) { // ����ҵ�
						Random random = new Random();
						int nextNodeIdx = random.nextInt(distanceLessNodes
								.size());
						srcNode = distanceLessNodes.get(nextNodeIdx);
					} else { // ���û���ҵ�������srcNode�ľ������distance�Ľڵ���ѡ�������С�Ľڵ㣬��Ϊ��һ���ڵ�
						int i;
						for (i = distance + 1; i < Node.BUCKETS; i++) {
							if (srcNode.getOutNeighbor(i) != null)
								srcNode = srcNode.getOutNeighbor(i);
							break;
						}
						if (i == Node.BUCKETS) // �������û���ҵ�����·��ʧ��
							return -1;
					}
				}
			}
		}
		if (steps == -1 || steps > maxSteps) { // ���·��ʧ�ܴ�ӡlog
			logger.info("route fail, from " + srcNode + " to " + destNode);
			srcNode.outputNeighbors();
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
		for (Node srcNode : nodes) { // ���������е�ÿ���ڵ�
			for (Node destNode : nodes) { // ���㵽�������нڵ��·������
				if (srcNode.equals(destNode))
					continue; // ���Դ�ڵ��Ŀ��ڵ���ͬһ���ڵ㣬�ͽ�������ѭ��
				steps = route(srcNode, destNode, Node.BUCKETS * Node.BUCKETS, 1);
				// logger.info(steps+" ");

				totalNumber++; // ��·�ɴ�����1
				if (steps == 0 || steps == -1) { // ���·��ʧ��
				} else { // ���·�ɳɹ�
					totalSteps += steps; // ������·������
					successNumber++; // ·�ɳɹ�������1
				}
			}
			// logger.info('\n');
		}
		avgSteps = (double) totalSteps / successNumber; // ƽ��·������ = ��·������ /
														// ·�ɳɹ�����
		successPercent = (double) successNumber / totalNumber * 100; // ·�ɳɹ��� =
																		// ·�ɳɹ�����
																		// /
																		// ��·�ɴ���
		logger.info("ƽ��·������ = " + avgSteps);
		logger.info("·�ɳɹ��� = " + successPercent + "%");

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
					if (tmpNode.getOutNeighbor(j) != null) {
						// fileOutput.print(tmpNode.getOutNeighbor(j).hashCode()+", ");
						fileOutput.print(tmpNode.getOutNeighbor(j)
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

	// �����ͳ����Ϣ��1���ڵ���ھӸ�����2���ڵ�����ھ�ʱ����Ч��
	public void stat() {
		int avgNeighborNum = 0, sumNeighborNum = 0, sumFailPercent = 0, avgFailPercent = 0;
		for (int i = 0; i < nodes.size(); i++) {
			sumNeighborNum += nodes.get(i).getNeighborNum();
			logger.info(i + "th node, neighbor number = "
					+ nodes.get(i).getNeighborNum() + ", durty degree = "
					+ nodes.get(i).getFailNum());
			sumFailPercent += nodes.get(i).getFailNum();
			for (int j = 0; j < Node.BUCKETS; j++) {
				if (nodes.get(i).getOutNeighbor(j) != null)
					logger.info(j + "th neighbor, position = "
							+ nodes.get(i).getOutNeighbor(j).getNodePosition());
			}
		}
		logger.info("ƽ���ڵ���� = " + sumNeighborNum / nodes.size() + "ƽ���ھ�ʧЧ�� = "
				+ sumFailPercent / nodes.size());

	}

	public static void main(String[] argv) {
		PropertyConfigurator.configure("sort2.properties");
		// Graph graph = new Graph(64);
		// graph.addEdgesAccordingToDistance();

		Graph graph = new Graph();
		graph.initializeTheFirstNode(new Node());
		for (int i = 0; i < 1024; i++) {
			Node node = new Node();
			int messageNum = graph.addNode4(node);
			logger.info(i + "th node," + "messages: " + messageNum
					+ " neighbor number = " + node.getNeighborNum());

		}
		// graph.routingToAllNodesLessthanDistance2(graph.nodes.get(32),
		// Node.BUCKETS-1);
		graph.testRoute();
		graph.stat();
		// graph.delNodes();
		// graph.output("graph.out2");
		// System.out.println(graph.isOutNeighborNumEqualsInNeighborNum());

	}
}
