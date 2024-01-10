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

public class WelcomeCanvas extends GenericMenuCanvas
{

    public WelcomeCanvas(MasterMidlet midlet)
    {
        super(midlet, new String[]{"Manage Exp", "Reports", "Settings", "Help", "Exit"},
            new String[]{"ManageExpenses.png", "Reports.png", "Settings.png", "Help.png",
                "Exit.png"});
        System.out.println("From Generic Canvas");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ragu.j2me.expenser.GenericMenuCanvas#handleFireEvent(int)
     */
    public void handleFireEvent(int currentPosition)
    {
        if (currentPosition == 0)
        {

            getMasterMidlet().showManageExpensesScreen();
        }
        else if (currentPosition == 1)
        {
            getMasterMidlet().showReportsScreen();
        }
        else if (currentPosition == 2)
        {
            getMasterMidlet().showSettingsScreen();

        }
        else if (currentPosition == 3)
        {
            getMasterMidlet().displayHelp();

        }
        else if (currentPosition == 4)
        {
            getMasterMidlet().exitApplication();

        }

    }

}
