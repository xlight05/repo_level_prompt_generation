package br.com.dyad.infrastructure.i18n;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

@Entity
@Table(name="SYSTRANSLATION")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999926")
public class Translation extends BaseEntity {
	
	@OneToOne
	private Language language;
	
	@Column(length=255)
	private String code;
	
	@Column(length=1000)
	private String value;
	
	private Boolean sendToClient;
	
	public Boolean getSendToClient() {
		return sendToClient;
	}
	public void setSendToClient(Boolean sendToClient) {
		this.sendToClient = sendToClient;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	
	//static	
	@Transient
	private static transient HashMap<Language, HashMap<String, String>> properties = new HashMap<Language, HashMap<String, String>>();
	
	public static synchronized HashMap<String, String> loadTokens(String database, Language language, boolean clientMessage){
		HashMap<String, String> tokens = clientMessage ? null : properties.get(language);
		
		if (tokens == null){			
			tokens = new HashMap<String, String>();
			
			Session session = PersistenceUtil.getSession(database);
			String where = clientMessage ? " and sendToClient = true" : "";
			Query query = session.createQuery("from Translation where language = :language" + where);
			query.setParameter("language", language);
			
			for (Object temp : query.list()){
				Translation tr = (Translation)temp;
				tokens.put(tr.getCode(), tr.getValue());
			}
			
			if (!clientMessage){				
				properties.put(language, tokens);
			}
		}
		
		return tokens;
	}
	public static String get(String database, Language language, String key){
		HashMap<String, String> tokens = properties.get(language);
		
		if (tokens == null){
			tokens = loadTokens(database, language, false);
		}
		
		return (tokens.get(key) == null ? key : tokens.get(key));
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField( 
				"language", 
				MetaField.order, 100,
				MetaField.required, true,
				MetaField.beanName, Language.class.getName() );
		
		this.defineField( 
				"code", 
				MetaField.order, 200,
				MetaField.required, true,
				MetaField.width, 300 );
		
		this.defineField( 
				"value", 
				MetaField.order, 300,
				MetaField.required, true,
				MetaField.width, 300);

		this.defineField( 
				"sendToClient", 
				MetaField.order, 400 );
	
	}
}
