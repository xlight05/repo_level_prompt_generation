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
	
	private int failNum = 0; // 加入邻居节点时，碰到重复节点的个数
	
	private BitSet nodePosition; //节点位置

	private ArrayList<Node> outNeighbors[]; // 出邻居节点列表，[某元组的索引值就是就是该节点和本节点的距离]
	private ArrayList<Node> inNeighbors[]; // 入邻居节点列表，[某元组的索引值就是就是该节点和本节点的距离]
	private ArrayList<Node> incidentalNeighbors[]; // 顺带邻居节点列表，[某元组的索引值就是就是该节点和本节点的距离]
	
	// 将距离为distance的节点neighbor加入出邻居节点列表
	public void setOutNeighbor(Node neighbor, int distance) {
		int i;
		// 如果距离不在合法范围内，则退出
		if (distance < 0 || distance >= BUCKETS) return;
		
		// 如果distance桶没满，把该节点加入桶
		if (outNeighbors[distance].size() < K) {
			for (i = 0; i < outNeighbors[distance].size(); i++) {
				//如果节点已经存在，则返回
				if (outNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			outNeighbors[distance].add(neighbor);
		}
		else {
			//如果桶已满，则从桶里随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				outNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// 获得距离为distance的出邻居(随机)
	public Node getOutNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (outNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(outNeighbors[distance].size());
			return outNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// 获得距离目标节点targetNode最近的出邻居(对应距离的桶中有邻居)
	public Node getOutNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// 从邻居节点列表选择
		if (outNeighbors[distance].size() > 0) {
			for (int i = 0; i < outNeighbors[distance].size(); i++) {
				if (outNeighbors[distance].get(i).getDistanceFrom(targetNode) < minDistance) {
					minDistance = outNeighbors[distance].get(i).getDistanceFrom(targetNode);
					neighbor = outNeighbors[distance].get(i);
				}
			}
		}
		return neighbor;
	}

	// 获得距离为distance的所有出邻居
	public ArrayList<Node> getOutNeighbors(int distance) {
		return outNeighbors[distance];
	}
	
	// 获得出邻居中的最小距离
	public int getOutMinDistance() {
		int i;
		for (i=0;i<BUCKETS;i++) {
			if (getOutNeighbor(i)!=null) break;
		}
		return i;
	}
	
	// 获得节点的出邻居个数
	public int getOutNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++) {
			neighborNum += outNeighbors[i].size();
		}
		return neighborNum;
	}
	
	// 删除一个出邻居
	public int delOutNeighbor(Node neighbor) {
		int ret = -1;
		int distance = this.getDistanceFrom(neighbor);
		if (this.getOutNeighbors(distance).size()>0) {
			for (int i=0;i<this.getOutNeighbors(distance).size();i++) {
				if (this.getOutNeighbors(distance).get(i)==neighbor) {
					this.getOutNeighbors(distance).remove(i);
					ret = 0;
					break;
				}
			}
		}
		return ret;
	}
	
	// 将距离为distance的节点neighbor加入入邻居节点列表
	public void setInNeighbor(Node neighbor, int distance) {
		int i;
		// 如果距离不在合法范围内，则退出
		if (distance < 0 || distance >= BUCKETS) return;
		
		// 如果distance桶没满，把该节点加入桶
		if (inNeighbors[distance].size() < K) {
			for (i = 0; i < inNeighbors[distance].size(); i++) {
				//如果节点已经存在，则返回
				if (inNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			inNeighbors[distance].add(neighbor);
		}
		else {
			//如果桶已满，则从桶里随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				inNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// 获得距离为distance的入邻居(随机)
	public Node getInNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (inNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(inNeighbors[distance].size());
			return inNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// 获得距离目标节点targetNode最近的入邻居(对应距离的桶中有邻居)
	public Node getInNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// 从邻居节点列表选择
		if (inNeighbors[distance].size() > 0) {
			for (int i = 0; i < inNeighbors[distance].size(); i++) {
				if (inNeighbors[distance].get(i).getDistanceFrom(targetNode) < minDistance) {
					minDistance = inNeighbors[distance].get(i).getDistanceFrom(targetNode);
					neighbor = inNeighbors[distance].get(i);
				}
			}
		}
		return neighbor;
	}

	// 获得距离为distance的所有入邻居
	public ArrayList<Node> getInNeighbors(int distance) {
		return inNeighbors[distance];
	}
	
	// 获得入邻居中的最小距离
	public int getInMinDistance() {
		int i;
		for (i=0;i<BUCKETS;i++) {
			if (getInNeighbor(i)!=null) break;
		}
		return i;
	}
	
	// 获得节点的入邻居个数
	public int getInNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++) {
			neighborNum += inNeighbors[i].size();
		}
		return neighborNum;
	}
	
	// 删除一个入邻居
	public int delInNeighbor(Node neighbor) {
		int ret = -1;
		int distance = this.getDistanceFrom(neighbor);
		if (this.getInNeighbors(distance).size()>0) {
			for (int i=0;i<this.getInNeighbors(distance).size();i++) {
				if (this.getInNeighbors(distance).get(i)==neighbor) {
					this.getInNeighbors(distance).remove(i);
					ret = 0;
					break;
				}
			}
		}
		return ret;
	}
	
	// 将距离为distance的节点neighbor加入顺带邻居节点列表
	public void setIncidentalNeighbor(Node neighbor, int distance) {
		int i;
		// 如果距离不在合法范围内，则退出
		if (distance < 0 || distance >= BUCKETS) return;
		
		// 如果distance桶没满，把该节点加入桶
		if (incidentalNeighbors[distance].size() < K) {
			for (i = 0; i < incidentalNeighbors[distance].size(); i++) {
				//如果节点已经存在，则返回
				if (incidentalNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			incidentalNeighbors[distance].add(neighbor);
		}
		else {
			//如果桶已满，则从桶里随机选择一个淘汰掉（淘汰算法可以是最近最少使用，参考 Kadmelia）
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				incidentalNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// 获得距离为distance的顺带邻居(随机)
	public Node getIncidentalNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (incidentalNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(incidentalNeighbors[distance].size());
			return incidentalNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// 获得距离目标节点targetNode最近的顺带邻居(对应距离的桶中有邻居)
	public Node getIncidentalNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// 从邻居节点列表选择
		if (incidentalNeighbors[distance].size() > 0) {
			for (int i = 0; i < incidentalNeighbors[distance].size(); i++) {
				if (incidentalNeighbors[distance].get(i).getDistanceFrom(targetNode) < minDistance) {
					minDistance = incidentalNeighbors[distance].get(i).getDistanceFrom(targetNode);
					neighbor = incidentalNeighbors[distance].get(i);
				}
			}
		}
		return neighbor;
	}

	// 获得距离为distance的所有顺带邻居
	public ArrayList<Node> getIncidentalNeighbors(int distance) {
		return incidentalNeighbors[distance];
	}
	

	// 获得距离目标节点targetNode最近的邻居(对应距离的桶中有邻居)
	public Node getNeighbor(Node targetNode) {
		
		int inDistance,outDistance,incidentalDistance,minDistance;
		
		Node inNeighbor = getInNeighbor(targetNode);
		if (inNeighbor==null) inDistance=BUCKETS;
		else inDistance = inNeighbor.getDistanceFrom(targetNode);
		
		Node outNeighbor = getOutNeighbor(targetNode);
		if (outNeighbor==null) outDistance=BUCKETS;
		else outDistance = outNeighbor.getDistanceFrom(targetNode);
		
		Node incidentalNeighbor = getIncidentalNeighbor(targetNode);
		if (incidentalNeighbor==null) incidentalDistance=BUCKETS;
		else incidentalDistance = incidentalNeighbor.getDistanceFrom(targetNode);
		
		minDistance = inDistance;
		Node minNeighbor = inNeighbor;
		if (outDistance<minDistance) {
			minDistance = outDistance;
			minNeighbor = outNeighbor;
		}
		if (incidentalDistance<minDistance) {
			minDistance = incidentalDistance;
			minNeighbor = incidentalNeighbor;
		}

		return minNeighbor;
	}	
		
	//初始化网络节点，位置为随机值
	@SuppressWarnings("unchecked")
	public Node() {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandom();
		this.outNeighbors = new ArrayList[BUCKETS];
		this.inNeighbors = new ArrayList[BUCKETS];
		this.incidentalNeighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++) {
			outNeighbors[i] = new ArrayList<Node>();
			inNeighbors[i] = new ArrayList<Node>();
			incidentalNeighbors[i] = new ArrayList<Node>();
		}
	}

	//初始化网络节点，位置和节点node的距离等于distance，随机选取
	@SuppressWarnings("unchecked")
	public Node(Node node, int distance) {
		super();
		this.nodePosition = new BitSet(BUCKETS);
		this.setNodePositionRandomByDistance(node, distance);
		this.outNeighbors = new ArrayList[BUCKETS];
		this.inNeighbors = new ArrayList[BUCKETS];
		this.incidentalNeighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++) {
			outNeighbors[i] = new ArrayList<Node>();
			inNeighbors[i] = new ArrayList<Node>();
			incidentalNeighbors[i] = new ArrayList<Node>();
		}
	}
	// 初始化网络节点，位置确定
	@SuppressWarnings("unchecked")
	public Node(BitSet nodePosition) {
		super();
		this.nodePosition = nodePosition;
		this.outNeighbors = new ArrayList[BUCKETS];
		this.inNeighbors = new ArrayList[BUCKETS];
		this.incidentalNeighbors = new ArrayList[BUCKETS];
		for (int i = 0; i < BUCKETS; i++) {
			outNeighbors[i] = new ArrayList<Node>();
			inNeighbors[i] = new ArrayList<Node>();
			incidentalNeighbors[i] = new ArrayList<Node>();
		}
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
			if (outNeighbors[i].size()>0) {
				for (int j = 0; j < outNeighbors[i].size(); j++) {
					logger.info("neighbor "+i+"-"+j+" : "+outNeighbors[i].get(j).getNodePosition());
				}
			}
		}
	}

	// 获得节点的出/入邻居个数
	public int getNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++) {
			neighborNum += outNeighbors[i].size();
			neighborNum += inNeighbors[i].size();
		}
		return neighborNum;
	}
	
	public double getFailNum() {
		return failNum;
	}
}
