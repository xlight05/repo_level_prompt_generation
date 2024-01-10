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

import javax.microedition.rms.RecordComparator;
import org.met.j2me.ExpenseSplitter;

public class ExpenseSorter implements RecordComparator
{

    public static final int ITEM_EXPENSE_TYPE = 0;
    public static final int ITEM_DATE = 1;
    public static final int ITEM_AMOUNT = 2;

    public ExpenseSorter()
    {
    }

    public int compare(byte[] arg0, byte[] arg1)
    {
        int one = Integer.parseInt(ExpenseSplitter.getDateFromExpense(new String(arg0)));
        int two = Integer.parseInt(ExpenseSplitter.getDateFromExpense(new String(arg1)));

        if (one < two)
        {
            return RecordComparator.PRECEDES;
        }
        if (one > two)
        {
            return RecordComparator.FOLLOWS;
        }

        return RecordComparator.EQUIVALENT;

    }
}
