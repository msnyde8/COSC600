


import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Project4 {
	
	static LinkedList<String> storedData = new LinkedList<String>();
	static Scanner lineReader = new Scanner(System.in);
	static int currentLine = 0;

	public static void main(String[] args) {
		String textStr = "";
		Boolean end = false;
		
		try
		{
			Boolean insertMode = false;
			
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
						insertMode = true;
						insertStatement("");
						break;
					case "$delete":
						System.out.println("Delete statement found!");
						if(storedData.size() > 0)
						{
							deleteStatement(strToken);
						}
						insertMode = false;
						break;
					case "$print":
						System.out.println("Print statement found!");
						if(storedData.size() > 0)
						{
							printStatement(strToken);
						}
						insertMode = false;
						break;
					case "$line":
						System.out.println("Line statement found!");
						lineStatement(strToken);
						insertMode = false;
						break;
					case "$search":
						System.out.println("Search statement found!");
						searchStatement(strToken);
						insertMode = false;
						break;
					case "$done":
						System.out.println("Thank you and have a nice day!");
						end = true;
						lineReader.close();
						break;
					default:
						if(true == insertMode)
						{
							insertStatement(textStr);
						}
						else
						{
							System.out.println("Not a valid command");
						}				
						break;
				}
				
				if(false == end)
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
	
	static void insertStatement(String textStr)
	{
		try
		{
			if((textStr == "") && (lineReader.hasNext()))
			{
				storedData.add(currentLine, lineReader.nextLine());
			}
			else
			{
				storedData.add(currentLine, textStr);
			}
			currentLine++;
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
				beginLine = storedData.indexOf(storedData.getFirst()) + 1;
				endLine = storedData.indexOf(storedData.getLast()) + 1;
			}
			else if(2 == strTokenized.countTokens())
			{
				beginLine = Integer.parseInt(strTokenized.nextElement().toString()) + 1;
				endLine = Integer.parseInt(strTokenized.nextElement().toString()) + 1;
			}
			else
			{
				System.out.println("Error not enough print parameters");
				return;
			}

			System.out.println("beginLine = " + beginLine + "; endLine = " + endLine + ";");
						
			if((beginLine > storedData.size()) || (endLine > storedData.size()) ||
					(endLine < beginLine) || (beginLine <= 0))
			{
				System.out.println("Print lines requested are out of bounds");
				return;
			}
			
			ListIterator<String> dataIterator = storedData.listIterator(beginLine-1);
			/*for(dataIterator = storedData.listIterator(beginLine);
					dataIterator.hasNext(), dataIterator != storedData.listIterator(endLine); )*/
			while(dataIterator.hasNext() && (dataIterator.nextIndex() != endLine))
			{
				String indicateLine = "";

				if(currentLine == (dataIterator.nextIndex()+1))
				{
					indicateLine = "CurrentLine: ";
				}
				System.out.println(indicateLine + dataIterator.next());
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
			
			System.out.println("beginLine = " + beginLine + "; endLine = " + endLine + ";");
			
			if((beginLine >= storedData.size()) || (endLine >= storedData.size()) ||
					(beginLine < storedData.indexOf(storedData.getFirst())) ||
					(endLine < storedData.indexOf(storedData.getFirst())) ||
					(endLine < beginLine))
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
	
	static void lineStatement(StringTokenizer strTokenized)
	{
		try
		{
			if(1 != strTokenized.countTokens())
			{
				System.out.println("Incorrect number of line parameters");
				return;
			}
			
			int newLine = Integer.parseInt(strTokenized.nextElement().toString());
						
			if((newLine > storedData.size()) || (newLine < storedData.indexOf(storedData.getFirst())))
			{
				System.out.println("Line requested is out of bounds");
				return;
			}
			
			currentLine = newLine;
			
			Integer beginLine = newLine - 3;
			if(beginLine < storedData.indexOf(storedData.getFirst()))
			{
				beginLine = storedData.indexOf(storedData.getFirst());
			}
			Integer endLine = newLine + 3;
			if(endLine > storedData.indexOf(storedData.getLast()))
			{
				endLine = storedData.indexOf(storedData.getLast());
			}
			String cmdStr = (beginLine.toString() + " " + endLine.toString());
			//System.out.println(cmdStr);
			StringTokenizer strTok = new StringTokenizer(cmdStr, " ");
			
			printStatement(strTok);
		}
		catch(IllegalStateException|NoSuchElementException|NumberFormatException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
