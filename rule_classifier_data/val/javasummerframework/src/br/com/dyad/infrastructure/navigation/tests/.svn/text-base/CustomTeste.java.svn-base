package br.com.dyad.infrastructure.navigation.tests;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.dyad.infrastructure.annotations.Customize;
import br.com.dyad.infrastructure.annotations.MetaField;

@Customize(clazz=EntityTemporario.class)
public class CustomTeste extends EntityTemporario {	
	@Temporal(TemporalType.TIME)
	private Date hora;
	private Long upload;
	
	public Long getUpload() {
		return upload;
	}

	public void setUpload(Long upload) {
		this.upload = upload;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public CustomTeste() {
	}
	
	public CustomTeste(String teste) {		
	}
	
	public void mostraMensagem() {
		System.out.println("Mensagem na classe ABCBCBCBC"); 
	}
	
	public void mostraMensagem2() {
		System.out.println("123"); 
	}	
	
	private String propriedadeTeste;

	public String getPropriedadeTeste() {
		return propriedadeTeste;
	}
	
	public static String teste(){
		return "método de teste";
	}
	
	public static String abc(){
		return "ABC";
	}

	public void setPropriedadeTeste(String propriedadeTeste) {
		this.propriedadeTeste = propriedadeTeste;
	}
	
	@Override
	public void defineFields() {		
		this.defineField(
				"upload",  
				MetaField.picture, true
		);
		super.defineFields();
	}
		
}
