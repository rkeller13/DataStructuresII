package csc403;
/**
 * Version 1.0
 * 
 * Your homework is to complete the methods marked ToDoX.
 * You must not change the declaration of any method.
 */
// Robert Keller

/**
 *  The B(inary)S(earch)T(ree) class represents a symbol table of
 *  generic key-value pairs.  It supports put, get, and delete methods.
 *  
 *  the book's recursive versions of get and put have been renamed 
 *  rGet  and rPut 
 *  to facilitate testing of your non-recursive versions
 *  
 */
public class BST403<Key extends Comparable<Key>, Value> {
	private Node root;             // root of BST

	private class Node {
		private Key key;           // sorted by key
		private Value val;         // associated data
		private Node left, right;  // left and right subtrees
		
		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
		}
		
		/**
		 * Appends the preorder string representation of the sub-tree to the given StringBuilder.
		 */
		public void buildString(StringBuilder s) {
			s.append(left == null ? '[' : '(');
			s.append(key + "," + val);
			s.append(right == null ? ']' : ')');
			if (left != null) left.buildString(s);
			if (right != null) right.buildString(s);
		}
	}
	/**
	 * Returns the preorder string representation of the BST.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		root.buildString(s);
		return s.toString();
	}
	
	/**
	 * Initializes an empty symbol table.
	 */
	public BST403() {
	}
	
	/* 
	 * return the size of the tree
	 */
	public int size() {
		if (root == null) return 0;
		int N = 0;
		return sizeHelper(root); // ToDo 0
	}
	
	private int sizeHelper(Node x) {
		if (x != null) {
			return sizeHelper(x.left) + sizeHelper(x.right) + 1;
		}
		return 0;
	}
	/**
	 * Returns the value associated with the given key.
	 * Returns null if the key is not in the table
	 * 
	 * ToDo 1   write a non-recursive implementation of get
	 * 
	 */
	public Value get(Key key) {
		Node x = root;
		while (x.key.compareTo(key) != 0) {
			if (x.key.compareTo(key) > 0 && x.left != null) {
				x = x.left;
			}
			else if (x.key.compareTo(key) < 0 && x.right != null) {
				x = x.right;
			}
			else {
				return null;
			}
		}
		if (x.key.compareTo(key) == 0) return x.val;
		else {
		return null; 
		}
		//Todo 1
	}

	

	
	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * 
	 * ToDo 2   write a non-recursive implementation of put
	 * 
	 * 
	 */
	public void put(Key key, Value val) {
		Node x = root;
		while (x.key.compareTo(key) != 0) {
			if (x.key.compareTo(key) > 0 && x.left != null) {
				x = x.left;
			}
			else if (x.key.compareTo(key) < 0 && x.right != null) {
				x = x.right;
			}
			else if (x.key.compareTo(key) > 0 && x.left == null) {
					x.left = new Node (key, val);
				}
			else if (x.key.compareTo(key) < 0 && x.right == null) {
					x.right = new Node (key, val);
				}
			else {
				return;
			}
		}
		if (x.key.compareTo(key) == 0) {
			x.val = val;
			return;
		}
		return;  // ToDo 2
	}
	
	/**
	 * deletes the key (&value) from the table if the key is present
	 * using the the dual of the Hibbard approach from the text. That is, 
	 * for the two-child scenario, delete the node by replacing it 
	 * with it's predecessor (instead of its successor)
	 * 
	 * ToDo 3:  implement a version of delete meeting the above spec
	 * 
	 */
	
	// The test compiler runs for all inputs besides the 3 that are commented out below, as stated in my submission notes
	/* 	public static void deleteTests() {
		testDelete("CA","31","C","A","1");
		//testDelete("CAD","314","D","CA","31");
		//testDelete("ABCDEFG","1234567","G","ABCDEF","123456");  // straight line right
		testDelete("ABCDEFG","1234567","D","ABCEFG","123567");  // straight line right
		//testDelete("GFEDCBA","7654321","A","GFEDCB","765432");  // straight line left
		testDelete("GFEDCBA","7654321","D","GFECBA","765321");  // straight line left
		testDelete("HCADMJZ","1234567", "H", "DCAMJZ","423567"); // delete root
		testDelete("HCADMJZGL","123456789", "M", "HCADLJZG","12349678"); // delete M
		testDelete("MJALBCD","1234567","J", "MDALBC","173456"); // delete J */
	
	public void delete(Key key) {
		root = deleteHelper(root, key);
		  // ToDo 3
	}
	
	private Node deleteHelper(Node x, Key key) {
		if (x == null) return null;
		if (key.compareTo(x.key) > 0) {
			x.right = deleteHelper(x.right, key);
		}
		else if (key.compareTo(x.key) < 0) {
			x.left = deleteHelper(x.left, key);
		}
		else {
			if (x.right == null && x.left != null) return x.left;
			if (x.left == null && x.right != null) return x.right;
			Node temp = x;
			x = max(temp.left);
			x.left = deleteMax(temp.left);  //deletes the predecessor node since it is replacing the deleted node
			x.right = temp.right;
		}
		return x;
	}
	
	public void deleteMax() {
		if (size() == 0) throw new Error("ST does not contain any nodes");
		root = deleteMax(root);
	}
	
	private Node deleteMax(Node x) {
		if (x.right == null) return x.left;  //I think this should be return x, check if I have time
		x.right = deleteMax(x.right);
		return x;
	}
	
	public Key max() {
		if (size() == 0) return null;
		return max(root).key;
	}
	
	private Node max(Node x) {
		if (x.right == null) return x;
		else {
		return max(x.right);
		}
	}
	
	/*
	 * equals determines if two BST403s are exactly the same:
	 * same key-value pairs, same structure
	 * recursion might be a good choice
	 * 
	 * NOT ToDo , but maybe think about how you would do it.
	 * 
	
	public boolean equals(BST403<Key,Value> x) {
		return false;  
	}
	*/

	/**
	 * Returns the number of leaf nodes in the tree
	 * 
	 * ToDo 4
	 */
	public int numLeaves() {
		
		return leafCounterHelper(root); // ToDo 4
	}
	
	private int leafCounterHelper(Node x) {
		if (x == null) return 0;
		if (x.left == null && x.right == null) {
			return 1;
		}
		else {
			return leafCounterHelper(x.left) + leafCounterHelper(x.right);
		}
	}
	
	/**
	 * Returns the length of the shortest path from root to a null node.
	 * 
	 * ToDo 5
	 */
	public int lenShortestPathToNull() {
		return shortestPathHelper(root);
		 // ToDo 5

	}
	
	private int shortestPathHelper(Node x) {
		if (x == null) return 0;
		if (x.left != null && x.right == null) {
			return shortestPathHelper(x.left);
		}
		else if (x.right != null && x.left == null) {
			return shortestPathHelper(x.right);
		}
		else if (x.right == null && x.left == null) {
			return 0;
		}
		else {
			int leftCount = shortestPathHelper(x.left) + 1;
			int rightCount = shortestPathHelper(x.right) + 1;
			if (leftCount < rightCount) {
				return leftCount;
			}
			else {
				return rightCount;
			}
		}
		
	}
	
	/**
	 * Verifies that 'this' is a valid binary search tree
	 * useful to verify that your version of delete works correctly
	 * ToDo 6
	 */
	public boolean isValidBST() {
		return validBSTHelper(root); //ToDo 6
	}
	
	private boolean validBSTHelper(Node x) {
		if (x.left.key.compareTo(x.right.key) > 0) return false;
		else if (x.left.key.compareTo(x.key) > 0) return false;
		else if (x.right.key.compareTo(x.key) < 0) return false;
		else {
			if (x.left != null) return validBSTHelper(x.left);
			else if (x.right != null) return validBSTHelper(x.right);
			return true;
		}
	}
	
	/*****************************************************
	 * 
	 * Utility functions 
	 */
	
	
	public Value rGet(Key key) {
		return rGet(root, key);
	}
	private Value rGet(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return rGet(x.left, key);
		else if (cmp > 0) return rGet(x.right, key);
		else              return x.val;
	}
	
	public void rPut(Key key, Value val) {
		if (key == null) throw new NullPointerException("first argument to put() is null");
		root = rPut(root, key, val);
	}
	
	private Node rPut(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = rPut(x.left,  key, val);
		else if (cmp > 0) x.right = rPut(x.right, key, val);
		else              x.val   = val;
		return x;
	}

}