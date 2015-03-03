package project_3;

import java.util.Vector;

public class Project3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Problem 6: ");
		problem_6();
		System.out.println("Problem 7 - (2,8): ");
		problem_7(2,8);
		//System.out.println("Problem 7 - (5,6): ");
		//problem_7(5,6);
		//System.out.println("Problem 7 - (7,5): ");
		//problem_7(7,5);
		System.out.println("Problem 8: ");
		problem_8();
		System.out.println("Problem 9: ");
		System.out.println("Part 1: ");
		problem_9_part1();
		System.out.println("Part 2: ");
		problem_9_part2();
	}
	
	static void problem_6()
	{
		System.out.println(function(5));
	}
	static int function(int n)
	{
		if (n == 0 || n == 1)
		{
			return 1;
		}
		else
		{
			return 2*function(n-2) + 3*function(n-1);
		}
	}
	
	static void problem_7(int p1, int p2)
	{
		if (p1 != p2) 
		{
			p1 = p1 + 2;
			p2 = p2 - 1;
			problem_7(p1,p2);
			System.out.println(p1);
			System.out.println(p2);
		}
	}
	
	static void problem_8()
	{
		Vector<Integer> integerVect = new Vector<Integer>();
		integerVect.add(1);
		integerVect.add(4);
        for(int i = 2; i < 12; i++)
        {
        	integerVect.add(2*(integerVect.elementAt(i-1) - (3*integerVect.elementAt(i-2))));
        	System.out.println(integerVect.elementAt(i));
        }
	}

	static void problem_9_part1()
	{
		
	}

	static void problem_9_part2()
	{
		
	}
}
