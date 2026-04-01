import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;

public class BitChange {

    private int a = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int width = 0;
    private int height = 0;
    private int i = 0;
    private String charBinary = null; 
    private int[] messageBytes = null;

    public File encode(File original, String message) throws IOException {
        BufferedImage image = ImageIO.read(original);
        
        messageBytes = Text2Binary(message);

        System.out.println(messageBytes.length);
        width = image.getWidth();
        height = image.getHeight();

        for (int y = 0; y < height; y++) {
            if(i >= messageBytes.length){
                    break;
                }
            for (int x = 0; x < width; x++) {
                if(i >= messageBytes.length){
                    break;
                }

                int p = image.getRGB(x, y);

                a = (p>>24) & 0xff;
                r = (p>>16) & 0xff;
                g = (p>>8) & 0xff;
                b = p & 0xff;

                r = r & 0xFE; // Set the least significant bit of red to 0
                g = g & 0xFE; // Set the least significant bit of green to 0
                b = b & 0xFE; // Set the least significant bit of blue to 0
                
                if(i < messageBytes.length){
                r = r | messageBytes[i]; // Set the least significant bit of red to the corresponding bit from the message bytes
                ++i;
                }

                if(i < messageBytes.length){
                g = g | messageBytes[i]; // Set the least significant bit of green to the corresponding bit from the message bytes
                ++i;
                }

                if(i < messageBytes.length){
                b = b | messageBytes[i]; // Set the least significant bit of blue to the corresponding bit from the message bytes
                ++i;
                }

                p = (a<<24) | (r<<16) | (g<<8) | b;

                image.setRGB(x, y, p);
            }
        }
        System.out.println("Message hidden in the image successfully."); 
        System.out.println(i);  

        Scanner S = new Scanner(System.in);
        System.out.print("Enter the new File name: ");
        String outputPath = original.getAbsolutePath().substring(0, original.getAbsolutePath().lastIndexOf(File.separator) + 1) + S.nextLine() + ".png";
        ImageIO.write(image, "png", new File(outputPath));
        return new File(outputPath);
    }

    public static String decode(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        StringBuilder binaryMessage = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                binaryMessage.append(r & 1); // Append the least significant bit of red
                binaryMessage.append(g & 1); // Append the least significant bit of green
                binaryMessage.append(b & 1); // Append the least significant bit of blue
            }
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            if (i + 8 > binaryMessage.length()) {
                break; // Avoid index out of bounds
            }
            String byteString = binaryMessage.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) { // Assuming null character indicates end of message
                break;
            }
            message.append((char) charCode);
        }

        return message.toString();
    }

    public int[] Text2Binary (String Message) {
        StringBuilder binary = new StringBuilder();
        for (char c : Message.toCharArray()) {
            charBinary = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(charBinary);
        }
        binary.append("00000000"); // Append null character to indicate end of message
        messageBytes = new int[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            messageBytes[i] = Character.getNumericValue(binary.charAt(i));
        }
        return messageBytes;
    }
}