package service;

import java.util.List;

import models.Tag;
import models.User;

/*
 * Tag'i description'ı ve başlığı olan tüm nesneler için kullanılabilecek bir interface'dir.
 *  Bu nterface'in metodlarını implement edebilen bir nesne, karşılaştırılabilir nesne olarak sayılır 
 *  ve karşılaştırma işlemlerinde input olarak kullanılabilir.
 */
public interface Matchable
{
	/*
	 * İlgili nesnenin tag'lerini getirir. 
	 */	
    List<Tag> getTags();
    /*
     * İlgili nesnenin tanımını getirir. 
     */
    String getDescription();
    /*
     * İlgili nesnenin başlığını getirir. 
     */
    String getTitle();
    
    /*
     *ilgili user'ın balance'ını getirir 
     */
    
    int getUserBalance();
    /*
     * ilgili user'ın offe/request'ten uzaklığını getirir.
     */
    float getUserDistance();
    /*
     * sıralama puanlarını ilgili offer/request'e yerleştirir
     */
    void setSortingpoints(float points);
    
    float getSortingPoints();
    
    User getItemUser();
    
}
