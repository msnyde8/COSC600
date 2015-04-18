package project_6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

public class Project6 {
	
	static String pathName = System.getProperty("user.dir") + System.getProperty("file.separator");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driveClass = new Driver();
		driveClass.runDriver();
	}
	
}

class Driver {
	private String inputStateName = Project6.pathName + "project6_statename_input.txt";
	private String inputName = Project6.pathName + "project6_input.txt";
	private String outputName = Project6.pathName + "project6_output.txt";
	private int numStates = 48;
	private boolean stateNamesSetup = false;
	private HashMap<Integer, String> stateNames;
	private adjListGraph aGraph;
	
	void stateNamesSetup()
	{
		try
		{
			stateNames = new HashMap<Integer, String>();
		    FileReader fileRead = new FileReader(inputStateName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();
            while(null != readBuff)
            {
                StringTokenizer strToken = new StringTokenizer(readBuff, ",");
                if(strToken.countTokens() > 1)
                {
                	Integer tmpNum = Integer.parseInt(strToken.nextToken());
                	String tmpStr = strToken.nextToken();
                	if(null != stateNames.put(tmpNum, tmpStr))
                	{
                		System.out.println("Unable to add state");
                	}
                }
                else
                {
                	System.out.println("Invalid format: not enough parameters");
                }
            	readBuff = buffRead.readLine();
            }
            buffRead.close();
//            System.out.println("State Names: "+ stateNames);
		}
		catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
	
	String getStateName(int stateNum)
	{
		if(false == stateNamesSetup)
		{
			stateNamesSetup();
			stateNamesSetup = true;
		}
		
		return stateNames.get(stateNum);
	}
	
	void printDFS(String dfsStr)
	{
		try
		{
			StringTokenizer strToken = new StringTokenizer(dfsStr);
			String tmpStr = "";
			int tmpInt = 0;
			while(strToken.hasMoreElements())
			{
				tmpInt = Integer.parseInt(strToken.nextElement().toString());
				tmpStr = getStateName(tmpInt);
				if(null != tmpStr)
				{
					System.out.println(tmpStr + "->");
				}
			}
		}
		catch(NullPointerException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	void graphSetup()
	{
		try
		{
			PrintWriter pWriter = new PrintWriter(outputName);
			aGraph = new adjListGraph(numStates);
		    FileReader fileRead = new FileReader(inputName);
	        BufferedReader buffRead = new BufferedReader(fileRead);
	        String readBuff = buffRead.readLine();
	
	        for(int stateNum = 1; (readBuff != null) && (stateNum <= numStates); ++stateNum)
	        {
	        	String strState = getStateName(stateNum);
	        	if(null != strState)
	        	{
//	        		System.out.println("State: " + strState);
	        	}
	        	pWriter.println("State: " + stateNum);
	        	// First in Linked List is State itself, to track if it has been visited
	        	aGraph.addEdge(stateNum, stateNum);
	        	// Then add dependencies
	        	StringTokenizer strToken = new StringTokenizer(readBuff," ,");
	        	while(strToken.hasMoreElements())
	        	{
	        		int adjVal = Integer.parseInt(strToken.nextElement().toString());
	        		if(true == aGraph.addEdge(stateNum,adjVal))
	        		{
	        			strState = getStateName(adjVal);
	        			if(null != strState)
	        			{
//	        				System.out.println("Dependency: " + strState);
	        			}
	            		pWriter.println("Dependency: " + adjVal);
	        		}
	        		else
	        		{
	        			System.out.println("error adding edge");
	        		}
	        	}
	        	readBuff = buffRead.readLine();
	        }
	        buffRead.close();
			pWriter.close();
		}
		catch(IOException|NullPointerException|NoSuchElementException|NumberFormatException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	void runDriver()
	{
		graphSetup();
		String strOutput = aGraph.depthFirstSearch();
		printDFS(strOutput);
	}
}

class graphNode
{
	private int nodeVal;
//	boolean nodeVisit;
	
	graphNode(int newVal)
	{
		nodeVal = newVal;
//		nodeVisit = false;
	}
	public int getNodeVal()
	{
		return nodeVal;
	}
}

class adjListGraph
{
	private LinkedList<graphNode>[] adjList;
	private boolean resetGraph = true;
	private boolean[] nodesVisited;
	adjListGraph(int numNodes)
	{
		adjList = new LinkedList[numNodes]; // No sure what warning means ...
		for(int i = 0; i < numNodes; ++i)
		{
			adjList[i] = new LinkedList<graphNode>();
		}
		nodesVisited = new boolean[numNodes];
		visitReset();
		resetGraph = false;
	}
	boolean addEdge(int stateNum, int nodeVal)
	{
		if((stateNum < 1) || (stateNum > adjList.length))
		{
			return false;
		}
		graphNode gNode = new graphNode(nodeVal);
		return adjList[(stateNum-1)].add(gNode);
	}
	void visitReset()
	{
		for(int i = 0; i < adjList.length; ++i)
		{
			nodesVisited[i] = false;
		}
	}
	String depthFirstSearch()
	{
		String printStr = "";
		try
		{
			if(resetGraph == true)
			{
				visitReset();
				resetGraph = false;
			}
			if(null == adjList)
			{
				System.out.println("Error: graph setup not complete");
				return "";
			}
			if(true == adjList[0].isEmpty())
			{
				System.out.println("Error: being state adj list setup not complete");
				return "";
			}
			printStr = graphDFS(adjList[0].get(0), printStr);
			resetGraph = true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
		return printStr;
	}
	String graphDFS(graphNode startNode, String outputStr)
	{
		if(null == startNode)
		{
			return outputStr;
		}
		try
		{
			outputStr = (outputStr + startNode.getNodeVal() + " ");
//			System.out.println(outputStr);
			nodesVisited[startNode.getNodeVal()-1] = true;

			ListIterator<graphNode> nodeIter = adjList[(startNode.getNodeVal()-1)].listIterator(1);
			while(nodeIter.hasNext())
			{
				graphNode nextNode = nodeIter.next();
				if(false == nodesVisited[nextNode.getNodeVal()-1])
				{
					outputStr = graphDFS(nextNode, outputStr);
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
		return outputStr;
	}
}
