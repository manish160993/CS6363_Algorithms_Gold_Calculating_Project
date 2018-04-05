
// Starter code for CS 6363.004 Project (Spring 2018).  Modify as needed.

// Change name of package from "NetId" to your net id
//package mxb172930Project6363;
//package mxb172930Project6363;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.concurrent.TimeUnit;

// Change name of file and class from "NetId" to your net id
public class mxb172930Project6363 {
    static int VERBOSE = 0;
    static class Jewel {
	public int num, weight, profit, minNo, maxNo, fine, cap;
	Jewel(int w, int p, int n, int x, int f, int c) {
	    weight = w;  profit = p;  minNo = n;  maxNo = x;  fine = f;  cap = c;
	}

	public String toString() { return weight + " " + profit + " " + minNo + " " + maxNo + " " + fine + " " + cap; }
    }
	/*
    static class Pair {
	public int p, n;
	Pair(int p, int n) {
	    this.p = p;  this.n = n;
	}

	public String toString() { return p + " " + n; }
    }
	*/

	//	Memoized version to calculate the maximum profit 
	/*
	static int select(Jewel[] r,int k,int f){
		int[][] C=new int[k+1][f];
		
		for(int i=0;i<k+1;i++)
		{
			for(int j=0;j<f;j++){
				C[i][j]=Integer.MIN_VALUE;
			}
		}
		return select1(r,k,f-1,C);
	}
	
	static int select1(Jewel[] r,int k,int i,int[][] C){
		
		if(i==0)
			return 0;
		if(C[k][i]>=0)
		{	
			return C[k][i];
		}
		if(k==0&&i==0)
			return 0;
		

		int max=Integer.MIN_VALUE;
		
		for(int num=0;num<=r[i].maxNo;num++){
			if(num*r[i].weight>k)
				break;
			int total=0;
			int penalty=0;
			if(num<r[i].minNo)
				penalty=-maxi((r[i].minNo-num)*r[i].fine,r[i].cap);
			total=penalty+num*r[i].profit+select1(r,k-r[i].weight*num,i-1,C);
			
			//System.out.println(total+" : "+" " +k+" "+i);
			if(max<total)
				max=total;
		}
		C[k][i]=max;
		//System.out.println();
		return max;
		
	}
	
	*/
	
    public static int[][] process(int G, Jewel[] items, int n) {
	// Code to be written
		int[][] calculate = new int[G+1][items.length]; //Array to store profit
		int[][] calculateNum = new int[G+1][items.length]; //Array to store no of optimal solutions
		
		for(int i=1;i<items.length;i++){
			
			int fine=-maxi((items[i].minNo)*items[i].fine,items[i].cap); //if gold=0 but items are still left
			calculate[0][i]=calculate[0][i-1]+fine;
		}
		
		for(int i=1;i<G+1;i++){
			for(int j=1;j<items.length;j++){
				int max=Integer.MIN_VALUE;
				
				for(int num=0;num<=items[j].maxNo;num++){
					if(num*items[j].weight>i){ // if gold available is less than current item.weight * total items that are to be made
						break;
					}
					int total=0;
					int fine=0;
					if(num<items[j].minNo) // if items made are less than minimum items, then fine calculation
						fine=-maxi((items[j].minNo-num)*items[j].fine,items[j].cap);
					total=fine+num*items[j].profit+calculate[i-items[j].weight*num][j-1]; // total profit made if these many items made
			
					
					if(max==total) // basically to calculate optimal solutions
					{
						if(calculateNum[i-items[j].weight*num][j-1]==0)
							calculateNum[i-items[j].weight*num][j-1]=1;
						calculateNum[i][j]+=calculateNum[i-items[j].weight*num][j-1];
					}
					else if(max<total) // getting maximum profit and 1st optimal solution
					{
						
						
						if(calculateNum[i-items[j].weight*num][j-1]==0)
							calculateNum[i-items[j].weight*num][j-1]=1;
						max=total;
						
						calculateNum[i][j]=calculateNum[i-items[j].weight*num][j-1];
					}
				}
				calculate[i][j]=max; //store maximum value in particular location
			}
			
		}
		
		// Printing the optimal solution and number of optimal solutions
		System.out.println(calculate[G][items.length-1]+" "+calculateNum[G][items.length-1]);
		//return array to enumerate
	return calculate;
    }
	
	// function to calculate minimum value of the 2 numbers
    static int maxi(int a,int b){
		if(a<b)
			return a;
		else
			return b;
	}
	
	
	// function to enumerate the paths
	static void enumerate(LinkedList<Integer> S,int i,int weight1,int[][] calculate,Jewel[] r){
		if(i==0) // if no items left print the path
		{
			for(int j=0;j<r.length-1;j++)
				System.out.print(S.get(j)+" ");
			System.out.println();
			return;
		}
		for(int j=0;j<=maxi(r[i].maxNo,weight1/r[i].weight);j++){
			int penalty=0;
			if(j<r[i].minNo)
				penalty=-maxi((r[i].minNo-j)*r[i].fine,r[i].cap);
			if(calculate[weight1][i]==calculate[weight1-r[i].weight*j][i-1]+j*r[i].profit+penalty)
			{
				S.addFirst(j);
				enumerate(S,i-1,weight1-r[i].weight*j,calculate,r);
				S.removeFirst();
			}
			
		}
		
	}
	
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	if(args.length == 0 || args[0].equals("-")) {
	    in = new Scanner(System.in);
	} else {		
	    File inputFile = new File(args[0]);
	    in = new Scanner(inputFile);
	}
	if (args.length > 1) {
	    VERBOSE = Integer.parseInt(args[1]);
	}
	int G = in.nextInt();
	int n = in.nextInt();
	Jewel[] items = new Jewel[n+1];
	for(int i=0; i<n; i++) {
	    int index = in.nextInt();
	    int weight = in.nextInt();
	    int profit = in.nextInt();
	    int min = in.nextInt();
	    int max = in.nextInt();
	    int fine = in.nextInt();
	    int cap = in.nextInt();
	    items[index] = new Jewel(weight, profit, min, max, fine, cap);
	    if(VERBOSE > 0) { System.out.println(index + " " + items[index]); }
	}
	//long startTime=System.nanoTime();
	int[][] answer = process(G, items, n);
	LinkedList<Integer> S=new LinkedList<>();
	if(VERBOSE > 0)
		enumerate(S,n,G,answer,items);
	//long endTime=System.nanoTime();
	//System.out.println(TimeUnit.NANOSECONDS.toSeconds(endTime-startTime)); // to calculate the time of execution
	//System.out.println(select(items,G,items.length)); // method to call recursive solution. Will work fast except for in40.
    }
}