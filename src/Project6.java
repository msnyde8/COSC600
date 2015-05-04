

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
		aGraph.setupColors(colorSet);
		//String dfsOutput = aGraph.graphSearch(true);
		//aGraph.printResult(dfsOutput, "Depth First Search: ", false);
		String bfsOutput = aGraph.graphSearch(false);
		aGraph.printResult(bfsOutput, "Breadth First Search:", false);
	}
}

class graphNode
{
	private int nodeVal = -1;
	private LinkedList<Integer> adjList = new LinkedList<Integer>();
	private String nodeColor = "";
	private int nodeLevel = -1;
	
	graphNode(int newVal)
	{
		nodeVal = newVal;
	}
	public LinkedList<Integer> getAdjList() {
		return adjList;
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
	public void setNodeLevel(int newLevel)
	{
		nodeLevel = newLevel;
	}
	public int getNodeLevel()
	{
		return nodeLevel;
	}
}

class adjListGraph
{
	private String inputName = Project6.pathName + "project6_input.txt";
	private String outputName = Project6.pathName + "project6_output.txt";
	private graphNode[] nodeList;
	private boolean resetGraph = true;
	private boolean[] nodesVisited;
	private String[] graphColors;
	private Queue<Integer> graphQueue = new LinkedList<Integer>();
	private StateNames nodeStates = new StateNames();
	
	adjListGraph(int numNodes)
	{
		nodeList = new graphNode[numNodes]; // No sure what warning means ...
		for(int i = 0; i < numNodes; ++i)
		{
			nodeList[i] = new graphNode((i+1));
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
	        	//addEdge(stateNum, stateNum);
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
		if((stateNum < 1) || (stateNum > nodeList.length))
		{
			return false;
		}
		graphNode gNode = new graphNode(nodeVal);
		return nodeList[(stateNum-1)].getAdjList().add(gNode.getNodeVal());
	}
	
	void visitReset()
	{
		for(int i = 0; i < nodesVisited.length; ++i)
		{
			nodesVisited[i] = false;
		}
	}
	
	void graphNodeReset()
	{
		for(int i = 0; i < nodeList.length; ++i)
		{
			nodeList[i].setNodeColor("");
			nodeList[i].setNodeLevel(-1);
		}
	}	
	void setupColors(String [] colorArray)
	{
		graphColors = new String [colorArray.length];
		System.arraycopy(colorArray, 0, graphColors, 0, colorArray.length);
	}
	
	String graphSearch(boolean dfs)
	{
		String printStr = "";
		
		try
		{
			if(resetGraph == true)
			{
				visitReset();
				graphNodeReset();
				resetGraph = false;
			}
			if(null == nodeList)
			{
				System.out.println("Error: graph setup not complete");
				return "";
			}
			if(true == nodeList[0].getAdjList().isEmpty())
			{
				System.out.println("Error: begin state adj list setup not complete");
				return "";
			}
			if(true == dfs)
			{
				printStr = graphDFS(nodeList[0], printStr, 0);
			}
			else
			{
				printStr = graphBFS(nodeList[0], printStr, 0);
			}
			resetGraph = true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
		return printStr;
	}
	
	private String graphDFS(graphNode startNode, String outputStr, int graphLevel)
	{
		if(null == startNode)
		{
			return outputStr;
		}
		try
		{
			int startVal = startNode.getNodeVal();
			outputStr = (outputStr + startVal + " ");
//			System.out.println(outputStr);
			nodesVisited[startVal-1] = true;
			colorNode(nodeList[(startVal-1)]);
			nodeList[(startVal-1)].setNodeLevel(graphLevel);

			ListIterator<Integer> nodeIter = nodeList[(startVal-1)].getAdjList().listIterator();
			while(nodeIter.hasNext())
			{
				int nextVal = nodeIter.next();
				if(false == nodesVisited[nextVal-1])
				{
					outputStr = graphDFS(nodeList[(nextVal-1)], outputStr, (graphLevel + 1));
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
	
	private String graphBFS(graphNode startNode, String outputStr, int graphLevel)
	{
		if(null == startNode)
		{
			return outputStr;
		}
		
		int startVal = startNode.getNodeVal();
		graphQueue.add(startVal);
		outputStr = (outputStr + startVal + " ");
		nodesVisited[startVal-1] = true;
		colorNode(nodeList[(startVal-1)]);
		nodeList[(startVal-1)].setNodeLevel(graphLevel);
		graphLevel++;
		
		boolean increaseLevel = false;		
		try
		{
			while(false == graphQueue.isEmpty())
			{
				int nextVal = graphQueue.remove();
				
				ListIterator<Integer> nodeIter = nodeList[(nextVal-1)].getAdjList().listIterator(0);
				while(nodeIter.hasNext())
				{
					int adjNode = nodeIter.next();
					//int adjVal = adjNode.getNodeVal();
					if(false == nodesVisited[adjNode-1])
					{
						nodesVisited[adjNode-1] = true;
						outputStr = (outputStr + adjNode + " ");
						colorNode(nodeList[(adjNode-1)]);
						nodeList[(adjNode-1)].setNodeLevel(graphLevel);
						graphQueue.add(adjNode);
						increaseLevel = true;
					}
				}
				if(true == increaseLevel)
				{
					graphLevel++;
					increaseLevel = false;
				}
			}

/*			ListIterator<Integer> nodeIter = nodeList[(startVal-1)].getAdjList().listIterator(0);
			while(nodeIter.hasNext())
			{
				int nextNode = nodeIter.next();
				if(false == nodesVisited[nextNode-1])
				{
					outputStr = graphBFS(nodeList[(nextNode-1)], outputStr, (graphLevel+1));
				}
			}*/
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
				ListIterator<Integer> nodeIter = nodeList[(colorNode.getNodeVal()-1)].getAdjList().listIterator(0);
				while((nodeIter.hasNext()) && (false == foundColor))
				{
					int adjNodeVal = nodeIter.next();
					graphNode adjNode = nodeList[(adjNodeVal-1)];
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
	
	void printResult(String resultStr, String openStr, boolean appendFlag)
	{		
		try
		{
			PrintWriter pWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputName,appendFlag)));

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
					System.out.println(tmpStr + ": Color->" + nodeList[tmpInt-1].getNodeColor() + ", Level->" + nodeList[tmpInt-1].getNodeLevel());
					pWriter.println(tmpStr + ": Color->" + nodeList[tmpInt-1].getNodeColor() + ", Level->" + nodeList[tmpInt-1].getNodeLevel());
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
			BufferedReader buffRead = new BufferedReader(new FileReader(inputStateName));
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