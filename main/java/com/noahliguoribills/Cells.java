package com.noahliguoribills;
import java.util.Scanner;
import java.util.Hashtable;
import org.apache.commons.lang3.*;

public class Cells {
    //length
    int l;
    //height
    int h;
    //matrix
    int[][] m;
    //random or single seed
    boolean rand;
    //rule
    int rule[];
    //rule mapping
    Hashtable<Integer, Integer> ruleMap = new Hashtable<Integer, Integer>();
    
    //print array for dev purposes
    private void printArray(int[] a) {
    	for (int e : a) {
    		System.out.print(e);
    	}
    }
    
    //round double between 0,1 to 0 or 1
    private int roundDouble(double n) {
    	if (n < 0.5) {
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    //to see internal matrix
    public void printMatrix() {
    	for (int y = 0; y < this.m.length; y++) {
    		System.out.println();
    		for (int x = 0; x < this.m[0].length; x++) {
    			System.out.print(this.m[y][x]);
    		}
    	}
    	System.out.println();
    }
    
    //convert int to binary array, used to compute rule
    private int[] intToBinary(int n, int digits) {
    	int[] binary = new int[digits];
    	for (int i = digits - 1; i >= 0; i--) {
    		if (n - Math.pow(2, i) >= 0) {
    			binary[binary.length - 1 - i] = 1;
    			n -= Math.pow(2, i);
    		} else {
    			binary[binary.length - 1 - i] = 0;
    		}
    	}
    	return binary;
    }
    
    //convert 3 element binary array to base 10 num for hash
    private int tripleToInt(int[] a) {
    	//NOT decimal equivalent, returns hash
    	return 100 * a[0] + 10 * a[1] + a[2];
    }
    
    //compute a row's cells
    private void computeRow(int rowIndex ) {
    	
    	int[] prev = this.m[rowIndex - 1];
    	int[] row = this.m[rowIndex];
    	
    	//special case for first key
    	int[] firstTriple = ArrayUtils.add(ArrayUtils.subarray(prev, 0, 2), 0, 0);
    	int firstKey = this.tripleToInt(firstTriple);
    	row[0] = this.ruleMap.get(firstKey);
    	
    	//special case for last key
    	int[] lastTriple = ArrayUtils.add(ArrayUtils.subarray(prev, this.l - 3, this.l - 1), 0);
    	int lastKey = this.tripleToInt(firstTriple);
    	row[this.l - 1] = this.ruleMap.get(lastKey);
    	
    	//rest of cells
    	for (int i = 1; i < this.l - 1; i++) {
    		int[] triple = ArrayUtils.subarray(prev, i - 1, i + 2);
    		//this.printArray(triple);
    		//System.out.println("");
        	int key = this.tripleToInt(triple);
        	row[i] = this.ruleMap.get(key);
    	}
    }
    
    //initialize
    public Cells() {
    	
    	//initializing matrix dimensions w input
    	Scanner s = new Scanner(System.in);
    	//height
    	System.out.printf("height in pixles: ");
    	this.h = s.nextInt();
    	//width
    	System.out.printf("width in pixles: ");
    	this.l = s.nextInt();
    	//rule
    	System.out.printf("rule (0 to 254): ");
    	this.rule = this.intToBinary(s.nextInt(), 8);
    	//seed type
    	System.out.printf("random or single seed? (r/s): ");
    	if (s.next().charAt(0) == 'r') {
    		this.rand = true;
    	} else {
    		this.rand = false;
    	}
    	s.close();
    	
        this.m = new int[this.h][this.l];
        
        //initialize first row
        if (rand) {
        	for (int i = 0; i < this.l; i++) {
        		int randNum = this.roundDouble(Math.random());
        		this.m[0][i] = randNum;
        	}
        } else {
        	for (int i = 0; i < this.l; i++) {
        		this.m[0][i] = 0;
        	}
        	int midIndex = Math.floorDiv(this.l, 2);
        	this.m[0][midIndex] = 1;
        }
        
        //create rule map
        for (int i = 0; i < 8; i++) {
        	
        	//create all possible triples and their hashes (keys)
        	int[] triple = this.intToBinary(i, 3);
        	int key = this.tripleToInt(triple);
        	
        	//map binary triple to rule outcome
        	this.ruleMap.put(key, this.rule[i]);
        }
        
        //generate all cells
        for (int i = 1; i < this.h - 1; i++) {
        	this.computeRow(i);
        }
        
        //this.printMatrix();
    }
    
    public int getHeight() {
    	return this.h;
    }
    
    public int getWidth() {
    	return this.l;
    }
    
    public int[][] getMat() {
    	return this.m;
    }

    public static void main(String[] args) {
        System.out.println("done.");
    }
}
