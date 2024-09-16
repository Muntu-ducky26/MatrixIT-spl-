package tensor;

public class Tensor {
    double[][][] data;
    int depth, rows, cols;

    public Tensor(int depth, int rows, int cols) {
        this.depth = depth;
        this.rows = rows;
        this.cols = cols;
        this.data = new double[depth][rows][cols];
    }

    public Tensor(double[][][] data) {
        this.data = data;
        this.depth = data.length;
        this.rows = data[0].length;
        this.cols = data[0][0].length;
    }

    public void setData(double[][][] data) {
        this.data = data;
    }

    public double[][][] getData() {
        return this.data;
    }

    public Tensor reshape(int newDepth, int newRows, int newCols) {
        if (newDepth * newRows * newCols != this.depth * this.rows * this.cols) {
            throw new IllegalArgumentException("New dimensions must match the total number of elements.");
        }

        double[] Datasequence = new double[this.depth * this.rows * this.cols];
        int index = 0;
        System.out.println("Data in sequence observation:");
        for (int d = 0; d < this.depth; d++) {
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                    Datasequence[index++] = this.data[d][r][c];
                    System.out.print("| " + this.data[d][r][c] + " |");
                }
            }
        }
        System.out.println();

        Tensor reshaped = new Tensor(newDepth, newRows, newCols);
        index = 0;
        System.out.println("Tensor Data Allocation : ");
        for (int d = 0; d < newDepth; d++) {
            for (int r = 0; r < newRows; r++) {
                for (int c = 0; c < newCols; c++) {
                    reshaped.data[d][r][c] = Datasequence[index++];
                    System.out.println(" Reshaped b"+ d + r + c + " = " + reshaped.data[d][r][c]);
                }
            }
        }
        return reshaped;
    }

    public Tensor transpose() {
        Tensor transposed = new Tensor(this.cols, this.rows, this.depth);
        System.out.println("The dimensions of transposed tensor will be changed like the following: ");
        System.out.println("Transposed tensor's[depth][row][col]  =  Initial tensor's[col][row][depth]");
        for (int d = 0; d < this.depth; d++) {
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                	System.out.println("Transposed b"+ c + r + d +" = " + "Initial a" + d + r + c +" : "+transposed.data[c][r][d] + " = " + this.data[d][r][c]);
                    transposed.data[c][r][d] = this.data[d][r][c];
                }
            }
        }
        return transposed;
    }
    public void dimension() {
    	System.out.println("The depth of the tensor is : " + this.depth);
    	System.out.println("The row of the tensor is : " + this.rows);
    	System.out.println("The column of the tensor is : " + this.cols);
    	System.out.println(".'. The Dimension of the tensor will be : [" + this.depth +"] [" + this.rows + "] [" + this.cols +"]");
    }
    public static void slicingTensor( Tensor tensor, int dS, int dE, int rS, int rE, int cS, int cE) {
        int depth = dE - dS;
        int rows = rE - rS;
        int cols = cE - cS;
        Tensor result = new Tensor(depth, rows, cols);

        for (int i = dS; i < dE; i++) {
            for (int j = rS; j < rE; j++) {
                for (int k = cS; k < cE; k++) {
                    result.data[i - dS][j - rS][k - cS] = tensor.data[i][j][k];
                }
            }
        }
        result.print();
    }
    public static void dotProduct(Tensor tensor1, Tensor tensor2) {
        int depth = tensor1.depth;
        int rows = tensor1.rows;
        int cols = tensor2.cols;

        Tensor result = new Tensor(depth,rows,cols);

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    for (int l = 0; l < tensor1.cols; l++) {
                        result.data[i][j][k] += tensor1.data[i][j][l] * tensor2.data[i][l][k];
                        System.out.print("(" + tensor1.data[i][j][l] + "*" + tensor2.data[i][l][k]+")" + " + ");
                    }
                    System.out.print("\t");
                }
                System.out.println();
            }
        }

        result.print();
    }

    public void dimensionalstride() {
    	System.out.println("The innermost dimension of the tensor is (along the columns) : " + 1);
    	System.out.println("The Stride along Rows will be : " + this.cols);
    	System.out.println("The outermost dimension of the tensor is (along the depth) :" + this.cols*this.rows );
    	System.out.println(".'. The Dimensional Stride of the tensor will be : [" + 1 +"] [" + this.cols + "] [" + this.cols*this.rows +"]");
    }
    
    public static Tensor add(Tensor t1,Tensor t2) {
        if (t1.depth != t2.depth || t1.rows != t2.rows || t1.cols != t2.cols) {
            throw new IllegalArgumentException("Tensor dimensions do not match for addition.");
        }
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] + t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " + " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }

    public static Tensor subtract(Tensor t1,Tensor t2) {
    	if (t1.depth != t2.depth || t1.rows != t2.rows || t1.cols != t2.cols) {
            throw new IllegalArgumentException("Tensor dimensions do not match for subtraction.");
        }
    	Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] - t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " - " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }

    public static Tensor multiply(Tensor t1,Tensor t2) {
    	if (t1.depth != t2.depth || t1.rows != t2.rows || t1.cols != t2.cols) {
            throw new IllegalArgumentException("Tensor dimensions do not match for multiplication.");
        }
    	Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] * t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " X " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }

    public static Tensor divide(Tensor t1,Tensor t2) {
    	if (t1.depth != t2.depth || t1.rows != t2.rows || t1.cols != t2.cols) {
            throw new IllegalArgumentException("Tensor dimensions do not match for division.");
        }
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    if (t2.data[d][r][c] == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result.data[d][r][c] = t1.data[d][r][c] / t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " / " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }
    public static Tensor scalarAdd(Tensor t1, double scalar) {
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] + scalar;
                    System.out.printf(t1.data[d][r][c] + " + " + scalar + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }
    
    public static Tensor scalarSub(Tensor t1, double scalar) {
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] - scalar;
                    System.out.printf(t1.data[d][r][c] + " - " + scalar + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }
    public static Tensor scalarMul(Tensor t1, double scalar) {
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] * scalar;
                    System.out.printf(t1.data[d][r][c] + " X " + scalar + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }
    public static Tensor scalarDiv(Tensor t1, double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        
        Tensor result = new Tensor(t1.depth, t1.rows, t1.cols);
        for (int d = 0; d < t1.depth; d++) {
            for (int r = 0; r < t1.rows; r++) {
                for (int c = 0; c < t1.cols; c++) {
                    result.data[d][r][c] = t1.data[d][r][c] / scalar;
                    System.out.printf(t1.data[d][r][c] + " / " + scalar + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return result;
    }

    
    
    
    
    public void print() {
        for (double[][] matrix : data) {
            for (double[] row : matrix) {
                for (double elem : row) {
                    System.out.print(elem + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static Tensor fromString(String tensorData) {
        String[] matrices = tensorData.split(";");
        int d = matrices.length;
        String[] rows = matrices[0].split(",");
        int r = rows.length;
        int c = rows[0].trim().split("\\s+").length;
        Tensor tensor = new Tensor(d, r, c);
        for (int i = 0; i < d; i++) {
            rows = matrices[i].split(",");
            for (int j = 0; j < r; j++) {
                String[] elements = rows[j].trim().split("\\s+");
                for (int k = 0; k < c; k++) {
                    tensor.data[i][j][k] = Double.parseDouble(elements[k]);
                }
            }
        }
        return tensor;
    }

    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[][] matrix : data) {
            for (double[] row : matrix) {
                for (double elem : row) {
                    sb.append(elem).append(" ");
                }
                sb.append(",");
            }
            sb.append(";");
        }
        return sb.toString().trim();
    }
}
