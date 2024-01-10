package org.bm.p2p.navigablep2p.test;

import static org.junit.Assert.*;

import org.bm.p2p.navigablep2p.Node;
import org.junit.Test;

public class TestNode {

	@Test
	public void testSetNodePositionRandom() {
		Node node = new Node();
		node.setNodePositionRandom();
		System.out.println(node.getNodePosition().toString());
		System.out.println(node.getNodePosition().length());
		System.out.println(node.getNodePosition().size());
		System.out.println(node.getNodePosition().nextSetBit(0));
		for(int i =0;i<node.getNodePosition().size();i++) {
			if (node.getNodePosition().get(i) == true)
				System.out.print("1");
			else
				System.out.print("0");
					
		}
		
		//fail("Not yet implemented");
	}

	@Test
	public void testGetDistanceFrom() {
		Node node1 = new Node();
		node1.setNodePositionRandom();
		System.out.println(node1.getNodePosition().toString());
		System.out.println(node1.getNodePosition().length());
		System.out.println(node1.getNodePosition().size());
		System.out.println(node1.getNodePosition().nextSetBit(0));
		for(int i =0;i<node1.getNodePosition().size();i++) {
			if (node1.getNodePosition().get(i) == true)
				System.out.print("1");
			else
				System.out.print("0");
					
		}

		Node node2 = new Node();
		node2.setNodePositionRandom();
		System.out.println(node2.getNodePosition().toString());
		System.out.println(node2.getNodePosition().length());
		System.out.println(node2.getNodePosition().size());
		System.out.println(node2.getNodePosition().nextSetBit(0));
		for(int i =0;i<node2.getNodePosition().size();i++) {
			if (node2.getNodePosition().get(i) == true)
				System.out.print("1");
			else
				System.out.print("0");
					
		}
		
		int distance = node1.getDistanceFrom(node2);
		System.out.println(distance);
		
	}

}
