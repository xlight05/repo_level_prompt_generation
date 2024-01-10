
package com.xiaonei.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * 2008-6-16 下午05:02:28
 */
public class XiaoneiRestClient implements IXiaoneiRestClient<Document>{
  /**
   * 
   */
  public static final String TARGET_API_VERSION = "1.0";
  /**
   * 
   */
  public static final String ERROR_TAG = "error_response";
  /**
   * 
   */
  public static final String XN_SERVER = "api.xiaonei.com/restserver.do";
  /**
   * 
   */
  public static final String SERVER_ADDR = "http://" + XN_SERVER;
  /**
   * 
   */
  public static final String HTTPS_SERVER_ADDR = "https://" + XN_SERVER;
  /**
   * 
   */
  public static URL SERVER_URL = null;
  /**
   * 
   */
  public static URL HTTPS_SERVER_URL = null;
  static {
    try {
      SERVER_URL = new URL(SERVER_ADDR);
      HTTPS_SERVER_URL = new URL(HTTPS_SERVER_ADDR);
    }
    catch (MalformedURLException e) {
      System.err.println("MalformedURLException: " + e.getMessage());
      System.exit(1);
    }
  }
  
  private static final Map<XiaoneiMethod, String> RETURN_TYPES;
  static {
      RETURN_TYPES = new HashMap<XiaoneiMethod, String>();
      Method[] candidates = XiaoneiRestClient.class.getMethods();
      //this loop is inefficient, but it only executes once per JVM, so it doesn't really matter
      for (XiaoneiMethod method : EnumSet.allOf(XiaoneiMethod.class)) {
          String name = method.methodName();
          name = name.substring(name.indexOf(".") + 1);
          name = name.replace(".", "_");
          for (Method candidate : candidates) {
              if (candidate.getName().equalsIgnoreCase(name)) {
                  String typeName = candidate.getReturnType().getName().toLowerCase();
                  //possible types are Document, String, Boolean, Integer, Long, void
                  if (typeName.indexOf("document") != -1) {
                      RETURN_TYPES.put(method, "default");
                  }
                  else if (typeName.indexOf("string") != -1) {
                      RETURN_TYPES.put(method, "string");
                  }
                  else if (typeName.indexOf("bool") != -1) {
                      RETURN_TYPES.put(method, "bool");
                  }
                  else if (typeName.indexOf("long") != -1) {
                      RETURN_TYPES.put(method, "long");
                  }
                  else if (typeName.indexOf("int") != -1) {
                      RETURN_TYPES.put(method, "int");
                  }
                  else if ((typeName.indexOf("applicationpropertyset") != -1) || (typeName.indexOf("list") != -1) 
                      || (typeName.indexOf("url") != -1) || (typeName.indexOf("map") != -1) 
                      || (typeName.indexOf("object") != -1)) {
                      //we don't autobox these for now, the user can parse them on their own
                      RETURN_TYPES.put(method, "default");
                  }
                  else {
                      RETURN_TYPES.put(method, "void");
                  }
                  break;
              }
          }
      }
  }

  protected final String _secret;
  protected final String _apiKey;
  protected final URL _serverUrl;
  protected String rawResponse;

  protected String _sessionKey; 
  protected Long _expires; 
  protected boolean _isDesktop = false;
  protected String _sessionSecret; 
  protected int _userId;
  protected int _timeout;
  
  protected List<Integer> friendsList; 
  public Boolean added;        
  

  /**
   * number of params that the client automatically appends to every API call
   */
  public static int NUM_AUTOAPPENDED_PARAMS = 6;
  protected static boolean DEBUG = true;
  protected Boolean _debug = null;

  protected File _uploadFile = null;
  protected static final String CRLF = "\r\n";
  protected static final String PREF = "--";
  protected static final int UPLOAD_BUFFER_SIZE = 512;

  /**
   * 
   * @param apiKey
   * @param secret
   */
  public XiaoneiRestClient(String apiKey, String secret) {
    this(SERVER_URL, apiKey, secret, null);
  }


  /**
   * 
   * @param apiKey
   * @param secret
   * @param sessionKey
   */
  public XiaoneiRestClient(String apiKey, String secret, String sessionKey) {
    this(SERVER_URL, apiKey, secret, sessionKey);
  }
  

