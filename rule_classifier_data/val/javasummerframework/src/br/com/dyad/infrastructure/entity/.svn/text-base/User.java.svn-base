package br.com.dyad.infrastructure.entity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;
   
@Entity
@Table(name="SYSUSER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999980")
public class User extends Auth {

	@Column()
	private String login;
	
	private String password;
		
	@Column
	private String completeName;

	@Column(length=150)
	private String email;	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="securityPolicyId",nullable=true)
	private SecurityPolicy securityPolicy;	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="languageId",nullable=true)
	private Language language;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="statusId",nullable=true)
	private UserStatus status;	
	
	private transient MasterDetail groups;

	public boolean find( String token ){
		if ( this.getLogin().indexOf(token) != -1 ){
			return true;
		} else if ( this.getCompleteName().indexOf(token) != -1 ){
			return true;
		}
		return false;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {			
		this.password = User.encryptPasswork( password );		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
	
	public MasterDetail getGroups() {
		return groups;
	}

	public void setGroups(MasterDetail groups) {
		this.groups = groups;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SecurityPolicy getSecurityPolicy() {
		return securityPolicy;
	}

	public void setSecurityPolicy(SecurityPolicy securityPolicy) {
		this.securityPolicy = securityPolicy;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public static String encryptPasswork( String password ){
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes("UTF8"));
		    byte s[] = m.digest();
		    String encryptPassword = "";
		    
		    for (int i = 0; i < s.length; i++) {
		    	encryptPassword += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
		    }
		    return encryptPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField(
			"login", 
			MetaField.required, true, 
			MetaField.order, 2,
			MetaField.width, 100
		);
		
		this.defineField(
			"password",
			MetaField.order, 3,
			MetaField.width, 100,
			MetaField.visible, false,
			MetaField.readOnly, false,
			MetaField.listable, false,
			MetaField.password, true
		);
		
		this.defineField(
				"admin",
				MetaField.order, 4,
				MetaField.visible, true,
				MetaField.readOnly, false,
				MetaField.listable, true,
				MetaField.label, "Administrator"
			);

		this.defineField(
			"completeName",
			MetaField.order, 4,
			MetaField.width, 200,
			MetaField.tableViewWidth, 100,
			MetaField.required, true
		);

		this.defineField(
			"email", 
			MetaField.required, true, 
			MetaField.order, 5,
			MetaField.width, 200,
			MetaField.label, "E-mail"
		);
		
		this.defineField(
			"securityPolicy",
			MetaField.order, 6,
			MetaField.required, false,
			MetaField.width, 200,
			MetaField.tableViewWidth, 100,
			MetaField.beanName, SecurityPolicy.class.getName()
		);	
		
		this.defineField(
			"language",
			MetaField.order, 7,
			MetaField.required, true,
			MetaField.width, 200,
			MetaField.tableViewWidth, 100,
			MetaField.beanName, Language.class.getName()
		);	

		this.defineField(
			"status",
			MetaField.order, 8,
			MetaField.required, true,
			MetaField.tableViewVisible, false,
			MetaField.width, 200,
			MetaField.beanName, UserStatus.class.getName()
		);	
		
		this.defineField(
			"groups",
			MetaField.order, 5000,
			MetaField.visible, true,
			MetaField.required, true, 
			MetaField.beanName, GroupAndUser.class.getName(),
			MetaField.masterFieldNames, "id",                
			MetaField.detailFieldNames, "user"               
		);
	}

	public String toString(){
		return this.getLogin() + " - " + this.getCompleteName();
	}
	
	public static String getFindExpress( String searchToken ){
		return " and ( login like '%" + searchToken + "%' or completeName like '%" + searchToken + "%' )";
	}
}