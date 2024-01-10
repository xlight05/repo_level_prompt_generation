package br.com.dyad.infrastructure.navigation.tests;

import br.com.dyad.infrastructure.annotations.Test;
import br.com.dyad.infrastructure.unit.TestCase;

public class TesteJUnit extends TestCase{

	@Test
	public void teste1() {
		assertTrue(123 == 123);
	}
	
	@Test
	public void teste2() {
		assertTrue(123 == 124);
	}
	
	@Test
	public void teste3() {
		assertTrue(soma(1, 2) == 3);
	}	
	
	private int soma(int a, int b) {
		return a + b;
	}
	
	public static void main(String[] args) {
		TesteJUnit teste = new TesteJUnit();
		teste.teste1();
	}
	
}
