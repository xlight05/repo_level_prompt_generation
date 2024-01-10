package br.com.dyad.infrastructure.reflect;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.dyad.client.AppException;
import br.com.dyad.client.DyadBaseModel;

public class TesteReflect {
	
	private int id;
	private String teste;
	private double valor;
	private ArrayList<TesteReflect> itens;
	private HashMap<String, TesteReflect> itens2;		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeste() {
		return teste;
	}
	
	public void setTeste(String teste) {
		this.teste = teste;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public ArrayList<TesteReflect> getItens() {
		if (itens == null){
			itens = new ArrayList<TesteReflect>();
		}
		return itens;
	}
	
	public void setItens(ArrayList<TesteReflect> itens) {
		this.itens = itens;
	}
	
	public HashMap<String, TesteReflect> getItens2() {
		if (itens2 == null){
			itens2 = new HashMap<String, TesteReflect>();
		}
		return itens2;
	}
	
	public void setItens2(HashMap<String, TesteReflect> itens2) {
		this.itens2 = itens2;
	}
	
	public static void main(String[] args) throws Exception {
		TesteReflect teste = new TesteReflect();
		teste.setId(1);
		teste.setTeste("teste1");
		teste.setValor(1);
		
		TesteReflect teste2 = new TesteReflect();
		teste2.setId(2);
		teste2.setTeste("teste2");
		teste2.setValor(2);
		
		teste2.getItens().add(teste);
		teste2.getItens2().put("abc", teste);
		
		teste.getItens().add(teste2);
		
		try {
			DyadBaseModel model = ObjectConverter.getInstance().getModel(teste2, -1, null);
			System.out.println(model.get("teste"));
			
			Object bean = ObjectConverter.getInstance().getBeanFromModel(model);
			System.out.println(bean.toString());
		} catch (AppException e) {
			e.printStackTrace();
		}
		
	}
	
}
