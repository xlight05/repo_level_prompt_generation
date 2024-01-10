package dovetaildb.dbservice;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;


import junit.framework.TestCase;

import dovetaildb.api.ApiBuffer;
import dovetaildb.api.AutocommitBuffer;
import dovetaildb.util.Util;

public class RebuildingDbService extends WrappingDbService {

	private static final long serialVersionUID = 88243319438954L;

	transient protected RebuildingThread rebuildingThread;
	transient protected ObjectOutputStream backlog;
	transient protected ObjectInputStream processlog;
	protected DbServiceFactory factory;
	protected DbService nextGeneration;
	protected File homeDir;
	protected File backlogFile;
	protected File processlogFile;
	protected long deadSpaceCount = 0;
	protected long txnSpace = 0;
	protected final long deadSpaceThreshold;
	protected final long txnSpaceIncr = 1000000000;
	protected final long cutover_delay_prevention_millis = 100;


	public String toString() {
		return "RebuildingDbService(factory="+factory+", cur="+subService+", next="+nextGeneration+", rebuild="+rebuildingThread+")";
	}
	
	@Override
	public synchronized long getHighestCompletedTxnId() {
		return subService.getHighestCompletedTxnId();
	}
	
	private static DbService makeWithHome(DbServiceFactory factory, File root, String dirName, DbService prevService) {
		File home = new File(root.getAbsolutePath() + File.separatorChar + dirName);
		if (home.exists()) throw new RuntimeException("DbService path already exists: "+home);
		if (!home.mkdir()) throw new RuntimeException("Could not mkdir home: "+home);
		return factory.makeDbService(home, prevService);
	}

	protected void log(Level level, String message) {
		Util.logger.log(level, "Rebuilder @ "+homeDir+": "+message);
	}
	
	public RebuildingDbService(File homeDir, DbServiceFactory factory, long deadSpaceThreshold) {
		super(makeWithHome(factory, homeDir, Util.genUUID(), null));
		subService = new TxnSpacedDbService(subService, txnSpace, txnSpace+txnSpaceIncr);
		subService.setDirtyListener(this);
		openBacklog();
		openProcesslog();
		this.homeDir = homeDir;
		this.factory = factory;
		this.deadSpaceThreshold = deadSpaceThreshold;
		createRebuildThread();
		startRebuildThread();
	}
	
	public RebuildingDbService(File dataRoot, DbServiceFactory dbFactory) {
		this(dataRoot, dbFactory, 2000);
	}

	private void createRebuildThread() {
		rebuildingThread = new RebuildingThread();
		rebuildingThread.setName("RebuildingThread:"+homeDir);
		rebuildingThread.setDaemon(true);
	}

