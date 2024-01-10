package models;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import controllers.BaseController;

import models.Offer.Status;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import service.Matchable;

@Entity
public class Request extends Model implements Matchable
{
    public enum Status {
        WAITING, HANDSHAKED, DEACTIVATED
    }
    
    public boolean isActive;
    
    

    public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Required
    public String title;

    @Required
    @Lob
    public String description;

    @Required
    @OneToMany(mappedBy="request", cascade=CascadeType.ALL)
    public List<Tag> tags;
    
    @Required
    @Temporal(TemporalType.TIMESTAMP)
    public Date endDate;

    @Required
    public Integer credit;
    
    @ManyToOne
    public User user;

    public Boolean isFinalized;

    // @Required
    @Enumerated(EnumType.STRING)
    public Status status;

    @ManyToOne
    public City city;
    
    @ManyToOne
    public County county;
    
    @ManyToOne
    public District district;
    
    public String city_name;
    
    public String county_name;
    
    public String district_name;
    
    public Boolean reoccure;       
    public Boolean is_rec_monday;    
    public Boolean is_rec_tuesday;
    public Boolean is_rec_wednesday;
    public Boolean is_rec_thursday;
    public Boolean is_rec_friday;
    public Boolean is_rec_saturday;
    public Boolean is_rec_sunday;
    
    public Boolean is_all_hours; 
    public Boolean is_virtual; 
       
    public String reocc_start_hour;    
    public String reocc_end_hour;
    public Integer reocc_start_hour_val;
    public Integer reocc_end_hour_val;
        
    @OneToOne
    public Subcategory subcategory;
    
    public Request(User user) {
	this.user = user;
    }

    public Request() {
	this.status = Status.WAITING;
    }

    @Override
    public List<Tag> getTags()
    {
        return tags;
    }
    
    @Override
    public String getDescription()
    {
        return description;
    }    

    @Override
    public String getTitle()
    {
        return title;
    }    
    
    public Request(User user, Offer offer) {
	this.user = user;
	this.title = offer.title;
	this.description = offer.description;
	this.endDate = offer.endDate;
	this.status = Status.WAITING;
    }

	@Override
	public int getUserBalance() {
		// TODO Auto-generated method stub
		return this.user.balance;
	}
	
	@Transient
	public float sortingPoints;

	@Override
	public float getUserDistance() {
		User connectedUser=BaseController.getConnectedUser();
		float totalDifference=0;
		
	        if (county != null && user.langtitude!=null && user.longtitude!=null ) {
	            
	            Float differenceX = (float)Math.pow((Float.valueOf(connectedUser.langtitude)-Float.valueOf(user.langtitude)),2);
	            Float differenceY = (float)Math.pow((Float.valueOf(connectedUser.longtitude)-Float.valueOf(user.longtitude)),2);
	            if(differenceX!=0 || differenceY!=0)
	            	totalDifference = (float)Math.sqrt(differenceX + differenceY);
	        }    
			return totalDifference;
	}

	@Override
	public void setSortingpoints(float points) {
		this.sortingPoints=points;		
	}

	@Override
	public User getItemUser() {
		
				return this.user;
	}

	@Override
	public float getSortingPoints() {
		return this.sortingPoints;
	} 
	
	public Blob photo;
	
	public Date creationDate;

	public String getCreationDate(){
		try{
		Format formatter = new SimpleDateFormat("MMM dd HH:mm");
		return formatter.format(creationDate);
		}catch (Exception e){
			return "";
		}
	}
	
}
