package br.com.dyad.infrastructure.widget.predicate;

import java.util.Date;

import org.apache.commons.collections.Predicate;

import br.com.dyad.commons.data.ComparatorFieldGetter;

public class SimpleDatePredicate implements Predicate {
	ComparatorFieldGetter comparator;
	String fieldName;
	String token;
	
	public SimpleDatePredicate(String fieldName, String token, ComparatorFieldGetter comparator){
		this.fieldName = fieldName;
		this.comparator = comparator;
		if ( token == null ){
			throw new RuntimeException("informe a string base para ser comparada.");
		}
		this.token = token;
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		Date token2 = (Date)comparator.getComparateValue(arg0, fieldName);
		if ( token2 == null && token == null ){
			return true;
		}
		if ( token2 == null || token == null ){
			return false;
		}
		String strDate = token2.toLocaleString().substring(0, 10).replace("/", "");  
	
		return strDate.indexOf(token) != -1;
	}
}
