package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.AbstractMap;
import java.util.HashMap;

import javax.persistence.Query;

import models.Offer;
import models.Request;
import models.Subcategory;
import models.Tag;
import models.User;
import models.Handshake;
import service.MatchService;
import service.Utils;

import play.db.jpa.JPA;


public class Offers extends BaseController
{
    public static void create() {
    	Offer offerItem = new Offer();
    	offerItem.tags = new ArrayList<Tag>();
    	renderTemplate("Offers/form.html", offerItem);
    }

    public static void doCreate(String tags, Offer offerItem) {
    	User user = getConnectedUser();

    	boolean isCreate = offerItem.id == null;
    	/*long param=Long.parseLong(params.get("offerItem.subcategory.id"));
		System.out.println("subcategory param ile gelen id="+ param);
*/

    	if (!isCreate) {
    		// prevent tags to be appended to existing tags on edit
    		Tag.delete("offer.id", offerItem.id);
    	}
		List<String> tagsListString = Utils.parseTags(tags);
		List<Tag> tagsList = new ArrayList<Tag>();
		for (String tagString : tagsListString) {
		    Tag tag = new Tag(offerItem, tagString);
		    tagsList.add(tag);
		}
		offerItem.tags = tagsList;
/*
		offerItem.subcategory=Subcategory.findById(offerItem.subcategory.id);
		
		System.out.println("subcategory="+ offerItem.subcategory);
*/
		offerItem.creationDate = new Date();

		validation.valid(offerItem);
		if (validation.hasErrors()) {
		    System.out.println("VALIDATION ERRORS:" + validation.errors());
		    renderTemplate("Offers/form.html", offerItem);
		}
	
		offerItem.user = user;
		offerItem.isActive=true;
		offerItem.save();
	
		show(offerItem.id, isCreate);
    }
    
    public static void save(Long offerId) {
    	Offer offerItem = Offer.findById(offerId);
	offerItem.save();
	show(offerItem.id, true);
    }

    public static void show(Long id, Boolean isCreate) {
	Offer offerItem = Offer.findById(id);
	Boolean isOldOffer = !isCreate;
	render(offerItem);
    }

    public static void showAfterEdit(Long id) {
	Offer offerItem = Offer.findById(id);
	Boolean isOldOffer = true;
	render(offerItem, isOldOffer);
    }

    
    
    public static void showDetails(Long id) {
    	User user = getConnectedUser(); // user who is inspecting the offer
    	Offer offerItem = Offer.findById(id); // the offer being inspected
	User offerOwner = offerItem.user; // owner of the offer

	Long handshakeId = new Long(0L); // variable to store the id of the matched handshake
	Query handshakeQuery = JPA.em().createQuery("from " + Handshake.class.getName() + " where offer.id=" + offerItem.id ); // handshakes which have been initiated with the current offer's id
	List<Object[]> handshakeList = handshakeQuery.getResultList(); // list of matching handshakes

	Boolean hasApplied = false; // inititate hasApplied boolean to false
	
	for(Object singleHandshake : handshakeList) { // iterate over handshakes
	    Handshake handshakeItem = (Handshake) singleHandshake; // type casting
	    Request requestItem = handshakeItem.request; // the request belonging to the current iteration's handshake
	    hasApplied = (requestItem.user == user); // if the user of the request is equal to the current user, set hasApplied to true
	    if (hasApplied) { // store the matched handhshake's id and break out of the for loop if we know user has applied to the current offer
		handshakeId = handshakeItem.id;
		break;
	    }
	}

	Query applicationsQuery = JPA.em().createQuery("from " + Handshake.class.getName() + " where offererId=" + offerOwner.id + " and offer_id=" + offerItem.id + " and status='WAITING_APPROVAL'");
	List<Object[]> applications = applicationsQuery.getResultList();
	List<Handshake> applicationList = new ArrayList(applications);

	AbstractMap<User, Handshake> userApplications = new HashMap();
	
	for (Handshake handshakeItem : applicationList) {
	    User applicant = User.findById(handshakeItem.requesterId);
	    userApplications.put(applicant, handshakeItem);
	}

	Boolean isOfferOwner = (user == offerOwner);
	Boolean someoneElsesOffer = (user != offerItem.user);
    	render(user, offerItem, offerOwner, someoneElsesOffer, hasApplied, userApplications, isOfferOwner);
    }
    
    

