package org.seamlets.cms.entities;

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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.seamlets.cms.comparator.NavigationFolderComparator;
import org.seamlets.cms.comparator.PageDefinitionComparator;


@Entity
@Table(name = "NAVIGATION_FOLDER")
@NamedQuery(name = "domain.rootFolders", query = "FROM NavigationFolder WHERE domain = :domain AND parentNavigationFolder is null ORDER BY name")
public class NavigationFolder extends AdminstrationEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer						id;

	@NotNull
	@JoinColumn(name = "APPLICATION")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Application					application;

	@NotNull
	@JoinColumn(name = "DOMAIN")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Domain						domain;

	@JoinColumn(name = "PARENT_FOLDER")
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	private NavigationFolder			parentNavigationFolder;

	@NotNull
	@NotEmpty
	@Column(name = "NAME", unique = true, nullable = false)
	private String						name;

	@OrderBy("name")
	@Sort(type = SortType.COMPARATOR, comparator = NavigationFolderComparator.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentNavigationFolder", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	private SortedSet<NavigationFolder>	navigationFolders	= new TreeSet<NavigationFolder>(
																	new NavigationFolderComparator());

	@OrderBy("displayName")
	@Sort(type = SortType.COMPARATOR, comparator = PageDefinitionComparator.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentFolder", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	private SortedSet<PageDefinition>	pages				= new TreeSet<PageDefinition>(
																	new PageDefinitionComparator());

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<NavigationFolder> getNavigationFolders() {
		return navigationFolders;
	}

	public void setNavigationFolders(SortedSet<NavigationFolder> navigationFolders) {
		this.navigationFolders = navigationFolders;
	}

	public SortedSet<PageDefinition> getPages() {
		return pages;
	}

	public void setPages(SortedSet<PageDefinition> pages) {
		this.pages = pages;
	}

	public NavigationFolder getParentNavigationFolder() {
		return parentNavigationFolder;
	}

	public void setParentNavigationFolder(NavigationFolder parentNavigationFolder) {
		this.parentNavigationFolder = parentNavigationFolder;
	}

	@Transient
	public String getUrlPath() {
		StringBuilder builder = new StringBuilder();
		if (parentNavigationFolder != null) {
			builder.append(parentNavigationFolder.getUrlPath());
		} else {
			builder.append(domain.getRootUrl());
		}
		builder.append("/");
		builder.append(name);

		return builder.toString();
	}

	@Override
	public String toString() {
		return name;
	}

}
