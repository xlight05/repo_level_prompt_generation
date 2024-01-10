package controllers;

import java.util.*;

import javax.persistence.Query;

import models.*;
import play.data.validation.*;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import play.data.validation.*;
import play.db.jpa.JPA;


public class BaseController extends Controller
{
	
	/*
	 * renderArgs scope'undaki hashtable'a boolean isloogedin ve User user yerlestirilir
	 */
    @Before
    static void setConnectedUser() {
    	User user = getConnectedUser();
		renderArgs.put("isLoggedIn", user != null);
		if (user != null) {
		    renderArgs.put("user", user);
		}
    }
    
    /*
     * play.db.jpa.JPA.em().createQuery() ile badge='NEW BEE' olan kullaniciları bulma query'si hazirlanir
     * query calistirilir ve (User fieldlarını içeren) uygun sonuclar veritabanından arrylist'e atilir
     * Arrayliste gelen verilerden usr adli User objesi hazırlanır
	 * user-new_bee_base adlı Arrylistten gelen Userlar birer birer atilir
	 * Olusturaln liste karistirilir
	 * Karılmış listedeki her user'ın id üzerinden offer ve requestlerinin listesi alınır
	 * listRequest ve listoffer listelerine atılır
	 * bu listelerin uzunlugu toplanıp user.balance ayarlanır,
	 * eger 6'dan fazlaysa users_new_bee listesine b eklenir
	 * balance'ı 6dan büyük olan kullanıcıların listesi (users_new_bee) renderArgs scope'una "users_new_bee" adı aldında atılır  	
	 */
    
    @Before
    static void set_New_Bee() {
    	Query query = JPA.em().createQuery("select id, fullname, registrationDate, photo, address from User where badge = 'NEW_BEE'");
    	List<Object[]> list = query.getResultList();
    	List<User> users_new_bee_base = new ArrayList<User>();
    	List<User> users_new_bee = new ArrayList<User>();
    	Integer count = 0;
    	
    	for(Object[] a : list)
       	{       		
    		User usr = new User();
    		usr.id = (Long) a[0];
    		usr.fullname = (String) a[1];
    		usr.registrationDate = (Date) a[2];
    		usr.photo = (play.db.jpa.Blob ) a[3];
    		usr.address = (String ) a[4];
    		usr.balance = 0;
       		
    		users_new_bee_base.add(usr);
       	}  
    	
    	Collections.shuffle(users_new_bee_base);
    	
    	for(User b : users_new_bee_base)
    	{
    		count ++;
    		
    		Query queryOffer = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.offer e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listOffer = queryOffer.getResultList();
        	
    		Query queryRequest = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.request e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listRequest = queryRequest.getResultList();
        	
        	b.balance = listOffer.size() + listRequest.size();
    		
   			if(count < 6)
   				users_new_bee.add(b);   				
    	}
    	
    	renderArgs.put("users_new_bee", users_new_bee); 
    }
    

    /*
     * Aynı set_new_bee() gibi
     * badge='BUSY BEE' olan kullanıcıların listesi alınır ve ve users_new_bee_base adlı
     * arraylist'e atılır, arraylist karılır(shuffle edilir)
     * 6dan fazla request+offer toplamı olanlar "users_busy_bee" adı altında
     * renderArgs scope'una atılır
     */
    
    @Before
    static void set_Busy_Bee() {
    	Query query = JPA.em().createQuery("select id, fullname, registrationDate, photo, address from User where badge = 'BUSY_BEE'");
    	List<Object[]> list = query.getResultList();
    	List<User> users_new_bee_base = new ArrayList<User>();
    	List<User> users_new_bee = new ArrayList<User>();
    	Integer count = 0;
    	
    	for(Object[] a : list)
       	{       		
    		User usr = new User();
    		usr.id = (Long) a[0];
    		usr.fullname = (String) a[1];
    		usr.registrationDate = (Date) a[2];
    		usr.photo = (play.db.jpa.Blob ) a[3];
    		usr.address = (String ) a[4];
    		usr.balance = 0;
       		
    		users_new_bee_base.add(usr);
       	}  
    	
    	Collections.shuffle(users_new_bee_base);
    	
    	for(User b : users_new_bee_base)
    	{
    		count ++;
    		
    		Query queryOffer = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.offer e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listOffer = queryOffer.getResultList();
        	
    		Query queryRequest = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.request e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listRequest = queryRequest.getResultList();
        	
        	b.balance = listOffer.size() + listRequest.size();
    		
   			if(count < 6)
   				users_new_bee.add(b);   				
    	}
    	
    	renderArgs.put("users_busy_bee", users_new_bee); 
    }
    
