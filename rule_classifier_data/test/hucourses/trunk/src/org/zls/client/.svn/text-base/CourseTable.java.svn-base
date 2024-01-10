package org.zls.client;

import com.google.gwt.user.client.Window;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;

public class CourseTable extends GridPanel {

	protected static BaseColumnConfig[] columns = new BaseColumnConfig[] {

	new ColumnConfig("State Abbreviation", "abbr", 100, true, null, "abbr"),
	new ColumnConfig("State Name", "state", 50, true, null, "state"),
	new ColumnConfig("Blah", "blah", 100, true, null, "blah")};

	private static RecordDef recordDef = new RecordDef(new FieldDef[] {
			new StringFieldDef("abbr"), new StringFieldDef("state") });

	protected Store store;

	public CourseTable() {
		Object[][] data = new Object[][] { new Object[] { "AL", "Alabama" },
				new Object[] { "AK", "Alaska", "1" },
				new Object[] { "AZ", "Arizona", "2"},
				new Object[] { "AR", "Arkansas", "3"},
				new Object[] { "CA", "California", "4"} };

		MemoryProxy proxy = new MemoryProxy(data);
		
		ArrayReader reader = new ArrayReader(recordDef);
		
		store = new Store(proxy, reader);
		store.load();
		setStore(store);
		
		setColumnModel(getColumnConfigs());
		setFrame(true);
		setLoadMask("Loading...");
		setAutoExpandColumn("state");
	}
	
	public void refresh() {
		store.removeAll();
		store.commitChanges();
		Window.alert("Whatup, bitches");
		
	}
	
    protected ColumnModel getColumnConfigs() {
        ColumnModel columnModel = null;
        if (columns == null) {
            columnModel = new ColumnModel(columns);
        } else {
            BaseColumnConfig[] columnConfigs = new BaseColumnConfig[columns.length];
            for (int i = 0; i < columns.length; i++) {
                columnConfigs[i] = columns[i];
            }
            columnModel = new ColumnModel(columnConfigs);
        }
        return columnModel;
    }
}
