package br.com.dyad.infrastructure.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.infrastructure.persistence.PersistenceUtil;

@Entity
@Table(name="SYSCONFIG")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999932")
public class SystemConfig extends BaseEntity {
	
	public static SystemConfig getCurrentConfig(String database) {
		String hql = "from SystemConfig where startDate <= :startDate order by startDate desc";
		Session session = PersistenceUtil.getSession(database);
		
		Query query = session.createQuery(hql);
		query.setMaxResults(1);		
		query.setParameter("startDate", new Date());
		List results = query.list();
		
		try {
			for (Object object : results) {
				return (SystemConfig)object;
			}
		} finally {
			//session.getTransaction().rollback();
		}
		
		throw new RuntimeException("System configuration not found!");
	}
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	private String companyName;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}			

}
