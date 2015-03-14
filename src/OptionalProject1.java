import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class OptionalProject1 {

	public static void main(String[] args) {
		try
		{
			String inputName = (System.getProperty("user.dir") +
					System.getProperty("file.separator") +
					"optionalproject1_input.txt");
			FileReader fileRead = new FileReader(inputName);
			BufferedReader inputStream = new BufferedReader(fileRead);

			String charStr = inputStream.readLine();
		
			Stack<String> operatorStack = new Stack<String>();
			Queue<String> charQueue = new LinkedList<String>();
		
			while((null != charStr) && (false == charStr.isEmpty()))
			{
				String rtnStr = "";
				System.out.println(charStr);
				StringTokenizer strToken = new StringTokenizer(charStr,"^*/+-() \t",true);
				while(strToken.hasMoreTokens())
				{
					String c = strToken.nextToken();

				if(true == isNumber(c))
				{
//					System.out.println("Add to Queue: " + c);
					charQueue.add(c);
				}
				else if(true == isOperator(c))
				{
					if((false == operatorStack.empty()) &&
							(true == isLowerPriority(c, operatorStack.peek())))
					{
//						System.out.println("Add to Queue: " + operatorStack.peek());
						charQueue.add(operatorStack.pop());
						while(false == operatorStack.empty())
						{
							if(true == isLowerPriority(c,operatorStack.peek()))
							{
//								System.out.println("Add to Queue: " + operatorStack.peek());
								charQueue.add(operatorStack.pop());
							}
							else
							{
								break;
							}
						}
					}
//					System.out.println("Add to Stack: " + c);
					operatorStack.add(c);
				}
				else if(true == c.equals("("))
				{
//					System.out.println("Add to Stack: " + c);
					operatorStack.add(c);
				}
				else if (true == c.equals(")"))
				{
					while((false == operatorStack.empty()) &&
							(false == operatorStack.peek().equals("(")))
					{
//						System.out.println("Add to Queue: " + operatorStack.peek());
						charQueue.add(operatorStack.pop());
					}
					if(false == operatorStack.empty())
					{
//						System.out.println("Remove from Stack: " + operatorStack.peek());
						operatorStack.pop();
					}
				}
			}
		
			while(false == operatorStack.empty())
			{
				charQueue.add(operatorStack.pop());
			}
			
			while(null != charQueue.peek())
			{
				rtnStr = rtnStr + charQueue.element();
//				System.out.print(charQueue.element());
				charQueue.remove();
			}
			System.out.println(rtnStr);
			charStr = inputStream.readLine();
			}
			inputStream.close();
		}
		catch(IOException|EmptyStackException|IllegalStateException|
				ClassCastException|NullPointerException|
				IllegalArgumentException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	static private boolean isNumber(String c)
	{
		return Pattern.compile( "[0-9]*" ).matcher(c).matches();
	}

	static private boolean isOperator(String c)
	{
		return ((c.equals("+")) || (c.equals("-")) || (c.equals("–")) ||
				(c.equals("*")) || (c.equals("x")) || (c.equals("/")) ||
				(c.equals("^")));
	}
	
	static private int charPriority(String priorChar)
	{
		switch(priorChar)
		{
			case "^":
				return 3;
			case "*":
			case "x":
			case "/":
				return 2;
			case "+":
			case "-":
			case "–":
				return 1;
			default:
				return 0;
		}
	}
	
	static private boolean isLowerPriority(String firstStr, String secondStr)
	{

		int firstPriority = charPriority(firstStr);
		int secondPriority = charPriority(secondStr);

		if((true == firstStr.equals("(")) || (true == secondStr.equals("(")))
		{
			return false;
		}
		else if(firstPriority <= secondPriority)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
