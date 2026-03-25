import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Encode {
    private File file = null;
    private String Message = null;

    public void Encoding() {
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

            System.out.println("File loaded successfully: " + filepath);
            System.out.print("Enter the message to hide in the file: ");
            Message = S.nextLine();
            File output = BitChange.encode(file, Message);
            System.out.println("Message hidden successfully in new file: " + output.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        } finally {
            S.close();
        }
    }
}