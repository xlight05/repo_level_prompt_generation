package br.com.dyad.infrastructure.navigation.tests;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.TreeGrid;

public class TesteTreeGrid extends Window {

	public TesteTreeGrid(HttpSession httpSession) {
		super(httpSession);
	}

	private String beanName = br.com.dyad.infrastructure.entity.NavigatorEntity.class.getName();
	private Long entityId = -89999999999989L; /* Navigation */
	private Long idToFind;

	public InteractionShowGrid showGrid;

	public TreeGrid grd = null;

	public DataList dataList = DataListFactory.newDataList(getDatabase());

	@Override
	public void defineWindow() throws Exception {			
		this.setHelp("Esse process Ã© um exemplo de uso de TreeGrid.");	
		
		/**
		 * this.entityId: id do root
		 * "id": propriedade do bean que vincula o pai aos filhos
		 * "parent": propriedade do bean que vincula o filho ao pai
		 * "name": campo que serÃ¡ exibida a Ã¡rvore(treeField)
		 * "TreeGrid teste": nome da grid 
		 */
		grd = new TreeGrid(this, this.getBeanName(), this.entityId, "id", "parent", "name", "TreeGrid teste"){
			@Override
			public List<BaseEntity> onExpand( String beanName, BaseEntity parent ) throws Exception {								
				/**
				 * Este mÃ©tido deve ser implementado para definir a forma de recurperar os filhos de um nÃ³ na treegrid. 
				 * Se nÃ£o for implementado serÃ¡ usado a forma padrÃ£o, que funcionada da seguinte forma:
				 * pega todos os registros da classe informada no construtor da grid(beanName), cujo o parentId Ã© igual ao parent 
				 */
				DataList dataList = DataListFactory.newDataList(getDatabase());
				String query = "from " + beanName + " where parentId = " + parent.getId();
				dataList.executeQuery(query);
				dataList.setCommitOnSave(true);		
				
				return dataList.getList();					
			}
		};	
		grd.setLeafField("window");
		add(this.getShowGrid());
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

	public Interaction getShowGrid() {
		if (this.showGrid == null) {
			this.showGrid = new InteractionShowGrid(this, "showGrid");
		}
		return (Interaction) this.showGrid;
	}

	public class InteractionShowGrid extends Interaction {
		public InteractionShowGrid(Window window, String string) {
			super(window, string);
		}

		@Override
		public void defineInteraction() throws Exception {
			TesteTreeGrid process = (TesteTreeGrid) this.getWindow();			
			String query = "from "
					+ process.getBeanName() 
					+ " where id = " + entityId; 

			dataList.executeQuery(query);
			dataList.setCommitOnSave(true);
			grd.setTitle("TreeGrid");
			grd.setDataList(dataList);
			this.add(grd);
		}
	}
}