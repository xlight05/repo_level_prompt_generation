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

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import org.met.j2me.DateSplitter;
import org.met.j2me.RGBPresenter;
import org.met.j2me.SettingsFilter;
import org.met.j2me.exceptions.StorageException;
import org.met.j2me.transport.MonthlyExporter;

/**
 * The main class for interacting with the record store. TODO: Needs bit of refactoring.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class StorageHelper
{
    private static RecordStore settingsDatabase = null;
    private static final String STG_EXP_TYPE_CHANGED = "EXP_TYPE_CHANGED";
    private static final String STG_THEME = "STG_THEME";
    private static final String STG_ST_LCN = "ST_LCN";
    private static final String EXP_DB_PREFIX = "EXP_DB_";
    private static final String SETTINGS_DS = "SETTINGS_DS";
    private static final String EXP_TYPE_DB = "EXP_TYPE_DB";
    private static String THEME_CHOICE = null;
    private static RGBPresenter rgbPresenter = null;

    static
    {
        try
        {
            settingsDatabase = RecordStore.openRecordStore(SETTINGS_DS, true);
            // In case if this is the first time application is used in the mobile
            // Create default expense types
            initialiseExpTypeDsOnDemand();
            initialiseThemeOnDemand();
        }
        catch (RecordStoreFullException e)
        {
            // Typically this should not happen
        }
        catch (RecordStoreNotFoundException e)
        {
            // Typically this should not happen
        }
        catch (RecordStoreException e)
        {
            // Typically this should not happen
        }
        catch (StorageException e)
        {

        }
    }

    public static void addExpenseForMonth(String expenseType, Date selectedDate, String amount)
        throws StorageException
    {

        String expenseRecord = expenseType + "^" + selectedDate + "^" + amount + "^";
        try
        {
            System.out.println("The New Record Store is" + EXP_DB_PREFIX
                + DateSplitter.getMonthAndYear(selectedDate));
            RecordStore expenseDataBase =
                RecordStore.openRecordStore(EXP_DB_PREFIX
                    + DateSplitter.getMonthAndYear(selectedDate), true);
            expenseDataBase.addRecord(expenseRecord.getBytes(), 0, expenseRecord.getBytes().length);
            expenseDataBase.closeRecordStore();
        }
        catch (Exception e)
        {
            throw new StorageException("Error while Adding Expenses for the Month", e);

        }

    }

    private static void initialiseExpTypeDsOnDemand() throws StorageException
    {

        try
        {
            // Check whether expense type is already modified
            RecordEnumeration enumeration =
                settingsDatabase.enumerateRecords(new SettingsFilter(STG_EXP_TYPE_CHANGED), null,
                    false);

            String[] locations = new String[enumeration.numRecords()];
            String expTypeChanged = "";
            for (int i = 0; i < locations.length; i++)
            {
                String currentRecord = new String(enumeration.nextRecord());
                if (currentRecord != null && currentRecord.indexOf("^") > 0)
                {
                    expTypeChanged = currentRecord.substring((currentRecord.indexOf("^") + 1));
                }
            }
            // In case if the user has already done some editing,then no need to load the default
            // expense types.
            if (!"TRUE".equalsIgnoreCase(expTypeChanged))
            {
                addExpenseTypes(new String[]{"Misc", "Travel", "NewsPaper", "Rent", "Mobile",
                    "Medicine", "Grocery", "Gas", "Electricity", "Dining", "Cable", "Broadband"});
            }
            storeSettingsInfo(settingsDatabase, STG_EXP_TYPE_CHANGED, "TRUE");

        }
        catch (Exception e)
        {
            throw new StorageException("Error Initialising Expense Types", e);
        }

    }

    private static void initialiseThemeOnDemand() throws StorageException
    {

        try
        {

            // Check whether expense type is already modified
            RecordEnumeration enumeration =
                settingsDatabase.enumerateRecords(new SettingsFilter(STG_THEME), null, false);

            THEME_CHOICE = getThemeChoice(enumeration);
            // In case if the user has already done some editing,then no need to load the default
            // expense types.
            if (THEME_CHOICE == null)
            {
                THEME_CHOICE = "DarkGrey";
                storeSettingsInfo(settingsDatabase, STG_THEME, THEME_CHOICE);
            }

        }
        catch (Exception e)
        {
            throw new StorageException("Error Initialising Expense Types", e);
        }

    }

    private static String getThemeChoice(RecordEnumeration enumeration)
        throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException
    {
        String[] themes = new String[enumeration.numRecords()];

        for (int i = 0; i < themes.length; i++)
        {
            String currentRecord = new String(enumeration.nextRecord());
            if (currentRecord != null && currentRecord.indexOf("^") > 0)
            {
                return currentRecord.substring((currentRecord.indexOf("^") + 1));
            }
        }
        return null;
    }

    public static String[] getExpensesForMonth(String selectedMonth, String selectedYear)
        throws StorageException
    {

        String data[] = null;

        try
        {

            RecordStore expenseDataBase =
                RecordStore.openRecordStore(EXP_DB_PREFIX + selectedMonth.substring(0, 3)
                    + selectedYear, false);
            if (expenseDataBase == null)
            {
                // return data;
                throw new RuntimeException("Expense data base is null" + EXP_DB_PREFIX
                    + selectedMonth.substring(0, 3) + selectedYear);
            }
            // List of Expense types should be retrieved from the local
            // database.

            RecordEnumeration enumeration = expenseDataBase.enumerateRecords(null, null, false);
            if (enumeration.numRecords() == 0)
            {
                // return data;
                throw new RuntimeException("Expense Enumeration is null");

            }
            data = new String[enumeration.numRecords()];
            String currentExpense = "";

            for (int i = 0; i < data.length; i++)
            {
                currentExpense = "";
                String currentRecord = new String(enumeration.nextRecord());
                char[] currentRecordChars = currentRecord.toCharArray();

                int startPosition = 0;
                int position = 0;
                for (int j = 0; j < currentRecordChars.length; j++)
                {

                    if (currentRecordChars[j] == '^')
                    {
                        if (position == 0)
                        {
                            currentExpense = currentRecord.substring(startPosition, j);

                        }
                        else if (position == 1)
                        {
                            currentExpense =
                                currentExpense
                                    + "-"
                                    + DateSplitter.getDay_Date(currentRecord.substring(
                                        startPosition, j));
                        }
                        else
                        {
                            currentExpense =
                                currentExpense + "-" + currentRecord.substring(startPosition, j);
                        }
                        startPosition = j + 1;
                        position = position + 1;
                    }

                }
                data[i] = currentExpense;
            }
            expenseDataBase.closeRecordStore();
        }
        catch (Exception e)
        {

            throw new StorageException("Error in getting expense details", e);
        }

        return data;
    }

    public static ExpensesBean getExpensesForMonth2(String selectedMonth, String selectedYear)
        throws StorageException
    {

        String data[][] = null;
        double total = 0;
        ExpensesBean expenseBean = null;
        try
        {
            // List of Expense types should be retrieved from the local
            // database.

            System.out.println("For  Display" + EXP_DB_PREFIX + selectedMonth.substring(0, 3)
                + selectedYear);
            RecordStore expenseDataBase =
                RecordStore.openRecordStore(EXP_DB_PREFIX + selectedMonth.substring(0, 3)
                    + selectedYear, true);
            RecordEnumeration enumeration =
                expenseDataBase.enumerateRecords(null, new ExpenseSorter(), false);
            if (enumeration.numRecords() == 0)
            {
                expenseDataBase.closeRecordStore();
                return null;
            }
            expenseBean = new ExpensesBean(selectedMonth, selectedYear);
            data = new String[enumeration.numRecords()][3];

            for (int i = 0; i < data.length; i++)
            {

                String currentRecord = new String(enumeration.nextRecord());

                char[] currentRecordChars = currentRecord.toCharArray();
                int startPosition = 0;
                int position = 0;
                for (int j = 0; j < currentRecordChars.length; j++)
                {
                    // System.out.println("Current Record Chars" +
                    // currentRecordChars);
                    if (currentRecordChars[j] == '^')
                    {
                        if (position == 1)
                        {
                            data[i][position] =
                                DateSplitter.getDay_Date(currentRecord.substring(startPosition, j));
                        }
                        else
                        {
                            data[i][position] = currentRecord.substring(startPosition, j);
                        }
                        startPosition = j + 1;
                        position = position + 1;
                    }

                }
                total = total + Double.parseDouble(data[i][2]);
                expenseBean.addExpenseByItem(data[i][0], data[i][1], data[i][2]);

            }
            expenseDataBase.closeRecordStore();
        }
        catch (Exception e)
        {
            throw new StorageException("Error in getting expense details", e);
        }

        expenseBean.setExpenses(data);
        expenseBean.setTotal(total);
        return expenseBean;
    }

    public static void addExpenseType(String expenseType) throws Exception
    {
        RecordStore expTypeDB = RecordStore.openRecordStore(EXP_TYPE_DB, true);
        byte[] expType = expenseType.getBytes();
        expTypeDB.addRecord(expType, 0, expType.length);
        expTypeDB.closeRecordStore();
    }

    public static void addExpenseTypes(String[] expenseType) throws RecordStoreFullException,
        RecordStoreNotFoundException, RecordStoreException
    {

        RecordStore expTypeDB = RecordStore.openRecordStore(EXP_TYPE_DB, true);
        for (int i = 0; i < expenseType.length; i++)
        {
            byte[] expType = expenseType[i].getBytes();
            expTypeDB.addRecord(expType, 0, expType.length);

        }
        expTypeDB.closeRecordStore();
    }

    public static void setStorageLocation(String storageLocation) throws StorageException
    {
        storeSettingsInfo(settingsDatabase, STG_ST_LCN, storageLocation);
    }

    private static void storeSettingsInfo(RecordStore settingsDataBase, String type, String value)
        throws StorageException
    {
        String totalData = type + "^" + value;
        byte[] newSettingData = totalData.getBytes();

        try
        {
            settingsDataBase.addRecord(newSettingData, 0, newSettingData.length);
            System.out.println("Stored the settings" + value);
        }
        catch (Exception e)
        {
            throw new StorageException("Error Storing Settings Info", e);
        }

    }

    public static String getStorageLocation() throws StorageException
    {

        try
        {
            // List of Expense types should be retrieved from the local
            // database.

            RecordEnumeration enumeration =
                settingsDatabase.enumerateRecords(new SettingsFilter(STG_ST_LCN), null, false);

            String[] locations = new String[enumeration.numRecords()];
            for (int i = 0; i < locations.length; i++)
            {
                String currentRecord = new String(enumeration.nextRecord());
                System.out.println("Current Memory Location" + currentRecord);
                if (currentRecord != null && currentRecord.indexOf("^") > 0)
                {
                    return currentRecord.substring((currentRecord.indexOf("^") + 1));
                }

            }
        }
        catch (Exception e)
        {
            throw new StorageException("Error getting storage Location", e);
        }

        return null;
    }

    public static String[] getExpenseTypes() throws StorageException
    {
        String[] expenseTypes = null;
        try
        {
            // List of Expense types should be retrieved from the local
            // database.

            RecordStore expTypeDB = RecordStore.openRecordStore(EXP_TYPE_DB, true);
            RecordEnumeration enumeration =
                expTypeDB.enumerateRecords(null, new GenericComparator(true), false);

            if (enumeration.numRecords() == 0)
                return null;
            expenseTypes = new String[enumeration.numRecords()];
            for (int i = 0; i < expenseTypes.length; i++)
            {
                String currentRecord = new String(enumeration.nextRecord());

                expenseTypes[i] = currentRecord;
                System.out.println("Curent One:" + currentRecord);
            }
            expTypeDB.closeRecordStore();
        }
        catch (Exception e)
        {
            throw new StorageException("Error Getting Expense Types", e);
        }
        return expenseTypes;
    }

    public static void recreateExpensesForMonth(String month, String year, boolean[] selectedFlags)
        throws Exception
    {

        RecordStore expenseDataBase =
            RecordStore.openRecordStore(EXP_DB_PREFIX + month.substring(0, 3) + year, false);
        if (expenseDataBase == null)
            return;
        // List of Expense types should be retrieved from the local
        // database.

        RecordEnumeration enumeration = expenseDataBase.enumerateRecords(null, null, false);
        Vector v = new Vector();
        for (int i = 0; i < enumeration.numRecords(); i++)
        {
            byte[] data = enumeration.nextRecord();
            if (!selectedFlags[i])
            {
                v.addElement(data);
            }
        }
        expenseDataBase.closeRecordStore();

        RecordStore.deleteRecordStore(EXP_DB_PREFIX + month.substring(0, 3) + year);
        if (v.size() == 0)
            return;
        expenseDataBase =
            RecordStore.openRecordStore(EXP_DB_PREFIX + month.substring(0, 3) + year, true);
        Enumeration elements = v.elements();
        while (elements.hasMoreElements())
        {
            byte[] data = (byte[]) elements.nextElement();
            expenseDataBase.addRecord(data, 0, data.length);
        }
        expenseDataBase.closeRecordStore();

    }

    public static void recreateExpenseTypes(ChoiceGroup expenseTypes, String[] actualExpenses)
        throws StorageException

    {
        try
        {
            boolean[] selectedFlags = new boolean[actualExpenses.length];
            expenseTypes.getSelectedFlags(selectedFlags);

            Vector v = new Vector();
            for (int i = 0; i < actualExpenses.length; i++)
            {

                if (!selectedFlags[i])
                {
                    v.addElement(actualExpenses[i]);
                }
            }

            RecordStore.deleteRecordStore(EXP_TYPE_DB);
            if (v.size() == 0)
                return;
            RecordStore expenseTypeDataBase = RecordStore.openRecordStore(EXP_TYPE_DB, true);
            Enumeration elements = v.elements();
            while (elements.hasMoreElements())
            {
                byte[] data = ((String) elements.nextElement()).getBytes();
                expenseTypeDataBase.addRecord(data, 0, data.length);
            }
            expenseTypeDataBase.closeRecordStore();
        }
        catch (Exception e)
        {
            throw new StorageException("Error Recreating Expense Types", e);
        }
    }

    public static void resetRecordStores() throws StorageException
    {
        String current = null;
        try
        {
            String[] recordStores = RecordStore.listRecordStores();
            for (int i = 0; i < recordStores.length; i++)
            {
                current = recordStores[i];
                if (current.startsWith(EXP_DB_PREFIX))
                {
                    RecordStore.deleteRecordStore(current);
                }

            }
            settingsDatabase.closeRecordStore();

            RecordStore.deleteRecordStore(SETTINGS_DS);
            settingsDatabase = RecordStore.openRecordStore(SETTINGS_DS, true);

            RecordStore.deleteRecordStore(EXP_TYPE_DB);
            initialiseExpTypeDsOnDemand();
        }
        catch (Exception e)
        {
            throw new StorageException("Error resetting RecordStores", e);

        }
    }

    public static void exportToFile(ExpensesBean expenseBean) throws Exception
    {

        String destination = StorageHelper.getStorageLocation();
        System.out.println("The Destnation" + destination);

        Thread t = new Thread(new MonthlyExporter(expenseBean, destination));
        t.start();

    }

    public static String[] getAvailableThemes()
    {
        return new String[]{"Dark Grey", "Orange", "Navy Blue", "Green"};
    }

    public static String getCurrentTheme()
    {
        return THEME_CHOICE;
    }

    public static RGBPresenter getCurrentThemeInRGB()
    {
        if (rgbPresenter != null)
            return rgbPresenter;

        createTheme();
        return rgbPresenter;
    }

    private static void createTheme()
    {
        rgbPresenter = new RGBPresenter();
        if (THEME_CHOICE.equalsIgnoreCase("Orange"))
        {

            rgbPresenter.setBackGround(new int[]{237, 157, 17});
            rgbPresenter.setForeGround(new int[]{2, 75, 149});
            rgbPresenter.setSelection(new int[]{85, 4, 2});
        }
        else if (THEME_CHOICE.equalsIgnoreCase("Navy Blue"))
        {
            rgbPresenter.setBackGround(new int[]{4, 70, 121});
            rgbPresenter.setForeGround(new int[]{255, 138, 0});
            rgbPresenter.setSelection(new int[]{0, 255, 222});

        }
        else if (THEME_CHOICE.equalsIgnoreCase("Green"))
        {
            rgbPresenter.setBackGround(new int[]{133, 195, 1});
            rgbPresenter.setForeGround(new int[]{0, 0, 0});
            rgbPresenter.setSelection(new int[]{154, 2, 2});

        }
        else
        {
            rgbPresenter.setBackGround(new int[]{57, 57, 57});
            rgbPresenter.setForeGround(new int[]{255, 255, 255});
            rgbPresenter.setSelection(new int[]{215, 216, 0});

        }
    }

    public static void resetTheme(String newTheme)
    {
        System.out.println();
        try
        {
            storeSettingsInfo(settingsDatabase, STG_THEME, newTheme);
        }
        catch (StorageException e)
        {
            // TODO, correct the exception handling
            e.printStackTrace();
        }
        THEME_CHOICE = newTheme;
        createTheme();
    }
}
