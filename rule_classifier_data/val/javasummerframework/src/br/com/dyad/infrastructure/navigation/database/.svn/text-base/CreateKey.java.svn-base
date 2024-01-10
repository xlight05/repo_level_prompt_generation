package br.com.dyad.infrastructure.navigation.database;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.KeyGenerator;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.BaseAction;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class CreateKey extends Window {
	
	public HashMap<String, Long> lics = new HashMap<String, Long>();
	
	public CreateKey(HttpSession httpSession) {
		super(httpSession);
		try{			
			for (Field field : ReflectUtil.getClassFields(KeyGenerator.class, true)){
				if (StringUtils.startsWith(field.getName(), "KEY_RANGE_")){
					lics.put(field.getName(), (Long)field.get(null));
				}
			}
		} catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	protected VariableGrid grid = null; 
			
	
	@Override
	public void defineWindow() throws Exception {
		grid = new VariableGrid(this, "grid"){

			@Override
			public void defineGrid() throws Exception {
				setTitle("Database Backup");
				
				FieldString key = new FieldString(this);
				key.setName("key");
				key.setOrder(1);
							
				add(key);
			}
			
		};
		
		for(String l : lics.keySet()){
			final String temp = l; 
			Action create = new Action(this, temp){
				@Override
				public void onClick() throws Exception {
					Long keyValue = KeyGenerator.getInstance(getDatabase()).generateKey(lics.get(temp));
					grid.getFieldByName("key").setValue(keyValue.toString());
				}
				
			};
			add(create);
		}
		
		Interaction backup = new Interaction(this, "Backup"){
			@Override
			public void defineInteraction() throws Exception {
				for (BaseAction action : getWindow().getActions()) {					
					getWindow().enableAndShowActions( action );
				}
				add(grid);
			}			
		};
		
		add(backup);		
	}
}
