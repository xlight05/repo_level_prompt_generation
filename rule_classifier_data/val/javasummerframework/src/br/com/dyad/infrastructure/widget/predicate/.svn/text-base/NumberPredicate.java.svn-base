package br.com.dyad.infrastructure.widget.predicate;

import org.apache.commons.collections.Predicate;

import br.com.dyad.commons.data.ComparatorFieldGetter;

public class NumberPredicate implements Predicate{
	ComparatorFieldGetter comparator;
	String fieldName;
	Double token;
	
	public NumberPredicate(String fieldName, Double token, ComparatorFieldGetter comparator){
		this.fieldName = fieldName;
		this.comparator = comparator;
		if ( token == null ){
			throw new RuntimeException("informe a string base para ser comparada.");
		}
		this.token = token;
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		Double token2 = (Double)comparator.getComparateValue(arg0, fieldName);
		if ( token2 == null && token == null ){
			return true;
		}
		if ( token2 == null || token == null ){
			return false;
		}				
		return token2.equals((token));
	}
}