  /**
   * 
   * @param serverUrl
   * @param apiKey
   * @param secret
   * @param sessionKey
   */
  public XiaoneiRestClient(URL serverUrl, String apiKey, String secret, String sessionKey) {
    _sessionKey = sessionKey;
    _apiKey = apiKey;
    _secret = secret;
    _serverUrl = (null != serverUrl) ? serverUrl : SERVER_URL;
    _timeout = -1;
    _userId = -1;
  }
  
  /**
   * 
   * @return
   */
  public String _getSessionKey() {
	return _sessionKey;
  }

  /**
   * 
   * @param key
   */
  public void _setSessionKey(String key) {
	_sessionKey = key;
  }

  /**
   * 
   * @return
   */
  public Long _getExpires() {
	return _expires;
  }

  /**
   * 
   * @param _expires
   */
  public void _setExpires(Long _expires) {
	this._expires = _expires;
  }

  /**
   * 
   * @return
   */
  public int _getUserId() {
	return _userId;
  }

  /**
   * 
   * @param id
   */
  public void _setUserId(int id) {
	_userId = id;
  }

  /**
   * 
   * @return
   */
  public List<Integer> _getFriendsList() {
	return friendsList;
  }

  /**
   * 
   * @param friendsList
   */
  public void _setFriendsList(List<Integer> friendsList) {
	this.friendsList = friendsList;
  }


  /**
   * 
   * @return
   */
  public String getResponseFormat() {
      return "xml";
  }

  /**
   * 
   * @param isDebug
   */
  public static void setDebugAll(boolean isDebug) {
    XiaoneiRestClient.DEBUG = isDebug;
  }

  /**
   * 
   */
  public void setDebug(boolean isDebug) {
    _debug = isDebug;
  }

  /**
   * 
   */
  public boolean isDebug() {
    return (null == _debug) ? XiaoneiRestClient.DEBUG : _debug.booleanValue();
  }

  /**
   * 
   */
  public boolean isDesktop() {
    return this._isDesktop;
  }

  /**
   * 
   */
  public void setIsDesktop(boolean isDesktop) {
    this._isDesktop = isDesktop;
  }

  /**
   * 
   * @param n
   * @param prefix
   */
  public static void printDom(Node n, String prefix) {
    String outString = prefix;
    if (n.getNodeType() == Node.TEXT_NODE) {
      outString += "'" + n.getTextContent().trim() + "'";
    }
    else {
      outString += n.getNodeName();
    }
    if (DEBUG) {
        System.out.println(outString);
    }
    NodeList children = n.getChildNodes();
    int length = children.getLength();
    for (int i = 0; i < length; i++) {
      XiaoneiRestClient.printDom(children.item(i), prefix + "  ");
    }
  }
  /**
   * 用逗号分隔成串
   * @param iterable
   * @return
   */
  private static CharSequence delimit(Collection<?> iterable) {
    if (iterable == null || iterable.isEmpty())
      return null;

    StringBuilder buffer = new StringBuilder();
    boolean notFirst = false;
    for (Object item: iterable) {
      if (notFirst)
        buffer.append(",");
      else
        notFirst = true;
      buffer.append(item.toString());
    }
    return buffer;
  }

  protected static CharSequence delimit(Collection<Map.Entry<String, CharSequence>> entries,
                                        CharSequence delimiter, CharSequence equals,
                                        boolean doEncode) {
    if (entries == null || entries.isEmpty())
      return null;

    StringBuilder buffer = new StringBuilder();
    boolean notFirst = false;
    for (Map.Entry<String, CharSequence> entry: entries) {
      if (notFirst)
        buffer.append(delimiter);
      else
        notFirst = true;
      CharSequence value = entry.getValue();
      buffer.append(entry.getKey()).append(equals).append(doEncode ? encode(value) : value);
    }
    return buffer;
  }

