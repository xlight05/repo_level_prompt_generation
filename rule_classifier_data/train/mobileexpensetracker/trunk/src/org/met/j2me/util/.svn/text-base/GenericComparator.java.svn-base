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

/**
 * A generic comparator class.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class GenericComparator implements RecordComparator
{

    boolean ascOrder = true;
    public static final int TYPE_INT = 0;
    public static final int TYPE_STR = 1;
    int dataType = TYPE_INT;

    public GenericComparator(boolean isAscending, int dataType)
    {
        ascOrder = isAscending;
        this.dataType = dataType;
    }

    public GenericComparator(boolean isAscending)
    {
        ascOrder = isAscending;
    }

    public int compare(byte[] arg0, byte[] arg1)
    {
        String one = new String(arg0);
        String two = new String(arg1);
        int compared = -1;
        if (ascOrder)
        {
            compared = (one.toLowerCase()).compareTo(two.toLowerCase());
        }
        else
        {
            compared = (two.toLowerCase()).compareTo(one.toLowerCase());
        }
        if (compared < 0)
        {
            return RecordComparator.PRECEDES;
        }
        if (compared > 0)
        {
            return RecordComparator.FOLLOWS;
        }

        return RecordComparator.EQUIVALENT;
    }
}
