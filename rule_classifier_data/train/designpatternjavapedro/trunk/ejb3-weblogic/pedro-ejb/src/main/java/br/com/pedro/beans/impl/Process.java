package br.com.pedro.beans.impl;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.pedro.beans.ProcessPayment;




@Stateless
public class Process implements ProcessPayment {

	@Resource private EJBContext session;
	
	
	public void doSomthing(String aux) {
		Object obj= session.lookup("ejb/cabin");
		System.out.println(obj.getClass().toString());
		
	}
	
	

}
