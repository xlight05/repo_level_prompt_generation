
package com.xiaonei.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 2008-6-16 下午06:03:04
 */
public final class XiaoneiSignatureUtil {
  private XiaoneiSignatureUtil() {
  }

  /**
   * 
   * @param reqParams
   * @return
   */
  public static Map<String, CharSequence> extractXiaoneiParamsFromArray(Map<CharSequence, CharSequence[]> reqParams) {
    if (null == reqParams)
      return null;
    Map<String,CharSequence> result = new HashMap<String,CharSequence>(reqParams.size());
    for (Map.Entry<CharSequence,CharSequence[]> entry : reqParams.entrySet()) {
      String key = entry.getKey().toString();
      if (XiaoneiParam.isInNamespace(key)) {
        CharSequence[] value = entry.getValue();
        if (value.length > 0)
          result.put(key, value[0]);
      }
    }
    return result;
  }
  
  /**
   * 
   * @param reqParams
   * @return
   */
  public static Map<String, CharSequence> extractXiaoneiParamsFromStandardsCompliantArray(Map<String, String[]> reqParams) {
    if (null == reqParams)
      return null;
    Map<String,CharSequence> result = new HashMap<String,CharSequence>(reqParams.size());
    for (Map.Entry<String,String[]> entry : reqParams.entrySet()) {
      String key = entry.getKey();
      if (XiaoneiParam.isInNamespace(key)) {
          String[] value = entry.getValue();
        if (value.length > 0)
          result.put(key, value[0]);
      }
    }
    return result;
  }

  /**
   * 
   * @param reqParams
   * @return
   */
  public static Map<String, CharSequence> extractXiaoneiNamespaceParams(Map<CharSequence, CharSequence> reqParams) {
    if (null == reqParams)
      return null;
    Map<String,CharSequence> result = new HashMap<String,CharSequence>(reqParams.size());
    for (Map.Entry<CharSequence,CharSequence> entry : reqParams.entrySet()) {
      String key = entry.getKey().toString();
      if (XiaoneiParam.isInNamespace(key))
        result.put(key, entry.getValue());
    }
    return result;
  }

  /**
   * 
   * @param reqParams
   * @return
   */
  public static EnumMap<XiaoneiParam, CharSequence> extractXiaoneiParams(Map<CharSequence, CharSequence> reqParams) {
    if (null == reqParams)
      return null;

    EnumMap<XiaoneiParam, CharSequence> result =
      new EnumMap<XiaoneiParam, CharSequence>(XiaoneiParam.class);
    for (Map.Entry<CharSequence, CharSequence> entry: reqParams.entrySet()) {
      XiaoneiParam matchingXiaoneiParam = XiaoneiParam.get(entry.getKey().toString());
      if (null != matchingXiaoneiParam) {
        result.put(matchingXiaoneiParam, entry.getValue());
      }
    }
    return result;
  }

  /**
   * 
   * @param params
   * @param secret
   * @return
   */
  public static boolean verifySignature(EnumMap<XiaoneiParam, CharSequence> params, String secret) {
    if (null == params || params.isEmpty() )
      return false;
    CharSequence sigParam = params.remove(XiaoneiParam.SIGNATURE);
    return (null == sigParam) ? false : verifySignature(params, secret, sigParam.toString()); 
  }

  /**
   * 
   * @param params
   * @param secret
   * @param expected
   * @return
   */
  public static boolean verifySignature(EnumMap<XiaoneiParam, CharSequence> params, String secret,
                                        String expected) {
    assert !(null == secret || "".equals(secret));
    if (null == params || params.isEmpty() )
      return false;
    if (null == expected || "".equals(expected)) {
      return false;
    }
    params.remove(XiaoneiParam.SIGNATURE);
    List<String> sigParams = convertXiaoneiParams(params.entrySet());
    return verifySignature(sigParams, secret, expected);
  }

  /**
   * 
   * @param params
   * @param secret
   * @return
   */
  public static boolean verifySignature(Map<String, CharSequence> params, String secret) {
    if (null == params || params.isEmpty() )
      return false;
    CharSequence sigParam = params.remove(XiaoneiParam.SIGNATURE.toString());
    return (null == sigParam) ? false : verifySignature(params, secret, sigParam.toString()); 
  }
  
  /**
   * 
   * @param requestParams
   * @param secret
   * @param expected
   * @return
   */
  public static boolean autoVerifySignature(Map<String, String[]> requestParams, String secret, String expected) {
      Map<String, CharSequence> convertedMap = extractXiaoneiParamsFromStandardsCompliantArray(requestParams);
      return verifySignature(convertedMap, secret, expected);
  }
  
  /**
   * 
   * @param requestParams
   * @param secret
   * @return
   */
  public static boolean autoVerifySignature(Map<String, String[]> requestParams, String secret) {
      String expected = requestParams.get("xn_sig")[0];
      return autoVerifySignature(requestParams, secret, expected);
  }

  /**
   * 
   * @param params
   * @param secret
   * @param expected
   * @return
   */
  public static boolean verifySignature(Map<String, CharSequence> params, String secret,
                                        String expected) {
    assert !(null == secret || "".equals(secret));
    if (null == params || params.isEmpty() )
      return false;
    if (null == expected || "".equals(expected)) {
      return false;
    }
    params.remove(XiaoneiParam.SIGNATURE.toString());
    List<String> sigParams = convert(params.entrySet());
    return verifySignature(sigParams, secret, expected);
  }


  private static boolean verifySignature(List<String> sigParams, String secret, String expected) {
    if (null == expected || "".equals(expected))
      return false;
    String signature = generateSignature(sigParams, secret);
    return expected.equals(signature);    
  }

  /**
   * 
   * @param entries
   * @return
   */
  public static List<String> convert(Collection<Map.Entry<String, CharSequence>> entries) {
    List<String> result = new ArrayList<String>(entries.size());
    for (Map.Entry<String, CharSequence> entry: entries)
      result.add(XiaoneiParam.stripSignaturePrefix(entry.getKey()) + "=" + entry.getValue());
    return result;
  }

  /**
   * 
   * @param entries
   * @return
   */
  public static List<String> convertXiaoneiParams(Collection<Map.Entry<XiaoneiParam, CharSequence>> entries) {
    List<String> result = new ArrayList<String>(entries.size());
    for (Map.Entry<XiaoneiParam, CharSequence> entry: entries)
      result.add(entry.getKey().getSignatureName() + "=" + entry.getValue());
    return result;
  }

  /**
   * 
   * @param params
   * @param secret
   * @return
   */
  public static String generateSignature(List<String> params, String secret) {
    StringBuffer buffer = new StringBuffer();
    Collections.sort(params);
    for (String param: params) {
      buffer.append(param);
    }

    buffer.append(secret);
    try {
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
      StringBuffer result = new StringBuffer();
      try {
          for (byte b: md.digest(buffer.toString().getBytes("UTF-8"))) {
            result.append(Integer.toHexString((b & 0xf0) >>> 4));
            result.append(Integer.toHexString(b & 0x0f));
          }
      }
      catch (UnsupportedEncodingException e) {
          for (byte b: md.digest(buffer.toString().getBytes())) {
              result.append(Integer.toHexString((b & 0xf0) >>> 4));
              result.append(Integer.toHexString(b & 0x0f));
            }
      }
      return result.toString();
    }
    catch (java.security.NoSuchAlgorithmException ex) {
      System.err.println("MD5 does not appear to be supported" + ex);
      return "";
    }
  }
}
