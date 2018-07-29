//Robert Keller
//Homework 5 Test Program

package csc403;


import algs34.LinearProbingHashST;
import algs34.SeparateChainingHashST;
import stdlib.In;
import stdlib.StdOut;
import stdlib.Stopwatch;
import csc403.HW5KeyClass;
import java.util.Arrays;


public class TestHW5 {

	public static void main(String[] args) {
	//	int minWordLen = 1;
	//	int minWordLen = 4;
		int minWordLen = 6;
	//	String file = "data/tinyTale.txt";
	//	String file = "data/tale.txt";
	//	String file = "data/medTale.txt";
		String file = "data/mobydick.txt";
		StdOut.println ("LinearProbingST\n"); 
		testLinearProbingHastST(minWordLen, file);
		StdOut.println ("SepareteChainingST\n");
		testSeparateChainingHashST(minWordLen, file);
	}
	
	private static void testLinearProbingHastST (int minWordLen, String file) {
		LinearProbingHashST<HW5KeyClass, Integer> st = new LinearProbingHashST<>();
		
		In in = new In (file);
		
		while (!in.isEmpty()) {
			String s = in.readString();
			// Obtain ASCII value of first letter of string, used to differentiate numbers that make key
			if (s.length() < minWordLen) continue;
			char c = s.charAt(0);
			int ascii = c * c / 1000;
			// System.out.println(ascii);
			HW5KeyClass key = new HW5KeyClass(ascii, s);
			if (st.contains(key)) { 
				st.put(key, st.get(key) + 1); 
				}
			else	  { 
				st.put(key, 1); 
				}	
		}
		//Question #
		StdOut.println("Question 1 Data");  
		// print table size
		StdOut.println("table size: " + st.size());  

		// Q1
	    // do the experiment for varying sizes of N
		int reps = 30;   

		for (int N=1024; N < 300000; N*=2) {
			Stopwatch sw2 = new Stopwatch();
			for (int r = 1; r <= reps; r++) {   // create and populate the table
				LinearProbingHashST<HW5KeyClass, Integer> test = new LinearProbingHashST<>();
				// fill from st 
				int count = 1;				   	 	// kludgy code to ensure exactly 
				for (HW5KeyClass key: st.keys()) {   // N values are added from the
					test.put(key, 0);          	 	// large data set
					if (count++ == N) break;
				}
			}
			System.out.println(N);
			
			//prints out average elapsed time of each rep
			StdOut.format("%.5f\n",sw2.elapsedTime ()/reps);
			
			// To view ST and hashcode for each key to ensure hash function is working correctly
			/*	for (HW5KeyClass s : st.keys()) {
					StdOut.println(s);
					StdOut.format("s = %s [hashcode=%d]\n", s, s.hashCode());
			}  */
		}
		
		//Question #2
		StdOut.println("\nQuestion 2 Data");  
	    // do the experiment for 512 puts, divide by N for each print to find time to add one put
		Stopwatch sw = new Stopwatch();
		for (int N=1; N < 512; N++) {
			LinearProbingHashST<HW5KeyClass, Integer> test = new LinearProbingHashST<>();
			// fill from st 
			int count = 1;				   	 	 
			for (HW5KeyClass key: st.keys()) {   
				test.put(key, 0);          	 	
				if (count++ == N) break;
			}
			
			StdOut.format("%.5f\n",sw.elapsedTime() / N);
			
		}
		
		//Question #3
		StdOut.println("\nQuestion 3 Data");  
	    // Complexity of Get, used three separate keys to search and divided time by 3
		// Have to run the search 100000 times each round to register readable time
		
		for (int N=1024; N < 300000; N*=2) {
			LinearProbingHashST<HW5KeyClass, Integer> test = new LinearProbingHashST<>();
			// fill from st 
			int count = 1;				   	 	 
			for (HW5KeyClass key: st.keys()) {   
				test.put(key, 0);
				if (count++ == N) break;
			}
			HW5KeyClass key1 = new HW5KeyClass(9, "corn-cob");
			HW5KeyClass key2 = new HW5KeyClass(13, "steadfastly");
			HW5KeyClass key3 = new HW5KeyClass(11, "integument");
			Stopwatch sw3 = new Stopwatch();
			for (int i = 1; i < 100000; i++) {
			test.get(key1);
			test.get(key2);
			test.get(key3);
			}
			StdOut.format("%.5f\n",sw3.elapsedTime() / 3);
			
		}
		
		
	}
	
