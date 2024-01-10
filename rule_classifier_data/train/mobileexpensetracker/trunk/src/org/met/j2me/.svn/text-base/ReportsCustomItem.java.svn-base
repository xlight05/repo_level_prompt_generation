/*
 * Copyright 2009 Ragupathi Palaniappan Licensed under the Apache License, Version 2.0 (the
 * "License");
 * 
 * You may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.met.j2me;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.met.j2me.util.ExpensesBean;
import org.met.j2me.util.StorageHelper;

import com.sun.midp.midlet.Selector;

/**
 * Custom class for providing the construction of reports and rendering the same.
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ReportsCustomItem extends CustomItem
{
    private Font headerFont =
        Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD | Font.STYLE_UNDERLINED,
            Font.SIZE_MEDIUM); // Font

    private Font itemFont =
        Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED, Font.SIZE_MEDIUM); // Font
    private Font splitFont =
        Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM); // Font
    private int fontHeight = itemFont.getHeight();
    private ExpensesBean expensesBean = null;

    private int itemWidth = 100;
    private int itemHeight = 600;

    public ReportsCustomItem(String title, ExpensesBean expenseBean, int width, int height)
    {
        super(title);
        this.expensesBean = expenseBean;
        this.itemWidth = width;
        int calculatedHeight = getCalculatedHeight();
        this.itemHeight = calculatedHeight > height ? calculatedHeight : height;

    }

    public int getMinContentWidth()
    {
        return itemWidth;
    }

    public int getMinContentHeight()
    {
        return itemHeight;
    }

    public int getPrefContentWidth(int width)
    {
        return itemWidth;
    }

    public int getPrefContentHeight(int height)
    {
        return itemHeight;
    }

    public void paint(Graphics g, int w, int h)
    {
        RGBPresenter presenter = StorageHelper.getCurrentThemeInRGB();
        int[] backGround = presenter.getBackGround();
        int[] itemColor = presenter.getForeGround();
        int[] dateColor = presenter.getSelection();
        g.setColor(backGround[0], backGround[1],backGround[2]);
        g.fillRect(0, 0, w, h);

        Hashtable expensesMap = expensesBean.getExpenseMap();
        Enumeration enumItems = expensesMap.elements();
        g.setFont(headerFont);
        g.setColor(itemColor[0], itemColor[1], itemColor[2]);
        int y = fontHeight;
        g.drawString("Monthly Report", w / 2, y, Graphics.HCENTER | Graphics.BASELINE);
        y = y + fontHeight;
        g.drawString(expensesBean.getMonth() + "_" + expensesBean.getYear(), w / 2, y,
            Graphics.HCENTER | Graphics.BASELINE);
        g.setColor(0, 255, 0);
        y = y + fontHeight;
        g.drawString("Total:" + expensesBean.getTotal(), 0, y, Graphics.TOP | Graphics.LEFT);

        y = y + fontHeight;

        g.drawString("Split up:", 0, y, Graphics.TOP | Graphics.LEFT);

        y = y + fontHeight;
        g.setFont(itemFont);
        g.setColor(dateColor[0], dateColor[1], dateColor[2]);
        while (enumItems.hasMoreElements())
        {

            ExpenseItem currentItem = (ExpenseItem) enumItems.nextElement();
            g.drawString(currentItem.getItemName(), 0, y, Graphics.TOP | Graphics.LEFT);

            Hashtable splits = currentItem.getSplitUp();
            Enumeration enumSplits = splits.keys();
            while (enumSplits.hasMoreElements())
            {
                g.setFont(splitFont);
                g.setColor(255, 255, 255);
                y = y + fontHeight;
                String date = (String) enumSplits.nextElement();
                String amount = (String) splits.get(date);

                g.drawString("  " + date + " -- " + amount, 0, y, Graphics.TOP | Graphics.LEFT);

            }
            g.setFont(itemFont);
            g.setColor(255, 216, 0);
            y = y + fontHeight;
        }

    }

    public int getCalculatedHeight()
    {
        Hashtable expensesMap = expensesBean.getExpenseMap();
        Enumeration enumItems = expensesMap.elements();
        // For first 5 items build the height.
        int y = headerFont.getHeight() * fontHeight;
        while (enumItems.hasMoreElements())
        {
            ExpenseItem currentItem = (ExpenseItem) enumItems.nextElement();
            Hashtable splits = currentItem.getSplitUp();
            y = y + splits.size() * fontHeight;
        }
        // Add some free bottom portion.
        return y + (2 * fontHeight);
    }
}
