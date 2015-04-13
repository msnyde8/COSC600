

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Random;

public class OptionalProject2 {
	/**
	 * Output file path
	 */
	static String pathName = System.getProperty("user.dir") + System.getProperty("file.separator");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{

			String genName = pathName + "optionalproject2_input.txt";

			PrintWriter writer = new PrintWriter(genName);
			Random randomGenerator = new Random();
			for (int i = 0; i < 20000; ++i)
			{
				int randomInt = randomGenerator.nextInt();
//	        	System.out.println(randomInt);
	        	writer.println(randomInt);
	        	writer.flush();
	    	}
			writer.close();

			SortClass sortObj = new SortClass(genName);
			sortObj.sort();
		}
		catch(FileNotFoundException|IllegalArgumentException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
        	e.printStackTrace();
		}
	}

}

class SortClass {
	/**
	 * Input file path
	 */
	private String inputName = OptionalProject2.pathName + "optionalproject2_input.txt";
	private String outputName = OptionalProject2.pathName + "optionalproject2_output.txt";
	private PrintWriter outputWriter;
	
	private int numElements = 20000;
	private int heapSize;
	private long startTime = 0;
	private long endTime = 0;
	private String classType = "";

	SortClass(String inName)
	{
		if(false == inName.isEmpty())
		{
			inputName = inName;
		}
	}
	
	void sort()
	{
		int numArray[] = new int [numElements];

		try
		{
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();

            for(int i = 0; (readBuff != null) && (i < numElements); ++i)
            {
    		    numArray[i] = Integer.parseInt(readBuff);
//    		    System.out.println("Number " + (i+1) + ": " + numArray[i]);
    		    //heapSize = i+1;
    		    readBuff = buffRead.readLine();
            }
            buffRead.close();

            outputWriter = new PrintWriter(outputName);
		
			// Insertion Sort
			int insertArray[] = new int [numElements];
			System.arraycopy(numArray, 0, insertArray, 0, numArray.length);
//			InsertionSort(insertArray);
	
			// Quick Sort
			int quickArray[] = new int [numElements];
			System.arraycopy(numArray, 0, quickArray, 0, numArray.length);
			QuickSort(quickArray);
			
			//Merge Sort
			int mergeArray[] = new int [numElements];
			System.arraycopy(numArray, 0, mergeArray, 0, numArray.length);
//			MergeSort(mergeArray);
			
			//Heap Sort
			int heapArray[] = new int [numElements];
			System.arraycopy(numArray, 0, heapArray, 0, numArray.length);
//			HeapSort(heapArray);

			outputWriter.close();
		}
	    catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
		

	}
	
	void printTime()
	{
		String printStr = classType + " complete in " + (endTime - startTime) + " milliseconds";
		System.out.println(printStr);
		outputWriter.println(printStr);
		return;
	}
	void printArray(int pArray[])
	{
		for(int i = 0; i < pArray.length; ++i)
		{
			System.out.println("Number " + (i+1) + ": " + pArray[i]);
		}
	}
	
	void aSwap(int qSwapArray[], int firstIndex, int secondIndex)
	{
		try
		{
			int tempInt = qSwapArray[firstIndex];
			qSwapArray[firstIndex] = qSwapArray[secondIndex];
			qSwapArray[secondIndex] = tempInt;
		}
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}

	void InsertionSort(int iArray[])
	{
		classType = "Insertion Sort";
//		System.out.println("Array size: " + numArray.length);
		
		startTime = System.currentTimeMillis();
		try
		{
			for(int i = 1; i < iArray.length; ++i)
			{
				int tempInt = iArray[i];
				int j;
				for (j = i-1; (j >= 0) && (tempInt < iArray[j]); --j)
				{
					iArray[j+1] = iArray[j];
				}
				iArray[j+1] = tempInt;
			}
		}
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
		endTime = System.currentTimeMillis();
		
		printTime();
//		printArray(numArray);
	}
	
