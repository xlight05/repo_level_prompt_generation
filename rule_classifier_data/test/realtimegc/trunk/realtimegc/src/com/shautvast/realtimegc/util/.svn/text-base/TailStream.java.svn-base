package com.shautvast.realtimegc.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Can handle log file rollovers of 2 types: <li>log4j style: the current file
 * (eg: any.log) has a constant name and rolled files are appended with a
 * postfix (any.log.1) <li>the current file name changes. For instance
 * any_&lt;timestamp&gt;.log <br>
 * The TailStream might pose a performance issue when the volumes are (very)
 * high. Carefully tune the different settings that effect it: <li>
 * skipOldLog</li <li>delay</li> <li>fileCheckInterval</li>
 * 
 * @author shautvast
 */
public class TailStream extends InputStream {
	private final static Log log = LogFactory.getLog(TailStream.class);
	private final static int DEFAULT_BUFFERSIZE = 8192;

	private BlockingQueue<Integer> buffer;
	/**
	 * the log file to work with
	 */
	private File file;

	/**
	 * The file that is monitored. Using RandomAccesFile allows for skipping old
	 * log file entries.
	 */
	private RandomAccessFile raf;

	/**
	 * indicates if the feeder is running
	 */
	private boolean running = false;

	/**
	 * Generator class that delivers the file name. Allows for maximal
	 * possiblities eg. constant name, or daily rolling where the current file
	 * is timestamped.
	 */
	private FileNameGenerator fileNameGenerator;

	/**
	 * Last time the file was checked for rolling.
	 */
	private volatile long lastFileCheckTime;

	/**
	 * The name of the file.
	 */
	private String fileName;

	/**
	 * reader checks filesize rolling by comparing current file size (the File
	 * object associated with the file name) with last read filesize. if there's
	 * a decrease, a roll must have occurred.
	 */
	private long lastLength;

	/**
	 * time that reader waits before checking if the file has rolled. Defaults
	 * to 1 minute.
	 */
	private long fileCheckInterval = 60000;

	/**
	 * time that reader sleeps before polling for new input from the file
	 */
	private long delay = 1000;

	/**
	 * Setting true allows for restarting the process without rereading old
	 * entries. There's never any skipping after detecting a file rollover and
	 * switching to a new file.
	 */
	private boolean skipOldLog = true;

	/**
	 * Creates a reader with the given filename
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public TailStream(String fileName) throws IOException {
		this(new File(fileName), true, DEFAULT_BUFFERSIZE);
	}

	public TailStream(String fileName, int buffersize) throws IOException {
		this(new File(fileName), true, buffersize);
	}

	/**
	 * Creates a reader with the given filename.
	 * 
	 * @param fileName
	 *            the logfile name
	 * @param skipOldLog
	 *            if true, skip what's already in the file
	 * @throws IOException
	 */
	public TailStream(String fileName, boolean skipOldLog, int buffersize)
			throws IOException {
		this(new File(fileName), skipOldLog, buffersize);
	}

	/**
	 * Creates a reader with the given filename.
	 * 
	 * @param fileName
	 *            the logfile name
	 * @param skipOldLog
	 *            if true, skip what's already in the file
	 * @throws IOException
	 */
	public TailStream(String fileName, boolean skipOldLog) throws IOException {
		this(new File(fileName), skipOldLog, DEFAULT_BUFFERSIZE);
	}

	/**
	 * Default constructor for bean usage.
	 */
	public TailStream() {
		super();
	}

	/**
	 * Creates a tailing reader that skips old content
	 * 
	 * @param file
	 * @throws IOException
	 */
	public TailStream(File file) throws IOException {
		this(file, true, DEFAULT_BUFFERSIZE);
	}

