package tensor;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
public class TensorOp {
	public static final String RESET = "\u001B[0m";
	public static final String RED  = "\u001B[31m";
	public static final String BLUE  = "\u001B[34m";
	public static final String GREEN  = "\u001B[32m";
    private static final String path = System.getProperty("user.home") + File.separator + "variablest.txt";
    private static final String helppath = "C:\\Users\\HP\\eclipse-workspace\\MatrixIT\\src\\helpt.txt";

    public static void main(String[] args) {
        readTensorFromFile();
    }

    public static void readTensorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                executeCommand(line, null);
            }
        } catch (IOException e) {
            System.out.println("IOException occurred: Try again or Type help");
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
            System.out.println("Try Again! Couldn't read from File.");
            
    	}
    	return null;
    }
    public static void executeCommand(String command, Scanner scanner) {
//    	String s="[a-zA-Z]+ *= *\\[ *([[0-9]+, *[0-9]+ *]+;?)+ *\\]";
    	String s="[a-zA-Z]+ *= *\\[ *([-]?[0-9]+(?:[ ,]+[-]?[0-9]+)* *(?:; *[-]?[0-9]+(?:[ ,]+[-]?[0-9]+)*)*) *\\]";
    	String add="add +[a-zA-z]+ +[a-zA-Z]+";
    	String sub="sub +[a-zA-z]+ +[a-zA-Z]+";
    	String mul="mul +[a-zA-z]+ +[a-zA-Z]+";
    	String div="div +[a-zA-z]+ +[a-zA-Z]+";
    	String sadd="sadd +[a-zA-z]+ +[-0-9]+";
    	String ssub="ssub +[a-zA-z]+ +[-0-9]+";
    	String smul="smul +[a-zA-z]+ +[-0-9]+";
    	String sdiv="sdiv +[a-zA-z]+ +[-0-9]+";
    	String dot="dot +[a-zA-z]+ +[a-zA-Z]+";
    	String slice="slice +[a-zA-Z]+";
    	String str="str +[a-zA-Z]+";
    	String dim="dim +[a-zA-Z]+";
    	String trans="trans +[a-zA-Z]+";
    	String reshape="reshape +[a-zA-z]+ +[0-9]+ +[0-9]+ +[0-9]+";
    	String c="[a-zA-Z]+";
        if (Pattern.matches(s,command)) {
            createTensor(command);
        } else if (Pattern.matches(reshape,command)) {
            reshapingTensor(command);
        } else if (Pattern.matches(trans,command)) {
            transTensor(command);
        } else if (Pattern.matches(add,command)) {
            addTensors(command);
        } else if (Pattern.matches(sub,command)) {
            subtractTensors(command);
        } else if (Pattern.matches(mul,command)) {
            multiplyTensors(command);
        } else if (Pattern.matches(div,command)) {
            divideTensors(command);
        } else if (Pattern.matches(sadd,command)) {
            scalarAddition(command);
        } else if (Pattern.matches(ssub,command)) {
            scalarSubtraction(command);
        } else if (Pattern.matches(smul,command)) {
            scalarMultiplication(command);
        } else if (Pattern.matches(sdiv,command)) {
            scalarDivision(command);
        } else if (Pattern.matches(dim,command)) {
            inquireDimension(command);
        } else if (Pattern.matches(str,command)) {
            inquireDimensionalStride(command);
        } else if (Pattern.matches(slice,command) ) {
            sliceTensors(command,scanner);
        } else if (Pattern.matches(dot,command)) {
            dotTensors(command);
        } else if(command.startsWith("help")) {
        	printHelp(command);
        } else if (Pattern.matches(c,command)){
        	if(!Pattern.matches("help",command)) {
        		printTen(command);
        }
        } else if(!Pattern.matches(s,command)) {
        	System.out.println("Create tensor---- name= [ a00 a01 a02....a0n ,..., an0 an1 an2 ann..;...;b00 b01 b02....b0n ,..., bn0 bn1 bn2 bnn..]");
        	System.out.println("b=[1 2 3, 2 3 1; 1 1 1, 2 2 2] \n'Write your desired numbers'");
        } else {
            System.out.println("Unknown command: " + command);
            System.out.println("Type help and any of the following:\\ncreate, add, mul, div, scalar add, scalar div, scalar add,\\ntranspose, stride, dimension, slice, dot");
        }
    }
    
    private static void createTensor(String command) {
        try {
            String[] parts = command.split("=", 2);
            String tensorName = parts[0].trim();
            String tensorData = parts[1].trim().replaceAll("[\\[\\]]", "");
            Tensor tensor = Tensor.fromString(tensorData);
            writeTensorToFile(tensorName, tensor);
            System.out.println("Tensor " + tensorName + " created:");
            tensor.print();
        } catch (Exception e) {
            System.err.println("An error occurred while creating tensor: " + e.getMessage());
        	System.out.println("Create tensor---- name= [ a00 a01 a02....a0n ,..., an0 an1 an2 ann..;...;b00 b01 b02....b0n ,..., bn0 bn1 bn2 bnn..]");
        	System.out.println("b=[1 2 3, 2 3 1; 1 1 1, 2 2 2] \n'Write your desired numbers'");
        }
    }

    private static void reshapingTensor(String command) {
        try {
            String[] s = command.substring(8, command.length()).split(" ");
            String ten = s[0].trim();
            int d = Integer.parseInt(s[1].trim());
            int r = Integer.parseInt(s[2].trim());
            int c = Integer.parseInt(s[3].trim());
            Tensor t = readTensorFromFile(ten);
            if (t != null) {
                Tensor t1 = t.reshape(d, r, c);
                System.out.println("Tensor " + ten + " reshaped:");
                t1.print();
            } else {
                System.out.println(ten + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while reshaping tensor: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Enter reshape Tensor A B C \nAxBxC should be equal to previous dimensional product");
        }
    }
    private static void inquireDimension(String command) {
    	try {
    		String ten= command.substring(4, command.length()).trim();
    		Tensor t = readTensorFromFile(ten);
            if (t != null) {
                System.out.println("Tensor " + ten + " Dimensions are:");
                t.dimension();
            } else {
                System.out.println(ten + " is not recognized");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while inquiring tensor: Try again!");
        }
    }
    private static void sliceTensors(String command, Scanner scanner) {
    	try {
    		String ten= command.substring(6, command.length()).trim();
    		Tensor t = readTensorFromFile(ten);
            if (t != null) {
                System.out.println("Enter the slicing data: ");
                System.out.println();
                System.out.println("Enter the depth start and end : ");
                int ds = scanner.nextInt();
                int de = scanner.nextInt();
                System.out.println("Enter the Row start and end : ");
                int rs = scanner.nextInt();
                int re = scanner.nextInt();
                System.out.println("Enter the Column start and end : ");
                int cs = scanner.nextInt();
                int ce = scanner.nextInt();
                
                Tensor.slicingTensor(t, ds, de, rs, re, cs, ce);
            } else {
                System.out.println(ten + " is not recognized");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while slicing tensor: Try again!");
        }
    }
    private static void printTen(String command) {
    	String name=command;
    	Tensor t=readTensorFromFile(name);
    	if(t!=null) { 
    	t.print();}
    	else {
    		System.out.println("Create tensor---- name= [ a00 a01 a02....a0n ,..., an0 an1 an2 ann..;...;b00 b01 b02....b0n ,..., bn0 bn1 bn2 bnn..]");
    		System.out.println("b=[1 2 3, 2 3 1; 1 1 1, 2 2 2] \n'Write your desired numbers'");
    		System.out.println("Type help and any of the following:\ncreate, add, mul, div, scalar add, scalar div, scalar add,\ntranspose, stride, dimension, slice, dot");
    	}
    }
    private static void inquireDimensionalStride(String command) {
    	try {
    		String tensorName= command.substring(4, command.length()).trim();
    		Tensor tensor = readTensorFromFile(tensorName);
            if (tensor != null) {
                System.out.println("Tensor " + tensorName + " Dimensional Stride will be :");
                tensor.dimensionalstride();
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while inquiring Dimensional Stride tensor: " + e.getMessage());
            
        }
    }

    private static void transTensor(String command) {
        try {
            String s = command.substring(6, command.length()).trim();
            Tensor t = readTensorFromFile(s);
            if (t != null) {
                Tensor T = t.transpose();
                System.out.println("Tensor " + s + " transposed:");
                T.print();
            } else{
                System.out.println(s + " is not recognized");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while transposing tensor: Try again!");
        }
    }

    private static void addTensors(String command) {
        try {
            String[] s = command.substring(4, command.length()).split(" ");
            String ten1 = s[0].trim();
            String ten2 = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            Tensor t2 = readTensorFromFile(ten2);
            if (t1 != null && t2 != null) {
                Tensor t = Tensor.add(t1, t2);
                System.out.println("Result of addition:");
                t.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding tensors: " + e.getMessage());
            System.out.println("Input Example: add a b");
        }
    }
    private static void scalarAddition(String command) {
        try {
            String[] s = command.substring(5, command.length()).split(" ");
            String ten1 = s[0].trim();
            String a = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            double s1 = Double.parseDouble(a);
            if (t1 != null) {
                Tensor t = Tensor.scalarAdd(t1, s1);
                System.out.println("Result of addition:");
                t.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding tensor: " + e.getMessage());
            System.out.println("Input Example: sadd a b");
        }
    }

    private static void subtractTensors(String command) {
        try {
            String[] s = command.substring(4, command.length()).split(" ");
            String ten1 = s[0].trim();
            String ten2 = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            Tensor t2 = readTensorFromFile(ten2);
            if (t1 != null && t2 != null) {
                Tensor t = Tensor.subtract(t1, t2);
                System.out.println("Result of subtraction:");
                t.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while subtracting tensors: " + e.getMessage());
            System.out.println("Input Example: sub a b");
        }
    }
    private static void dotTensors(String command) {
        try {
            String[] s = command.substring(4, command.length()).split(" ");
            String ten1 = s[0].trim();
            String ten2 = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            Tensor t2 = readTensorFromFile(ten2);
            if (t1 != null && t2 != null) {
                Tensor.dotProduct(t1, t2);
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while doing dot product tensors: " + e.getMessage());
            System.out.println("Input Example: dot a b");
        }
    }
    
    private static void scalarSubtraction(String command) {
        try {
            String[] s = command.substring(5, command.length() ).split(" ");
            String ten1 = s[0].trim();
            String a = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            double s1 = Double.parseDouble(a);
            if (t1 != null) {
                Tensor t = Tensor.scalarSub(t1, s1);
                System.out.println("Result of scalar subtraction:");
                t.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while subtracting tensor: " + e.getMessage());
            System.out.println("Input Example: ssub a b");
        }
    }

    private static void multiplyTensors(String command) {
        try {
            String[] s = command.substring(4, command.length()).split(" ");
            String ten1 = s[0].trim();
            String ten2 = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            Tensor t2 = readTensorFromFile(ten2);
            if (t1 != null && t2 != null) {
                Tensor t = Tensor.multiply(t1, t2);
                System.out.println("Result of multiplication:");
                t.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while multiplying tensors: " + e.getMessage());
            System.out.println("Input Example: mul a b");
        }
    }
    private static void scalarMultiplication(String command) {
        try {
            String[] s = command.substring(5, command.length()).split(" ");
            String ten1 = s[0].trim();
            String a = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            double s1 = Double.parseDouble(a);
            if (t1 != null) {
                Tensor t = Tensor.scalarMul(t1, s1);
                System.out.println("Result of addition:");
                t.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while multiplying tensors: " + e.getMessage());
            System.out.println("Input Example: smul a b");
        }
    }

    private static void divideTensors(String command) {
        try {
            String[] s = command.substring(4, command.length()).split(" ");
            String ten1 = s[0].trim();
            String ten2 = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            Tensor t2 = readTensorFromFile(ten2);
            if (t1 != null && t2 != null) {
                Tensor t = Tensor.divide(t1, t2);
                System.out.println("Result of division:");
                t.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while dividing tensors: " + e.getMessage());
            System.out.println("Input Example: div a b");
        }
    }
    private static void scalarDivision(String command) {
        try {
            String[] s = command.substring(5, command.length()).split(" ");
            String ten1 = s[0].trim();
            String a = s[1].trim();
            Tensor t1 = readTensorFromFile(ten1);
            double s1 = Double.parseDouble(a);
            if (t1 != null) {
                Tensor t = Tensor.scalarDiv(t1, s1);
                System.out.println("Result of division:");
                t.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while dividing tensor: " + e.getMessage());
            System.out.println("Input Example: sdiv a b");
        }
    }

    private static void writeTensorToFile(String tensorName, Tensor tensor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(tensorName + "=" + tensor.toString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("IOException occurred while writing tensor to file: " + e.getMessage());
            System.out.println("Try again!");
        }
    }
    private static void printHelp(String command) {
    	String[] s=command.split(" ",2);
    	if(s.length>=2) {
    	String helpName=s[1].trim();
    	String line = readHelpFromFile(helpName);
    	if(line!=null) {
    		System.out.println(line);
    	}

    	else {
    		System.out.println(GREEN+"Create tensor---- name= [ a00 a01 a02....a0n ,..., an0 an1 an2 ann..;...;b00 b01 b02....b0n ,..., bn0 bn1 bn2 bnn..]"+RESET);
    		System.out.println("b=[1 2 3, 2 3 1; 1 1 1, 2 2 2] \n'Write your desired numbers'");
    		System.out.println(RED+"Type help and any of the following:\ncreate, add, mul, div, scalar add, scalar div, scalar add,\ntranspose, stride, dimension, slice, dot"+RESET);
        	}
    	
    }
    	else {
    		System.out.println(GREEN+"Create tensor---- name= [ a00 a01 a02....a0n ,..., an0 an1 an2 ann..;...;b00 b01 b02....b0n ,..., bn0 bn1 bn2 bnn..]"+RESET);
    		System.out.println("b=[1 2 3, 2 3 1; 1 1 1, 2 2 2] \n'Write your desired numbers'");
    		System.out.println(RED+"Type help and any of the following:\ncreate, add, mul, div, scalar add, scalar div, scalar add,\ntranspose, stride, dimension, slice, dot"+RESET);
        	}
   }
    private static Tensor readTensorFromFile(String tensorName) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(tensorName + "=")) {
                    String tensorData = line.split("=", 2)[1];
                    return Tensor.fromString(tensorData);
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred while reading tensor from file: " + e.getMessage());
            System.out.println("Try again!");
        }
        return null;
    }
    
    public static void clearFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("");
            System.out.println("Thank you for choosing MatrixIT :)");
            System.out.println("Enter your choice again or exit to quit:");
            System.out.println(" m. Matrix \n t. Tensor \n l.Linear\n Or Type help");
        } catch (IOException e) {
            System.err.println("IOException occurred while clearing the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
