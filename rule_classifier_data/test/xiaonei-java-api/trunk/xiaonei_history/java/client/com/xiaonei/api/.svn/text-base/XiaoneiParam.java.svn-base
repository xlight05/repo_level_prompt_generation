
package com.xiaonei.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 2008-6-16 下午06:06:45
 */
public enum XiaoneiParam
  implements CharSequence {
  SIGNATURE,
  USER("user"),
  SESSION_KEY("session_key"),
  EXPIRES("expires"),
  IN_CANVAS("in_canvas"),
  IN_IFRAME("in_iframe"),
  IN_PROFILE("profile"),
  TIME("time"),
  FRIENDS("friends"),
  ADDED("added"),
  PROFILE_UPDATE_TIME("profile_update_time"),
  API_KEY("api_key"),
  //SMS sig params
  SMS("sms"),
  MESSAGE("message"),
  SMS_SID("sms_sid"),
  SMS_NEW_USER("sms_new_user"),
  POSITION_FIX("position_fix")
  ;

  private static Map<String, XiaoneiParam> _lookupTable =
    new HashMap<String, XiaoneiParam>(XiaoneiParam.values().length);
  static {
    for (XiaoneiParam param: XiaoneiParam.values()) {
      _lookupTable.put(param.toString(), param);
    }
  }

  /**
   * 
   * @param key
   * @return
   */
  public static XiaoneiParam get(String key) {
    return isInNamespace(key) ? _lookupTable.get(key) : null;
  }

  /**
   * 
   * @param key
   * @return
   */
  public static boolean isInNamespace(String key) {
    return null != key && key.startsWith(XiaoneiParam.SIGNATURE.toString());
  }
  
  /**
   * 
   * @param key
   * @return
   */
  public static boolean isSignature(String key) {
    return SIGNATURE.equals(get(key));
  }

  private String _paramName;
  private String _signatureName;

  private XiaoneiParam() {
    this._paramName = "xn_sig";
  }

  private XiaoneiParam(String name) {
    this._signatureName = name;
    this._paramName = "xn_sig_" + name;
  }

  public char charAt(int index) {
    return this._paramName.charAt(index);
  }

  public int length() {
    return this._paramName.length();
  }

  public CharSequence subSequence(int start, int end) {
    return this._paramName.subSequence(start, end);
  }

  public String toString() {
    return this._paramName;
  }
  
  /**
   * 
   * @return
   */
  public String getSignatureName() {
    return this._signatureName;
  }
  
  /**
   * 
   * @param paramName
   * @return
   */
  public static String stripSignaturePrefix(String paramName) {
    if (paramName != null && paramName.startsWith("xn_sig_")) {
      return paramName.substring(7);
    }
    return paramName;
  }
}
