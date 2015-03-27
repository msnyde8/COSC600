package project5;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Project5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driveClass = new Driver();
		driveClass.runDriver();
	}

}

class Driver {
	private Scanner lineReader = new Scanner(System.in);
	private Vector<treeClass> createdTrees = new Vector<treeClass>();
	
	void runDriver()
	{
		int cmdChoice = -1;
		boolean endCmd = false;
		String cmdStr = "";
		
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
//				if((cmdChoice < 1) || (cmdChoice > 8))
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
						visitTree(true);
						break;
					case 3:
						visitTree(false);
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
/*					case 8:
						treeNode tmpNode = new treeNode();
						tmpNode.dataInt = 25;
						if(true == createdTrees.get(0).findNode(createdTrees.get(0).getRoot(), tmpNode))
						{
							System.out.println("Found node!");
						}
						else
						{
							System.out.println("Node not found");
						}
*/
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
		try
		{
			System.out.println("Please enter integers for tree T" + (createdTrees.size()+1) + ":");
			
			StringTokenizer strToken = new StringTokenizer(lineReader.nextLine(), " ,");
			if(false == strToken.hasMoreElements())
			{
				System.out.println("No nodes to insert");
				return;
			}

			treeClass newTree = new treeClass();
						
			while(strToken.hasMoreElements())
			{
				treeNode newNode = new treeNode();
				newNode.dataInt = Integer.parseInt(strToken.nextElement().toString());
				System.out.println("newNode.dataInt: "+newNode.dataInt);
				if(false == newTree.insertNode(newNode))
				{
					System.out.println("Inserting new node failed");
				}
			}
			
			createdTrees.addElement(newTree);
		}
		catch(NullPointerException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Returns the index for the createdTrees vector
	int pickTree()
	{
		boolean endLoop = false;
		String choiceStr = "";
		int intChoice = -1;
		
		if(createdTrees.isEmpty())
		{
			System.out.println("No trees added to traverse");
			return -1;
		}
		do
		{
			System.out.println("Which tree to traverse?: (1 to " + createdTrees.size() + ")");
			choiceStr = lineReader.nextLine();
			
			if(false == choiceStr.matches("[+-]?\\d"))
			{
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
			
			intChoice = Integer.parseInt(choiceStr);
			
			if((0 < intChoice) && (intChoice <= createdTrees.size()))
			{
				endLoop = true;
			}
			else
			{
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
		}while(false == endLoop);
		
		return (intChoice-1);
	}
	void visitTree(boolean traverseInorder)
	{
		int treeChoice = pickTree();
		
		if((-1 == treeChoice) || (0 < treeChoice) || ((createdTrees.size()-1) < treeChoice))
		{
			return;
		}

		if(false == traverseInorder)
		{
			System.out.println("Printing tree postorder ...");
			createdTrees.get(treeChoice).traversePostorder();
		}
		else
		{
			System.out.println("Printing tree inorder ...");
			createdTrees.get(treeChoice).traverseInorder();
		}
	}
	
	void leafCount()
	{
		int treeChoice = pickTree();
		
		if((-1 == treeChoice) || (treeChoice < 0) || ((createdTrees.size()-1) < treeChoice))
		{
			return;
		}

		System.out.println("Counting tree leaf nodes ...");
		try
		{
			createdTrees.get(treeChoice).countLeaves();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void childSwap()
	{
		int treeChoice = pickTree();
		
		if((-1 == treeChoice) || (treeChoice < 0) || ((createdTrees.size()-1) < treeChoice))
		{
			return;
		}

		System.out.println("Swapping children ...");
		
		try
		{
			createdTrees.get(treeChoice).swapChildren();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void treeCompare()
	{
		System.out.println("Comparing trees ...");
	}
}

class treeNode
{
	int dataInt;
	treeNode leftChild;
	treeNode rightChild;
	
	public treeNode() {}
	
	public treeNode(treeNode tNode)
	{
		dataInt = tNode.dataInt;
		leftChild = tNode.leftChild;
		rightChild = tNode.rightChild;
	}
	boolean isLeaf()
	{
		if((null == leftChild) && (null == rightChild))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	int minValue()
	{
		if(null == leftChild)
		{
			return dataInt;
		}
		else
		{
			return leftChild.minValue();
		}
	}
	boolean insertNode(treeNode newNode)
	{
		if(dataInt == newNode.dataInt)
		{
			System.out.println("Node already exists");
			return false;
		}
		else if(dataInt < newNode.dataInt)
		{
			if(null == rightChild)
			{
				System.out.println("RightChild added");
				rightChild = newNode;
				return true;
			}
			else
			{
				System.out.println("Looking at rightChild");
				return (rightChild.insertNode(newNode));
			}
		}
		else // (dataInt > newNode.dataInt)
		{
			System.out.println("LeftChild added");
			if(null == leftChild)
			{
				System.out.println("LightChild added");
				leftChild = new treeNode(newNode);
				return true;
			}
			else
			{
				System.out.println("Looking at leftChild");
				return (leftChild.insertNode(newNode));
			}
		}
	}
	boolean findNode(treeNode nodeFind)
	{
		if(dataInt == nodeFind.dataInt)
		{
			return true;
		}
		else if(dataInt < nodeFind.dataInt)
		{
			if(null == leftChild)
			{
				return false;
			}
			else
			{
				return leftChild.findNode(nodeFind);
			}
		}
		else // (rootNode.dataInt > findNode.dataInt)
		{
			if(null == rightChild)
			{
				return false;
			}
			else
			{
				return rightChild.findNode(nodeFind);
			}
		}
	}
	boolean deleteNode(treeNode oldNode, treeNode parentNode)
	{
		if(dataInt < oldNode.dataInt)
		{
			if(null == leftChild)
			{
				return false;
			}
			else
			{
				return leftChild.deleteNode(oldNode, this);
			}	
		}
		else if(dataInt > oldNode.dataInt)
		{
			if(null == rightChild)
			{
				return false;
			}
			else
			{
				return rightChild.deleteNode(oldNode, this);
			}
		}
		else // (dataInt == oldNode.dataInt)
		{
			if((null != leftChild) && (null != rightChild))
			{
				dataInt = rightChild.minValue();
				rightChild.deleteNode(this,this);
			}
			else if(parentNode.leftChild == this)
			{
				if(null == leftChild)
				{
					parentNode.leftChild = rightChild;
				}
				else
				{
					parentNode.leftChild = leftChild;
				}
			}
			else if(parentNode.rightChild == this)
			{
				if(null == leftChild)
				{
					parentNode.rightChild = rightChild;
				}
				else
				{
					parentNode.rightChild = leftChild;
				}
			}
			return true;
		}
	}
	String traverseInorder(String outputStr)
	{
		if(null != leftChild)
		{
//			System.out.println("Looking at leftChild");
			outputStr = leftChild.traverseInorder(outputStr);
		}
		outputStr = outputStr + ((Integer)dataInt).toString() + ", ";
		if(null != rightChild)
		{
//			System.out.println("Looking at rightChild");
			outputStr = rightChild.traverseInorder(outputStr);
		}
		return outputStr;
	}
	String traversePostorder(String outputStr)
	{
		if(null != leftChild)
		{
//			System.out.println("Looking at leftChild");
			outputStr = leftChild.traversePostorder(outputStr);
		}
		if(null != rightChild)
		{
//			System.out.println("Looking at rightChild");
			outputStr = rightChild.traversePostorder(outputStr);
		}
		outputStr = outputStr + ((Integer)dataInt).toString() + ", ";
		
		return outputStr;
	}
	int countLeaf(int leafCount)
	{
		if(this.isLeaf())
		{
//			System.out.println("Leaf found!");
			return (leafCount + 1);
		}
		if(null != leftChild)
		{
			leafCount = leftChild.countLeaf(leafCount);
		}
		if(null != rightChild)
		{
			leafCount = rightChild.countLeaf(leafCount);
		}
		return leafCount;
	}
	void swapChild()
	{
		if((null == leftChild) && (null == rightChild))
		{
			return;
		}
		if(null != leftChild)
		{
			leftChild.swapChild();
		}
		if(null != rightChild)
		{
			rightChild.swapChild();
		}
		
		if(null == rightChild)
		{
			rightChild = leftChild;
			leftChild = null;
		}
		else
		{
			treeNode tmpRightNode = new treeNode(rightChild);
			rightChild = leftChild;
			leftChild = tmpRightNode;
		}
	}
}

class treeClass
{
	private treeNode treeRoot;
	
	public treeClass() {}
	
	public treeClass(treeNode rNode)
	{
		treeRoot = new treeNode(rNode);
	}
	treeNode getRoot()
	{
		return treeRoot;
	}
	boolean insertNode(treeNode newNode)
	{
		if(null == treeRoot)
		{
			System.out.println("EmptyTree");
			treeRoot = new treeNode(newNode);
			return true;
		}
		else
		{
			return treeRoot.insertNode(newNode);
		}
	}
	boolean deleteNode(treeNode oldNode)
	{
		if(null == treeRoot)
		{
			return false;
		}
		else
		{
			if(treeRoot.dataInt == oldNode.dataInt)
			{
				treeNode tmpNode = new treeNode();
				tmpNode.dataInt = 0;
				tmpNode.leftChild = treeRoot;
				boolean delResult = treeRoot.deleteNode(oldNode, tmpNode);
				treeRoot = tmpNode.leftChild;
				return delResult;
			}
			else
			{
				return treeRoot.deleteNode(oldNode, null);
			}
		}
	}
	boolean findNode(treeNode nodeFind)
	{
		if(null == treeRoot)
		{
			return false;
		}
		else
		{
			return treeRoot.findNode(nodeFind);
		}
	}
	void traverseInorder()
	{
		String outputStr = "";
		if(null == treeRoot)
		{
			return;
		}
		else
		{
			outputStr = treeRoot.traverseInorder(outputStr);
		}
		System.out.println("Inorder: " + outputStr);
	}
	void traversePostorder()
	{
		String outputStr = "";
		if(null == treeRoot)
		{
			return;
		}
		else
		{
			outputStr = treeRoot.traversePostorder(outputStr);
		}
		System.out.println("Postorder: " + outputStr);
	}
	void countLeaves()
	{
		if(null == treeRoot)
		{
			System.out.println("Error: Empty Tree");
		}
		else
		{
			int leafCount = 0;
			leafCount = treeRoot.countLeaf(leafCount);
			if(0 < leafCount)
			{
				System.out.println("Number of leaves: " + leafCount);
			}
			else
			{
				System.out.println("Error counting leaves");
			}
		}
	}
	void swapChildren()
	{
		if(null == treeRoot)
		{
			System.out.println("Error: Empty Tree");
		}
		else
		{
			treeRoot.swapChild();
		}
	}
}