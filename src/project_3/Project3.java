package project_3;

import java.util.Vector;

public class Project3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		problem_8();
		problem_9();
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

	static void problem_9()
	{
		
	}
}
