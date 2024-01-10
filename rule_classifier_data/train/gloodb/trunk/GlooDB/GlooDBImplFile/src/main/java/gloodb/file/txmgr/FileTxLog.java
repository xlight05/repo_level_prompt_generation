/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.file.txmgr;

import gloodb.txmgr.*;
import gloodb.GlooException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import java.math.BigInteger;

/**
 * Transaction log implementation class. A transaction log consists of a 
 * sequence of xxxxxxxx.tlog files stored in the namespace directory. 
 * The xxxxx number is the transaction log version. Each file containes the 
 * serialized managed object followed by all the write transactions that changed 
 * the managed object state. Based on this information it is easy to restore the 
 * current state of the manged object (deserialize the managed object's initial 
 * state and apply all transactions).
 * 
 * 
 */
class FileTxLog implements TxLog {

    class TLogFilter implements FileFilter {

        private String tlogExtension = configuration.getTlogExtension();

        public boolean accept(File file) {
            try {
                String name = file.getName();
                if (name.endsWith(tlogExtension) && new BigInteger(name.substring(0, name.length() - tlogExtension.length())).compareTo(BigInteger.ZERO) >= 0) {
                    return true;
                }
            } catch (Exception e) {
                // Ignore the exception, but don't include the file.
            }
            return false;
        }
    }
    private final File namespaceDir;
    private BigInteger version;
    private DataOutputStream dos;
    private boolean hasTransactions;
    private final FileTxLogConfiguration configuration;
    private final ReentrantLock lock;

    /**
     * Creates the transaction log namespace (i.e. the directory of the transaction log files).
     * @param nameSpace
     */
    public FileTxLog(FileTxLogConfiguration configuration) {
        this.configuration = configuration;
        this.hasTransactions = false;
        this.lock = new ReentrantLock();
        // Set and create if necessary the base directory
        this.namespaceDir = new File(configuration.getNameSpace());
        if (!namespaceDir.exists()) {
            namespaceDir.mkdirs();
        }
    }

    /**
     * Starts a transaction log. If this is a new log, a 000000.tlog file is
     * created with the serialized managed object. If a .tlog file exists already,
     * the state of the object is loaded from the most recent .tlog file (highest
     * version) then a new tlog is started.
     * @param startState The managed object.
     * @param <T> The managed object type.
     * @return The restored state of the managed object.
     * 
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T start(TxContext ctx, T start) {
    	T startState = start;
        // Search for *.tlog
        File[] logList = namespaceDir.listFiles(new TLogFilter());

        // If there's no such file this is version 0; create a new tlog
        version = BigInteger.ZERO;
        if (logList != null && logList.length > 0) {
            startState = (T) loadLog(ctx, getLastLog(logList));
        }
        // Once the log has been loaded and the managed object state is up to date,
        // start a new log.
        createLog(startState);
        return startState;
    }
    
    private File getLastLog(File[] logList) {
		File lastLog = logList[0];
		for(File f: logList) {
			if(lastLog.getName().compareTo(f.getName()) < 1) lastLog = f;
		}
		return lastLog;
	}

	/**
     * Returns the initial version of the managed object. 
     * @return The initial version of the managed object.
     */
    public BigInteger getStartVersion() {
        // Search for *.tlog
        String tlogExtension = configuration.getTlogExtension();
        File[] logList = namespaceDir.listFiles(new TLogFilter());

        // If there's no such file this is version 0; create a new tlog
        version = BigInteger.ZERO;
        if (logList != null && logList.length > 0) {
        	String name = logList[logList.length - 1].getName();
        	version = new BigInteger(name.substring(0, name.length() - tlogExtension.length()));
        }
        return version;
    }

    /**
     * Closes the current transaction log and starts a new one.
     * @param startState The startState of the new transaction log.
     */
    public void takeSnapshot(Serializable startState) {
    	try {
    		lock.lock();
	        closeLog();
	        createLog(startState);
    	} finally {
    		lock.unlock();
    	}
    }

    /**
     * Returns the version of the transaction log.
     * @return The version of the transaction log.
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Creates a new transaction log. If the current transaction log doesn't 
     * have any transaction then no new log is created.
     * @param startState The initial state of the managed object.
     */
    private void createLog(Serializable startState) {
        try {
            File log = new File(namespaceDir.getAbsolutePath() + File.separator + getTlogName(version));
            if (!hasTransactions && log.exists()) {
                // If there are no transactions in the current log
                // do not create a new one, just re-open this one.
                dos = new DataOutputStream(new FileOutputStream(log, true));
            } else if (log.exists()) {
                throw new GlooException(log.getAbsolutePath() + " already exists.");
            } else {
                dos = new DataOutputStream(new FileOutputStream(log));
                // The initial value in the log is the managed object itself.
                writeLog(startState);
            }
        } catch (IOException e) {
            throw new GlooException(e);
        }
        hasTransactions = false;
    }

