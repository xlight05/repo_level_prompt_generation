package br.com.pizzaria.pizzaria;

import br.com.pizzaria.pizzas.PizzaCalabresaCampinas;
import br.com.pizzaria.pizzas.PizzaCalabresaRioDeJaneiro;
import br.com.pizzaria.pizzas.PizzasInterface;
/**
 * Exemplo de uma classe altamente acoplada
 * Esta classe depende de objetos pizzas porque esta criando os produtos 
 * concretos diretamente.
 * Para cada tipo de pizza nova , mais uma nova dependencia sera criada
 * @author Pedro
 *
 */
public class PizzariaDependente {
	PizzasInterface pizza=null;
	
		public  PizzasInterface createPizza(String local,String tipo){
			if(local.equals("campinas")){
				if(tipo.equals("Calabresa" )){
					pizza=new PizzaCalabresaCampinas();
				}else if(tipo.equals("Peperone" )){
					pizza=	new PizzaCalabresaCampinas();
			}else if (local.equals("Rio de Janeiro")) {
				if(tipo.equals("Calabresa" )){
					pizza=new PizzaCalabresaRioDeJaneiro();
				}else if(tipo.equals("Peperone" )){
					pizza=new PizzaCalabresaRioDeJaneiro();
			}}
				

			}
			pizza.tempera();
			pizza.poemNoForno();
			return pizza;
		}
}
