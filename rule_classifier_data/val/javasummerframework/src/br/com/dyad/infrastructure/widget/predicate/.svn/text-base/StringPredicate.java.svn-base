package br.com.dyad.infrastructure.widget.predicate;

import org.apache.commons.collections.Predicate;

import br.com.dyad.commons.data.ComparatorFieldGetter;

public class StringPredicate implements Predicate{
	ComparatorFieldGetter comparator;
	String fieldName;
	String token;
	public static String[] especialChars = {
		"á", "é", "í", "ó", "ú",
		"ã", "õ", "â", "ê", "ô",  
		"ç"
	};
	public static String[] standarChars = {
		"a", "e", "i", "o", "u",
		"a", "o", "a", "e", "o",  
		"c"
	};
	
	public StringPredicate(String fieldName, String token, ComparatorFieldGetter comparator){
		this.fieldName = fieldName;
		this.comparator = comparator;
		if ( token == null ){
			throw new RuntimeException("informe a string base para ser comparada.");
		}
		this.token = token.toLowerCase();
		for (int i = 0; i < especialChars.length; i++) {
			this.token = this.token.replaceAll(especialChars[i], standarChars[i]);
		}
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		String token2 = (String)comparator.getComparateValue(arg0, fieldName);
		if ( token2 == null && token == null ){
			return true;
		}
		if ( token2 == null || token == null ){
			return false;
		}
		token2 = token2.toLowerCase();
		for (int i = 0; i < especialChars.length; i++) {
			token2 = token2.replaceAll(especialChars[i], standarChars[i]);
		}		
		return token2.indexOf(token) != -1;
	}
	
	/*public static void main(String[] args) {
		StringPredicate p = new StringPredicate("danilo");
		ArrayList list = new ArrayList();
		
		for (int i = 0; i < 10000; i++) {
			list.add("teste açúcar pé mão" + i);
		}
		list.add("danilo");
		List result = (List)CollectionUtils.select(list, p);
		System.out.println(result.size());
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println(object);
		}
	}*/

}
