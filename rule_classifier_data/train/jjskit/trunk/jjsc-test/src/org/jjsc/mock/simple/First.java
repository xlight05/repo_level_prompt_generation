package org.jjsc.mock.simple;

public class First {
	private String name;
	private int data;
	
	First(String name){
		this.name = name;
		this.data = getCalculated(name.length());
	}
	
	public First(int data){
		this("");
		this.data = data;
		System.out.println("WARN - name is absent");
	}
	
	private int getCalculated(int param){
		int rez = param +1;
		rez = name.length()/rez;
		return rez;
	}
	
	public int getData(){
		return data;
	}
}
