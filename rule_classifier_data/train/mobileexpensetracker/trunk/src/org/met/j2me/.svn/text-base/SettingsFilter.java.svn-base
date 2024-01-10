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

import javax.microedition.rms.RecordFilter;

public class SettingsFilter implements RecordFilter
{
    private String settingsType;

    public SettingsFilter(String settigngsType)
    {
        
        this.settingsType = settigngsType;
        System.out.println("Settings type" + settigngsType);
    }

    public boolean matches(byte[] recordData)
    {
        // TODO Auto-generated method stub
        String currentRecord = new String(recordData);
        System.out.println("Current DAta" + currentRecord);
        int start = currentRecord.indexOf('^');
        String subSet = currentRecord.substring(0, start);
        System.out.println("The subset" + subSet);
        if ((subSet != null) && (settingsType.equals(subSet)))
        {
            System.out.println("Right hit, " + currentRecord);
            return true;
        }
        else
        {
            System.out.println("Wrong hit");
            return false;
        }
    }
}
