package csc403;

import algs13.Queue;
import stdlib.StdOut;

/**
 * Complete the methods marked TODO.
 * You must not change the declaration of any method.
 */

/**
 *  The LinkedListST class represents an (unordered) symbol table of
 *  generic key-value pairs.  It supports put, get, and delete methods.
 */
public class LinkedListST<Key extends Comparable<Key>, Value extends Comparable<Value>> {
    private Node first;      // the linked list of key-value pairs

    // a helper linked list data type
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public LinkedListST() {
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     */
    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null"); 
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null.
     */
    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("first argument to put() is null"); 
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     */
    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null"); 
        first = delete(first, key);
    }

    // delete key in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * size returns the number of key-value pairs in the symbol table.
     * it returns 0 if the symbol table is empty.
     */
   
    public int size () {
    		int n = 0;
    		for (Node x = first; x != null; x = x.next) {
    			n++;
    		}
    		return n;  
    }
    
    /**
     * secondMaxKey returns the second maximum key in the symbol table.
     * it returns null if the symbol table is empty or if it has only one key.
     *  See if you can write it with only one loop
     */
   
	public Key secondMaxKey () {
		if (first == null || first.next == null) {
			return null;
		}
		Node maxNode = first;
		Node secondMaxNode = first.next;
		if (secondMaxNode.key.compareTo(maxNode.key) == 1) {
			maxNode = secondMaxNode;
			secondMaxNode = first;
		}	
    		for (Node x = secondMaxNode.next; x != null; x = x.next) {
    				if (x.key.compareTo(maxNode.key) == 1) {
    					maxNode = x;
    			}
    				else if ((x.key.compareTo(secondMaxNode.key) == 1) && (x.key.compareTo(maxNode.key) < 0)) {
    					secondMaxNode = x;	
    			}	
    		}
    		
		return secondMaxNode.key; 
    }


    /**
     * rank returns the number of keys in this symbol table that is less than the given key.
     * your implementation should be recursive. 
     */
	int n = 0;
    public int rank(Key key) {
        if (key == null) return 0;
        else if (key.compareTo(first.key) > 0) {
        		n++;
        		if (first.next != null) {
        			first = first.next;
        			return rank(key);
        		}
        }	
        if (first.next != null) {
    			first = first.next;
    			return rank(key);
        }
    		return n;
    }

    
    
    /**
     * floor returns the largest key in the symbol table that is less than or equal to the given key.
     * it returns null if there is no such key.
     */
    public Key floor (Key key) {
		if (first == null) {
			return null;
		}
		Node floorNode = first;
		if (floorNode.key.compareTo(key) <= 0) {
			floorNode.key = key;
		} 
    		for (Node x = first.next; x != null; x = x.next) {
    				if (x.key.compareTo(key) <= 0) {
    					if (x.key.compareTo(floorNode.key) > 0) {
    						floorNode.key = x.key;
    					}
    				}
    		}
		return floorNode.key; 
    }

    
    
    /**
     * inverse returns the inverse of this symbol table.
     * if the symbol table contains duplicate values, you can use any of the keys for the inverse
     */
    public LinkedListST<Value, Key> inverse () {
    		LinkedListST<Value, Key> valKey = new LinkedListST<>();
            for (Node x = first; x != null; x = x.next) {
            		Value invVal = x.val;
            		Key invKey = x.key;
            		valKey.put(invVal,invKey);
                }
            
           return valKey;
    }
    

    
    
    public Iterable<Key>  keys() {
    	Queue<Key> theKeys = new Queue<Key>();
    	for ( Node temp = first; temp != null; temp=temp.next) {
    		theKeys.enqueue(temp.key);
    	}
    	return theKeys;
    }
}