package com.xiaonei.api;

import java.util.EnumSet;

public enum XiaoneiMethod
  implements IXiaoneiMethod, CharSequence {
  // 认证
  AUTH_CREATE_TOKEN("xiaonei.auth.createToken"),
  AUTH_GET_SESSION("xiaonei.auth.getSession", 1),
  // 好友
  FRIENDS_GET_FRIENDS("xiaonei.friends.getFriends", 2),
  FRIENDS_GET_APP_USERS("xiaonei.friends.getAppUsers"),
  FRIENDS_ARE_FRIENDS("xiaonei.friends.areFriends", 2),
  FRIENDS_GET("xiaonei.friends.get", 1),
  FRIENDS_GET_APP_FRIENDS("xiaonei.friends.getAppFriends"),
  // 用户
  USERS_GET_INFO("xiaonei.users.getInfo", 2),
  USERS_GET_LOGGED_IN_USER("xiaonei.users.getLoggedInUser", 1),
  USERS_IS_APP_ADDED("xiaonei.users.isAppAdded"),
  USERS_HAS_APP_PERMISSION("xiaonei.users.hasAppPermission",1),
  
  // XNML
  PROFILE_SET_XNML("xiaonei.profile.setXNML", 4),
  PROFILE_GET_XNML("xiaonei.profile.getXNML", 1),
  XNML_REFRESH_REF_URL("xiaonei.xnml.refreshRefUrl", 1),
  XNML_SET_REF_HANDLE("xiaonei.xnml.setRefHandle", 2),
  // Feed
  FEED_PUBLISH_STORY_TO_USER("xiaonei.feed.publishStoryToUser", 11),
  FEED_PUBLISH_TEMPLATIZED_ACTION("xiaonei.feed.publishTemplatizedAction", 15),
  //Notifications
  NOTIFICATIONS_SEND("notifications.send",4),
  NOTIFICATIONS_SEND_EMAIL("xiaonei.notifications.sendemail",2),
  //请求
  REQUESTS_SEND_REQUEST("xiaonei.requests.sendRequest",2),
  //邀请
  INVITATIONS_GET_OS_INFO("xiaonei.invitations.getOsInfo",2),
  INVITATIONS_GET_USER_OS_CNT("xiaonei.invitations.getUserOsInviteCnt",2),
  INVITATIONS_GET_IS_INVITERS("xiaonei.invitations.getIsInviters",1),
  //admin
  ADMIN_GET_ALLCATION("xiaonei.admin.getAllocation",1),
  //校内豆支付平台
  PAY_REG_ORDER("xiaonei.pay.regOrder",2),
  PAY_IS_COMPLETED("xiaonei.pay.isCompleted",1),
//校内豆支付平台
  PAY4TEST_REG_ORDER("xiaonei.pay4Test.regOrder",2),
  PAY4TEST_IS_COMPLETED("xiaonei.pay4Test.isCompleted",1),
  ;

  private String methodName;
  private int numParams;
  private int maxParamsWithSession;
  private boolean takesFile;

  private static EnumSet<XiaoneiMethod> preAuth = null;
  private static EnumSet<XiaoneiMethod> postAuth = null;

  /**
   * 有一些特殊的方法，调用它们前不需要先得到一个session_key进行认证
   * @return 
   */
  public static EnumSet<XiaoneiMethod> preAuthMethods() {
    if (null == preAuth)
      preAuth = EnumSet.of(AUTH_CREATE_TOKEN, AUTH_GET_SESSION);
    return preAuth;
  }

  /**
   * 有一些的方法，调用它们前必须要先得到一个session_key进行认证
   */
  public static EnumSet<XiaoneiMethod> postAuthMethods() {
    if (null == postAuth)
      postAuth = EnumSet.complementOf(preAuthMethods());
    return postAuth;
  }

  XiaoneiMethod(String name) {
    this(name, 0, false);
  }

  XiaoneiMethod(String name, int maxParams ) {
    this(name, maxParams, false);
  }

  XiaoneiMethod(String name, int maxParams, boolean takesFile ) {
    assert (name != null && 0 != name.length());
    this.methodName = name;
    this.numParams = maxParams;
    this.maxParamsWithSession = maxParams + AbstractXiaoneiRestClient.NUM_AUTOAPPENDED_PARAMS;
    this.takesFile = takesFile;
  }

  /**
   */
  public String methodName() {
    return this.methodName;
  }

  /**
   */
  public int numParams() {
    return this.numParams;
  }

  public boolean requiresSession() {
    return postAuthMethods().contains(this);
  }

  public int numTotalParams() {
    return requiresSession() ? this.maxParamsWithSession : this.numParams;
  }

  public boolean takesFile() {
    return this.takesFile;
  }

  public char charAt(int index) {
    return this.methodName.charAt(index);
  }

  public int length() {
    return this.methodName.length();
  }

  public CharSequence subSequence(int start, int end) {
    return this.methodName.subSequence(start, end);
  }

  public String toString() {
    return this.methodName;
  }
}
