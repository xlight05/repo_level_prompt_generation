package br.com.dyad.infrastructure.entity;

import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.annotations.CacheClass;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

@Entity
@Table(name="SYSREFERENCE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999929")
@Authorization(data=true, dataField="name")
public class ProductLicense extends Reference{
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="keyrangeid")
	private KeyRange keyRange;

	/**
	 * Indica que esta base pode emitir chaves desta licença.
	 * O upgrade não deve sobscrever esta informação na base de destino.
	 */
	private Boolean canGenerate;
	
	public KeyRange getKeyRange() {
		return keyRange;
	}

	public void setKeyRange(KeyRange keyRange) {
		this.keyRange = keyRange;
	}
	
	public Boolean getCanGenerate() {
		return canGenerate;
	}

	public void setCanGenerate(Boolean canGenerate) {
		this.canGenerate = canGenerate;
	}

	@Override
	public void defineFields() {
		super.defineFields();
		
		defineField("id", 
				MetaField.visible, true 
		);
		defineField("keyRange", 
				MetaField.order, 100,
				MetaField.visible, true,
				MetaField.beanName, "br.com.dyad.infrastructure.entity.KeyRange" 
		);
		defineField("canGenerate", 
				MetaField.order, 200,
				MetaField.visible, true,
				MetaField.canCustomizeValue, true 
		);
		
	}
	
	public static ProductLicense getLicenseById(String database, Long key) {		
		Session sess = PersistenceUtil.getSession(database);
		sess.beginTransaction();
		
		try {
			String hql = "from ProductLicense as pl " +
			 " where pl.keyRange.beginKey <= :key and pl.keyRange.endKey >= :key"; // and pl.classId = :classId";
			
			Query query = sess.createQuery(hql);
			query.setParameter("key", key);
			//query.setParameter("classId", BaseEntity.getClassIdentifier(ProductLicense.class).toString());
			List<ProductLicense> result = query.list();
			
			return (result == null || result.size() == 0) ? null : result.get(0);
		} catch(Exception e){
			throw new RuntimeException(e);
		} finally {
			sess.getTransaction().rollback();
		}
	}
}
