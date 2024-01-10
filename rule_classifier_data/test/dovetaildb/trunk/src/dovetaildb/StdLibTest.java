package dovetaildb;

import java.io.File;


import dovetaildb.api.ApiService;
import dovetaildb.api.ApiServiceTest;
import dovetaildb.api.ChangesetBuffer;
import dovetaildb.dbservice.DbService;
import dovetaildb.dbservice.RebuildingDbService;
import dovetaildb.util.Util;

public class StdLibTest  extends ApiServiceTest {

	protected ApiService createApi(DbService dbService) {
		return new ChangesetBuffer(dbService);
	}
	
	protected DbService createDbService() {
		float lengthToRowRatio = 1.0f;
		File bagDir = Util.createTempDirectory("StdLibTest_bag");
		RebuildingDbService db = (RebuildingDbService)StdLib.makeFsDb(bagDir.getAbsolutePath(), true, lengthToRowRatio);
		db.stopRebuildThread();
		return db;
	}
	@Override
	protected void betweenIters(DbService dbService, ApiService apiService) {
		RebuildingDbService db = (RebuildingDbService)dbService;
		System.out.println(" -- begin rebuild -- ");
		db.runRebuild();
		System.out.println(" -- end rebuild -- ");
	}
	
}
