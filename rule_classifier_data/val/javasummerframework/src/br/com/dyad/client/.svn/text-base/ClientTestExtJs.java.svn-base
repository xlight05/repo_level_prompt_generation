package br.com.dyad.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

public class ClientTestExtJs extends Window{
	public ClientTestExtJs() {
		super();
    	
		// 1) Window config
		this.setHeading( "Example" );
		this.setLayout(new FitLayout());
	    this.setSize(500, 500);
	    this.setMinimizable(true);
	    this.setMaximizable(true);
	    this.setCollapsible(true);
	    this.setScrollMode(Scroll.AUTO);	    	    
	    
	    // 3) Create a frame (FormPanel with TableLayout) and add to main panel
		TableLayout tableLayout = new TableLayout();
		tableLayout.setColumns(1);
	    FormPanel formFrame = new FormPanel();
	    formFrame.setLayout(tableLayout);
	    
	    this.add( formFrame );
	    
	    TableData col = new TableData();
		col.setColspan(1);
		col.setPadding(5);
		
		formFrame.add( ClientTestExtJs.getEditorGridExample(), col ); //here I can add this grid without problems.
	
		// 5) Add a tab manager and a tab item in formFrame.

		//tab manager
		TabPanel tabs = new TabPanel();  
		tabs.setDeferredRender(false); 
		tabs.setWidth(400);   

		//a tab
		TabItem tabItem = new TabItem();  
		tabItem.setText( "Tab Item 1" );  
		tabItem.setWidth(200);
		tabItem.setHeight(200);

		// a form inside the tab
		TableLayout tableLayout2 = new TableLayout();
		tableLayout2.setColumns(1);
	    FormPanel formFrameTab = new FormPanel();
	    formFrameTab.setLayout(tableLayout2);

	    TableData col2 = new TableData();
		col.setColspan(1);
		col.setPadding(5);
	    
		//formFrameTab.add( ClientTestExtJs.getEditorGridExample(), col2 ); //here causes the PROBLEM! i cant add a editorgrid in formPanel inside a tabitem.
	    
		tabItem.add( formFrameTab );

		// add the tabitem
		tabs.add(tabItem);     

		// add the tab manager to formFrame
		TableData column2 = new TableData();
		col.setColspan(1);
		
		formFrame.add(tabs, col);
	}
	
	
	
	//Just a method that returns a EditorGrid used in this example.
	public static EditorGrid<ModelData> getEditorGridExample(){	    
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	       
	    ColumnConfig column = new ColumnConfig();  
	    column.setId("name");  
	    column.setHeader("Common Name");  
	    column.setWidth(220);  
	       
	    TextField<String> text = new TextField<String>();  
	    text.setAllowBlank(false);  
	    column.setEditor(new CellEditor(text));  
	    configs.add(column);  
	       
	    final SimpleComboBox<String> combo = new SimpleComboBox<String>();  
	    combo.setForceSelection(true);  
	    combo.setTriggerAction(TriggerAction.ALL);  
	    combo.add("Shade");  
	    combo.add("Mostly Shady");  
	    combo.add("Sun or Shade");  
	    combo.add("Mostly Sunny");  
	    combo.add("Sunny");  
	    
	    column = new ColumnConfig();  
	    column.setId("light");  
	    column.setHeader("Light");  
	    column.setWidth(130);  
	    //column.setEditor(editor);  
	    configs.add(column);  
	    
	    column = new ColumnConfig();  
	    column.setId("price");  
	    column.setHeader("Price");  
	    column.setAlignment(HorizontalAlignment.RIGHT);  
	    column.setWidth(70);  
	    column.setNumberFormat(NumberFormat.getCurrencyFormat());  
	    column.setEditor(new CellEditor(new NumberField()));  
	       
	    configs.add(column);  
	       
	    DateField dateField = new DateField();  
	    dateField.getPropertyEditor().setFormat(DateTimeFormat.getFormat("MM/dd/y"));  
	       
	    column = new ColumnConfig();  
	    column.setId("available");  
	    column.setHeader("Available");  
	    column.setWidth(95);  
	    column.setEditor(new CellEditor(dateField));  
	    column.setDateTimeFormat(DateTimeFormat.getMediumDateFormat());  
	    configs.add(column);  
	       
	    CheckColumnConfig checkColumn = new CheckColumnConfig("indoor", "Indoor?", 55);  
	    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
	    checkColumn.setEditor(checkBoxEditor);  
	    configs.add(checkColumn);  
	     
	    final ListStore<ModelData> store = new ListStore<ModelData>();  
	    	    
	    ColumnModel cm = new ColumnModel(configs);  
	    
	    
	    final EditorGrid<ModelData> grid = new EditorGrid<ModelData>(store, cm);  
	    grid.setBorders(true);  
	    grid.addPlugin(checkColumn);  
	    
	    return grid;
	}		
}