	/**
	 * @param skip
	 */
	private void init() {
		log.debug("init");
		try {
			if (fileNameGenerator != null) {
				fileName = fileNameGenerator.getFileName();
				file = new File(fileName);
			}
			raf = new RandomAccessFile(file, "r");
		} catch (Exception e) {
			fileName = "";
			raf = null;
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a reader
	 * 
	 * @param file
	 *            the logfile
	 * @param skipOldLog
	 *            if true, skip what's already in the file
	 * @throws IOException
	 */
	public TailStream(File file, boolean skipOldLog, int buffersize)
			throws IOException {
		super();
		this.file = file;
		buffer = new ArrayBlockingQueue<Integer>(buffersize);
		fileName = file.toString();
		this.skipOldLog = skipOldLog;
		init();
		run();
	}

	/**
	 * Creates a reader with a possibly changing filename
	 * 
	 * @param fileNameGenerator
	 * @see FileNameGenerator
	 * @param skipOldLog
	 *            if true, skip what's already in the file
	 * @throws IOException
	 */
	public TailStream(FileNameGenerator fileNameGenerator, boolean skipOldLog,
			int buffersize) throws IOException {
		super();
		this.fileNameGenerator = fileNameGenerator;
		buffer = new ArrayBlockingQueue<Integer>(buffersize);
		this.skipOldLog = skipOldLog;
		init();
		run();
	}

	public TailStream(FileNameGenerator fileNameGenerator, boolean skipOldLog)
			throws IOException {
		this(fileNameGenerator, skipOldLog, DEFAULT_BUFFERSIZE);
	}

	private void run() {
		if (!isRunning()) {
			setRunning(true);
			new Thread() {
				@Override
				public void run() {
					doRun();
				}
			}.start();
		}
	}

	/**
	 * starts tailing
	 * 
	 * @see java.lang.Thread#run()
	 */
	void doRun() {
		// in case default constructor has been called.
		if (raf == null) {
			throw new IllegalStateException("file name (generator) not set");
		}
		long fileLength = 0, filePointer = 0;
		if (skipOldLog) {
			try {
				filePointer = raf.length();
				raf.seek(filePointer);
			} catch (IOException e) {
				log.error(e);
			}
		}
		int c;
		while (isRunning()) {
			try {
				checkFile();
				if (raf != null) {
					fileLength = raf.length();
					if (fileLength < filePointer) {
						init();
						filePointer = 0;
					}

					if (fileLength > filePointer) {
						// There is data to read
						raf.seek(filePointer);
						while ((c = raf.read()) > -1) {
							buffer.put(c);
						}
					}
					filePointer = raf.getFilePointer();

				}
				Thread.sleep(delay);
			} catch (Exception e) {
				log.warn("Cannot tail " + file.getAbsolutePath(), e);
				try {
					if (raf != null) {
						raf.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				init();
			}
		}
	}

	/**
   * 
   */
	private void checkFile() {
		// LOG.info("check file");
		long time = System.currentTimeMillis();
		if (time - lastFileCheckTime > fileCheckInterval) {
			lastFileCheckTime = time;
			if (fileNameGenerator != null) {
				if (!fileNameGenerator.getFileName().equals(fileName)) {
					init();
				}
			}
			long length = file.length();

			if (length < lastLength) {
				init();
			}
			lastLength = length;
		}
	}

	/**
	 * @return
	 */
	public synchronized boolean isRunning() {
		return running;
	}

	/**
	 * @throws IOException
	 */
	public void close() throws IOException {
		raf.close();
	}

	/**
	 * @param running
	 */
	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @return
	 */
	public long getFileCheckInterval() {
		return fileCheckInterval;
	}

	/**
	 * @param fileCheckInterval
	 */
	public void setFileCheckInterval(long fileCheckInterval) {
		this.fileCheckInterval = fileCheckInterval;
	}

	/**
	 * @return
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * @param delay
	 *            time that reader sleeps before polling for new input from the
	 *            file in milliseconds.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
		this.fileName = file.toString();
		init();
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		setFile(new File(fileName));
		init();
	}

	/**
	 * @return
	 */
	public boolean isSkipOldLog() {
		return skipOldLog;
	}

	/**
	 * @param skipOldLog
	 */
	public void setSkipOldLog(boolean skipOldLog) {
		this.skipOldLog = skipOldLog;
	}

	/**
	 * @return the fileNameGenerator
	 */
	public FileNameGenerator getFileNameGenerator() {
		return fileNameGenerator;
	}

	/**
	 * @param fileNameGenerator
	 *            the fileNameGenerator to set
	 */
	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
		init();
	}

	/**
	 * waits indefinitely for a line to become available
	 * 
	 * @return
	 */
	public String readLine() {
		StringBuilder temp = new StringBuilder();
		int c = 0;
		String line = null;

		for (;;) {
			try {
				c = buffer.take();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			if (c != '\n' && c != -1) {
				temp.append(c);
			} else {
				line = temp.toString();
				return line;
			}
		}
	}

	/**
	 * waits for new input and reads until current eof
	 * 
	 * @return
	 */
	public String readEof() {
		StringBuilder temp = new StringBuilder();

		int c;
		try {
			c = buffer.take();
		} catch (InterruptedException e1) {
			throw new RuntimeException(e1);
		}
		temp.append(c);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (; buffer.size() > 0;) {
			try {
				c = buffer.take();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			temp.append(c);

		}
		return temp.toString();
	}

	@Override
	public int read() throws IOException {
		try {
			return buffer.take();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int available() throws IOException {
		return buffer.size();
	}
}