    /**
     * Loads the manage object based on the most recent transaction log file. 
     * It first reads the initial object state then it applies all transactions
     * in saved in the log file.
     * @param log The log file.
     * @return The current object state.
     */
    private Serializable loadLog(TxContext ctx, File log) {
        Serializable managedObject = null;
        DataInputStream dis;
        String name = log.getName();
        version = new BigInteger(name.substring(0, name.length() - configuration.getTlogExtension().length()));
        try {
            dis = new DataInputStream(new FileInputStream(log));
            this.hasTransactions = false;
            try {
                TxLogEntry entry = readLog(dis);
                Date timestamp = entry.getTimeStamp();
                // First entry in the log is the initial value of the persistent.
                managedObject = entry.getTransaction();

                while (true) {
                    // Following entries are changes of the persistent (transactions).
                    entry = readLog(dis);
                    this.hasTransactions = true;
                    timestamp = entry.getTimeStamp();
                    Serializable transaction = entry.getTransaction();
                    runLoggedTransaction(ctx, transaction, managedObject, timestamp);
                    version = version.add(BigInteger.ONE);
                }
            } catch (EOFException e) {
                // end of file
            } finally {
                try {
                    dis.close();
                } catch (Exception e) {
                    throw new GlooException(e);
                }
            }
        } catch (IOException e) {
            throw new GlooException(e);
        } catch (ClassNotFoundException e) {
            throw new GlooException(e);
        }
        return managedObject;
    }

    /**
     * Applies a logged transaction to the managed object.
     * @param transaction The transaction to apply.
     * @param managedObject The managed object.
     * @param timestamp The timestamp of the transaction.
     */
    private void runLoggedTransaction(TxContext ctx, Serializable transaction, Serializable managedObject, Date timestamp) {
            if (transaction instanceof TxLogWriteTransaction) {
                try {
	                TxLogWriteTransaction t = (TxLogWriteTransaction) transaction;
	                t.execute(ctx, managedObject, timestamp);
                } catch (Exception e) {
                    // Transaction may throw exceptions while running. This may result
                    // into partial changes to the managed object. When loading the
                    // transaction log allow the transactions to run and just ignore
                    // the exceptions. This way we end up with the same final state of
                    // the managed object.
                }
            } else {
                throw new GlooException("Unknown object found in transaction log: " + transaction);
            }
    }

    /**
     * Closes a transaction log.
     */
    void closeLog() {
        if (dos == null) {
            return;
        }
        try {
            dos.flush();
        } catch (IOException e) {
            throw new GlooException(e);
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                throw new GlooException(e);
            }
        }
    }

    /**
     * Writes the provided value (the initial state of the managed object or 
     * a transaction).
     * @param value The initial managed object or a transaction.
     * @throws java.io.IOException If the transaction log cannot be written.
     */
    private void writeLog(Serializable value) throws IOException {
        TxLogEntry entry = new TxLogEntry(value);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream zipStream = new GZIPOutputStream(bos);
        CipherOutputStream encryptedStream = new CipherOutputStream(zipStream, configuration.getOutputCipher());
        ObjectOutputStream oos = new ObjectOutputStream(encryptedStream);
        
        oos.writeObject(entry);
        oos.flush();
        encryptedStream.flush();
        zipStream.finish();
        zipStream.flush();

        byte[] buf = bos.toByteArray();
        bos.close();
        zipStream.close();
        encryptedStream.close();
        oos.close();
        // This may occur due to encryption problems (for example).
        if (dos == null) {
            throw new GlooException("Invalid transaction log.");
        }
        dos.writeInt(buf.length);
        dos.write(buf);
        dos.flush();
        hasTransactions = true;
    }

    /**
     * Reads the log entry.
     * @param dis The data input stream mapped on the transaction log.
     * @return The next log entry.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    private TxLogEntry readLog(DataInputStream dis) throws IOException, ClassNotFoundException {
        int readLength = dis.readInt();
        byte[] bytes = new byte[readLength];
        dis.read(bytes);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        GZIPInputStream zipStream = new GZIPInputStream(bais);
        CipherInputStream encryptedStream = new CipherInputStream(zipStream, configuration.getInputCipher());
        ObjectInputStream ois = new ObjectInputStream(encryptedStream);
        TxLogEntry result = (TxLogEntry) ois.readObject();
        
        ois.close();
        zipStream.close();
        encryptedStream.close();
        ois.close();
        
        return result;
    }

    /** 
     * Creates a new entry in the transaction log. The transaction log version
     * is incremented.
     * @param value The transaction log entry.
     */
    public void log(Serializable value) {
        try {
        	lock.lock();
            writeLog(value);
            version = version.add(BigInteger.ONE);
        } catch (IOException e) {
            throw new GlooException(e);
        } finally {
        	lock.unlock();
        }
    }

    private String getTlogName(BigInteger txLogVersion) {
        return String.format(configuration.getTlogName(), txLogVersion) + configuration.getTlogExtension();
    }
}
