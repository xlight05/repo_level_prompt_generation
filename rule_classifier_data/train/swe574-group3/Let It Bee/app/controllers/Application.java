package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Query;

import controllers.Offers.OfferComparator;

import models.Handshake;
import models.Offer;
import models.Request;
import models.User;
import play.db.jpa.JPA;

public class Application extends BaseController
{   
    public static void index()
	{
	    int recentUserCount = 5;
	    int recentHandshakeCount = 5;
	    int recentOfferCount = 20;
	    List<User> newUsers = User.getNewUsers(recentUserCount);
	    List<Handshake> newHandshakes = Handshake.find("(status='ACCEPTED' or status='STARTED' or status='DONE') order by creationDate desc").fetch(recentHandshakeCount);
	    
	    List<Offer> newOffers = new ArrayList<Offer>();
	    List<Request> newRequests = new ArrayList<Request>();
	    User connectedUser=BaseController.getConnectedUser();
	    //If user is not logged in, empty lists for newOffers and newRequests will be sent
	    
	    if (connectedUser != null){
	    	
		    //1.Get most recent 20 offers from database
		    //2.Pick 4 offers that are closest to the user
		    newOffers = Offer.find("(status='WAITING'and user.longtitude is not null and user.langtitude is not null and user.id <> " + connectedUser.id + " and title not like 'OFFER FOR%' and title not like 'OFFER FOR%') order by id desc").fetch(recentOfferCount);
		    Collections.sort(newOffers, new OfferComparator());	    
		    if (newOffers != null && newOffers.size() > 4){
		    	newOffers = newOffers.subList(0, 4);
		    }
		    
		    //1.Get most recent 20 request from database
		    //2.Pick 4 requests that are closest to the user
		     newRequests = Request.find("(status='WAITING'and user.longtitude is not null and user.langtitude is not null and user.id <> " + connectedUser.id + " and title not like 'REQUEST FOR%') order by id desc").fetch(recentOfferCount);
		    Collections.sort(newRequests, new RequestComparator());	    
		    if (newRequests != null && newRequests.size() > 4){
		    	newRequests = newRequests.subList(0, 4);
		    }
	    }
	    render(newUsers, newHandshakes, newOffers, newRequests);     
	}

    public static void register() {
	render();
    }

    public static void about() {
	render();
    }

    public static void faq() {
	render();
    }

    public static void contact() {
	render();
    }
    
    public static void termsOfService() {
	render();
    }

    public static void showUserPhoto(Long userId) {
	User user = User.findById(userId);
	renderBinary(user.photo.get());
    }
    
    public static void showOfferPhoto(Long offerId) {
    	Offer offer = Offer.findById(offerId);
    	renderBinary(offer.photo.get());
        }

    
    public static void showRequestPhoto(Long requestId) {
    	Request req = Request.findById(requestId);
    	renderBinary(req.photo.get());
        }

    
    
    public static class OfferComparator implements Comparator{
 	   
        public int compare(Object emp1, Object emp2){
      
           
            Float dist1 = ((Offer)emp1).getUserDistance();        
            Float dist2 = ((Offer)emp2).getUserDistance();
           
            if (dist1 == null || dist2 == null){
            	return 1;
            }
            
            if(dist2 > dist1)
                return 1;
            else if(dist2 < dist1)
                return -1;
            else
                return 0;    
        }
       
    }
    
    public static class RequestComparator implements Comparator{
  	   
        public int compare(Object emp1, Object emp2){
      
           
            Float dist1 = ((Request)emp1).getUserDistance();        
            Float dist2 = ((Request)emp2).getUserDistance();
           
            
            if(dist2 > dist1)
                return 1;
            else if(dist2 < dist1)
                return -1;
            else
                return 0;    
        }
       
    }

}
