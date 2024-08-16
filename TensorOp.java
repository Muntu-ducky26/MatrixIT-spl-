package tensor;

import java.io.*;
import java.util.*;

public class TensorOp {
    private static final String filepath = "C:\\Users\\HP\\eclipse-workspace\\MatrixIT\\src\\tensor.txt";

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
            System.err.println("An error occurred: " + e.getMessage());
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
            System.err.println("An error occurred: " + e.getMessage());
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
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeTensorToFile(String tensorName, Tensor tensor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(tensorName + "=" + tensor.toString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
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
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
