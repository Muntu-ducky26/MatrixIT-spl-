package main;
import java.io.*;
import linear.LinearEq;
import matrix.MatrixOp;
import java.util.Scanner;
import tensor.TensorOp;
public class Main {
	public static final String RESET = "\u001B[0m";
	public static final String RED  = "\u001B[31m";
	public static final String BLUE  = "\u001B[34m"; 
	public static final String GREEN  = "\u001B[32m";
	public static final String CYAN  = "\u001B[36m";
	public static final String ANSI_boxred_BACKGROUND  = "\u001B[51m";
	
	

	
    public static void main(String[] args) {

    	System.out.println(ANSI_boxred_BACKGROUND+RED+" Welcome to MatrixIT! "+RESET);
    	System.out.println(BLUE+"Make your choice (or 'exit' to quit): m.Matrix / t.Tensor / l.Linear Equation \nType m, t or l"+RESET);
        Scanner scanner = new Scanner(System.in);
        
        String ch = ""; 
        do {
          ch = scanner.nextLine().toLowerCase(); 
          switch (ch) {
            case "m":
            	System.out.println("To create Matrix: MatrixName = [ a00 a01 ; b00 b01 ]\n		Example:	a	= 	[1 2; 3 4]");
            	System.out.println("Type help for Details!");
            	while (true) {
            		System.out.print(RED+"mat>"+RESET);
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
            	System.out.println("Perform Operations or simply Type 'help");
            	while (true) {
            		System.out.print(RED+"lin>"+RESET);
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
                        System.out.println(GREEN+"Thank you for choosing MatrixIT :)"+ RESET);
                        System.out.println(BLUE+"Enter your choice again or exit to quit:"+RESET);
                        System.out.println(BLUE+" m. Matrix \n t. Tensor \n l.Linear\n Then Type help"+RESET);
            			break;
            			}
            		LinearEq.executeCommand(command,scanner);
            		}
            	break;
            case "t":
            	System.out.println("To create Matrix: MatrixName = [ a000 a001 , a010 a011 ; a100 a101 , a110 a111 ]\n		  Example:	a	= 	[1 2 , 5 6 ; 3 4 , 1 2]");
            	System.out.println("Type help for Details!");
            	while (true) {
            		System.out.print(RED+"ten>"+RESET);
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
        	   System.out.println(RED+"Sorry, Try again!"+RESET);
        	   break;
    }
  } while (!ch.equals("exit"));
        scanner.close();
    }
}
