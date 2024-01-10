/*
 * Copyright 2009 Ragupathi Palaniappan. Licensed under the Apache License, Version 2.0 (the
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

import java.util.Date;

/**
 * Class for helping Date related operations.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class DateSplitter
{
    private String data;

    private String month;
    private String year;
    private String date;
    private String day;

    public DateSplitter(String date)
    {

        data = date;
        processData();
    }

    // ^Sun Feb 15 00:00:00 UTC 2009
    private void processData()
    {
        day = data.substring(0, 4);
        month = data.substring(4, 7);
        date = data.substring(8, 10);

        if (data.indexOf("GMT") > 0)
        {
            year = data.substring(29);
        }
        else
        {
            year = data.substring(24);
        }

    }

    public static void main(String[] args)
    {
        DateSplitter ds = new DateSplitter("Sun Feb 15 00:00:00 UTC 2009");
        DateSplitter ds2 = new DateSplitter("Sun Feb 15 00:00:00 GMT+05:30 2009");
        System.out.println(ds);
        System.out.println(ds2);

    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public static String getDay_Date(String date)
    {
        return date.substring(0, 4) + "(" + date.substring(8, 10) + ")";
    }

    public static String getDate(String date)
    {
        return date.substring(8, 10);
    }

    public static String getMonthAndYear(Date givenDate)
    {
        String date = givenDate.toString();
        return date.substring(4, 7) + date.substring(date.length() - 4, date.length());
    }

    public String toString()
    {

        return "Day" + day + "Month:" + month + " Year:" + year + "Date" + date;
    }

}
