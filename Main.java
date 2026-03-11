import java.nio.file.*;
import java.util.*; 

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = new InputStream();
        Scanner S = new Scanner(System.in);
        System.out.print("Enter the file path of the image: ");
        String filepath = S.nextLine();
        System.out.println();
        Path AbsolutePath = Paths.get(filepath).toAbsolutePath();
        if(java.nio.file.Files.exists(AbsolutePath)){
            System.out.println("File exists at: " + AbsolutePath);
            inputStream.readFile(AbsolutePath.toString());
        } else {
            System.out.println("File does not exist at: " + AbsolutePath);
        }
        S.close();
    }
}