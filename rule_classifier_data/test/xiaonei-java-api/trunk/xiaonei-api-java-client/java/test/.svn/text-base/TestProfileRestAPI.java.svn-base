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
 * 用来测试xiaonei.profile的rest api
 * @author fusong.li@opi-corp.com
 * 2008-6-17 下午06:28:38
 */
public class TestProfileRestAPI {
	static Log logger = LogFactory.getLog(TestProfileRestAPI.class);
	
	public static void main(String[] args) throws XiaoneiException, IOException {

		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,Config.secret,Config.sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.profile.setXNML
		 */
		testSetXNML(client);
		logger.info("---- 测试结束 ----");
	}
	/**
	 * 测试 xiaonei.profile.setXNML
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testSetXNML(XiaoneiRestClient client) throws XiaoneiException, IOException{
		/*
		 * 设置同样安装了此app的你的好友的Profile上的此app的展示 
		 */
		String xnmlMarkup = "hello";
		boolean isSuccessful = client.profile_setProfileXNML(xnmlMarkup);
		logger.info("profile|setXNML|isSuccessful|"+isSuccessful);
	}
	
}
