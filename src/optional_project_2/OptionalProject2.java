package optional_project_2;

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
			SortClass sortObj = new SortClass(outputName);
			sortObj.sort();
			writer.close();
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
	static String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "optionalproject2_input.txt");
	private static int heapSize;
	long startTime;
	long endTime;
	String classType = "";

	SortClass(String inName)
	{
		if(false == inName.isEmpty())
		{
			inputName = inName;
		}
	}
	
	void sort()
	{
		int numArray[] = new int [20000];

		try
		{
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();

            for(int i = 0; readBuff != null; ++i)
            {
    		    numArray[i] = Integer.parseInt(readBuff);
//    		    System.out.println("Number " + (i+1) + ": " + numArray[i]);
    		    //heapSize = i+1;
    		    readBuff = buffRead.readLine();
            }
            buffRead.close();
		}
	    catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
		
		// Insertion Sort
		int insertArray[] = new int [20000];
		System.arraycopy(numArray, 0, insertArray, 0, numArray.length);
		InsertionSort(insertArray);
		
		// Quick Sort
		int quickArray[] = new int [20000];
		System.arraycopy(numArray, 0, quickArray, 0, numArray.length);
		QuickSort(quickArray);
		
		//Merge Sort
		int mergeArray[] = new int [20000];
		System.arraycopy(numArray, 0, mergeArray, 0, numArray.length);
		MergeSort(mergeArray);
		
		//Heap Sort
		int heapArray[] = new int [20000];
		System.arraycopy(numArray, 0, heapArray, 0, numArray.length);
		HeapSort(heapArray);
	}
	
	void printTime()
	{
		System.out.println(classType + " complete in "+ (endTime - startTime) + " milliseconds");
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
//		printArray(qArray);
//		System.out.println("Array size: " + qArray.length);
		startTime = System.currentTimeMillis();
		qSort(qArray, 0, (qArray.length - 1));
		endTime = System.currentTimeMillis();
		printTime();
	}
	void qSort(int qSortArray[], int startIndex, int endIndex)
	{
		int origStart = startIndex;
		int origEnd = endIndex;
		
		if((origEnd - origStart) < 1)
		{
			return;
		}
		else
		{
			int pivotInt = qSortArray[origStart];
			
			while(endIndex > startIndex)
			{
				while((qSortArray[startIndex] <= pivotInt) && (startIndex <= origEnd) && (endIndex > startIndex))
				{
					++startIndex;
				}
				while((qSortArray[endIndex] > pivotInt) && (endIndex >= origStart ) && (endIndex >= startIndex))
				{
					--endIndex;
				}
				if(endIndex > startIndex)
				{
					aSwap(qSortArray, startIndex, endIndex);
				}
			}
			
			aSwap(qSortArray, origStart, endIndex);
			
			qSort(qSortArray, origStart, (endIndex-1));
			qSort(qSortArray, (endIndex+1), origEnd);
		}
	}
	void MergeSort(int mArray[])
	{
		classType = "Merge Sort";
//		printArray(mArray);
		startTime = System.currentTimeMillis();
		mArray = mSort(mArray);
		endTime = System.currentTimeMillis();
//		printArray(mArray);
		printTime();
	}
	int[] mSort(int mSortArray[])
	{
		try
		{
			if(mSortArray.length > 1)
			{
				int leftSize = (mSortArray.length / 2);
				int rightSize = (mSortArray.length - leftSize);
				int leftArray[] = new int[leftSize];
				int rightArray[] = new int[rightSize];
				
				System.arraycopy(mSortArray, 0, leftArray, 0, leftSize);
				System.arraycopy(mSortArray, leftSize, rightArray, 0, rightSize);

				leftArray = mSort(leftArray);
				rightArray = mSort(rightArray);
				
				int i = 0, j = 0, k = 0;
				while(leftArray.length != j && rightArray.length != k)
				{
					if(leftArray[j] < rightArray[k])
					{
						mSortArray[i] = leftArray[j];
						i++;
						j++;
					}
					else
					{
						mSortArray[i] = rightArray[k];
						i++;
						k++;
					}
				}
				
				while(leftArray.length != j)
				{
					mSortArray[i] = leftArray[j];
					i++;
					j++;
				}
				while(rightArray.length != k)
				{
					mSortArray[i] = rightArray[k];
					i++;
					k++;
				}
			}
		}
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
		return mSortArray;
	}
	
	void HeapSort(int hArray[])
	{
		classType = "Heap Sort";
		startTime = System.currentTimeMillis();
		hSort(hArray);
		endTime = System.currentTimeMillis();
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
			aSwap(hSortArray, 0, 1);
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
		if ((rightIndex <= heapSize) && (aHeap[rightIndex-1] > aHeap[greatestIndex]))
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