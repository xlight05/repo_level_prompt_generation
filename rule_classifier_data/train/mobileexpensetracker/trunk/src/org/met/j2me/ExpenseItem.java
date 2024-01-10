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

import java.util.Hashtable;

/**
 * Class to represent individual Expense item
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ExpenseItem
{

    private double totalAmount;
    private String itemName;
    private Hashtable splitUp = new Hashtable();

    public ExpenseItem(String item)
    {
        this.itemName = item;
    }

    public ExpenseItem(String item, double amount)
    {
        this.itemName = item;
        this.totalAmount = amount;
    }

    /**
     * Returns the amount.
     * 
     * @return the amount
     */
    public double getAmount()
    {
        return totalAmount;
    }

    /**
     * Sets the amount.
     * 
     * @param amount the amount to set
     */
    public void setAmount(double amount)
    {
        this.totalAmount = amount;
    }

    /**
     * Returns the itemName.
     * 
     * @return the itemName
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * Sets the itemName.
     * 
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public void addMore(String date, String newAmount)
    {
        totalAmount = totalAmount + Double.parseDouble(newAmount);
        splitUp.put(date, "" + newAmount);

    }

    /**
     * Returns the dates.
     * 
     * @return the dates
     */
    public Hashtable getSplitUp()
    {
        return splitUp;
    }
}
