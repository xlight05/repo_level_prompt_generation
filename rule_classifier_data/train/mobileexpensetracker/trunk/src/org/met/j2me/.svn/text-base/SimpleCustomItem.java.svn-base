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

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.met.j2me.util.ImageHelper;
import org.met.j2me.util.StorageHelper;

/**
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 30, 2009
 */
public class SimpleCustomItem extends CustomItem
{

    private Font itemFont =
        Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED, Font.SIZE_SMALL); // Font

    // Keeping a default value, right now may look like a magic value.
    private int itemWidth = 100;
    // Keeping a default value, right now may look like a magic value.
    private int itemHeight = 600;
    private String bgImageName = null;
    private String message = "";

    public SimpleCustomItem(String title, int width, int height, String imageSource, String message)
    {
        super(title);
        this.itemWidth = width;
        this.itemHeight = height;
        this.bgImageName = imageSource;
        this.message = message;
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
        int[] selection = presenter.getSelection();
        g.setColor(backGround[0], backGround[1], backGround[2]);
        g.fillRect(0, 0, w, h);

        Image bgImage = ImageHelper.getImage(this.bgImageName);
        g.drawImage(bgImage, w / 2, h / 2, Graphics.HCENTER | Graphics.VCENTER);

        g.setFont(itemFont);
        g.setColor(selection[0], selection[1], selection[2]);
        g.drawString(message, w / 2, (h / 2) + bgImage.getHeight(), Graphics.HCENTER
            | Graphics.BASELINE);

    }

}
