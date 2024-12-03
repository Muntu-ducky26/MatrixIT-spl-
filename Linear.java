package linear;
import java.util.Scanner;

public class Linear {
	public static double f(double x,int p,int q,int r,int s) {
	       
        return p*Math.pow(x, 3)+q*Math.pow(x, 2)+r*x+s;
        
    }
	public static double fbar(double x, int p1, int q1, int r1) {
        return p1 * Math.pow(x, 2) + q1 * x + r1;
    }
    public static void findBisection(double a, double b,int p,int q,int r,int s) {
    	if (f(a, p, q, r, s) * f(b, p, q, r, s) >= 0) {
            System.out.println("Invalid initial interval. The function should have opposite signs at the endpoints a and b.");
            System.out.println("Use alternatives like Newton Raphson, Secant etc.");
            return;
        }
    	
        double c = (a + b) / 2.0;
        System.out.println("Here, c = (a + b) / 2 = "+c);
        int flag=0;

        while (Math.abs(f(c,p,q,r,s)) >= 0.01) {
        	flag++;
        	System.out.println("In iteration "+ flag+":");
        	System.out.println("The value of f(a)= "+f(a,p,q,r,s)+", f(c=)"+f(c,p,q,r,s));
        	System.out.println("The value of f(a)*f(c)= "+f(a,p,q,r,s)*f(c,p,q,r,s));
            if (f(a,p,q,r,s) * f(c,p,q,r,s) < 0) {
            	System.out.println("The value of f(a)*f(c)<0 , so b=c");
                b = c;
            } else {
            	System.out.println("The value of f(a)*f(c)>0 , so a=c");
                a = c;
            }
            c = (a + b) / 2.0;
            
            System.out.println(" Iteration value of c is: "+c);
            System.out.println();
            if(flag==1000) break;
        }

        System.out.printf("%.6f\n",c);
        
    }
    public static void Secant(double x0, double x1, int p, int q, int r, int s) {
        double x2;
        int flag = 0;

        while (Math.abs(f(x1, p, q, r, s)) >= 0.01) {//close to zero
            flag++;
            System.out.println("In iteration " + flag + ":");
            System.out.println("The value of f(x0) = " + f(x0, p, q, r, s));
            System.out.println("The value of f(x1) = " + f(x1, p, q, r, s));
            x2 =x1-f(x1, p, q, r, s) * (x1 - x0) / (f(x1, p, q, r, s) - f(x0, p, q, r, s));
            System.out.println("Iteration value of x is: " + x2);
            System.out.println();
            x0 = x1;//Replacing values
            x1 = x2;
            if(flag==1000)break;
        }

        System.out.printf("%.6f\n", x1);
    }
    public static void NewtonRaphson(double x0, int p, int q, int r, int s, int p1, int q1, int r1) {
        double x1;
        int flag = 0;
        System.out.println("Here, iteration general formula is , X_i = X_(i-1) - f(X_i)/f'(X_i)");
        while (Math.abs(f(x0, p, q, r, s)) >= 0.01) {
            flag++;
            System.out.println("In iteration " + flag + ":");
            System.out.println("The value of f(x0) = " +f(x0, p, q, r, s));
            System.out.println("The value of f'(x0) = " +fbar(x0, p1, q1, r1));
            
            x1 =x0-f(x0, p, q, r, s) / fbar(x0, p1, q1, r1);
            
            System.out.println("Iteration value of x is: " + x1);
            System.out.println();
            x0 = x1;
            if(flag==100)break;
        }
        
        System.out.printf("%.6f\n", x0);
    }
    public static void GaussJordan(int n) {
    	int i,j,k;
    	Scanner s1=new Scanner(System.in);
    	double arr[][]= new double[50][51];
    	for (i=0; i<n; i++) {
            for (j=0; j<=n; j++) {
            	double p=s1.nextDouble();
                arr[i][j]= p;
            }
        }
        for (i=0; i<n; i++) {
            if (arr[i][i] == 0) {
                int m = i + 1;//swap
                while (m < n && arr[m][i] == 0) {
                    m++;
                }
                if(m == n){
                    System.out.println("System is either inconsistent or has infinite solutions.");
                    return;
                }
                double[] temp = arr[i];
                arr[i] = arr[m];
                arr[m] = temp;
                System.out.println("Swapped row " + (i + 1) + " with row " + (m + 1));
                printMatrix(arr, n);
            }
            double temp = arr[i][i];
            System.out.println("Divide row " + (i + 1) + " by " + temp);
            for (j=0; j<=n; j++) {
                arr[i][j] = arr[i][j]/temp;
            }
            printMatrix(arr,n);
            
   
            for (j=0; j<n; j++) {
                if (i==j) continue;
                temp = arr[j][i];
                System.out.println("Subtract " + temp + " * row " + (i + 1) + " from row " + (j + 1));

            for (k=0; k<=n; k++) {
                  arr[j][k] -= temp*arr[i][k];
                }
            printMatrix(arr, n);
            }

        }
        
        for (i = 0; i < n; i++) {
            boolean zeros = true;
            for (j = 0; j < n; j++) {
                if (arr[i][j] != 0) {
                    zeros = false;
                    break;
                }
            }
            if (zeros && arr[i][n] != 0) {
                System.out.println("System is inconsistent. No solution.");
                return;
            }
        }
        
        System.out.println("Solution is: ");
        for (i=0; i<n; i++) {
            System.out.printf("%.2f \t", arr[i][n]);
        }
        System.out.println();
    	
    }
    
