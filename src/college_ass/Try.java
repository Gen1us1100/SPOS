package college_ass;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class Process {
	int id;
	int arrivalTime;
	int burstTime;
	int completionTime;
	int turnaroundTime;
	int waitingTime;
	int priority;
}

public class Try {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		System.out.println("Enter the number of processes:");
		int numberOfProcesses = in.nextInt();

		ArrayList<Process> processes = new ArrayList<>();

		for (int i = 0; i < numberOfProcesses; i++) {
			Process process = new Process();
			process.id = i + 1;
			System.out.println("Enter the burst time of Process " + process.id + ":");
			process.burstTime = in.nextInt();
			System.out.println("Enter the arrival time of Process " + process.id + ":");
			process.arrivalTime = in.nextInt();
			System.out.println("Enter the priority of Process " + process.id + ":");
			process.priority = in.nextInt();
			processes.add(process);
		}

		// Sort processes based on arrival time and priority
		Collections.sort(processes, (p1, p2) -> {
			if (p1.arrivalTime == p2.arrivalTime) {
				return p1.priority - p2.priority;
			}
			return p1.arrivalTime - p2.arrivalTime;
		});

		int currentTime = 0;
		for (Process process : processes) {
			if (process.arrivalTime > currentTime) {
				currentTime = process.arrivalTime;
			}

			process.completionTime = currentTime + process.burstTime;
			process.turnaroundTime = process.completionTime - process.arrivalTime;
			process.waitingTime = process.turnaroundTime - process.burstTime;

			currentTime = process.completionTime;
		}

		// Display the results
		System.out
				.println("Process\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
		for (Process process : processes) {
			System.out.println(
					process.id + "\t" + process.arrivalTime + "\t" + process.burstTime + "\t" + process.priority + "\t"
							+ process.completionTime + "\t" + process.turnaroundTime + "\t" + process.waitingTime);
		}

		// Calculate and display the average turnaround time and waiting time
		double totalTurnaroundTime = 0;
		for (Process p : processes) {
			totalTurnaroundTime += p.turnaroundTime;
		}
		double avgTurnaroundTime = processes.isEmpty() ? 0 : totalTurnaroundTime / processes.size();

		// Calculate average waiting time
		double totalWaitingTime = 0;
		for (Process p : processes) {
			totalWaitingTime += p.waitingTime;
		}
		double avgWaitingTime = processes.isEmpty() ? 0 : totalWaitingTime / processes.size();
		System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
		System.out.println("Average Waiting Time: " + avgWaitingTime);
	}
}
