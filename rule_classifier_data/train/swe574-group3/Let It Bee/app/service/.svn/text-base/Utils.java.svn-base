package service;
import java.util.ArrayList;
import java.util.List;

import models.Tag;

/*
 * Utils sınıfı genel kullanım için yazılmış metodları içerir.
 * İçindeki metodlar statik tanımlanır. 
 */
public class Utils
{
	/*
	 *TAG_SEPARATOR değişkeni statik olarak tanımlanır ve parseTags metodu bu tanıma göre işlem yapar.
	 */
    private static final String TAG_SEPARATOR = "[ ,]";

    /*
	 *Ardı ardına dizilmiş Tag'leri bir string olarak alan bu metod, TAG_SEPARATOR değişkenini kullanarak 
	 *input stringi split eder. Elde edilen array'i string listesine dönüştürüp döndürür. 
	 */
    public static List<String> parseTags(String tags)
    {
        List<String> tagsList = new ArrayList<String>();
        if (tags == null) {
            return tagsList;
        }

        String[] tagsArr = tags.split(TAG_SEPARATOR);
        for (String tagString : tagsArr) {
            tagsList.add(tagString.trim().toLowerCase());
        }
        return tagsList;
    }
}
