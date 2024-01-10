
package com.xiaonei.api;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;



public interface IXiaoneiRestClient<T> {
  public static final String TARGET_API_VERSION = "1.0";
  public static final String ERROR_TAG         = "error_response";
  public static final String XN_SERVER         = "api.xiaonei.com/restserver.do";
  public static final String SERVER_ADDR       = "http://" + XN_SERVER;
  public static final String HTTPS_SERVER_ADDR = "https://" + XN_SERVER;



  public void setDebug(boolean isDebug);


  public boolean isDebug();


  public boolean isDesktop();


  public void setIsDesktop(boolean isDesktop);

  /**
   * 
   * @param xnmlMarkup
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean profile_setProfileXNML(CharSequence xnmlMarkup) throws XiaoneiException,
          IOException;

  /**
   * 
   * @param xnmlMarkup
   * @param profileId
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean profile_setProfileXNML(CharSequence xnmlMarkup, Integer profileId)
          throws XiaoneiException, IOException;

  /**
   * 
   * @param userId
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T profile_getXNML(Integer userId)
    throws XiaoneiException, IOException;
/**
   * 
   * @param userId1
   * @param userId2
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T friends_areFriends(int userId1, int userId2)
    throws XiaoneiException, IOException;

  /**
   * 
   * @param userIds1
   * @param userIds2
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T friends_areFriends(Collection<Integer> userIds1, Collection<Integer> userIds2)
    throws XiaoneiException, IOException;

  /**
   * 
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T friends_getAppUsers()
    throws XiaoneiException, IOException;

  /**
   * 得到当前登陆用户的好友列表（不考虑分页）
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T friends_get() throws XiaoneiException, IOException;
  
	/**
	 * 
	 * 得到当前登陆用户的好友列表（不考虑分页）
	 * 
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public T friends_getFriends() throws XiaoneiException, IOException;
    
	/**
	   * 获取安装应用的好友列表
	   * @return
	   * @throws XiaoneiException
	   * @throws IOException
	   */
	  public T friends_getAppFriends()
	    throws XiaoneiException, IOException;
	
  /**
   * 
   * @param userId
   * @param fields
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T users_getInfo(Integer userId, EnumSet<ProfileField> fields) throws XiaoneiException, IOException;
 
  /**
   * 
   * @param userIds
   * @param fields
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T users_getInfo(Collection<Integer> userIds, EnumSet<ProfileField> fields)
    throws XiaoneiException, IOException;


  /**
   * 
   * @param userIds
   * @param fields
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T users_getInfo(Collection<Integer> userIds, Set<CharSequence> fields)
    throws XiaoneiException, IOException;

  /**
   * 
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public int users_getLoggedInUser()
    throws XiaoneiException, IOException;

  /**
   * 判断当前用户是否加入过当前App
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean users_isAppAdded()
    throws XiaoneiException, IOException;
  /**
   * 判断一个人是否加入过当前App
   * @param userId
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean users_isAppAdded(Integer userId)
    throws XiaoneiException, IOException;
  /**
   * 
   * @param userIds
   * @return
   * @throws XiaoneiException
   * @throws IOException
   * @deprecated
   */
  public T requests_sendRequest(Collection<Integer> userIds) throws XiaoneiException, IOException;

  /**
	 * 
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	public String auth_createToken() throws XiaoneiException, IOException;

	/**
	 * 
	 * @param authToken
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
  public String auth_getSession(String authToken)
    throws XiaoneiException, IOException;


 
  /**
   * 
   * @return
   */
  public String getRawResponse();
  
  /**
   * 
   * @return
   */
  public Object getResponsePOJO();
  

  public boolean feed_PublishTemplatizedAction(TemplatizedAction action) throws XiaoneiException, IOException;
  
  /**
   * 给当前用户发notifications 消息
   * @param notification 消息内容
   * @throws XiaoneiException
   * @throws IOException
   */
  public void notifications_send(CharSequence notification) throws XiaoneiException, IOException;
  /**
   * 给指定的用户发notifications 消息
   * @param recipientIds 接收者用户ids
   * @param notification 消息内容
   * @throws XiaoneiException
   * @throws IOException
   */
  public void notifications_send(Collection<Integer> recipientIds, CharSequence notification) throws XiaoneiException, IOException;
  
  public T  invitations_getOsInfo(Collection<String> inviteIds) throws XiaoneiException, IOException;
  /**
   * 得到用户站外邀请的数据
   * @param uids
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T  invitations_getUserOsInviteCnt(Collection<String> uids) throws XiaoneiException, IOException;
  /**
   * 得到一个应用通知的配额
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T  admin_getAllocation()throws XiaoneiException, IOException;
  /**
   * 获取每次用于校内豆支付平台验证身份的token,应用必须保证每次传递的订单号唯一,用户Id为当前登陆用户
   * @param orderId
   * @param amount
   * @param userId
   * @param desc
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T  pay_regOrder(Long orderId,Integer amount,String desc) throws XiaoneiException,IOException;
  /**
   * 判断用户是否已经完成校内豆支付
   * @param orderId
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean   pay_isCompleted(Long orderId) throws XiaoneiException,IOException;
  
  /**
   * 用于应用测试
   * 获取每次用于校内豆支付平台验证身份的token,应用必须保证每次传递的订单号唯一,用户Id为当前登陆用户
   * @param orderId
   * @param amount
   * @param userId
   * @param desc
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T  pay4Test_regOrder(Long orderId,Integer amount,String desc) throws XiaoneiException,IOException;
  /**
   * 用于应用测试
   * 判断用户是否已经完成校内豆支付
   * @param orderId
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean  pay4Test_isCompleted(Long orderId) throws XiaoneiException,IOException;
  /**
   * 负责查询向用户发送站内邀请的好友id
   * @param uid
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public T  invitations_getIsInviters(Collection<String> uid) throws XiaoneiException, IOException;

  
  /**
   * 向用户发送email
   * @param recipientIds
   * @param templateId
   * @throws XiaoneiException
   * @throws IOException
   */
  public T notifications_sendEmail(Collection<Integer> recipientIds, Integer templateId) throws XiaoneiException, IOException;
  
  /**
   * 判断用户是否对app授予了特殊的权限
   * @param ext_perm
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean users_hasAppPermission(CharSequence ext_perm)
    throws XiaoneiException, IOException;
  /**
   * 判断指定用户是否对app授予了特殊的权限
   * @param uid
   * @param ext_perm
   * @return
   * @throws XiaoneiException
   * @throws IOException
   */
  public boolean users_hasAppPermission(Integer uid,CharSequence ext_perm)
    throws XiaoneiException, IOException;
}
