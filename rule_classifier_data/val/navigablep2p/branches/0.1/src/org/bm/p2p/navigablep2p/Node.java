package org.bm.p2p.navigablep2p;

import java.util.BitSet;
import java.util.Random;

/**
 * 
 * @author duanshuiliu
 * 网络中的节点
 *
 */
public class Node {
	static final int BUCKETS = 6; // 桶(数值是32的倍数，也可以是16，还可以是8)
	static final int K = 1; // 每桶的元素个数

	private BitSet nodePosition; //节点位置

	private Node inNeighbors[][]; // 入邻居列表
	private Node outNeighbors[][]; // 出邻居节点列表，[某元组的索引值就是就是该节点和本节点的距离]
	
	// 将距离为distance的节点neighbor加入入邻居节点列表
	public void setInNeighbor(Node neighbor, int distance) {
		int i;
		// 如果桶没满，把该节点加入桶
		for (i = 0; i < K; i++) {
			if (inNeighbors[distance][i] == null) {
				inNeighbors[distance][i] = neighbor;
				break;
			}
		}
		//如果桶已满，则从(桶里和新节点)中随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
		if (i == K) {
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				inNeighbors[distance][chosedIdx] = neighbor;
		}
	}
	// 获得距离为distance的入邻居
	public Node getInNeighbor(int distance) {
		int i;
		//从桶中随机选取一个邻居
		for (i = 0; i < K; i++) {
			if (inNeighbors[distance][i] == null) {
				break;
			}
		}
		if (i>0) {
			Random random = new Random();
			return inNeighbors[distance][random.nextInt(i)];
		}
		return null;
	}
	
	
	// 将距离为distance的节点neighbor加入出邻居节点列表
	public void setOutNeighbor(Node neighbor, int distance) {
		int i;
		// 如果桶没满，把该节点加入桶
		for (i = 0; i < K; i++) {
			if (outNeighbors[distance][i] == null) {
				outNeighbors[distance][i] = neighbor;
				break;
			}
		}
		//如果桶已满，则从桶里随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
		if (i == K) {
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				outNeighbors[distance][chosedIdx] = neighbor;
		}
	}
	// 获得距离为distance的出邻居
	public Node getOutNeighbor(int distance) {
		int i;
		//从桶中随机选取一个邻居
		for (i = 0; i < K; i++) {
			if (outNeighbors[distance][i] == null) {
				break;
			}
		}
		if (i>0) {
			Random random = new Random();
			return outNeighbors[distance][random.nextInt(i)];
		}
		return null;
	}

	// 将距离为distance的节点neighbor加入出邻居节点列表
	public boolean setOutNeighborByIdx(Node neighbor, int distance, int idx) {
		if (idx >= K ) return false;
		outNeighbors[distance][idx] = neighbor;
		return true;
	}
	// 获得距离为distance的出邻居
	public Node getOutNeighborByIdx(int distance, int idx) {
		if (idx >= K) return null;
		return outNeighbors[distance][idx];
	}

	//初始化网络节点，位置为随机值
	public Node() {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandom();
		this.outNeighbors = new Node[BUCKETS][K];
		this.inNeighbors = new Node[BUCKETS][K];
		System.out.println(nodePosition);
	}

	//初始化网络节点，位置和节点node的距离等于distance，随机选取
	public Node(Node node, int distance) {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandomByDistance(node, distance);
		this.outNeighbors = new Node[BUCKETS][K];
		this.inNeighbors = new Node[BUCKETS][K];
		System.out.println(nodePosition);
	}

	public Node(BitSet nodePosition) {
		super();
		this.nodePosition = nodePosition;
		this.outNeighbors = new Node[BUCKETS][K];
		this.inNeighbors = new Node[BUCKETS][K];
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
	
	
}
