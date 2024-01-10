package org.seamlets.cms.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.security.Identity;

@MappedSuperclass
@XmlTransient
public class AdminstrationEntity implements Serializable {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED", nullable = false)
	private Date	updated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false)
	private Date	created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED")
	private Date	deleted;

	@Column(name = "LAST_ACCES_USER", nullable = false)
	private String	lastAccesUser;
	
	@PrePersist
	public void created(){
		created = new Date();
		updated = created;
		if(Contexts.isSessionContextActive())
			lastAccesUser = Identity.instance().getPrincipal().getName();
		else 
			lastAccesUser = "SYSTEM";
	}
	
	@PreUpdate
	public void updated(){
		updated = new Date();
		if(Contexts.isSessionContextActive())
			lastAccesUser = Identity.instance().getPrincipal().getName();
		else 
			lastAccesUser = "SYSTEM";
	}

	@XmlTransient
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@XmlTransient
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@XmlTransient
	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	@XmlTransient
	public String getLastAccesUser() {
		return lastAccesUser;
	}

	public void setLastAccesUser(String lastAccesUser) {
		this.lastAccesUser = lastAccesUser;
	}

}
