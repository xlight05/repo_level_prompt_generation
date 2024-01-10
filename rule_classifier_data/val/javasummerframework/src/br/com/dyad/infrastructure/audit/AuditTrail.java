package br.com.dyad.infrastructure.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.dyad.infrastructure.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="SYSLOG")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
//@DiscriminatorValue(value="-89999999999951")
public class AuditTrail implements Serializable, NotAuditable {
	
	public static final Long OPERATION_TYPE_ERROR   = -89999999999846L /* Errors */;
    public static final Long OPERATION_TYPE_INSERT  = -89999999999959L /* Insert */;
    public static final Long OPERATION_TYPE_UPDATE  = -89999999999958L /* Update */;
    public static final Long OPERATION_TYPE_DELETE  = -89999999999957L /* Delete */;
    public static final Long OPERATION_TYPE_LOGIN   = -89999999999956L /* Login */;
    public static final Long OPERATION_TYPE_LOGOUT  = -89999999999955L /* Logout */;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	private Long recordId;

	private Long transactionId;

	private String entityName;
	
	private String classId;
	
	private String tableName;
    
    private Long type;

    private String fieldNames;

    private String oldValues;

    private String newValues;

    private String userLogin;

    private Date date;

    private String note;
    
    @Column(length=32)
    private String signature;
    
    public AuditTrail(){
        // do nothing
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*public Integer getId() {
        return this.id;
    }*/

    public String getOldValues() {
        return this.oldValues;
    }

    public void setOldValues(String oldValues) {
        this.oldValues = oldValues;
    }

    public String getNewValues() {
        return this.newValues;
    }

    public void setNewValues(String newValues) {
        this.newValues = newValues;
    }

    public Date getDate() {
        return this.date;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getType() {
        return this.type;
    }
    public void setType(Long type) {
        this.type = type;
    }

    public String getFieldNames() {
        return this.fieldNames;
    }
    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AuditTrail))
            return false;
        final AuditTrail other = (AuditTrail) obj;
        return new EqualsBuilder()
        .append(this.id, other.getId())
        .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    
    /*
     @Override
     public int hashCode() {
        return new HashCodeBuilder()
        .append(this.toString())
        .hashCode();
    }*/
    public String getHashCodeSignature(){
    	String sign = this.toString();
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(sign.getBytes());
			byte[] hash = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10)
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				else
					hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
			sign = hexString.toString();
		} catch (Exception nsae) {
			nsae.printStackTrace();
		}
		return sign;  	
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
        .append("id", this.id)
        .append("recordId", this.recordId)
        .append("transactionId", this.transactionId)
        .append("entityName", this.entityName)
        .append("type", this.type)
        .append("fieldNames", this.fieldNames)
        .append("oldValues", this.oldValues)
        .append("newValues", this.newValues)
        .append("userLogin", this.userLogin)
        .append("date", this.date)
        .toString();
    }
    
    public String getClassIdentifier() throws Exception{
		return BaseEntity.getClassIdentifier(this.getClass());
	}
    
} 