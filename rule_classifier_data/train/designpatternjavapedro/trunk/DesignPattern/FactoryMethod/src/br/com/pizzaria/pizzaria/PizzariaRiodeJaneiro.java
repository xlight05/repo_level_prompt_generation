package br.com.pizzaria.pizzaria;


import br.com.pizzaria.pizzas.PizzaCalabresaRioDeJaneiro;
import br.com.pizzaria.pizzas.PizzasInterface;

public class PizzariaRiodeJaneiro extends Pizzaria{

	

	@Override
	public PizzasInterface criaPizza(String pizza) {
		if(pizza.equals("Calabresa")){
			return  new PizzaCalabresaRioDeJaneiro();
		}
	
		return null;
	}

	
}
