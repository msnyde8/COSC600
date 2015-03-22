package project_4;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Project4 {
	
	static linkedNodes storedData = new linkedNodes();
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
				StringTokenizer strToken = new StringTokenizer(textStr, " ,");
				String switchStr = "";
				if(strToken.hasMoreElements())
				{
					switchStr = strToken.nextElement().toString();
				}
				
				switch(switchStr)
				{
					case "$insert":
						//System.out.println("Insert statement found!");
						insertMode = true;
						break;
					case "$delete":
						//System.out.println("Delete statement found!");
						if(storedData.size() > 0)
						{
							deleteStatement(strToken);
						}
						insertMode = false;
						break;
					case "$print":
						//System.out.println("Print statement found!");
						if(storedData.size() > 0)
						{
							printStatement(strToken);
						}
						insertMode = false;
						break;
					case "$line":
						//System.out.println("Line statement found!");
						lineStatement(strToken);
						insertMode = false;
						break;
					case "$search":
						//System.out.println("Search statement found!");
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
			if((textStr == "$insert") || (textStr == ""))
			{
				return;
			}
			else if(currentLine == storedData.size())
			{
				currentLine++;
				storedData.add(currentLine, textStr);
			}
			else
			{
				storedData.add(currentLine, textStr);
				currentLine++;
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
				beginLine = 1;
				endLine = storedData.size();
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

			//System.out.println("beginLine = " + beginLine + "; endLine = " + endLine + "; size = " + storedData.size());
						
			if((beginLine > storedData.size()) || (endLine > storedData.size()) ||
					(endLine < beginLine) || (beginLine < 1))
			{
				System.out.println("Print lines requested are out of bounds");
				return;
			}
			
			for(int i = beginLine; i < endLine+1; ++i)
			{
				linkNode tmpNode = storedData.getAtIndex(i);
				String indicateLine = "";
						
				if(currentLine == i)
				{
					indicateLine = "Current ";
				}
				indicateLine = indicateLine + "Line " + i + ": ";
				
				System.out.println(indicateLine + tmpNode.dataStr);
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
			
			//System.out.println("beginLine = " + beginLine + "; endLine = " + endLine + ";");
			
			if((beginLine > storedData.size()) || (endLine > storedData.size()) ||
					(beginLine < 1) || (endLine < 1) ||	(endLine < beginLine))
			{
				System.out.println("Delete lines requested are out of bounds");
				return;
			}
			
			for(int i = beginLine; i <= endLine; --endLine)
			{
				storedData.removeAtIndex(i);
				if(currentLine >= beginLine)
				{
					currentLine--;
				}
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
			
			linkNode tmpNode = storedData.getFirst();
			int countNode = 1;
			do
			{
				String tokenStr = tmpNode.dataStr;
				if(tokenStr.contains(searchStr))
				{
					String cmdStr = (countNode + " " + countNode);
					StringTokenizer strTok = new StringTokenizer(cmdStr, " ,");
					
					printStatement(strTok);
					break;
				}
				tmpNode = tmpNode.next;
			}while(tmpNode != null);
			
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

			if((newLine > storedData.size()) || (newLine < 1))
			{
				System.out.println("Line requested is out of bounds");
				return;
			}
			
			currentLine = newLine;
			
			Integer beginLine = newLine - 3;
			if(beginLine < 1)
			{
				beginLine = 1;
			}
			Integer endLine = newLine + 3;
			if(endLine > storedData.size())
			{
				endLine = storedData.size();
			}
			String cmdStr = (beginLine.toString() + " " + endLine.toString());
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

class linkedNodes {
	
	private static linkNode head;
	private static linkNode curNode;
	
	// default constructor
	linkedNodes()
	{
		// set up head
		head = new linkNode();
		curNode = head;
	}
	
	void add(int nodeIndex, String nodeStr)
	{
		linkNode newNode = new linkNode(nodeStr, null);
		
		// if index is before first node, add to beginning
		if(1 > nodeIndex)
		{
			head.next = newNode;
			newNode.next = null;
			curNode = newNode;
		}
		// if index is past the end of the list, add to end
		else if(size() < nodeIndex)
		{
			curNode.next = newNode;
			newNode.next = null;
			curNode = newNode;
		}
		else
		{
			linkNode tmpNode = head;
			for(int i = 1; i < nodeIndex; ++i)
			{
				tmpNode = tmpNode.next;
			}
			newNode.next = tmpNode.next;
			tmpNode.next = newNode;
		}
	}
		
	int size()
	{
		int size = 0;
		linkNode tmpNode = head;
		while(tmpNode.next != null)
		{
			size++;
			tmpNode = tmpNode.next;
		}
		return size;
	}
	
	linkNode getFirst()
	{
		return head.next;
	}
	
	linkNode getLast()
	{
		return curNode;
	}
	
	linkNode getAtIndex(int nodeIndex)
	{		
		if(nodeIndex > size())
			return curNode;
		else if(nodeIndex <= 1)
			return getFirst();
		else
		{
			linkNode tmpNode = getFirst();
			for(int i = 1; i < nodeIndex; ++i)
			{
				tmpNode = tmpNode.next;
			}
			return tmpNode;
		}
	}
	
	void removeAtIndex(int nodeIndex)
	{
		if(nodeIndex > size())
			return;
		else if(nodeIndex < 1)
			return;
		else
		{
			linkNode tmpNode = head;
			for(int i = 1; i < nodeIndex; ++i)
			{
				tmpNode = tmpNode.next;
			}
			linkNode nextNode = tmpNode.next;
			if(nextNode != null)
			{
				tmpNode.next = nextNode.next;
			}
			else
			{
				tmpNode.next = null;
			}
		}
	}
}

class linkNode {
	String dataStr;
	linkNode next;
	
	linkNode()
	{
		dataStr = "";
		next = null;
	}
	
	linkNode(String dStr, linkNode nNode)
	{
		dataStr = dStr;
		next = nNode;
	}
	
	
}