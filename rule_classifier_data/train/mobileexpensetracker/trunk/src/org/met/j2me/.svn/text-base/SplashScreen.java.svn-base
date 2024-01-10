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

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;

import org.met.j2me.util.ImageHelper;

/**
 * Splash screen for managing the Welcome screen.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class SplashScreen extends Canvas
{
    
    private MasterMidlet next;
    private Timer timer = new Timer();
    private String imageSource = null;
    public static final int DESTINATION_WELCOME=0;
    public static final int DESTINATION_EXP_SUCCESS=1;
    public static final int DESTINATION_EXP_FAILURE=2;
    public static final int DESTINATION_EXP_TYPE_SUCCESS=3;
    public static final int DESTINATION_EXP_TYPE_FAILURE=4;
    private int destination=0;
    public SplashScreen(Display display, MasterMidlet next, String imageSource,int destination)
    {
        this.next = next;
        this.imageSource = imageSource;
        this.destination=destination;
        display.setCurrent(this);
    }

    protected void keyPressed(int keyCode)
    {
        dismiss();
    }

    protected void paint(Graphics g)
    {
        int w = getWidth();
        int h = getHeight();

        g.drawImage(ImageHelper.getImage(imageSource), w / 2, h / 2, Graphics.HCENTER
            | Graphics.VCENTER);

    }

    protected void pointerPressed(int x, int y)
    {
        dismiss();
    }

    protected void showNotify()
    {
        timer.schedule(new CountDown(), 3000);
    }

    private void dismiss()
    {
        timer.cancel();
        switch (destination)
        {
            case DESTINATION_WELCOME:
                next.showWelcomeScreen();
                break;
            case DESTINATION_EXP_SUCCESS:
                next.showManageExpensesScreen();
                break;
            case DESTINATION_EXP_FAILURE:
                next.showEditExpenseScreen();
                break;
            case DESTINATION_EXP_TYPE_SUCCESS:
                next.showSettingsScreen();
                break;
            case DESTINATION_EXP_TYPE_FAILURE:
                next.showSettingsScreen();
                break;
                                
            default:
                break;
        }
        
    }

    private class CountDown extends TimerTask
    {
        public void run()
        {
            dismiss();
        }
    }
}