package br.com.dyad.infrastructure.navigation.tests;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.persistence.DataListFactory;

public class TesteDataList {
	
	public static void main(String[] args) {
		DataList data = DataListFactory.newDataList("INFRA");
		data.executeQuery("from User");
		
		try {
			Boolean result = data.find("language.name", "Portuguese - Brazil");
			System.out.println("Result=" + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
