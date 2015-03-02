package project_4;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project4 {
	
	static LinkedList<String> storedData = new LinkedList<String>();
	static Scanner lineReader;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				
				switch(textStr)
				{
					case "$insert":
						System.out.println("Insert statement found!");
						insertStatement();
						newCommand = false;
						break;
					case "$delete":
						System.out.println("Delete statement found!");
						break;
					case "$print":
						System.out.println("Print statement found!");
						printStatement();
						newCommand = true;
						break;
					case "$line":
						System.out.println("Line statement found!");
						break;
					case "$search":
						System.out.println("Search statement found!");
						break;
					case "$done":
						System.out.println("Thank you and have a nice day!");
						end = true;
						lineReader.close();
						newCommand = false;
						break;
					default:
						System.out.println("Unuseful!!");
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
			while((!lineReader.hasNext("\\$delete")) && 
					(!lineReader.hasNext("\\$print")) &&
					(!lineReader.hasNext("\\$line")) &&
					(!lineReader.hasNext("\\$search")) &&
					(!lineReader.hasNext("\\$done")))
			{
				storedData.add(lineReader.nextLine());
			}
		}
		catch(IllegalStateException e)
		{
			System.out.println("Caught IllegalStateException: " + e.getMessage());
			e.printStackTrace();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Caught NoSuchElementException: " + e.getMessage());
			e.printStackTrace();
		}
		catch(UnsupportedOperationException e)
		{
			System.out.println("Caught UnsupportedOperationException: " + e.getMessage());
			e.printStackTrace();
		}
		catch(ClassCastException e)
		{
			System.out.println("Caught ClassCastException: " + e.getMessage());
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Caught IllegalArgumentException: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	static void printStatement()
	{
		try
		{
			ListIterator<String> dataIterator = storedData.listIterator();
			while(dataIterator.hasNext())
			{
				System.out.println(dataIterator.next());
			}
		}
		catch(IllegalStateException e)
		{
			System.out.println("Caught IllegatlStateException: " + e.getMessage());
			e.printStackTrace();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Caught NoSuchElementException: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
