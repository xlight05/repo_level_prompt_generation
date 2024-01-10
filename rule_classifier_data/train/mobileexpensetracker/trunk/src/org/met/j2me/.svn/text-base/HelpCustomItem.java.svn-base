/*
 * Copyright 2009 Ragupathi Palaniappan. Licensed under the Apache License, Version 2.0 (the
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

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * Custom Item for showing the Help contents.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class HelpCustomItem extends CustomItem
{

    private Font itemFont =
        Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED, Font.SIZE_SMALL); // Font
    private int fontHeight = itemFont.getHeight();

    //Keeping a default value, right now may look like a magic value.
    private int itemWidth = 100;
  //Keeping a default value, right now may look like a magic value.
    private int itemHeight = 600;

    public HelpCustomItem(String title, int width, int height)
    {
        super(title);
        this.itemWidth = width;
        this.itemHeight = height;
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
        g.setColor(0, 199, 140);
        g.fillRect(0, 0, w, h);
        g.setFont(itemFont);
        g.setColor(255, 255, 255);
        int y = fontHeight;
        g
            .drawString("Mobile Expense Tracker (MET)", w / 2, y, Graphics.HCENTER
                | Graphics.BASELINE);
        y = y + fontHeight;
        g.drawString("V 1.0 (c) 2009", w / 2, y, Graphics.HCENTER | Graphics.BASELINE);
        y = y + fontHeight;
        g.drawString("ragupathipalaniappan.com", w / 2, y, Graphics.HCENTER | Graphics.BASELINE);

    }
}
