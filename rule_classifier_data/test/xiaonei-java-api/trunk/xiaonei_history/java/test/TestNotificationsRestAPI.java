import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;

/**
 * 用来测试xiaonei.notifications的rest api
 * 
 * @author fusong.li@opi-corp.com 2008-6-17 下午06:28:38
 */
public class TestNotificationsRestAPI {
	static Log logger = LogFactory.getLog(TestNotificationsRestAPI.class);

	public static void main(String[] args) throws XiaoneiException, IOException {

		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,
				Config.secret, Config.sessionKey);
		// 设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.notifications.send
		 */
//		for(int i=0;i<20;i++){
			testNotificationsSend(client);
//		}
		

		logger.info("---- 测试结束 ----");
	}

	/**
	 * 测试 xiaonei.notifications.send
	 * 
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testNotificationsSend(XiaoneiRestClient client)
			throws XiaoneiException, IOException {
		/*
		 * 给当前用户发通知
		 */
//		client.notifications_send("hello");

		/*
		 * 给指定用户发通知
		 */
		List<Integer> userIds = new ArrayList();
//		userIds.add(265575840);
		userIds.add(700001044);
//		userIds.add(240650143);
		client.notifications_send(userIds,"<xn:name uid=\"700001044\" linked=\"true\" shownetwork=\"true\"/> 有人刚刚以xxx的分数超过你");
	}
}
