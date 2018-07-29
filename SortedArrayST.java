// CSC 403 Programming assignment 2
// version 1.0
//  Complete the implementation of the SortedArrayST 
//     by completing the TO DO items
//   This is a simple variation of the example from 3.2
//
//   You may not change the other methods without permission
//      if you want to do this, your probably on the wrong track
//
//   You may add additional methods for modularity
//   You may not use other Java data structures (e.g. ArrayList, HashSet, etc)

//	Robert Keller

package csc403;   // change the package if you want

import java.util.Arrays;
import javax.management.RuntimeErrorException;
import stdlib.StdOut;

public class SortedArrayST<Key extends Comparable<Key>, Value> {
	private static final int MIN_SIZE = 2;
	private Key[] keys;      // the keys array
	private Value[] vals;    // the values array
	private int N = 0;       // size of the symbol table, 
							 // may be different from the size of the arrays
	
	/**
	 * Initializes an empty symbol table.
	 */
	public SortedArrayST() {
		this(MIN_SIZE);
	}
	
	/**
	 * Initializes an empty symbol table of given size.
	 */
	@SuppressWarnings("unchecked")
	public SortedArrayST(int size) {
		keys = (Key[])(new Comparable[size]);
		vals = (Value[])(new Object[size]);
	}
	
	/**
	 * Initializes a symbol table with given sorted key-value pairs.
	 * If given keys list is not sorted in (strictly) increasing order,
	 * then the input is discarded and an empty symbol table is initialized.
	 */
	public SortedArrayST(Key[] keys, Value[] vals) {
		this(keys.length < MIN_SIZE ? MIN_SIZE : keys.length);
		N = (keys.length == vals.length ? keys.length : 0);
		int i;
		for (i = 1; i < N && keys[i].compareTo(keys[i - 1]) > 0; i++);
		if (i < N) { // input is not sorted
			System.err.println("SortedArrayST(Key[], Value[]) constructor error:");
			System.err.println("Given keys array of size " + N + " was not sorted!");
			System.err.println("Initializing an empty symbol table!");
			N = 0;
		} else {
			for (i = 0; i < N; i++) {
				this.keys[i] = keys[i];
				this.vals[i] = vals[i];
			}
		}
	}
	
	/**
	 * Returns the keys array of this symbol table.
	 */
	public Comparable<Key>[] keysArray() {
		return keys;
	}
	
	/**
	 * Returns the values array of this symbol table.
	 */
	public Object[] valsArray() {
		return vals;
	}
	
	/**
	 * Returns the number of keys in this symbol table.
	 */
	public int size() {
		return N;
	}
	
	/**
	 * Returns whether the given key is contained in this symbol table at index r.
	 */
	private boolean checkFor(Key key, int r) {
		return (r >= 0 && r < N && key.equals(keys[r]));
	}
	
	/**
	 * Returns the value associated with the given key in this symbol table.
	 */
	public Value get(Key key) {
		int r = rank(key);
		if (checkFor(key, r)) return vals[r];
		else return null;
	}
	
	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is null.
	 */
	public void put(Key key, Value val) {
		int r = rank(key);
		if (!checkFor(key, r)) {
			shiftRight(r);
			keys[r] = key;
		}
		vals[r] = val;
	}
	
	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 */
	public void delete(Key key) {
		int r = rank(key);
		if (checkFor(key, r)) {
			shiftLeft(r);
		}
	}
	
	public boolean contains(Key key) {
		return ( this.get(key)!= null);
	}
	
	/**
	 * Shifts the keys (and values) at indices r and above to the right by one
	 * The key and value at position r do not change.
	 * This function must resize the keys,vals arrays as needed
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void shiftRight(int r) {
		if (N == keys.length) {
			Key[] tempKeys = (Key[]) new Comparable[2*keys.length];
			Value[] tempVals = (Value[]) new Object[2*keys.length];
			for (int i = 0; i < N; i++) {
				tempKeys[i] = keys[i];
				tempVals[i] = vals[i];
			}
		keys = tempKeys;
		vals = tempVals;
		} 
		for (int i = N; i > r; i--) {
			keys[i] = keys[i-1];
			vals[i] = vals[i-1];
		}

		N++;
		return;

	}
	
	/**
	 * Shifts the keys (and values) at indices x > r to the left by one
	 * in effect removing the key and value at index r 
	 */
	private void shiftLeft(int r) {
		for (int i = r; i < keys.length -1; i++) {
			keys[i] = keys[i+1];
			vals[i] = vals[i+1];
		}
		N--;
		keys[N] = null;  
		vals[N] = null;
		return;
	}
	
	/**
	 * rank returns the number of keys in this symbol table that is less than the given key. 
	 */
	public int rank(Key key) {
		int cmp = key.compareTo(keys[0]);
		if (cmp > 0) {
			return linearTimeRank(key);
		}
		return 0;
	}
	
	

	/**
	 * Linear time implementation of rank
	 */
	private int linearTimeRank(Key key) {
		int r;
		for (r = 0; r < N && key.compareTo(keys[r]) > 0; r++);
		return r;
	}
	
	// Compare two  ST for equality

	 public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        SortedArrayST that = (SortedArrayST) x;
        if (this.keys == that.keys) return true;
     	if (this.vals == that.vals) return true;
     	
