package matrix;

public class Matrix {
    double[][] data;
    int rows, cols;

    public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED  = "\u001B[31m";
	public static final String ANSI_BLUE  = "\u001B[34m";
	public static final String ANSI_GREEN  = "\u001B[32m";
    
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    public Matrix(double[][] data) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public double[][] getData() {
        return this.data;
    }

    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.cols != B.rows) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }
        Matrix result = new Matrix(A.rows, B.cols);
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                result.data[i][j] = 0;
                System.out.println("Value of a"+i+j+" ---");
                for (int k = 0; k < A.cols; k++) {
                    result.data[i][j] += A.data[i][k] * B.data[k][j];
                    System.out.print("("+A.data[i][k] +" * "+ B.data[k][j]+")");
                    if(k==A.cols-1) {
                    	System.out.println();
                    	System.out.println("= "+result.data[i][j]);
                    	break;
                    }
                    System.out.print("+");
                }
            }
        }
        return result;
    }
    public static double sparseMatrix(Matrix A) {
        
        int total = A.rows * A.cols;
        int Count = 0;

        
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.cols; j++) {
                if (A.data[i][j] == 0.0) {
                    Count++; //counting zeros
                }
            }
        }
        System.out.println("Total number of elements in matrix: "+ total);
        System.out.println("Total number of elements are zero : "+ Count);
        System.out.println();
        System.out.println("Sparsity :");
        System.out.println("= ("+ Count + "\\" + total + "  *100 %");

        
        double sparsity = (double) Count / total;
        return sparsity;
    }
    
    public static Matrix extractMatrix(Matrix matrix, int numRows, int numCols, int startRow, int startCol) {
        if (startRow + numRows > matrix.rows || startCol + numCols > matrix.cols) {
            throw new IllegalArgumentException(ANSI_RED+"Submatrix dimensions exceed original matrix dimensions."+ANSI_RESET);
        }

        Matrix subMatrix = new Matrix(numRows, numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                subMatrix.data[i][j] = matrix.data[startRow + i][startCol + j];
            }
        }
        return subMatrix;
    }
    public static Matrix subMatrices(Matrix matrix1, Matrix matrix2) {
    	if(matrix1.rows!=matrix2.rows || matrix2.cols!=matrix1.cols) {
    		throw new IllegalArgumentException(ANSI_RED+"Dimensions doesn't match!"+ANSI_RESET);
    	}
    	
        Matrix sub = new Matrix(matrix1.rows,matrix1.cols);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.cols; j++) {
                sub.data[i][j] = matrix1.data[i][j] - matrix2.data[i][j];
                System.out.print("|a"+i+j+" = " + matrix1.data[i][j] +" - " + matrix2.data[i][j]+" |");
            }
            System.out.println();
        }
  
        return sub;
    }
    
    public static Matrix addMatrices(Matrix matrix1, Matrix matrix2) {
    	if(matrix1.rows!=matrix2.rows || matrix2.cols!=matrix1.cols) {
    		throw new IllegalArgumentException(ANSI_RED+"Dimensions doesn't match!"+ANSI_RESET);
    	}
    	
        Matrix add = new Matrix(matrix1.rows,matrix1.cols);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.cols; j++) {
                add.data[i][j] = matrix1.data[i][j] + matrix2.data[i][j];
                System.out.print("|a"+i+j+" = " + matrix1.data[i][j] +" + " + matrix2.data[i][j]+" |");
            }
            System.out.println();
        }
  
        return add;
    }
    public static Matrix invert(Matrix matrix) {
        int n = matrix.rows;
        Matrix inverse = new Matrix(n,n);
        double det = determinant(matrix);
        if (det == 0) {
            throw new ArithmeticException(ANSI_RED+"Matrix is singular and cannot be inverted."+ANSI_RESET);//singular matrix have determinant zero and we can't divide by zero
        }
     
        Matrix minors = matrixOfMinors(matrix);
        
        for (int i = 0; i < minors.rows; i++) {
            for (int j = 0; j < minors.cols; j++) {
                System.out.println("Minor of a"+i+j+" ="+minors.data[i][j]);//printing minors
            }
         System.out.println();    
        }
        Matrix cofactors = matrixOfCofactors(minors);//calling the method
        System.out.println("The matrix consisting cofactors are:");
        for (int a = 0; a < cofactors.rows; a++) {
            for (int j = 0; j < cofactors.cols; j++) {
            	System.out.println("Cofactors of a"+a+j+" ="+cofactors.data[a][j]);
            }
         System.out.println();  
        }

        // Find the adjacent matrix (transpose of the cofactor matrix)
        Matrix adjacent = transpose(cofactors);
        System.out.println("The adjacent matrix will be :");
        for (int k = 0; k < adjacent.rows; k++) {
            for (int j = 0; j < adjacent.cols; j++) {
            	System.out.println("Adjacent entries of a"+k+j+" ="+adjacent.data[k][j]);//printing adjacents
            }
            System.out.println();     
        }

        // Divide each element of the adjacent matrix by the determinant
        for (int b = 0; b < n; b++) {
            for (int j = 0; j < n; j++) {
                inverse.data[b][j] = adjacent.data[b][j] / det;
            }
        }
        
        return inverse;
        
    }
    public static double determinant(Matrix matrix) {
        int n=matrix.rows;
        if (n == 1) {
            return matrix.data[0][0];//single element 
        }
        if (n == 2) {
            return matrix.data[0][0] * matrix.data[1][1] - matrix.data[0][1] * matrix.data[1][0];//cross mul for 4 element and then div
        }
        double det = 0;
        for (int i = 0; i < n; i++) {
            det += Math.pow(-1, i) * matrix.data[0][i] * determinant(minor(matrix, 0, i));
        }
        return det;
    }
    public static Matrix transpose(Matrix matrix) {
        int n1 = matrix.rows;
        int n2= matrix.cols;
        Matrix transposed = new Matrix(n2,n1);
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                transposed.data[j][i] = matrix.data[i][j];//exchanging rows and columns
            }
        }
        return transposed;
    }
    // Method to calculate the minor matrix by removing one row and one column
    public static Matrix minor(Matrix matrix, int row, int col) {
        int n = matrix.rows;
        Matrix minor = new Matrix(n - 1,n - 1);
        int r = -1;
        for (int i = 0; i < n; i++) { //i=row maintain
            if (i == row) continue; //row equal then avoid
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) { //j=column
                if (j == col) continue; //column equal then avoid
                minor.data[r][++c] = matrix.data[i][j];
            }
        }
        return minor;
    }
    public static Matrix matrixOfMinors(Matrix matrix) {
        int n = matrix.rows;
        Matrix minors = new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                minors.data[i][j] = determinant(minor(matrix, i, j));
            }
        }
        return minors;
    }
    public static Matrix matrixOfCofactors(Matrix minors) {
        int n = minors.rows;
        Matrix cofactors = new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactors.data[i][j] = Math.pow(-1, i + j) * minors.data[i][j]; //setting + and - i=row and j=col
            }
        }
        return cofactors;
    }
    
    
    public static int rank(Matrix matrix) {
        Matrix echelonMatrix = toRowEchelonForm(matrix);
        int rank = 0;
        for (int i = 0; i < echelonMatrix.rows; i++) {
            boolean nonZeroRow = false;
            for (int j = 0; j < echelonMatrix.cols; j++) {
                if (echelonMatrix.data[i][j] != 0) {
                    nonZeroRow = true;
                    break;
                }
            }
            if (nonZeroRow) {
                rank++;
            }
        }
        return rank;
    }
    public static Matrix toRowEchelonForm(Matrix matrix) {
        int rows = matrix.rows;
        int cols = matrix.cols;
        Matrix echelonMatrix = new Matrix(rows, cols);

        // Copy the original matrix to avoid modifying it
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix.data[i], 0, echelonMatrix.data[i], 0, cols);
        }

        int lead = 0;
        for (int r = 0; r < rows; r++) {
            if (lead >= cols) {
                break;
            }
            int i = r;
            while (echelonMatrix.data[i][lead] == 0) {
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols) {
                        lead--;
                        break;
                    }
                }
            }
            swapRows(echelonMatrix, i, r);
            if (echelonMatrix.data[r][lead] != 0) {
                double lv = echelonMatrix.data[r][lead];
                for (int j = 0; j < cols; j++) {
                    echelonMatrix.data[r][j] /= lv;
                }
            }
            for (int i2 = 0; i2 < rows; i2++) {
                if (i2 != r) {
                    double lv = echelonMatrix.data[i2][lead];
                    for (int j = 0; j < cols; j++) {
                        echelonMatrix.data[i2][j] -= lv * echelonMatrix.data[r][j];
                    }
                }
            }
            lead++;
        }
        return echelonMatrix;
    }

    // Method to swap two rows in a matrix
    private static void swapRows(Matrix matrix, int row1, int row2) {
        double[] temp = matrix.data[row1];
        matrix.data[row1] = matrix.data[row2];
        matrix.data[row2] = temp;
    }

    
    
    public static double[] matrixVectorMultiply(Matrix matrix, double[] vector) {
        int n = matrix.rows;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = 0;
            for (int j = 0; j < n; j++) {
                result[i] += matrix.data[i][j] * vector[j];
            }
        }
        return result;
    }
    public static double vectorNorm(double[] vector) {
        double sum = 0;
        for (double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }
    public static double[] normalizeVector(double[] vector) {
        double norm = vectorNorm(vector);//root of square of values of vector or scalar value
        int n = vector.length;
        double[] normalizedVector = new double[n];
        for (int i = 0; i < n; i++) {
            normalizedVector[i] = vector[i] / norm;//dividing by absolute value
        }
        return normalizedVector;
    }
    public static double powerIteration(Matrix matrix, double[] initialVector, int maxIterations, double tolerance) {
        double[] b = initialVector;
        double eigenvalue = 0;
        for (int i = 0; i < maxIterations; i++) {
            double[] bNext = matrixVectorMultiply(matrix, b);
            bNext = normalizeVector(bNext); //storing next vector for iteration

            double newEigenvalue = 0;
            for (int j = 0; j < matrix.rows; j++) {
                newEigenvalue += bNext[j] * matrixVectorMultiply(matrix, bNext)[j];
            }

            if (Math.abs(newEigenvalue - eigenvalue) < tolerance) {
                eigenvalue = newEigenvalue;
                break;
            }
            eigenvalue = newEigenvalue;
            b = bNext;
        }
        return eigenvalue;
    }
    
    
    public void print() {
        for (double[] row : data) {
            for (double elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
    public static Matrix fromString(String matrixData) {
        String[] rows = matrixData.split(";");
        int rowCount = rows.length;
        int colCount = rows[0].trim().split("\\s+").length;
        Matrix matrix = new Matrix(rowCount, colCount);
        for (int i = 0; i < rowCount; i++) {
            String[] elements = rows[i].trim().split("\\s+");
            for (int j = 0; j < colCount; j++) {
                matrix.data[i][j] = Double.parseDouble(elements[j]);
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            for (double elem : row) {
                sb.append(elem).append(" ");
            }
            sb.append(";");
        }
        return sb.toString().trim();
    }
}
