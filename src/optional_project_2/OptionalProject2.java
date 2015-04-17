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
	static String pathName = System.getProperty("user.dir") + System.getProperty("file.separator");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{

			String genName = pathName + "optionalproject2_input.txt";
			int numElements = 20000;

			PrintWriter writer = new PrintWriter(genName);
			Random randomGenerator = new Random();
			for (int i = 0; i < numElements; ++i)
			{
				int randomInt = randomGenerator.nextInt();
//	        	System.out.println(randomInt);
	        	writer.println(randomInt);
	        	writer.flush();
	    	}
			writer.close();

			SortClass sortObj = new SortClass(genName, numElements);
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
	private int heapSize = -1;
	private long startTime = 0;
	private long endTime = 0;
	private String classType = "";

	SortClass(String inName, int numElem)
	{
		numElements = numElem;
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
			InsertionSort(insertArray);
	
			// Quick Sort
			int quickArray[] = new int [numElements];
			System.arraycopy(numArray, 0, quickArray, 0, numArray.length);
			QuickSort(quickArray);
			
			//Merge Sort
			int mergeArray[] = new int [numElements];
			System.arraycopy(numArray, 0, mergeArray, 0, numArray.length);
			MergeSort(mergeArray);
			
			//Heap Sort
			int heapArray[] = new int [numElements];
			System.arraycopy(numArray, 0, heapArray, 0, numArray.length);
			HeapSort(heapArray);

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
//		printArray(qArray);
//		System.out.println("Array size: " + qArray.length);
		startTime = System.currentTimeMillis();
		qSort(qArray, 0, (qArray.length - 1));
		endTime = System.currentTimeMillis();
//		printArray(qArray);
//		System.out.println(startTime);
//		System.out.println(endTime);
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
		
		int pivotInt = qSortArray[(startIndex + ((endIndex-startIndex)/2))];

//		System.out.println("pivotInt: " + pivotInt);
		try
		{
			while(newStart <= newEnd)
			{

				while(qSortArray[newStart] < pivotInt)
				{
					newStart++;
				}
				while(qSortArray[newEnd] > pivotInt)
				{
					newEnd--;
				}
				if(newStart <= newEnd)
				{
					aSwap(qSortArray, newStart, newEnd);
					newStart++;
					newEnd--;
				}
			}
			
			if(startIndex < newEnd)
			{
				qSort(qSortArray, startIndex, newEnd);
			}
			if(newStart < endIndex)
			{
				qSort(qSortArray, newStart, endIndex);
			}
		}
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
	
	void MergeSort(int mArray[])
	{
		classType = "Merge Sort";
//		printArray(mArray);
		startTime = System.currentTimeMillis();
		int mTmpArray[] = new int[mArray.length];
		mSort(mArray, mTmpArray, 0, (mArray.length-1));
		endTime = System.currentTimeMillis();
//		printArray(mArray);
		printTime();
	}
	void mSort(int mSortArray[], int mTmpSort[], int startIndex, int endIndex)
	{
		if(startIndex >= endIndex)
		{
			return;
		}
		
		int leftEnd = ((startIndex + endIndex) / 2);
				
		mSort(mSortArray, mTmpSort, startIndex, leftEnd);
		mSort(mSortArray, mTmpSort, (leftEnd+1), endIndex);
			
		mMerge(mSortArray, mTmpSort, startIndex, leftEnd, endIndex);
	}
	void mMerge(int mMergeArray[], int mTmpMerge[], int mLeft, int mMiddle, int mRight)
	{
		int rightStart = (mMiddle + 1);
		int tmpIndex = mLeft;
		try
		{
			while((mLeft <= mMiddle) && (rightStart <= mRight))
			{
				if(mMergeArray[mLeft] <= mMergeArray[rightStart])
				{
					mTmpMerge[tmpIndex++] = mMergeArray[mLeft++];
				}
				else
				{
					mTmpMerge[tmpIndex++] = mMergeArray[rightStart++];
				}
			}
		
			while(mLeft <= mMiddle)
			{
				mTmpMerge[tmpIndex++] = mMergeArray[mLeft++];
			}
			
			while(rightStart <= mRight)
			{
				mTmpMerge[tmpIndex++] = mMergeArray[rightStart++];
			}
			
			for(int i = 0; i < (mRight - mLeft + 1); --mRight)
			{
				mMergeArray[mRight] = mTmpMerge[mRight];
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
		
		if ((leftIndex <= heapSize) && (aHeap[leftIndex] > aHeap[hIndex]))
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