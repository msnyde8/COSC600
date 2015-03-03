

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * The main class for Project2
 * @author msnyde8
 */
public class Project2 {

	public static void main(String[] args) {
		/**
		 * Input file path
		 */
		String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project2_input.txt");
		/**
		 * Output file path
		 */
		String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project2_output.txt");
		
		/**
		 * Driver object
		 */
		Driver driveClass = new Driver();
		driveClass.ReadFile(inputName);
		driveClass.Statement(outputName);
	}
} // End class Project2


/**
 * The Driver class reads the input file and prints out the end of month statement to the output file
 * @author msnyde8
 */
class Driver {
	/**
	 * Vector of accounts from input file
	 */
    private Vector<Account> accounts;
    
    /**
     * Driver constructor
     */
    Driver()
    {
    	accounts = new Vector<Account>();
    }
    
	/**
     * Read the account information from the input file
     * @param inputName file path of account information input file
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
            
            buffRead.close();
        }
	    catch(FileNotFoundException e)
        {
        	System.out.println("Caught FileNotFoundException when reading from file " + inputName + ": " + e.getMessage());
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
	    catch(NullPointerException e)
	    {
	    	System.out.println("Caught NullPointerException: " + e.getMessage());
	    	e.printStackTrace();
	    }
	    catch(NoSuchElementException e)
	    {
	    	System.out.println("Caught NoSuchElementException: " + e.getMessage());
	    	e.printStackTrace();
	    }
    }

    /**
     * Print the monthly statement for the bank accounts to the output file
     * @param outputName file path of output file to print the monthly accounts statement
     */
    void Statement(String outputName)
    {
        try{
            PrintWriter writer = new PrintWriter(outputName);
//            System.out.println("Name\tSSN\t\tAccountNum\tPhoneNum\tOpenBal\t\tCloseBal");
		    writer.write("Name\tSSN\t\t\tAccountNum\tPhoneNum\tOpenBal\t\tCloseBal" + System.getProperty("line.separator"));

		    Iterator<Account> iter = accounts.iterator();
		    
		    do
		    {
		    	Account accountInfo = iter.next();
		    	Double closeBalance = accountInfo.calcInterest(accountInfo.getBalance());
/*		    	System.out.printf("%s\t%s\t%d\t%s\t%.2f\t\t%.2f"+ System.getProperty("line.separator"), accountInfo.getName(),
		    			accountInfo.getSsn(), accountInfo.getAccountNum(),
		    			accountInfo.getPhone(), accountInfo.getBalance(), closeBalance);*/
		    	String name = accountInfo.getName();
		    	if(accountInfo.getName().length() < 4)
		    		name = accountInfo.getName() + "\t";
		    	writer.printf("%s\t%s\t%d\t%s\t%.2f\t\t%.2f" + System.getProperty("line.separator"), name,
		    			accountInfo.getSsn(), accountInfo.getAccountNum(),
		    			accountInfo.getPhone(), accountInfo.getBalance(), closeBalance);
/*		    	System.out.println("  " + accountInfo.getName() + "\t"
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
} // End class Driver


/**
 * The Account parent class
 * @author msnyde8
 */
class Account {
	/**
	 * Account owner name
	 */
	private String name = "";
	/**
	 * Account number
	 */
	private	Integer accountNum = 0;
	/**
	 * Account phone number
	 */
	private	String phone = "";
	/**
	 * Account SSN
	 */
	private String ssn = "";
	/**
	 * Account balance
	 */
	private Double balance = 0.0;
	//private String type = "";

    /**
     * Account constructor
     * @param accName - account owner name
     * @param accNum - account number
     * @param accPhone - account phone number
     * @param accSsn - account SSN
     * @param accBalance - account balance
     */
	Account(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		name = accName;
//		System.out.println(name);
		accountNum = accNum;
//		System.out.println(accountNum);
		phone = accPhone;
//		System.out.println(phone);
		ssn = accSsn;
//		System.out.println(ssn);
		balance = accBalance;
//		System.out.printf("%.2f\n",balance);
		//type = accType;
		//System.out.println(type);
	}

    /**
     * getName returns account owner name
     * @return String - name
     */
	public String getName() {
		return name;
	}

    /**
     * getAccountNum returns account number
     * @return Integer - account number
     */
	public Integer getAccountNum() {
		return accountNum;
	}

    /**
     * getPhone returns account phone number
     * @return String - account phone
     */
	public String getPhone() {
		return phone;
	}

    /**
     * getSsn returns account SSN
     * @return String - account SSN
     */
	public String getSsn() {
		return ssn;
	}

    /**
     * getBalance returns account balance
     * @return Double - account balance
     */
	public Double getBalance() {
		return balance;
	}

	/*public String getType() {
		return type;
	}*/

    /**
     * calcInterest calculates the interest/closing balance given the opening balance
     * @param openBalance - opening account balance
     * @return Double - closing account balance (including interest)
     */
	Double calcInterest(Double openBalance)
	{
		return openBalance;
	}
} // End class Account


/**
 * The SavingsAccount child class
 * @author msnyde8
 */
class SavingsAccount extends Account{
    /**
     * SavingsAccount constructor
     * @param accName - account owner name
     * @param accNum - account number
     * @param accPhone - account phone number
     * @param accSsn - account SSN
     * @param accBalance - account balance
     */
	SavingsAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
	
    /**
     * calcInterest (overwrites parent Account function) calculates the interest/closing balance given the opening balance
     * @param openBalance - opening account balance
     * @return Double - closing account balance (including interest)
     */
	Double calcInterest(Double openBalance)
	{
		if(openBalance < 5000)
			return (openBalance * 1.04);
		else
			return (openBalance * 1.05);
	}
} // End class SavingsAccount


/**
 * The CheckingAccount child class
 * @author msnyde8
 */
class CheckingAccount extends Account{
    /**
     * CheckingAccount constructor
     * @param accName - account owner name
     * @param accNum - account number
     * @param accPhone - account phone number
     * @param accSsn - account SSN
     * @param accBalance - account balance
     */
	CheckingAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
	
    /**
     * calcInterest (overwrites parent Account function) calculates the interest/closing balance given the opening balance
     * @param openBalance - opening account balance
     * @return Double - closing account balance (including interest)
     */
	Double calcInterest(Double openBalance)
	{
		return (openBalance * 1.025);
	}
} // End class CheckingAccount


/**
 * The BusinessAccount child class
 * @author msnyde8
 */
class BusinessAccount extends Account{
    /**
     * BusinessAccount constructor
     * @param accName - account owner name
     * @param accNum - account number
     * @param accPhone - account phone number
     * @param accSsn - account SSN
     * @param accBalance - account balance
     */
	BusinessAccount(String accName, Integer accNum, String accPhone, String accSsn, Double accBalance/*, String accType*/)
	{
		super(accName, accNum, accPhone, accSsn, accBalance/*, accType*/);
	}
} // End class BusinessAccount