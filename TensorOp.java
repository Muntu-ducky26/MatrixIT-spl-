package tensor;

import java.io.*;
import java.util.*;

public class TensorOp {
    private static final String filepath = "C:\\Users\\HP\\eclipse-workspace\\MatrixIT\\src\\tensor.txt";

    public static void main(String[] args) {
        readTensorFromFile();
    }

    public static void readTensorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                executeCommand(line, null);
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void executeCommand(String command, Scanner scanner) {
        if (command.contains("=")) {
            createTensor(command);
        } else if (command.startsWith("reshape(") && command.endsWith(")")) {
            reshapingTensor(command);
        } else if (command.startsWith("trans(") && command.endsWith(")")) {
            transTensor(command);
        } else if (command.startsWith("add(") && command.endsWith(")")) {
            addTensors(command);
        } else if (command.startsWith("sub(") && command.endsWith(")")) {
            subtractTensors(command);
        } else if (command.startsWith("mul(") && command.endsWith(")")) {
            multiplyTensors(command);
        } else if (command.startsWith("div(") && command.endsWith(")")) {
            divideTensors(command);
        } else if (command.startsWith("sadd(") && command.endsWith(")")) {
            scalarAddition(command);
        } else if (command.startsWith("ssub(") && command.endsWith(")")) {
            scalarSubtraction(command);
        } else if (command.startsWith("smul(") && command.endsWith(")")) {
            scalarMultiplication(command);
        } else if (command.startsWith("sdiv(") && command.endsWith(")")) {
            scalarDivision(command);
        } else if (command.startsWith("dim(") && command.endsWith(")")) {
            inquireDimension(command);
        } else if (command.startsWith("str(") && command.endsWith(")")) {
            inquireDimensionalStride(command);
        } else if (command.startsWith("slice(") && command.endsWith(")")) {
            sliceTensors(command,scanner);
        } else if (command.startsWith("slice(") && command.endsWith(")")) {
            dotTensors(command);
        } else {
            System.out.println("Unknown command: " + command);
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
            e.printStackTrace();
        }
    }