  /**
   * 
   * @param method
   * @param paramPairs
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  protected Document callMethod(IXiaoneiMethod method,
                                Pair<String, CharSequence>... paramPairs) throws XiaoneiException,
                                                                                 IOException {
    return callMethod(method, Arrays.asList(paramPairs));
  }
  
  
  /**
   * 
   * @param method
   * @param paramPairs
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  protected Document callMethod(IXiaoneiMethod method,
                                Collection<Pair<String, CharSequence>> paramPairs) throws XiaoneiException,
                                                                                          IOException {
    this.rawResponse = null;
    HashMap<String, CharSequence> params =
      new HashMap<String, CharSequence>(2 * method.numTotalParams());

    params.put("method", method.methodName());
    params.put("api_key", _apiKey);
    params.put("v", TARGET_API_VERSION);
    if (method.requiresSession() && _sessionKey != null) {
        params.put("call_id", Long.toString(System.currentTimeMillis()));
        params.put("session_key", _sessionKey);
    }
    CharSequence oldVal;
    for (Pair<String, CharSequence> p: paramPairs) {
      oldVal = params.put(p.first, p.second);
      if (oldVal != null)
          System.out.println("For parameter " + p.first + ", overwrote old value " + oldVal +
                " with new value " + p.second + ".");
    }

    assert (!params.containsKey("sig"));
    String signature = generateSignature(XiaoneiSignatureUtil.convert(params.entrySet()), method.requiresSession());
    params.put("sig", signature);
    

    try {
//    	System.out.println("---System.getProperty--"+System.getProperty("javax.xml.parsers.DocumentBuilderFactory"));
    	
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      boolean doHttps = this.isDesktop() && XiaoneiMethod.AUTH_GET_SESSION.equals(method);
      
      InputStream data = postRequest(method.methodName(), params, doHttps, /* doEncode */true);

      BufferedReader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
      StringBuffer buffer = new StringBuffer();
      String line;
      boolean insideTagBody = false;
      while ((line = in.readLine()) != null) {
    	  if (true) {
    		  if (line.trim().startsWith("<") && line.contains(">")) {
    			  insideTagBody = true;
    	  	  }
    	  	  if (line.trim().endsWith(">")) {
    	  		  insideTagBody = false;
    	  	  }
    	  	  if(insideTagBody) {
    	  		  line += ",";
    	  	  }
    	  }
    	  buffer.append(line);
      }

      String xmlResp = new String(buffer);
      this.rawResponse = xmlResp;

      Document doc = builder.parse(new ByteArrayInputStream(xmlResp.getBytes("UTF-8")));
      try {
          doc.normalizeDocument();	
	} catch (Exception e) {
		
	}
      stripEmptyTextNodes(doc);

      if (isDebug())
        XiaoneiRestClient.printDom(doc, method.methodName() + "| "); // TEST
      NodeList errors = doc.getElementsByTagName(ERROR_TAG);
      if (errors.getLength() > 0) {
        int errorCode =
          Integer.parseInt(errors.item(0).getFirstChild().getFirstChild().getTextContent());
        String message = errors.item(0).getFirstChild().getNextSibling().getTextContent();
        System.out.println("Xiaonei returns error code " + errorCode);
        for (Map.Entry<String,CharSequence> entry : params.entrySet())
            System.out.println("  - " + entry.getKey() + " -> " + entry.getValue());
        throw new XiaoneiException(errorCode, message);
      }
      return doc;
    }
    catch (java.net.SocketException ex) {
        System.err.println("Socket exception when calling xiaonei method: " + ex.getMessage());
    }
    catch (javax.xml.parsers.ParserConfigurationException ex) {
        System.err.println("huh?");
        ex.printStackTrace();
    }
    catch (org.xml.sax.SAXException ex) {
    	ex.printStackTrace();
      throw new IOException("error parsing xml");
    }
    return null;
  }

  /**
   * 
   */
  public String getRawResponse() {
      String result = this.rawResponse;
      this.rawResponse = null;
      return result;
  }

  /**
   * 
   * @param n
   */
  private static void stripEmptyTextNodes(Node n) {
    NodeList children = n.getChildNodes();
    int length = children.getLength();
    for (int i = 0; i < length; i++) {
      Node c = children.item(i);
      if (!c.hasChildNodes() && c.getNodeType() == Node.TEXT_NODE &&
          c.getTextContent().trim().length() == 0) {
        n.removeChild(c);
        i--;
        length--;
        children = n.getChildNodes();
      }
      else {
        stripEmptyTextNodes(c);
      }
    }
  }

  private String generateSignature(List<String> params, boolean requiresSession) {
    String secret = (isDesktop() && requiresSession) ? this._sessionSecret : this._secret;
    return XiaoneiSignatureUtil.generateSignature(params, secret);
  }

  private static String encode(CharSequence target) {
    String result = (target != null) ? target.toString() : "";
    try {
      result = URLEncoder.encode(result, "UTF8");
    }
    catch (UnsupportedEncodingException e) {
        System.err.println("Unsuccessful attempt to encode '" + result + "' into UTF8");
    }
    return result;
  }

  private InputStream postRequest(CharSequence method, Map<String, CharSequence> params,
                                  boolean doHttps, boolean doEncode) throws IOException {
    CharSequence buffer = (null == params) ? "" : delimit(params.entrySet(), "&", "=", doEncode);
    URL serverUrl = (doHttps) ? HTTPS_SERVER_URL : _serverUrl;
    if (isDebug() && DEBUG) {
        System.out.println(method);
        System.out.println(" POST: ");
        System.out.println(serverUrl.toString());
        System.out.println("/");
        System.out.println(buffer);
    }

    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
    if (this._timeout != -1) {
        conn.setConnectTimeout(this._timeout);
    }
    try {
       conn.setRequestMethod("POST");
//      conn.setRequestMethod("GET");
    }
    catch (ProtocolException ex) {
        System.err.println("huh?");
        ex.printStackTrace();
    }
    conn.setDoOutput(true);
    conn.connect();
    conn.getOutputStream().write(buffer.toString().getBytes());

    return conn.getInputStream();
  }

  /**
   * 
   */
  public Document profile_getXNML(Integer userId) throws XiaoneiException, IOException {
    return this.callMethod(XiaoneiMethod.PROFILE_GET_XNML,
                          new Pair<String, CharSequence>("uid", Integer.toString(userId)));

  }




