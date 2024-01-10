package br.com.pedro.automoveis;

public class Caminhonete implements CaminhoneteInterface{

	@Override
	/**
	 * Assumindo que caminhonete acelera devagar
	 */
	public void acelera() {
		System.out.println("Acelerando lentamente");		
	}

}
