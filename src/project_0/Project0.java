package project_0;
/**
 * 
 */


import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author msnyde8
 */
public class Project0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project0_output.txt");
        System.out.println("Hello, Towson");
        System.out.println("How are you today?");
        try{
            PrintWriter writer = new PrintWriter(fileName);
        	writer.write("Hello, Towson" + System.getProperty("line.separator"));
            writer.write("How are you today?");
            writer.close();
        }
        catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when writing to file " + fileName + ": " + e.getMessage());
        }
        
	}

}