protected boolean templatizedFeedHandler(Long actorId, XiaoneiMethod method, String titleTemplate, String titleData, String bodyTemplate,
        String bodyData, String bodyGeneral, Collection<? extends IPair<? extends Object, URL>> pictures, String targetIds, Long pageId) throws XiaoneiException, IOException {
    assert (pictures == null || pictures.size() <= 4);

    ArrayList<Pair<String, CharSequence>> params = new ArrayList<Pair<String, CharSequence>>(method.numParams());

    //these are always required parameters
    params.add(new Pair<String, CharSequence>("title_template", titleTemplate));

    //these are optional parameters
    if (titleData != null) {
        params.add(new Pair<String, CharSequence>("title_data", titleData));
    }
    if (bodyTemplate != null) {
        params.add(new Pair<String, CharSequence>("body_template", bodyTemplate));
        if (bodyData != null) {
            params.add(new Pair<String, CharSequence>("body_data", bodyData));
        }
    }
    if (bodyGeneral != null) {
        params.add(new Pair<String, CharSequence>("body_general", bodyGeneral));
    }
    if (pictures != null) {
        int count = 1;
        for (IPair picture : pictures) {
              params.add(new Pair<String, CharSequence>("image_" + count, picture.getFirst().toString()));
              if (picture.getSecond() != null) {
                  params.add(new Pair<String, CharSequence>("image_" + count + "_link", picture.getSecond().toString()));
              }
              count++;
        }
    }
    if (targetIds != null) {
        params.add(new Pair<String, CharSequence>("target_ids", targetIds));
    }
    if (pageId != null) {
        params.add(new Pair<String, CharSequence>("page_actor_id", Long.toString(pageId)));
    }
    this.callMethod(method, params);
    if (this.rawResponse == null) {
        return false;
    }
    return this.rawResponse.contains(">1<"); //a code of '1' indicates success
}

  /**
   * 
   */
  public Document friends_areFriends(int userId1, int userId2) throws XiaoneiException,
                                                                      IOException {
    return this.callMethod(XiaoneiMethod.FRIENDS_ARE_FRIENDS,
                           new Pair<String, CharSequence>("uids1", Integer.toString(userId1)),
                           new Pair<String, CharSequence>("uids2", Integer.toString(userId2)));
  }

  public Document friends_areFriends(Collection<Integer> userIds1,
                                     Collection<Integer> userIds2) throws XiaoneiException,
                                                                          IOException {
    assert (userIds1 != null && userIds2 != null);
    assert (!userIds1.isEmpty() && !userIds2.isEmpty());
    assert (userIds1.size() == userIds2.size());

    return this.callMethod(XiaoneiMethod.FRIENDS_ARE_FRIENDS,
                           new Pair<String, CharSequence>("uids1", delimit(userIds1)),
                           new Pair<String, CharSequence>("uids2", delimit(userIds2)));
  }


	public Document friends_getFriends()
			throws XiaoneiException, IOException {
		Document d = this.callMethod(XiaoneiMethod.FRIENDS_GET_FRIENDS);
		return d;
	}
	public Document friends_get()
		throws XiaoneiException, IOException {
			return this.callMethod(XiaoneiMethod.FRIENDS_GET);
	}

  /**
   * 
   */
  public Document friends_getAppUsers() throws XiaoneiException, IOException {
    return this.callMethod(XiaoneiMethod.FRIENDS_GET_APP_USERS);
  }
  
  /**
   * 
   */
  public Document friends_getAppFriends() throws XiaoneiException, IOException {
    return this.callMethod(XiaoneiMethod.FRIENDS_GET_APP_FRIENDS);
  }

  /**
   * 
   */
  public Document users_getInfo(Integer userId,
                                EnumSet<ProfileField> fields) throws XiaoneiException,
                                                                     IOException {
    // assertions test for invalid params
    assert (userId != null);
    assert (fields != null);
    assert (!fields.isEmpty());

    return this.callMethod(XiaoneiMethod.USERS_GET_INFO,
                           new Pair<String, CharSequence>("uids", String.valueOf(userId)),
                           new Pair<String, CharSequence>("fields", delimit(fields)));
  }

  /**
   * 
   */
  public Document users_getInfo(Collection<Integer> userIds,
                                EnumSet<ProfileField> fields) throws XiaoneiException,
                                                                     IOException {
    // assertions test for invalid params
    assert (userIds != null);
    assert (fields != null);
    assert (!fields.isEmpty());

    return this.callMethod(XiaoneiMethod.USERS_GET_INFO,
                           new Pair<String, CharSequence>("uids", delimit(userIds)),
                           new Pair<String, CharSequence>("fields", delimit(fields)));
  }

  /**
   * 
   */
  public Document users_getInfo(Collection<Integer> userIds,
                                Set<CharSequence> fields) throws XiaoneiException, IOException {
    assert (userIds != null);
    assert (fields != null);
    assert (!fields.isEmpty());

    return this.callMethod(XiaoneiMethod.USERS_GET_INFO,
                           new Pair<String, CharSequence>("uids", delimit(userIds)),
                           new Pair<String, CharSequence>("fields", delimit(fields)));
  }

  /**
   * 
   */
  public int users_getLoggedInUser() throws XiaoneiException, IOException {
    if (this._userId == -1) {
    	Document d = this.callMethod(XiaoneiMethod.USERS_GET_LOGGED_IN_USER);
    	if (d == null) {
        	return 0;
    	}
    	this._userId = Integer.parseInt(d.getFirstChild().getTextContent());
    }
    return this._userId;
  }

  /**
   * 
   */
  public boolean users_isAppAdded() throws XiaoneiException, IOException {
    if (added==null) {
        added = extractBoolean(this.callMethod(XiaoneiMethod.USERS_IS_APP_ADDED));
    }
	return added.booleanValue();
  }

  /**
   * 
   */
  public boolean users_isAppAdded(Integer userId) throws XiaoneiException, IOException {
    if (added==null) {
        added = extractBoolean(this.callMethod(XiaoneiMethod.USERS_IS_APP_ADDED,
        		new Pair<String, CharSequence>("uids", Integer.toString(userId))));
    }
	return added.booleanValue();
  }

  public static boolean extractBoolean(Node doc) {
      if (doc == null) {
          return false;
      }
      String content = doc.getFirstChild().getTextContent();
      return "1".equals(content);
  }
  
 
 /**
  * @deprecated
  */
  public Document requests_sendRequest(Collection<Integer> userIds) throws XiaoneiException, IOException {

	    // assertions test for invalid params
	    assert (userIds != null);
	    return this.callMethod(XiaoneiMethod.REQUESTS_SEND_REQUEST,
	                           new Pair<String, CharSequence>("uids", delimit(userIds)));
}


