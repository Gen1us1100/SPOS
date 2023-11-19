package college_ass.scheduling;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin {

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
		Queue<Integer> readyQueue = new LinkedList<Integer>();

		// take input for arrival time and burst time for each process
		for (int i = 0; i < n; i++) {
			System.out.println("Enter Arrival time for process P" + (i + 1));
			AT[i] = myScanner.nextInt();
			System.out.println("Enter Burst time for process P" + (i + 1));
			BT[i] = myScanner.nextInt();
			RT[i] = BT[i]; // initializing Remaining Time of all processes to their Burst Time
			TotalTime += BT[i];
		}
		System.out.println("\nEnter Time Quantum: ");
		int temp = 0;
		// sorting according to arrival times
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - (i + 1); j++) {
				if (AT[j] > AT[j + 1]) {
					temp = AT[j];
					AT[j] = AT[j + 1];
					AT[j + 1] = temp;
					temp = BT[j];
					BT[j] = BT[j + 1];
					BT[j + 1] = temp;
					temp = RT[j];
					RT[j] = RT[j + 1];
					RT[j + 1] = temp;
				}
			}
		}

		int TimeQ = myScanner.nextInt();
		int currentProcess = -1;
		int completed = 0;
		while (currentTime < TotalTime) {
			for (int i = 0; i < n; i++) {
				if (AT[i] <= currentTime && RT[i] > 0) {
					if (completed != (n - 1)) {
						if (i != currentProcess && (!readyQueue.contains(i)))
							readyQueue.add(i);
					}
				}
			}

			if (!readyQueue.isEmpty())
				currentProcess = readyQueue.poll();

			if (RT[currentProcess] < TimeQ) {
				RT[currentProcess] = 0;
				completed++;
				currentTime++;
				CT[currentProcess] = currentTime;
			} else if (RT[currentProcess] == TimeQ) {
				RT[currentProcess] -= TimeQ;
				completed++;
				currentTime += TimeQ;
				CT[currentProcess] = currentTime;
			} else {
				RT[currentProcess] -= TimeQ;
				currentTime += TimeQ;
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

		System.out.println("\n\t\t----Round Robin----\t\t");
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
