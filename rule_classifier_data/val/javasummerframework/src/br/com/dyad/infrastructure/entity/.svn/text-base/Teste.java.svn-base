package br.com.dyad.infrastructure.entity;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.dyad.infrastructure.annotations.MetaField;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999946")
public class Teste extends BaseEntity {
	
	private String descricao;

	private String descricao2;
	
	@Temporal(TemporalType.DATE)
	private Date data;	
	
	private Double preco;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	private Date hora;
	
	@Column(nullable=true)
	private Boolean executar;
	
	private Integer sexo;
		
	public Boolean getExecutar() {
		return executar;
	}

	public void setExecutar(Boolean exec) {
		this.executar = exec;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getDescricao2() {
		return descricao2;
	}

	public void setDescricao2(String descricao2) {
		this.descricao2 = descricao2;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField(
				"id", 
				MetaField.visible, true, 
				MetaField.order, 0
		);

		this.defineField(
				"hora", 
				MetaField.tableViewVisible, false, 
				MetaField.order, 0,
				MetaField.required, true
		);

		this.defineField(
				"descricao", 
				MetaField.label, "Desc"
		);

		this.defineField(
				"sexo", 
				MetaField.tableViewVisible, false, 
				MetaField.order, 100,
				MetaField.required, false,
				MetaField.options, Teste.sexoOptions
		);
	}
	
	@SuppressWarnings("unchecked")
	public static final transient ArrayList sexoOptions = BaseEntity.getOptions( 1, "Feminino", 2, "Masculino" );

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}
}

