/* CSC 403
 * 
 * Robert Keller
 * 
 * 
 * 
 * Homework 1 Driver 
 * 
 * Instructions:  using sizeTest as a template, create additional functions to test
 *                the member functions in your LinkedListST implementation.
 *                AND
 *                create a reasonable set of test cases for each; 
 *                call your testing functions from main
 *                
 */
package csc403;

import stdlib.StdIn;
import stdlib.StdOut;

public class hw1Driver {

		public static void main(String[] args)	{
			// To do:   call you testing functions with your test cases. 
			//  label each test case with a comment describing what you are testing for.
			
			sizeTest("",0);				// test size on an empty ST (symbol table)
			sizeTest("abcde",5);			// test size on a non-empty ST
			sizeTest("zaz",2);			// test size on a repeating substring/key
			
			secondMaxKeyTest("bA7xqa","q");		// tests the second maximum key on a non empty ST
			secondMaxKeyTest("", null);			// tests on an empty ST
			secondMaxKeyTest("7", null);			// tests on a ST with one key
			secondMaxKeyTest("BUllS", "l");		// tests on a ST when max key = second max key
			
			rankTest("a", "bA7xqa", 2);			// tests a key in the middle of the ST
			rankTest("7", "7", 0);				// tests on a ST with one key
			rankTest("l", "BUllS", 3);			// tests while given key is largest and also a duplicate
			
			floorTest("F", "", null);				// test an empty ST
			floorTest("R", "ABCR", "R");				// tests a ST with a floor that is equal to the given key
			floorTest("j", "jjBEARS", "j");			// tests a given key that is larger than all other keys
			
			inverseTest("a", 3);				// test for inverse
			inverseTest("abcde", 98765);		// test for inverse
			inverseTest("zad",789);			// test for inverse

			
		}

		// sample testing function.
		// param vals: all substrings of length 1 are added to the ST
		// param answer:  the correct value of the ST for the input:vals
		public static void sizeTest( String vals, int answer ) {
			
			// create and populate the table from the input string vals
			LinkedListST<String,Integer> aList = new LinkedListST<String,Integer>();
			for (int i=0; i < vals.length(); i++) {
				aList.put(vals.substring(i, i+1),i);
			}
			//  call the size function
			int result = aList.size();
			//report result
			if ( result == answer)  // test passes
				StdOut.format("sizeTest: Correct  String %s Answer: %d\n", vals,result);
			else
				StdOut.format("sizeTest: *Error*  String %s Answer: %d\n", vals,result);
		}
		
		//add your testing functions here
		//See note about testing inverse function
		
		// param vals: all substrings of length 1 are added to the ST
		// param answer: the second largest key of the ST for the input vals or null if ST is < 2
		
		public static void secondMaxKeyTest( String vals, String answer ) {
			
			// create and populate the table from the input string vals
			LinkedListST<String,Integer> aList = new LinkedListST<String,Integer>();
			for (int i=0; i < vals.length(); i++) {
				aList.put(vals.substring(i, i+1),i);
			}
			//  call the secondMaxKey function
			String result = aList.secondMaxKey();
			//report result
			if ((result == null) && (answer == null))  // test passes
				StdOut.format("secondMaxKeyTest: Correct  String %s Answer: %s\n", vals,result);
			else if (result.equals(answer))  // test passes
				StdOut.format("secondMaxKeyTest: Correct  String %s Answer: %s\n", vals,result);
			else
				StdOut.format("secondMaxKeyTest: *Error*  String %s Answer: %s\n", vals,result);
		}
		
		
		// param key: given key for the ST
		// param vals: all substrings of length 1 are added to the ST
		// param answer:  the number of keys smaller than the given key
		public static <Key> void rankTest( String givenKey, String vals, int answer ) {
			
			// create and populate the table from the input string vals
			LinkedListST<String,Integer> aList = new LinkedListST<String,Integer>();
			for (int i=0; i < vals.length(); i++) {
				aList.put(vals.substring(i, i+1),i);
			}
			//  call the rank function
			int result = aList.rank(givenKey);
			//report result
			if ( result == answer)  // test passes
				StdOut.format("rankTest: Correct  String %s Key %s Answer: %d\n",vals, givenKey, result);
			else
				StdOut.format("rankTest: *Error*  String %s Key %s Answer: %d\n",vals, givenKey, result);
		}
		
		
		// param key: given key for the ST
		// param vals: all substrings of length 1 are added to the ST
		// param answer:  the floor key
		public static <Key> void floorTest( String givenKey, String vals, String answer ) {
			
			// create and populate the table from the input string vals
			LinkedListST<String,Integer> aList = new LinkedListST<String,Integer>();
			for (int i=0; i < vals.length(); i++) {
				aList.put(vals.substring(i, i+1),i);
			}
			//  call the floor function
			String result = aList.floor(givenKey);
			//report result
			if ( result == answer)  // test passes
				StdOut.format("floorTest: Correct  String %s Key %s Answer: %s\n",vals, givenKey, result);
			else
				StdOut.format("floorTest: *Error*  String %s Key %s Answer: %s\n",vals, givenKey, result);
		}
		
		
		// param vals: the former key of the ST
		// param answer:  the former value of the ST
		public static void inverseTest( String vals, Integer values  ) {
			
			// create and populate the table from the input string vals
			LinkedListST<String,Integer> aList = new LinkedListST<String,Integer>();
			for (int i=0; i < vals.length(); i++) {
				aList.put(vals, i);
			}
			//  call the inverse function
	
			LinkedListST<Integer,String> result = aList.inverse();

			//report result
			if ( result.get(values) == null)  // test passes
				StdOut.format("inverseTest: Correct  Value %d String: %s\n", values, vals);
			else
				StdOut.format("inverseTest: *Error*  Value %d String: %s\n", values, vals); 

			        
		}
		
		
		
}
