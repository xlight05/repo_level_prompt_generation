import java.io.IOException;
import java.util.List;

import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;
import com.xiaonei.api.schema.FriendsGetAppFriendsResponse;
import com.xiaonei.api.schema.FriendsGetAppUsersResponse;


public class TestGetAllocationAPI {
	public static void main(String[] args) throws XiaoneiException, IOException {

	
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,Config.secret,Config.sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.friends.getFriends
		 */
	    testGetAllocation(client);
		
	}
	public static void testGetAllocation(XiaoneiRestClient client) throws XiaoneiException, IOException{
		client.admin_getAllocation();
//		int userId=252363421;
//		int count=20;
//		int page=1;
//		client.pay_regOrder(103301422544456661L, 1212312221);
//		client.pay4Test_isCompleted(1010L);
		 client.friends_getAppFriends();
		 FriendsGetAppFriendsResponse faf = (FriendsGetAppFriendsResponse)client.getResponsePOJO();
          client.users_hasAppPermission("email");
	
//		client.friends_getAppUsers();
//         FriendsGetAppUsersResponse faf = (FriendsGetAppUsersResponse)client.getResponsePOJO();
           
            List<Integer> list0 = faf.getUid();
            
            for(Integer aInteger:list0){
                 System.out.println(aInteger);
            } 
	}
}