/**
   * 
   */
  public String auth_createToken() throws XiaoneiException, IOException {
    Document d = this.callMethod(XiaoneiMethod.AUTH_CREATE_TOKEN);
    if (d == null) {
        return null;
    }
    return d.getFirstChild().getTextContent();
  }

  /**
   * 
   */
  public String auth_getSession(String authToken) throws XiaoneiException, IOException {
    Document d =
      this.callMethod(XiaoneiMethod.AUTH_GET_SESSION, 
    		  new Pair<String, CharSequence>("auth_token", authToken.toString()));
    if (d == null) {
        return null;
    }
    this._sessionKey =
        d.getElementsByTagName("session_key").item(0).getFirstChild().getTextContent();
    this._userId =
        Integer.parseInt(d.getElementsByTagName("uid").item(0).getFirstChild().getTextContent());
    if (this._isDesktop)
      this._sessionSecret =
          d.getElementsByTagName("secret").item(0).getFirstChild().getTextContent();
    return this._sessionKey;
  }

  /**
   * 
   */
  public Object getResponsePOJO(){
      if (this.rawResponse == null) {
          return null;
      }
      JAXBContext jc;
      Object pojo = null;
      try {
          jc = JAXBContext.newInstance("com.xiaonei.api.schema");
          Unmarshaller unmarshaller = jc.createUnmarshaller();
          pojo =  unmarshaller.unmarshal(new ByteArrayInputStream(this.rawResponse.getBytes("UTF-8")));
      } catch (JAXBException e) {
          System.err.println("getResponsePOJO() - Could not unmarshall XML stream into POJO 1");
          e.printStackTrace();
      }
      catch (NullPointerException e) {
          System.err.println("getResponsePOJO() - Could not unmarshall XML stream into POJO 2.");
          e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
          System.err.println("getResponsePOJO() - Could not unmarshall XML stream into POJO 3.");
          e.printStackTrace();
      }
      return pojo;
  }

  private void checkError() throws XiaoneiException {
      if (this.rawResponse.contains("error_response")) {
          Integer code = Integer.parseInt(this.rawResponse.substring(this.rawResponse.indexOf("<error_code>") + "<error_code>".length(),
                  this.rawResponse.indexOf("</error_code>") + "</error_code>".length()));
          throw new XiaoneiException(code, "The request could not be completed!");
      }
  }

  private String reconstructValue(String input) {
      if ((input == null) || ("".equals(input))) {
          return null;
      }
      if (input.charAt(0) == '_') {
          return input.substring(1);
      }
      return input;
  }


  private String mapToJsonString(Map<String, CharSequence> map) {
      if (null == map || map.isEmpty()) {
          return null;
      }
      JSONObject titleDataJson = new JSONObject();
      for (String key : map.keySet()) {
          try {
              titleDataJson.put(key, map.get(key));
          }
          catch (Exception ignored) {}
      }
      return titleDataJson.toString();
  }
    
    /**
     * 
     * @param doc
     * @return
     */
    public static int extractInt(Node doc) {
        if (doc == null) {
            return 0;
        }
        return Integer.parseInt(doc.getFirstChild().getTextContent());
    }
    
    /**
     * 
     * @param d
     * @return
     */
    public static String extractString(Node d) {
        if (d == null) {
            return null;
        }
        return d.getFirstChild().getTextContent();
    }

    public boolean profile_setProfileXNML(CharSequence xnmlMarkup) throws XiaoneiException, IOException {
        return this.profile_setXNML(this.users_getLoggedInUser(), xnmlMarkup == null ? null : xnmlMarkup.toString());
    }

    public boolean profile_setProfileXNML(CharSequence xnmlMarkup, Integer profileId) throws XiaoneiException, IOException {
        return this.profile_setXNML(profileId, xnmlMarkup == null ? null : xnmlMarkup.toString());
    }
    private boolean profile_setXNML(Integer userId, String profileXnml) throws XiaoneiException, IOException {
        Collection<Pair<String, CharSequence>> params = new ArrayList<Pair<String, CharSequence>>();
        params.add(new Pair<String, CharSequence>("uid", Integer.toString(userId)));
        if ((profileXnml != null) && (! "".equals(profileXnml))) {
            params.add(new Pair<String, CharSequence>("profile", profileXnml));
        }
        return extractBoolean(this.callMethod(XiaoneiMethod.PROFILE_SET_XNML, params));
    } 
    
    
    public boolean feed_PublishTemplatizedAction(TemplatizedAction action) throws XiaoneiException, IOException {
        return this.feed_publishTemplatizedAction(action.getTemplateId(), action.getTitleParams(), action.getBodyParams(),action.getResourceId() );
    }
    
    public boolean feed_publishTemplatizedAction(Integer templateId, String titleData,String bodyData, Integer resourceId) throws XiaoneiException, IOException {

        return templatizedFeedHandler(null, XiaoneiMethod.FEED_PUBLISH_TEMPLATIZED_ACTION, templateId, titleData, bodyData, resourceId);
    }
    
    protected boolean templatizedFeedHandler(Integer actorId, XiaoneiMethod method, Integer templateId, String titleData, String bodyData, Integer resourceId) throws XiaoneiException, IOException {
        
        ArrayList<Pair<String, CharSequence>> params = new ArrayList<Pair<String, CharSequence>>(method.numParams());

        //these are always required parameters
        params.add(new Pair<String, CharSequence>("template_id", Integer.toString(templateId)));

        //these are optional parameters
        if (titleData != null) {
            params.add(new Pair<String, CharSequence>("title_data", titleData));
        }
        if (bodyData != null) {
            params.add(new Pair<String, CharSequence>("body_data", bodyData));
        }
        if (resourceId != null) {
            params.add(new Pair<String, CharSequence>("resource_id", Integer.toString(resourceId)));
        }
        this.callMethod(method, params);
        if (this.rawResponse == null) {
            return false;
        }
        return this.rawResponse.contains(">1<"); //a code of '1' indicates success
    }


	public void notifications_send(CharSequence notification) throws XiaoneiException, IOException {
//        Integer currentUser = this.users_getLoggedInUser();
//        Collection<Integer> coll = new ArrayList<Integer>();
//        coll.add(currentUser);
        notifications_send(null, notification);
	}


	public void notifications_send(Collection<Integer> recipientIds, CharSequence notification) throws XiaoneiException, IOException {
		 if (null == notification || "".equals(notification)) {
	            throw new XiaoneiException(ErrorCode.GEN_INVALID_PARAMETER, "你不能传入一个空的 notification!");
	        }
	        if ((recipientIds != null) && (! recipientIds.isEmpty())) {
	            this.callMethod(XiaoneiMethod.NOTIFICATIONS_SEND,
	                    new Pair<String, CharSequence>("to_ids", delimit(recipientIds)),
	                    new Pair<String, CharSequence>("notification", notification));
	        }
	        else {
	            this.callMethod(XiaoneiMethod.NOTIFICATIONS_SEND,
	                    new Pair<String, CharSequence>("notification", notification));
	        }
	}


	public Document invitations_getOsInfo(Collection<String> inviteIds) throws XiaoneiException, IOException {
		 if (null == inviteIds || inviteIds.isEmpty()) {
	            throw new XiaoneiException(ErrorCode.GEN_INVALID_PARAMETER, "你不能传入一个空的 invite_ids !");
	        }
		return this.callMethod(XiaoneiMethod.INVITATIONS_GET_OS_INFO,
                 new Pair<String, CharSequence>("invite_ids", delimit(inviteIds)));
	}
	/**
	 * 得到某些用户站外邀请的数据
	 * @param uids 用户id串
	 * @return 用户站外邀请数量，包括其中星级用户的数量
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public Document invitations_getUserOsInviteCnt(Collection<String> uids) throws XiaoneiException, IOException {
		 if (null == uids || uids.isEmpty()) {
	            throw new XiaoneiException(ErrorCode.GEN_INVALID_PARAMETER, "你不能传入一个空的 uids !");
	        }
		return this.callMethod(XiaoneiMethod.INVITATIONS_GET_USER_OS_CNT,
                new Pair<String, CharSequence>("uids", delimit(uids)));
	}
	/**
	 * 获取到一个app发送通知的配额
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public Document admin_getAllocation() throws XiaoneiException, IOException {
		return this.callMethod(XiaoneiMethod.ADMIN_GET_ALLCATION);
	}
	/**
	 * 获取每次用于校内豆支付平台验证身份的token,应用必须保证每次传递的订单号唯一,用户Id为当前登陆用户
	 * @param orderId
	 * @param amount
	 * @param userId 
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public Document pay_regOrder(Long orderId,Integer amount,String desc) throws XiaoneiException, IOException {
		return this.callMethod(XiaoneiMethod.PAY_REG_ORDER
				,new Pair<String, CharSequence>("order_id", Long.toString(orderId))
				,new Pair<String, CharSequence>("amount", Integer.toString(amount))
				,new Pair<String, CharSequence>("desc", desc));
	}
	/**
	 * 
	 */
	public boolean pay_isCompleted(Long orderId)throws XiaoneiException, IOException{
		return extractBoolean(this.callMethod(XiaoneiMethod.PAY_IS_COMPLETED,new Pair<String, CharSequence>("order_id", Long.toString(orderId)))); 	
		}
	
	/**
	 * 用于应用测试校内豆开发
	 * 获取每次用于校内豆支付平台验证身份的token,应用必须保证每次传递的订单号唯一,用户Id为当前登陆用户
	 * @param orderId
	 * @param amount
	 * @param userId 
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public Document pay4Test_regOrder(Long orderId,Integer amount,String desc) throws XiaoneiException, IOException {
		return this.callMethod(XiaoneiMethod.PAY4TEST_REG_ORDER
				,new Pair<String, CharSequence>("order_id", Long.toString(orderId))
				,new Pair<String, CharSequence>("amount", Integer.toString(amount)),new Pair<String, CharSequence>("desc", desc));
	}
	/**
	 * 用于应用测试校内豆开发
	 * 是否用户完成支付
	 */
	public boolean pay4Test_isCompleted(Long orderId)throws XiaoneiException, IOException{
		return extractBoolean(this.callMethod(XiaoneiMethod.PAY4TEST_IS_COMPLETED,new Pair<String, CharSequence>("order_id", Long.toString(orderId)))); 	
		}
	
	/**
	 * 得到向用户发送站内邀请的好友id列表
	 */
	public Document invitations_getIsInviters(Collection<String> uids) throws XiaoneiException, IOException {
		 if (null == uids || uids.isEmpty()) {
	            throw new XiaoneiException(ErrorCode.GEN_INVALID_PARAMETER, "你不能传入一个空的 uids !");
	        }
		return this.callMethod(XiaoneiMethod.INVITATIONS_GET_IS_INVITERS,
                new Pair<String, CharSequence>("uids", delimit(uids)));
	}
	/**
	 * 向用户发送email
	 */
	public Document notifications_sendEmail(Collection<Integer> recipientIds, Integer templateId) throws XiaoneiException, IOException {
	        if ((recipientIds == null) && ( recipientIds.isEmpty())) {
	        	throw new XiaoneiException(ErrorCode.GEN_INVALID_PARAMETER, "你不能传入一个空的 recipientIds !");
	        }
	        return this.callMethod(XiaoneiMethod.NOTIFICATIONS_SEND_EMAIL,
                    new Pair<String, CharSequence>("recipients", delimit(recipientIds)),
                    new Pair<String, CharSequence>("template_id", Integer.toString(templateId)));
	}
	/**
	 * 判断用户是否对应用进行特殊的授权
	 */
	public boolean users_hasAppPermission(CharSequence ext_perm) throws XiaoneiException, IOException {
            return extractBoolean(this.callMethod(XiaoneiMethod.USERS_HAS_APP_PERMISSION,
                    new Pair<String, CharSequence>("ext_perm", ext_perm)));
	}
	
	/**
	 * 判断指定用户是否对应用进行特殊的授权
	 */
	public boolean users_hasAppPermission(Integer uid,CharSequence ext_perm) throws XiaoneiException, IOException {
            return extractBoolean(this.callMethod(XiaoneiMethod.USERS_HAS_APP_PERMISSION,
                    new Pair<String, CharSequence>("ext_perm", ext_perm),new Pair<String, CharSequence>("uid", Integer.toString(uid))));
	}
	
	
}
