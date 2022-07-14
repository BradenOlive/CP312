package sorting;

import java.util.*;
import java.time.Clock;

public class testing {
	int[] myList;
	int max;
	Long time;
	
	public static Clock clock = Clock.systemDefaultZone();
	public static long comparisons = 0;
	
	public testing(int num_elements, int max_value) {
		myList = new int[num_elements];
		time = Long.valueOf(0);
		max = max_value;
		
		for(int i=0;i<num_elements;i++) {
			myList[i] = new Random().nextInt(max);
		}
	}
	
	public void insertionSort() {
		System.out.println();
		System.out.println("Starting Insertion Sort...");
		this.StartClock();
		//create a temporary variable to hold the value of the current element being sorted
		int temp = 0;
		
		//we want to sort through very element of the list *except* the first because
		//the first element would already be trivially sorted
		for(int i=1;i<this.myList.length;i++) {
			comparisons++;
			//set temp to the current element to be sorted
			temp = this.myList[i];
			
			//we want to sort the current element into the section of the list thats already sorted, starting from the leftmost element.
			int j = i-1;
			
			//while we are not at the front of the list and the element being compared is larger than temp
			while(j>= 0 && myList[j] > temp) {
				comparisons++;
				comparisons++;
				
				//move the element one space to the right
				this.myList[j+1] = this.myList[j];
				
				//move to the next element (on the left)
				j--;
			}
			//we've either reached the end of the list(j=-1) or the current element j is smaller than temp so
			//place temp into j+1
			this.myList[j+1]= temp;
		}
		
		System.out.println("Insertion Sort Complete.");
		this.StopClock();
	}
	
	public int[] mergeSort(int A[],int start, int end) {
		
		System.out.println("Starting Merge Sort...");
		this.StartClock();
		mergeSortAlgo(A,start,end);
		System.out.println("Merge Sort Complete");
		this.StopClock();
		return A;
		
	}
	
	private int[] mergeSortAlgo(int A[],int start, int end) {
		if(start<end) {
			comparisons++;
			int mid = (start + end)/2;
			A = mergeSortAlgo(A,start,mid);
			A = mergeSortAlgo(A,mid+1, end);
			A = combine_merge_sort(A, start, mid, end);		
		}
		return A;
	}
	
	private int[] combine_merge_sort(int[] A,int start,int mid,int end) {
		int[] left = new int[mid-start+2];
		int[] right = new int[end-mid +1];
		for (int i=start; i<=mid;i++) {
			comparisons++;
			left[i-start] = A[i];
		}
		left[left.length-1] = 99999;
		for(int i=mid+1;i<=end;i++) {
			comparisons++;
			right[i-(mid+1)] = A[i];
		}
		right[right.length-1]=99999;
		int j=0;
		int k=0;
		for(int i=0;i<=end-start;i++) {
			comparisons++;
			if( left[j]<=right[k]) {
				A[start+i] = left[j];
				j++;
			}else {
				A[start +i] = right[k];
				k++;
			}
			comparisons++;
		}
		return A;
		
	}
	
	public void quickSort() {
		System.out.println("Starting Quick Sort...");
		this.StartClock();
		this.myList = quickSortAlgo(this.myList,0,this.myList.length -1);
		System.out.println("Quick Sort Complete");
		this.StopClock();
		return;
	}
	
	private int[] quickSortAlgo(int[] A, int start, int end) {
		if(start<end) {
			int middle = quickSort_Partition(A,start,end);
			A = quickSortAlgo(A,start,middle-1);
			A = quickSortAlgo(A,middle+1,end);
		}
		return A;
	}
	private int quickSort_Partition(int[] A, int start, int end) {
		int x = A[end];
		int i = start-1;
		for(int j=start;j<end;j++) {
			if(A[j]<=x) {
				i=i+1;
				int temp = A[i];
				A[i] = A[j];
				A[j] = temp;
			}
		}
		A[end] = A[i+1];
		A[i+1] = x;
		return i+1;
	}

	private void countingSort() {
		
		System.out.println();
		System.out.println("Starting Counting Sort...");
		this.StartClock();
		
		int[] B = new int[this.myList.length];
		int[] C = new int[this.max +1];
		
		for(int i=0;i<this.myList.length;i++) {
			C[this.myList[i]] = C[this.myList[i]] + 1;
		}
		for(int i=1;i<this.max;i++) {
			C[i] = C[i] + C[i-1];
		}
		for(int i=this.myList.length-1;i>=0;i--) {
			B[C[this.myList[i]]-1] = this.myList[i];
			C[this.myList[i]]--;
		}
		
		this.myList = B;
		System.out.println("Counting Sort Complete.");
		this.StopClock();
	}
	
	private void radixSort() {
		System.out.println();
		System.out.println("Starting Radix Sort...");
		this.StartClock();
		
		for(int i=0;i<Math.log10(this.max);i++) {
			int[] B = new int[this.myList.length];
			int[] C = new int[10];
			
			for(int j=0;j<this.myList.length;j++) {
				C[(this.myList[j] % (int)Math.pow(10, i+1))/(int)(Math.pow(10, i))] = C[(this.myList[j] % (int)Math.pow(10, i+1))/(int)(Math.pow(10, i))] + 1;
			}
			for(int j=1;j<10;j++) {
				C[j] = C[j] + C[j-1];
			}
			for(int j=this.myList.length-1;j>=0;j--) {
				B[C[(this.myList[j] % (int)Math.pow(10, i+1))/(int)(Math.pow(10, i))]-1] = this.myList[j];
				C[(this.myList[j] % (int)Math.pow(10, i+1))/(int)(Math.pow(10, i))]--;
			}
			this.myList = B;
		}
		
		System.out.println("Radix Sort Complete.");
		this.StopClock();
	}
	
	
	//print the list
	public void printList() {
		System.out.println();
		System.out.println("Displaying Array...");
		System.out.print("[");
		for(int i=0;i<myList.length;i++) {
			System.out.print(myList[i]);
			if(i!= myList.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
		System.out.println("Printing Complete.");
	}
	
	public static void printTest(long[] test_times) {
		long sum = 0;
		int count = 0;
		
		for(int i=0;i<test_times.length;i++) {
			sum = sum + test_times[i];
			count++;
		}
		System.out.println("All tests completed.");
		System.out.println(String.valueOf(count) + " test completed with an average time of " + String.valueOf(sum/count) + " milliseconds.");
	}
	//start clock
	public void StartClock() {
		this.time = clock.millis();
	}
	//stop the clock and display operation time
	public void StopClock() {
		this.time = clock.millis() - this.time;
		System.out.println("Operation took " + String.valueOf(this.time) + " milliseconds." );
	}
	public static void ResetComparisons() {
		System.out.println("Operation made " + String.valueOf(comparisons) + " comparisons.");
		comparisons = 0;
	}
	
	public static void main(String[] args) {
		
		int num_tests = 3;
		int ListSize = 1000000000;
		long[] testTimes = new long[num_tests];
		for(int i=0;i<num_tests;i++) {
			System.out.println("Test " + String.valueOf(i));
			testing Test1 = new testing(ListSize,1000);
			//Test1.printList();
			//Test1.quickSort();
			//Test1.insertionSort();
			//Test1.mergeSort(Test1.myList,0, Test1.myList.length-1);
			Test1.countingSort();
			//Test1.radixSort();
			//Test1.printList();
			ResetComparisons();
			testTimes[i] = Test1.time;
			System.out.println();
		}
		
		printTest(testTimes);
		
		
		
	}

}
