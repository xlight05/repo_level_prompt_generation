import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;
/**
 * 用来测试xiaonei.feed的rest api
 * @author fusong.li@opi-corp.com
 * 2008-6-17 下午06:28:38
 */
public class TestFeedRestAPI {
	static Log logger = LogFactory.getLog(TestFeedRestAPI.class);
	
	public static void main(String[] args) throws XiaoneiException, IOException {

		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,Config.secret,Config.sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.profile.setXNML
		 */
		testPublishTemplatizedAction(client);
		logger.info("---- 测试结束 ----");
	}
	/**
	 * 测试 xiaonei.profile.setXNML
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testPublishTemplatizedAction(XiaoneiRestClient client) throws XiaoneiException, IOException{
		/*
		 * 发送自定义Feed 
		 */
		Integer templateId = 1;
		String stockname="中国石化";
		String body_data="{\"tsete\":\"爱情忠贞者\"}"; 
		String title_data="{\"title\":\"爱情方式测试\",\"content_id\":\"1\"}";
		JSONObject  ob=new JSONObject();
		try {
			ob.put("test","<a href=\"http://apps.xiaonei.com/tt\">测试吗？</a>");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer resourceId = 0;
		boolean isSuccessful = client.feed_publishTemplatizedAction(templateId, "", ob.toString(), resourceId);
		logger.info("feed|publishTemplatizedAction|isSuccessful|"+isSuccessful);
	}
	
}