    public static void findFalsePosition(double a, double b, int p, int q, int r, int s) {

        
        if (f(a, p, q, r, s) * f(b, p, q, r, s) >= 0) {
            System.out.println("Invalid initial intervals. The function should have opposite signs at the endpoints a and b");
            System.out.println("Use alternatives like Newton Raphson, Secant etc.");
            return;
        }

        double c=a;
        int flag = 0;
        System.out.println("Here, c= a - (f(a) * (b - a)) / (f(b) - f(a) ");
        while (true) {
            c= a-(f(a, p, q, r, s)*(b-a))/(f(b, p, q, r, s)-f(a, p, q, r, s));
            flag++;
            System.out.println("In iteration " + flag + ":");
            System.out.println("The value of f(a)= "+f(a, p, q, r, s)+", f(c)= "+f(c, p, q, r, s));
            System.out.println("The value of f(a) * f(c)= " + f(a, p, q, r, s)*f(c, p, q, r, s));
            if (Math.abs(f(c, p, q, r, s)) < 0.01) {
                break;
            }
            if (f(a, p, q, r, s) * f(c, p, q, r, s) < 0) {
                System.out.println("The value of f(a) * f(c) < 0, so b = c ");
                b=c;
            } else {
                System.out.println("The value of f(a) * f(c) > 0, so a = c ");
                a = c;
            }
            System.out.println("Iteration value of c is: " + c);
            System.out.println();
            if(flag==1000)break;
        }
        System.out.printf("%.6f\n", c);
    }
    public static void printMatrix(double[][] matrix, int n) {
        System.out.println("Current state of Matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.printf("%.2f \t", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void GaussSeidel(int n) {
    	Scanner s2=new Scanner(System.in);
    	double sol2[]=new double[50];
    	double sol[]=new double [50];//to store the solution
    	double arr[][]=new double [50][51];//initial array allocation
        double precision = 0.0001;//tolerance 
        int i, j, k;
        for (i=0; i<n; i++) {
            for (j=0; j<=n; j++) {
            	double w=s2.nextDouble();
                arr[i][j]=w;
            }
        }
        if (!checkGaussseidel(arr, n)) {
            System.out.println("The Gauss Seidel method is not be applied for converging equation.");
            return;
        }
        System.out.println("Initial Guess for the equation : ");
        for (i=0; i<n; i++) {
        	double x=s2.nextDouble();
            sol[i]=x;
        }
        while (true) {
            for (i=0; i<n; i++) {
            	sol2[i] = sol[i];  	
            }
            for (j=0; j<n; j++) {
                double temp = arr[j][n];
                for (k=0; k<n; k++) {
                    if (j==k) continue;
                    temp = temp-arr[j][k]*sol[k];    
                }
                sol[j] = temp / arr[j][j];
                if(j==0) {System.out.println("Value of x in iteration = ");}
                else if(j==1) {System.out.println("Value of y in iteration = ");}
                else{System.out.println("Value of z in iteration = ");}
                System.out.println(sol[j]);
               System.out.println();
            }
            int flag = 0;
            for (i=0; i<n; i++) {
                if (Math.abs(sol2[i]-sol[i]) > precision) flag = 1;
            }
            if (flag == 0) break;
        }
        System.out.println("Result is ");
        for (i=0; i<n; i++) {
        System.out.printf("%.2f \t", sol[i]);
        }
    }
    public static boolean checkGaussseidel(double[][] matrix, int n) {
        for (int i=0; i<n; i++) {
            double sum=0;
            for (int j=0; j<n; j++) {
                if (i!=j){
                    sum+= Math.abs(matrix[i][j]);
                }}
            if (Math.abs(matrix[i][i])<sum){
                return false;
            }
        }
        return true;
    }   
    
}
