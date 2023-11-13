package college_ass;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Priority_scheduling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 0;

		int TotalTime = 0; // Total time for all processes
		int currentTime = 0;// keep track of current time

		Scanner myScanner = new Scanner(System.in);
		System.out.println("Enter no. of processes: ");
		n = myScanner.nextInt();
		
		int[] pid = new int[n];// Process ID
		int[] AT = new int[n];// Arrival Time
		int[] BT = new int[n];// Burst Time
		int[] pri = new int[n];
		int[] CT = new int[n];// Completion Time
		int[] TAT = new int[n];// Turn Around Time = completion time - Arrival time
		int[] WT = new int[n];// Waiting Time = TAT - Burst Time
		int[] RT = new int[n];// Remaining Time -- updated BT

		Queue<Integer> readyQueue = new LinkedList<Integer>();

		// take input for arrival time and burst time for each process
		for (int i = 0; i < n; i++) {
			System.out.println("Enter Arrival time for process P" + (i + 1));
			AT[i] = myScanner.nextInt();
			pid[i]= i+1; 
			System.out.println("Enter Burst time for process P" + (i + 1));
			BT[i] = myScanner.nextInt();
			System.out.println("Enter Priority for process P" + (i + 1));
			pri[i] = myScanner.nextInt();
			TotalTime += BT[i];
			RT[i] = BT[i];
		}

		int temp;
		// sorting according to arrival times
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - (i + 1); j++) {
				if (pri[j] > pri[j + 1]) {
					temp = pid[j];
					pid[j] = pid[j + 1];
					pid[j + 1] = temp;
					temp = AT[j];
					AT[j] = AT[j + 1];
					AT[j + 1] = temp;
					temp = BT[j];
					BT[j] = BT[j + 1];
					BT[j + 1] = temp;
					temp = RT[j];
					RT[j] = RT[j + 1];
					RT[j + 1] = temp;
					temp = pri[j];
					pri[j] = pri[j + 1];
					pri[j + 1] = temp;
				}
			}
		}
//		for (int i = 0; i < pri.length; i++) {
//			System.out.println(AT[i] + "\t" + BT[i] + "\t" + RT[i] + "\t" + pri[i]);
//		}
		int currentProcess = -1;
		int completed = 0;
		while (currentTime < TotalTime) {
			for (int i = 0; i < n; i++) {
				if (AT[i] <= currentTime && RT[i] != 0 && (!readyQueue.contains(i))) {
//					if (completed != (n - 1)) {
//						if (i != currentProcess && (!readyQueue.contains(i)))
					readyQueue.add(i);
//					}
				}
			}
			System.out.println("\nCurrentTime: " + currentTime);
			System.out.println("QUEUE: " + readyQueue);
			System.out.println("currentProcess: " + (readyQueue.peek()-1));
			currentProcess = readyQueue.poll();
			RT[currentProcess] = 0;
			currentTime += BT[currentProcess];
			CT[currentProcess] = currentTime;
		}
		float totalTAT = 0;
		float totalWT = 0;
		for (int i = 0; i < n; i++) {
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			totalTAT += TAT[i];
			totalWT += WT[i];
		}

		System.out.println("\n\t\t----Priority scheduling----\t\t");
		System.out.println("ProcessID\tArrivalT\tBurstT\tCompletionT\tTurnAroundT\tWaitingT\tPriority");
		for (int i = 0; i < n; i++) {
			System.out.println("P" + pid[i] + "\t\t" + AT[i] + "\t\t" + BT[i] + "\t\t" + CT[i] + "\t\t" + TAT[i]
					+ "\t\t" + WT[i] + "\t\t" + pri[i] + "\n");
		}
		System.out.println("Average Turn Around Time: " + (totalTAT / n));
		System.out.println("Average Waiting Time: " + (totalWT / n));
		myScanner.close();
	}

}
