package org.sunspotUI.graphicLibraries;

import org.sunspotUI.graphicLibraries.DraggableLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.LinkedList;

/**
 * The class represents pair of components with a connecting line.
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @author Stanislav Lapitsky
 * @version 1.0
 */
public class JConnector extends JPanel {
    public static final int CONNECT_LINE_TYPE_SIMPLE = 0;
    public static final int CONNECT_LINE_TYPE_RECTANGULAR = 1;
    private final int OFFSET_LINE_MESSAGE = 40;
    
    protected JComponent source;
    protected JComponent dest;
    protected ConnectLine line;
    //protected ConnectLine lifeLine;
    
    protected int lineArrow = ConnectLine.LINE_ARROW_NONE;
    protected int lineType = CONNECT_LINE_TYPE_RECTANGULAR;
    protected Color lineColor;
    
    protected String agent_name;
    protected String text = "";
    protected int lengthLifeLine;
    protected int offset;
    private JConnector agentSource;
    private JConnector agentDest;
    protected int posLifeLine;
    private int num_messages;
    private boolean loop = false;
    private boolean remove = false;
    private boolean isconnection = false;
    
    /**
     * Constructs default connector.
     * @param source JComponent
     * @param dest JComponent
     */
    public JConnector(JComponent source, JComponent dest) {
        this(source, dest, "", ConnectLine.LINE_ARROW_NONE, Color.BLACK);
    }

    /**
     * Constructs a connector with specified arrow and color.
     * @param source JComponent
     * @param dest JComponent
     * @param lineArrow int
     * @param lineColor Color
     */
    public JConnector(JComponent source, JComponent dest, String message, int lineArrow, Color lineColor) {
        //this(source, dest, message, lineArrow,CONNECT_LINE_TYPE_RECTANGULAR, lineColor);
    }

    /**
     * Constructs a connector with specified arrow, line type and color.
     * @param source JComponent
     * @param dest JComponent
     * @param lineArrow int
     * @param lineType int
     * @param lineColor Color
     */
    public JConnector(JConnector source, JConnector dest, LinkedList<JConnector> agents, String message, Color lineColor) {
        this.agentSource = source;
        this.agentDest = dest;
        this.text = message;
        //lengthLifeLine = source.getLengthLifeLine();
        //this.source = agentSource.generateMessageLine();
        //this.dest = agentDest.generateMessageLine();
        int len = this.maxAgentsLength(agents);
        if(source.equals(dest)){
            loop = true;
            this.generateLoopMessageLine(len);
        }else{
            this.generateMessageLine(len);
        }
        
        this.lineArrow = ConnectLine.LINE_START_HORIZONTAL;
        this.lineType = JConnector.CONNECT_LINE_TYPE_RECTANGULAR;
        this.lineColor = lineColor;
        isconnection = true;
    }
    
    /*public JConnector(String agent, String message, int offset, Color lineColor) {
        this(agent,message,offset,lineColor); //10 is the default lenght for the lifetime line.
    }*/
    
    public JConnector(String agent, String message, int offset, Color lineColor) {
        this.agent_name = agent;
        this.source = this.generateAgentBox(agent,offset);
        this.lengthLifeLine = source.getHeight()+source.getY();
        //System.out.println("Mid: "+source.getHeight()+" Length: "+lengthLifeLine);
        this.dest = this.generateLifeLine(lengthLifeLine);
        
        //this.posLifeLine = source.getX();
        this.offset = offset;
        this.text = message;
        this.num_messages = 0;
        
        this.lineArrow = ConnectLine.LINE_START_HORIZONTAL;
        this.lineType = JConnector.CONNECT_LINE_TYPE_RECTANGULAR;
        this.lineColor = lineColor;
    } 
    
