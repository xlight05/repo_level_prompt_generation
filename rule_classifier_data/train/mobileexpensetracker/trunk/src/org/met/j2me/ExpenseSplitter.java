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

/**
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ExpenseSplitter
{
    private String item = null;
    private String amount = null;
    private String date = null;

    public ExpenseSplitter(byte[] expense)
    {
        String data = new String(expense);
        initialiseData(data);

    }

    public ExpenseSplitter(String expense)
    {
        initialiseData(expense);
    }

    private void initialiseData(String currentRecord)
    {

        String items[] = getItemsOnDemand(currentRecord, 3);
        item = items[0];
        date = DateSplitter.getDay_Date(items[1]);
        amount = items[2];
    }

    private static String[] getItemsOnDemand(String expense, int positionUntil)
    {

        char[] currentRecordChars = expense.toCharArray();
        int startPosition = 0;
        int position = 0;
        String items[] = new String[3];
        for (int j = 0; j < currentRecordChars.length; j++)
        {
            // System.out.println("Current Record Chars" +
            // currentRecordChars);
            if (currentRecordChars[j] == '^')
            {
                if (position == 1)
                {
                    items[position] = expense.substring(startPosition, j);
                }
                else if (position == 0)
                {
                    items[position] = expense.substring(startPosition, j);
                }
                else
                {
                    items[position] = expense.substring(startPosition, j);
                }

                startPosition = j + 1;
                position = position + 1;
            }
            if (position == positionUntil)
                return items;

        }

        return items;
    }

    public static String getDateFromExpense(String expense)
    {
        String items[] = getItemsOnDemand(expense, 2);
        return DateSplitter.getDate(items[1]);
    }

    public String toString()
    {
        return "[Item:" + item + "] [Date:" + date + "] [ Amount:" + amount + "]";
    }

    public static void main(String[] args)
    {
        String expense = "Broadband^Sat Dec 12 00:00:00 UTC 2009^123213^";
        System.out.println("Data" + new ExpenseSplitter(expense));
        System.out.println(" Expense Date" + getDateFromExpense(expense));
    }
}
