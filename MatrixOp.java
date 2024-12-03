package matrix;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
public class MatrixOp {
	public static final String RESET = "\u001B[0m";
	public static final String RED  = "\u001B[31m";
	public static final String BLUE  = "\u001B[34m";
	public static final String GREEN  = "\u001B[32m";
    private static final String fpath = System.getProperty("user.home") + File.separator + "variablesm.txt";
    private static final String helppath = "C:\\Users\\HP\\eclipse-workspace\\MatrixIT\\src\\helpm.txt";
    
    public static void readMatrixFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fpath))) {
            String line;
            while ((line = br.readLine()) != null) {
                executeCommand(line,null);
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    

    public static void executeCommand(String command,Scanner scanner) {
//    	String s = "[a-zA-Z]+ *= *\\[[0-9 ]+(?:;[0-9 ]+)*\\]";
    	String s = "[a-zA-Z]+ *= *\\[ *[-0-9 ]+(?:; *[-0-9 ]+)* *\\]";
    	String sqr="sqr +[a-zA-Z]+";
    	String add="add +[a-zA-z]+ +[a-zA-Z]+";
    	String sub="sub +[a-zA-z]+ +[a-zA-Z]+";
    	String mul="mul +[a-zA-z]+ +[a-zA-Z]+";
    	String inv="inv +[a-zA-Z]+";
    	String extsub="extsub +[a-zA-Z]+";
    	String det="det +[a-zA-Z]+";
    	String rank="rank +[a-zA-Z]+";
    	String RE="RE +[a-zA-Z]+";
    	String trans="trans +[a-zA-Z]+";
    	String calsprs="calsprs +[a-zA-Z]+";
    	String eig="eig +[a-zA-Z]+";
    	String c="[a-zA-Z]+";
        if (Pattern.matches(s, command)) {
            createMatrix(command);
        }else if (Pattern.matches(sqr, command) ) {
            squareMatrix(command);
        } else if (Pattern.matches(mul, command)) {
            multiplyMatrices(command);
        } else if (Pattern.matches(calsprs, command)) {
        	calculateSparsity(command);
        } else if (Pattern.matches(extsub, command)) {
        	extractSub(command,scanner);
        } else if (Pattern.matches(sub, command)) {
        	subtractMatrices(command);
        } else if (Pattern.matches(add, command)) {
        	additionMatrices(command);
        } else if (Pattern.matches(det, command)) {
        	calculateDeterminant(command);
        } else if (Pattern.matches(inv, command)) {
        	calculateInverse(command);
        } else if (Pattern.matches(trans, command)) {
        	calculateTranspose(command);
        } else if (Pattern.matches(eig, command)) {
        	calculateEigenvalue(command,scanner);
        } else if (Pattern.matches(RE, command)) {
        	calculateRowechelon(command);
        } else if (Pattern.matches(rank, command)) {
        	calculateRank(command);
        } else if(command.startsWith("help")) {
        	printHelp(command);
        } else if (Pattern.matches(c,command)){
        	if(!Pattern.matches("help",command)) {
        		printMatrix(command);
        	}
        } else {
            System.out.println("Unknown command: " + command);
            System.out.println(BLUE+"Create matrix---- name= [ a00 a01 a02....a0n ; a10 a11 a12..;...;a0n a1n ....ann]"+RESET);
        	System.out.println(RED+"Type help and then any of the following: "+RESET);
        	System.out.println(GREEN+"create, add, sub, mul, square, transpose, determinant, \ninverse, sparsity, rank, eigen, row echelon, extract..."+RESET);
            
        }
    }

    private static void createMatrix(String command) {
        try {
            String[] parts = command.split("=", 2);
            String matName = parts[0].trim();
            String data = parts[1].trim().replaceAll("[\\[\\]]", "");
            Matrix m = Matrix.fromString(data);
            writeMatrixToFile(matName, m);
            System.out.println("Matrix " + matName + " created:");
            m.print();
        } catch (Exception e) {
            System.err.println(RED+"An error occurred: Try creating Matrix again or type help"+RESET);
        }
    }
    private static void printMatrix(String command) {
    	String mat = command;
    	Matrix m = readMatrixFromFile(mat);
    	if(m!=null) {
    		m.print();
    	}
    	else {
    		System.out.println(BLUE+"Create matrix---- name= [ a00 a01 a02....a0n ; a10 a11 a12..;...;a0n a1n ....ann]"+RESET);
    		System.out.println("Example:	 b=[1 1; 2 2; 3 3]");
        	System.out.println(RED+"Type help and then any of the following: "+RESET);
        	System.out.println(GREEN+"create, add, sub, mul, square, transpose, determinant, inverse, sparsity, rank, eigen, row echelon, extract..."+RESET);
        	
    	}
    }
    private static void printHelp(String command) {
    	String[] a=command.split(" ",2);
    	if(a.length>=2) {
    	String helpName=a[1].trim();
    	String line = readHelpFromFile(helpName);
    	if(line!=null) {
    		System.out.println(line);
    	}

    	else {
    	System.out.println(BLUE+"Create matrix---- name= [ a00 a01 a02....a0n ; a10 a11 a12..;...;a0n a1n ....ann]"+RESET);
    	System.out.println(RED+"Type help and then any of the following: "+RESET);
    	System.out.println(GREEN+"create, add, sub, mul, square, transpose, determinant, inverse, sparsity, rank, eigen, rowechelon, extract..."+RESET);
    	}
    	
    }
    	else {
        	System.out.println(BLUE+"Create matrix---- name= [ a00 a01 a02....a0n ; a10 a11 a12..;...;a0n a1n ....ann]"+RESET);
        	System.out.println(RED+"Type help and then any of the following: "+RESET);
        	System.out.println(GREEN+"create, add, sub, mul, square, transpose, determinant, inverse, sparsity, rank, eigen, rowechelon, extract..."+RESET);
        	}
   }
    

    private static void squareMatrix(String command) {
        try {
            String matName = command.substring(4, command.length()).trim();
            Matrix m=readMatrixFromFile(matName);
            if (m != null) {
                Matrix result = Matrix.multiply(m, m);
                System.out.println("Matrix " + matName + " squared:");
                result.print();
            }
            else {
                System.out.println(RED+matName + " is not recognized"+RESET);
            }
        } catch (Exception e) {
            System.out.println(RED+"An error occurred: " + e.getMessage()+RESET);
            e.printStackTrace();
        }
    }

    private static void multiplyMatrices(String command) {
        try {
            String s = command.substring(4, command.length()).trim();
            String[] matnames = s.split(" ");
            if (matnames.length != 2) {
                System.out.println(RED+"Invalid multiply command format. Use mul matrix1 matrix2 | Check the dimensions"+RESET);
                return;
            }

            String matName1 = matnames[0].trim();
            String matName2 = matnames[1].trim();

            Matrix m1 = readMatrixFromFile(matName1);
            Matrix m2 = readMatrixFromFile(matName2);

            if (m1 == null || m2 == null) {
                System.out.println(RED+"One or both matrices are not recognized"+RESET);
                return;
            }
            Matrix result = Matrix.multiply(m1, m2);
            System.out.println("Result of "+matName1+" multiplied by " + matName2 + ":");
            result.print();
        } catch (Exception e) {
            System.out.println(RED+"An error occurred: Type 'mul matrix1 matrix2' or 'help' | Check the dimensions"+RESET);
        }
    }
    
    private static void subtractMatrices(String command) {
        try {
            String s = command.substring(4, command.length()).trim();
            String[] matnames = s.split(" ");
            if (matnames.length != 2) {
                System.out.println(RED+"Invalid subtraction command format. Use 'sub matrix1 matrix2'"+RESET);
                return;
            }

            String mat1 = matnames[0].trim();
            String mat2 = matnames[1].trim();

            Matrix m1 = readMatrixFromFile(mat1);
            Matrix m2 = readMatrixFromFile(mat2);

            if (m1 == null || m2 == null) {
                System.out.println(RED+"One or both matrices are not recognized"+RESET);
                return;
            }

            Matrix result = Matrix.subMatrices(m1, m2);
            System.out.println("Result of " + mat1 + " - " + mat2 + ":");
            result.print();
        } catch (Exception e) {
            System.out.println(RED+"An error occurred: Use 'sub matrix1 matrix2'| Check the dimensions"+ RESET);
        }
    }
    
    private static void additionMatrices(String command) {
        try {
            String s = command.substring(4, command.length()).trim();
            String[] matnames = s.split(" ");
            if (matnames.length != 2) {
                System.out.println(RED+"Invalid additon command format. Use add matrix1 matrix2 | Check the dimensions"+RESET);
                return;
            }

            String mat1 = matnames[0].trim();
            String mat2 = matnames[1].trim();

            Matrix m1 = readMatrixFromFile(mat1);
            Matrix m2 = readMatrixFromFile(mat2);

            if (m1 == null || m2 == null) {
                System.out.println(RED+"One or both matrices are not recognized"+RESET);
                return;
            }

            Matrix result = Matrix.addMatrices(m1, m2);
            System.out.println("Result of " + mat1 + " + " + mat2 + ":");
            result.print();
        } catch (Exception e) {
            System.out.println("An error occurred: Use 'add matrix1 matrix2'| Check the dimensions"+RESET);
        }
    }
    
    private static void calculateSparsity(String command) {
    	try {
    		String s = command.substring(8, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            double a=Matrix.sparseMatrix(m);
            System.out.println("Sparsity of the matrix is : "+ a*100 +"%");
    	}
    	catch (Exception e) {
    		System.out.println("An error occured: Use 'calsprs matrix'"+ RESET);
    	}
    }
    private static void calculateRank(String command) {
    	try {
    		String s = command.substring(5, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            int a=Matrix.rank(m);
            System.out.println("Rank of the matrix is : "+ a);
    	}
    	catch (Exception e) {
    		System.out.println("An error occured: ' Use rank matrix '"+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateEigenvalue(String command,Scanner scanner) {
    	try {
    		String s = command.substring(4, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            System.out.println("Enter dimension of matrix: ");
	        int n=scanner.nextInt();
	        double[] initialVector = new double[n];

	        for (int i = 0; i < n; i++) {
	          initialVector[i] = 1;
	        }
	        int maxIter = 1000;
	        double precision = 1e-6;
	        double eigen=Matrix.powerIteration(m, initialVector, maxIter, precision);
	        System.out.print("Dominant Eigen: "+ eigen);
	        
    	}
    	catch (Exception e) {
    		System.out.println(RED+"An error occured: Use 'eig matrix'"+ RESET);
    	}
    }
    private static void calculateTranspose(String command) {
    	try {
    		String s = command.substring(6, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            Matrix a=Matrix.transpose(m);
            System.out.println("Transpose of the matrix is : ");
            a.print();
    	}
    	catch (Exception e) {
    		System.out.println(RED+"An error occured: Use 'trans matrix'"+RESET);
    	}
    }
    private static void calculateRowechelon(String command) {
    	try {
    		String s = command.substring(3, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            Matrix a=Matrix.toRowEchelonForm(m);
            System.out.println("Rowechelon form of the matrix is : ");
            a.print();
    	}
    	catch (Exception e) {
    		System.out.println("An error occured: Use 'RE matrix'"+ e.getMessage());
    	}
    }
    private static void calculateDeterminant(String command) {
    	try {
    		String matrixName = command.substring(4, command.length()).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            double a=Matrix.determinant(matrix);
            System.out.println("Determinant of the matrix is : "+ a);
    	}
    	catch (Exception e) {
    		System.out.println(RED+"An error occured: Use 'det matrix'| Check the matrix again"+RESET);
    	}
    }
    private static void calculateInverse(String command) {
    	try {
    		String matrixName = command.substring(4, command.length()).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            Matrix a=Matrix.invert(matrix);
            System.out.println("Inverse of the matrix is : " );
            a.print();
    	}
    	catch (Exception e) {
    		System.out.println(RED+"An error occured: Use 'inv matrix' | Matrix is Singular or not Square"+RESET);
    	}
    }
    private static void extractSub(String command,Scanner scanner) {
        try {
            String s = command.substring(7, command.length()).trim();
            Matrix m = readMatrixFromFile(s);
            if (m == null) {
                System.out.println(s + " is not recognized");
                return;
            }
            System.out.println("Insert Start row and end row:");
            int startRow = scanner.nextInt();
            int endRow = scanner.nextInt();
            System.out.println("Insert Start col and end col:");
            int startCol = scanner.nextInt();
            int endCol = scanner.nextInt();

            int numRows = endRow - startRow + 1;
            int numCols = endCol - startCol + 1;
            if (startRow >= 0 && startCol >= 0 && startRow + numRows <= m.r && startCol + numCols <= m.c) {
                Matrix subMatrix = Matrix.extractMatrix(m, numRows, numCols, startRow, startCol);
                System.out.println("Extracted submatrix:");
                subMatrix.print();
            } else {
                System.out.println("Submatrix dimensions exceed original matrix dimensions.");
            }
            
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private static void writeMatrixToFile(String matrixName, Matrix matrix) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fpath, true))) {
            bw.write(matrixName + "=" + matrix.toString());
            bw.newLine();
        }
    }
    private static String readHelpFromFile(String s) {
    	try(BufferedReader br = new BufferedReader(new FileReader(helppath))){
    		String line;
    		while((line=br.readLine())!=null) {
    			if (line.startsWith(s)) {
    				return line;
    			}
    		}
    	}
    	catch (IOException e) {
    		System.err.println("IOException occurred: " + e.getMessage());
            System.out.println("Try Again!");
            
    	}
    	return null;
    }
    private static Matrix readMatrixFromFile(String matrixName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fpath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2 && parts[0].trim().equals(matrixName)) {
                    return Matrix.fromString(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            System.out.println("Try Again!");
        }
        return null;
    }
    
    public static void clearFile(String filepath) {
    	try (FileOutputStream fos = new FileOutputStream(filepath)) {
            System.out.println("Thank you for choosing MatrixIT :)");
            System.out.println("Enter your choice again or exit to quit:");
            System.out.println(" m. Matrix \n t. Tensor \n l.Linear\n Or Then Type help");
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
    }     
}