    /*
     * Aynı set_new_bee() gibi
     * badge = 'WORKING_BEE' olan kullanıcıların listesi alınır ve ve users_new_bee_base adlı
     * arraylist'e atılır, arraylist shuffle edilir
     * 6dan fazla request+offer toplamı olanlar "users_working_bee" adı altında
     * renderArgs scope'una atılır
     */
    
    
    @Before
    static void set_Working_Bee() {
    	Query query = JPA.em().createQuery("select id, fullname, registrationDate, photo, address from User where badge = 'WORKING_BEE'");
    	List<Object[]> list = query.getResultList();
    	List<User> users_new_bee_base = new ArrayList<User>();
    	List<User> users_new_bee = new ArrayList<User>();
    	Integer count = 0;
    	
    	for(Object[] a : list)
       	{       		
    		User usr = new User();
    		usr.id = (Long) a[0];
    		usr.fullname = (String) a[1];
    		usr.registrationDate = (Date) a[2];
    		usr.photo = (play.db.jpa.Blob ) a[3];
    		usr.address = (String ) a[4];
    		usr.balance = 0;
       		
    		users_new_bee_base.add(usr);
       	}  
    	
    	Collections.shuffle(users_new_bee_base);
    	
    	for(User b : users_new_bee_base)
    	{
    		count ++;
    		
    		Query queryOffer = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.offer e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listOffer = queryOffer.getResultList();
        	
    		Query queryRequest = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.request e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listRequest = queryRequest.getResultList();
        	
        	b.balance = listOffer.size() + listRequest.size();
    		
   			if(count < 6)
   				users_new_bee.add(b);   				
    	}
    	
    	renderArgs.put("users_working_bee", users_new_bee); 
    }
    

    /*
     * Aynı set_new_bee() gibi
     * badge = 'BUMBLE_BEE' olan kullanıcıların listesi alınır ve ve users_new_bee_base adlı
     * arraylist'e atılır, arraylist shuffle edilir
     * 6dan fazla request+offer toplamı olanlar "users_bumble_bee" adı altında
     * renderArgs scope'una atılır
     */

    
    @Before
    static void set_Bumble_Bee() {
    	Query query = JPA.em().createQuery("select id, fullname, registrationDate, photo, address from User where badge = 'BUMBLE_BEE'");
    	List<Object[]> list = query.getResultList();
    	List<User> users_new_bee_base = new ArrayList<User>();
    	List<User> users_new_bee = new ArrayList<User>();
    	Integer count = 0;
    	
    	for(Object[] a : list)
       	{       		
    		User usr = new User();
    		usr.id = (Long) a[0];
    		usr.fullname = (String) a[1];
    		usr.registrationDate = (Date) a[2];
    		usr.photo = (play.db.jpa.Blob ) a[3];
    		usr.address = (String ) a[4];
    		usr.balance = 0;
       		
    		users_new_bee_base.add(usr);
       	}  
    	
    	Collections.shuffle(users_new_bee_base);
    	
    	for(User b : users_new_bee_base)
    	{
    		count ++;
    		
    		Query queryOffer = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.offer e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listOffer = queryOffer.getResultList();
        	
    		Query queryRequest = JPA.em().createQuery("SELECT e.id " 
    				+ "FROM Tag p JOIN p.request e JOIN e.user u where u.id = " + b.id.toString()
    				+ " group by e.id");
    		List<Object[]> listRequest = queryRequest.getResultList();
        	
        	b.balance = listOffer.size() + listRequest.size();
    		
   			if(count < 6)
   				users_new_bee.add(b);   				
    	}
    	
    	renderArgs.put("users_bumble_bee", users_new_bee); 
    }
    

