package project_1;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * The main class for Project1
 * @author msnyde8
 */
public class Project1{

	public static void main(String[] args) {
		/**
		 * Input file path
		 */
		String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project1_input.txt");
		/**
		 * Output file path
		 */
		String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project1_output.txt");
//		System.out.println(fileName);
        
		/**
		 * Driver object
		 */
        Driver driveClass = new Driver();
        driveClass.ReadFile(inputName);
        driveClass.PrintData(outputName);
	}
}

/**
 * The Driver class reads the input file and prints the results of assigning scores to the output file
 * @author msnyde8
 */
class Driver {

	/**
	 * Average of scores
	 */
    private Double scoreAvg;
	/**
	 * Vector of scores from input file
	 */
    private Vector<AssignScore> scores;
        
    /**
     * Driver constructor
     */
    Driver()
    {
    	scoreAvg = 0.0;
    	scores = new Vector<AssignScore>();
    }
    
    /**
     * Read the class scores from the input file
     * @param inputName file path of class scores input file
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
    		    StringTokenizer strTokened = new StringTokenizer(readBuff, " ,\t");
    		
    	        while(strTokened.hasMoreElements())
    		    {
    	        	// Add new AssignScore object for each score read from the input file
    		        AssignScore assignScore = new AssignScore(Integer.parseInt(strTokened.nextElement().toString()));
    			    scores.addElement(assignScore);
    			    // Recompute average score as each AssignScore object is added
    			    scoreAvg = ( ( (scoreAvg * (scores.size() - 1) ) + scores.lastElement().getScore()) / scores.size());
//    			    System.out.println(scores.lastElement().getScore() + " " + scoreAvg);
    		    }
            	
                readBuff = buffRead.readLine();
            }
            buffRead.close();
        }
        catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when reading file " + inputName + ": " + e.getMessage());
        }
		catch(IOException e)
		{
            System.out.println("Caught IOException: " + e.getMessage());
        	e.printStackTrace();
		}
	    catch(NumberFormatException e)
	    {
	    	System.out.println("Caught NumberFormatException: " + e.getMessage());
	    	e.printStackTrace();
	    }
	    catch(NoSuchElementException e)
	    {
	    	System.out.println("Caught NoSuchElementException: " + e.getMessage());
	    	e.printStackTrace();
	    }
    }
    
    /**
     * Print the grades for the class scores to output file
     * @param outputName file path of output file to print the class scores and grades
     */
    void PrintData(String outputName)
    {
        try{
            PrintWriter writer = new PrintWriter(outputName);
        	
//	    	System.out.println("Score" + "    " + "Grade");
		    writer.write("Score" + "    " + "Grade" + System.getProperty("line.separator"));

		    Iterator<AssignScore> iter = scores.iterator();
		    
		    while(iter.hasNext())
		    {	
		    	AssignScore scoreGrade = iter.next();
		    	scoreGrade.calcGrade(scoreAvg);
//		    	System.out.println("  " + scoreGrade.score + "       " + scoreGrade.grade);
			    writer.write("  " + scoreGrade.getScore() + "       " + scoreGrade.getGrade() + System.getProperty("line.separator"));
            
		    }
		    
		    writer.close();
        }
        catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when writing to file " + outputName + ": " + e.getMessage());
        }
	    catch(NoSuchElementException e)
	    {
	    	System.out.println("Caught NoSuchElementException: " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
}

/**
 * The AssignScore class will determine if grade is Outstanding/Unsatisfactory/Satisfactory (O/U/S)
 * @author msnyde8
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
     * @param newScore - score
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
    
    /**
     * getGrade returns grade value
     * @return String - grade
     */
    String getGrade()
    {
    	return grade;
    }
    
    /**
     * getScore returns score value
     * @return Integer - score
     */
    Integer getScore()
    {
    	return score;
    }
}

