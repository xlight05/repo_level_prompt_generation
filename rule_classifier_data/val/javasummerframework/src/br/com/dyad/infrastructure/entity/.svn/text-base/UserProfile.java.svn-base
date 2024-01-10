package br.com.dyad.infrastructure.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.dyad.infrastructure.annotations.MetaField;

@MappedSuperclass
@DiscriminatorValue(value="-89999999999836")
public abstract class UserProfile extends Auth{
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid", nullable=false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void defineFields() {
		super.defineFields();
		
		this.defineField("id", 
				MetaField.visible, false);

		this.defineField(
				"user", 
				MetaField.order, 10,
				MetaField.required, true,
				MetaField.width, 200,
				MetaField.visible, false,
				MetaField.label, "User",
				MetaField.beanName, "br.com.dyad.infrastructure.entity.User"
			);
	}
}
