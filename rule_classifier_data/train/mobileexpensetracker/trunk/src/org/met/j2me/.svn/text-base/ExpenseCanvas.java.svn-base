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

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import org.met.j2me.util.StorageHelper;

/**
 * Canvas to show expense related activities.
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ExpenseCanvas extends GenericMenuCanvas
{

    public ExpenseCanvas(MasterMidlet midlet)
    {
        super(midlet, new String[]{"Add Expense", "Edit Expense", "Home"}, new String[]{
            "AddExpenseType.png", "Edit.png", "Home.png"});
        System.out.println("From Generic Canvas");
    }

    public ExpenseCanvas(MasterMidlet midlet, String[] expenseTypes)
    {
        super(midlet, new String[]{"Add Expense", "Edit Expense", "Home"}, new String[]{
            "AddExpenseType.png", "Edit.png", "Home.png"});
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

            getMasterMidlet().showAddExpenseScreen();

        }
        else if (currentPosition == 1)
        {
            getMasterMidlet().showEditExpenseScreen();
        }

        else if (currentPosition == 2)
        {
            getMasterMidlet().showWelcomeScreen();

        }

    }

}
