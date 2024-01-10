package org.seamlets.cms.entities;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.jboss.seam.Component;
import org.seamlets.cms.comparator.NavigationFolderComparator;
import org.seamlets.cms.comparator.PageDefinitionComparator;

@Entity
@Table(name = "DOMAIN", uniqueConstraints = { @UniqueConstraint(columnNames = { "ROOT_URL", "APPLICATION" }) })
@NamedQueries( { @NamedQuery(name = "domain.name", query = "FROM Domain WHERE application.name = :name AND rootUrl = :rootUrl") })
@XmlType(propOrder = { "rootUrl", "application", "id" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Domain extends AdminstrationEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer						id;

	@NotNull
	@JoinColumn(name = "APPLICATION")
	@ManyToOne(optional = false)
	private Application					application;

	@NotNull
	@NotEmpty
	@Column(name = "ROOT_URL", unique = true, nullable = false)
	private String						rootUrl;

	@OrderBy("displayName")
	@Sort(type = SortType.COMPARATOR, comparator = PageDefinitionComparator.class)
	@OneToMany(mappedBy = "domain", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private SortedSet<PageDefinition>	pages		= new TreeSet<PageDefinition>(new PageDefinitionComparator());

	@OrderBy("name")
	@Sort(type = SortType.COMPARATOR, comparator = NavigationFolderComparator.class)
	@OneToMany(mappedBy = "domain", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private SortedSet<NavigationFolder>	folders		= new TreeSet<NavigationFolder>(new NavigationFolderComparator());

	@Transient
	private SortedSet<NavigationFolder>	rootFolders	= new TreeSet<NavigationFolder>(new NavigationFolderComparator());

	@Transient
	private SortedSet<PageDefinition>	rootPages	= new TreeSet<PageDefinition>(new PageDefinitionComparator());

	@PostLoad
	@SuppressWarnings("unchecked")
	public void postLoad() {
		EntityManager entityManager = (EntityManager) Component.getInstance("componentsEntityManager");
		Query rootFolderQuery = entityManager.createNamedQuery("domain.rootFolders");
		Query rootPageQuery = entityManager.createNamedQuery("domain.rootPages");

		rootFolderQuery.setParameter("domain", this);
		rootPageQuery.setParameter("domain", this);

		rootFolders.clear();
		rootFolders.addAll(rootFolderQuery.getResultList());
		rootPages.clear();
		rootPages.addAll(rootPageQuery.getResultList());
	}

	@XmlAttribute(required = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@XmlTransient
	public SortedSet<PageDefinition> getPages() {
		return pages;
	}

	public void setPages(SortedSet<PageDefinition> pages) {
		this.pages = pages;
	}

	@XmlTransient
	public SortedSet<PageDefinition> getRootPages() {
		return rootPages;
	}

	public void setRootPages(SortedSet<PageDefinition> rootPages) {
		this.rootPages = rootPages;
	}

	@XmlElement
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return rootUrl;
	}

	@XmlTransient
	public SortedSet<NavigationFolder> getFolders() {
		return folders;
	}

	public void setFolders(SortedSet<NavigationFolder> folders) {
		this.folders = folders;
	}

	@XmlTransient
	public SortedSet<NavigationFolder> getRootFolders() {
		return rootFolders;
	}

	public void setRootFolders(SortedSet<NavigationFolder> rootFolders) {
		this.rootFolders = rootFolders;
	}

}
