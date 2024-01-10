package org.bm.p2p.navigablep2p;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 
 * @author duanshuiliu
 * 网络中的节点
 *
 */
public class Node {
	static final int BUCKETS = 16; // 桶(数值是32的倍数，也可以是16，还可以是8)
	static final int K = 3; // 每桶的元素个数

	static Logger logger = Logger.getLogger(Node.class.getName());
	
	private int failNum = 0;
	
	private BitSet nodePosition; //节点位置

	private ArrayList<Node> neighbors[]; // 邻居节点列表，[某元组的索引值就是就是该节点和本节点的距离]
	
	// 将距离为distance的节点neighbor加入出邻居节点列表
	public void setNeighbor(Node neighbor, int distance) {
		int i;
		if (distance < 0) return;
		// 如果桶没满，把该节点加入桶
		if (neighbors[distance].size() < K) {
			for (i = 0; i < neighbors[distance].size(); i++) {
				if (neighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			neighbors[distance].add(neighbor);
		}
		else {
			//如果桶已满，则从桶里随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				neighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// 获得距离为distance的出邻居(随机)
	public Node getNeighbor(int distance) {
		if (distance >=0&&neighbors[distance].size() > 0) {
			Random random = new Random();
			return neighbors[distance].get(random.nextInt(neighbors[distance].size()));
		}
		return null;
	}

	// 获得距离目标节点targetNode最近的邻居
	public Node getNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		int minDistance = distance;
		if (distance>=0&&neighbors[distance].size() > 0) {
			for (int i = 0; i < neighbors[distance].size(); i++) {
				if (neighbors[distance].get(i).getDistanceFrom(targetNode) < minDistance) {
					minDistance = neighbors[distance].get(i).getDistanceFrom(targetNode);
					neighbor = neighbors[distance].get(i);
				}
			}
		}
		return neighbor;
	}

	// 获得距离为distance的所有邻居
	public ArrayList<Node> getNeighbors(int distance) {
		return neighbors[distance];
	}
	
	//初始化网络节点，位置为随机值
	@SuppressWarnings("unchecked")
	public Node() {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandom();
		this.neighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++)
			neighbors[i] = new ArrayList<Node>();
		//System.out.println(nodePosition);
	}

	//初始化网络节点，位置和节点node的距离等于distance，随机选取
	@SuppressWarnings("unchecked")
	public Node(Node node, int distance) {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandomByDistance(node, distance);
		this.neighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++)
			neighbors[i] = new ArrayList<Node>();
		//System.out.println(nodePosition);
	}

	@SuppressWarnings("unchecked")
	public Node(BitSet nodePosition) {
		super();
		this.nodePosition = nodePosition;
		this.neighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++)
			neighbors[i] = new ArrayList<Node>();
	}

	// 随机生成节点的位置
	public void setNodePositionRandom() {
		Random random = new Random();
		int randomInt;
		boolean randomBool;
		int i,j;
		assert((BUCKETS%32==0)||(BUCKETS<32)); //下面的循环以此为前提: BUCKETS是32的倍数或者等于16或者等于8
		for (i =0 ; i < ((BUCKETS<32)?1:BUCKETS/32); i++) { // 依次生成BUCKETS/32个随机整数
			randomInt = random.nextInt();
			//randomInt = (int) (random.nextGaussian()*Math.pow(2, 32));
			for(j =0 ; j < ((BUCKETS<32)?BUCKETS:32); j++) {
				if ((randomInt & 1) == 1) randomBool = true;
				else randomBool = false;
				nodePosition.set(i*32+j, randomBool);
				randomInt >>= 1; //右移一位
			}
		}
/*		for (i=0;i<BUCKETS;i++) {
			nodePosition.set(i, random.nextBoolean());
		}
*/	}
	
	//随机生成和节点node的距离为distance的节点位置
	public void setNodePositionRandomByDistance(Node node, int distance) {
		Random random = new Random();
		int randomInt;
		boolean randomBool;
		int i,j;
		assert((BUCKETS%32==0)||(BUCKETS==16)||(BUCKETS==8)); //下面的循环以此为前提: BUCKETS是32的倍数或者等于16或者等于8
		assert(distance<BUCKETS&&distance>=0); // 距离位于[0, BUCKETS-1]
		for (i =0 ; i < ((BUCKETS==16)||(BUCKETS==8)?1:BUCKETS/32); i++) { // 依次生成BUCKETS/32个随机整数
			randomInt = random.nextInt();
			//randomInt = (int) (random.nextGaussian()*Math.pow(2, 32));
			for(j =0 ; j < ((BUCKETS==8)?8:(BUCKETS==16?16:32)); j++) {
				if ((randomInt & 1) == 1) randomBool = true;
				else randomBool = false;
				nodePosition.set(i*32+j, randomBool);
				randomInt >>= 1; //右移一位
			}
		}
		// 将对应位置位
		i = Node.BUCKETS-1;
		while (i>distance) {
			nodePosition.set(i, node.getNodePosition().get(i));
			i--;
		}
/*		for (i=0;i<BUCKETS;i++) {
			nodePosition.set(i, random.nextBoolean());
		}
*/	}

	public BitSet getNodePosition() {
		return nodePosition;
	}

	public void setNodePosition(BitSet nodePosition) {
		this.nodePosition = nodePosition;
	}
	
	// 计算和节点node之间的距离
	public int getDistanceFrom(Node node) {
		int ret = 0;
		// clone本节点（因为在计算距离的过程中，由于xor操作，本节点的距离要发生改变）！
		BitSet twinPosition = (BitSet) this.nodePosition.clone();
		twinPosition.xor(node.getNodePosition());
		ret = twinPosition.length() - 1;
		return ret;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodePosition == null) ? 0 : nodePosition.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (nodePosition == null) {
			if (other.nodePosition != null)
				return false;
		} else if (!nodePosition.equals(other.nodePosition))
			return false;
		return true;
	}
	
	// 输出节点的所有邻居节点
	public void outputNeighbors() {
		for (int i = 0; i < BUCKETS; i++) {
			if (neighbors[i].size()>0) {
				for (int j = 0; j < neighbors[i].size(); j++) {
					logger.info("neighbor "+i+"-"+j+" : "+neighbors[i].get(j).getNodePosition());
				}
			}
		}
	}

	// 获得节点的邻居个数
	public int getNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++)
			neighborNum += neighbors[i].size();
		return neighborNum;
	}
	
	public double getFailNum() {
		return failNum;
	}
}
