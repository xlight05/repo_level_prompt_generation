package br.com.dyad.infrastructure.navigation.tests;

public class TesteThreadAspect {
	
	public static void main(String[] args) {
		System.out.println("Local thread 1: " + Thread.currentThread().getId());
		Thread thread = new Thread(){
			@Override
			public void run() {
				System.out.println("Local thread 2: " + Thread.currentThread().getId());
				System.out.println("Teste");
			}
		};
		
		thread.start();
	}

}
