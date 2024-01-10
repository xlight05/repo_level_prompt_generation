package dovetaildb.dbservice;

import java.io.File;
import java.util.HashMap;

import dovetaildb.bagindex.FsBlueSteelBagIndex;
import dovetaildb.bagindex.MemoryBlueSteelBagIndex;
import dovetaildb.bagindex.TrivialBagIndex;
import dovetaildb.util.Util;

public class BagIndexBridgeTest extends DbServiceTest {

	protected DbService createService() {
		final File tempDir = Util.createTempDirectory("BagIndexBridgeTest");
		try {
			BagIndexBridge b = new BagIndexBridge(tempDir);
			b.setBagIndexFactory(new BagEntryFactory() {
				public BagEntry makeBagEntry(String bagName) {
					//return new BagEntry(new TrivialBagIndex(), new DefaultTermEncoder(), 0);
					FsBlueSteelBagIndex index = new FsBlueSteelBagIndex(false);
					index.setHomedir(new File(tempDir, "bag_"+bagName).getAbsolutePath());
					//MemoryBlueSteelBagIndex index = new MemoryBlueSteelBagIndex();
					index.setTermTableDepth(3);
					return new BagEntry(index);
				}
			});
			ProcessTransactionMapper mapper = new ProcessTransactionMapper();
			mapper.addRevsForTxn(0, new HashMap<String,Long>());
			b.setTxnMapper(mapper);
			return b;
		} finally {
			Util.deleteDirectory(tempDir);
		}
	}
	
}
