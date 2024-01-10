package org.seamlets.cms.entities.viewpart;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.seamlets.cms.comparator.ComponentComparator;
import org.seamlets.cms.entities.PageDefinition;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;

@Entity
@Table(name = "VIEWPARTS")
@XmlType(propOrder = { "id", "name", "viewPartContent" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ViewPart implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer							id;

	@Column(name = "NAME", nullable = false)
	@NotEmpty
	@NotNull
	private String							name;

	@Column(name = "SORTORDER", nullable = false)
	private Integer							sortorder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAGE_DEFINITION_ID")
	@NotNull
	private PageDefinition					pageDefinition;

	@OrderBy("sortIndex")
	@Sort(type = SortType.COMPARATOR, comparator = ComponentComparator.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "viewPart", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private SortedSet<PageContentEntity>	viewPartContent	= new TreeSet<PageContentEntity>(new ComponentComparator());

	@XmlAttribute(required = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public Integer getSortorder() {
		return sortorder;
	}

	public void setSortorder(Integer sortorder) {
		this.sortorder = sortorder;
	}

	@XmlElementRef
	public SortedSet<PageContentEntity> getViewPartContent() {
		return viewPartContent;
	}

	public void setViewPartContent(SortedSet<PageContentEntity> viewPartContent) {
		this.viewPartContent = viewPartContent;
	}

	@XmlTransient
	public PageDefinition getPageDefinition() {
		return pageDefinition;
	}

	public void setPageDefinition(PageDefinition pageDefinition) {
		this.pageDefinition = pageDefinition;
	}

}
