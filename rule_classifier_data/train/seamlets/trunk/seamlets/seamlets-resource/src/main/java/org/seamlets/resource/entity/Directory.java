package org.seamlets.resource.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.seamlets.cms.entities.AdminstrationEntity;

@Entity
@Table(name = "RESOURCE_DIRECTORY", uniqueConstraints = @UniqueConstraint(columnNames = { "parent_id", "name" }))
@NamedQuery(name = "resource.directory.root", query = "FROM Directory WHERE parent is null")
public class Directory extends AdminstrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long			id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Directory		parent;
	private String			name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy("name")
	private List<Directory>	directories;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "directory")
	@OrderBy("name")
	private List<File>		files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<Directory> directories) {
		this.directories = directories;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Directory other = (Directory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

}
