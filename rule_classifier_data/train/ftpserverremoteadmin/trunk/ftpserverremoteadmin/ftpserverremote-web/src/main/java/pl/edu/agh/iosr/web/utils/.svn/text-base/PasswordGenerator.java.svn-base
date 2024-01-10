/*
 * PasswordGenerator.java
 * 
 * Created on 2008-01-05, 09:01:28
 * 
 */

package pl.edu.agh.iosr.web.utils;

/**
 *
 * Class generating password in user page
 * 
 * @author Monika Nawrot
 */
public class PasswordGenerator {
   
    
    /**
     * Generate password 
     * @return password
     */
    public String generate() {
        String result = "";
        
        int length = 5 + (int) (Math.random() * 5);
        for (int i = 0; i < length ; i++) {
        	int number = (65 + (int) (Math.random() * 57));
        	
        	if (number > 90 && number < 97) {
        		result += (char) 48 + ((int)(Math.random() * 9));
        		continue;
        	}
        	
        	result += (char) number;
        }
        
        return result;
    }
    
}