    /**
     * Overrides parent's paint(). It resets clip to draw connecting line
     * between components and set the clip back.
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        
        /*g.setColor(Color.WHITE);
        g.fillRect( 0, 0, super.getWidth(), super.getHeight());
        //super.paint(g);
        super.setBackground(Color.white);*/
        if(!remove){
            Graphics2D g2d = (Graphics2D) g;
            calculateLine();
            String s = getMessage();
            if (line != null) {
                Shape oldClip = g2d.getClip();
                g2d.setClip(getLineBounds());
                g2d.setColor(lineColor);
                if(!loop){
                    line.paint(g2d);
                }else{               
                    if(line.getP1().x == line.getP2().x){
                        Point p1 = line.getP1();
                        Point p2 = line.getP2();                    

                        QuadCurve2D q = new QuadCurve2D.Float();
                        // draw QuadCurve2D.Float with set coordinates
                        q.setCurve(line.getP1().x, line.getP1().y, line.getP1().x+50 , (line.getP1().y+line.getP2().y)/2, line.getP2().x, line.getP2().y);
                        g2d.setClip(q);
                        g2d.draw(q);

                        g2d.setColor(Color.red);
                        line.paint(g2d);
                        g2d.setColor(Color.blue);

                        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
                        int diameter = (int) Math.round(p1.distance(p2));

                        g2d.translate(p2.x, p2.y);
                        g2d.rotate(angle);
                        g2d.drawArc(0, -diameter/2, diameter, diameter, 0, 180);
                        g2d.fill(new Polygon(new int[] {0,10,-10}, new int[] {0,-10,-10}, 3));
                    }
                        g2d.setColor(Color.red);
                        line.paint(g2d);      
                        g2d.setColor(lineColor);
                }
                    g2d.setClip(oldClip);
                    g.drawString(s, Math.min(source.getX(),dest.getX())+5, line.getP1().y-5);
            }
        }
    }

    protected void calculateLine() {
        Rectangle rSource = source.getBounds();
        Rectangle rDest = dest.getBounds();
        if (rSource.intersects(rDest)) {
            line = null;
            return;
        }

        boolean xIntersect = (rSource.x <= rDest.x && rSource.x + rSource.width >= rDest.x)
            || (rDest.x <= rSource.x && rDest.x + rDest.width >= rSource.x);
        boolean yIntersect = rSource.y <= rDest.y && rSource.y + rSource.height >= rDest.y
            || (rDest.y <= rSource.y && rDest.y + rDest.height >= rSource.y);

        if (xIntersect) {
            int y1;
            int y2;
            int x1 = rSource.x + rSource.width / 2;
            int x2 = rDest.x + rDest.width / 2;
            if (rSource.y + rSource.height <= rDest.y) {
                //source higher
                y1 = rSource.y + rSource.height;
                y2 = rDest.y;
            }
            else {
                y1 = rSource.y;
                y2 = rDest.y + rDest.height;
            }
            line = new ConnectLine(new Point(x1, y1), new Point(x2, y2), ConnectLine.LINE_TYPE_RECT_2BREAK, ConnectLine.LINE_START_VERTICAL, lineArrow);
            if (lineType == CONNECT_LINE_TYPE_SIMPLE) {
                line.setLineType(ConnectLine.LINE_TYPE_SIMPLE);
            }
        }
        else if (yIntersect) {
            int y1 = rSource.y + rSource.height / 2;
            ;
            int y2 = rDest.y + rDest.height / 2;
            ;
            int x1;
            int x2;
            if (rSource.x + rSource.width <= rDest.x) {
                x1 = rSource.x + rSource.width;
                x2 = rDest.x;
            }
            else {
                x1 = rSource.x;
                x2 = rDest.x + rDest.width;
            }
            line = new ConnectLine(new Point(x1, y1), new Point(x2, y2), ConnectLine.LINE_TYPE_RECT_2BREAK, ConnectLine.LINE_START_HORIZONTAL, lineArrow);
            if (lineType == CONNECT_LINE_TYPE_SIMPLE) {
                line.setLineType(ConnectLine.LINE_TYPE_SIMPLE);
            }
        }
        else {
            int y1;
            int y2;
            int x1;
            int x2;
            if (rSource.y + rSource.height <= rDest.y) {
                //source higher
                y1 = rSource.y + rSource.height / 2;
                y2 = rDest.y;
                if (rSource.x + rSource.width <= rDest.x) {
                    x1 = rSource.x + rSource.width;
                }
                else {
                    x1 = rSource.x;
                }
                x2 = rDest.x + rDest.width / 2;
            }
            else {
                y1 = rSource.y + rSource.height / 2;
                y2 = rDest.y + rDest.height;
                if (rSource.x + rSource.width <= rDest.x) {
                    x1 = rSource.x + rSource.width;
                }
                else {
                    x1 = rSource.x;
                }
                x2 = rDest.x + rDest.width / 2;
            }
            line = new ConnectLine(new Point(x1, y1), new Point(x2, y2), ConnectLine.LINE_TYPE_RECT_1BREAK, ConnectLine.LINE_START_HORIZONTAL, lineArrow);
            if (lineType == CONNECT_LINE_TYPE_SIMPLE) {
                line.setLineType(ConnectLine.LINE_TYPE_SIMPLE);
            }
        }
    }

    protected Rectangle getLineBounds() {
        int add = 10;
        int maxX = Math.max(line.getP1().x, line.getP2().x);
        int minX = Math.min(line.getP1().x, line.getP2().x);
        int maxY = Math.max(line.getP1().y, line.getP2().y);
        int minY = Math.min(line.getP1().y, line.getP2().y);

        Rectangle res = new Rectangle(minX - add, minY - add, maxX - minX + 2 * add, maxY - minY + 2 * add);
        return res;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color c) {
        lineColor = c;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int type) {
        lineType = type;
    }

    public int getLineArrow() {
        return lineArrow;
    }

    public void setLineArrow(int arrow) {
        lineArrow = arrow;
    }

    public void getMidArrowPoint(Graphics g) {
        //repaint();
        this.calculateLine();
        Point2D.Float p = new Point2D.Float(line.getP1().x, line.getP2().y-20);
        g.drawString("Hola", (int) p.x, (int) p.y);
        //return p;
    }

    public void setMessage(String s){
        text = s;
    }
    
    public String getMessage() {
        return text;
    }

    public int getMidPointX() {
        Rectangle rect = source.getBounds();
        return (int) (rect.getX()+rect.getWidth()/2);
    }
    
    public int getMidPointY() {
        Rectangle rect = source.getBounds();
        return (int) (rect.getY()+rect.getHeight())/2;
    }

    private JComponent generateAgentBox(String agent, int offset) {
        JLabel b1 = (JLabel) new DraggableLabel(agent+"  ");
        b1.setHorizontalAlignment(SwingConstants.CENTER);
        b1.setBounds(0+offset, 10, 80, 40);
        return b1;
    }

    private JComponent generateLifeLine(int lengthLifeLine) {
        JLabel aux = new JLabel();
        aux.setBounds(getMidPointX(), lengthLifeLine, 1, 1);
        return aux;
    }
    
    /*public JComponent generateMessageLine(){
        JLabel b1 = new DraggableLabel("");
        this.num_messages++;
        int verticalPoint = this.getMidPointY()+OFFSET_LINE_MESSAGE*num_messages;
        System.out.println("PuntVertical: "+verticalPoint);
        if(verticalPoint>lengthLifeLine){
            this.lengthLifeLine = verticalPoint;
            //this.source = this.generateLifeLine(lengthLifeLine);
            this.dest = this.generateLifeLine(lengthLifeLine);
            this.repaint();
            System.out.println("Repintado");
        }
        b1.setBounds(getMidPointX(), verticalPoint, 1, 1);
        return b1;
    }*/
    
    public void generateMessageLine(int len){
        JLabel b1 = new DraggableLabel("");
        JLabel b2 = new DraggableLabel("");

        //int maxLength = Math.max(agentSource.lengthLifeLine, agentDest.lengthLifeLine);
        int messages_source = agentSource.num_messages;
        int messages_dest = agentDest.num_messages;
        
        messages_source++;
        messages_dest++;
        
        int verticalPointSource = agentSource.lengthLifeLine+OFFSET_LINE_MESSAGE;
        int verticalPointDest = agentDest.lengthLifeLine+OFFSET_LINE_MESSAGE;
        
        int maxLength = Math.max(verticalPointSource, verticalPointDest);
        maxLength = Math.max(maxLength, len+OFFSET_LINE_MESSAGE);
        verticalPointSource = maxLength;
        verticalPointDest = maxLength;
        
        agentSource.lengthLifeLine = maxLength;
        agentDest.lengthLifeLine = maxLength;
        agentSource.dest = agentSource.generateLifeLine(maxLength);
        agentDest.dest = agentDest.generateLifeLine(maxLength);
        
        agentSource.repaint();
        agentDest.repaint();
        
        b1.setBounds(agentSource.getMidPointX(), verticalPointSource, 1, 1);
        b2.setBounds(agentDest.getMidPointX(), verticalPointDest, 1, 1);
        
        this.source = b1;
        this.dest = b2;
        
        agentSource.num_messages = messages_source;
        agentDest.num_messages = messages_dest;
    }

    public JComponent getAgentBox(){
        return source;
    }
    
    public int getPosLifeLine(){
        return dest.getY();
    }
    
    public int getLengthLifeLine(){
        return lengthLifeLine;
    }

    private void resizeLifeLine(int verticalPoint) {
        //lengthLifeLine = this.lengthLifeLine + verticalPoint;
        lengthLifeLine = lengthLifeLine + verticalPoint;
        repaint();
    }

    /*private void resizeLifeLine(JConnector aThis, int verticalPoint) {
        this(aThis.source, aThis.text, aThis.lengthLifeLine, aThis.offset, aThis.lineColor);
    }*/

    /**
     * @return the agentSource
     */
    public JConnector getAgentSource() {
        return agentSource;
    }

    /**
     * @return the agentDest
     */
    public JConnector getAgentDest() {
        return agentDest;
    }
    
    public static int maxAgentsLength(LinkedList<JConnector> agents){
        int i=0, max=0, agLen=0;
        int len = agents.size();
        
        for(i=0;i<len;i++){
            agLen = agents.get(i).lengthLifeLine;
            if(agLen > max){
                max = agLen;
            }
        }
        return max;
    }

    private void generateLoopMessageLine(int len) {
        JLabel b1 = new DraggableLabel("");
        JLabel b2 = new DraggableLabel("");

        //int maxLength = Math.max(agentSource.lengthLifeLine, agentDest.lengthLifeLine);
        int messages_source = agentSource.num_messages;
        //int messages_dest = agentDest.num_messages;
        
        messages_source++;
        //messages_dest++;
        
        int verticalPoint = agentSource.lengthLifeLine+OFFSET_LINE_MESSAGE;
        //int verticalPointDest = agentDest.lengthLifeLine+OFFSET_LINE_MESSAGE;
        
        //int maxLength = Math.max(verticalPointSource, verticalPointDest);
        //maxLength = Math.max(maxLength, len+OFFSET_LINE_MESSAGE);
        //verticalPointSource = maxLength;
        //verticalPointDest = maxLength;
        
        agentSource.lengthLifeLine = verticalPoint;
        //agentDest.lengthLifeLine = maxLength;
        agentSource.dest = agentSource.generateLifeLine(verticalPoint);
        //agentDest.dest = agentDest.generateLifeLine(maxLength);
        
        //agentSource.repaint();
        //agentDest.repaint();
        
        b1.setBounds(agentSource.getMidPointX(), verticalPoint, 1, 1);
        b2.setBounds(agentSource.getMidPointX(), verticalPoint+OFFSET_LINE_MESSAGE, 1, 1);
        
        this.source = b1;
        this.dest = b2;
        
        agentSource.num_messages = messages_source;
    }
    
    public void remove(){
        remove = true;
        repaint();
    }
    
    public JComponent getDest(){
        return dest;
    }
    
    public String getAgentName(){
        return agent_name;
    }
    
    public String getSourceConnectionName(){
        return agentSource.getAgentName();
    }
    
    public String getDestConnectionName(){
        return agentDest.getAgentName();
    }
    
    public boolean isConnection(){
        return isconnection;
    }
}
