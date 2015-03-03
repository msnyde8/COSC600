package project_3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

public class Project3 {

	static String outputName = (System.getProperty("user.dir") + System.getProperty("file.separator") + "project3_output.txt");
	static PrintWriter writer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			writer = new PrintWriter(outputName);
			System.out.println("Problem 6: ");
			writer.write("Problem 6: " + System.getProperty("line.separator"));
			problem_6();
		
			System.out.println("Problem 7 - (2,8): ");
			writer.write("Problem 7 - (2,8): " + System.getProperty("line.separator"));
			problem_7(2,8);
			//System.out.println("Problem 7 - (5,6): ");
			//problem_7(5,6);
			//System.out.println("Problem 7 - (7,5): ");
			//problem_7(7,5);
			System.out.println("Problem 8: ");
			writer.write("Problem 8: " + System.getProperty("line.separator"));
			problem_8();
			System.out.println("Problem 9: " + System.getProperty("line.separator"));
			writer.write("Problem 9: " + System.getProperty("line.separator") + System.getProperty("line.separator"));
			System.out.println("Part 1: ");
			writer.write("Part 1: " + System.getProperty("line.separator"));
			problem_9_part1(10);
			problem_9_part1(100);
			problem_9_part1(1000);
			problem_9_part1(10000);
			problem_9_part1(100000);
			System.out.println(System.getProperty("line.separator") + "Part 2: ");
			writer.write(System.getProperty("line.separator") + "Part 2: " + System.getProperty("line.separator"));
			problem_9_part2(1);
			problem_9_part2(10);
			problem_9_part2(20);
			problem_9_part2(30);
			problem_9_part2(40);
			problem_9_part2(50);
			problem_9_part2(60);

			writer.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Caught Exception: " + e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	static void problem_6()
	{
		int probSix = function(5);
		System.out.println(probSix);
		writer.write(probSix + System.getProperty("line.separator"));
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
			writer.write(p1 + System.getProperty("line.separator"));
			System.out.println(p2);
			writer.write(p2 + System.getProperty("line.separator"));
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
        	writer.write(integerVect.elementAt(i)+ System.getProperty("line.separator"));
        }
	}

	static void problem_9_part1(double num)
	{
		Long startTime= System.currentTimeMillis();
		double probNine1 = sumFunct(num);
		Long endTime = System.currentTimeMillis();

		System.out.println("N = " + num);
		System.out.println("Answer = " + probNine1);
		System.out.println("Elapsed Time = " + (endTime - startTime));
		writer.write("N = " + num + System.getProperty("line.separator"));
		writer.write("Answer = " + probNine1 + System.getProperty("line.separator"));
		writer.write("Elapsed Time = " + (endTime - startTime) + System.getProperty("line.separator"));
	}
	static double sumFunct(double num)
	{
		double sum = 0;
		for(int j=1; j<num; j++)
		{
			sum = sum + j;
		}
		return sum;		
	}

	static void problem_9_part2(double num)
	{
		Long startTime= System.currentTimeMillis();
		double probNine2 = fibonacci(num);
		Long endTime = System.currentTimeMillis();

		System.out.println("N = " + num);
		System.out.println("Answer = " + probNine2);
		System.out.println("Elapsed Time = " + (endTime - startTime));
		//classAlgo(num);
		writer.write("N = " + num + System.getProperty("line.separator"));
		writer.write("Answer = " + probNine2 + System.getProperty("line.separator"));
		writer.write("Elapsed Time = " + (endTime - startTime) + System.getProperty("line.separator"));
	}
	static double fibonacci(double n)
	{
		if (n <= 1)
		{
			return n;
		}
		else
		{
			return (fibonacci(n-1) + fibonacci(n-2)); 
		}
	}
/*	static void classAlgo(double power)
	{
		power = power * 2;
		for(int k = 1; k < power; k++)
		{
			System.out.println(k);
		}
	}*/
}