	private void readObject(ObjectInputStream in) {
		try {
			in.defaultReadObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (nextGeneration != null) { // delete partially completed regeneration (though it would be nice to continue it somehow)
			nextGeneration.drop(); // TODO test that this works
		}
		createRebuildThread();
		startRebuildThread();
	}
	
	public DbServiceFactory getFactory() {
		return factory;
	}

	public void setFactory(DbServiceFactory factory) {
		this.factory = factory;
	}

	public void rebuildPrimarySegment(int flushCount) {
		long readTxnId = subService.getHighestCompletedTxnId();
		startRebuild();
		AutocommitBuffer buffer = new AutocommitBuffer(nextGeneration, flushCount);
		for(String bag : subService.getBags(readTxnId)) {
			log(Level.FINER, "Starting rebuild for bag \""+bag+"\"");
			Iterator i = subService.query(bag, readTxnId, Util.literalMap(), Util.literalSMap());
			while(i.hasNext()) {
				DbResultMapView view = (DbResultMapView)i.next();
				Map retCopy = (Map)DbServiceUtil.deepCopyResult(view);
//				System.out.println("REBUILD :"+txnSpace+" : "+retCopy.toString());
				buffer.putEntryIntoBag(retCopy, bag);
			}
		}
		buffer.flush();
	}

	public boolean rebuildCatchupIter() {
		try {
			boolean isFinished = swapIter();
			if (isFinished) return true;
			while(processlog.readBoolean()) {
				processlog.readLong(); // txn id
				log(Level.FINE, "Rebuild: started batch object read");
				Map<String, ApiBuffer> batch = (Map<String, ApiBuffer>)processlog.readObject();
				log(Level.FINE, "Rebuild: about to commit");
				nextGeneration.commit(nextGeneration.getHighestCompletedTxnId(), batch);
				log(Level.FINE, "Rebuild: incremental commit complete");
			}
			processlog.close();
			return false;
		} catch(IOException e) {
			throw new RuntimeException(e);
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void runRebuild() {
		rebuildPrimarySegment(1000);
		// catch up loop
		while(! rebuildCatchupIter()) {}
		log(Level.FINE, "Rebuild complete");
	}
	
	public boolean isRebuilding() {
		return (nextGeneration != null);
	}
	
	public void triggerRebuild() {
		this.deadSpaceCount += this.deadSpaceThreshold;
		synchronized(rebuildingThread) {
			rebuildingThread.notifyAll();
		}
	}
	
	class RebuildingThread extends Thread {

		protected long lastBuild;
		protected long timeDelta;
		public void run() {
			while(true) {
				lastBuild = System.currentTimeMillis();
				timeDelta = 0;
				try {
					synchronized(this) {
						wait(3600);
					}
					if (rebuildingThread != this) {
						break;
					}
					timeDelta = System.currentTimeMillis() - lastBuild;
					if (isPastThreshold()) {
						deadSpaceCount = 0;
						runRebuild();
						lastBuild = System.currentTimeMillis();
					} else if (deadSpaceCount > 0) {
						deadSpaceCount++;
					}
				} catch (Exception e) {
					Util.logger.log(Level.SEVERE, "Unexpected rebuild error for "+homeDir+".  Rebuilding has been suspended for the life of this process.", e);
					throw new RuntimeException(e);
				}
			}
		}

		public boolean isPastThreshold() {
			return (deadSpaceCount + timeDelta/3600 >= deadSpaceThreshold);
		}
	}
	
	public synchronized void startRebuild() {
		String nextHome = Util.genUUID();
		nextGeneration = makeWithHome(factory, homeDir, nextHome, this);
		log(Level.FINE, "Rebuild started into this subdirectory: "+nextHome);
		nextGeneration = new TxnSpacedDbService(nextGeneration, txnSpace+txnSpaceIncr, txnSpace+2*txnSpaceIncr);
		nextGeneration.initialize();
		processlog = null;
		backlog = null;
		setDirty();
	}
	public synchronized boolean swapIter() throws IOException {
		File deleteHome = null;
		deleteProcesslog();
		if (backlog == null) {
			// we're done
			deleteHome = subService.getHomeDir();
			subService = nextGeneration;
			txnSpace += txnSpaceIncr;
			nextGeneration = null;
			notifyAll();
		} else {
			// not done yet
			flipBacklog();
		}
		setDirty();
		if (deleteHome != null) {
			deleteProcesslog();
			Util.deleteDirectory(deleteHome);
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized long commit(long fromTxnId, Map<String, ApiBuffer> batch) {
		if (nextGeneration != null && backlog == null) {
			try {
				this.wait(cutover_delay_prevention_millis); // prefer to cut over to new segment
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		int deadSpaceIncr = 0;
		for(ApiBuffer buf : batch.values()) {
			deadSpaceIncr += buf.estimatedNewDeadSpace();
		}
		long commitTxnId = subService.commit(fromTxnId, batch); // note this alters <batch>
		if (nextGeneration != null) {
			try {
				if (backlog == null) {
					makeBacklog();
				}
				backlog.writeBoolean(true);
				backlog.writeLong(commitTxnId);
				if (batch instanceof Serializable) {
					backlog.writeObject((Serializable)batch);
				} else {
					backlog.writeObject(new HashMap<String,ApiBuffer>(batch));
				}
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		deadSpaceCount += deadSpaceIncr;
		synchronized(rebuildingThread) {
			if (rebuildingThread.isPastThreshold()) {
				rebuildingThread.notifyAll();
			}
		}
		return commitTxnId;
	}

	protected void openBacklog() {
		try {
			if (backlogFile != null) {
				backlog = new ObjectOutputStream(new FileOutputStream(backlogFile));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void makeBacklog() {
		try {
			backlogFile = File.createTempFile("rebuild_backlog", "blg", homeDir);
			Util.logger.log(Level.FINER, "New backlog file: "+backlogFile);
			setDirty();
			openBacklog();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void deleteProcesslog() {
		if (processlogFile != null) {
			if (!processlogFile.delete()) {
				throw new RuntimeException("Unable to delete backlog file "+processlogFile);
			}
			processlogFile = null;
			setDirty();
		}
	}
	protected void openProcesslog() {
		try {
			if (processlogFile != null) {
				processlog = new ObjectInputStream(new FileInputStream(processlogFile));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void flipBacklog() {
		try {
			backlog.writeBoolean(false);
			backlog.close();
			backlog = null;
			log(Level.FINER, "Flipped to next file: "+backlogFile);
			processlogFile = backlogFile;
			setDirty();
			openProcesslog();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public synchronized void rollback(long commitId) throws TxnNotFoundException {
		// handle this with an event in the backlog
		throw new RuntimeException("ned to handle (how do i rollback a commit in the backlog?)");
//		subService.rollback(commitId);
//		if (nextGeneration != null) {
//			try { // this could fail
//				nextGeneration.rollback(commitId);
//			} catch(TxnNotFoundException e) {
//			}
//		}
	}

	public void dropBag(String bagName) {
		// handle this with an event in the backlog
		subService.dropBag(bagName);
		if (nextGeneration != null) {
			nextGeneration.dropBag(bagName);
		}
		throw new RuntimeException("Need to drop any entries in the backlog");
	}
	
	public void drop() {
		// handle this with an event in the backlog?
		subService.drop();
		if (nextGeneration != null) {
			nextGeneration.drop();
		}
		stopRebuildThread();
		super.drop();
	}

	public File getHomeDir() { return homeDir; }
	
	public void startRebuildThread() {
		rebuildingThread.start();
	}

	public void stopRebuildThread() {
		Thread oldThread = rebuildingThread;
		createRebuildThread();
		synchronized(oldThread) {
			oldThread.notifyAll();
		}
		try {
			oldThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public RebuildingThread getRebuildThread() {
		return rebuildingThread;
	}

}
