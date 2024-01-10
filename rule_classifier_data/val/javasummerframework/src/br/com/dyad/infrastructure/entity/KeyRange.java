package br.com.dyad.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.audit.NotAuditable;

@Entity
@Table(name="SYSKEYRANGE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999983")
public class KeyRange extends BaseEntity implements NotAuditable{
	
	@Column(name = "BEGINKEY")
	private Long beginKey;
	
	@Column(name = "ENDKEY")
	private Long endKey;
	
	@Column(name = "RANGENAME", length=100)
	private String rangeName;

	@Column(name = "LASTGENERATEDKEY")
	private Long lastGeneratedKey;
	
	@Column(name = "RANGETOKEY")
	private int rangeToKey;

	private transient Boolean keyCacheReady = false;
	private transient Long cacheLastGeneretedKey;
	

	public String toString(){
		return this.getRangeName();
	}
	
	public Long getBeginKey() {
		return beginKey;
	}

	public void setBeginKey(Long beginKey) {
		this.beginKey = beginKey;
	}

	public Long getEndKey() {
		return endKey;
	}

	public void setEndKey(Long endKey) {
		this.endKey = endKey;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public Long getLastGeneratedKey() {
		return lastGeneratedKey;
	}

	public void setLastGeneratedKey(Long lastGeneratedKey) {
		this.lastGeneratedKey = lastGeneratedKey;
	}

	public int getRangeToKey() {
		return rangeToKey;
	}

	public void setRangeToKey(int rangeToKey) {
		this.rangeToKey = rangeToKey;
	}
	
	public Boolean getKeyCacheReady() {
		return keyCacheReady;
	}

	public void setKeyCacheReady(Boolean keyCacheReady) {
		this.keyCacheReady = keyCacheReady;
	}
	
	public Long getCacheLastGeneretedKey() {
		return cacheLastGeneretedKey;
	}

	public void setCacheLastGeneretedKey(Long cacheLastGeneretedKey) {
		this.cacheLastGeneretedKey = cacheLastGeneretedKey;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField(
			"beginKey", 
			MetaField.required, true, 
			MetaField.readOnly, true
		);
		this.defineField(
				"endKey", 
				MetaField.required, true, 
				MetaField.readOnly, true
			);
		this.defineField(
				"rangeName", 
				MetaField.required, true
			);
		this.defineField(
				"lastGeneratedKey", 
				MetaField.required, true, 
				MetaField.readOnly, true
			);
		this.defineField(
				"rangeToKey", 
				MetaField.required, true, 
				MetaField.readOnly, true
			);
		this.defineField(
				"cacheLastGeneretedKey", 
				MetaField.visible, false
			);
		this.defineField(
				"id", 
				MetaField.visible, true
			);
	}
}
