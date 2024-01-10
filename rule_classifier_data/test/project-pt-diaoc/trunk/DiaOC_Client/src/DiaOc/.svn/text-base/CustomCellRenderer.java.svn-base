/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diaoc;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author Nixforest
 */
public class CustomCellRenderer implements ListCellRenderer{
    public Component getListCellRendererComponent(JList list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        Component c = (Component)value;
        c.setForeground(Color.red);
        if (isSelected) {
            c.setBackground(new Color(200, 200, 200));            
        }else{
            c.setBackground(Color.white);            
        }
        return c;
    }
    
}
