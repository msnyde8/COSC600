

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * The main class for Project1
 * @author msnyde8
 */
public class Project1{

	public static void main(String[] args) {
		// Project1 fields
		String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project1_input.txt");
		String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project1_output.txt");
//		System.out.println(fileName);
        
        Driver driveClass = new Driver();
        driveClass.ReadFile(inputName);
        driveClass.PrintData(outputName);
	}
}

/**
 * The Driver class reads the file and prints out results of assigning scores
 * @author msnyde8
 */
class Driver {
	// Driver fields
    private Double scoreAvg;
    private Vector<AssignScore> scores;
        
    // Driver constructor
    Driver()
    {
    	scoreAvg = 0.0;
    	scores = new Vector<AssignScore>();
    }
    
    // Driver methods
    /**
     * Read the class scores from the input file
     * @param inputName file path of class scores
     */
    void ReadFile(String inputName)
    {
	    try
		{
		    FileReader fileRead = new FileReader(inputName);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String readBuff = buffRead.readLine();
            
            while(readBuff != null)
            {
    		    StringTokenizer strTokened = new StringTokenizer(readBuff, " ");
    		
    	        while(strTokened.hasMoreElements())
    		    {
    		        AssignScore assignScore = new AssignScore(Integer.parseInt(strTokened.nextElement().toString()));
    			    scores.addElement(assignScore);
    			    scoreAvg = ( ( (scoreAvg * (scores.size() - 1) ) + scores.lastElement().getScore()) / scores.size());
//    			    System.out.println(scores.lastElement() + " " + scoreAvg);
    		    }
            	
                readBuff = buffRead.readLine();
            }
        }
		catch(IOException e)
		{
            System.out.println("Caught IOException: " + e.getMessage());
        	e.printStackTrace();
		}
    }
    
    /**
     * Print the grades for the class scores to output file
     * @param outputName file path to print the class scores and grades
     */
    void PrintData(String outputName)
    {
        try{
            PrintWriter writer = new PrintWriter(outputName);
        	
//	    	System.out.println("Score" + "    " + "Grade");
		    writer.write("Score" + "    " + "Grade" + System.getProperty("line.separator"));

		    Iterator<AssignScore> iter = scores.iterator();
		    
		    do
		    {
		    	AssignScore scoreGrade = iter.next();
		    	scoreGrade.calcGrade(scoreAvg);
//		    	System.out.println("  " + scoreGrade.score + "       " + scoreGrade.grade);
			    writer.write("  " + scoreGrade.getScore() + "       " + scoreGrade.getGrade() + System.getProperty("line.separator"));
            
		    }while(iter.hasNext());
		    
		    writer.close();
        }
        catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when writing to file " + outputName + ": " + e.getMessage());
        }
	}
}

/**
 * The AssignScore class will determine if grade is Outstanding/Unsatisfactory/Satisfactory (O/U/S)
 * @author snydemj1
 */
class AssignScore {
	
	/**
	 * Score to assign grade
	 */
	private Integer score;
	/**
	 * Grade value of score
	 */
    private String grade;
    
    /**
     * AssignScore constructor
     * @param newScore
     */
    AssignScore(Integer newScore)
    {
    	score = newScore;
    	grade = "";
    }
    
    /**
     * Calculate the grade (O/U/S) based on the average
     * @param average class grade average
     */
    void calcGrade(Double average)
    {
    	if(score > (1.10 * average))
    	{
		    grade = "O";
    	}
	    else if(score < (.90 * average))
	    {
		    grade = "U";
	    }
	    else
	    {
	    	grade = "S";
	    }
    }
    
    String getGrade()
    {
    	return grade;
    }
    
    Integer getScore()
    {
    	return score;
    }
}

