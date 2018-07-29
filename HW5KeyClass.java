//Robert Keller
//Homework 5 custom Key Class

package csc403;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import algs34.LinearProbingHashST;
import stdlib.*;


public class HW5KeyClass {
	int i;
	String s;
	
	
	public HW5KeyClass (int i, String s) {
		this.i = i;
		this.s = s;
	}
	
	@Override
	public boolean equals(Object x) {
		if (x == this) return true;
		if (x == null) return false;
		if (x.getClass() != this.getClass()) return false;
		HW5KeyClass that = (HW5KeyClass) x;
		if (!((this.i == that.i) && (this.s == that.s))) return false;
		return true;
	}
	
	
	public String toString() {
		return String.format("%d %s", i, s);
		 
	}  
	
	@Override
	public int hashCode() {
		int h = 17;
		h = 31 * h + ((Integer) i).hashCode();
		h = 31 * h + s.hashCode();
		return h;
	}
	

	
	LinearProbingHashST<HW5KeyClass, Integer> st = new LinearProbingHashST<>();
	
	public static void main(String[] args) {
		HW5KeyClass a = new HW5KeyClass(3, "hello");
		HW5KeyClass b = new HW5KeyClass(0, "Cubs");
		HW5KeyClass c = new HW5KeyClass(13, "Bears");
		HW5KeyClass d = new HW5KeyClass(13, "Bears");
		
		StdOut.format("a = %s [hashcode=%d]\n", a, a.hashCode());
		StdOut.format("b = %s [hashcode=%d]\n", b, b.hashCode());
		StdOut.format("c = %s [hashcode=%d]\n", c, c.hashCode());
		StdOut.format("d = %s [hashcode=%d]\n", d, d.hashCode());
		
		HashSet<HW5KeyClass> set = new HashSet<>();
		set.add(a);
		set.add(b);
		set.add(c);
		set.add(d);
		StdOut.println("contains a:  " + set.contains(a));
		StdOut.println("contains b:  " + set.contains(b));
		StdOut.println("contains c:  " + set.contains(c));
		StdOut.println("contains d:  " + set.contains(d));
		StdOut.println("a == c:      " + (a == c));
		StdOut.println("c.equals(d): " + (c.equals(d)));
	}

}
