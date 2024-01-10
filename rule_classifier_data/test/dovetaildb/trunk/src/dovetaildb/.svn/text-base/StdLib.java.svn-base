package dovetaildb;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;


import dovetaildb.api.ApiBuffer;
import dovetaildb.bagindex.BagIndex;
import dovetaildb.bagindex.BlueSteelBagIndex;
import dovetaildb.bagindex.FsBlueSteelBagIndex;
import dovetaildb.bagindex.MemoryBlueSteelBagIndex;
import dovetaildb.bagindex.PrefixCompressedBagIndex;
import dovetaildb.bytes.Bytes;
import dovetaildb.dbservice.BagEntry;
import dovetaildb.dbservice.BagEntryFactory;
import dovetaildb.dbservice.BagIndexBridge;
import dovetaildb.dbservice.DbService;
import dovetaildb.dbservice.DbServiceFactory;
import dovetaildb.dbservice.FsTransactionMapper;
import dovetaildb.dbservice.ProcessTransactionMapper;
import dovetaildb.dbservice.RebuildingDbService;
import dovetaildb.dbservice.ScoringDbService;
import dovetaildb.servlet.HttpReturnThrowable;
import dovetaildb.util.Base64;

public class StdLib {

	public static DbService makeMemoryDb() {
		BagIndexBridge b = new BagIndexBridge();
		final ProcessTransactionMapper mapper = new ProcessTransactionMapper();
		mapper.addRevsForTxn(0, new HashMap<String,Long>());
		b.setBagIndexFactory(new BagEntryFactory() {
			public BagEntry makeBagEntry(String bagName) {
				MemoryBlueSteelBagIndex bagIndex = new MemoryBlueSteelBagIndex();
				mapper.introduceBag(bagName, bagIndex);
				return new BagEntry(bagIndex);
			}
		});
		b.setTxnMapper(mapper);
		return new ScoringDbService(b);
	}

	public static String makeHostLockdownCode() {
		return "function wrapApiService(api, req){req.getRequest().getRemoteAddr()==\"127.0.0.1\" ? api : null}";
	}
	public static String makeAccesskeyLockdownCode(String accesskey) {
		accesskey = accesskey.replaceAll("\"", "\\\"");
		return "function wrapApiService(api, req){return req.getParameter(\"accesskey\")==\""+accesskey+"\" ? api : null}";
	}
	public static DbService makeFsDb(String dataRootName) {
		return makeFsDb(dataRootName, true);
	}
	public static DbService makeFsDb(String dataRootName, final boolean sync) {
		return makeFsDb(dataRootName, sync, 4.0);
	}
	public static DbService makeFsDb(String dataRootName, final boolean sync, final double lengthToRowRatio) {
		File dataRoot = new File(dataRootName);
		if (! dataRoot.exists()) {
			if (!dataRoot.mkdir()) {
				throw new RuntimeException("Could not create database directory at "+dataRootName);
			}
		}
		DbServiceFactory factory = new DbServiceFactory() {
			@Override
			public DbService makeDbService(final File subDbServiceHome, final DbService prevService) {
				BagIndexBridge b = new BagIndexBridge(subDbServiceHome);
				b.setBagIndexFactory(new BagEntryFactory() {
					public BagEntry makeBagEntry(String bagName) {
						BlueSteelBagIndex bsBagIndex = new FsBlueSteelBagIndex(sync);
						BagIndex bagIndex = bsBagIndex;
						bagIndex.setHomedir(new File(subDbServiceHome, "bag_"+bagName).getAbsolutePath());
						if (prevService != null) {
							BagIndex prevIndex = prevService.getBag(bagName);
							if (prevIndex != null) {
								Bytes[] table = PrefixCompressedBagIndex.determineCompressionTable(prevIndex, lengthToRowRatio);
								PrefixCompressedBagIndex pcBagIndex = new PrefixCompressedBagIndex(bsBagIndex, table);
								bagIndex = pcBagIndex;
								bsBagIndex.setTermTableDepth(pcBagIndex.getNumCompressedBytes());
							}
						}
						return new BagEntry(bagIndex);
					}
				});
				FsTransactionMapper mapper = new FsTransactionMapper(subDbServiceHome, sync);
				b.setTxnMapper(mapper);
				// when creating a new BagIndexBridge, issue an empty commit to get a valid initial txn entry
				b.commit(mapper.getHighestTxnId(), new HashMap<String, ApiBuffer>());
				DbService dbService = b;
				dbService = new ScoringDbService(dbService);
				return dbService;
			}
		};
		RebuildingDbService rebuilding = new RebuildingDbService(dataRoot, factory);
		return rebuilding;
	}
	
	/**
	 * Terminates the current request, sending the given status code, headers, and 
	 * content.  This method never returns.
	 */
	public static void httpReturn(int status, List<String> headers, String content) {
		throw new HttpReturnThrowable(status, headers, content); 
	}

	static SecureRandom secureRandom = new SecureRandom();
	public static String genUUID() {
		// 16 bytes is a standard type 4 uuid, but 18 makes an even number of base64 chars
		byte[] buf = new byte[18];
		secureRandom.nextBytes(buf);
		return Base64.encodeBytes(buf);
	}
}
