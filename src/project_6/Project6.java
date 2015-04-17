package project_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Project6 {
	
	static String pathName = System.getProperty("user.dir") + System.getProperty("file.separator");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driveClass = new Driver();
//		driveClass.runDriver();
		driveClass.setupStateNames();
	}
	
}

class Driver {
	private String inputStateName = Project6.pathName + "project6_statename_input.txt";
	private String inputName = Project6.pathName + "project6_input.txt";
	private String outputName = Project6.pathName + "project6_output.txt";
	private int numStates = 0;
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
                	if(null == stateNames.put(tmpNum, tmpStr))
                	{
                		++numStates;
                	}
                	else
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
            System.out.println("Number of states: " + numStates);
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
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();

            for(numStates = 0; (readBuff != null); ++numStates)
            {
    		    int tmpNum = Integer.parseInt(readBuff);
    		    System.out.println("State" + tmpNum + " dependencies: ");
    		    readBuff = buffRead.readLine();
            }
            buffRead.close();
            System.out.println("Number of states: " + numStates);
		}
		catch(IOException|NumberFormatException|NullPointerException|NoSuchElementException e)
	    {
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
}
