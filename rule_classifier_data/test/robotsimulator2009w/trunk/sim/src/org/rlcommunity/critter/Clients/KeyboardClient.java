/*
 * Copyright 2008 Brian Tanner
 * http://bt-recordbook.googlecode.com/
 * brian@tannerpages.com
 * http://brian.tannerpages.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.rlcommunity.critter.Clients;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.rlcommunity.critter.Drops.*;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author Brian Tanner
 * @author Marc G. Bellemare
 */
public class KeyboardClient implements DropClient, KeyListener {

    public int up;
    public int down;
    public int left;
    public int right;
    
    public int left_motor_throttle_pos;
    public int left_motor_throttle_neg;
    public int right_motor_throttle_pos;
    public int right_motor_throttle_neg;

    public boolean hasChange;

    public KeyboardClient() {
        up = down = left = right = 0;
        left_motor_throttle_pos = left_motor_throttle_neg
         = right_motor_throttle_pos = right_motor_throttle_neg = 0;
        hasChange = false;
    }

    public void keyTyped(KeyEvent e) {
    }

    protected void keyChanged(KeyEvent e, int state) {
        hasChange = true;

        switch (e.getKeyCode())
        {
        	case KeyEvent.VK_UP: 	up = state; break;
        	case KeyEvent.VK_DOWN: 	down = state; break;
        	case KeyEvent.VK_LEFT: 	left = state; break;
        	case KeyEvent.VK_RIGHT: right = state; break;
        	case KeyEvent.VK_A: left_motor_throttle_pos = state; break;
        	case KeyEvent.VK_Z: left_motor_throttle_neg = state; break;
        	case KeyEvent.VK_L: right_motor_throttle_pos = state; break;
        	case KeyEvent.VK_COMMA: right_motor_throttle_neg = state; break;
        }
    }

    public void keyPressed(KeyEvent e) {
        keyChanged(e,1);    
    }

    public void keyReleased(KeyEvent e) {
        keyChanged(e,0);
    }


    long lastDropTime=System.currentTimeMillis();
    long keyboardDropInterval=50;
    
    protected boolean anyPressed()
    {
    	return (up>0 || down>0 || left>0 || right>0
    		|| left_motor_throttle_pos>0
    		|| left_motor_throttle_neg>0
    		|| right_motor_throttle_pos>0
    		|| right_motor_throttle_neg>0);
    }
    /** Called by the client who wants to receive a keyboard event.
     * This might be setting velocity to 0 when no command received.
     * @return
     */
    public List<SimulatorDrop> receive() 
    {
      double velocityX,  angVel;
      int maxVel=1; // 1m/s 
      int maxAngularVel=27; // Roughly 1/2 a turn in a second @@@ TODO what is the unit here??!!

      LinkedList<SimulatorDrop> dropList = new LinkedList<SimulatorDrop>();

      // Produce a drop iff: a key is pressed, or was released, and enough 
      //  time has elapsed
      if (hasChange || anyPressed()) 
      {
        if(System.currentTimeMillis()-lastDropTime >= keyboardDropInterval)
        {
          velocityX = (up * maxVel - down * maxVel);
          angVel = (right * -maxAngularVel + left * maxAngularVel);

    	  int left_motor_input = left_motor_throttle_pos - left_motor_throttle_neg;
    	  int right_motor_input = right_motor_throttle_pos - right_motor_throttle_neg;
          
          CritterControlDrop controlDrop = new CritterControlDrop();
          if (angVel!=0 || velocityX!=0 || hasChange)
          {
        	  controlDrop.motor_mode = CritterControlDrop.MotorMode.XYTHETA_SPACE;
        	  controlDrop.x_vel = (int) velocityX;
        	  controlDrop.y_vel = 0;
        	  controlDrop.theta_vel = (int) angVel;
          }
          else // @@@ TODO: not good (must decide at the beginning of the control modes?)
          {
        	  controlDrop.motor_mode = CritterControlDrop.MotorMode.WHEEL_SPACE;
        	  controlDrop.m100_vel = left_motor_input;
        	  controlDrop.m220_vel = right_motor_input;
          }
          hasChange = false;      
          lastDropTime=System.currentTimeMillis();
          dropList.add(controlDrop);
        }
      }
    
      
      return dropList;
    }

    public void send(SimulatorDrop pData) {
      // Do nothing - the keyboard client has no 'output' per se
    }
}
