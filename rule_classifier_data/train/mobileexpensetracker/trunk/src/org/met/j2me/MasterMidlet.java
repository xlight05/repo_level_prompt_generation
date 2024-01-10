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

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.met.j2me.exceptions.StorageException;
import org.met.j2me.util.ExpensesBean;
import org.met.j2me.util.ImageHelper;
import org.met.j2me.util.StorageHelper;

/**
 * The single midlet for the entire Application. Starts from opening the welcome screen. Manages a
 * mix of Form and Canvas references.<br>
 * For collecting data from user, Forms are user <br>
 * For navigational purposes Canvas is used.
 * 
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class MasterMidlet extends MIDlet implements CommandListener
{

    public Display display;

    // Datastore

    // private RecordStore settingsDataBase;

    // Holder for containing the expenses.
    private ExpensesBean expenseBean = null;

    // Canvas for holding the Welcome Page
    private WelcomeCanvas welcomeCanvas;
    // Canvas for holding the expense related activities
    private ExpenseCanvas expCanvas;
    // Contains the choices for different reports
    private ReportsCanvas reportsCanvas;
    // Canvas for showing different types of Settings related choics
    private SettingsCanvas settingsScn;

    // Create Forms
    // Form to add expenes
    private Form addExpenseScn;
    // Form for month selection for Expense modification
    private Form modifyExpenseSelectionScn;
    // Actual form showing the list of expenese for expense removal.
    private Form modifyExpenseScn;

    // To add expenese types
    private Form expenseTypeScn;
    // Result form for expense type save addition
    // private Form expenseTypeSaveResultScn;

    private Form monthSelectionScreen;
    private Form sysInfoScn;
    private Form storageSettingResultScn;
    private Form storageSelectionForm;
    private Form modifyExpenseTypeScn;
    private Form monthlyReportsScn;
    private Form resetConfirmationScn;
    private Form helpScn;
    private Form themeSelectionScn;
    /*
     * Create Commands for the Main Screen Create commands for the whole midlet. Ensure that Command
     * variables are readable at the same time they are small.
     */
    // Screens and commands
    // Generic Exit command for all the screens
    private static final Command EXIT_COMMAND = new Command("Exit", Command.EXIT, 0);
    private static final Command HOME_COMMAND = new Command("Home", Command.EXIT, 1);

    private Command ws_settingsCmd;

    // Commands for the Add Expense screen.
    private Command ae_saveExpenseCmd;

    // Command for Modify/Delete expense
    private Command me_modifyExpenseCmd;
    private Command me_removeExpCmd;
    // Commands for the Result screen for Expense Addition
    private Command ar_AddMoreCmd;

    // Commands for adding Expense types
    private Command aet_saveExpenseTypeCmd;
    private Command me_removeExpTypeCmd;
    // Expense type addition result screen
    private Command atr_addMoreCmd;

    // Month Selection screen.
    private Command ms_showExpenseCmd;

    private Command de_exportExpensesCmd;

    private Command dst_setStorageCmd;
    private Command re_ConfirmCmd;
    private Command re_IgnoreCmd;
    private Command ts_confirmCmd;

    // Fields
    TextField expenseTypeField = new TextField("ExpenseType", "", 20, TextField.INITIAL_CAPS_WORD);

    DateField selectMonthField = new DateField("Select Month", DateField.DATE);

    private ChoiceGroup monthSelection;
    private ChoiceGroup yearSelection;
    private ChoiceGroup expenseTypeGroup;
    private DateField expenseDateField;
    private TextField amountField;

    private ChoiceGroup storageLocation;

    // For Edit expenses
    private ChoiceGroup expenses;
    private String[] expensesForMonth = null;

    private ChoiceGroup expenseTypes;
    private ChoiceGroup theme;

    public MasterMidlet()
    {
        display = Display.getDisplay(this);

    }

    protected void destroyApp(boolean destroyApp) throws MIDletStateChangeException
    {
        // Ignore this

    }

    protected void pauseApp()
    {
        // not required right now.
    }

    protected void startApp() throws MIDletStateChangeException
    {

        if (display.getCurrent() == null)
        {
            // startApp called for the first time
            // showWelcomeScreen();
            showSplashScreen();
        }

    }

    public void showSplashScreen()
    {
        new SplashScreen(Display.getDisplay(this), this, "Welcome.png",
            SplashScreen.DESTINATION_WELCOME);
    }

    public void showExpSuccessSplash()
    {
        new SplashScreen(Display.getDisplay(this), this, "thumpsup.png",
            SplashScreen.DESTINATION_EXP_SUCCESS);
    }

    public void showExpFailureSplash()
    {
        new SplashScreen(Display.getDisplay(this), this, "thumbsdown.png",
            SplashScreen.DESTINATION_EXP_SUCCESS);
    }

    public void showExpTypeSuccessSplash()
    {
        new SplashScreen(Display.getDisplay(this), this, "thumpsup.png",
            SplashScreen.DESTINATION_EXP_TYPE_SUCCESS);
    }

    public void showExpTypeFailureSplash()
    {
        new SplashScreen(Display.getDisplay(this), this, "thumbsdown.png",
            SplashScreen.DESTINATION_EXP_TYPE_SUCCESS);
    }

    public void showWelcomeScreen()
    {
        display = Display.getDisplay(this);
        if (welcomeCanvas == null)
        {
            Alert welcome =
                new Alert("Welcome", "Copyright 2009 by Ragu.",
                    ImageHelper.getImage("Welcome.png"), null);
            welcome.setTimeout(Alert.FOREVER);
            welcomeCanvas = new WelcomeCanvas(this);
            welcomeCanvas.setFullScreenMode(true);
            // display.setCurrent(welcome, welcomeCanvas);
            display.setCurrent(welcomeCanvas);
        }
        else
        {
            display.setCurrent(welcomeCanvas);
        }

    }

    public void commandAction(Command cmd, Displayable displayable)
    {

        if (ws_settingsCmd == cmd)
        {
            showSettingsScreen();
        }
        else if (EXIT_COMMAND == cmd)
        {
            try
            {
                destroyApp(false);
                notifyDestroyed();
            }
            catch (MIDletStateChangeException ignore)
            {
                // Ignoring this error.
            }
        }

        else if (cmd == ae_saveExpenseCmd)
        {
            saveExpense();

            // showResultScreen();
        }
        else if (cmd == HOME_COMMAND)
        {

            showWelcomeScreen();

        }
        else if (cmd == ar_AddMoreCmd)
        {

            showAddExpenseScreen();

        }
        else if (cmd == re_IgnoreCmd)
        {
            showSettingsScreen();
        }
        else if (cmd == aet_saveExpenseTypeCmd)
        {
            String expenseType = expenseTypeField.getString();
            if (expenseType == null || expenseType.trim().length() <= 0)
            {
                Alert alert =
                    new Alert("Expense Type Mandatory ", "Expense Type Mandatory ", null,
                        AlertType.ERROR);
                display.setCurrent(alert, display.getCurrent());
            }
            else
            {
                showExpenseTypeSaveResultScreen(expenseType);
            }

        }
        else if (cmd == atr_addMoreCmd)
        {

            showAddExpenseTypeScreen();
        }

        else if (cmd == ms_showExpenseCmd)
        {
            String selectedMonth = monthSelection.getString(monthSelection.getSelectedIndex());
            String selectedYear = yearSelection.getString(yearSelection.getSelectedIndex());

            try
            {
                expenseBean = StorageHelper.getExpensesForMonth2(selectedMonth, selectedYear);
            }
            catch (StorageException e)
            {
                showErrorOnExit("Error Getting Expenses For Month" + selectedMonth + "_"
                    + selectedYear, e);
            }

            if (expenseBean == null || expenseBean.getExpenseMap() == null)
            {
                Alert alert =
                    new Alert("No expenses", "You have no expenses for this month", null,
                        AlertType.WARNING);
                display.setCurrent(alert, display.getCurrent());
            }
            else
            {

                showMonthlyReportsScreen(expenseBean);
            }
        }
        else if (cmd == de_exportExpensesCmd)
        {

            displayExportResult();

        }
        else if (re_ConfirmCmd == cmd)
        {
            resetApplication();
        }
        else if (ts_confirmCmd == cmd)
        {
            StorageHelper.resetTheme(theme.getString(theme.getSelectedIndex()));
            showWelcomeScreen();
        }
        else if (cmd == me_removeExpCmd)
        {
            System.out.println("Refreshing the DB");
            editExpensesForMonth();
        }
        else if (cmd == me_removeExpTypeCmd)
        {
            editExpenseTypes();
        }
        else if (cmd == dst_setStorageCmd)
        {
            showStorageSelectionResultScreen();
        }
        else if (cmd == me_modifyExpenseCmd)
        {

            try
            {
                expensesForMonth =
                    StorageHelper.getExpensesForMonth(monthSelection.getString(monthSelection
                        .getSelectedIndex()), yearSelection.getString(yearSelection
                        .getSelectedIndex()));
            }
            catch (Throwable e)
            {
                Alert alert =
                    new Alert("No Expenses added till now",
                        "No Expenses added till now for this Month" + e.getMessage(), null,
                        AlertType.WARNING);
                display.setCurrent(alert, display.getCurrent());

            }
            if (expensesForMonth == null)
            {
                Alert alert =
                    new Alert("No Expenses added till now",
                        "No Expenses added till now for this Month", null, AlertType.WARNING);
                display.setCurrent(alert, display.getCurrent());
            }
            else
            {
                showExpenseEditingScreen(expensesForMonth);
            }
        }

    }

    private void editExpensesForMonth()
    {
        boolean[] selectedFlags = new boolean[expensesForMonth.length];
        expenses.getSelectedFlags(selectedFlags);
        try
        {
            StorageHelper.recreateExpensesForMonth(monthSelection.getString(monthSelection
                .getSelectedIndex()), yearSelection.getString(yearSelection.getSelectedIndex()),
                selectedFlags);
            display.setCurrent(new Alert("Editing successful", "Editing successful", null,
                AlertType.WARNING), display.getCurrent());

            // showManageExpensesScreen();
            showExpSuccessSplash();
            expensesForMonth = null;
        }
        catch (Exception e)
        {
            showErrorOnExit(e.getMessage(), e);
        }

    }

    private void editExpenseTypes()
    {
        try
        {
            String[] expenseTypesStr = StorageHelper.getExpenseTypes();
            StorageHelper.recreateExpenseTypes(expenseTypes, expenseTypesStr);
            System.out.println("After re creation");
            showExpTypeSuccessSplash();
        }
        catch (StorageException e)
        {
            showErrorOnExit(e.getMessage(), e);

        }

    }

    private void saveExpense()
    {
        // Validate data
        // Save data
        // Show result screen

        String expenseType = expenseTypeGroup.getString(expenseTypeGroup.getSelectedIndex());

        Date selectedDate = expenseDateField.getDate();
        Form addResultScn = null;
        String amount = amountField.getString();
        if (amount == null || amount.trim().length() == 0)
        {

            Alert alert = new Alert("Missing Amount", "Amount is Mandatory", null, AlertType.ERROR);
            display.setCurrent(alert, display.getCurrent());
        }
        else
        {

            try
            {
                if (amount == null || amount.trim().length() == 0)
                {
                    Alert alert =
                        new Alert("Missing Amount", "Amount is Mandatory", null, AlertType.ERROR);
                    display.setCurrent(alert, display.getCurrent());
                }
                else
                {
                    StorageHelper.addExpenseForMonth(expenseType, selectedDate, amount);
                    Item[] items =
                        new Item[]{new SimpleCustomItem("", welcomeCanvas.getWidth(), welcomeCanvas
                            .getHeight(), "thumpsup.png", "Wanna Add More?")};
                    addResultScn = new Form("Success!", items);

                    ar_AddMoreCmd = new Command("Yes", Command.ITEM, 2);

                    addResultScn.addCommand(ar_AddMoreCmd);
                    addResultScn.addCommand(HOME_COMMAND);

                    addResultScn.setCommandListener(this);
                    display.setCurrent(addResultScn);
                }

            }
            catch (Throwable e)
            {

                showErrorOnExit(e.getMessage(), e);

            }

        }
    }

    public void displayExportResult()
    {
        Alert exportAlert = null;

        try
        {
            StorageHelper.exportToFile(expenseBean);
            exportAlert = new Alert("Exporting....", "Exported Successfully", null, null);
            exportAlert.setType(AlertType.CONFIRMATION);
        }
        catch (Exception e)
        {

            exportAlert =
                new Alert("Error Exporting....",
                    "Error Exporting Data, select the Storage location first", null, null);
            exportAlert.setType(AlertType.ERROR);
        }

        display.setCurrent(exportAlert, reportsCanvas);
    }

    private void showExpenseEditingScreen(String[] expensesForMonth)
    {

        expenses =
            new ChoiceGroup("Expense Type (Item-Date-Amount)", ChoiceGroup.MULTIPLE,
                expensesForMonth, null);
        modifyExpenseScn = new Form("Modify Expenses", new Item[]{expenses});
        modifyExpenseScn.addCommand(HOME_COMMAND);
        me_removeExpCmd = new Command("Remove", Command.ITEM, 2);
        modifyExpenseScn.addCommand(me_removeExpCmd);
        modifyExpenseScn.setCommandListener(this);

        display.setCurrent(modifyExpenseScn);

    }

    public void showThemeSelectionScren()
    {

        theme =
            new ChoiceGroup("Select Background", ChoiceGroup.EXCLUSIVE, StorageHelper
                .getAvailableThemes(), null);
        themeSelectionScn = new Form("Modify Expenses", new Item[]{theme});
        themeSelectionScn.addCommand(HOME_COMMAND);
        ts_confirmCmd = new Command("Confirm", Command.ITEM, 2);
        themeSelectionScn.addCommand(ts_confirmCmd);
        themeSelectionScn.setCommandListener(this);

        display.setCurrent(themeSelectionScn);

    }

    public void showExpenseTypeRemovalScreen()
    {

        String[] expenseTypesStr = null;
        try
        {
            expenseTypesStr = StorageHelper.getExpenseTypes();
        }
        catch (StorageException e)
        {
            showErrorOnExit("Error Getting Expense Types for Removal", e);
        }

        expenseTypes =
            new ChoiceGroup("Expense Types", ChoiceGroup.MULTIPLE, expenseTypesStr, null);
        modifyExpenseTypeScn = new Form("Modify Expenses", new Item[]{expenseTypes});
        modifyExpenseTypeScn.addCommand(HOME_COMMAND);
        me_removeExpTypeCmd = new Command("Remove", Command.ITEM, 2);
        modifyExpenseTypeScn.addCommand(me_removeExpTypeCmd);
        modifyExpenseTypeScn.setCommandListener(this);

        display.setCurrent(modifyExpenseTypeScn);

    }

    public void exitApplication()
    {
        // Exit the application.
        try
        {

            destroyApp(false);
            notifyDestroyed();
        }
        catch (Exception ignore)
        {

        }
    }

    public void showReportsScreen()
    {
        if (reportsCanvas == null)
        {
            reportsCanvas = new ReportsCanvas(this);
            reportsCanvas.setFullScreenMode(true);
        }

        display.setCurrent(reportsCanvas);

    }

    public void showManageExpensesScreen()
    {
        if (expCanvas == null)
        {
            expCanvas = new ExpenseCanvas(this);
            expCanvas.setFullScreenMode(true);

        }
        display.setCurrent(expCanvas);

    }

    public void displayStorageLocationsScn()
    {
        if (storageSelectionForm == null)
        {
            storageLocation = new ChoiceGroup("Storage Location", ChoiceGroup.POPUP);
            storageSelectionForm = new Form("Storage Selection");
            storageSelectionForm.append(storageLocation);
            dst_setStorageCmd = new Command("Set Storage", Command.ITEM, 2);
            storageSelectionForm.addCommand(HOME_COMMAND);
            storageSelectionForm.addCommand(dst_setStorageCmd);
            storageSelectionForm.setCommandListener(this);
        }

        Thread t = new Thread(new Runnable()
        {

            public void run()
            {
                Enumeration fileRoots = FileSystemRegistry.listRoots();

                while (fileRoots.hasMoreElements())
                {
                    String object = (String) fileRoots.nextElement();

                    // sysInfoScn.append("Current File Element:" + object);
                    storageLocation.append(object, null);
                }
            }

        });

        t.start();

        display.setCurrent(storageSelectionForm);
    }

    public void resetApplication()
    {

        String message = "Reset done successfully";
        try
        {
            StorageHelper.resetRecordStores();
        }
        catch (StorageException e)
        {
            showErrorOnExit("Error While Resetting the application" + e.getMessage(), e);
        }
        Form resetScreen = new Form("Reset Result", new StringItem[]{new StringItem("", message)});
        resetScreen.setCommandListener(this);
        resetScreen.addCommand(HOME_COMMAND);
        display.setCurrent(resetScreen);

    }

    public void displaySystemInfo()
    {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long free = runtime.freeMemory();
        if (sysInfoScn == null)
        {
            sysInfoScn = new Form("System Properties");
            long total = runtime.totalMemory();
            sysInfoScn.append("MET Version =" + getAppProperty("MIDlet-Version"));
            sysInfoScn.append("Vendor =" + getAppProperty("MIDlet-Vendor"));
            sysInfoScn.append(showProp("microedition.configuration"));
            sysInfoScn.append(showProp("microedition.profiles"));

            sysInfoScn.append(showProp("microedition.platform"));
            sysInfoScn.append(showProp("microedition.locale"));
            sysInfoScn.append(showProp("microedition.encoding"));
            sysInfoScn.append(showProp("microedition.io.file.FileConnection.version"));
            sysInfoScn.addCommand(HOME_COMMAND);
            sysInfoScn.setCommandListener(this);
            display.setCurrent(sysInfoScn);
        }
        else
        {
            sysInfoScn.set(0, new StringItem("", "Free Memory = " + free + "\n"));
        }

        display.setCurrent(sysInfoScn);

    }

    /**
     * Show a property.
     */
    private String showProp(String prop)
    {
        String value = System.getProperty(prop);
        StringBuffer propbuf = new StringBuffer();
        propbuf.setLength(0);
        propbuf.append(prop);
        propbuf.append(" = ");

        if (value == null)
        {
            propbuf.append("<undefined>");
        }
        else
        {
            propbuf.append("\"");
            propbuf.append(value);
            propbuf.append("\"");
        }

        propbuf.append("\n");

        return propbuf.toString();
    }

    public void showMonthSelectionScreen()
    {

        if (monthSelectionScreen == null)
        {
            Calendar c = Calendar.getInstance();
            monthSelection =
                new ChoiceGroup("Select Month", ChoiceGroup.POPUP,

                new String[]{"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"}, null);
            monthSelection.setSelectedIndex(c.get(Calendar.MONTH), true);
            yearSelection =
                new ChoiceGroup("Select Year", ChoiceGroup.POPUP,

                new String[]{"" + (c.get(Calendar.YEAR) - 1), "" + c.get(Calendar.YEAR),
                    "" + (c.get(Calendar.YEAR) + 1)}, null);
            yearSelection.setSelectedIndex(1, true);
            Item[] monthItem = new Item[]{monthSelection, yearSelection};

            monthSelectionScreen = new Form("Select Month", monthItem);
            ms_showExpenseCmd = new Command("Show Expense", Command.ITEM, 2);
            monthSelectionScreen.addCommand(ms_showExpenseCmd);
            monthSelectionScreen.addCommand(HOME_COMMAND);
            monthSelectionScreen.setCommandListener(this);
        }

        display.setCurrent(monthSelectionScreen);
    }

    private void showExpenseTypeSaveResultScreen(String expenseType)
    {

        Form expenseTypeSaveResultScn =
            new Form("Expense Type Saved", new Item[]{new SimpleCustomItem("", welcomeCanvas
                .getWidth(), welcomeCanvas.getHeight(), "thumpsup.png", "Wanna Add More?")});
        atr_addMoreCmd = new Command("Yes", Command.ITEM, 2);
        expenseTypeSaveResultScn.addCommand(atr_addMoreCmd);

        expenseTypeSaveResultScn.addCommand(HOME_COMMAND);
        expenseTypeSaveResultScn.setCommandListener(this);

        if (expenseType != null && expenseType.trim().length() > 0)
        {
            try
            {
                StorageHelper.addExpenseType(expenseType);
            }
            catch (Exception e)
            {
                showErrorOnExit(e.getMessage(), e);

            }
        }

        display.setCurrent(expenseTypeSaveResultScn);
    }

    public void showAddExpenseScreen()
    {

        String[] expenseTypes = null;
        try
        {
            expenseTypes = StorageHelper.getExpenseTypes();
        }
        catch (StorageException e)
        {
            showErrorOnExit("Error Getting Expense types", e);
        }
        if (expenseTypes == null)
        {
            Alert alert =
                new Alert("Add Expense Types First", "Add Expense Types first", null,
                    AlertType.WARNING);
            display.setCurrent(alert, display.getCurrent());
        }
        else
        {
            expenseTypeGroup =
                new ChoiceGroup("Expense Type", ChoiceGroup.POPUP, expenseTypes, null);
            expenseDateField = new DateField("Date", DateField.DATE);
            expenseDateField.setDate(new Date());
            amountField = new TextField("Amount", "", 6, TextField.DECIMAL);

            Item[] items = new Item[]{expenseTypeGroup, expenseDateField, amountField};
            addExpenseScn = new Form("Add Expense", items);
            ae_saveExpenseCmd = new Command("Save Expense", Command.ITEM, 2);
            addExpenseScn.addCommand(ae_saveExpenseCmd);
            addExpenseScn.addCommand(HOME_COMMAND);
            addExpenseScn.setCommandListener(this);

            display.setCurrent(addExpenseScn);

        }
    }

    public void showMonthlyReportsScreen(ExpensesBean expensesBean)
    {
        monthlyReportsScn = new Form("Monthly Report");

        CustomItem i =
            new ReportsCustomItem("", expensesBean, welcomeCanvas.getWidth(), welcomeCanvas
                .getHeight());
        monthlyReportsScn.append(i);
        de_exportExpensesCmd = new Command("Export", Command.ITEM, 2);
        monthlyReportsScn.addCommand(de_exportExpensesCmd);
        monthlyReportsScn.addCommand(HOME_COMMAND);
        monthlyReportsScn.setCommandListener(this);

        display.setCurrent(monthlyReportsScn);
    }

    public void showEditExpenseScreen()
    {

        if (modifyExpenseSelectionScn == null)
        {
            Calendar c = Calendar.getInstance();
            monthSelection =
                new ChoiceGroup("Select Month", ChoiceGroup.POPUP,

                new String[]{"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"}, null);
            monthSelection.setSelectedIndex(c.get(Calendar.MONTH), true);

            yearSelection =
                new ChoiceGroup("Select Year", ChoiceGroup.POPUP,

                new String[]{"" + (c.get(Calendar.YEAR) - 1), "" + c.get(Calendar.YEAR),
                    "" + (c.get(Calendar.YEAR) + 1)}, null);
            yearSelection.setSelectedIndex(1, true);
            Item[] monthItem = new Item[]{monthSelection, yearSelection};

            modifyExpenseSelectionScn = new Form("Modify Expense", monthItem);

            me_modifyExpenseCmd = new Command("Edit", Command.ITEM, 2);
            modifyExpenseSelectionScn.addCommand(me_modifyExpenseCmd);
            modifyExpenseSelectionScn.addCommand(HOME_COMMAND);
            modifyExpenseSelectionScn.setCommandListener(this);
        }
        display.setCurrent(modifyExpenseSelectionScn);
    }

    public void showResetCofirmationScn()
    {

        if (resetConfirmationScn == null)
        {

            resetConfirmationScn =
                new Form(
                    "Are you Sure?",
                    new Item[]{new StringItem(
                        "Resetting the application will remove all the settings you "
                            + "have made in addition to your expenses stored till date. "
                            + "However this will not remove the reports exported to your storage already."
                            + "Are you SURE to reset??", "")});

            re_ConfirmCmd = new Command("Yes", Command.ITEM, 2);
            re_IgnoreCmd = new Command("No", Command.CANCEL, 2);
            resetConfirmationScn.addCommand(re_ConfirmCmd);
            resetConfirmationScn.addCommand(re_IgnoreCmd);
            resetConfirmationScn.setCommandListener(this);
        }
        display.setCurrent(resetConfirmationScn);
    }

    private void showStorageSelectionResultScreen()
    {

        String storageDir = storageLocation.getString(storageLocation.getSelectedIndex());
        try
        {
            StorageHelper.setStorageLocation(storageDir);
        }
        catch (StorageException e)
        {
            showErrorOnExit("Error in setting the storage location" + e.getMessage(), e);
        }
        if (storageSettingResultScn == null)
        {

            storageSettingResultScn =
                new Form("Storage selection Saved", new Item[]{new StringItem(null,
                    "Storage Selection " + storageDir + " Set Successfully")});
            storageSettingResultScn.addCommand(HOME_COMMAND);
            storageSettingResultScn.setCommandListener(this);

        }
        StringItem item = (StringItem) storageSettingResultScn.get(0);
        item.setText("Storage Location  " + storageDir + " set Successfully");

        display.setCurrent(storageSettingResultScn);

    }

    public void showSettingsScreen()
    {
        if (settingsScn == null)
        {
            settingsScn = new SettingsCanvas(this);
            settingsScn.setFullScreenMode(true);

        }
        display.setCurrent(settingsScn);

    }

    public void showAddExpenseTypeScreen()
    {
        expenseTypeField.setString("");

        if (expenseTypeScn == null)
        {
            Item[] items = new Item[]{expenseTypeField};

            expenseTypeScn = new Form("Add Expense Type", items);
            aet_saveExpenseTypeCmd = new Command("Save", Command.ITEM, 2);
            expenseTypeScn.addCommand(aet_saveExpenseTypeCmd);
            expenseTypeScn.addCommand(HOME_COMMAND);
            expenseTypeScn.setCommandListener(this);
        }
        display.setCurrent(expenseTypeScn);

    }

    public void displayHelp()
    {

        if (helpScn == null)
        {

            StringItem newExpense =
                new StringItem("To Add a new Expense", " Home --> Manage Exp --> Add Expense");
            StringItem removeExpense =
                new StringItem("To remove a wrong Exp", " Home --> Manage Exp --> Edit Expense");
            StringItem expTypes = new StringItem("Not Happy with default expense Types?", "");

            StringItem addExpTypes =
                new StringItem("Add new Expense Type", "Home --> Settings --> Add Expsnse Type");
            StringItem removeExpTypes =
                new StringItem("Remove existing Expense Types",
                    "Home --> Settings --> Remove Expense Types");
            StringItem sysInfo =
                new StringItem("System Info pertain to this Implementation",
                    "Home --> Settings --> System Info");

            StringItem startAgain =
                new StringItem("Want to start over again??",
                    "Home --> Settings --> Reset Application");
            StringItem storeReports =
                new StringItem(
                    "Want to preserve your expense reports outside MET? Ensure that you chose Memory Card location, otherwise you may find permission issues."
                        + "    You will find the exported files under <Storage>/MET",
                    "Home --> Settings --> Set storage");

            // helpScn = new Form("MET Help!", new Item[]{thanks, newExpense,
            // removeExpense, expTypes, addExpTypes, removeExpTypes, sysInfo,
            // startAgain, storeReports});
            helpScn =
                new Form("MET Help!", new Item[]{
                    new HelpCustomItem("", welcomeCanvas.getWidth(), 80), newExpense,
                    removeExpense, expTypes, addExpTypes, removeExpTypes, sysInfo, startAgain,
                    storeReports});
            helpScn.addCommand(HOME_COMMAND);
            helpScn.setCommandListener(this);
        }
        display.setCurrent(helpScn);

    }

    private void showErrorOnExit(String message, Throwable throwable)
    {

        throwable.printStackTrace();
        // Create and display the lcdui alert
        Alert alert = new Alert("Error");
        alert.setString(message);
        alert.setType(AlertType.ERROR);
        alert.setCommandListener(this);
        alert.addCommand(EXIT_COMMAND);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);

    }
}