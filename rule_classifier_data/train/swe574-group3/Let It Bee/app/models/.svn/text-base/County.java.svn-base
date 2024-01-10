package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class County extends Model
{
	
	public String longtitude;
	public String langtitude;
	
    @Required
    public String name;

    @Required
    @ManyToOne
    public City city;
}
