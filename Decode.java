import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Decode {
    private File file = null;

    public void Decoding() {
        Scanner S = new Scanner(System.in);
        System.out.print("Enter the file path of the image: ");
        String path = S.nextLine();
        String filepath = path.replace("\"", "");
        System.out.println();
        Path AbsolutePath = Paths.get(filepath).toAbsolutePath();
        if(java.nio.file.Files.exists(AbsolutePath)){
            System.out.println("File exists at: " + AbsolutePath);
            readFile(AbsolutePath.toString());
        } else {
            System.out.println("File does not exist at: " + AbsolutePath);
        }
        S.close();
    }

    public void readFile(String filepath) {
        Scanner S = new Scanner(System.in);
        try {
            file = new File(filepath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filepath);
                return;
            }

            String message = BitChange.decode(file);
            if (message == null) {
                System.out.println("No hidden message found in file.");
            } else {
                System.out.println("Decoded message: " + message);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            S.close();
        }
    }

}
