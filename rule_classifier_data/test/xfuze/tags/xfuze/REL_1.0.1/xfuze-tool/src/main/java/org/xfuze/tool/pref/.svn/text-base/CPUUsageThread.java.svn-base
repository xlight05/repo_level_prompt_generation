/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xfuze.tool.pref;

import java.util.ArrayList;

import org.xfuze.tool.pref.SystemInformation.NegativeCPUTime;

/**
 * This class shows a sample API for recording and reporting periodic CPU usage shapshots. See <code>CPUmon</code> class
 * for a usage example.
 *
 * @author Jason Chan
 *
 */
public class CPUUsageThread extends Thread {
	private long samplingInterval; // assertion: non-negative
	private final ArrayList<CPUUsageEventListener> listeners;

	private static CPUUsageThread singleton;

	/**
	 * Default value for the data sampling interval [in milliseconds]. Currently the value is 500 ms.
	 */
	public static final int DEFAULT_SAMPLING_INTERVAL = 500;

	/**
	 * Any client interested in receiving CPU usage events should implement this interface and call
	 * {@link #addUsageEventListener} to add itself as the event listener.
	 */
	public static interface CPUUsageEventListener {
		void accept(SystemInformation.CPUUsageSnapshot event);
	}

	/**
	 * Protected constructor used by {@link getCPUThreadUsageThread} singleton factory method. The created thread will
	 * be a daemon thread.
	 */
	protected CPUUsageThread(final long samplingInterval) {
		setName(getClass().getName() + " [interval: " + samplingInterval + " ms]");
		setDaemon(true);

		setSamplingInterval(samplingInterval);

		listeners = new ArrayList<CPUUsageEventListener>();
	}

	/**
	 * Factory method for obtaining the CPU usage profiling thread singleton. The first call constructs the thread,
	 * whose sampling interval will default to {@link #DEFAULT_SAMPLING_INTERVAL} and can be adjusted via
	 * {@link #setSamplingInterval}.
	 */
	public static synchronized CPUUsageThread getCPUThreadUsageThread() {
		if (singleton == null) {
			singleton = new CPUUsageThread(DEFAULT_SAMPLING_INTERVAL);
		}

		return singleton;
	}

	/**
	 * Sets the CPU usage sampling interval.
	 *
	 * @param samplingInterval
	 *            new sampling interval [in milliseconds].
	 * @return previous value of the sampling interval.
	 *
	 * @throws IllegalArgumentException
	 *             if 'samplingInterval' is not positive.
	 */
	public synchronized long setSamplingInterval(final long samplingInterval) {
		if (samplingInterval <= 0) {
			throw new IllegalArgumentException("must be positive: samplingInterval");
		}

		final long old = this.samplingInterval;
		this.samplingInterval = samplingInterval;

		return old;
	}

	/**
	 * Adds a new CPU usage event listener. No uniqueness check is performed.
	 */
	public synchronized void addUsageEventListener(final CPUUsageEventListener listener) {
		if (listener != null)
			listeners.add(listener);
	}

	/**
	 * Removes a CPU usage event listener [previously added via {@link addUsageEventListener}].
	 */
	public synchronized void removeUsageEventListener(final CPUUsageEventListener listener) {
		if (listener != null)
			listeners.remove(listener);
	}

	/**
	 * Records and broadcasts periodic CPU usage events. Follows the standard interruptible thread termination model.
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				SystemInformation.CPUUsageSnapshot snapshot = SystemInformation.makeCPUUsageSnapshot();
				notifyListeners(snapshot);
			} catch (NegativeCPUTime e1) {
				e1.printStackTrace();
			}

			final long sleepTime;
			synchronized (this) {
				sleepTime = this.samplingInterval;
			}

			// for simplicity, this assumes that all listeners take a short time to process
			// their accept()s; if that is not the case, you might want to compensate for
			// that by adjusting the value of sleepTime:
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				return;
			}
		}

		// reset the singleton field [Threads are not restartable]:
		synchronized (CPUUsageThread.class) {
			singleton = null;
		}
	}

	/**
	 * Effects the listener notification.
	 */
	@SuppressWarnings("unchecked")
	private void notifyListeners(final SystemInformation.CPUUsageSnapshot event) {
		final ArrayList<CPUUsageEventListener> listeners;
		synchronized (this) {
			listeners = (ArrayList<CPUUsageEventListener>) this.listeners.clone();
		}

		for (int i = 0; i < listeners.size(); ++i) {
			((CPUUsageEventListener) listeners.get(i)).accept(event);
		}
	}
}
