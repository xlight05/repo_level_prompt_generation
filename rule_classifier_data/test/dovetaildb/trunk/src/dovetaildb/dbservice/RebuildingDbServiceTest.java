package dovetaildb.dbservice;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


import dovetaildb.Main;
import dovetaildb.api.ApiBuffer;
import dovetaildb.api.AutocommitBuffer;
import dovetaildb.bagindex.FsBlueSteelBagIndex;
import dovetaildb.bagindex.MemoryBlueSteelBagIndex;
import dovetaildb.iter.Iter;
import dovetaildb.util.Util;

public class RebuildingDbServiceTest extends DbServiceTest {

	protected DbService createService() {
		Main.setLogLevel(Level.ALL);
		final File tempDir = Util.createTempDirectory("BagIndexBridgeTest");
		DbServiceFactory factory = new DbServiceFactory() {
			public DbService makeDbService(final File subDbServiceHome, DbService prevService) {
				BagIndexBridge b = new BagIndexBridge(subDbServiceHome);
				b.setBagIndexFactory(new BagEntryFactory() {
					public BagEntry makeBagEntry(String bagName) {
//						return new BagEntry(new MemoryBlueSteelBagIndex());
						FsBlueSteelBagIndex index = new FsBlueSteelBagIndex(false);
						index.setHomedir(new File(subDbServiceHome, "bag_"+bagName).getAbsolutePath());
						index.setTermTableDepth(3);
						return new BagEntry(index);
					}
				});
				ProcessTransactionMapper mapper = new ProcessTransactionMapper();
				mapper.addRevsForTxn(0, new HashMap<String,Long>());
				b.setTxnMapper(mapper);
				return b;
			}
		};
		RebuildingDbService rebuilding = new RebuildingDbService(tempDir, factory, Long.MAX_VALUE);
		return rebuilding;
	}

	public void testRebuild() throws Exception {
		testAll();
		testAll();
		RebuildingDbService db = (RebuildingDbService)this.db;
		db.rebuildPrimarySegment(1000);
		testAll();
		assertFalse(db.rebuildCatchupIter());
		assertTrue(db.rebuildCatchupIter());
		testAll();
	}
	
	public void testStaticDataRebuild() throws Exception {
		testAll();
		RebuildingDbService db = (RebuildingDbService)this.db;
		HashMap<String,ApiBuffer> batch = new HashMap<String,ApiBuffer>();
		ApiBuffer buffer = new ApiBuffer();
		buffer.put("ID1", Util.literalSMap().p("name", "Joe") .p("gender", "m").p("age", 31));
		buffer.put("ID2", Util.literalSMap().p("name", "Jill").p("gender", "f").p("age", 23));
		batch.put("people", buffer);
		long lcommit = db.commit(db.getHighestCompletedTxnId(), batch);
		db.rebuildPrimarySegment(1000);
		assertTrue(db.rebuildCatchupIter());
		Iter i = db.query("people", db.getHighestCompletedTxnId(), Util.literalMap(), Util.literalSMap());
		assertTrue(i.hasNext());
		String name1 = (String)((Map)i.next()).get("name");
		assertTrue(i.hasNext());
		String name2 = (String)((Map)i.next()).get("name");
		assertFalse(i.hasNext());
		assertEquals(Util.literalSet().a(name1).a(name2), Util.literalSet().a("Joe").a("Jill"));
	}
}
