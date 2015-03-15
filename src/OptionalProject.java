import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.regex.*;


public class OptionalProject {
	public static void main(String[] args) {
		try
		{
			boolean end = false;
			Scanner lineReader = new Scanner(System.in);
			do
			{
				System.out.println("Would you like to input from: 1) Input file (optionalproject1_input.txt) 2) GUI or 0) Exit?");

				Integer optionInt = Integer.parseInt(lineReader.nextLine());
				switch(optionInt)
				{
					case 1:
						InfixToPostfix infixPostfix = new InfixToPostfix();
						infixPostfix.main(args);
						end = true;
						break;
					case 2:
						RPNCalcGUI calcGUI = new RPNCalcGUI();
						calcGUI.main(args);
						end = true;
						break;
					case 0:
						end = true;
						break;
					default:
						System.out.println("Incorrect option please try again");
						break;
				}				
			}while((false == end) && lineReader.hasNextLine());
		}
		catch(IllegalStateException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

class RPNCalcGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField displayField;
	private boolean startNums = true;

	public RPNCalcGUI()
	{
		displayField = new JTextField("0", 12);
		displayField.setHorizontalAlignment(JTextField.RIGHT);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClear();
			}
		});
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JButton delButton = new JButton("Del");
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		int strLength = displayField.getText().length();

        		if(1 < strLength)
        		{
        			String actCmd = displayField.getText().substring(0, strLength-1);
        			displayField.setText(actCmd);
        		}
        		else
        		{
        			actionClear();
        		}
			}
		});
		
        String buttonOrder = "789456123E0.( )";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 3, 2, 2));
        for (int i = 0; i < buttonOrder.length(); ++i) {
            String numButton = buttonOrder.substring(i, i+1);
            JButton txtButton = new JButton(numButton);
            if (numButton.equals(" ")) {
                txtButton.setEnabled(false);
            } else {
                txtButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		String actCmd = e.getActionCommand();
                		if(actCmd.equals("E"))
                		{
                			actCmd = " ";
                		}
                		if(true == startNums)
                		{
                			displayField.setText(actCmd);
                			startNums = false;
                		}
                		else
                		{
                			displayField.setText(displayField.getText() + actCmd);
                		}
                	}
                });
            }
            buttonPanel.add(txtButton);
        }
        
        //... One ActionListener to use for all operator buttons.
        //... Create panel with gridlayout to hold operator buttons.
        //    Use array of button names to create buttons in a loop.
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(6, 1, 2, 2));
        String[] opOrder = {"+", "-", "*", "/", "^", "="};
        for (int i = 0; i < opOrder.length; i++) {
            JButton opButton = new JButton(opOrder[i]);
            opButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		try
            		{
            			String actCmd = e.getActionCommand();
            				
            			if(true == actCmd.equals("="))
            			{
            				InfixToPostfix inToPost = new InfixToPostfix();
            				String rtnStr = inToPost.convertStr(displayField.getText());
            				displayField.setText(rtnStr);
            			}
            			else if(startNums)
            			{
            				displayField.setText(actCmd);
            				startNums = false;
            			}
            			else
            			{
            				displayField.setText(displayField.getText() + actCmd);
            			}
                		delButton.setEnabled(true);
            		}
            		catch(NumberFormatException ex)
            		{
            			actionClear();
            			displayField.setText("ERROR - Exception");
            		}
            	}
            });
            opPanel.add(opButton);
        }
        
        //... Put Clear button in flow layout to keep from expanding.
        JPanel cmdPanel = new JPanel();
        cmdPanel.setLayout(new FlowLayout());
        cmdPanel.add(quitButton);
        cmdPanel.add(clearButton);
        cmdPanel.add(delButton);
        
		
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5,5));
        contentPanel.add(displayField, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(opPanel, BorderLayout.EAST);
        contentPanel.add(cmdPanel, BorderLayout.SOUTH);
        
        this.setContentPane(contentPanel);
        this.pack();
        this.setTitle("Infix Calculator");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private void actionClear()
	{
		startNums = true;
		displayField.setText("0");
	}
	
	public static void main(String[] args) {
		RPNCalcGUI calcGUI = new RPNCalcGUI();
		calcGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calcGUI.setVisible(true);
	}
}


class InfixToPostfix {

	private static String infixStr = "";
	private static List<String> postfixStr = new LinkedList<String>();
	
	static String convertStr(String inStr)
	{
		try
		{
			infixStr = inStr;
			postfixStr.clear();
		
			Stack<String> operatorStack = new Stack<String>();
			Queue<String> charQueue = new LinkedList<String>();

			StringTokenizer strToken = new StringTokenizer(infixStr,"^*/+-() \t",true);
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
//				System.out.println(postfixStr);
				postfixStr.add(charQueue.element());
//				System.out.println(postfixStr);
				charQueue.remove();
			}
		}
		catch(EmptyStackException|IllegalStateException|ClassCastException|
				NullPointerException|IllegalArgumentException e)
		{
	    	System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
		
		return printAnswer(postfixStr);
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
	
	static private String printAnswer(List<String> postfix)
	{
		String ansStr = "ERROR - Statement malformed";
		Double printAnswer = 0.0;
		Stack<String> printStack = new Stack<String>();

		Iterator<String> listIter = postfix.iterator();
/*		while(listIter.hasNext())
		{
			System.out.print(listIter.next());
		}
		System.out.println();
		
		listIter = postfix.iterator();*/
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
					System.out.println(ansStr);
					return ansStr;
				}
				Double secInt = Double.parseDouble(printStack.pop());
				if(printStack.empty())
				{
					System.out.println(ansStr);
					return ansStr;
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
						System.out.println(ansStr);
						return ansStr;
				}
//				System.out.println(printAnswer);
				if((printAnswer*10)%10 == 0)
				{
					printStack.add(((Integer)(printAnswer.intValue())).toString());
					
				}
				else
				{
					printStack.add(printAnswer.toString());
				}
			}
		}
		ansStr = printStack.pop();
		return ansStr;
	}
	
	public static void main(String[] args) {
		try
		{
			String inputName = (System.getProperty("user.dir") +
					System.getProperty("file.separator") +
					"optionalproject1_input.txt");
			FileReader fileRead = new FileReader(inputName);
			BufferedReader inputStream = new BufferedReader(fileRead);

			String charStr = inputStream.readLine();

		
			while((null != charStr) && (false == charStr.isEmpty()))
			{
				System.out.println(charStr);
				System.out.println(InfixToPostfix.convertStr(charStr));

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
}
