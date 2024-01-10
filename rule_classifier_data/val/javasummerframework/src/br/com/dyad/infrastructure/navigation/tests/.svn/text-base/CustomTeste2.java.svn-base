package br.com.dyad.infrastructure.navigation.tests;

import br.com.dyad.infrastructure.annotations.Customize;
import br.com.dyad.infrastructure.customization.CustomType;
import br.com.dyad.infrastructure.customization.ExecuteCustomization;

@Customize(clazz=CustomTeste.class, type=CustomType.METHOD)
public class CustomTeste2 extends CustomTeste {
	public CustomTeste2(String teste) {
		super(teste);
	}
	
	@Customize
	public static String teste(){		
		return CustomTeste.teste() + "_" + CustomTeste.abc();
	}
	
	@Customize(callSuper=false)
	public static String abc(){
		return CustomTeste.abc() + "--------";
	}
	
	@Customize(callSuper=false)
	public static void setPropriedadeTeste(Object obj, String prop){
		((CustomTeste)obj).setPropriedadeTeste("Cabeça de bode_" + prop);
		
	}
	
	public static void main(String[] args) {
		ExecuteCustomization.executeCustomization();		
		CustomTeste2 custom = new CustomTeste2("");
		custom.setPropriedadeTeste("abc");
		System.out.println(custom.teste());
	}

}
