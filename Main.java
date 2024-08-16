package main;

import linear.LinearEq;
import matrix.MatrixOp;
import java.util.Scanner;
import tensor.TensorOp;
public class Main {
    public static void main(String[] args) {
    	System.out.println("Welcome to MatrixIT!");
    	System.out.println("Make your choice (or 'exit' to quit): Matrix / Tensor / Linear Equation");
        Scanner scanner = new Scanner(System.in);
        
        String choice = ""; 
        do {
          choice = scanner.nextLine().toLowerCase(); 
          switch (choice) {
            case "matrix":
            	MatrixOp.readMatrixFromFile();
            	while (true) {
            		System.out.print("Enter command for Matrices: ");
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
            			break;
            			}
            		MatrixOp.executeCommand(command, scanner);
            		}
            	break;
            case "linear":
            	while (true) {
            		System.out.print("Enter command Linear Equations: ");
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
            			break;
            			}
            		LinearEq.executeCommand(command,scanner);
            		}
            	break;
            case "tensor":
            	while (true) {
            		System.out.print("Enter command for Tensors: ");
            		String command = scanner.nextLine().trim();
            		if (command.equalsIgnoreCase("exit")) {
            			break;
            			}
            		TensorOp.executeCommand(command,scanner);
            		}
            	break;
           default:
        	   System.out.println("Sorry, Try again!");
        	   break;
    }
  } while (!choice.equals("exit"));
        scanner.close();
    }
}
