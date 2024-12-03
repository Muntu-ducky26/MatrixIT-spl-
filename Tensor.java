package tensor;

public class Tensor {
    double[][][] data;
    int d, r, c;
    public Tensor(int d,int r,int c) {
        this.d=d;
        this.r=r;
        this.c=c;
        this.data=new double[d][r][c];
    }
    public Tensor(double[][][] data) {
        this.data=data;
        this.d=data.length;
        this.r=data[0].length;
        this.c=data[0][0].length;
    }
    public void setData(double[][][] data) {
    	this.data=data;
    }
    public double[][][] getData() {
        return this.data;
    }
    public Tensor reshape(int depth, int row, int col){
        if (depth*row*col != this.d * this.r * this.c) {
            throw new IllegalArgumentException("New dimensions must match the total number of elements.");
        }
        double[] datseq = new double[this.d * this.r * this.c];
        int flag = 0;
        System.out.println("Data in sequence observation:");
        for (int d = 0; d < this.d; d++) {
            for (int r = 0; r < this.r; r++) {
                for (int c = 0; c < this.c; c++) {
                    datseq[flag++] = this.data[d][r][c];
                    System.out.print("| " + this.data[d][r][c] + " |");
                }
            }
        }
        System.out.println();
        Tensor t=new Tensor(depth, row, col);
        flag = 0;
        System.out.println("Tensor Data Allocation : ");
        for (int d=0; d<depth; d++) {
            for (int r=0; r<row; r++) {
                for (int c=0; c<col; c++) {
                    t.data[d][r][c] = datseq[flag++];
                    System.out.println("Reshaped b"+d+r+c+" = "+t.data[d][r][c]);
                }
           }
        }
        return t;
    }
    public Tensor transpose() {
        Tensor t = new Tensor(this.c, this.r, this.d);
        int x=this.c;
        int y=this.r;
        int z=this.d;
        System.out.println("The dimensions of transposed tensor will be changed like the following: ");
        System.out.println("Transposed tensor's[depth][row][col]  =  Initial tensor's[col][row][depth]");
        for (int d = 0;d<z; d++) {
            for (int r = 0;r<y; r++) {
                for (int c = 0;c<x; c++) {
                	System.out.println("Transposed b"+ c + r + d +" = " + "Initial a" + d + r + c +" : "+t.data[c][r][d] + " = " + this.data[d][r][c]);
                    t.data[c][r][d] = this.data[d][r][c];
                }
            }
        }
        return t;
    }
    public void dimension() {
    	System.out.println("The depth of the tensor is : " + this.d);
    	System.out.println("The row of the tensor is : " + this.r);
    	System.out.println("The column of the tensor is : " + this.c);
    	System.out.println(".'. The Dimension of the tensor will be : ["+this.d+"] ["+this.r+"] ["+this.c+"]");
    }
    public static void slicingTensor( Tensor tensor, int dS, int dE, int rS, int rE, int cS, int cE) {
        int d=dE-dS;
        int r=rE-rS;
        int c=cE-cS;
        Tensor t = new Tensor(d, r, c);
        for (int i=dS; i<dE; i++) {
            for (int j=rS; j<rE; j++) {
                for (int k=cS; k<cE; k++) {
                    t.data[i-dS][j-rS][k-cS]=tensor.data[i][j][k];
                }
            }
        }
        t.print();
    }
    public static void dotProduct(Tensor tensor1, Tensor tensor2) {
        int d=tensor1.d;
        int r=tensor1.r;
        int c=tensor2.c;
        Tensor t = new Tensor(d,r,c);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < c; k++) {
                    for (int l = 0; l < tensor1.c; l++) {
                        t.data[i][j][k] += tensor1.data[i][j][l] * tensor2.data[i][l][k];
                        System.out.print("(" + tensor1.data[i][j][l] + "*" + tensor2.data[i][l][k]+") "+" + ");
                    }
                    System.out.print("\t");
                }
                System.out.println();
            }
        }

        t.print();
    }

    public void dimensionalstride() {
    	System.out.println("The innermost dimension of the tensor is (along the columns) : " + 1);
    	System.out.println("The Stride along Rows will be : " + this.c);
    	System.out.println("The outermost dimension of the tensor is (along the depth) :" + this.c*this.r );
    	System.out.println(".'. The Dimensional Stride of the tensor will be : [" + 1 +"] [" + this.c + "] [" + this.c*this.r +"]");
    }
    
    public static Tensor add(Tensor t1,Tensor t2) {
        if (t1.d != t2.d || t1.r != t2.r || t1.c != t2.c) {
            throw new IllegalArgumentException("Tensor dimensions do not match for addition.");
        }
        Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d = 0; d < t1.d; d++) {
            for (int r = 0; r < t1.r; r++) {
                for (int c = 0; c < t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] + t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " + " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }

    public static Tensor subtract(Tensor t1,Tensor t2) {
    	if (t1.d != t2.d || t1.r != t2.r || t1.c != t2.c) {
            throw new IllegalArgumentException("Tensor dimensions do not match for subtraction.");
        }
    	Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d = 0; d < t1.d; d++) {
            for (int r = 0; r < t1.r; r++) {
                for (int c = 0; c < t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] - t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " - " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }

    public static Tensor multiply(Tensor t1,Tensor t2) {
    	if (t1.d != t2.d || t1.r != t2.r || t1.c != t2.c) {
            throw new IllegalArgumentException("Tensor dimensions do not match for multiplication.");
        }
    	Tensor result = new Tensor(t1.d, t1.r, t1.c);
        for (int d = 0; d < t1.d; d++) {
            for (int r = 0; r < t1.r; r++) {
                for (int c = 0; c < t1.c; c++) {
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
    	if (t1.d != t2.d || t1.r != t2.r || t1.c != t2.c) {
            throw new IllegalArgumentException("Tensor dimensions do not match for division.");
        }
        Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d = 0; d < t1.d; d++) {
            for (int r = 0; r < t1.r; r++) {
                for (int c = 0; c < t1.c; c++) {
                    if (t2.data[d][r][c] == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    t.data[d][r][c] = t1.data[d][r][c] / t2.data[d][r][c];
                    System.out.printf(t1.data[d][r][c] + " / " + t2.data[d][r][c] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }
    public static Tensor scalarAdd(Tensor t1, double a) {
        Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d=0; d<t1.d; d++) {
            for (int r=0; r<t1.r; r++) {
                for (int c=0; c<t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] + a;
                    System.out.printf(t1.data[d][r][c] + " + " + a + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }
    
    public static Tensor scalarSub(Tensor t1, double a) {
        Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d=0; d<t1.d; d++) {
            for (int r=0; r<t1.r; r++) {
                for (int c=0; c<t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] - a;
                    System.out.printf(t1.data[d][r][c] + " - " + a + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }
    public static Tensor scalarMul(Tensor t1, double a) {
        Tensor t=new Tensor(t1.d, t1.r, t1.c);
        for (int d=0; d<t1.d; d++) {
            for (int r=0; r<t1.r; r++) {
                for (int c=0; c<t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] * a;
                    System.out.printf(t1.data[d][r][c] + " X " + a + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }
    public static Tensor scalarDiv(Tensor t1, double a) {
        if (a == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        Tensor t = new Tensor(t1.d, t1.r, t1.c);
        for (int d = 0; d < t1.d; d++) {
            for (int r = 0; r < t1.r; r++) {
                for (int c = 0; c < t1.c; c++) {
                    t.data[d][r][c] = t1.data[d][r][c] / a;
                    System.out.printf(t1.data[d][r][c] + " / " + a + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return t;
    }
    public void print(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                for (int k = 0; k < data[i][j].length; k++) {
                    System.out.print(data[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }


    public static Tensor fromString(String tensorData) {
        String[] s = tensorData.split(";");
        int d = s.length;
        String[] rows = s[0].split(",");
        int r = rows.length;
        int c = rows[0].trim().split("\\s+").length;
        Tensor t = new Tensor(d, r, c);
        for (int i = 0; i < d; i++) {
            rows = s[i].split(",");
            for (int j = 0; j < r; j++) {
                String[] elem = rows[j].trim().split("\\s+");
                for (int k = 0; k < c; k++) {
                    t.data[i][j][k] = Double.parseDouble(elem[k]);
                }
            }
        }
        return t;
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
