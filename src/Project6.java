

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;
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
	private int numStates = 48;
	private adjListGraph aGraph;
	
	void runDriver()
	{
		aGraph = new adjListGraph(numStates);
		aGraph.graphSetup();
		String [] colorSet = new String [] {"Red","Yellow","Green","Blue"};
		String dfsOutput = aGraph.depthFirstSearch(colorSet);
		aGraph.printResult(dfsOutput, "Depth First Search: ");
		String bfsOutput = aGraph.breadthFirstSearch();
		aGraph.printResult(bfsOutput, "Breadth First Search:" );
	}
}

class graphNode
{
	private int nodeVal;
	private String nodeColor = "";
	
	graphNode(int newVal)
	{
		nodeVal = newVal;
	}
	public int getNodeVal()
	{
		return nodeVal;
	}
	public void setNodeColor(String newColor)
	{
		nodeColor = newColor;
	}
	public String getNodeColor()
	{
		return nodeColor;
	}
}

class adjListGraph
{
	private String inputName = Project6.pathName + "project6_input.txt";
	private String outputName = Project6.pathName + "project6_output.txt";
	private LinkedList<graphNode>[] adjList;
	private boolean resetGraph = true;
	private boolean[] nodesVisited;
	private String[] graphColors;
	private Queue<graphNode> graphQueue = new LinkedList<graphNode>();
	private StateNames nodeStates = new StateNames();
	
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
	
	void graphSetup()
	{
		try
		{
	        BufferedReader buffRead = new BufferedReader(new FileReader(inputName));
	        String readBuff = buffRead.readLine();
	
	        for(int stateNum = 1; (readBuff != null) && (stateNum <= nodeStates.getNumberStates()); ++stateNum)
	        {
	        	String strState = nodeStates.getStateName(stateNum);
	        	if(null != strState)
	        	{
//	        		System.out.println("State: " + strState);
	        	}
//	        	pWriter.println("State: " + stateNum);
	        	// First in Linked List is State itself, to track if it has been visited
	        	addEdge(stateNum, stateNum);
	        	// Then add dependencies
	        	StringTokenizer strToken = new StringTokenizer(readBuff," ,");
	        	while(strToken.hasMoreElements())
	        	{
	        		int adjVal = Integer.parseInt(strToken.nextElement().toString());
	        		if(true == addEdge(stateNum,adjVal))
	        		{
	        			strState = nodeStates.getStateName(adjVal);
	        			if(null != strState)
	        			{
//	        				System.out.println("Dependency: " + strState);
	        			}
//	            		pWriter.println("Dependency: " + adjVal);
	        		}
	        		else
	        		{
	        			System.out.println("error adding edge");
	        		}
	        	}
	        	readBuff = buffRead.readLine();
	        }
	        buffRead.close();
//			pWriter.close();
		}
		catch(IOException|NullPointerException|NoSuchElementException|NumberFormatException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
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
	
	String depthFirstSearch(String [] colorArray)
	{
		String printStr = "";
		
		graphColors = new String [colorArray.length];
		System.arraycopy(colorArray, 0, graphColors, 0, colorArray.length);
		
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
				System.out.println("Error: begin state adj list setup not complete");
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
			colorNode(adjList[(startNode.getNodeVal()-1)].get(0));

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
	
	String breadthFirstSearch()
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
				System.out.println("Error: begin state adj list setup not complete");
				return "";
			}
			printStr = graphBFS(adjList[0].get(0), printStr);
			resetGraph = true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
		return printStr;
	}
	String graphBFS(graphNode startNode, String outputStr)
	{
		if(null == startNode)
		{
			return outputStr;
		}
		
		graphQueue.add(startNode);
		outputStr = (outputStr + startNode.getNodeVal() + " ");
		nodesVisited[startNode.getNodeVal()-1] = true;
		colorNode(adjList[(startNode.getNodeVal()-1)].get(0));
		
		try
		{
			while(false == graphQueue.isEmpty())
			{
				graphNode nextNode = graphQueue.remove();
				
				ListIterator<graphNode> nodeIter = adjList[(nextNode.getNodeVal()-1)].listIterator(1);
				while(nodeIter.hasNext())
				{
					graphNode adjNode = nodeIter.next();
					if(false == nodesVisited[adjNode.getNodeVal()-1])
					{
						nodesVisited[adjNode.getNodeVal()-1] = true;
						outputStr = (outputStr + adjNode.getNodeVal() + " ");
						graphQueue.add(adjNode);
					}
				}
			}

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
	
	void colorNode(graphNode colorNode)
	{
		if(null == colorNode)
		{
			return;
		}
		if(0 < colorNode.getNodeColor().length())
		{
			return;
		}
		
		try {
			boolean foundColor = false;
			for(int i = 0; i < graphColors.length; ++i)
			{
				ListIterator<graphNode> nodeIter = adjList[(colorNode.getNodeVal()-1)].listIterator(1);
				while((nodeIter.hasNext()) && (false == foundColor))
				{
					int adjNodeVal = nodeIter.next().getNodeVal();
					graphNode adjNode = adjList[(adjNodeVal-1)].get(0);
					if(0 == graphColors[i].compareToIgnoreCase(adjNode.getNodeColor()))
					{
						foundColor = true;
					}
				}
				if(false == foundColor)
				{
					colorNode.setNodeColor(graphColors[i]);
					break;
				}
				foundColor = false;
			}
		}
		catch(NullPointerException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	void printResult(String resultStr, String openStr)
	{		
		try
		{
			PrintWriter pWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputName,true)));

			System.out.println(openStr);
			pWriter.println(openStr);
			
			StringTokenizer strToken = new StringTokenizer(resultStr);
			String tmpStr = "";
			int tmpInt = 0;
			while(strToken.hasMoreElements())
			{
				tmpInt = Integer.parseInt(strToken.nextElement().toString());
				tmpStr = nodeStates.getStateName(tmpInt);
				if(null != tmpStr)
				{
					System.out.println(tmpStr + "->" + adjList[tmpInt-1].get(0).getNodeColor());
					pWriter.println(tmpStr + "->" + adjList[tmpInt-1].get(0).getNodeColor());
//					System.out.println(tmpInt + ". " + tmpStr);
//					pWriter.println(tmpInt + ". " + tmpStr);
				}
			}
			
			System.out.println();
			pWriter.println();
			pWriter.close();
		}
		catch(NullPointerException|IOException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
}

class StateNames
{
	private String inputStateName = Project6.pathName + "project6_statename_input.txt";
	private boolean stateNamesSetup = false;
	private HashMap<Integer, String> stateNames = new HashMap<Integer, String>();
	
	public StateNames()
	{
		if(false == stateNamesSetup)
		{
			stateNamesSetup();
		}
	}
	
	void stateNamesSetup()
	{
		try
		{
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
//	        System.out.println("State Names: "+ stateNames);
		}
		catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
		stateNamesSetup = true;
	}

	String getStateName(int stateNum)
	{
		if(false == stateNamesSetup)
		{
			stateNamesSetup();
		}
	
		return stateNames.get(stateNum);
	}
	
	int getNumberStates()
	{
		if(false == stateNamesSetup)
		{
			stateNamesSetup();
		}
		return stateNames.size();
	}
}