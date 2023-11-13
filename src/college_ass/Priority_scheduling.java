package college_ass;

import java.util.Scanner;

public class Priority_scheduling {
	class process {
		public int pid, AT, BT, CT, TAT, WT, Pri;
		int RT = BT;

		public process() {
			// TODO Auto-generated constructor stub
			this.pid = this.AT = this.BT = this.CT = this.TAT = this.WT = this.Pri = 0;
		}
	}

	public static void sortProcesses(process[] myProcesses) {
		process temp;
		// sorting according to arrival times and priority
		for (int i = 0; i < myProcesses.length; i++) {
			for (int j = 0; j < myProcesses.length - (i + 1); j++) {
				if (myProcesses[j].AT > myProcesses[j + 1].AT && myProcesses[j].Pri > myProcesses[j + 1].Pri) {
					temp = myProcesses[j];
					myProcesses[j] = myProcesses[j + 1];
					myProcesses[j + 1] = temp;
				}
			}
		}

	}

	public static void main(String[] args) {
		int n = 0;
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Enter no. of processes: ");
		n = myScanner.nextInt();
		Priority_scheduling myInstance = new Priority_scheduling(); 
		process[] processes = new process[n];
		for (int i = 0; i < n; i++) {
			processes[i] = myInstance.new process();
			System.out.println("Enter Arrival time for process P" + (i + 1));
			processes[i].AT = myScanner.nextInt();
			processes[i].pid = i + 1;
			System.out.println("Enter Burst time for process P" + (i + 1));
			processes[i].BT = myScanner.nextInt();
			System.out.println("Enter Priority for process P" + (i + 1));
			processes[i].Pri = myScanner.nextInt();
		}

		sortProcesses(processes);

		for (int i = 0; i < processes.length; i++) {
			if (i == 0) {
				processes[i].CT = processes[i].BT;
			} else {
				processes[i].CT = processes[i - 1].CT + processes[i].BT;
			}
		}

		float totalTAT = 0;
		float totalWT = 0;
		for (int i = 0; i < n; i++) {
			processes[i].TAT = processes[i].CT - processes[i].AT;
			processes[i].WT = processes[i].TAT - processes[i].BT;
			totalTAT += processes[i].TAT;
			totalWT += processes[i].WT;
		}

		System.out.println("\n\t\t----Priority scheduling----\t\t");
		System.out.println("ProcessID\tArrivalT\tBurstT\tCompletionT\tTurnAroundT\tWaitingT\tPriority");
		for (int i = 0; i < n; i++) {
			System.out.println("P" + processes[i].pid + "\t\t" + processes[i].AT + "\t\t" + processes[i].BT + "\t\t"
					+ processes[i].CT + "\t\t" + processes[i].TAT + "\t\t" + processes[i].WT + "\t\t" + processes[i].Pri
					+ "\n");
		}
		System.out.println("Average Turn Around Time: " + (totalTAT / n));
		System.out.println("Average Waiting Time: " + (totalWT / n));

		myScanner.close();
	}
}
