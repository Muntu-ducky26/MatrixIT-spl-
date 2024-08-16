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
