package org.seamlets.cms.entities.pagecomponent;

import java.io.InputStream;
import java.net.URL;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotNull;
import org.seamlets.cms.comparator.PageContentSortIndexComparator;
import org.seamlets.cms.entities.AdminstrationEntity;
import org.seamlets.cms.entities.viewpart.ViewPart;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PAGE_CONTENT")
@XmlRootElement
@XmlType(propOrder = {"id", "childContent"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class PageContentEntity extends AdminstrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer							id;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "VIEW_PART_ID")
	private ViewPart						viewPart;

	@Column(name = "SORT_INDEX")
	@NotNull
	private Integer							sortIndex		= new Integer("0");

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "container", cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@Sort(type = SortType.COMPARATOR, comparator = PageContentSortIndexComparator.class)
	@OrderBy("sortIndex")
	private SortedSet<PageContentEntity>	childContent	= new TreeSet<PageContentEntity>(
																	new PageContentSortIndexComparator());

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "CONTAINER_ID")
	private PageContentEntity				container;

	@XmlAttribute(required = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	public ViewPart getViewPart() {
		return viewPart;
	}

	public void setViewPart(ViewPart viewPart) {
		this.viewPart = viewPart;
	}

	@XmlTransient
	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	@XmlElementWrapper(name = "childs")
	@XmlElement(name="content")
	public SortedSet<PageContentEntity> getChildContent() {
		return childContent;
	}

	public void setChildContent(SortedSet<PageContentEntity> childContent) {
		this.childContent = childContent;
	}

	@XmlTransient
	public PageContentEntity getContainer() {
		return container;
	}

	public void setContainer(PageContentEntity container) {
		this.container = container;
	}

	@XmlTransient
	public abstract InputStream getComponentImage();

	@XmlTransient
	public abstract URL getComponentDetailPage();

	@Override
	public String toString() {
		return "" + id;
	}

}
