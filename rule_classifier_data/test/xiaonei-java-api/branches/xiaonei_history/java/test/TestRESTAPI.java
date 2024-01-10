import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaonei.api.ProfileField;
import com.xiaonei.api.XiaoneiException;
import com.xiaonei.api.XiaoneiRestClient;
import com.xiaonei.api.schema.HometownLocation;
import com.xiaonei.api.schema.HsHistory;
import com.xiaonei.api.schema.HsInfo;
import com.xiaonei.api.schema.UniversityHistory;
import com.xiaonei.api.schema.UniversityInfo;
import com.xiaonei.api.schema.User;
import com.xiaonei.api.schema.UsersGetInfoResponse;
import com.xiaonei.api.schema.WorkHistory;
import com.xiaonei.api.schema.WorkInfo;

public class TestRESTAPI {
	static Log logger = LogFactory.getLog(TestRESTAPI.class);
	
	public static void main(String[] args) throws XiaoneiException, IOException {
		logger.info("---- 开始测试新版rest api client ----");
		String apiKey = "8355b3bacffd43dda77579563ff6cd9e";
		String secret = "e0fffeb8a75f48dd8b99216ecb14d210";
		String sessionKey = "xVO1XuwBr1eCbiRPjafbJ01jFbzwAl4RThb2jAxFNeJlHorrhR9UBFxlu/5kduSX-200032219";

		XiaoneiRestClient client = new XiaoneiRestClient(apiKey,secret,sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.users.getLoggedInUser
		 */
		int loggedInUserId = testUsersLoggedInUser(client);
		/*
		 * 测试 xiaonei.users.getInfo
		 */
		testUsersGetInfo(client,loggedInUserId);
		
		logger.info("---- 测试结束 ----");
	}
	/**
	 * 测试 xiaonei.users.getLoggedInUser
	 * @param client
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static int testUsersLoggedInUser(XiaoneiRestClient client) throws XiaoneiException, IOException{
		/*
		 * 得到当前登录者的用户id
		 */
		int loggedInUserId = 0;
		loggedInUserId = client.users_getLoggedInUser();
		logger.info("loggedInUserId="+loggedInUserId);
		return loggedInUserId;
	}
	/**
	 * 测试 xiaonei.users.getInfo
	 * @param client
	 * @param userId
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testUsersGetInfo(XiaoneiRestClient client,int userId) throws XiaoneiException, IOException{
		/*
		 * 得到一个人的信息
		 */
		EnumSet<ProfileField> enumFields = EnumSet.of(ProfileField.NAME,
				ProfileField.SEX,ProfileField.BIRTHDAY,
				ProfileField.HOMETOWN_LOCATION,ProfileField.UNIVERSITY_HISTORY,
				ProfileField.TINYURL,ProfileField.HEADURL,ProfileField.MAINURL,
				ProfileField.HS_HISTORY,
				ProfileField.WORK_HISTORY,ProfileField.UNIVERSITY_HISTORY);
		//得到用户信息
		client.users_getInfo(userId, enumFields);
		UsersGetInfoResponse loggedUserGetInfoRes = (UsersGetInfoResponse) client.getResponsePOJO();
		List<User> users = loggedUserGetInfoRes.getUser();
		logger.info("user size="+users.size());
		for (User user : users) {
			logger.info("user|Uid|"+user.getUid());
			logger.info("user|Name|"+user.getName());
			logger.info("user|Birthday|"+user.getBirthday());
			logger.info("user|Tinyurl|"+user.getTinyurl());
			logger.info("user|Headurl|"+user.getHeadurl());
			logger.info("user|Mainurl|"+user.getMainurl());
			HometownLocation hl = user.getHometownLocation();
			logger.info("user|HometownLocation|Province|"+hl.getProvince());
			logger.info("user|HometownLocation|City|"+hl.getCity());
			//
			HsHistory hh = user.getHsHistory();
			if(hh!=null){
				List<HsInfo> his = hh.getHsInfo();
				for (HsInfo info : his) {
					logger.info("user|HsHistory|HsInfo|Name|"+info.getName());
					logger.info("user|HsHistory|HsInfo|GradYear|"+info.getGradYear());
				}
			}
			UniversityHistory uh = user.getUniversityHistory();
			if(uh!=null){
				List<UniversityInfo> uis = uh.getUniversityInfo();
				for (UniversityInfo ui : uis) {
					logger.info("user|UniversityHistory|UniversityInfo|Name|"+ui.getName());
					logger.info("user|UniversityHistory|UniversityInfo|Department|"+ui.getDepartment());
					logger.info("user|UniversityHistory|UniversityInfo|Year|"+ui.getYear());
				}
			}
			WorkHistory wh = user.getWorkHistory();
			if(wh!=null){
				List<WorkInfo> wis = wh.getWorkInfo();
				for (WorkInfo wi : wis) {
					logger.info("user|WorkHistory|WorkInfo|CompanyName|"+wi.getCompanyName());
					logger.info("user|WorkHistory|WorkInfo|Description|"+wi.getDescription());
					logger.info("user|WorkHistory|WorkInfo|StartDate|"+wi.getStartDate());
					logger.info("user|WorkHistory|WorkInfo|EndDate|"+wi.getEndDate());
				}
			}
		}
	}
}
