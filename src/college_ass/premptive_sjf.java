package college_ass;

import java.util.Scanner;

public class premptive_sjf {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 0;

		int TotalTime = 0; // Total time for all processes
		int currentTime = 0;// keep track of current time

		Scanner myScanner = new Scanner(System.in);
		System.out.println("Enter no. of processes: ");
		n = myScanner.nextInt();

		int[] AT = new int[n];// Arrival Time
		int[] BT = new int[n];// Burst Time
		int[] RT = new int[n];// Remaining Time -- updated BT

		int[] CT = new int[n];// Completion Time
		int[] TAT = new int[n];// Turn Around Time = completion time - Arrival time
		int[] WT = new int[n];// Waiting Time = TAT - Burst Time

		// take input for arrival time and burst time for each process
		for (int i = 0; i < n; i++) {
			System.out.println("Enter Arrival time for process P" + (i + 1));
			AT[i] = myScanner.nextInt();
			System.out.println("Enter Burst time for process P" + (i + 1));
			BT[i] = myScanner.nextInt();
			RT[i] = BT[i]; // initializing Remaining Time of all processes to their Burst Time
			TotalTime += BT[i];
		}

		while (TotalTime != 0) {
			boolean flag = false;//to check if at least one process is ready
			int minimum = Integer.MAX_VALUE;
			int currentProcess = -1;
			// loops over all process finds the process which has minimum remaining time
			// and is in ready state i.e Arrived & Not Completed
			for (int i = 0; i < n; i++) {
				if (AT[i] <= currentTime && RT[i] > 0) {
					if (RT[i] < minimum) {
						minimum = RT[i];
						currentProcess = i;
						flag=true;
					}
				}
			}
			if(flag==false)
				currentTime++;
			else {
			RT[currentProcess]--;// decrease RT of current process by 1
			currentTime++;
			if (RT[currentProcess] == 0) {// if process is completed
				CT[currentProcess] = currentTime;
			}
			TotalTime--;
			}
		}

		float totalTAT = 0;
		float totalWT = 0;

		for (int i = 0; i < n; i++) {
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			totalTAT += TAT[i];
			totalWT += WT[i];
		}

		System.out.println("\n\t\t----Premptive SJF(SRTF)----\t\t");
		System.out.println("ProcessID\tArrivalT\tBurstT\tCompletionT\tTurnAroundT\tWaitingT");
		for (int i = 0; i < n; i++) {
			System.out.println("P" + (i + 1) + "\t\t" + AT[i] + "\t\t" + BT[i] + "\t\t" + CT[i] + "\t\t" + TAT[i]
					+ "\t\t" + WT[i] + "\n");
		}
		System.out.println("Average Turn Around Time: " + (totalTAT / n));
		System.out.println("Average Waiting Time: " + (totalWT / n));
		myScanner.close();
	}

}


