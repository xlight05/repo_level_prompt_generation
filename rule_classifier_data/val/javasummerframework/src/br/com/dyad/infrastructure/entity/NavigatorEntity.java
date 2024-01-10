package br.com.dyad.infrastructure.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.MetaField;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "SYSNAVIGATOR")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999982")
public class NavigatorEntity extends BaseEntity implements IsSerializable{
	
	@Column(name="NAME", length=150)
	private String name;
	
	@Column(name="BEANNAME", length=200)
	private String beanName;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENTID")
	private List<NavigatorEntity> submenu;
	
	@ManyToOne
	@JoinColumn (name="PARENTID")
	private NavigatorEntity parent;
	
	@Column(name="PROCESS", length=200)
	private String window;
	
	@Column(name="CSSNAME", length=50)
	private String cssName;

	public List<NavigatorEntity> getSubmenu() {
		if (submenu == null){
			submenu = new ArrayList<NavigatorEntity>();
		}
		return submenu;
	}

	public String toString(){
		return this.getBeanName() != null ? this.getBeanName() : this.getName();
	}
	
	public static String getFindExpress( String searchToken ){
		//return " and ( lower(name) like lower('%" + searchToken + "%') and beanName is not null )";
		return " and ( lower(name) like lower('%" + searchToken + "%') )";
	}
	
	public void setSubmenu(List<NavigatorEntity> submenu) {
		this.submenu = submenu;
	}

	public String getName(){
		return name;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public NavigatorEntity getParent() {
		return parent;
	}

	public void setParent(NavigatorEntity parent) {
		this.parent = parent;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssName() {
		return cssName;
	}

	public void setCssName(String cssName) {
		this.cssName = cssName;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField(
				"id", 
				MetaField.visible, false
		);

		this.defineField(
				"name", 
				MetaField.required, true, 
				MetaField.order, 10,
				MetaField.width, 200,
				MetaField.label, "Name"
		);

		this.defineField(
				"beanName", 
				MetaField.required, false, 
				MetaField.order, 15,
				MetaField.width, 100,
				MetaField.tableViewWidth, 200,
				MetaField.label, "Bean Name"
		);

		this.defineField(
				"window",
				MetaField.order, 20,
				MetaField.width, 250,
				MetaField.visible, true,
				MetaField.required, false,
				MetaField.label, "Window Location"
		);

		this.defineField(
				"cssName",
				MetaField.order, 30,
				MetaField.visible, true,
				MetaField.required, false, 
				MetaField.label, "Css Name"
		);

		this.defineField(
				"parent",
				MetaField.order, 0,
				MetaField.visible, false
		);

		this.defineField(
				"submenu",
				MetaField.order, 0,
				MetaField.visible, false
		);
				

	}	
}
