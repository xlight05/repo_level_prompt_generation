package org.jjsc.compiler.namespace;

import java.util.HashSet;
import java.util.Set;

import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.utils.Name;
/**
 * Utility class for new names generation.
 * Clients should create instances of this class using 
 * factory methods provided.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
abstract class NamesGenerator {
	/**
	 * @return generator for handeling java types names.
	 */
	static NamesGenerator createForTypesNames(){
		return new TypesNamesGenerator();
	}
	/**
	 * @return generator for the local variables names.
	 */
	static NamesGenerator createLocal(){
		return new LocalNamesGenerator();
	}
	
	private NamesGenerator(){}
	/**
	 * Generates short name for fully qualified name.
	 * @param qName
	 * @return new short name for fully qualified name passed.
	 */
	abstract String get(String qName);
	
	private static class TypesNamesGenerator extends NamesGenerator {
		private final Set<String> registered;
		
		private TypesNamesGenerator(){
			registered = new HashSet<String>();
		}

		@Override
		String get(final String qName) {
			String name = Name.getSimpleName(qName);
			if(registered.contains(name)||
				JavaScriptLang.RESERVED_KEYWORDS.contains(name)){
				int i;
				for(i = 0;registered.contains(name+i);i++);
				name = name+i;
			}
			registered.add(name);
			return name;
		}
		
	}
	
	private static class LocalNamesGenerator extends NamesGenerator {
		private static char[] LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		// this represents a combination of LETTERS.length^5 variables.
		// imposible to be exceed
		private int[] data = new int[4]; 
		private int cursor = 0;

		@Override
		String get(final String qName) {
			String name = next();
			while(JavaScriptLang.RESERVED_KEYWORDS.contains(name)){
				name = next();
			}
			return name;
		}

		private String next() {
			final int bound = LETTERS.length-1;
			if(data[cursor]<LETTERS.length){
				return create();
			}
			for(int i = cursor-1;i>=0;i--){
				if(data[i]<bound){
					data[i]++;
					fillZeroesFrom(i+1);
					return create();
				}
			}
			return increateLength();
		}

		private void fillZeroesFrom(final int from) {
			for(int i=from;i<=cursor;i++){
				data[i] = 0;
			}
		}

		private String increateLength() {
			cursor++;
			if(cursor>=data.length){
				throw new IndexOutOfBoundsException(
					"Too much variables for this generator - consider to use custom one");
			}
			fillZeroesFrom(0);
			return create();
		}

		private String create() {
			char[] rez = new char[cursor+1];
			for(int i=0;i<=cursor;i++){
				rez[i] = LETTERS[data[i]];
			}
			data[cursor]++;
			return new String(rez);
		}
		
	}
}
