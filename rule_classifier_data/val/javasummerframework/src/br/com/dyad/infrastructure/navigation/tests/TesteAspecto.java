package br.com.dyad.infrastructure.navigation.tests;

import org.hibernate.Session;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class TesteAspecto {
	
	public static void main(String[] args) {
		try {			
			String database = "INFRA";
			Session session = PersistenceUtil.getSession(database);
						
			session.save("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
