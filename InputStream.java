import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;

public class InputStream {
    private File file = null;
    private BufferedImage image = null;
    private String Message = null;
    private String charBinary = null;
    private int[] binaryArray = null;

    public void readFile(String filepath) {
        Scanner S = new Scanner(System.in);
        try {
            file = new File(filepath);
            image = ImageIO.read(file);
            if (image != null) {
                System.out.println("Image loaded successfully: " + image.getWidth() + "x" + image.getHeight());
                System.out.print("Enter the message to hide in the image: ");
                Message = S.nextLine();
                Text2Binary(Message);
                BitChange bitChange = new BitChange();
                bitChange.test(image, binaryArray);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        S.close();
    }

    public int[] Text2Binary (String Message) {
        StringBuilder binary = new StringBuilder();
        for (char c : Message.toCharArray()) {
            charBinary = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(charBinary);
        }
        binaryArray = new int[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            binaryArray[i] = Character.getNumericValue(binary.charAt(i));
        }
        return binaryArray;
    }
}