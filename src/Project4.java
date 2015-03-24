

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Project4 {
	public static void main(String[] args) {
		Driver driveClass = new Driver();
		driveClass.runDriver();
	}
}

class Driver {
	private Scanner lineReader = new Scanner(System.in);
	private linkedNodes storedData = new linkedNodes();
	private int currentLine = -1;
	private	String textStr = "";
	private Boolean end = false;
	
	void runDriver()
	{
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
						if(strToken.hasMoreElements())
						{
							System.out.println("Not a valid command");
						}
						else
						{
							insertMode = true;
						}
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
						searchStatement(textStr);
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
	
	void insertStatement(String textStr)
	{
		try
		{
			if(true == textStr.startsWith("$"))
			{
				System.out.println("Not a valid command");
			}
			else if((textStr == "$insert") || (textStr == ""))
			{
				return;
			}
			else if(currentLine == -1)
			{
				storedData.add(storedData.size()+1, textStr);
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
	
	void printStatement(StringTokenizer strTokenized)
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
					indicateLine = "Current Line -> ";
				}
				else if((currentLine == -1) && (i == storedData.size()))
				{
					indicateLine = "Default Current Line -> ";
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
	
	void deleteStatement(StringTokenizer strTokenized)
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
			System.out.println("Delete successful");
		}
		catch(IllegalStateException|NoSuchElementException|NumberFormatException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void searchStatement(String txtStr)
	{
		try
		{
			StringTokenizer strTokenized = new StringTokenizer(txtStr, " ", true);
			
			if(3 > strTokenized.countTokens())
			{
				System.out.println("Incorrect number of search parameters");
				return;
			}
			
			// Move past $search element
			strTokenized.nextElement();
			
			String searchStr = strTokenized.nextElement().toString();
			while(strTokenized.hasMoreElements())
			{
				searchStr += strTokenized.nextElement().toString();
			}
			searchStr = searchStr.trim();
			//System.out.println(searchStr);
			
			linkNode tmpNode = storedData.getFirst();
			int countNode = 1;
			do
			{
				String tokenStr = tmpNode.dataStr;
				//System.out.println(tokenStr);
				if(tokenStr.contains(searchStr))
				{
					String cmdStr = (countNode + " " + countNode);
					StringTokenizer strTok = new StringTokenizer(cmdStr, " ,");
					
					printStatement(strTok);
					break;
				}
				tmpNode = tmpNode.next;
				countNode++;
			}while(tmpNode != null);
			if(tmpNode == null)
			{
				System.out.println("Search string not found in text");
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void lineStatement(StringTokenizer strTokenized)
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
	
	private linkNode head;
	private linkNode curNode;
	
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