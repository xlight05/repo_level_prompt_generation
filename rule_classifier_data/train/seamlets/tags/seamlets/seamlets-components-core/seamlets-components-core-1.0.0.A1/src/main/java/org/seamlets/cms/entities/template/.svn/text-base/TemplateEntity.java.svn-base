package org.seamlets.cms.entities.template;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.jboss.seam.Component;
import org.seamlets.cms.annotations.Template;
import org.seamlets.cms.entities.AdminstrationEntity;
import org.seamlets.cms.entities.template.viewprovider.TemplateConfigurationViewProvider;
import org.seamlets.cms.xsd.template.ViewPart;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TEMPLATE")
@NamedQuery(name = "templateEntity.viewId", query = "FROM TemplateEntity t WHERE t.templateViewId = :viewId")
@XmlRootElement(name = "template")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "templateViewId", "displayName"})
public abstract class TemplateEntity extends AdminstrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer			id;

	@Column(name = "TEMPLATE_VIEWID", nullable = false)
	@NotNull
	@NotEmpty
	protected String		templateViewId;

	@Column(name = "DISPLAYNAME")
	@NotEmpty
	@NotNull
	private String			displayName;

	@CollectionOfElements(fetch = FetchType.LAZY, targetElement = ViewPart.class)
	@JoinTable(name = "TEMPLATE_VIEWPARTS") 
	private Set<ViewPart>	viewParts	= new HashSet<ViewPart>();

	@XmlAttribute(required=true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getTemplateViewId() {
		return templateViewId;
	}

	public void setTemplateViewId(String templateViewId) {
		this.templateViewId = templateViewId;
	}

	@XmlElement
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlTransient
	public Set<ViewPart> getViewParts() {
		return viewParts;
	}

	public void setViewParts(Set<ViewPart> viewParts) {
		this.viewParts = viewParts;
	}

	@Override
	public String toString() {
		return displayName + "|" + templateViewId;
	}

	@SuppressWarnings("unchecked")
	@Transient
	@XmlTransient
	public String getConfigurationView() {
		if (this instanceof TemplateConfigurationViewProvider) {
			return ((TemplateConfigurationViewProvider) this).getConfViewId();
		}

		Template templateAnnotation = this.getClass().getAnnotation(Template.class);
		if (templateAnnotation.confViewId().length() != 0) {
			return templateAnnotation.confViewId();
		}

		String confViewId = ((Map<String, String>) Component.getInstance("confViewIds")).get(templateViewId);
		if (confViewId != null && confViewId.length() != 0) {
			return confViewId;
		}
		return null;
	}

}
