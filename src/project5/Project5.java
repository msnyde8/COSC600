package project5;

import java.util.Scanner;

public class Project5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driveClass = new Driver();
		driveClass.runDriver();
	}

}

class Driver {
	private Scanner lineReader = new Scanner(System.in);
	private int cmdChoice = -1;
	private	String cmdStr = "";
	private boolean endCmd = false;
	
	void runDriver()
	{
		do
		{
			System.out.println("Please enter a command:\n1) Build a tree," +
					"\n2) Print tree inorder,\n3) Print tree postorder," +
					"\n4) Count tree leaf nodes,\n5) Swap node children," +
					"\n6) Compare two trees,\n7) Exit");
		
			try
			{
				cmdStr = lineReader.nextLine();
				
				if(false == cmdStr.matches("[+-]?\\d"))
				{
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
				cmdChoice = Integer.parseInt(cmdStr);
				if((cmdChoice < 1) || (cmdChoice > 7))
				{
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
				
				switch(cmdChoice)
				{
					case 1:
						buildTree();
						break;
					case 2:
						visitInorder();
						break;
					case 3:
						visitPostorder();
						break;
					case 4:
						leafCount();
						break;
					case 5:
						childSwap();
						break;
					case 6:
						treeCompare();
						break;
					case 7:
						System.out.println("Thank you and have a nice day!");
						endCmd = true;
						break;
					default:
						break;
				}
			}
			catch(IllegalStateException|NumberFormatException e)
			{
				System.out.println("Caught Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}while(false == endCmd);
	}

	void buildTree()
	{
		System.out.println("Please enter integers for tree:");
	}
	
	void visitInorder()
	{
		System.out.println("Printing tree inorder:");
	}
	
	void visitPostorder()
	{
		System.out.println("Printing tree postorder:");
	}
	
	void leafCount()
	{
		System.out.println("Counting tree leaf nodes:");
	}
	
	void childSwap()
	{
		System.out.println("Please enter parent node Integer value for child swap:");
	}
	
	void treeCompare()
	{
		System.out.println("Comparing trees:");
	}
}