	void QuickSort(int qArray[])
	{
		classType = "Quick Sort";
		printArray(qArray);
//		System.out.println("Array size: " + qArray.length);
		long startTime1 = System.currentTimeMillis();
		qSort(qArray, 0, (qArray.length - 1));
		long endTime1 = System.currentTimeMillis();
		printArray(qArray);
		System.out.println(startTime1);
		System.out.println(endTime1);
		printTime();
	}
	void qSort(int qSortArray[], int startIndex, int endIndex)
	{
		int newStart = startIndex;
		int newEnd = endIndex;

		if((endIndex - startIndex) < 1)
		{
			return;
		}
		
		int pivotInt = qSortArray[startIndex];

		while(newStart < newEnd)
		{
			while((newStart <= endIndex) && (newStart < newEnd) && (qSortArray[newStart] <= pivotInt))
			{
				++newStart;
			}
			while( (qSortArray[newEnd] > pivotInt) && (startIndex <= newEnd) && (newStart <= newEnd))
			{
				--newEnd;
			}
			if(newStart < newEnd)
			{
				aSwap(qSortArray, newStart, newEnd);
			}
		}
		
		aSwap(qSortArray, startIndex, newEnd);
		
		qSort(qSortArray, startIndex, (newEnd - 1));
		qSort(qSortArray, newEnd+1, endIndex);
	}
	
	void MergeSort(int mArray[])
	{
		classType = "Merge Sort";
//		printArray(mArray);
		startTime = System.currentTimeMillis();
		mSort(mArray, 0, (mArray.length-1));
		endTime = System.currentTimeMillis();
//		printArray(mArray);
		printTime();
	}
	void mSort(int mSortArray[], int startIndex, int endIndex)
	{
		try
		{
			if(startIndex >= endIndex)
			{
				return;
			}
			
			int leftEnd = ((startIndex + endIndex) / 2);
			int rightStart = (leftEnd + 1);
				
			mSort(mSortArray, startIndex, leftEnd);
			mSort(mSortArray, rightStart, endIndex);
				
			while((startIndex <= leftEnd) && (rightStart <= endIndex))
			{
				if(mSortArray[startIndex] < mSortArray[rightStart])
				{
					++startIndex;
				}
				else
				{
					int tempNum = mSortArray[rightStart];
					for(int k = rightStart-1; k >= startIndex; --k)
					{
						mSortArray[k+1] = mSortArray[k];
					}
					mSortArray[startIndex] = tempNum;
					++startIndex;
					++leftEnd;
					++rightStart;
				}
			}
		}
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
	
	void HeapSort(int hArray[])
	{
		classType = "Heap Sort";
//		printArray(hArray);
		startTime = System.currentTimeMillis();
		hSort(hArray);
		endTime = System.currentTimeMillis();
//		printArray(hArray);
		printTime();
	}
	void hSort(int hSortArray[])
	{
		heapSize = (hSortArray.length-1);
		for(int i = (heapSize/2); i >= 0; --i)
		{
			heapify(hSortArray, i);
		}
		for(int j = heapSize; j > 0; --j)
		{
			aSwap(hSortArray, 0, j);
			heapSize--;
			heapify(hSortArray, 0);
		}
	}
	void heapify(int aHeap[], int hIndex)
	{
		int leftIndex = (hIndex*2);
		int rightIndex = (leftIndex + 1);
		int greatestIndex = hIndex;
		
		if ((leftIndex <= heapSize) && (aHeap[leftIndex] > aHeap[greatestIndex]))
		{
			greatestIndex = leftIndex;
		}
		if ((rightIndex <= heapSize) && (aHeap[rightIndex] > aHeap[greatestIndex]))
		{
			greatestIndex = rightIndex;
		}
		if (greatestIndex != hIndex)
		{
			aSwap(aHeap, hIndex, greatestIndex);
			heapify(aHeap, greatestIndex);
		}
	}
}