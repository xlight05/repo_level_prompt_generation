package br.com.dyad.infrastructure.navigation.utilities;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.DataClass;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldClassLookup;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class DataExplorer extends Window {

	public DataExplorer(HttpSession httpSession) {
		super(httpSession);
	}

	public Action actShowData;
	public Action actShowVariables;

	private String beanName;
	private Long idToFind;

	public InteractionShowVariables showVariables;
	public InteractionShowData showData;

	public VariableGrid grdVariables = null;

	public DataList dataList = DataListFactory.newDataList(getDatabase());
	public DataGrid grid = null;// new DataGrid( "dataGrid" );

	@Override
	public void defineWindow() throws Exception {			
		this.setHelp("This process is a general feature and manipulates all entities from this application.");

		actShowData = new Action( this, "Show Data") {
			public void onClick() throws Exception {
				DataExplorer process = (DataExplorer) this.getParent();
				DataClass bean = (DataClass) process.grdVariables.getFieldByName("class").getValue();
				System.out.println("bean: " + bean);
				process.setBeanName(bean.getClassName());
				process.setNextInteraction(showData);
			}
		};

		actShowVariables = new Action( this, "Show Variables") {
			public void onClick() throws Exception {
				DataExplorer process = (DataExplorer) this.getParent();
				process.setNextInteraction(showVariables);
			}
		};		
		
		grdVariables = new VariableGrid(this, "variableGrid"){
			FieldClassLookup field;
			@Override
			public void defineGrid() throws Exception {		
				field = new FieldClassLookup( this, "class" );
				field.setBeanName("br.com.dyad.infrastructure.entity.BaseEntity");
				field.setIsClassLookup(true);
				field.setLabel("Class");
				field.setRequired(true);
			}
		};
		
		add(this.getShowVariables());
		add(this.getShowData());
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Long getIdToFind() {
		return idToFind;
	}

	public void setIdToFind(Long idToFind) {
		this.idToFind = idToFind;
	}

	public Interaction getShowVariables() {
		if (this.showVariables == null) {
			this.showVariables = new InteractionShowVariables(this, "showVariables");
		}
		return (Interaction) this.showVariables;
	}

	public Interaction getShowData() {
		if (this.showData == null) {
			this.showData = new InteractionShowData(this, "showData");
		}
		return (Interaction) this.showData;
	}

	public class InteractionShowVariables extends Interaction {
		public InteractionShowVariables(Window window, String string) {
			super(window, string);
		}

		@Override
		public void defineInteraction() throws Exception {
			grdVariables.setTitle("Parameters");
			this.getWindow().enableAndShowActions("Show Data");
			this.add(grdVariables);
		}

	}

	public class InteractionShowData extends Interaction {
		public InteractionShowData(Window window, String string) {
			super(window, string);
		}
		
		@Override
		public void defineInteraction() throws Exception {
			this.getWindow().enableAndShowActions(actShowVariables);
			DataExplorer process = (DataExplorer) this.getWindow();

			String query = "from "
					+ process.getBeanName()
					+ " where classId = '"
					+ BaseEntity.getClassIdentifierByClassName(process.getBeanName()) + "'";

			dataList.executeQuery(query);
			dataList.setCommitOnSave(true);

			if ( grid != null ){
				this.remove( grid );
			}
			
			grid = new DataGrid(this.getWindow(), "dataGrid");		
			grid.setHelp("This grid is a general grid to show entities from a class.");

			grid.setBeanName(process.getBeanName());
			grid.setDataList(dataList);
			
			String processTitle = ReflectUtil.getSingleNameFromClassName( process.getBeanName() );
			
			grid.setTitle( Field.getLabelByName( processTitle ) );
			grid.setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);

			this.add(grid);
		}
	}
}