package college_ass.memory_placement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class First_Next_fit {
	@SuppressWarnings("unchecked")
	public static void First_Fit(ArrayList<Integer> process_size, ArrayList<Integer> block_size, int n, int m) {
		block_size = (ArrayList<Integer>) block_size.clone();
		int[] allocation = new int[process_size.size()];
		Arrays.fill(allocation, -1);

		// main logic -- find first block that can fit the process
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (process_size.get(i) <= block_size.get(j)) {
					if (allocation[i] == -1) {
						allocation[i] = j;
						block_size.set(j, block_size.get(j) - process_size.get(i));
					}
				}
			}
		}
		System.out.println("\n\t--------------- First Fit ---------------");
		Display(process_size, block_size, allocation, n, m);
	}

	private static void Next_Fit(ArrayList<Integer> process_size, ArrayList<Integer> block_size, int n, int m) {
		int[] allocation = new int[process_size.size()];
		Arrays.fill(allocation, -1);
		int j = 0;
		int count = 0;
		// main logic -- find Next block that can fit the process

		for (int i = 0; i < n; i++) {
			count = 0;
			for (; j < m; j = (j + 1) % m) {// mod m will help coming to start after end
				if (count == m) { // avoiding infinitely checking blocks for a process that can't fit
					break;
				}
				if (process_size.get(i) <= block_size.get(j)) {
					if (allocation[i] == -1) {
						allocation[i] = j;
						block_size.set(j, block_size.get(j) - process_size.get(i));

						break;// This ensures that j is not updated
					}
				}
				count++;
			}

		}

		System.out.println("\n\t--------------- Next Fit ---------------");
		Display(process_size, block_size, allocation, n, m);
	}

	public static void Display(ArrayList<Integer> process_array, ArrayList<Integer> block_array, int[] allocated, int n,
			int m) {
		System.out.println("\nProcessNo.\tProcess Size\tBlockNo.\tFree Block Size");
		for (int j = 0; j < allocated.length; j++) {
			System.out.print("\tP" + (j + 1) + "\t\t" + process_array.get(j) + "\t\tB");
			if (allocated[j] != -1) {
				System.out.print((allocated[j] + 1) + "\t\t" + block_array.get(allocated[j]) + "\n"); // plus 1 because
																										// indices are
				// off by 1
			} else {
				System.out.print("\tNot Allocated\n");
			}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner myScanner = new Scanner(System.in);

		System.out.println("Enter no. of processes");
		int n = myScanner.nextInt();
		ArrayList<Integer> process_size = new ArrayList<Integer>(n);

		for (int i = 0; i < n; i++) {
			System.out.println("Enter Size of Process P" + (i + 1));
			process_size.add(myScanner.nextInt());
		}

		System.out.println("Enter no. of Blocks");
		int m = myScanner.nextInt();
		ArrayList<Integer> block_size = new ArrayList<Integer>(n);

		for (int i = 0; i < m; i++) {
			System.out.println("Enter Size of Block B" + (i + 1));
			block_size.add(myScanner.nextInt());
		}

		First_Fit(process_size, block_size, n, m);
		Next_Fit(process_size, block_size, n, m);

		myScanner.close();
	}
}
