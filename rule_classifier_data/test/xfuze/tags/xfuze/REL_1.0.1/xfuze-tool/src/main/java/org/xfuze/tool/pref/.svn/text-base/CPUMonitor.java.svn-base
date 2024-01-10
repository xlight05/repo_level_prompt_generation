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

import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * @author Jason Chan
 *
 */
public class CPUMonitor implements CPUUsageThread.CPUUsageEventListener {
	private static final DecimalFormat CPU_FORMAT = new DecimalFormat("0.0%");
	private static final DecimalFormat MEMORY_FORMAT = new DecimalFormat("##,###");

	private final int processId; // process ID
	private final DecimalFormat format;
	private SystemInformation.CPUUsageSnapshot prevSnapshot;

	public CPUMonitor() {
		processId = SystemInformation.getProcessID();

		format = new DecimalFormat();
		format.setMaximumFractionDigits(1);
	}

	/**
	 * Implements {@link CPUUsageThread.CPUUsageEventListener}. Simply prints the current process PID and CPU usage
	 * since last snapshot to System.out.
	 */
	public void accept(final SystemInformation.CPUUsageSnapshot event) {
		if (prevSnapshot != null) {
			String cpuUsage = " CPU: ";
			cpuUsage += CPU_FORMAT.format(SystemInformation.getProcessCPUUsage(prevSnapshot, event));

			String memoryUsage = "  Mem.: ";
			memoryUsage += MEMORY_FORMAT.format(SystemInformation.getMemoryResident()) + "K resident ";
			memoryUsage += MEMORY_FORMAT.format(SystemInformation.getMemoryUsage()) + "K allocated ";

			System.out.println("[PID: " + processId + "]" + cpuUsage + memoryUsage);
		}

		prevSnapshot = event;
	}

	public static void main(final String[] args) throws Exception {
		if (args.length == 0) {
			throw new IllegalArgumentException("usage: CPUMonitor <app_main_class> <app_main_args...>");
		}

		System.out.println("System Information:");
		System.out.println("----------------------------------------------------------------------");
		System.out.println(SystemInformation.getSysInfo());
		System.out.println("----------------------------------------------------------------------");

		final CPUUsageThread monitor = CPUUsageThread.getCPUThreadUsageThread();
		final CPUMonitor cpuMonitor = new CPUMonitor();

		final Class<?> app = Class.forName(args[0]);
		final Method appMain = app.getMethod("main", new Class[] { String[].class });
		final String[] appArgs = new String[args.length - 1];
		System.arraycopy(args, 1, appArgs, 0, appArgs.length);

		monitor.addUsageEventListener(cpuMonitor);
		monitor.start();
		appMain.invoke(null, new Object[] { appArgs });
	}
}
