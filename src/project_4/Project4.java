package project_4;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Project4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		LinkedList storedData = new LinkedList();
		String textStr = "";
		Scanner reader = new Scanner(System.in);
		Boolean end = false;
		/*try
		{*/
			System.out.println("Please enter a command or a line of text");
			while((false == end) && reader.hasNextLine())
			{
				textStr = reader.nextLine();
				switch(textStr)
				{
					case "$insert":
						System.out.println("Insert statement found!");
						break;
					case "$delete":
						System.out.println("Delete statement found!");
						break;
					case "$print":
						System.out.println("Print statement found!");
						break;
					case "$line":
						System.out.println("Line statement found!");
						break;
					case "$search":
						System.out.println("Search statement found!");
						break;
					case "$done":
						System.out.println("Thank you and have a nice day!");
						end = true;
						reader.close();
						break;
					default:
						System.out.println("Unuseful!!");
						break;
				}
				
			}
			/*
		}
		catch(IOException e)
		{
			System.out.println("Caught IOException: " + e.getMessage());
        	e.printStackTrace();
		}
	*/
	}
}
