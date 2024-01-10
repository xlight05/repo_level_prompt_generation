package edu.spbsu.eshop.storage.data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EShopPersistanceUnit {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    public final Long getId() {
	return id;
    }

    public final void setId(Long id) {
	this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	EShopPersistanceUnit other = (EShopPersistanceUnit) obj;
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
	    return false;
	}
	return true;
    }
}