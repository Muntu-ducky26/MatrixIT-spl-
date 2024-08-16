package linear;
import java.util.Scanner;
public class LinearEq {
	public static void executeCommand(String command,Scanner scanner) {
        if (command.startsWith("bis(") && command.endsWith(")")) {
            calculateBisection(command,scanner);
        }
        else if (command.startsWith("gjor(") && command.endsWith(")")) {
        	calculateGaussjordan(command,scanner);
        }
        else if (command.startsWith("gsei(") && command.endsWith(")")) {
        	calculateGaussseidel(command,scanner);
        } 
        else if (command.startsWith("fp(") && command.endsWith(")")) {
        	calculateFalseposition(command,scanner);
        }
        else if (command.startsWith("NR(") && command.endsWith(")")) {
        	calculateNewtonrhapson(command,scanner);
        }
        else if (command.startsWith("scnt(") && command.endsWith(")")) {
        	calculateSecant(command,scanner);
        }
        else if (command.startsWith("hel") && command.endsWith("p")) {
        	printHelp(command);
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }



private static void calculateBisection(String command, Scanner scanner) {
    System.out.println("Enter the limit lower and higher: a and b :");
    double a = scanner.nextDouble();
    double b = scanner.nextDouble();
    System.out.println("Enter the coefficients of x^3, x^2, x and the constant term:");
    int p = scanner.nextInt();
    int q = scanner.nextInt();
    int r = scanner.nextInt();
    int s = scanner.nextInt();
    Linear.findBisection(a,b,p,q,r,s);
    }

private static void calculateNewtonrhapson(String command, Scanner scanner) {
	System.out.print("Enter initial guess (x0): ");
    double x0 = scanner.nextDouble();
    System.out.print("Enter the coefficients off(x)| x^3, x^2, x and the constant term: ");
    int p = scanner.nextInt();
    int q = scanner.nextInt();
    int r = scanner.nextInt();
    int s = scanner.nextInt();
    System.out.print("Enter the coefficients of f'(x)| x^2, x and the constant term: ");
    int p1 = scanner.nextInt();
    int q1 = scanner.nextInt();
    int r1 = scanner.nextInt();
    Linear.NewtonRaphson(x0,p,q,r,s,p1,q1,r1);
    }
private static void calculateSecant(String command, Scanner scanner) {
	System.out.print("Enter initial guess (x0,x1): ");
    double x0 = scanner.nextDouble();
    double x1 = scanner.nextDouble();
    System.out.print("Enter the coefficients off(x)| x^3, x^2, x and the constant term: ");
    int p = scanner.nextInt();
    int q = scanner.nextInt();
    int r = scanner.nextInt();
    int s = scanner.nextInt();
    Linear.Secant(x0,x1,p,q,r,s);
    }
private static void calculateFalseposition(String command, Scanner scanner) {
    System.out.println("Enter the limit lower and higher: a and b :");
    double a = scanner.nextDouble();
    double b = scanner.nextDouble();
    System.out.println("Enter the coefficients of x^3, x^2, x and the constant term:");
    int p = scanner.nextInt();
    int q = scanner.nextInt();
    int r = scanner.nextInt();
    int s = scanner.nextInt();
    Linear.findFalsePosition(a,b,p,q,r,s);
    }
private static void calculateGaussjordan(String command, Scanner scanner) {
	System.out.println("Enter the value of unknowns: ");
	int n=scanner.nextInt();
	Linear.GaussJordan(n);
}
private static void calculateGaussseidel(String command, Scanner scanner) {
	System.out.println("Enter the value of unknowns: ");
	int n=scanner.nextInt();
	Linear.GaussSeidel(n);
}
private static void printHelp(String command) {
    System.out.println("Bisection method = bis()");
    System.out.println("Gauss-Jordan method = gjor()");
    System.out.println("Gauss-Seidel method = gsei()");
    System.out.println("False Position method = fp()");
    System.out.println("Newton-Raphson method = NR()");
    System.out.println("Secant method = scnt()");
    }

}