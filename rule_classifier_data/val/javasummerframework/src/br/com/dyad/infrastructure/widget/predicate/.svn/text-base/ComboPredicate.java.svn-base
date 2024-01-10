package br.com.dyad.infrastructure.widget.predicate;

import org.apache.commons.collections.Predicate;

import br.com.dyad.commons.data.ComparatorFieldGetter;

public class ComboPredicate implements Predicate {
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
	
	public ComboPredicate(String fieldName, String token, ComparatorFieldGetter comparator){
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
		/*Long ent = (Long)comparator.getComparateValue(arg0, fieldName);
		String token2 = ent.toString();
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
		return token2.indexOf(token) != -1;*/
		return false;
	}
}
