package matrix;

public class Matrix {
    double[][] data;
    int r, c;
    public static final String RESET = "\u001B[0m";
	public static final String RED  = "\u001B[31m";
	public static final String BLUE  = "\u001B[34m";
	public static final String GREEN  = "\u001B[32m";
    
    public Matrix(int r, int c) {
        this.r = r;
        this.c = c;
        this.data = new double[r][c];
    }
    public Matrix(double[][] data) {
        this.data = data;
        this.r = data.length;
        this.c = data[0].length;
    }
    public void setData(double[][] data) {
        this.data = data;
    }
    public double[][] getData() {
        return this.data;
    }
    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.c != B.r) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }
        Matrix arr = new Matrix(A.r, B.c);
        for (int i = 0; i < A.r; i++) {
            for (int j = 0; j < B.c; j++) {
                arr.data[i][j] = 0;
                System.out.println("Value of a"+i+j+" ---");
                for (int k = 0; k < A.c; k++) {
                    arr.data[i][j] += A.data[i][k] * B.data[k][j];
                    System.out.print("("+A.data[i][k] +" * "+ B.data[k][j]+")");
                    if(k==A.c-1) {
                    	System.out.println();
                    	System.out.println("= "+arr.data[i][j]);
                    	break;
                    }
                    System.out.print("+");
                }
            }
        }
        return arr;
    }
    public static double sparseMatrix(Matrix A){
        int total = A.r * A.c;
        int flag = 0;
        for (int i = 0; i < A.r; i++) {
            for (int j = 0; j < A.c; j++) {
                if (A.data[i][j] == 0.0) {
                    flag++;
                }
            }
        }
        System.out.println("Total number of elements in matrix: "+ total);
        System.out.println("Total number of elements are zero : "+ flag);
        System.out.println();
        System.out.println("Sparsity :");
        System.out.println("= ("+ flag + "\\" + total + "  *100 %");
        double sparsity = (double) flag / total;
        return sparsity;
    }
    public static Matrix extractMatrix(Matrix m, int numR, int numC, int startRow, int startCol) {
        if (startRow + numR > m.r || startCol + numC > m.c) {
            throw new IllegalArgumentException(RED+"Submatrix dimensions exceed original matrix dimensions."+RESET);
        }
        Matrix sub=new Matrix(numR, numC);
        for (int i=0; i<numR; i++) {
            for (int j = 0; j < numC; j++) {
                sub.data[i][j] = m.data[startRow + i][startCol + j];
            }
        }
        return sub;
    }
    public static Matrix subMatrices(Matrix m1, Matrix m2) {
    	if(m1.r!=m2.r || m2.c!=m1.c) {
    		throw new IllegalArgumentException(RED+"Dimensions doesn't match!"+RESET);
    	}
        Matrix sub = new Matrix(m1.r,m1.c);
        for (int i = 0; i < m1.r; i++) {
            for (int j = 0; j < m1.c; j++) {
                sub.data[i][j] = m1.data[i][j] - m2.data[i][j];
                System.out.print("|a"+i+j+" = " + m1.data[i][j] +" - " + m2.data[i][j]+" |");
            }
            System.out.println();}  
        return sub;
    } 
    public static Matrix addMatrices(Matrix m1, Matrix m2){
    	if(m1.r!=m2.r || m2.c!=m1.c){
    		throw new IllegalArgumentException(RED+"Dimensions doesn't match!"+RESET);
    	}   	
        Matrix add = new Matrix(m1.r,m1.c);
        for (int i = 0; i < m1.r; i++){
            for (int j = 0; j < m1.c; j++) {
                add.data[i][j] = m1.data[i][j] + m2.data[i][j];
                System.out.print("|a"+i+j+" = " + m1.data[i][j] +" + " + m2.data[i][j]+" |");
            }
            System.out.println();
        }
        return add;
    }
    public static Matrix invert(Matrix m) {
        int n=m.r;
        Matrix inv=new Matrix(n,n);
        double det=determinant(m);
        if (det == 0){
            throw new ArithmeticException(RED+"Matrix is singular and cannot be inverted."+RESET);//singular matrix have determinant zero and we can't divide by zero
        }   
        Matrix minOfmat=matrixOfMinors(m);    
        for (int i = 0; i < minOfmat.r; i++){
            for (int j = 0; j < minOfmat.c; j++){
                System.out.println("Minor of a"+i+j+" ="+minOfmat.data[i][j]);//printing minors
            }
         System.out.println();    
        }
        Matrix cofactorMatrix=matrixOfCofactors(minOfmat);
        System.out.println("The matrix consisting cofactors are:");
        for (int a = 0; a < cofactorMatrix.r; a++){
            for (int j = 0; j < cofactorMatrix.c; j++){
            	System.out.println("Cofactors of a"+a+j+" ="+cofactorMatrix.data[a][j]);
            }
         System.out.println();  
        }
        Matrix adj = transpose(cofactorMatrix);
        System.out.println("The adjacent matrix will be :");
        for (int k = 0; k < adj.r; k++){
            for (int j = 0; j < adj.c; j++){
            	System.out.println("Adjacent entries of a"+k+j+" ="+adj.data[k][j]);//printing adjacents
            }
            System.out.println();}
        for (int b=0; b<n; b++){
            for (int j =0; j<n; j++){
                inv.data[b][j] = adj.data[b][j]/det;
            }}
        return inv;
    }
    public static double determinant(Matrix m) {
        int n=m.r;
        if(n==1){
            return m.data[0][0];//single element 
        }
        if(n==2){
        	System.out.println("(("+m.data[0][0] +" X "+ m.data[1][1] +") - ("+ m.data[0][1] +" X "+m.data[1][0]+"))");
            return m.data[0][0] * m.data[1][1] - m.data[0][1] * m.data[1][0];//cross mul for 4 element and then div
        }
        double det = 0;
        for (int i = 0; i < n; i++) {
        	System.out.println("For index "+ i+1 );
        	System.out.println("( -1^"+i +" X "+ m.data[0][i]+" X "+determinant(minOfMat(m, 0, i))+") + ");
            det += Math.pow(-1, i)*m.data[0][i]*determinant(minOfMat(m, 0, i));
            }
        System.out.println("Determinant is: "+det);
        return det;
    }
    public static Matrix transpose(Matrix m){
        int n1 = m.r;
        int n2= m.c;
        Matrix trans=new Matrix(n2,n1);
        for (int i=0; i<n1; i++) {
            for (int j=0; j<n2; j++){
                trans.data[j][i] = m.data[i][j];
            }}
        return trans;
        }
    public static Matrix minOfMat(Matrix m, int row, int col) {
        int n = m.r;
        Matrix minor = new Matrix(n - 1,n - 1);
        int r = -1;
        for (int i = 0; i < n; i++) { //i=row maintain
            if (i == row) continue; //row equal then avoid
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) { //j=column
                if (j == col) continue; //column equal then avoid
                minor.data[r][++c] = m.data[i][j];
            }
        }
        return minor;
        }
    public static Matrix matrixOfMinors(Matrix m) {
        int n = m.r;
        Matrix minors = new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                minors.data[i][j] = determinant(minOfMat(m, i, j));
            }
        }
        return minors;
        }
    public static Matrix matrixOfCofactors(Matrix minors) {
        int n = minors.r;
        Matrix cofactors = new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactors.data[i][j] = Math.pow(-1, i + j) * minors.data[i][j]; //setting + and - i=row and j=col
            }
        }
        return cofactors;
        }
    public static int rank(Matrix m) {
    	System.out.println("Calculating rank of the matrix...");
        Matrix reMat = toRowEchelonForm(m);
        int rank = 0;
        for (int i = 0; i < reMat.r; i++) {
            boolean nonZeroRow = false;
            for (int j = 0; j < reMat.c; j++) {
                if (reMat.data[i][j] != 0) {
                    nonZeroRow = true;
                    break;
                }
            }
            if (nonZeroRow) {
                rank++;
            }}
        System.out.println("Row echelon form of the matrix:");
        reMat.print();
        System.out.println("Rank of the matrix: " + rank);
        return rank;
    }
    public static Matrix toRowEchelonForm(Matrix m) {
    	System.out.println("Converting matrix to row echelon form...");
        int rows = m.r;
        int cols = m.c;
        Matrix rowechMat = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(m.data[i], 0, rowechMat.data[i], 0, cols);
        }

        int lead = 0;
        for (int r = 0; r < rows; r++) {
            if (lead >= cols) {
                break;
            }
            int i = r;
            while (rowechMat.data[i][lead] == 0) {
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
            if(i!=r){
                System.out.println("Swapping rows " + (i + 1) + " and " + (r + 1));
                swapRows(rowechMat,i,r);
                rowechMat.print();
            }
            if(rowechMat.data[r][lead] != 0) {
                double lv = rowechMat.data[r][lead];
                System.out.println("Normalizing row " + (r + 1) + " by dividing by " + lv);
                for (int j = 0; j < cols; j++) {
                    rowechMat.data[r][j] /= lv;
                }
            }
            for (int i2 = 0; i2 < rows; i2++) {
                if (i2 != r) {
                    double lv = rowechMat.data[i2][lead];
                    System.out.println("Eliminating element at row " + (i2 + 1) + ", column " + (lead + 1));
                    for (int j = 0; j < cols; j++) {
                        rowechMat.data[i2][j] -= lv * rowechMat.data[r][j];
                    }
                }
            }
            lead++;
        }
        return rowechMat;
        }
    private static void swapRows(Matrix m, int row1, int row2) {
    	System.out.println("Swapping rows: "+(row1+1) +" and "+(row2+1));
        double[] temp=m.data[row1];
        m.data[row1]=m.data[row2];
        m.data[row2]=temp;
    }       
    public static double[] matrixVectorMultiply(Matrix m, double[] vec) {
    	System.out.println("Performing matrix-vector multiplication...");
        int n = m.r;
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = 0;
            for (int j = 0; j < n; j++) {
            	System.out.printf("Adding %f * %f\n", m.data[i][j], vec[j]);
                arr[i] += m.data[i][j] * vec[j];
            }
            System.out.printf("Row %d result: %f\n", i + 1, arr[i]);
        }
        return arr;
    }
    public static double vectorNorm(double[] vec) {
    	System.out.println("Calculating vector norm...");
        double sum = 0;
        for (double v:vec){
        	System.out.printf("Adding square of %f\n", v);
            sum += v*v;}
        double norm=Math.sqrt(sum);
        System.out.printf("Norm of the vector: %f\n", norm);
        return norm;
    }
    public static double[] normalizeVector(double[] vector) {
    	System.out.println("Normalizing vector...");
        double norm = vectorNorm(vector);//root of square of values of vector or scalar value
        int n = vector.length;
        double[] normalizedVector = new double[n];
        for (int i = 0; i < n; i++) {
            normalizedVector[i] = vector[i] / norm;//dividing by absolute value
            System.out.printf("Normalized element %d: %f\n", i + 1, normalizedVector[i]);
        }
        return normalizedVector;
    }
    public static double powerIteration(Matrix m, double[] vecI, int maxIterations, double tolerance) {
        System.out.println("Performing power iteration to calculate the dominant eigenvalue\n");
        double[] b=vecI;
        double egVal = 0;

        for (int i = 0; i<maxIterations; i++) {
            System.out.println("Iteration " + (i + 1) + ":");
            double[] b2 = matrixVectorMultiply(m, b);
            b2 = normalizeVector(b2);

            double egVal2 = 0;
            for (int j = 0; j < m.r; j++) {
                egVal2 += b2[j]*matrixVectorMultiply(m, b2)[j];
            }

            System.out.printf("Calculated eigenvalue: %f\n", egVal2);
            if (Math.abs(egVal2 - egVal) < tolerance) {
                System.out.println("Desired Convergence achieved");
                egVal=egVal2;
                break;
            }
            egVal = egVal2;
            b = b2;
        }

        System.out.printf("Dominant eigenvalue: %f\n", egVal);
        return egVal;
    }   
    public void print() {
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static Matrix fromString(String mData) {
        String[] r=mData.split(";");
        int numR=r.length;
        int numC=r[0].trim().split("\\s+").length;
        Matrix m=new Matrix(numR, numC);
        for (int i = 0; i < numR; i++) {
            String[] data = r[i].trim().split("\\s+");
            for (int j = 0; j < numC; j++) {
                m.data[i][j]=Double.parseDouble(data[j]);
            }
        }
        return m;
    }
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data[i].length; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append(";");
        }
        return sb.toString().trim();
    }

}
