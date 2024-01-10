package org.bm.p2p.navigablep2p;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class printGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();    
	    Shell shell = new Shell(display);    
	    shell.setText("Drawing Example");    
	    
	    Canvas canvas = new Canvas(shell, SWT.NONE);    
	    canvas.setSize(500, 500);    
	    //canvas.setLocation(20, 20);    
	    shell.open();    
	    shell.setSize(600, 600);    
	    shell.setLocation(100, 100);
	    
	    Rectangle clientArea = canvas.getClientArea();
	    
	    GC gc = new GC(canvas);    
	    gc.drawPoint(clientArea.width / 2, clientArea.height / 2);
	    int length = 20;
	    double angel = 0;
	    int startX = clientArea.width / 2;
	    int startY = clientArea.height / 2;
	    gc.drawOval(startX-2, startY-2, 4, 4);
	    int endX = startX + (int) (length * Math.cos(angel*Math.PI/180));
	    int endY = startY - (int) (length * Math.sin(angel*Math.PI/180));
	    //gc.drawLine(startX, startY, endX, endY);
	    System.out.println("StartX="+startX+" StartY="+startY+" endX="+endX+" endY="+endY);
	    
	    gc.setLineStyle(SWT.LINE_DOT); // 设置线型
	    gc.setLineWidth(1); // 设置线宽
	    gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GRAY)); // 设置前景色
	    
	    // 广度优先遍历
	    class TreeNode {
	    	Point point;
	    	int level;
	    	double angel;
	    	public TreeNode(Point point, int level, double angel) {
	    		this.point = point;
	    		this.level = level;
	    		this.angel = angel;
	    	}
	    	
	    }
	    Queue<TreeNode> visitedTreeNodes = new LinkedList<TreeNode> ();
	    Point root = new Point(startX, startY);
	    TreeNode treeNode = new TreeNode(root,1,0);
	    visitedTreeNodes.add(treeNode);
	    int level = 0;
	    while (visitedTreeNodes.size()>0) {
	    	TreeNode visitedTreeNode = visitedTreeNodes.remove();
	    	angel = visitedTreeNode.angel;
	    	level = visitedTreeNode.level;
	    	if (level>6) break;
	    	startX = visitedTreeNode.point.x;
	    	startY = visitedTreeNode.point.y;
	    	endX = startX + (int) ((length*Math.sqrt(level)) * Math.cos((angel+90/Math.pow(2, level-1))*Math.PI/180));
	    	endY = startY - (int) ((length*Math.sqrt(level)) * Math.sin((angel+90/Math.pow(2, level-1))*Math.PI/180));
	    	gc.drawLine(startX, startY, endX, endY);
	    	visitedTreeNodes.add(new TreeNode(new Point(endX,endY),level+1,(angel+90/Math.pow(2, level-1))));
	    	endX = startX + (int) ((length*Math.sqrt(level)) * Math.cos((angel-90/Math.pow(2, level-1))*Math.PI/180));
	    	endY = startY - (int) ((length*Math.sqrt(level)) * Math.sin((angel-90/Math.pow(2, level-1))*Math.PI/180));
	    	gc.drawLine(startX, startY, endX, endY);
	    	visitedTreeNodes.add(new TreeNode(new Point(endX,endY),level+1,(angel-90/Math.pow(2, level-1))));
	    }
	    
	    gc.setLineStyle(SWT.LINE_SOLID); // 设置线型
	    gc.setLineWidth(1); // 设置线宽
	    gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); // 设置前景色

//		Graph graph = new Graph();
//		graph.initializeTheFirstNode(new Node());
//		for (int i =0;i<32;i++) {
//			int messageNum = graph.addNode2(new Node());
//			System.out.println("messages: " + messageNum);
//		}
//		//graph.output();
		Graph graph = new Graph(16);
		graph.addEdgesAccordingToDistance();		
		// 对于网络所有节点
		for (int i=0;i<graph.getNodes().size();i++) {
			Node srcNode = graph.getNodes().get(i);
		    startX = clientArea.width / 2;
		    startY = clientArea.height / 2;
		    angel = 0;
			for (int j = 1; j <= Node.BUCKETS; j++) {
				if (srcNode.getNodePosition().get(Node.BUCKETS-j)==true) {
					startX = startX + (int) ((length*Math.sqrt(j)) * Math.cos((angel+90/Math.pow(2, j-1))*Math.PI/180));
					startY = startY - (int) ((length*Math.sqrt(j)) * Math.sin((angel+90/Math.pow(2, j-1))*Math.PI/180));
					angel = (angel+90/Math.pow(2, j-1));
				}
				else {
					startX = startX + (int) ((length*Math.sqrt(j)) * Math.cos((angel-90/Math.pow(2, j-1))*Math.PI/180));
					startY = startY - (int) ((length*Math.sqrt(j)) * Math.sin((angel-90/Math.pow(2, j-1))*Math.PI/180));
					angel = (angel-90/Math.pow(2, j-1));
					
				}
			}
		    gc.drawOval(startX-2, startY-2, 4, 4);
			for (int j=0;j<Node.BUCKETS;j++) {
				Node destNode = srcNode.getOutNeighbor(j);
				if (destNode!=null) {
				    endX = clientArea.width / 2;
				    endY = clientArea.height / 2;
				    angel = 0;
					for (int k = 1; k <= Node.BUCKETS; k++) {
						if (destNode.getNodePosition().get(Node.BUCKETS-k)==true) {
							endX = endX + (int) ((length*Math.sqrt(k)) * Math.cos((angel+90/Math.pow(2, k-1))*Math.PI/180));
							endY = endY - (int) ((length*Math.sqrt(k)) * Math.sin((angel+90/Math.pow(2, k-1))*Math.PI/180));
							angel = (angel+90/Math.pow(2, k-1));
						}
						else {
							endX = endX + (int) ((length*Math.sqrt(k)) * Math.cos((angel-90/Math.pow(2, k-1))*Math.PI/180));
							endY = endY - (int) ((length*Math.sqrt(k)) * Math.sin((angel-90/Math.pow(2, k-1))*Math.PI/180));
							angel = (angel-90/Math.pow(2, k-1));

						}
					}
					System.out.println("srcNode: " + srcNode.getNodePosition()+" destNode: " + destNode.getNodePosition());
					
				    gc.drawOval(endX-2, endY-2, 4, 4);
				    //if (i==1)
					gc.drawLine(startX, startY, endX, endY);
				}
			}
			
			
		}

		//	    gc.drawArc(5,5,90,45,90,200);
//	    gc.drawRectangle(10, 10, 40, 45);    
//	    gc.drawOval(65, 10, 30, 35);    
//	    gc.drawLine(130, 10, 90, 80);    
//	    gc.drawPolygon(new int[] { 20, 70, 45, 90, 70, 70 });    
//	    gc.drawPolyline(new int[] { 10, 120, 70, 100, 100, 130, 130, 75 });    
	    gc.dispose();    
	   
	    while (!shell.isDisposed()) {    
	      if (!display.readAndDispatch())    
	        display.sleep();    
	    }    
	    display.dispose();   
	    canvas.dispose();
	    //gc.dispose();

	}

}
