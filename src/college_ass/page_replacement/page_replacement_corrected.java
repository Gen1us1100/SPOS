package college_ass.page_replacement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class page_replacement_corrected {
	public static void FIFO(int[] pages, int capacity) {
		pages = pages.clone();

		Vector<Integer> framesVector = new Vector<Integer>();
		Queue<Integer> FramesQueue = new LinkedList<Integer>();
		int pageFaults = 0;
		int pageHits = 0;
		System.out.println("\n----------------- First In First Out -----------------");
		System.out.println("incoming\tframes\t Page Hit/Page Fault");
		for (int i = 0; i < pages.length; i++) {
			if (FramesQueue.size() < capacity) {
				if (!FramesQueue.contains(pages[i])) {
					FramesQueue.add(pages[i]);
					framesVector.add(pages[i]);

					pageFaults++;
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]" );
					}
					System.out.print("\tPage Fault");
					System.out.println();
				} else {
					pageHits++;
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]");
					}
					System.out.print("\tPage Hit");
					System.out.println();
				}
			} else {// no place in frames so use FIFO for replacement
				if (!FramesQueue.contains(pages[i])) {
					int temp = FramesQueue.poll(); // removes the first element

					FramesQueue.add(pages[i]);
					int frame_idx = framesVector.indexOf(temp);
					framesVector.removeElement(temp);
					framesVector.add(frame_idx, pages[i]);
					pageFaults++;

					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]");
					}
					System.out.print("\tPage Fault");
					System.out.println();
				} else {
					pageHits++;
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]");
					}
					System.out.print("\tPage Hit");
					System.out.println();
				}
			}
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Page Hits: " + pageHits);
	}

	public static void LRU(int[] pages, int capacity) {

		System.out.println("incoming\tframes\t Page Hit/Page Fault");
		Vector<Integer> framesVector = new Vector<Integer>();

		HashMap<Integer, Integer> indexHashMap = new HashMap<Integer, Integer>();
		int pageFaults = 0;
		int pageHits = 0;
		// Main Logic -- we find a page that has least index used
		// so basically indexHashMap keeps track of indices of pages that are present in
		// queue
		System.out.println("\n----------------- Least Recently Used -----------------");
		for (int i = 0; i < pages.length; i++) {
			if (framesVector.size() < capacity) {
				if (!framesVector.contains(pages[i])) {
					framesVector.add(pages[i]);
					indexHashMap.put(pages[i], i);
					pageFaults++;
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]" );
					}
					System.out.print("\tPage Fault");
					System.out.println();
				} else {
					pageHits++;
					indexHashMap.put(pages[i], i);
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]");
					}
					System.out.print("\tPage Hit");
					System.out.println();
				}
			} else {// no place in frames so use LRU for replacement
				if (!framesVector.contains(pages[i])) {
					int min_IDX = Integer.MAX_VALUE;
					int val = -1;
					for (Integer temp : framesVector) {
						int temp_idx = indexHashMap.get(temp);
						if (temp_idx < min_IDX) {
							min_IDX = temp_idx;
							val = temp;
						}
					}
					int frame_idx = framesVector.indexOf(val);
					framesVector.removeElement(val);
					framesVector.add(frame_idx, pages[i]);
					pageFaults++;
					indexHashMap.put(pages[i], i);
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]" );
					}
					System.out.print("\tPage Fault");
					System.out.println();
				} else {
					pageHits++;
					System.out.print(pages[i] + "\t\t");
					for (Integer j : framesVector) {
						System.out.print("[" + j + "]");
					}
					System.out.print("\tPage Hit");
					System.out.println();
				}
			}
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Page Hits: " + pageHits);
	}

	public static void Optimal(int[] pages, int capacity) {
		int[] mypages = pages.clone();
		System.out.println("\n----------------- Optimal Page Replacement -----------------");
		HashSet<Integer> FramesQueue = new HashSet<Integer>(capacity);
		HashMap<Integer, Integer> indexHashMap = new HashMap<Integer, Integer>();
		int pageFaults = 0;
		int pageHits = 0;
		// Main Logic -- we find a page that is used latermost among current pages
		// HashMap is used to keep track of next indices of pages present in page frames

		for (int i = 0; i < mypages.length; i++) {
			if (FramesQueue.size() < capacity) {
				if (!FramesQueue.contains(pages[i])) {
					FramesQueue.add(mypages[i]);
					int nextIdx = findNextIndex(mypages, i);

					// now store the upcoming index in hashmap
					indexHashMap.put(mypages[i], nextIdx);
					pageFaults++;
					for (Integer j : FramesQueue) {
						System.out.print(j + "\t");
					}
					System.out.println();
				} else {
					pageHits++;
					indexHashMap.put(mypages[i], findNextIndex(mypages, i));
				}
			} else {// no place in frames so use Optimal Page Replacement for replacement
				if (!FramesQueue.contains(mypages[i])) {
					int max_IDX = Integer.MIN_VALUE;
					int val = -1;
					for (Integer temp : FramesQueue) {
						int temp_next_idx = indexHashMap.get(temp);
						if (temp_next_idx > max_IDX) {
							max_IDX = temp_next_idx;
							val = temp;
						}
					}

					FramesQueue.remove(val);
					FramesQueue.add(mypages[i]);
					pageFaults++;
					int nextIdx = findNextIndex(mypages, i);
					indexHashMap.put(mypages[i], nextIdx);
					for (Integer j : FramesQueue) {
						System.out.print(j + "\t");
					}
					System.out.println();
				} else {
					pageHits++;
					indexHashMap.put(mypages[i], findNextIndex(mypages, i));
				}
			}
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Page Hits: " + pageHits);
	}

	private static int findNextIndex(int[] mypages, int curIdx) {
		// TODO finds next occurrence of current page

		for (int i = curIdx + 1; i < mypages.length; i++) {
			if (mypages[i] == mypages[curIdx]) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}

	public static void menu(Scanner myScanner) throws IOException {
		BufferedReader myBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter no. of pages: ");
		int noofpages = myScanner.nextInt();

		int[] pages = new int[noofpages];
		System.out.println("Enter Reference String(separated by space) : ");
		String[] refString = myBufferedReader.readLine().split(" ");
		for (int i = 0; i < refString.length; i++) {
			pages[i] = Integer.parseInt(refString[i]);
		}

		System.out.println("Enter no. of page frames: ");
		int capacity = myScanner.nextInt();
		int option = -1;
		while (option != 0) {
			System.out.println("\n\t----------------- Page Replacement System -----------------");
			System.out.println("Choose one of the following Algorithms: ");
			System.out.println(
					"1. First In First Out(FIFO) \n2. Least Recently Used(LRU) \n3. Optimal Page Replacement \n0. Exit");
			System.out.println("-------------------------------------------------------------");
			System.out.println("Please Enter your choice no.: ");
			option = myScanner.nextInt();
			switch (option) {
			case 1:
				FIFO(pages, capacity);
				break;
			case 2:
				LRU(pages, capacity);
				break;
			case 3:
				Optimal(pages, capacity);
				break;
			case 0:
				System.out.println("Thanks for using this program!");
				System.exit(0);

			default:
				System.out.println("Invalid Option " + option + ", Try Again!");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner myScanner = new Scanner(System.in);
		menu(myScanner);
		myScanner.close();

	}

}
