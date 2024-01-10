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
package org.met.j2me.util;

import java.util.Hashtable;
import org.met.j2me.ExpenseItem;

/**
 * Class for holding expense related itesm
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ExpensesBean
{

    private String month;
    private String year;
    private double total;
    private String[][] expenses = null;

    private Hashtable expenseMap = new Hashtable();

    /**
     * Returns the expenseMap.
     * 
     * @return the expenseMap
     */
    public Hashtable getExpenseMap()
    {
        return expenseMap;
    }

    public void addExpense(String item, String date, String amount)
    {
    }

    /**
     * Sets the expenseMap.
     * 
     * @param expenseMap the expenseMap to set
     */
    public void addExpenseByItem(String item, String date, String amount)
    {
        ExpenseItem expItem = (ExpenseItem) expenseMap.get(item);
        if (expItem == null)
        {
            expItem = new ExpenseItem(item);
            expenseMap.put(item, expItem);

        }
        expItem.addMore(date, amount);

    }

    public ExpensesBean(String month, String year)
    {
        this.month = month;
        this.year = year;
    }

    /**
     * Returns the month.
     * 
     * @return the month
     */
    public String getMonth()
    {
        return month;
    }

    /**
     * Sets the month.
     * 
     * @param month the month to set
     */
    public void setMonth(String month)
    {
        this.month = month;
    }

    /**
     * Returns the year.
     * 
     * @return the year
     */
    public String getYear()
    {
        return year;
    }

    /**
     * Sets the year.
     * 
     * @param year the year to set
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     * Returns the total.
     * 
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * Sets the total.
     * 
     * @param total the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * Returns the expenses.
     * 
     * @return the expenses
     */
    public String[][] getExpenses()
    {
        return expenses;
    }

    /**
     * Sets the expenses.
     * 
     * @param expenses the expenses to set
     */
    public void setExpenses(String[][] expenses)
    {
        this.expenses = expenses;
    }

}
