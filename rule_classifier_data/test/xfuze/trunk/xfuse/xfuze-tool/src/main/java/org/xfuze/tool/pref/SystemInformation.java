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

/**
 * @author Jason Chan
 *
 */
public final class SystemInformation {
	/**
	 * Minimum time difference [in milliseconds] enforced for the inputs into
	 * {@link #getProcessCPUUsage(SystemInformation.CPUUsageSnapshot,SystemInformation.CPUUsageSnapshot)}. The
	 * motivation for this restriction is the fact that <code>System.currentTimeMillis()</code> on some systems has a
	 * low resolution (e.g., 10ms on win32). The current value is 100 ms.
	 */
	public static final int MIN_ELAPSED_TIME = 100;
	private static final String LIB_SYS_MON = "libsysmon";

	private SystemInformation() {
		// prevent subclassing
	}

	static {
		// loading a native lib in a static initializer ensures that it is
		// available done before any method in this class is called:
		try {
			System.loadLibrary(LIB_SYS_MON);
		} catch (UnsatisfiedLinkError e) {
			String libraryPath = System.getProperty("java.library.path");
			String message = "native library '" + LIB_SYS_MON + "' not found in 'java.library.path': " + libraryPath;
			System.err.println(message);

			throw e; // re-throw
		}
	}

	/**
	 * A simple class to represent data snapshots taken by {@link #makeCPUUsageSnapshot}.
	 */
	public static final class CPUUsageSnapshot {
		public final long m_time, m_CPUTime;

		// constructor is private to ensure that makeCPUUsageSnapshot()
		// is used as the factory method for this class:
		private CPUUsageSnapshot(final long time, final long CPUTime) {
			m_time = time;
			m_CPUTime = CPUTime;
		}

	}

	// Custom exception class for throwing
	public static final class NegativeCPUTime extends Exception {
		private static final long serialVersionUID = 401138046685850051L;
	}

	/**
	 * Creates a CPU usage data snapshot by associating CPU time used with system time. The resulting data can be fed
	 * into {@link #getProcessCPUUsage(SystemInformation.CPUUsageSnapshot,SystemInformation.CPUUsageSnapshot)}.
	 */
	public static CPUUsageSnapshot makeCPUUsageSnapshot() throws SystemInformation.NegativeCPUTime {
		long prCPUTime = getProcessCPUTime();
		if (prCPUTime < 0) {
			throw new NegativeCPUTime();
		}
		return new CPUUsageSnapshot(System.currentTimeMillis(), getProcessCPUTime());
	}

	/**
	 * Computes CPU usage (fraction of 1.0) between <code>start.m_CPUTime</code> and <code>end.m_CPUTime</code> time
	 * points [1.0 corresponds to 100% utilization of all processors].
	 *
	 * @throws IllegalArgumentException
	 *             if start and end time points are less than {@link #MIN_ELAPSED_TIME} ms apart.
	 * @throws IllegalArgumentException
	 *             if either argument is null;
	 */
	public static double getProcessCPUUsage(final CPUUsageSnapshot start, final CPUUsageSnapshot end) {
		if (start == null) {
			throw new IllegalArgumentException("null input: start");
		}
		if (end == null) {
			throw new IllegalArgumentException("null input: end");
		}
		if (end.m_time < start.m_time + MIN_ELAPSED_TIME) {
			String message = "end time must be at least " + MIN_ELAPSED_TIME + " ms later than start time";
			throw new IllegalArgumentException(message);
		}

		return ((double) (end.m_CPUTime - start.m_CPUTime)) / (end.m_time - start.m_time);
	}

	/**
	 * Returns the PID of the current process. The result is useful when you need to integrate a Java app with external
	 * tools.
	 */
	public static native int getProcessID();

	/**
	 * Returns the number of processors on machine
	 */
	public static native int getCPUs();

	/**
	 * Returns CPU (kernel + user) time used by the current process [in milliseconds]. The returned value is adjusted
	 * for the number of processors in the system.
	 */
	public static native long getProcessCPUTime();

	/**
	 * Returns CPU (kernel + user) time used by the current process [in perecents]. The returned value is either CPU
	 * percentage, or zero if this is not supported by OS. Currently it is supported by Solaris8, and not supported by
	 * Windows XP
	 */
	public static native double getProcessCPUPercentage();

	/**
	 * Returns maximum memory available in the system.
	 */
	public static native long getMaxMem();

	/**
	 * Returns current free memory in the system.
	 */
	public static native long getFreeMem();

	/**
	 * Returns system name info like "uname" command output
	 */
	public static native String getSysInfo();

	/**
	 * Returns CPU usage (fraction of 1.0) so far by the current process. This is a total for all processors since the
	 * process creation time.
	 */
	public static native double getProcessCPUUsage();

	/**
	 * Returns current space allocated for the process, in Kbytes. Those pages may or may not be in memory.
	 */
	public static native long getMemoryUsage();

	/**
	 * Returns current process space being resident in memory, in Kbytes.
	 */
	public static native long getMemoryResident();

	/**
	 * Sets the system native process PID for which all measurements will be done. If this method is not called then the
	 * current JVM pid will act as a default. Returns the native-dependent error code, or 0 in case of success.
	 */
	public static native int setPid(int pid);

	/**
	 * Closes native-dependent process handle, if necessary.
	 */
	public static native int detachProcess();
}