    /*
     * Tag tablosundan offer_id'lere göre gruplanmış olarak offer 
     * isimleri ve o id ye sahip kaç tane offer record'u oldugu alınır(count() a göre 
     * büyükten küçüğe sıralı olarak)
     * TagCloudItem t_Item a gelen sonuçlar aktarılır.
     * cssflag=listflag%2 ile olusturulur (listflag sıralı gelen sonuclşarın sıra numarası, yani queryden sıralı gelen
     * sonucların sıra numarası)
     * t_Item ın css class alanına "tag_" + cssFlag.toString() konulur
     * ilk 13 sonuç tagcloud olarak karılır, "tagCloudOffer" olarak renderArg scope'una atılır
     * ?hyperlink boş, neden?
     */

    
    @Before
    static void setOfferTagCloud() {
    	
    	Query query = JPA.em().createQuery("select name, count(*) from Tag where offer_id is not null group by name order by count(*) desc");
    	List<Object[]> list = query.getResultList();
       	
    	List<TagCloudItem> tagCloud = new ArrayList<TagCloudItem>(); 
    	Integer listFlag = 0;
    	
    	for(Object[] a : list)
       	{
    		listFlag ++;
       		
       		TagCloudItem t_Item = new TagCloudItem();
       		t_Item.name = (String) a[0];
       		t_Item.count = (Long)a[1];
       		t_Item.hyperlink = "";
       		
      	    Integer cssFlag = 0;
       	    
       	    if(listFlag%2 == 0)
       	    	cssFlag = (listFlag + 2)/2;
       	    else cssFlag = (listFlag + 1)/2;
       	    
       	    t_Item.CssClass = "tag_" + cssFlag.toString();       	    		
       		
       		if(listFlag < 13)
       			tagCloud.add(t_Item);
       	}  
       	
    	Collections.shuffle(tagCloud);
    	
    	renderArgs.put("tagCloudOffer", tagCloud);  
    }

    /*
     * Tag tablosundan request_id'lere göre gruplanmış olarak 
     * isimleri ve o id ye sahip kaç tane offer record'u oldugu alınır(count() a göre 
     * büyükten küçüğe sıralı olarak)
     * TagCloudItem t_Item a gelen sonuçlar aktarılır.
     * cssflag=listflag%2 ile olusturulur (listflag sıralı gelen sonuclşarın sıra numarası, yani queryden sıralı gelen
     * sonucların sıra numarası)
     * t_Item ın css class alanına "tag_" + cssFlag.toString() konulur
     * ilk 13 sonuç tagcloud olarak karılır, "tagCloudRequest" olarak renderArg scope'una atılır
     * ?hyperlink boş, neden?
     */
    
    @Before
    static void setRequestTagCloud() {
    	
    	Query query = JPA.em().createQuery("select name, count(*) from Tag where request_id is not null group by name order by count(*) desc");
    	List<Object[]> list = query.getResultList();
       	
    	List<TagCloudItem> tagCloud = new ArrayList<TagCloudItem>(); 
    	Integer listFlag = 0;
    	
    	for(Object[] a : list)
       	{
    		listFlag ++;
       		
       		TagCloudItem t_Item = new TagCloudItem();
       		t_Item.name = (String) a[0];
       		t_Item.count = (Long)a[1];
       	    t_Item.hyperlink = "";
       		
      	    Integer cssFlag = 0;
       	    
       	    if(listFlag%2 == 0)
       	    	cssFlag = (listFlag + 2)/2;
       	    else cssFlag = (listFlag + 1)/2;
       	    
       	    t_Item.CssClass = "tag_" + cssFlag.toString();       	    		
       		
       		if(listFlag < 13)
       			tagCloud.add(t_Item);
       	}  
       	
    	Collections.shuffle(tagCloud);
    	
    	renderArgs.put("tagCloudRequest", tagCloud);  
    }

