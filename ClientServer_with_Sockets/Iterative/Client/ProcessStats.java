/* CNT 4504 COMPUTER NETWORKS AND DISTRIBUTED PROCESSING    
 * Name:   ProcessStats.java
 * Demo Date Thursday, June 5 , 2014
 * Author: Group 5
 * ProcessStats.java offers methods for tracking of statistics. Contains  timer for duration capture.
 */

public class ProcessStats {
	// Private 
	private long startTime;
	private long durationInMillis;
	private int threadId;
	private String markerName;

	// Constructor

	public ProcessStats(int newthreadId, String name) {
		threadId = newthreadId;
		markerName = name;
	}

	// start the clock
	public long start() {
		startTime = System.currentTimeMillis();
		return startTime;
	}

	// stop the clock
	public long getDuration() {
		long endTime = System.currentTimeMillis();
		durationInMillis = endTime - startTime;
		return durationInMillis;
	}

	//return cvs style entry
	public String getStat() {

		return markerName + "," + threadId + "," + durationInMillis;
	}
}
