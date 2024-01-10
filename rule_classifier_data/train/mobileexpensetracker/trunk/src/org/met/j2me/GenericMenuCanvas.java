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

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.met.j2me.util.ImageHelper;
import org.met.j2me.util.StorageHelper;

/**
 * Generic Canvas class for handling Menu operations. Brings consistency across canvas screens in
 * the application
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public abstract class GenericMenuCanvas extends Canvas
{
    private Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM); // Font
    // used
    // for
    // drawing
    // text

    private int currentPosition = 0;
    private MasterMidlet masterMidlet = null;

    private String[] items = null;
    // Images should be positionied to map the position of the corresponding item.
    private String[] images = null;

    public GenericMenuCanvas(MasterMidlet midlet, String[] items, String[] images)
    {
        this.masterMidlet = midlet;

        this.images = images;
        this.items = items;
    }

    /**
     * Sets the items.
     * 
     * @param items the items to set
     */
    public void setItems(String[] items)
    {
        this.items = items;
    }

    /**
     * Sets the images.
     * 
     * @param images the images to set
     */
    public void setImages(String[] images)
    {
        this.images = images;
    }

    /**
     * Returns the currentPosition.
     * 
     * @return the currentPosition
     */
    public int getCurrentPosition()
    {
        return currentPosition;
    }

    /**
     * Returns the masterMidlet.
     * 
     * @return the masterMidlet
     */
    public MasterMidlet getMasterMidlet()
    {
        return masterMidlet;
    }

    /**
     * Sets the masterMidlet.
     * 
     * @param masterMidlet the masterMidlet to set
     */
    public void setMasterMidlet(MasterMidlet masterMidlet)
    {
        this.masterMidlet = masterMidlet;
    }

    protected void keyPressed(int key)
    {
        handleActions(key);

    }

    private void handleActions(int keyCode)
    {

        int action = getGameAction(keyCode);
        switch (action)
        {
            case UP:
                if (currentPosition != 0)
                    currentPosition--;
                else if (currentPosition == 0)
                {
                    currentPosition = items.length - 1;
                }

                repaint();
                break;
            case DOWN:
                if (currentPosition < (items.length - 1))
                {
                    currentPosition++;
                }
                else if (currentPosition == (items.length - 1))
                {
                    currentPosition = 0;
                }
                repaint();
                break;
            case FIRE:
            {
                // Let the firing be handled by the individual Implementors.
                handleFireEvent(currentPosition);
            }
        }

        // repaint();
    }

    protected void paint(Graphics g)
    {

        RGBPresenter presenter = StorageHelper.getCurrentThemeInRGB();
        int[] backGround = presenter.getBackGround();
        int[] foreGround = presenter.getForeGround();
        int[] selection = presenter.getSelection();

        g.setFont(font);
        g.setColor(backGround[0], backGround[1], backGround[2]);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(foreGround[0], foreGround[1], foreGround[2]);
        int currentHeight = 0;
        Image firstImage = ImageHelper.getImage(images[0]);
        int height = firstImage.getHeight() + 2;
        int width = firstImage.getWidth() + 2;
        for (int i = 0; i < items.length; i++)
        {
            g.drawImage(ImageHelper.getImage(images[i]), 0, currentHeight, Graphics.TOP
                | Graphics.LEFT);

            if (i == currentPosition)
            {

                g.setColor(selection[0], selection[1], selection[2]);
                g.drawString(items[i], width, currentHeight, Graphics.TOP | Graphics.LEFT);

                g.setColor(foreGround[0], foreGround[1], foreGround[2]);
            }
            else
            {

                g.drawString(items[i], width, currentHeight, Graphics.TOP | Graphics.LEFT);
            }
            currentHeight = currentHeight + height;
        }

    }

    /**
     * To be handled by the concrete implementations.
     * @param currentPosition
     */
    public abstract void handleFireEvent(int currentPosition);

}
