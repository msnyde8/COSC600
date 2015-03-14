package optional_project_1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class OptionalProject1_save {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			String inputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "optionalproject1_input.txt");
			FileReader fileRead = new FileReader(inputName);
			BufferedReader inputStream = new BufferedReader(fileRead);

			String charStr = inputStream.readLine();
			//int characterNum = inputStream.read();
		
			Stack<String> operatorStack = new Stack<String>();
			Queue<String> charQueue = new LinkedList<String>();
			//Stack<Character> operatorStack = new Stack<Character>();
			//Queue<Character> charQueue = new LinkedList<Character>();
		
			//while(-1 != characterNum)
			while((null != charStr) && (false == charStr.isEmpty()))
			{
				System.out.println(charStr);
				//char c = (char)characterNum;
				StringTokenizer strToken = new StringTokenizer(charStr,"^*/+-() \t",true);
				while(strToken.hasMoreTokens())
				{
					String c = strToken.nextToken();

				if(true == isNumber(c))
				{
					System.out.println("Add to Queue: " + c);
					charQueue.add(c);
				}
				else if(true == isOperator(c))
				{
					if((false == operatorStack.empty()) &&
							(true == isLowerPriority(c, operatorStack.peek())))
					{
						System.out.println("Add to Queue: " + operatorStack.peek());
						charQueue.add(operatorStack.pop());
						while(false == operatorStack.empty())
						{
							if(true == isLowerPriority(c,operatorStack.peek()))
							{
								System.out.println("Add to Queue: " + operatorStack.peek());
								charQueue.add(operatorStack.pop());
							}
							else
							{
								break;
							}
						}
					}
					System.out.println("Add to Stack: " + c);
					operatorStack.add(c);
				}
				else if(true == c.equals("("))
				{
					System.out.println("Add to Stack: " + c);
					operatorStack.add(c);
				}
				else if (true == c.equals(")"))
				{
					while((false == operatorStack.empty()) &&
							(false == operatorStack.peek().equals("(")))
					{
						System.out.println("Add to Queue: " + operatorStack.peek());
						charQueue.add(operatorStack.pop());
					}
					if(false == operatorStack.empty())
					{
						System.out.println("Remove from Stack: " + operatorStack.peek());
						operatorStack.pop();
					}
				}
				//characterNum = inputStream.read();
			}
			//inputStream.close();
		
			while(false == operatorStack.empty())
			{
				//System.out.println("Add to Queue: " + operatorStack.peek());
				charQueue.add(operatorStack.pop());
			}
			
			while(null != charQueue.peek())
			{
				System.out.print(charQueue.element());
				charQueue.remove();
			}
			System.out.println();
			charStr = inputStream.readLine();
			}
			inputStream.close();
		}
		catch(IOException|EmptyStackException|
				IllegalStateException|ClassCastException|NullPointerException|
				IllegalArgumentException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	static private boolean isNumber(String c)
	{
		/*return ((c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') ||
				(c == '6') || (c == '7') || (c == '8') || (c == '9') || (c == '0'));*/
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
		System.out.println(firstStr);
		System.out.println(secondStr);
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
	
/*	static private boolean isNumber(char c)
	{
		return ((c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') ||
				(c == '6') || (c == '7') || (c == '8') || (c == '9') || (c == '0'));
	}
	
	static private boolean isOperator(char c)
	{
		return ((c == '+') || (c == '-') || (c == '*') ||
				(c == '/') || (c == '^'));
	}
	
	static private int charPriority(char priorChar)
	{
		switch(priorChar)
		{
			case '^':
				return 3;
			case '*':
			case '/':
				return 2;
			case '+':
			case '-':
				return 1;
			default:
				return 0;
		}
	}
	
	static private boolean isLowerPriority(char firstC, char secondC)
	{
		int firstPriority = charPriority(firstC);
		int secondPriority = charPriority(secondC);

		if(firstC == '(')
		{
			return true;
		}
		else if(firstPriority <= secondPriority)
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/
}
