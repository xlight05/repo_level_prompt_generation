package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.dyad.infrastructure.annotations.CacheClass;

@Entity
@Table(name="SYSREFERENCE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999943")
@CacheClass
public class Language extends Reference{

	/*public static void main(String[] args) {
		//System.out.println( KeyGenerator.getInstance().generateKey(KeyGenerator.KEY_RANGE_PRODUCT_KEY) );
	}*/
}
