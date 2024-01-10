package org.seamlets.cms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.seamlets.cms.comparator.ViewPartComparator;
import org.seamlets.cms.entities.template.TemplateEntity;
import org.seamlets.cms.entities.viewpart.ViewPart;

@Entity
@Table(name = "PAGE_DEFINITION", uniqueConstraints = { @UniqueConstraint(columnNames = { "DOMAIN_ID", "VIEW_ID" }) })
@NamedQuery(name = "domain.rootPages", query = "FROM PageDefinition WHERE domain = :domain AND parentFolder is null ORDER BY displayName")
@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "id", "template", "title", "viewParts", "canonicalViewID", "displayName", "domain" })
public class PageDefinition extends AdminstrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer				id;

	@JoinColumn(name = "DOMAIN_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Domain				domain;

	@Column(name = "VIEW_ID", unique = true)
	private String				viewID;

	@Column(name = "CANONICAL_VIEW_ID", unique = true)
	private String				canonicalViewID;

	@Column(name = "DISPLAY_NAME")
	private String				displayName;

	@Column(name = "TITLE")
	private String				title;

	@JoinColumn(name = "PARENT_FOLDER")
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	private NavigationFolder	parentFolder;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "TEMPLATE_ID")
	private TemplateEntity		template;

	@OrderBy("sortorder")
	@Sort(type = SortType.COMPARATOR, comparator = ViewPartComparator.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pageDefinition", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private List<ViewPart>		viewParts	= new ArrayList<ViewPart>();

	@XmlAttribute(required = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	public String getViewID() {
		return viewID;
	}

	public void setViewID(String viewID) {
		this.viewID = viewID;
	}

	@XmlElement(name = "viewId")
	public String getCanonicalViewID() {
		return canonicalViewID;
	}

	public void setCanonicalViewID(String canonicalViewID) {
		this.canonicalViewID = canonicalViewID;
	}

	@XmlElement
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@XmlTransient
	public NavigationFolder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(NavigationFolder parentFolder) {
		this.parentFolder = parentFolder;
	}

	@XmlElementWrapper(name = "viewParts")
	@XmlElement(name = "viewPart")
	public List<ViewPart> getViewParts() {
		return viewParts;
	}

	public void setViewParts(List<ViewPart> viewParts) {
		this.viewParts = viewParts;
	}

	@XmlElementRef
	public TemplateEntity getTemplate() {
		return template;
	}

	public void setTemplate(TemplateEntity template) {
		this.template = template;
	}

	@Transient
	@XmlTransient
	public String getUrlPath() {
		StringBuilder builder = new StringBuilder();
		if (parentFolder != null) {
			builder.append(parentFolder.getUrlPath());
		} else {
			builder.append(domain.getRootUrl());
		}
		builder.append("/");

		return builder.toString();
	}
	
	@Transient
	@XmlTransient
	public String getCanonicalPath() {
		StringBuilder builder = new StringBuilder();
		if (parentFolder != null) {
			builder.append(parentFolder.getUrlPath());
		}
		builder.append("/");
		return builder.toString();
	}

	@PrePersist
	@PreUpdate
	public void calcCanonicalViewId() {
		String url = getCanonicalPath();
		url += viewID;
		url += ".xhtml";
		canonicalViewID = url;
	}

	@Override
	public String toString() {
		return displayName + " | " + viewID;
	}

}
