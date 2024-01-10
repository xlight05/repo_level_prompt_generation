package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue(value="-89999999999838")
public class Entidade extends BaseEntity {

	private String codigo;
	private String nome;
	
	public String toString(){
		return this.getCodigo() + " - " + this.getNome();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
