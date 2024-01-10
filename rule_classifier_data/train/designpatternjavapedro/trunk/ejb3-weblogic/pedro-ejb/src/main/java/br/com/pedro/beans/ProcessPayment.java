package br.com.pedro.beans;

import javax.ejb.Remote;

@Remote
public interface ProcessPayment {
	public void doSomthing(String aux);
}
