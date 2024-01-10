package org.bm.p2p.navigablep2p;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 
 * @author duanshuiliu
 * �����еĽڵ�
 *
 */
public class Node {
	static final int BUCKETS = 16; // Ͱ(��ֵ��32�ı�����Ҳ������16����������8)
	static final int K = 3; // ÿͰ��Ԫ�ظ���

	static Logger logger = Logger.getLogger(Node.class.getName());
	
	private int failNum = 0; // �����ھӽڵ�ʱ�������ظ��ڵ�ĸ���
	
	private BitSet nodePosition; //�ڵ�λ��

	private ArrayList<Node> outNeighbors[]; // ���ھӽڵ��б�[ĳԪ�������ֵ���Ǿ��Ǹýڵ�ͱ��ڵ�ľ���]
	private ArrayList<Node> inNeighbors[]; // ���ھӽڵ��б�[ĳԪ�������ֵ���Ǿ��Ǹýڵ�ͱ��ڵ�ľ���]
	private ArrayList<Node> incidentalNeighbors[]; // ˳���ھӽڵ��б�[ĳԪ�������ֵ���Ǿ��Ǹýڵ�ͱ��ڵ�ľ���]
	
	// ������Ϊdistance�Ľڵ�neighbor������ھӽڵ��б�
	public void setOutNeighbor(Node neighbor, int distance) {
		int i;
		// ������벻�ںϷ���Χ�ڣ����˳�
		if (distance < 0 || distance >= BUCKETS) return;
		
		// ���distanceͰû�����Ѹýڵ����Ͱ
		if (outNeighbors[distance].size() < K) {
			for (i = 0; i < outNeighbors[distance].size(); i++) {
				//����ڵ��Ѿ����ڣ��򷵻�
				if (outNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			outNeighbors[distance].add(neighbor);
		}
		else {
			//���Ͱ���������Ͱ�����ѡ��һ����̭������̭�㷨�������������ʹ�ã��ο� Kadmelia��
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				outNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// ��þ���Ϊdistance�ĳ��ھ�(���)
	public Node getOutNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (outNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(outNeighbors[distance].size());
			return outNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// ��þ���Ŀ��ڵ�targetNode����ĳ��ھ�(��Ӧ�����Ͱ�����ھ�)
	public Node getOutNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// ���ھӽڵ��б�ѡ��
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

	// ��þ���Ϊdistance�����г��ھ�
	public ArrayList<Node> getOutNeighbors(int distance) {
		return outNeighbors[distance];
	}
	
	// ��ó��ھ��е���С����
	public int getOutMinDistance() {
		int i;
		for (i=0;i<BUCKETS;i++) {
			if (getOutNeighbor(i)!=null) break;
		}
		return i;
	}
	
	// ��ýڵ�ĳ��ھӸ���
	public int getOutNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++) {
			neighborNum += outNeighbors[i].size();
		}
		return neighborNum;
	}
	
	// ɾ��һ�����ھ�
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
	
	// ������Ϊdistance�Ľڵ�neighbor�������ھӽڵ��б�
	public void setInNeighbor(Node neighbor, int distance) {
		int i;
		// ������벻�ںϷ���Χ�ڣ����˳�
		if (distance < 0 || distance >= BUCKETS) return;
		
		// ���distanceͰû�����Ѹýڵ����Ͱ
		if (inNeighbors[distance].size() < K) {
			for (i = 0; i < inNeighbors[distance].size(); i++) {
				//����ڵ��Ѿ����ڣ��򷵻�
				if (inNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			inNeighbors[distance].add(neighbor);
		}
		else {
			//���Ͱ���������Ͱ�����ѡ��һ����̭������̭�㷨�������������ʹ�ã��ο� Kadmelia��
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				inNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// ��þ���Ϊdistance�����ھ�(���)
	public Node getInNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (inNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(inNeighbors[distance].size());
			return inNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// ��þ���Ŀ��ڵ�targetNode��������ھ�(��Ӧ�����Ͱ�����ھ�)
	public Node getInNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// ���ھӽڵ��б�ѡ��
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

	// ��þ���Ϊdistance���������ھ�
	public ArrayList<Node> getInNeighbors(int distance) {
		return inNeighbors[distance];
	}
	
	// ������ھ��е���С����
	public int getInMinDistance() {
		int i;
		for (i=0;i<BUCKETS;i++) {
			if (getInNeighbor(i)!=null) break;
		}
		return i;
	}
	
	// ��ýڵ�����ھӸ���
	public int getInNeighborNum() {
		int neighborNum = 0;
		for (int i = 0; i < BUCKETS; i++) {
			neighborNum += inNeighbors[i].size();
		}
		return neighborNum;
	}
	
	// ɾ��һ�����ھ�
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
	
	// ������Ϊdistance�Ľڵ�neighbor����˳���ھӽڵ��б�
	public void setIncidentalNeighbor(Node neighbor, int distance) {
		int i;
		// ������벻�ںϷ���Χ�ڣ����˳�
		if (distance < 0 || distance >= BUCKETS) return;
		
		// ���distanceͰû�����Ѹýڵ����Ͱ
		if (incidentalNeighbors[distance].size() < K) {
			for (i = 0; i < incidentalNeighbors[distance].size(); i++) {
				//����ڵ��Ѿ����ڣ��򷵻�
				if (incidentalNeighbors[distance].get(i)==neighbor){
					failNum ++;
					return;
				}
			}
			incidentalNeighbors[distance].add(neighbor);
		}
		else {
			//���Ͱ���������Ͱ�����ѡ��һ����̭������̭�㷨�������������ʹ�ã��ο� Kadmelia��
			Random random = new Random();
			int chosedIdx = random.nextInt(K+1);
			if (chosedIdx < K)
				incidentalNeighbors[distance].set(chosedIdx, neighbor);
			
		}
	}
	// ��þ���Ϊdistance��˳���ھ�(���)
	public Node getIncidentalNeighbor(int distance) {
		if (distance<0||distance>=BUCKETS) return null;
		if (incidentalNeighbors[distance].size() > 0) {
			Random random = new Random();
			int chosenIdx = random.nextInt(incidentalNeighbors[distance].size());
			return incidentalNeighbors[distance].get(chosenIdx);
		}
		return null;
	}

	// ��þ���Ŀ��ڵ�targetNode�����˳���ھ�(��Ӧ�����Ͱ�����ھ�)
	public Node getIncidentalNeighbor(Node targetNode) {
		Node neighbor = null;
		int distance = this.getDistanceFrom(targetNode);
		if (distance<0||distance>=BUCKETS) return null;

		int minDistance = distance;
		// ���ھӽڵ��б�ѡ��
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

	// ��þ���Ϊdistance������˳���ھ�
	public ArrayList<Node> getIncidentalNeighbors(int distance) {
		return incidentalNeighbors[distance];
	}
	

	// ��þ���Ŀ��ڵ�targetNode������ھ�(��Ӧ�����Ͱ�����ھ�)
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
		
	//��ʼ������ڵ㣬λ��Ϊ���ֵ
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

	//��ʼ������ڵ㣬λ�úͽڵ�node�ľ������distance�����ѡȡ
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
	// ��ʼ������ڵ㣬λ��ȷ��
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

	// ������ɽڵ��λ��
	public void setNodePositionRandom() {
		Random random = new Random();
		int randomInt;
		boolean randomBool;
		int i,j;
		assert((BUCKETS%32==0)||(BUCKETS<32)); //�����ѭ���Դ�Ϊǰ��: BUCKETS��32�ı������ߵ���16���ߵ���8
		for (i =0 ; i < ((BUCKETS<32)?1:BUCKETS/32); i++) { // ��������BUCKETS/32���������
			randomInt = random.nextInt();
			//randomInt = (int) (random.nextGaussian()*Math.pow(2, 32));
			for(j =0 ; j < ((BUCKETS<32)?BUCKETS:32); j++) {
				if ((randomInt & 1) == 1) randomBool = true;
				else randomBool = false;
				nodePosition.set(i*32+j, randomBool);
				randomInt >>= 1; //����һλ
			}
		}
/*		for (i=0;i<BUCKETS;i++) {
			nodePosition.set(i, random.nextBoolean());
		}
*/	}
	
	//������ɺͽڵ�node�ľ���Ϊdistance�Ľڵ�λ��
	public void setNodePositionRandomByDistance(Node node, int distance) {
		Random random = new Random();
		int randomInt;
		boolean randomBool;
		int i,j;
		assert((BUCKETS%32==0)||(BUCKETS==16)||(BUCKETS==8)); //�����ѭ���Դ�Ϊǰ��: BUCKETS��32�ı������ߵ���16���ߵ���8
		assert(distance<BUCKETS&&distance>=0); // ����λ��[0, BUCKETS-1]
		for (i =0 ; i < ((BUCKETS==16)||(BUCKETS==8)?1:BUCKETS/32); i++) { // ��������BUCKETS/32���������
			randomInt = random.nextInt();
			//randomInt = (int) (random.nextGaussian()*Math.pow(2, 32));
			for(j =0 ; j < ((BUCKETS==8)?8:(BUCKETS==16?16:32)); j++) {
				if ((randomInt & 1) == 1) randomBool = true;
				else randomBool = false;
				nodePosition.set(i*32+j, randomBool);
				randomInt >>= 1; //����һλ
			}
		}
		// ����Ӧλ��λ
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
	
	// ����ͽڵ�node֮��ľ���
	public int getDistanceFrom(Node node) {
		int ret = 0;
		// clone���ڵ㣨��Ϊ�ڼ������Ĺ����У�����xor���������ڵ�ľ���Ҫ�����ı䣩��
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
	
	// ����ڵ�������ھӽڵ�
	public void outputNeighbors() {
		for (int i = 0; i < BUCKETS; i++) {
			if (outNeighbors[i].size()>0) {
				for (int j = 0; j < outNeighbors[i].size(); j++) {
					logger.info("neighbor "+i+"-"+j+" : "+outNeighbors[i].get(j).getNodePosition());
				}
			}
		}
	}

	// ��ýڵ�ĳ�/���ھӸ���
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
