package dovetaildb.dbrepository;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

import dovetaildb.dbservice.DbService;
import dovetaildb.util.Base64;
import dovetaildb.util.Dirty;

public class FileDbRepository extends StandardDbRepository {

	private static final long serialVersionUID = -7691737344294137582L;
	
	transient String filename;
	
	public FileDbRepository(String filename) {
		super();
		this.repo = new ConcurrentHashMap<String, DbRecord>();
		this.filename = filename;
	}

	public String getFile() { return this.filename; }
	
	private synchronized void save() {
		ObjectOutputStream s;
		try {
			s = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			s.writeInt(1); // verison
			// do not persist listener, if any
			Dirty.Listener listener = dirtyListener;
			dirtyListener = null;
			s.writeObject(this);
			dirtyListener = listener;
			s.flush();
			s.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static FileDbRepository load(String filename) {
		if (! new File(filename).exists()) {
			throw new RuntimeException("Metadata file \""+filename+"\" does not exist");
		} else {
			ObjectInputStream s;
			try {
				s = new ObjectInputStream(new FileInputStream(new File(filename)));
				int ver = s.readInt();
				if (ver > 1) throw new RuntimeException("Version "+ver+" is too new for this code to handle.");
				FileDbRepository fileRepo = (FileDbRepository)s.readObject();
				fileRepo.filename = filename;
				fileRepo.initMeta();
				return fileRepo;
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// Overrides to call save() when changes are made
	
	public void setDb(String dbName, DbService dbService) {
		super.setDb(dbName, dbService);
		save();
	}
	
	public boolean deleteDb(String dbName) {
		boolean ret = super.deleteDb(dbName);
		save();
		return ret;
	}
	
	@Override
	public void setCodeFile(String dbName, String fileName, String content) {
		super.setCodeFile(dbName, fileName, content);
		save();
	}
	
	@Override
	public boolean deleteCodeFile(String dbName, String fileName) {
		boolean ret = super.deleteCodeFile(dbName, fileName);
		save();
		return ret;
	}
	
	@Override
	public String getCodeFile(String dbName, String filename) {
		return repo.get(dbName).code.get(filename);
	}
		
	public void force() {
		save();
	}

	public String toString() {
		return "FileDbRepository@"+filename+" "+repo;
	}
	
}
