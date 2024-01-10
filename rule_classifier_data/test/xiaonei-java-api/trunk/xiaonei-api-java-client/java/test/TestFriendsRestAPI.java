import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;
import com.xiaonei.api.schema.Friend;
import com.xiaonei.api.schema.FriendInfo;
import com.xiaonei.api.schema.FriendsAreFriendsResponse;
import com.xiaonei.api.schema.FriendsGetAppUsersResponse;
import com.xiaonei.api.schema.FriendsGetFriendsResponse;
/**
 * 用来测试xiaonei.friends的rest api
 * @author fusong.li@opi-corp.com
 * 2008-6-17 下午06:28:38
 */
public class TestFriendsRestAPI {
	static Log logger = LogFactory.getLog(TestFriendsRestAPI.class);
	
	public static void main(String[] args) throws XiaoneiException, IOException {

		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,Config.secret,Config.sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.friends.getFriends
		 */
		testFriendsGetFriends(client);
//		/*
//		 * 测试 xiaonei.friends.getAppUsers
//		 */
		testFriendsGetAppUsers(client);
//		/*
//		 * 测试 xiaonei.friends.areFriends
//		 */
 testFriendsAreFriends(client);
		
testFriendsGet(client);
		logger.info("---- 测试结束 ----");
	}
	/**
	 * 测试 xiaonei.friends.getFriends
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testFriendsGetFriends(XiaoneiRestClient client) throws XiaoneiException, IOException{
		/*
		 * 得到好友 
		 */
		client.friends_getFriends();
		FriendsGetFriendsResponse resp = (FriendsGetFriendsResponse) client.getResponsePOJO();
		List<Friend> list = resp.getFriend();
		for (Friend friend : list) {
			System.out.println("friends|getFriends|Id|"+friend.getId());
			System.out.println("friends|getFriends|Name|"+friend.getName());
			System.out.println("friends|getFriends|Headurl|"+friend.getHeadurl());
		}
	}
	/**
	 * 测试 xiaonei.friends.getAppUsers
	 * @param client
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testFriendsGetAppUsers(XiaoneiRestClient client) throws XiaoneiException, IOException{
		
		//得到用户信息
		client.friends_getAppUsers();
		FriendsGetAppUsersResponse resp = (FriendsGetAppUsersResponse) client.getResponsePOJO();
		List<Integer> uids = resp.getUid();
		for (Integer uid : uids) {
			logger.info("friends|getAppUsers|uid|"+uid);
		}
	}
	/**
	 * 测试 xiaonei.friends.areFriends
	 * @param client
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testFriendsAreFriends(XiaoneiRestClient client) throws XiaoneiException, IOException{
		int userId1 = 700001044;
		int userId2 = 700000524;//王少斌  
		//得到用户信息
		client.friends_areFriends(userId1, userId2);
		FriendsAreFriendsResponse resp = (FriendsAreFriendsResponse) client.getResponsePOJO();
		List<FriendInfo> fis = resp.getFriendInfo();
		for (FriendInfo fi : fis) {
			logger.info("friends|areFriends|uid1|"+fi.getUid1()+"|uid2|"+fi.getUid2()+"|areFriends|"+fi.isAreFriends());
		}
	}
	
	private static void testFriendsGet(XiaoneiRestClient client){
		try {
			client.friends_get();
			
		} catch (XiaoneiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
