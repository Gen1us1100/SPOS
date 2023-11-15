package college_ass.memory_placement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Best_Worst_Fit {

	@SuppressWarnings("unchecked")
	public static void Best_Fit(ArrayList<Integer> process_size, ArrayList<Integer> block_size, int n, int m) {
		block_size = (ArrayList<Integer>) block_size.clone();
		int[] allocation = new int[process_size.size()];
		Arrays.fill(allocation, -1);
		for (int i = 0; i < n; i++) {
			int best_Idx = -1;// finds smallest block that can fit process
			for (int j = 0; j < m; j++) {

				if (process_size.get(i) <= block_size.get(j)) {
					if (best_Idx == -1) {
						best_Idx = j;
					} else if (block_size.get(best_Idx) > block_size.get(j)) {
						best_Idx = j;
					}
				}
			}
			if (best_Idx != -1) {
				allocation[i] = best_Idx;
				block_size.set(best_Idx, block_size.get(best_Idx) - process_size.get(i));
			}
		}
		System.out.println("\n\t--------------- Best Fit ---------------\t");
		Display(process_size, block_size, allocation, n, m);
	}

	public static void Worst_Fit(ArrayList<Integer> process_size, ArrayList<Integer> block_size, int n, int m) {
		int[] allocation = new int[process_size.size()];
		Arrays.fill(allocation, -1);
		for (int i = 0; i < n; i++) {
			int Worst_Idx = -1;// finds largest block that can fit process
			for (int j = 0; j < m; j++) {
				if (process_size.get(i) <= block_size.get(j)) {
					if (Worst_Idx == -1) {
						Worst_Idx = j;
					} else if (block_size.get(Worst_Idx) < block_size.get(j)) {
						Worst_Idx = j;
					}
				}
			}
			if (Worst_Idx != -1) {
				allocation[i] = Worst_Idx;
				block_size.set(Worst_Idx, block_size.get(Worst_Idx) - process_size.get(i));
			}
		}
		System.out.println("\n\t--------------- Worst Fit ---------------");
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

		Best_Fit(process_size, block_size, n, m);
		Worst_Fit(process_size, block_size, n, m);

		myScanner.close();
	}

}