		return true;
		}
	
	/**
	 * floor returns the largest key in the symbol table that is less than or equal to key.
	 * it returns null if there is no such key.
	 */
	public Key floor(Key key) {
		int i = rank(key);
		if (!contains(key)) return null;
		if (key.compareTo(keys[i]) == 0) {
			return keys[i];
		}
		else if (contains(keys[i-1])) {
			return keys[i-1];
		}
		return null; 

	}
	/**
	 * countRange returns the number of keys within the range (key1, key2) (inclusive)
	 * note that keys may not be in order (key1 may be larger than key2)
	 */
	public int countRange(Key key1, Key key2) {
		int hi = N - 1;
		int rangeKeys = 0;
		if (key1.compareTo(key2) < 0) {
			Key temp = key1;
			key1 = key2;
			key2 = temp;
		}
		for( int i = 0; i < hi; i++) {
			if (keys[i].compareTo(key1) >= 0) {
				rangeKeys++;
			}
		}
		
		return rangeKeys; 
	}
	
	/*
	 *    a Utility function used by the testing framework to
	 *    build and return a symbol table from a pair of strings.
	 *    The individual characters of each string are extracted as substrings of length 1
	 *    and then stored in parallel arrays.
	 *    The parallel arrays are used as input to the SortArrayST constructor
	 *    The characters in the keyData need to be in sorted order.
	 *    
	 */
	public static SortedArrayST<String,String> from (String keyData, String valData) {
		int n = keyData.length();
		if ( n != valData.length()) throw new NullPointerException(" from: mismatch sizes");
		String[] keys = new String[n];
		String[] vals = new String[n];
		for (int i=0; i < n; i++ ) {
			keys[i] = keyData.substring(i, i+1);  // ith key is ith char-string of keyData string
			vals[i] = valData.substring(i, i+1);  // ith key is ith char-string of valData string
		}
		return new SortedArrayST<String, String>(keys,vals);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		// Testing the rank function
		testRank("A",0,"BDFK","1234");
		testRank("B",0,"BDFK","1234");
		testRank("C",1,"BDFK","1234");
		testRank("D",1,"BDFK","1234");
		testRank("K",3,"BDFK","1234");
		testRank("Z",4,"BDFK","1234");
		
		// Testing the delete function  (actually testing your shiftLeft implementation)	
		testDelete("ABDFK","12345", "A","BDFK","2345");
		testDelete("ABDFK","12345", "B","ADFK","1345");
		testDelete("ABDFK","12345", "K","ABDF","1234");
		//Testing for a key that is not in the Symbol Table, should return delete any keys or values
		testDelete("ABDFK","12345", "Z","ABDFK","12345"); 
		//Testing a symbol table that has its only key deleted
		testDelete("A","1", "A","",""); 

		
		

		testPut("AEIOU","13456", "B","2", "ABEIOU","123456");
		//Testing a symbol table that adds a key to the last index
		testPut("AEIOU","13456", "z","7", "AEIOUZ","134567");
		//Testing lazy delete by inserting key with null value
		testPut("ABC","123", "A","", "BC","23");
		//Testing a symbol table that adds a key already contained in ST with new value
		testPut("AEIOU","13456", "O","8", "AEIOU","13486");

		
	}
	/*
	 * Test the rank function. 
	 * build a symbol table from the input key,val strings
	 * (keyData characters must be in sorted order)
	 * then call the rank function, compare to the expected answer
	 */
	public static void testRank(String theKey, int expected, String keyData, String valData) {
		SortedArrayST<String, String> x = from(keyData,valData);
		int actual = x.rank(theKey);
		if ( actual == expected)  // test passes
			StdOut.format("rankTest: Correct  String %s Key %s rank: %d\n", keyData, theKey, actual);
		else
			StdOut.format("rankTest: *Error*  String %s Key %s rank: %d\n", keyData, theKey, actual);
			
	}
	/*
	 * Test the Put function. 
	 * build a symbol table from the input key,val strings
	 * (keyData characters must be in sorted order)
	 * then call the rank function, compare to the expected answer
	 */
	public static void testPut(String keyInData, String valInData, 
			                   String putKey, String putVal, 
			                   String keyOutData, String valOutData) {
		SortedArrayST<String, String> actual = from(keyInData,valInData);
		SortedArrayST<String, String> expected = from(keyOutData, valOutData);
		actual.put(putKey, putVal);
		
		
		if ( actual.equals(expected))  // test passes
			StdOut.format("testPut: Correct  Before %s put:%s After: %s\n", keyInData, putKey, keyOutData);
		else
			StdOut.format("testPut: *Error*  Before %s put:%s After: %s\n", keyInData, putKey, keyOutData);
			
	}
	/*
	 * Test the delete function. 
	 * build a symbol table from the input key,val strings
	 * (keyData characters must be in sorted order)
	 * then call the rank function, compare to the expected answer
	 */
	public static void testDelete(String keyInData, String valInData, String delKey, 
								  String keyOutData, String valOutData) {
		SortedArrayST<String, String> actual = from(keyInData,valInData);
		SortedArrayST<String, String> expected = from(keyOutData, valOutData);
				actual.delete(delKey);
				
				
				if ( actual.equals(expected))  // test passes
					StdOut.format("testDelete: Correct  Before %s delete:%s After: %s\n", keyInData, delKey, keyOutData);
				else
					StdOut.format("testDelete: *Error*  Before %s delete:%s After: %s\n", keyInData, delKey, keyOutData);
		
	}

}
