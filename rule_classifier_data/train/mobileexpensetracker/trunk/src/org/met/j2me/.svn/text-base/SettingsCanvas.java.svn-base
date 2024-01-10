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

public class SettingsCanvas extends GenericMenuCanvas
{

    public SettingsCanvas(MasterMidlet midlet)
    {
        super(midlet, new String[]{"Add Expense type", "Remove Expense Types", "System Info",
            "Reset Application", "Set storage", "Select Theme", "Home"}, new String[]{
            "AddExpenseType.png", "Remove.png", "Information.png", "Reset.png", "Storage.png",
            "SelectTheme.png", "Home.png"});
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
            getMasterMidlet().showAddExpenseTypeScreen();
        }
        if (currentPosition == 1)
        {
            getMasterMidlet().showExpenseTypeRemovalScreen();
        }
        else if (currentPosition == 2)
        {
            getMasterMidlet().displaySystemInfo();
        }
        else if (currentPosition == 3)
        {
            getMasterMidlet().showResetCofirmationScn();

        }
        else if (currentPosition == 4)
        {
            getMasterMidlet().displayStorageLocationsScn();
        }
        else if (currentPosition == 5)
        {
            // Give option for choosing the themes.
            getMasterMidlet().showThemeSelectionScren();

        }
        else if (currentPosition == 6)
        {
            try
            {
                getMasterMidlet().showWelcomeScreen();
            }
            catch (Exception ignore)
            {
                System.out.println("Error IN calling Welcome Screen from Settings Canvas.");
            }
        }

    }

}