    /*
     * Tag tablosundan offer_id'lere göre gruplanmış olarak 
     * isimleri ve o id ye sahip kaç tane offer record'ları oldugu alınır(count() a göre 
     * büyükten küçüğe sıralı olarak)
     * TagCloudItem t_Item a gelen sonuçlar aktarılır.
     * hyperlink="/offers/search?phrase=" + t_Item.name olarak belirlenir
     * cssflag=listflag%2 ile olusturulur (listflag sıralı gelen sonuclşarın sıra numarası, yani queryden sıralı gelen
     * sonucların sıra numarası)
     * t_Item ın css class alanına "tag_big" + cssFlag.toString() konulur
     * ilk 13 sonuç tagcloud olarak karılır, "tagCloudBigOffer" olarak renderArg scope'una atılır
     * 
     */

    @Before
    static void setOfferTagCloudBig() {
    	
    	Query query = JPA.em().createQuery("select name, count(*) from Tag where offer_id is not null group by name order by count(*) desc");
    	List<Object[]> list = query.getResultList();
       	
    	List<TagCloudItem> tagCloud = new ArrayList<TagCloudItem>(); 
    	Integer listFlag = 0;
    	
    	for(Object[] a : list)
       	{
    		listFlag ++;
       		
       		TagCloudItem t_Item = new TagCloudItem();
       		t_Item.name = (String) a[0];
       		t_Item.count = (Long)a[1];
       		t_Item.hyperlink = "/offers/search?phrase=" + t_Item.name;
       		
      	    Integer cssFlag = 0;
       	    
       	    if(listFlag%2 == 0)
       	    	cssFlag = (listFlag + 2)/2;
       	    else cssFlag = (listFlag + 1)/2;
       	    
       	    t_Item.CssClass = "tag_big_" + cssFlag.toString();       	    		
       		
       		if(listFlag < 13)
       			tagCloud.add(t_Item);
       	}  
       	
    	Collections.shuffle(tagCloud);
    	
    	renderArgs.put("tagCloudBigOffer", tagCloud);  
    }
    
    /*
     * Tag tablosundan offer_id'lere göre gruplanmış olarak 
     * isimleri ve o id ye sahip kaç tane offer record'ları oldugu alınır(count() a göre 
     * büyükten küçüğe sıralı olarak)
     * TagCloudItem t_Item a gelen sonuçlar aktarılır.
     * hyperlink="/requests/search?phrase=" + t_Item.name olarak belirlenir
     * cssflag=listflag%2 ile olusturulur (listflag: count()'a göre büyükten küçüğe
     * sıralı gelen sonucların sıra numarası, yani queryden sıralı gelen
     * sonucların sıra numarası)
     * t_Item ın css class alanına "tag_big" + cssFlag.toString() konulur
     * ilk 13 sonuç tagcloud olarak karılır, "tagCloudBigRequest" olarak renderArg scope'una atılır
     * 
     */

    
    @Before
    static void setRequestTagCloudBig() {
    	
    	Query query = JPA.em().createQuery("select name, count(*) from Tag where request_id is not null group by name order by count(*) desc");
    	List<Object[]> list = query.getResultList();
       	
    	List<TagCloudItem> tagCloud = new ArrayList<TagCloudItem>(); 
    	Integer listFlag = 0;
    	
    	for(Object[] a : list)
       	{
    		listFlag ++;
       		
       		TagCloudItem t_Item = new TagCloudItem();
       		t_Item.name = (String) a[0];
       		t_Item.count = (Long)a[1];
       		t_Item.hyperlink = "/requests/search?phrase=" + t_Item.name;
       		
      	    Integer cssFlag = 0;
       	    
       	    if(listFlag%2 == 0)
       	    	cssFlag = (listFlag + 2)/2;
       	    else cssFlag = (listFlag + 1)/2;
       	    
       	    t_Item.CssClass = "tag_big_" + cssFlag.toString();       	    		
       		
       		if(listFlag < 13)
       			tagCloud.add(t_Item);
       	}  
       	
    	Collections.shuffle(tagCloud);
    	
    	renderArgs.put("tagCloudBigRequest", tagCloud);  
    }
    
