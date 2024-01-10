package br.com.pedro.carros.cliente;

import br.com.pedro.carros.componentes.Farol;
import br.com.pedro.carros.componentes.Motor;
import br.com.pedro.carros.componentes.TocaCD;
import br.com.pedro.carros.facade.CarroFacade;

/**
 * A principal ideia do facade eh fornecer uma interface
 * simplificada para o cliente ( No caso Carro Facade ) .
 * A classe facade que trata os componentes do sub sistema 
 * nao o proprio cliente 
 * 
 * A diferenca entre Facade e Adapter esta na intencao .
 * O adapter altera uma interface para tornar-la compativel com  o que
 * o cliente esta esperando . 
 * A intencao do facade eh gerar uma interface simplificada .
 * 
 * Lembrando que o cliente ainda deve saber as funcionalidades ainda ( motor , farol , tocacd)
 * As fachadas nao encapsulam as classes do subsistema.
 * @author Pedro
 *
 */
public class TestFacade {

	
	public static void main(String[] args) {
		CarroFacade carrofacade=new CarroFacade(new Farol(), new Motor(), new TocaCD());
		carrofacade.ligaDispositivos();
		carrofacade.posLigar();
		carrofacade.desligar();

	}

}
