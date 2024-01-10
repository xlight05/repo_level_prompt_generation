package org.seamlets.cms.entities;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.seamlets.cms.comparator.DomainComparator;

@Entity
@Table(name = "APPLICATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@NamedQueries( { @NamedQuery(name = "application.name", query = "FROM Application WHERE name = :name") })
@XmlType(propOrder = { "name", "id" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Application extends AdminstrationEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long				id;

	@Column(name = "NAME", unique = true)
	@NotNull
	@NotEmpty
	private String				name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "application", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	@Sort(type = SortType.COMPARATOR, comparator = DomainComparator.class)
	@OrderBy("rootUrl")
	private SortedSet<Domain>	domains	= new TreeSet<Domain>(new DomainComparator());

	@XmlAttribute(required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	public SortedSet<Domain> getDomains() {
		return domains;
	}

	public void setDomains(SortedSet<Domain> domains) {
		this.domains = domains;
	}

	@Override
	public String toString() {
		return name;
	}

}
