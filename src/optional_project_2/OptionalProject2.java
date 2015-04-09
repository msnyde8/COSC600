package optional_project_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

public class OptionalProject2 {
	/**
	 * Output file path
	 */
	static String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "optionalproject2_output.txt");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			PrintWriter writer = new PrintWriter(outputName);
			Random randomGenerator = new Random();
			for (int i = 0; i < 20000; ++i)
			{
				int randomInt = randomGenerator.nextInt(100000);
//	        	System.out.println(randomInt);
	        	writer.println(randomInt);
	        	writer.flush();
	    	}
			HeapSort newSort = new HeapSort(outputName);
		}
		catch(FileNotFoundException|IllegalArgumentException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
        	e.printStackTrace();
		}
	}

}

class sortClass {
	/**
	 * Input file path
	 */
	static String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "optionalproject2_output.txt");
	long startTime;
	long endTime;
	String classType = "";
	
	sortClass(){}
	
	void sort(){
		startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis();
	}
	
	void printTime()
	{
		System.out.println(classType + " complete in "+ (endTime - startTime) + " milliseconds");
		return;
	}
}

class InsertionSort extends sortClass {
	InsertionSort(String inName)
	{
		classType = "Insertion Sort";
	}
}

class QuickSort extends sortClass {	
	QuickSort(String inName)
	{
		classType = "Quick Sort";
	}
}

class MergeSort extends sortClass{
	MergeSort(String inName)
	{
		classType = "Merge Sort";
	}
}

class HeapSort extends sortClass {
	int heapSize;
	int intNum;
	int numArray[] = new int [20000];
	
	public void sort(int arr[])
	{
	    try
		{
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();
            System.out.println("here");
            for(int i = 0; readBuff != null; ++i)
            {
    		    numArray[i] = Integer.parseInt(readBuff);
    		    System.out.println("Number " + (i+1) + ": " + numArray[i]);
    		    heapSize = i+1;
            }
		    System.out.println("HeapSize: " + heapSize);
            buffRead.close();
		}
	    catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
	public int LEFT(int i)
	{
		return 2*i+1;
	}
	public int RIGHT(int i)
	{
		return 2*i+2;
	}
	
	HeapSort(String inName)
	{
		classType = "Heap Sort";
		sort();
		printTime();
	}
}