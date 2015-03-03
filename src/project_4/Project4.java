package project_4;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Project4 {
	
	static LinkedList<String> storedData = new LinkedList<String>();
	static Scanner lineReader;

	public static void main(String[] args) {
		String textStr = "";
		lineReader = new Scanner(System.in);
		Boolean end = false;
		try
		{
			Boolean newCommand = true;
			System.out.println("Please enter a command or a line of text");
			
			do
			{
				textStr = lineReader.nextLine();
				StringTokenizer strToken = new StringTokenizer(textStr, " ");
				String switchStr = "";
				if(strToken.hasMoreElements())
				{
					switchStr = strToken.nextElement().toString();
				}
				
				switch(switchStr)
				{
					case "$insert":
						System.out.println("Insert statement found!");
						insertStatement();
						newCommand = false;
						break;
					case "$delete":
						System.out.println("Delete statement found!");
						if(storedData.size() > 0)
						{
							deleteStatement(strToken);
						}
						newCommand = true;
						break;
					case "$print":
						System.out.println("Print statement found!");
						if(storedData.size() > 0)
						{
							printStatement(strToken);
						}
						newCommand = true;
						break;
					case "$line":
						System.out.println("Line statement found!");
						break;
					case "$search":
						System.out.println("Search statement found!");
						searchStatement(strToken);
						newCommand = true;
						break;
					case "$done":
						System.out.println("Thank you and have a nice day!");
						end = true;
						lineReader.close();
						newCommand = false;
						break;
					default:
						System.out.println("Not a valid command");
						newCommand = true;
						break;
				}
				
				if(true == newCommand)
				{
					System.out.println("Please enter a command or a line of text");
				}
			}while((false == end) && lineReader.hasNextLine());
		}
		catch(IllegalStateException e)
		{
			System.out.println("Caught IllegalStateException: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void insertStatement()
	{
		try
		{
			/*while((!lineReader.hasNext("\\$delete")) && 
					(!lineReader.hasNext("\\$print")) &&
					(!lineReader.hasNext("\\$line")) &&
					(!lineReader.hasNext("\\$search")) &&
					(!lineReader.hasNext("\\$done")))*/
			while(!lineReader.hasNext("\\$"))
			{
				storedData.add(lineReader.nextLine());
				System.out.println("Please enter a command or a line of text");
			}
		}
		catch(IllegalStateException|NoSuchElementException|UnsupportedOperationException|
				ClassCastException|IllegalArgumentException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void printStatement(StringTokenizer strTokenized)
	{
		try
		{
			int beginLine = -1;
			int endLine = -1;
			
			if(0 == strTokenized.countTokens())
			{
				beginLine = storedData.indexOf(storedData.getFirst());
				endLine = storedData.indexOf(storedData.getLast());
			}
			else if(2 == strTokenized.countTokens())
			{
				beginLine = Integer.parseInt(strTokenized.nextElement().toString());
				endLine = Integer.parseInt(strTokenized.nextElement().toString());
			}
			else
			{
				System.out.println("Error not enough print parameters");
				return;
			}
			
			if((beginLine >= storedData.size()) || (endLine >= storedData.size()) || (endLine > beginLine))
			{
				System.out.println("Print lines requested are out of bounds");
				return;
			}
			
//			System.out.println("beginLine = " + beginLine + "; endLine = " + endLine + ";");
						
			ListIterator<String> dataIterator = storedData.listIterator(beginLine);
			/*for(dataIterator = storedData.listIterator(beginLine);
					dataIterator.hasNext(), dataIterator != storedData.listIterator(endLine); )*/
			while(dataIterator.hasNext() && (dataIterator.nextIndex() != endLine+1))
			{
				System.out.println(dataIterator.next());
			}
		}
		catch(IllegalStateException|NoSuchElementException|NumberFormatException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void deleteStatement(StringTokenizer strTokenized)
	{
		try
		{
			if(2 != strTokenized.countTokens())
			{
				System.out.println("Incorrect number of delete parameters");
				return;
			}
			
			int beginLine = Integer.parseInt(strTokenized.nextElement().toString());
			int endLine = Integer.parseInt(strTokenized.nextElement().toString());
			
			if((beginLine >= storedData.size()) || (endLine >= storedData.size()) || (endLine > beginLine))
			{
				System.out.println("Delete lines requested are out of bounds");
				return;
			}
			
			ListIterator<String> dataIterator = storedData.listIterator(beginLine);

			while(dataIterator.hasNext() && (dataIterator.nextIndex() != endLine+1))
			{
				dataIterator.next();
				dataIterator.remove();
				endLine--;
			}
		}
		catch(IllegalStateException|NoSuchElementException|NumberFormatException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void searchStatement(StringTokenizer strTokenized)
	{
		try
		{
			if(1 != strTokenized.countTokens())
			{
				System.out.println("Incorrect number of search parameters");
				return;
			}
			
			String searchStr = strTokenized.nextElement().toString();
			
			ListIterator<String> dataIterator = storedData.listIterator();
			while(dataIterator.hasNext())
			{
				String tokenStr = dataIterator.next();
				if(tokenStr.contains(searchStr))
				{
					System.out.println(tokenStr);
					break;
				}
			}
			
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
