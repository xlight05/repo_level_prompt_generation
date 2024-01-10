package br.com.dyad.infrastructure.navigation;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.GenericService;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.UserShortcut;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class Preferences extends Window{

	public InteractionShowPreferences showPreferences;
	public DataList dataList = DataListFactory.newDataList(getDatabase());
	public DataGrid grid = null;
	public Long userKey = (Long)getHttpSession().getAttribute(GenericService.USER_KEY);

	public Preferences(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		this.showPreferences = new InteractionShowPreferences(this,"");

		String query = "from UserShortcut where userid = " + userKey + " and classId = '"
					   + BaseEntity.getClassIdentifierByClassName( UserShortcut.class.getName() ) + "'";

		dataList.executeQuery(query);
		dataList.setCommitOnSave(true);

		grid = new DataGrid(this, "dataGrid");	
		grid.setBeanName(UserShortcut.class.getName());
		grid.setDataList(dataList);
		grid.setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);		
		grid.setTitle("Shortcuts");
		grid.setCanInsert(false);

		add(this.showPreferences);
	}

	public class InteractionShowPreferences extends Interaction {
		public InteractionShowPreferences(Window window, String string) {
			super(window, string);
		}
		@Override
		public void defineInteraction() throws Exception {
			Preferences process = (Preferences) this.getWindow();
			this.add(grid);
		}
	}
}
