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
package org.met.j2me.transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import org.met.j2me.ExpenseItem;
import org.met.j2me.util.ExpensesBean;

/**
 * CLass for aiding the Export of monthly report to persistence storage like Memory Card.
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class MonthlyExporter implements Runnable
{

    
    private static final String DEFAULT_DIR = "MET";

    private ExpensesBean expenseInfo = null;
    private String destination = null;

    public void run()
    {
        try
        {
            export();
        }
        catch (Exception ignore)
        {
            // Ideally this should not Happen
        }

    }

    public MonthlyExporter(ExpensesBean expenseBean, String destination)
    {
        this.expenseInfo = expenseBean;
        this.destination = destination;

    }

    private void export() throws Exception
    {

        Runnable r = new Runnable()
        {

            public void run()
            {
                try
                {

                    FileConnection fc =
                        (FileConnection) Connector.open("file:///" + destination + DEFAULT_DIR);

                    if (!fc.exists())
                    {
                        fc.mkdir();
                    }

                    FileConnection monthlyFile =
                        (FileConnection) Connector.open("file:///" + destination + DEFAULT_DIR
                            + "/" + expenseInfo.getMonth() + "_" + expenseInfo.getYear() + ".csv",
                            Connector.READ_WRITE);

                    if (!monthlyFile.exists())
                        monthlyFile.create();

                    String content = generateContentFromData();

                    DataOutputStream dos = monthlyFile.openDataOutputStream();
                    Writer writer = new java.io.OutputStreamWriter(dos);
                    writer.write(content, 0, content.length());
                    writer.flush();
                    dos.close();
                    writer.close();
                    monthlyFile.close();
                }
                catch (IOException ignore)
                {
                    // Ideally this shouldnot happen
                }

            }
        };
        new Thread(r).start();

    }

    /**
     * Generate a Comma separated files.
     * @return
     */
    private String generateContentFromData()
    {
        StringBuffer appender = new StringBuffer(200);
        Hashtable expenseMap = expenseInfo.getExpenseMap();
        Enumeration enumDays = expenseMap.keys();
        while (enumDays.hasMoreElements())
        {
            String item = (String) enumDays.nextElement();
            ExpenseItem itemMap = (ExpenseItem) expenseMap.get(item);
            Hashtable splitUp = itemMap.getSplitUp();

            Enumeration enumItems = splitUp.keys();
            while (enumItems.hasMoreElements())
            {
                String currentDate = (String) enumItems.nextElement();
                String amount = (String) splitUp.get(currentDate);
                appender.append(item);
                appender.append(",");
                appender.append(currentDate);
                appender.append(",");
                appender.append(amount);
                appender.append("\n");
            }

        }

        return appender.toString();
    }

}
