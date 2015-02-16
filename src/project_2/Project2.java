package project_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 * @author msnyde8
 */
public class Project2 {

	public static void main(String[] args) {

		String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project2_input.txt");
		String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project2_output.txt");
		
		Driver driveClass = new Driver();
		driveClass.ReadFile(inputName);
		driveClass.Statement(outputName);
		
	}
}

/**
 * The Driver class reads the file and prints out results of assigning scores
 * @author msnyde8
 */
class Driver {
	
    private Vector<Account> accounts;
        
    Driver()
    {
    	accounts = new Vector<Account>();
    }
    
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
    		    StringTokenizer strTokened = new StringTokenizer(readBuff, " ,\t");
    		
    	        while(strTokened.countTokens() > 5)
    	        {
    	        	String name = strTokened.nextElement().toString();
    	        	Integer accountNum = Integer.parseInt(strTokened.nextElement().toString());
    	        	String phone = strTokened.nextElement().toString();
    	        	String ssn = strTokened.nextElement().toString();
    	        	Double balance = Double.parseDouble(strTokened.nextElement().toString());
    	        	//balance.setPrecision(2);
    	        	String type = strTokened.nextElement().toString();
    	        	Account addAccount;
    	        	
    	        	switch(type)
    	        	{
    	        		case "B":
    	        			addAccount = new BusinessAccount(name, accountNum, phone, ssn, balance);
    	        			break;
    	        		case "C":
    	        			addAccount = new CheckingAccount(name, accountNum, phone, ssn, balance);
    	        			break;
    	        		case "S":
    	        			addAccount = new SavingsAccount(name, accountNum, phone, ssn, balance);
    	        			break;
    	        		default:
    	        			addAccount = new Account(name, accountNum, phone, ssn, balance);
    	        			break;
    	        	}
    	        	
    		        /*Account addAccount = new Account(strTokened.nextElement().toString(),
    		        		Integer.parseInt(strTokened.nextElement().toString()),
    		        		strTokened.nextElement().toString(),
    		        		strTokened.nextElement().toString(),
    		        		Integer.parseInt(strTokened.nextElement().toString()),
    		        		strTokened.nextElement().toString());*/
    			    accounts.addElement(addAccount);
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

    void Statement(String outputName)
    {
        try{
            PrintWriter writer = new PrintWriter(outputName);
            System.out.println("Name\tSSN\t\tAccountNum\tPhoneNum\tOpenBal\t\tCloseBal");
		    writer.write("Name\tSSN\t\tAccountNum\tPhoneNum\tOpenBal\t\tCloseBal" + System.getProperty("line.separator"));

		    Iterator<Account> iter = accounts.iterator();
		    
		    do
		    {
		    	Account accountInfo = iter.next();
		    	Double closeBalance = accountInfo.calcInterest(accountInfo.getBalance());
		    	System.out.printf("%s\t%s\t%d\t%s\t%.2f\t\t%.2f"+ System.getProperty("line.separator"), accountInfo.getName(),
		    			accountInfo.getSsn(), accountInfo.getAccountNum(),
		    			accountInfo.getPhone(), accountInfo.getBalance(), closeBalance);
		    	writer.printf("%s\t%s\t%d\t%s\t%.2f\t\t%.2f" + System.getProperty("line.separator"), accountInfo.getName(),
		    			accountInfo.getSsn(), accountInfo.getAccountNum(),
		    			accountInfo.getPhone(), accountInfo.getBalance(), closeBalance);
		    	/*System.out.println("  " + accountInfo.getName() + "\t"
		    			+ accountInfo.getSsn() + "\t" + accountInfo.getAccountNum() + "\t"
		    			+ accountInfo.getPhone() + "\t" + accountInfo.getBalance() + "\t" + closeBalance);*/
			    //writer.write("  " + scoreGrade.getScore() + "       " + scoreGrade.getGrade() + System.getProperty("line.separator"));
            
		    }while(iter.hasNext());
		    
		    writer.close();
        }
        catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when writing to file " + outputName + ": " + e.getMessage());
        }
    }
}

class Account {

	private String name = "";
	private	Integer accountNum = 0;
	private	String phone = "";
	private String ssn = "";
	private Double balance = 0.0;
	//private String type = "";

	Account(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		name = accName;
		//System.out.println(name);
		accountNum = accNum;
		//System.out.println(accountNum);
		phone = accPhone;
		//System.out.println(phone);
		ssn = accSsn;
		//System.out.println(ssn);
		balance = accBalance;
		//System.out.printf("%.2f\n",balance);
		//type = accType;
		//System.out.println(type);
	}
	
	public String getName() {
		return name;
	}

	public Integer getAccountNum() {
		return accountNum;
	}

	public String getPhone() {
		return phone;
	}

	public String getSsn() {
		return ssn;
	}

	public Double getBalance() {
		return balance;
	}

	/*public String getType() {
		return type;
	}*/

	Double calcInterest(Double openBalance)
	{
		return openBalance;
	}
	
}

class SavingsAccount extends Account{

	SavingsAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
	
	Double calcInterest(Double openBalance)
	{
		if(openBalance < 5000)
			return (openBalance * 1.04);
		else
			return (openBalance * 1.05);
	}
	
}

class CheckingAccount extends Account{
	
	CheckingAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
	
	Double calcInterest(Double openBalance)
	{
		return (openBalance * 1.025);
	}
}

class BusinessAccount extends Account{
	
	BusinessAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
	
}