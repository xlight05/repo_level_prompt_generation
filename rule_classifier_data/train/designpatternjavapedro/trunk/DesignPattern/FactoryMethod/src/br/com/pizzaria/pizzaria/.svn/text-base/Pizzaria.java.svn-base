package br.com.pizzaria.pizzaria;

import br.com.pizzaria.pizzas.PizzasInterface;
/**
 * Implementacao do Padrao Factory Method
 * Este pattern atua encapsulando o criacao de Objetos deixando as subclasses decidirem
 * quais objetos criar
 * Pizzaria depende de um produnto abstrato ( Pizza no caso ) 
 * As pizzas sao produzidas na subclasse  . 
 * Os produtos sao escolhidos em tempo de execucao e depende de qual subclasse foi escolhidar
 * 
 * Inversao de Dependencia
 * Classes de alto nivel ( Pizzaria )  nao podem depender de componentes de baixo nivel ( Pizzas )
 * Deve se abstrair as pizzas em uma interface
 * Boa Pratica = programe sempre para interfaces /classes abstratas.  
 * 
 * Dicas OO
 * Nenhuma variavel deve conter uma referencia a uma classe concreta ( Boa pratica , nao verdade universal )
 * Nao substituir implementacoes da classe base 
 * @author Pedro
 *
 */
public abstract class Pizzaria {
	/**
	 * Este metodo atua como uma fabrica de pizza , sem precisar ser instanciado
	 * È delegado para as subclasses implementar esse metodo
	 * @param pizza
	 * @return
	 */
	public abstract PizzasInterface criaPizza(String pizza);

	public PizzasInterface facaPizza(String sabor){
		
		/**
		 * A classe pizzaria nao depende nenhuma implementacao de pizza
		 * Utiliza apenas a interface de pizzas . Baixo Acoplamento.
		 */
	 PizzasInterface pizza=this.criaPizza(sabor);
	 pizza.tempera();
	 pizza.poemNoForno();
	 pizza.corta();
	 pizza.caixa();
		 
		
		
		
		return pizza;
		
		
	}

}
