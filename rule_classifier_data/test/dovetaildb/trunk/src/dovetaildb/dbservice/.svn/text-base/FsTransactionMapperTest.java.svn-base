package dovetaildb.dbservice;

import java.io.File;

import dovetaildb.util.Util;


public class FsTransactionMapperTest extends TransactionMapperTest {

	File dir = null;
	
	protected TransactionMapper createMapper() {
		if (dir != null) throw new RuntimeException("Only make one FsTransactionMapper");
		dir = Util.createTempDirectory("FsTransactionMapperTest");
		return new FsTransactionMapper(dir, false);
	}

	@Override
	public void tearDown() {
		if (dir != null) Util.deleteDirectory(dir);
	}
	
}
