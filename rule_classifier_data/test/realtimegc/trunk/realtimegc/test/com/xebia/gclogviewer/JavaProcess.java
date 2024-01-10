package com.xebia.gclogviewer;

import java.util.ArrayList;
import java.util.Date;

/**
 * start it with verbose:gc (to file) to produce a log
 * 
 * sun: java -verbose:gc -Xloggc:gc.log
 * ibm: java -verbose:gc 2>gc.log
 * @author sander
 *
 */
public class JavaProcess {
	public static void main(String[] args) {
		ArrayList<String> list=new ArrayList<String>();
		for (;;){
			list.add(new Date().toString()+".................");
			if (Math.random()>.7){
				list.remove(0);
			}
			
		}
	}
}