    /*
     * gerekli query hazırlanıp District tablosundan name, district_id
     * ve county_id'ler DistrictItem objelerine doldurulur,
     * doldurulan objeler listeye atılır ve bu liste
     * "districts" adı altında rendarArgs scope'una atılır
     * 
     */

    
    @Before
    static void setDistricts() {
    	
    	Query query = JPA.em().createQuery("select name,id,county.id from District order by county_id, name");
    	List<Object[]> list = query.getResultList();
       	
    	List<DistrictItem> districts = new ArrayList<DistrictItem>(); 
    	
    	for(Object[] a : list)
       	{    		       		
    		DistrictItem t_Item = new DistrictItem();
       		t_Item.name = (String) a[0];
       		t_Item.district_id = (Long)a[1];
       		t_Item.county_id = (Long)a[2];
       		
       		districts.add(t_Item);
       	}  
       	    	
    	renderArgs.put("districts", districts);  
    }       
    
    /*
     * County tablosundan name,id,city.id ler
     * city.id ve name e göre sıralı olarak alınır
     * CountyItem objelerine doldurulur, önce name="All"
     * id=0,city.id=0 olan obje ardından da
     * tablodan gelen objeler bir Listeye
     * atılır.Liste "counties" adı alrında renderArgs scopuna atılır. 
     * 
     */

    
    @Before
    static void setCounties() {
    	
    	Query query = JPA.em().createQuery("select name,id,city.id from County order by city_id, name");
    	List<Object[]> list = query.getResultList();
       	
    	List<CountyItem> counties = new ArrayList<CountyItem>(); 
    	
    	CountyItem t_Item_All = new CountyItem();
    	t_Item_All.name = "All";
    	t_Item_All.county_id = Long.getLong("0");
    	t_Item_All.city_id = Long.getLong("0");
   		
   		counties.add(t_Item_All);
    	
    	for(Object[] a : list)
       	{    		       		
    		CountyItem t_Item = new CountyItem();
       		t_Item.name = (String) a[0];
       		t_Item.county_id = (Long)a[1];
       		t_Item.city_id = (Long)a[2];
       		
       		counties.add(t_Item);
       	}  
       	    	
    	renderArgs.put("counties", counties);  
    }       
    
    @Before
    static void setRecoccuranceHour() {
    	
    	Query query = JPA.em().createQuery("select hour_min,val from Recoccurancehour order by val");
    	List<Object[]> list = query.getResultList();
       	
    	List<Recoccurancehouritem> hours = new ArrayList<Recoccurancehouritem>(); 
    	
    	for(Object[] a : list)
       	{    		       		
    		Recoccurancehouritem t_Item = new Recoccurancehouritem();
       		t_Item.hour_min = (String) a[0];
       		t_Item.val = (Long)a[1];
       		
       		hours.add(t_Item);
       	}  
       	    	
    	renderArgs.put("recoccurancehouritems", hours);  
    }      
    /*
     * Security.isconnected() cagiriliyor
     * Security.connected.first() ile baglantiyi yapanın e-maili alınıyor
     * play.dp.jpa.Model.find() fonksiyonu email uzerinden çalıştırılıyor
     */
    public static User getConnectedUser() {
		if (Security.isConnected()) {
		    return User.find("byEmail", Security.connected()).first();
		}
		return null;
    }
   
    @Before
    static void setSubcategoryList()
    {
    	Query query=JPA.em().createQuery("select id, categoryName, subcategoryName from Subcategory order by subcategoryName");
    	List<Object[]> list = query.getResultList();
       	
    	List<Subcategory> subcategories = new ArrayList<Subcategory>(); 
    	
    	/*
    	 * Adding blank item
    	 */
    	
    	Subcategory blank_Item = new Subcategory(0," Lutfen Kategori Seciniz", "");

    	subcategories.add(blank_Item);
    	
    	for(Object[] a : list)
       	{    		       		
    		Subcategory t_Item = new Subcategory((Long) a[0],(String) a[1], (String) a[2]);

       		
    		 subcategories.add(t_Item);
       	}  
    	renderArgs.put("subcategoryList", subcategories);
    	
    	
    }
    
}
