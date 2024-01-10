import java.io.IOException;
import java.util.ArrayList;
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
/**
 * 用来测试xiaonei.users的rest api
 * @author fusong.li@opi-corp.com
 * 2008-6-17 下午04:10:55
 */
public class TestUsersRestAPI {
	static Log logger = LogFactory.getLog(TestUsersRestAPI.class);
	
	public static void main(String[] args) throws XiaoneiException, IOException {
		logger.info("---- 开始测试新版rest api client ----");
		XiaoneiRestClient client = new XiaoneiRestClient(Config.apiKey,Config.secret,Config.sessionKey);
		//设置调试状态
		client.setDebug(true);
		/*
		 * 测试 xiaonei.users.getLoggedInUser
		 */
		int loggedInUserId = testUsersLoggedInUser(client);
		/*
		 * 测试 xiaonei.users.getInfo
		 */
		List uids=new ArrayList();
//		uids.add(66271);
//		uids.add(66272);
//		uids.add(66273);
//		uids.add(66274);
//		uids.add(66275);
//		uids.add(66276);
//		uids.add(66277);
//		uids.add(66278);
//		uids.add(66279);
		uids.add(704899619);
		testUsersGetInfo(client,uids);
		/*
		 * 测试 xiaonei.users.isAppAdded
		 */
		testUsersIsAppAdded(client,0);
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
		System.out.println(loggedInUserId);
		logger.info("users.getLoggedInUser|loggedInUserId|"+loggedInUserId);
		return loggedInUserId;
	}
	/**
	 * 测试 xiaonei.users.isAppAdded
	 * @param client
	 * @param userId
	 * @return
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testUsersIsAppAdded(XiaoneiRestClient client,int userId) throws XiaoneiException, IOException{
		//看当前登录用户是否是加入了此app
		boolean isAppAdded = client.users_isAppAdded();
		logger.info("users.isAppAdded|isAppAdded|"+isAppAdded);
	}
	
	/**
	 * 测试 xiaonei.users.getInfo
	 * @param client
	 * @param userId
	 * @throws XiaoneiException
	 * @throws IOException
	 */
	private static void testUsersGetInfo(XiaoneiRestClient client,List userId) throws XiaoneiException, IOException{
		/*
		 * 得到一个人的信息
		 */
		EnumSet<ProfileField> enumFields = EnumSet.of(ProfileField.NAME,ProfileField.STAR,
				ProfileField.SEX,ProfileField.BIRTHDAY,
				ProfileField.HOMETOWN_LOCATION,ProfileField.UNIVERSITY_HISTORY,
				ProfileField.TINYURL,ProfileField.HEADURL,ProfileField.MAINURL,
				ProfileField.HS_HISTORY,
				ProfileField.WORK_HISTORY,ProfileField.UNIVERSITY_HISTORY,ProfileField.ZIDOU);
		//得到用户信息
		client.users_getInfo(userId, enumFields);
		UsersGetInfoResponse loggedUserGetInfoRes = (UsersGetInfoResponse) client.getResponsePOJO();
		List<User> users = loggedUserGetInfoRes.getUser();
		logger.info("user size="+users.size());
		for (User user : users) {
			logger.info("users.getInfo|Uid|"+user.getUid());
			logger.info("users.getInfo|Name|"+user.getName());
			logger.info("users.getInfo|Birthday|"+user.getBirthday());
			logger.info("users.getInfo|Tinyurl|"+user.getTinyurl());
			logger.info("users.getInfo|Headurl|"+user.getHeadurl());
			logger.info("users.getInfo|Mainurl|"+user.getMainurl());
			HometownLocation hl = user.getHometownLocation();
			logger.info("users.getInfo|HometownLocation|Province|"+hl.getProvince());
			logger.info("users.getInfo|HometownLocation|City|"+hl.getCity());
			logger.info("users.getInfo|HometownLocation|City|"+user.getZidou());

			//
			HsHistory hh = user.getHsHistory();
			if(hh!=null){
				List<HsInfo> his = hh.getHsInfo();
				for (HsInfo info : his) {
					logger.info("users.getInfo|HsHistory|HsInfo|Name|"+info.getName());
					logger.info("users.getInfo|HsHistory|HsInfo|GradYear|"+info.getGradYear());
				}
			}
			UniversityHistory uh = user.getUniversityHistory();
			if(uh!=null){
				List<UniversityInfo> uis = uh.getUniversityInfo();
				for (UniversityInfo ui : uis) {
					logger.info("users.getInfo|UniversityHistory|UniversityInfo|Name|"+ui.getName());
					logger.info("users.getInfo|UniversityHistory|UniversityInfo|Department|"+ui.getDepartment());
					logger.info("users.getInfo|UniversityHistory|UniversityInfo|Year|"+ui.getYear());
				}
			}
			WorkHistory wh = user.getWorkHistory();
			if(wh!=null){
				List<WorkInfo> wis = wh.getWorkInfo();
				for (WorkInfo wi : wis) {
					logger.info("users.getInfo|WorkHistory|WorkInfo|CompanyName|"+wi.getCompanyName());
					logger.info("users.getInfo|WorkHistory|WorkInfo|Description|"+wi.getDescription());
					logger.info("users.getInfo|WorkHistory|WorkInfo|StartDate|"+wi.getStartDate());
					logger.info("users.getInfo|WorkHistory|WorkInfo|EndDate|"+wi.getEndDate());
				}
			}
		}
	}
}