    private static void reshapingTensor(String command) {
        try {
            String[] parts = command.substring(8, command.length() - 1).split(",");
            String tensorName = parts[0].trim();
            int newDepth = Integer.parseInt(parts[1].trim());
            int newRows = Integer.parseInt(parts[2].trim());
            int newCols = Integer.parseInt(parts[3].trim());
            Tensor tensor = readTensorFromFile(tensorName);
            if (tensor != null) {
                Tensor reshaped = tensor.reshape(newDepth, newRows, newCols);
                System.out.println("Tensor " + tensorName + " reshaped:");
                reshaped.print();
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while reshaping tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void inquireDimension(String command) {
    	try {
    		String tensorName= command.substring(4, command.length() -1).trim();
    		Tensor tensor = readTensorFromFile(tensorName);
            if (tensor != null) {
                System.out.println("Tensor " + tensorName + " Dimensions are:");
                tensor.dimension();
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while transposing tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void sliceTensors(String command, Scanner scanner) {
    	try {
    		String tensorName= command.substring(6, command.length() -1).trim();
    		Tensor t = readTensorFromFile(tensorName);
            if (t != null) {
                System.out.println("Enter the slicing data: ");
                System.out.println();
                System.out.println("Enter the depth start and end : ");
                int startDepth = scanner.nextInt();
                int endDepth = scanner.nextInt();
                System.out.println("Enter the Row start and end : ");
                int startRow = scanner.nextInt();
                int endRow = scanner.nextInt();
                System.out.println("Enter the Column start and end : ");
                int startCol = scanner.nextInt();
                int endCol = scanner.nextInt();
                
                Tensor.slicingTensor(t, startDepth, endDepth, startRow, endRow, startCol, endCol);
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while transposing tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void inquireDimensionalStride(String command) {
    	try {
    		String tensorName= command.substring(4, command.length() -1).trim();
    		Tensor tensor = readTensorFromFile(tensorName);
            if (tensor != null) {
                System.out.println("Tensor " + tensorName + " Dimensional Stride will be :");
                tensor.dimensionalstride();
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while transposing tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void transTensor(String command) {
        try {
            String tensorName = command.substring(6, command.length() - 1).trim();
            Tensor tensor = readTensorFromFile(tensorName);
            if (tensor != null) {
                Tensor T = tensor.transpose();
                System.out.println("Tensor " + tensorName + " transposed:");
                T.print();
            } else {
                System.out.println(tensorName + " is not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while transposing tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addTensors(String command) {
        try {
            String[] parts = command.substring(4, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String tensorName2 = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            Tensor tensor2 = readTensorFromFile(tensorName2);
            if (tensor1 != null && tensor2 != null) {
                Tensor result = Tensor.add(tensor1, tensor2);
                System.out.println("Result of addition:");
                result.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void scalarAddition(String command) {
        try {
            String[] parts = command.substring(5, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String scalar = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            double s = Double.parseDouble(scalar);
            if (tensor1 != null) {
                Tensor result = Tensor.scalarAdd(tensor1, s);
                System.out.println("Result of addition:");
                result.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void subtractTensors(String command) {
        try {
            String[] parts = command.substring(4, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String tensorName2 = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            Tensor tensor2 = readTensorFromFile(tensorName2);
            if (tensor1 != null && tensor2 != null) {
                Tensor result = Tensor.subtract(tensor1, tensor2);
                System.out.println("Result of subtraction:");
                result.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while subtracting tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void dotTensors(String command) {
        try {
            String[] parts = command.substring(4, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String tensorName2 = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            Tensor tensor2 = readTensorFromFile(tensorName2);
            if (tensor1 != null && tensor2 != null) {
                Tensor.dotProduct(tensor1, tensor2);
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while subtracting tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void scalarSubtraction(String command) {
        try {
            String[] parts = command.substring(5, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String scalar = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            double s = Double.parseDouble(scalar);
            if (tensor1 != null) {
                Tensor result = Tensor.scalarSub(tensor1, s);
                System.out.println("Result of scalar subtraction:");
                result.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while subtracting tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void multiplyTensors(String command) {
        try {
            String[] parts = command.substring(4, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String tensorName2 = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            Tensor tensor2 = readTensorFromFile(tensorName2);
            if (tensor1 != null && tensor2 != null) {
                Tensor result = Tensor.multiply(tensor1, tensor2);
                System.out.println("Result of multiplication:");
                result.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while multiplying tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void scalarMultiplication(String command) {
        try {
            String[] parts = command.substring(5, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String scalar = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            double s = Double.parseDouble(scalar);
            if (tensor1 != null) {
                Tensor result = Tensor.scalarMul(tensor1, s);
                System.out.println("Result of addition:");
                result.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while multiplying tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void divideTensors(String command) {
        try {
            String[] parts = command.substring(4, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String tensorName2 = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            Tensor tensor2 = readTensorFromFile(tensorName2);
            if (tensor1 != null && tensor2 != null) {
                Tensor result = Tensor.divide(tensor1, tensor2);
                System.out.println("Result of division:");
                result.print();
            } else {
                System.out.println("One or both tensor names are not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while dividing tensors: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void scalarDivision(String command) {
        try {
            String[] parts = command.substring(5, command.length() - 1).split(",");
            String tensorName1 = parts[0].trim();
            String scalar = parts[1].trim();
            Tensor tensor1 = readTensorFromFile(tensorName1);
            double s = Double.parseDouble(scalar);
            if (tensor1 != null) {
                Tensor result = Tensor.scalarDiv(tensor1, s);
                System.out.println("Result of division:");
                result.print();
            } else {
                System.out.println("Tensor name not recognized");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while dividing tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeTensorToFile(String tensorName, Tensor tensor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(tensorName + "=" + tensor.toString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("IOException occurred while writing tensor to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Tensor readTensorFromFile(String tensorName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(tensorName + "=")) {
                    String tensorData = line.split("=", 2)[1];
                    return Tensor.fromString(tensorData);
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred while reading tensor from file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
