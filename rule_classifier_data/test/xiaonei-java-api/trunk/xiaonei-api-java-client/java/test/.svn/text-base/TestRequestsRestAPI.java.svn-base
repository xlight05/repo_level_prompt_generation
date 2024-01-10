import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;
import com.xiaonei.api.schema.RequestsSendRequestResponse;

/**
 * 用来测试xiaonei.requests的rest api
 * 
 * @author fusong.li@opi-corp.com 2008-6-17 下午06:28:38
 */
public class TestRequestsRestAPI {
	static Log logger = LogFactory.getLog(TestRequestsRestAPI.class);

	public static void main(String[] args) throws XiaoneiException, IOException {

		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,
				Config.secret, Config.sessionKey);
		// 设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.requests.sendRequest
		 */
		testRequestsSendRequest(client);

		logger.info("---- 测试结束 ----");
	}

	/**
	 * 测试 xiaonei.requests.sendRequest
	 * 
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testRequestsSendRequest(XiaoneiRestClient client)
			throws XiaoneiException, IOException {
		/*
		 * 发送邀请
		 */
		List<Integer> userIds = new ArrayList();
		userIds.add(11);
		userIds.add(12);
		client.requests_sendRequest(userIds);
		RequestsSendRequestResponse resp = (RequestsSendRequestResponse) client
				.getResponsePOJO();
		List<Integer> list = resp.getUid();
		for (Integer uid : list) {
			logger.info("requests|sendRequest|uid|" + uid);
		}
	}
	
	private static void testRequestsGetOutsiteInvite(XiaoneiRestClient client)
	throws XiaoneiException, IOException {
//		client
	}
}
