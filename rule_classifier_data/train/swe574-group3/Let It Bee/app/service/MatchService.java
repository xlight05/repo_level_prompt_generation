package service;

import java.util.ArrayList;
import java.util.List;

import controllers.BaseController;

import models.Offer;
import models.Tag;
import models.User;

public class MatchService
{
	/*
	 * Bu metod karşılaştırılabilecek olan yani Matchable interface'inden türeyen
	 * nesnelerin listesini alır. Buna ek olarak bir de karşılaştırma cümlesi alır.
	 * Alınan karşılaştırma cümlesi önce tag'lerine ayrılır.
	 * Metoda parametre olarak gelen nesne listesindeki tüm nesneler için, 
	 * o nesnenin tag'i ile input tag'ler karşılaştırılır
     * Eğer tag'leri eşleşiyorsa, o nesnenin description ve title'ı karşılaştırılır.
	 * Onlar da eşleşiyorsa o nesne, eşleşen nesneler listesine eklenir.
	 * Ardından tüm input nesneleri için tekrar döngüye girilir.
	 * Sıradaki nesnenin tag'leri input tag'ler ile eşleşiyorsa,
	 * description ve title'ı alınır. Description ve Title'ından en az birisi eşleşiyorsa,
	 * eşleşenler listesine eklenir. Aynı nesneyi ikinci kez eklememek amacıyla, 
	 * bu nesnenin listede olup olmadığı kontrol edilir.
	 * Tüm input nesneleri için 3. kez döngüye girilir ve bu sefer,
	 * tag'leri description'ı veya title'ından en az biri eşleşen nesneler seçilerek,
	 * eşleşenler listesine eklenir. İkinci kez ekleme kontrolü burada da yapılır.
	 * Eşleşenler listesi şu sırayla oluşturulmuş olur:
	 * 1.Hem tag'leri, hem description'ı hem title'ı eşleşen nesneler
	 * 2.Tag'lerine ek oalrak description veya title'ından en az birisi eşleşen nesneler
	 * 3. Tag, description veya title'dan en az birisi eşleşen nesneler
	 * Oluşturulan liste döndürülür.
	 */
	public static <M extends Matchable> List<M> match(List<M> allItems, String phrase)
    {
		/*
		 * aramada kullanilan tagler kelimlere parcalanir
		 */
	    List<String> tags = Utils.parseTags(phrase);

        List<M> matchedItems = new ArrayList<M>();    
        
        /*
         * GENEL YORUM(ÖMER)
         * önceki algoritma sanırım hatalı zira 4 tane ortak tag bulunarsa listeye eklemiyor
         * 3 tane bulunursa listebasına ekleniyor
         * 2 tane bulunurasa title ile phrase karsilastiriliyor, burada title.uppercase ile arama phrase karsilasitiriliyor
         * tam tersine phrase title'ı iceriyor mu bakılması lazım.
         * ve kelimelere bakılması lazım cümle tıpatıp aynı olmasa da olur
         * ama burada bu şekilde yapılmamış.
         * 1 tane bulursa da aynı şekilde description ile karşılaştırılıyor
         * 
         */
        
        /*
         * 
         * ÖNEMLİ: Algoritmayı değiştirdim (kırptım)
         * 
         */
        
    
        
        for(M item : allItems) {
            Integer foundCount = 0;
            
            for(Tag tag : item.getTags()) {
                for(String tagString : tags) {
                    if(tag.name.toUpperCase().contains(tagString.toUpperCase())) {
                    	foundCount++;
                    	System.out.println("foundcount tag("+tag+") sebebiyle arttı:");
                        break;
                    }
                }
            }
            
            System.out.println("itemtag'i ile eslesen phrasetag sayısı sebebiyle foundcount="+foundCount);
            

	        String Desc = item.getDescription();
	        
	        if(Desc!= null && Desc.length()>0 && phrase!=null && phrase.length()>0)
	        {
	            if(Desc.toUpperCase().contains(phrase.toUpperCase())) {
	            	System.out.println("arama cümlesi-description eşleşmesi sebebiyle foundcount arttı:"+foundCount);
	            	foundCount++;
	            }
	        }
	        		        
	        String Title = item.getTitle();
	        
	        if(Title!= null && Title.length()>0 && phrase!=null && phrase.length()>0)
	        {
	            if(Title.toUpperCase().contains(phrase.toUpperCase())) {
	            	System.out.println("arama cümlesi-title eşleşmesi sebebiyle foundcount arttı:"+foundCount);
	            	foundCount++;
	            }	
	        }
	        
	        /*
	         * Tag, description veya title 'ı eşleşen sonuç, eşleşme sayısı(foundCount)
	         * sonucun sortingpoint'ine yerleştirilir ve matchedItems listesine eklenir.
	         *  
	         */
	   	            
	        if (foundCount > 0) {
	        	item.setSortingpoints((float)foundCount);
	        	if(!matchedItems.contains(item))
	        		matchedItems.add(item);
	        }
	        
	        
	        
	        
	    }
	    
	    /*
	     * todo su anda taglerine göre arama sonucları sıralı geliyor buna
	     * 1-user balance etkisi (balance'ı yüksek user üste çıkar)
	     * 2-aradaki uzaklık etkisi (daha yakın user yüksek çıkar)
	     * 3-TAGLERI çok eşleşen daha yukarıda çıkar
	     * 
	     */
        
        List<M> ratedItems = new ArrayList<M>();    

	    
	    float sortingPoints=0;
	    
	    for(M item : matchedItems) {
	    	/*
	    	 * item'ın user'ı aramayaı yapan ile aynı kişiyse arama sonuçlarında gösterilmez
	    	 */
	    	if(item.getItemUser().equals(BaseController.getConnectedUser()))
	    	{
	    		System.out.println("ilanı veren kullanıcı:"+item.getItemUser().email); 
	    		continue;
	    	}
	    	/*
	    	 * Yukarıda Tag eşleşme sayısı olan foundCount'u sortingpoints'e yerleştirmiştik
	    	 * Şimdi geri çağırıyoruz, sıralama algoritmasında kullanacağız
	    	 *  
	    	 */
	    	
	    	
	    	sortingPoints=item.getSortingPoints();
	    	/*
	    	 * offer'ı veren kullanıcının balance'i ve arayana uzaklığı gelir
	    	 */
	    	int userBalance=item.getUserBalance();
	    	float userDistance=item.getUserDistance();

	    	System.out.println("userbalance:"+userBalance+"   userdistance="+userDistance+"   sortingPoints="+sortingPoints);
	    	
	    	if(userDistance>0.0000001)
	    	{
	    		sortingPoints=(userBalance*sortingPoints)/userDistance;	
	    	}
	    	else
	    		sortingPoints=userBalance*sortingPoints;
	    	
	    	System.out.println(item.getTitle()+" başlıklı ilan için sorting points="+sortingPoints);
	    	item.setSortingpoints(sortingPoints);
	    	
	    	
	    	
	    	/*
	    	List<User> newUserList=User.getNewUsers(20);
	    	
	    	if(newUserList.contains(item.getItemUser()))
	    			item.setSortingpoints(item.getSortingPoints()*(float)3);

	    		*/
	    	
	    	/*
	    	 *Eğer item sahibinin puanı 1'in üstünde değilse ekleme yapılmaz 
	    	 * 
	    	 */
	    	
	    	if(item.getItemUser().balance>1)
	    	ratedItems.add(item);
	    }
        
        return ratedItems;
    }

	
}