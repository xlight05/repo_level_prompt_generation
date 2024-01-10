package br.com.pedro.test;

import br.com.pedro.adapter.CaminhoneteAdapter;
import br.com.pedro.automoveis.Caminhonete;
import br.com.pedro.automoveis.CaminhoneteInterface;
import br.com.pedro.automoveis.Carro;
import br.com.pedro.automoveis.CarroInterface;

/**
 * O Padrao adpapter em suma converte uma interface alvo em outra interface esperada
 * pelo cliente 
 * Permitindo assim , compatibilidade entre as interfaces
 * @author Pedro
 *
 */
public class Test {

	
			public static void main(String[] args){
				// Instanciando um carro
				CarroInterface c1= new Carro();
				System.out.print("Acelerando um carro : ");
				c1.acelera();
				
				// Instanciando uma caminhonete
				CaminhoneteInterface  caminhonete= new Caminhonete();				
				System.out.print("Acelerando uma caminhonete : ");
				caminhonete.acelera();
				
				//Tratando um Carro como se fosse uma caminhonete atraves do adapter
				// O cliente so ve a interface alvo ( CaminhoneteInterface )
				//  O adaptador implementa a interface alvo
				// CaminhoneteAdapter usa composicao de Carro
				CaminhoneteInterface carroadaptado=new CaminhoneteAdapter(new Carro());
				System.out.print("Acelerando um carro adaptado para caminhonete :");
				carroadaptado.acelera();
			}
}
