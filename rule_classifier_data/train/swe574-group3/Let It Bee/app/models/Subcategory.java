package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

/*
 * id modelden geliyor
 */
@Entity
public class Subcategory extends Model {
	
	
	@Required
	private String categoryName;
	
	@Required
	private String subcategoryName;
	
	public Subcategory(long id)
	{
		super.id=id;
	}
	
	public Subcategory(long id,String categoryName, String subcategoryName)
	{
		this.categoryName=categoryName;
		this.subcategoryName=subcategoryName;	
		super.id=id;
	}
	
	@Override
	 public String toString()
	 {
		 return String.format("%s>%s", this.categoryName, this.subcategoryName);
	 }

}
