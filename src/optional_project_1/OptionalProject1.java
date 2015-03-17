package optional_project_1;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.Math;

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
				List<String> rtnStr = new LinkedList<String>();
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
				rtnStr.add(charQueue.element());
				//System.out.print(charQueue.element());
				charQueue.remove();
			}
			printAnswer(rtnStr);
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
	
	static private void printAnswer(List<String> postfix)
	{
		Double printAnswer = 0.0;
		Stack<String> printStack = new Stack<String>();

		Iterator<String> listIter = postfix.iterator();
		while(listIter.hasNext())
		{
			System.out.print(listIter.next());
		}
		System.out.println();
		
		listIter = postfix.iterator();
		while(listIter.hasNext())
		{
			String valStr = listIter.next();
			if(true == isNumber(valStr))
			{
				printStack.push(valStr);
			}
			else
			{
				if(printStack.empty())
				{
					System.out.println("Statement malformed");
					return;
				}
				Double secInt = Double.parseDouble(printStack.pop());
				if(printStack.empty())
				{
					System.out.println("Statement malformed");
					return;
				}
				Double firstInt = Double.parseDouble(printStack.pop());
				switch(valStr)
				{
					case "+":
						printAnswer = firstInt + secInt;
						break;
					case "-":
					case "–":
						printAnswer = firstInt - secInt;
						break;
					case "*":
					case "x":
						printAnswer = firstInt * secInt;
						break;
					case "/":
						printAnswer = firstInt / secInt;
						break;
					case "^":
						printAnswer = Math.pow(firstInt, secInt);
						break;
					default:
						System.out.println("Statement malformed");
						return;
				}
//				System.out.println(printAnswer);
				printStack.add(printAnswer.toString());
			}
		}
		System.out.println(printStack.pop());
	}
}