	private static void testSeparateChainingHashST (int minWordLen, String file) {
		SeparateChainingHashST<HW5KeyClass, Integer> st = new SeparateChainingHashST<>();
		
		In in = new In (file);
		
		while (!in.isEmpty()) {
			String s = in.readString();
			// Obtain ASCII value of first letter of string, used to differentiate numbers that make key
			if (s.length() < minWordLen) continue;
			char c = s.charAt(0);
			int ascii = c * c / 1000;
			HW5KeyClass key = new HW5KeyClass(ascii, s);
			if (st.contains(key)) { 
				st.put(key, st.get(key) + 1); 
				}
			else	  { 
				st.put(key, 1); 
				}	
		}
		
		// print table size
		StdOut.println("table size: " + st.size());  

		// Q1
	    // do the experiment for varying sizes of N
		int reps = 30;   // to account for a potential coarse-grained timer
					     // might be worth playing with to see how many reps
					     // are needed to ensure precision

		for (int N=1024; N < 300000; N*=2) {
			Stopwatch sw = new Stopwatch();
			for (int r = 1; r <= reps; r++) {   // create and populate the table
				SeparateChainingHashST<HW5KeyClass, Integer> test = new SeparateChainingHashST<>();
				// fill from st 
				int count = 1;				   	 	// kludgy code to ensure exactly 
				for (HW5KeyClass key: st.keys()) {   // N values are added from the
					test.put(key, 0);          	 	// large data set
					if (count++ == N) break;
				}
			}
			System.out.println(N);
			StdOut.format("%.5f\n",sw.elapsedTime ()/reps);
			// To view ST and hashcode for each key to ensure hash function is working correctly
			/*for (HW5KeyClass s : st.keys()) {
				StdOut.println(s);
				StdOut.format("s = %s [hashcode=%d]\n", s, s.hashCode());
			}  */
		}
		
		//Question #2
		StdOut.println("\nQuestion 2 Data");  
	    // do the experiment for 512 puts, divide by N for each print to find time to add one put
		Stopwatch sw2 = new Stopwatch();
		for (int N=1; N < 512; N++) {
			SeparateChainingHashST<HW5KeyClass, Integer> test = new SeparateChainingHashST<>();
			// fill from st 
			int count = 1;				   	 	 
			for (HW5KeyClass key: st.keys()) {   
				test.put(key, 0);          	 	
				if (count++ == N) break;
			}
			
			StdOut.format("%.5f\n",sw2.elapsedTime() / N);
			
		}
		
		
		//Question #3
		StdOut.println("\nQuestion 3 Data");  
	    // Complexity of Get, used three separate keys to search and divided time by 3
		// Have to run the search 100000 times each round to register readable time
		
		for (int N=1024; N < 300000; N*=2) {
			SeparateChainingHashST<HW5KeyClass, Integer> test = new SeparateChainingHashST<>();
			// fill from st 
			int count = 1;				   	 	 
			for (HW5KeyClass key: st.keys()) {   
				test.put(key, 0);
				if (count++ == N) break;
			}
			HW5KeyClass key1 = new HW5KeyClass(9, "corn-cob");
			HW5KeyClass key2 = new HW5KeyClass(13, "steadfastly");
			HW5KeyClass key3 = new HW5KeyClass(11, "integument");
			Stopwatch sw3 = new Stopwatch();
			for (int i = 1; i < 100000; i++) {
			test.get(key1);
			test.get(key2);
			test.get(key3);
			}
			StdOut.format("%.5f\n",sw3.elapsedTime() / 3);
			
		}
		
	} 
	
}

