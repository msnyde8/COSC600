package project_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Project6 {
	
	static String pathName = System.getProperty("user.dir") + System.getProperty("file.separator");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driveClass = new Driver();
		driveClass.runDriver();
		driveClass.setupStateNames();
	}
	
}

class Driver {
	private String inputStateName = Project6.pathName + "project6_statename_input.txt";
	private String inputName = Project6.pathName + "project6_input.txt";
	private String outputName = Project6.pathName + "project6_output.txt";
	private int numStates = 48;
	private HashMap<Integer, String> stateNames = new HashMap<Integer, String>();
	
	void setupStateNames()
	{
		try
		{
		    FileReader fileRead = new FileReader(inputStateName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();
            while(null != readBuff)
            {
                StringTokenizer strToken = new StringTokenizer(readBuff, " ,");
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
            System.out.println("State Names: "+ stateNames);
		}
		catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
		
	void runDriver()
	{
		try
		{
			PrintWriter pWriter = new PrintWriter(outputName);
			adjListGraph aGraph = new adjListGraph(numStates);
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();

            for(int stateNum = 1; (readBuff != null) && (stateNum <= numStates); ++stateNum)
            {
            	StringTokenizer strToken = new StringTokenizer(readBuff," ,");
            	System.out.println("State: " + stateNum);
            	pWriter.println("State: " + stateNum);
            	while(strToken.hasMoreElements())
            	{
            		int adjVal = Integer.parseInt(strToken.nextElement().toString());
            		if(true == aGraph.addEdge(stateNum,adjVal))
            		{
            			System.out.println("Dependency: " + adjVal);
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
		catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
}

class graphNode
{
	private int nodeVal;
	graphNode(int newVal)
	{
		nodeVal = newVal;
	}
	public int getNodeVal()
	{
		return nodeVal;
	}
}

class adjListGraph
{
	private LinkedList<graphNode>[] adjList;
	adjListGraph(int numNodes)
	{
		adjList = new LinkedList[numNodes];
		for(int i = 0; i < numNodes; ++i)
		{
			adjList[i] = new LinkedList<graphNode>();
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
}
