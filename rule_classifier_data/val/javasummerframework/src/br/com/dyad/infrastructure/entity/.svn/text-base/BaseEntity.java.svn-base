package br.com.dyad.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.dyad.infrastructure.annotations.Authorization;

/**
 * 
 * Esta classe deve ser a classe de origem para todos os beans que trabalham com todo o padrão de campos (id automatico, versao, classId, lastTransactionId).
 * É interessante notar que podemos criar dataLists e grid para entidades que são AbstractEntity. Para mais detalhes, ver AbstractEntity.
 * @see br.com.dyad.infrastructure.entity.AbstractEntity
 * @author Helton Gonçalves. 
 */
@Authorization
@MappedSuperclass
@DiscriminatorValue(value="-89999999999839")
public abstract class BaseEntity extends AbstractEntity {		
	@Id	
	protected Long id;

	@Column(name = "CLASSID",insertable=false,updatable=false)
	private String classId;

	private Long lastTransaction;
		
	/**
	 * Guarda a licença do registro.
	 */
	private Long licenseId;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Long getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(Long lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	/**
	 * Retorna a licença do registro.
	 */
	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}
}
