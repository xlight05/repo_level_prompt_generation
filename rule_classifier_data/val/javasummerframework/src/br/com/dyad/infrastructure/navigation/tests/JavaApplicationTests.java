package br.com.dyad.infrastructure.navigation.tests;

import java.util.Iterator;
import java.util.List;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;

public class JavaApplicationTests {
	public static void main(String[] args) {		
		try {

			DataList dt = DataListFactory.newDataList("INFRA");

			dt.executeQuery("from NavigatorEntity where classId = '" + BaseEntity.getClassIdentifier(NavigatorEntity.class) + "'");

			//dt.getKeySetFromGroupIndex("name");

			BaseEntity root = (BaseEntity)dt.getObjectById( -89999999999989L );
			
			List<BaseEntity> navis = (List<BaseEntity>)dt.getRange("parent", root );

			System.out.println( navis.size() );
			
			for (Iterator iterator = navis.iterator(); iterator.hasNext();) {
				NavigatorEntity nav = (NavigatorEntity) iterator.next();
				System.out.println(nav.getId() + " - " + nav.getName() );
			}
			
			System.out.println( dt.getList().size() );

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
