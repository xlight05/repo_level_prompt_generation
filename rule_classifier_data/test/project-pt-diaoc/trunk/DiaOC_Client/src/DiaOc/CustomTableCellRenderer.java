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
public class CustomTableCellRenderer extends DefaultTableCellRenderer{
    private ImageIcon icon = null;
    public CustomTableCellRenderer(ImageIcon icon){
        this.icon = icon;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, 
            int row, int column){
        setText((String)value);
        setIcon(icon);
        return this;
    }
}