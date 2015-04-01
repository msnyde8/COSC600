

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
					"\n2) Print full tree or Nth node inorder," +
					"\n3) Print full tree or Nth node postorder," +
					"\n4) Count tree leaf nodes," +
					"\n5) Swap node children (creates new tree)," +
					"\n6) Delete node," +
					"\n7) Compare two trees," +
					"\n0) Exit");
		
			try
			{
				cmdStr = lineReader.nextLine();
				
				if(false == cmdStr.matches("\\d"))
				{
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
				cmdChoice = Integer.parseInt(cmdStr);
//				if((cmdChoice < 0) || (cmdChoice > 8))
				if((cmdChoice < 0) || (cmdChoice > 7))
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
						deleteNode();
						break;
					case 7:
						treeCompare();
						break;
					case 0:
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
				newNode.setDataInt(Integer.parseInt(strToken.nextElement().toString()));
//				System.out.println("newNode.dataInt: "+newNode.getDataInt());
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
	int pickTree(String actionStr)
	{
		boolean endLoop = false;
		String choiceStr = "";
		int intChoice = -1;
		
		if(createdTrees.isEmpty())
		{
			System.out.println("No trees created. Please build a tree first.");
			return -1;
		}
		do
		{
			System.out.println("Which tree to " + actionStr + "?: (1 to " + createdTrees.size() + ")");
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
		int treeChoice = pickTree("traverse");
		int startNode = -1;
		int endNode = -1;
		
		if(-1 == treeChoice)
		{
			return;
		}
		if((treeChoice < 0) || (createdTrees.size() < treeChoice))
		{
			System.out.println("Invalid tree choice");
			return;
		}
		
		startNode = createdTrees.get(treeChoice).pickNode(lineReader);
		if(startNode < 0)
		{
			return;
		}
		if(0 == startNode)
		{
			startNode = 1;
			endNode = createdTrees.get(treeChoice).countNodes();
		}
		else
		{
			endNode = startNode;
		}
		System.out.println("startNode=" + startNode + "; endNode=" + endNode);

		if(false == traverseInorder)
		{
			System.out.println("Printing tree postorder ...");
			createdTrees.get(treeChoice).traverseTree(false, startNode, endNode);
		}
		else
		{
			System.out.println("Printing tree inorder ...");
			createdTrees.get(treeChoice).traverseTree(true, startNode, endNode);
		}
	}
	
	void leafCount()
	{
		int treeChoice = pickTree("count leaves");
		
		if((-1 == treeChoice) || (treeChoice < 0) || ((createdTrees.size()-1) < treeChoice))
		{
			return;
		}

		System.out.println("Counting tree leaf nodes ...");
		try
		{
			int leafCount = createdTrees.get(treeChoice).countLeaves();
			if(0 <leafCount)
			{
				System.out.println("Number of leaves: " + leafCount);
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void childSwap()
	{
		int treeChoice = pickTree("swap children");
		
		if((-1 == treeChoice) || (treeChoice < 0) || ((createdTrees.size()-1) < treeChoice))
		{
			return;
		}

		System.out.println("Swapping children ...");
		
		try
		{
			treeClass newTree = new treeClass();
			if(false == newTree.copyTree(createdTrees.get(treeChoice)))
			{
				System.out.println("Error copying tree");
			}
			else
			{	
				createdTrees.add(newTree);
				newTree.swapChildren();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	boolean deleteNode()
	{
		return false;
	}
	
	void treeCompare()
	{
		int firstTree = pickTree("compare(1)");
		int secondTree = pickTree("compare(2)");
		
		if((-1 == firstTree) || (-1 == secondTree) || (firstTree < 0) || (secondTree < 0) ||
				((createdTrees.size()-1) < firstTree) || ((createdTrees.size()-1) < secondTree))
		{
			System.out.println("Invalid tree(s) selected.");
			return;
		}
		System.out.println("Comparing trees ...");
		if(true == createdTrees.get(firstTree).compareTrees(createdTrees.get(secondTree)))
		{
			System.out.println("Trees are identical");
		}
	}
}

class treeNode
{
	private int dataInt;
	private treeNode leftChild;
	private treeNode rightChild;
	
	public treeNode() {}
	
	public treeNode(treeNode tNode)
	{
		dataInt = tNode.dataInt;
		leftChild = tNode.leftChild;
		rightChild = tNode.rightChild;
	}
	public int getDataInt()
	{
		return dataInt;
	}
	public void setDataInt(int newDataInt)
	{
		dataInt = newDataInt;
	}
	public treeNode getLeftChild()
	{
		return leftChild;
	}
	public void setLeftChild(treeNode newLeftChild)
	{
		leftChild = newLeftChild;
	}
	public treeNode getRightChild()
	{
		return rightChild;
	}
	public void setRightChild(treeNode newRightChild)
	{
		rightChild = newRightChild;
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
//				System.out.println("RightChild added");
				rightChild = newNode;
				return true;
			}
			else
			{
//				System.out.println("Looking at rightChild");
				return (rightChild.insertNode(newNode));
			}
		}
		else // (dataInt > newNode.dataInt)
		{
			if(null == leftChild)
			{
//				System.out.println("LightChild added");
				leftChild = new treeNode(newNode);
				return true;
			}
			else
			{
//				System.out.println("Looking at leftChild");
				return (leftChild.insertNode(newNode));
			}
		}
	}
	treeNode findNode(int findData)
	{
		if(dataInt == findData)
		{
			return this;
		}
		else if(dataInt < findData)
		{
			if(null == leftChild)
			{
				return null;
			}
			else
			{
				return leftChild.findNode(findData);
			}
		}
		else // (dataInt > findData)
		{
			if(null == rightChild)
			{
				return null;
			}
			else
			{
				return rightChild.findNode(findData);
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
	int countChildren(int childCount)
	{
		if(null != leftChild)
		{
			childCount = leftChild.countChildren(childCount);
		}
		if(null != rightChild)
		{
			childCount = rightChild.countChildren(childCount);
		}
		return (childCount+1);
	}
	void copyNode(treeNode oldNode)
	{
		dataInt = oldNode.dataInt;
		leftChild = null;
		rightChild = null;
		if(null != oldNode.leftChild)
		{
			leftChild = new treeNode(oldNode.leftChild);
			leftChild.copyNode(oldNode.leftChild);
		}
		if(null != oldNode.rightChild)
		{
			rightChild = new treeNode(oldNode.rightChild);
			rightChild.copyNode(oldNode.rightChild);
		}
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
	boolean compareNodes(treeNode compNode)
	{
		if(dataInt != compNode.dataInt)
		{
			System.out.println("Nodes are not the same "+dataInt+"!="+compNode.dataInt);
			return false;
		}
		if((null != leftChild) && (null != compNode.leftChild))
		{
			return leftChild.compareNodes(compNode.leftChild);
		}
		else if(((null == leftChild) && (null != compNode.leftChild)) ||
				((null != leftChild) && (null == compNode.leftChild)))
		{
			System.out.println("Nodes are not the same (left children differ) for node "+dataInt);
			return false;
		}
		if((null != rightChild) && (null != compNode.rightChild))
		{
			return rightChild.compareNodes(compNode.rightChild);
		}
		else if(((null == rightChild) && (null != compNode.rightChild)) ||
				((null != rightChild) && (null == compNode.rightChild)))
		{
			System.out.println("Nodes are not the same (right children differ) for node "+dataInt);
			return false;
		}
		return true;
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
	treeNode getTreeRoot()
	{
		return treeRoot;
	}
	boolean insertNode(treeNode newNode)
	{
		if(null == treeRoot)
		{
//			System.out.println("EmptyTree");
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
			if(treeRoot.getDataInt() == oldNode.getDataInt())
			{
				treeNode tmpNode = new treeNode();
				tmpNode.setDataInt(0);
				tmpNode.setLeftChild(treeRoot);
				boolean delResult = treeRoot.deleteNode(oldNode, tmpNode);
				treeRoot = tmpNode.getLeftChild();
				return delResult;
			}
			else
			{
				return treeRoot.deleteNode(oldNode, null);
			}
		}
	}
	void traverseTree(boolean traverseInorder, int startNode, int endNode)
	{
		String outputStr = "";
		if(startNode > countNodes())
		{
			System.out.println("Invalid node for Inorder traversal");
			return;
		}
		else
		{
			if(true == traverseInorder)
			{
				outputStr = treeRoot.traverseInorder(outputStr);
			}
			else
			{
				outputStr = treeRoot.traversePostorder(outputStr);
			}
			
			StringTokenizer strTokened = new StringTokenizer(outputStr, ", ");
			String tmpStr = "";
			int visitCount = 1;
//			System.out.println("Number of Tokens: " + strTokened.countTokens());
			while(true == strTokened.hasMoreElements())
			{				
				if((startNode <= visitCount) && (visitCount <= endNode))
				{
					tmpStr = tmpStr + strTokened.nextElement();
					tmpStr = tmpStr + ", ";
//					System.out.println("visitCount =" + visitCount + "; startNode=" + startNode + "; endNode=" + endNode);
				}
				else
				{
					strTokened.nextElement();
				}
				visitCount++;
			}
			outputStr = tmpStr;
		}
		if(true == traverseInorder)
		{
			System.out.println("Inorder: " + outputStr);
		}
		else
		{
			System.out.println("Postorder: " + outputStr);
		}
	}
	int countLeaves()
	{
		if(null == treeRoot)
		{
			System.out.println("Error: Empty Tree");
			return -1;
		}
		else
		{
			int leafCount = 0;
			leafCount = treeRoot.countLeaf(leafCount);
			if(0 < leafCount)
			{
				return leafCount;
			}
			else
			{
				System.out.println("Error counting leaves");
				return -1;
			}
		}
	}
	int countNodes()
	{
		if(null == treeRoot)
		{
			return 0;
		}
		else
		{
			int nodeCount = 0;
			nodeCount = treeRoot.countChildren(nodeCount);
			if(0 < nodeCount)
			{
				return nodeCount;
			}
			else
			{
				System.out.println("Error counting nodes");
				return -1;
			}
		}
	}
	boolean copyTree(treeClass oldTree)
	{
		if(null == oldTree.treeRoot)
		{
			System.out.println("EmptyTree");
			return false;
		}
		else
		{
			treeRoot = new treeNode(oldTree.treeRoot);
			treeRoot.copyNode(oldTree.treeRoot);
			return true;
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
	boolean compareTrees(treeClass treeComp)
	{
		if((null == treeRoot) || (null == treeComp.treeRoot))
		{
			System.out.println("Error: One or both trees empty");
			return false;
		}
		return treeRoot.compareNodes(treeComp.treeRoot);
	}
	int pickNode(Scanner lineReader)
	{
		boolean endLoop = false;
		String choiceStr = "";
		int intChoice = -1;
		int maxNodes = countNodes();
			
		if(null == treeRoot)
		{
			System.out.println("Empty Tree");
			return -1;
		}
		if(-1 == maxNodes)
		{
			System.out.println("Error picking node");
		}
		do
		{
			System.out.println("Which node to display?: (0 for entire tree, 1 to " + maxNodes + ")");
			choiceStr = lineReader.nextLine();
			
			if(false == choiceStr.matches("[+-]?\\d"))
				{
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
				
				intChoice = Integer.parseInt(choiceStr);
				
				if((0 <= intChoice) && (intChoice <= maxNodes))
				{
					endLoop = true;
				}
				else
				{
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
			}while(false == endLoop);
			
			return (intChoice);
		}
}