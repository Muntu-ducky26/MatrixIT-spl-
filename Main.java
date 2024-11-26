package main;
import java.io.*;
import linear.LinearEq;
import matrix.MatrixOp;
import java.util.Scanner;
import tensor.TensorOp;
public class Main {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED  = "\u001B[31m";
	public static final String ANSI_BLUE  = "\u001B[34m"; 
	public static final String ANSI_GREEN  = "\u001B[32m";
	public static final String ANSI_CYAN  = "\u001B[36m";
	public static final String ANSI_boxred_BACKGROUND  = "\u001B[51m";
	
	

	
    public static void main(String[] args) {

    	System.out.println(ANSI_boxred_BACKGROUND+ANSI_RED+" Welcome to MatrixIT! "+ANSI_RESET);
    	System.out.println(ANSI_BLUE+"Make your choice (or 'exit' to quit): m.Matrix / t.Tensor / l.Linear Equation \nType m, t or l"+ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        
        String choice = ""; 
        do {
          choice = scanner.nextLine().toLowerCase(); 
          switch (choice) {
            case "m":
            	System.out.println("To create Matrix: MatrixName = [ a00 a01 ; b00 b01 ]\n		Example:	a	= 	[1 2; 3 4]");
            	System.out.println("Type help for Details!");
            	while (true) {
            		System.out.print(ANSI_RED+"mat>"+ANSI_RESET);
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
            			String filepath = System.getProperty("user.home") + File.separator + "variablesm.txt";
                		MatrixOp.clearFile(filepath);
            			break;
            			}
            		
            		MatrixOp.executeCommand(command, scanner);
            		}
            	break;
            case "l":
            	while (true) {
            		System.out.print(ANSI_RED+"lin>"+ANSI_RESET);
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
                        System.out.println(ANSI_GREEN+"Thank you for choosing MatrixIT :)"+ ANSI_RESET);
                        System.out.println(ANSI_BLUE+"Enter your choice again or exit to quit:"+ANSI_RESET);
                        System.out.println(ANSI_BLUE+" m. Matrix \n t. Tensor \n l.Linear\n Then Type help"+ANSI_RESET);
            			break;
            			}
            		LinearEq.executeCommand(command,scanner);
            		}
            	break;
            case "t":
            	System.out.println("To create Matrix: MatrixName = [ a000 a001 , a010 a011 ; a100 a101 , a110 a111 ]\n		  Example:	a	= 	[1 2 , 5 6 ; 3 4 , 1 2]");
            	System.out.println("Type help for Details!");
            	while (true) {
            		System.out.print(ANSI_RED+"ten>"+ANSI_RESET);
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
            			String filepath = System.getProperty("user.home") + File.separator + "variablest.txt";
                		TensorOp.clearFile(filepath);
            			break;
            			}
            		
            		TensorOp.executeCommand(command,scanner);
            		}
            	break;
           default:
        	   System.out.println(ANSI_RED+"Sorry, Try again!"+ANSI_RESET);
        	   break;
    }
  } while (!choice.equals("exit"));
        scanner.close();
    }
}