    public static void search(int subcategoryId, String phrase, String location, String county_id, String district_id, String reocc, String m1, String t2, String w3, String t4, String f5, String s6, String s7, String tFrom, String tTo) {
    	User user = getConnectedUser();

    	if(location == null) location = "0";
    	Query openOffersQuery;   
    	String showFiltered = null;
    	String dayHoursFilter = "";
    	String originalPhrase = phrase;
    	
    	String categoryString=String.format("%d",subcategoryId);
    	
    	
    	if(subcategoryId!=0)
    		categoryString=" AND subcategory_id="+categoryString;
    	else
    		categoryString="";
    	
    	if(phrase != null && phrase.length() > 0)
    	{
    		if(phrase.toUpperCase().contains("ING"))
    		{
    			phrase = phrase.toUpperCase().replace("ING","");
    		}
    	}
    	
    	if(reocc != null && reocc.contains("1"))
    	{
    		dayHoursFilter = " and reoccure = True ";
    		
    		if(m1 != null && m1.contains("on"))
    			dayHoursFilter += " and is_rec_monday = True ";
    		
    		if(t2 != null && t2.contains("on"))
    			dayHoursFilter += " and is_rec_tuesday = True ";
    		
    		if(w3 != null && w3.contains("on"))
    			dayHoursFilter += " and is_rec_wednesday = True ";
    		
    		if(t4 != null && t4.contains("on"))
    			dayHoursFilter += " and is_rec_thursday = True ";
    		
    		if(f5 != null && f5.contains("on"))
    			dayHoursFilter += " and is_rec_friday = True ";
    		
    		if(s6 != null && s6.contains("on"))
    			dayHoursFilter += " and is_rec_saturday = True ";
    		
    		if(s7 != null && s7.contains("on"))
    			dayHoursFilter += " and is_rec_sunday = True ";
    		
    		Integer tFromInt = 0;
    		Integer tToInt = 0;
    		
    		if(tFrom != null && tFrom.length() > 0)
    		{
    			tFromInt = Integer.valueOf(tFrom);
    		}
    		
    		if(tTo!= null && tTo.length() > 0)
    		{
    			tToInt = Integer.valueOf(tTo);
    		}
    		
    		if(tFromInt != tToInt)
    		{
    			dayHoursFilter += " and ((reocc_start_hour_val < " +  tFromInt.toString() + " and reocc_end_hour_val > " + tFromInt.toString() + ")"; 
    			dayHoursFilter += " or (reocc_start_hour_val <" + tToInt.toString() + " and reocc_end_hour_val > " + tToInt.toString() + ")";
    			dayHoursFilter += " or (reocc_start_hour_val >" + tFromInt.toString() + " and reocc_end_hour_val < " + tToInt.toString() + "))";
    		}
    	}
    	
    	if(location.contains("1"))
    	{
    		Query openOffersQueryAll = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING' AND isActive=True");
    		List<Object[]> openOffersListAll = openOffersQueryAll.getResultList();
    		
    		String addStr = "";
    		
    		if(district_id != null && district_id.length() > 0)
    		{
    			addStr = " and district_id =" + district_id;
    		}
    		else if(county_id != null && county_id.length() > 0)
    		{
    			addStr = " and county_id =" + county_id;
    		}
    		    		
    		openOffersQuery = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING' and isActive=True and (is_virtual is null or is_virtual = False) " + addStr + categoryString + dayHoursFilter);
    		List<Object[]> openOffersList = openOffersQuery.getResultList();
        	List<Offer> allOffers = new ArrayList(openOffersList);
        	
        	List<Offer> foundOffers = MatchService.match(allOffers, phrase);

        	if(phrase == null || phrase.length() == 0)
        	{
        		showFiltered= "1";
        		foundOffers = allOffers;
        	}
        	
        	allOffers = new ArrayList(openOffersListAll);
        	
        	phrase = originalPhrase;
       		render(user, foundOffers, allOffers, phrase, location, county_id, district_id, showFiltered, reocc, m1, t2, w3, t4, f5, s6, s7, tFrom, tTo);
    	}
    	else if(location.contains("2"))
    	{   		
    		Query openOffersQueryAll = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING'");
    		List<Object[]> openOffersListAll = openOffersQueryAll.getResultList();
    		
    		openOffersQuery = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING'"+ " and is_virtual = True" + " and isActive=True "+ categoryString + dayHoursFilter);
    		List<Object[]> openOffersList = openOffersQuery.getResultList();
        	List<Offer> allOffers = new ArrayList(openOffersList);
        	List<Offer> foundOffers = MatchService.match(allOffers, phrase);

        	if(phrase == null || phrase.length() == 0)
        	{
        		showFiltered= "1";
        		foundOffers = allOffers;
        	}
        	
        	allOffers = new ArrayList(openOffersListAll);
        	phrase = originalPhrase;
       		render(user, foundOffers, allOffers, phrase, location, county_id, district_id, showFiltered, reocc, m1, t2, w3, t4, f5, s6, s7, tFrom, tTo);
    	}
    	else 
    	{
    		Query openOffersQueryAll = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING'");
    		List<Object[]> openOffersListAll = openOffersQueryAll.getResultList();
    		
    		openOffersQuery = JPA.em().createQuery("from " + Offer.class.getName() + " where status is 'WAITING'" + " and isActive=True "+ categoryString + dayHoursFilter);
    		List<Object[]> openOffersList = openOffersQuery.getResultList();
        	List<Offer> allOffers = new ArrayList(openOffersList);
        	List<Offer> foundOffers = MatchService.match(allOffers, phrase);
        	
        	if(phrase == null || phrase.length() == 0)
        	{
        		showFiltered= "1";
        		foundOffers = allOffers;
        	}
        	


            
            Collections.sort(allOffers, new OfferComparator());

        	
            foundOffers = new ArrayList(allOffers);
        	phrase = originalPhrase;
        	render(user, foundOffers, allOffers, phrase, location, county_id, district_id, showFiltered, reocc, m1, t2, w3, t4, f5, s6, s7, tFrom, tTo);
    	}

    }
    
    
    
    public static void edit(Long id) {
    	Offer offerItem = Offer.findById(id);
    	renderTemplate("Offers/form.html", offerItem);
    }

    public static void list() {	
	User user = getConnectedUser();
	List<Offer> offers = Offer.find("user.id", user.id).fetch();
	render(user, offers);
    }
    
    public static class OfferComparator implements Comparator{
    	   
        public int compare(Object emp1, Object emp2){
      
           
            Float dist1 = ((Offer)emp1).sortingPoints;        
            Float dist2 = ((Offer)emp2).sortingPoints;
           
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
}
