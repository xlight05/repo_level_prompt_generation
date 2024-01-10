package com.xiaonei.api;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 */
public abstract class AbstractXiaoneiRestClient<T>
  implements IXiaoneiRestClient<T> {

	  /**
	   * 
	   */
	  public static int NUM_AUTOAPPENDED_PARAMS = 6;
	  

	  static Map<ApplicationProperty, String> parseProperties(String json) {
	      Map<ApplicationProperty, String> result = new HashMap<ApplicationProperty, String>();
	      if (json == null) {
	          return null;
	      }
	      if (json.matches("\\{.*\\}")) {
	          json = json.substring(1, json.lastIndexOf("}"));
	      }
	      else {
	          json = json.substring(1, json.lastIndexOf("]"));
	      }
	      String[] parts = json.split("\\,");
	      for (String part : parts) {
	          parseFragment(part, result);
	      }
	      
	      return result;
	  }
	  
	  private static void parseFragment(String fragment, Map<ApplicationProperty, String> result) {
	      if (fragment.startsWith("{")) {
	          fragment = fragment.substring(1, fragment.lastIndexOf("}"));
	      }
	      String keyString = fragment.substring(1);
	      keyString = keyString.substring(0, keyString.indexOf('"'));
	      ApplicationProperty key = ApplicationProperty.getPropertyForString(keyString);
	      String value = fragment.substring(fragment.indexOf(":") + 1).replaceAll("\\\\", "");  //strip escape characters
	      if (key.getType().equals("string")) {
	          result.put(key, value.substring(1, value.lastIndexOf('"')));
	      }
	      else {
	          if (value.equals("1")) {
	              result.put(key, "true");
	          }
	          else {
	              result.put(key, "false");
	          }
	      }
	  }
}
