package br.com.dyad.infrastructure.i18n;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.AppException;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class TranslationWindow extends GenericEntityBeanWindow {
	
	public TranslationWindow(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		
		this.setBeanName( Translation.class.getName() );
		
		super.defineWindow();
		
		this.grid.addWidgetListener(DyadEvents.onBeforePost, new WidgetListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleEvent(Object sender) throws Exception {
				
				DataGrid grid = (DataGrid)sender;				
				if ( grid.getEditionMode() != null && grid.getEditionMode().equals(DataGrid.EDITION_MODE_INSERT) ){
					DataList dataList = grid.getDataList();

					Object language = grid.getFieldByName("language").getValue();
					Object code = grid.getFieldByName("code").getValue();
					Object id = grid.getFieldByName("id").getValue();

					String index = "language;code";

					if ( dataList.find( index, language, code ) ){
						ArrayList<Translation> tanslations = (ArrayList<Translation>)dataList.getRange(index, language, code);					
						for (Iterator iterator = tanslations.iterator(); iterator.hasNext();) {
							Translation translation = (Translation) iterator.next();						
							if ( ! id.equals( translation.getId() ) ){
								throw new AppException( "Code already used for this language." );
							}						
						}
					}
				}	
			}			
		});			
	}

}
