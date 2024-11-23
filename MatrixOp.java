package matrix;

import java.io.*;
import java.util.*;
public class MatrixOp {
//    private static final String filepath = System.getProperty("user.home") + File.separator + "matrices.txt";
	private static final String filepath = "C:\\Users\\HP\\eclipse-workspace\\MatrixIT\\src\\variables.txt";
    
    public static void readMatrixFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
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
        if (command.contains("=")) {
            createMatrix(command);
        } else if (command.startsWith("sqrt(") && command.endsWith(")")) {
            squareMatrix(command);
        } else if (command.startsWith("mul(") && command.endsWith(")")) {
            multiplyMatrices(command);
        } else if (command.startsWith("calsprs(") && command.endsWith(")")) {
        	calculateSparsity(command);
        } else if (command.startsWith("extsub(") && command.endsWith(")")) {
        	extractSub(command,scanner);
        } else if (command.startsWith("sub(") && command.endsWith(")")) {
        	subtractMatrices(command);
        } else if (command.startsWith("add(") && command.endsWith(")")) {
        	additionMatrices(command);
        } else if (command.startsWith("det(") && command.endsWith(")")) {
        	calculateDeterminant(command);
        } else if (command.startsWith("inv(") && command.endsWith(")")) {
        	calculateInverse(command);
        } else if (command.startsWith("trans(") && command.endsWith(")")) {
        	calculateTranspose(command);
        } else if (command.startsWith("eig(") && command.endsWith(")")) {
        	calculateEigenvalue(command,scanner);
        } else if (command.startsWith("RE(") && command.endsWith(")")) {
        	calculateRowechelon(command);
        } else if (command.startsWith("rank(") && command.endsWith(")")) {
        	calculateRank(command);
        } else if (command.startsWith("hel") && command.endsWith("p")) {
        	printHelp(command);
        }
          else {
            System.out.println("Unknown command: " + command);
        }
    }

    private static void createMatrix(String command) {
        try {
            String[] parts = command.split("=", 2);
            String matrixName = parts[0].trim();
            String matrixData = parts[1].trim().replaceAll("[\\[\\]]", "");
            Matrix matrix = Matrix.fromString(matrixData);
            writeMatrixToFile(matrixName, matrix);
            System.out.println("Matrix " + matrixName + " created:");
            matrix.print();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void printHelp(String command) {
    	System.out.println("Create matrix---- name= [ a00 a01 a02....a0n ; a10 a11 a12..;...;a0n a1n ....ann]");
    	System.out.println();
    	System.out.println("Addition           = add(Matrix A, Matrix B)");
    	System.out.println("Subtraction        = sub(Matrix A, Matrix B) || Note:Matrix B to be subtracted from A");
    	System.out.println("Multiplication     = mul(Matrix A, Matrix B) || Note:A*B not B*A");
    	System.out.println("Square of matrix   = sqrt(Matrix name)");
    	System.out.println("Transpose          = trans(Matrix name )");
    	System.out.println("Determinant        = det(Matrix name)");
    	System.out.println("Inverse matrix     = inv(Matrix name)");
    	System.out.println("Calculate sparsity = calsprs(Matrix name)");
    	System.out.println("Rank of matrix     = rank(Matrix name)");
    	System.out.println("Eigenvalue calculation(Dominant value)= eig(Matrix name)");
    	System.out.println("Rowchelon form     = RE(matrix name)");
    	System.out.println("Extract sub matrix = extsub(matrix name)");
    	System.out.println();
    	System.out.println();
    }
    

    private static void squareMatrix(String command) {
        try {
            String matrixName = command.substring(5, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            if (matrix != null) {
                Matrix result = Matrix.multiply(matrix, matrix);
                System.out.println("Matrix " + matrixName + " squared:");
                result.print();
            }
            else {
                System.out.println(matrixName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void multiplyMatrices(String command) {
        try {
            String matrixNamesPart = command.substring(4, command.length() - 1).trim();
            String[] matrixNames = matrixNamesPart.split(",");
            if (matrixNames.length != 2) {
                System.out.println("Invalid multiply command format. Use mul(matrix1,matrix2)");
                return;
            }

            String matrixName1 = matrixNames[0].trim();
            String matrixName2 = matrixNames[1].trim();

            Matrix matrix1 = readMatrixFromFile(matrixName1);
            Matrix matrix2 = readMatrixFromFile(matrixName2);

            if (matrix1 == null || matrix2 == null) {
                System.out.println("One or both matrices are not recognized");
                return;
            }

            Matrix result = Matrix.multiply(matrix1, matrix2);
            System.out.println("Result of " + matrixName1 + " multiplied by " + matrixName2 + ":");
            result.print();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void subtractMatrices(String command) {
        try {
            String matrixNamesPart = command.substring(4, command.length() - 1).trim();
            String[] matrixNames = matrixNamesPart.split(",");
            if (matrixNames.length != 2) {
                System.out.println("Invalid subtraction command format. Use sub(matrix1,matrix2)");
                return;
            }

            String matrixName1 = matrixNames[0].trim();
            String matrixName2 = matrixNames[1].trim();

            Matrix matrix1 = readMatrixFromFile(matrixName1);
            Matrix matrix2 = readMatrixFromFile(matrixName2);

            if (matrix1 == null || matrix2 == null) {
                System.out.println("One or both matrices are not recognized");
                return;
            }

            Matrix result = Matrix.subMatrices(matrix1, matrix2);
            System.out.println("Result of " + matrixName1 + " - " + matrixName2 + ":");
            result.print();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void additionMatrices(String command) {
        try {
            String matrixNamesPart = command.substring(4, command.length() - 1).trim();
            String[] matrixNames = matrixNamesPart.split(",");
            if (matrixNames.length != 2) {
                System.out.println("Invalid additon command format. Use add(matrix1,matrix2)");
                return;
            }

            String matrixName1 = matrixNames[0].trim();
            String matrixName2 = matrixNames[1].trim();

            Matrix matrix1 = readMatrixFromFile(matrixName1);
            Matrix matrix2 = readMatrixFromFile(matrixName2);

            if (matrix1 == null || matrix2 == null) {
                System.out.println("One or both matrices are not recognized");
                return;
            }

            Matrix result = Matrix.addMatrices(matrix1, matrix2);
            System.out.println("Result of " + matrixName1 + " + " + matrixName2 + ":");
            result.print();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void calculateSparsity(String command) {
    	try {
    		String matrixName = command.substring(8, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            double a=Matrix.sparseMatrix(matrix);
            System.out.println("Sparsity of the matrix is : "+ a*100 +"%");
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateRank(String command) {
    	try {
    		String matrixName = command.substring(5, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            int a=Matrix.rank(matrix);
            System.out.println("Rank of the matrix is : "+ a);
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateEigenvalue(String command,Scanner scanner) {
    	try {
    		String matrixName = command.substring(4, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            System.out.println("Enter dimension of matrix: ");
	        int n=scanner.nextInt();
	        double[] initialVector = new double[n]; // Allocate memory for n elements

	        for (int i = 0; i < n; i++) {
	          initialVector[i] = 1;
	        }
	        int maxIterations = 1000;
	        double tolerance = 1e-6;
	        double eigen=Matrix.powerIteration(matrix, initialVector, maxIterations, tolerance);
	        System.out.print("Dominant Eigen: "+ eigen);
	        
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateTranspose(String command) {
    	try {
    		String matrixName = command.substring(6, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            Matrix a=Matrix.transpose(matrix);
            System.out.println("Transpose of the matrix is : ");
            a.print();
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateRowechelon(String command) {
    	try {
    		String matrixName = command.substring(3, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            Matrix a=Matrix.toRowEchelonForm(matrix);
            System.out.println("Rowechelon form of the matrix is : ");
            a.print();
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateDeterminant(String command) {
    	try {
    		String matrixName = command.substring(4, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            double a=Matrix.determinant(matrix);
            System.out.println("Determinant of the matrix is : "+ a);
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void calculateInverse(String command) {
    	try {
    		String matrixName = command.substring(4, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            Matrix a=Matrix.invert(matrix);
            System.out.println("Inverse of the matrix is : " );
            a.print();
    	}
    	catch (Exception e) {
    		System.err.println("An error occured: "+ e.getMessage());
    		e.printStackTrace();
    	}
    }
    private static void extractSub(String command,Scanner scanner) {
        try {
            String matrixName = command.substring(7, command.length() - 1).trim();
            Matrix matrix = readMatrixFromFile(matrixName);
            if (matrix == null) {
                System.out.println(matrixName + " is not recognized");
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
            if (startRow >= 0 && startCol >= 0 && startRow + numRows <= matrix.rows && startCol + numCols <= matrix.cols) {
                Matrix subMatrix = Matrix.extractMatrix(matrix, numRows, numCols, startRow, startCol);
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(matrixName + "=" + matrix.toString());
            bw.newLine();
        }
    }
    
    private static Matrix readMatrixFromFile(String matrixName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2 && parts[0].trim().equals(matrixName)) {
                    return Matrix.fromString(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public static void clearFile(String filepath) {
    	try (FileOutputStream fos = new FileOutputStream(filepath)) {
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
    }  

    
    
}
