package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.client.AppException;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.ProductLicense;
import br.com.dyad.infrastructure.navigation.admin.security.SecurityUtil;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.Grid;

public class SetFieldValue extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		System.out.println("SynchronizerService->setando o value de um field:");
		
		String serverWindowId = (String)params.get("serverWindowId");				
		String serverGridId = (String)params.get("serverGridId");
		String serverFieldId = (String)params.get("serverFieldId");
		Object value = (Object)params.get("value");
		
		System.out.println("value=" + value);
		
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverWindowId is empty.");
		}
		if ( serverGridId == null || serverGridId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverGridId is empty.");
		}
		
		if ( serverFieldId == null || serverFieldId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverFieldId is empty.");
		}
					
		Window window = getWindowMap().get(serverWindowId);
		Grid grid = window.getGridByServerObjectId(serverGridId); 
		Field field = grid.getFieldByServerObjectId(serverFieldId);
		
		if ( field.getReadOnly() ){			
			throw new Exception("Field is read-only.");					
		}
		
		//Verifica se é chave negativa para 
		Field fieldId = field.getGrid().getFieldByName("id");
		if (fieldId != null) {					
			Long idValue = (Long)fieldId.getValue();
			
			if (idValue != null && idValue < 0){
				ProductLicense license = ProductLicense.getLicenseById(getDatabase(), idValue);
				if (license != null && (field.getCanCustomizeValue() == null || !field.getCanCustomizeValue()) && !SecurityUtil.userCancustomizeLicense(getDatabase(), UserInfoAspect.getUserKey(), license.getId())) {
					throw new AppException(translate("You can't customize this field!"));
				}
			}
		}
		//
		
		field.setValue(value);
		if ( grid instanceof DataGrid ){
			((DataGrid)grid).fillFieldValuesWithCurrentEntity();
		}	
		
		System.out.println("Executou o SET_FIELD_VALUE no servidor!");

		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );
		
		return resultMap;
	}

}
