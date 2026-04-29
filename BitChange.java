//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.awt.Graphics2D;
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
    private String outputPath = null;

    public File encode(File original, String message, Scanner S) throws IOException {
        BufferedImage image = ImageIO.read(original);
        if(image != null) { //Checks for null images
            int TestWidth = image.getWidth(null);
            if(TestWidth == -1){
        
            } else{
            BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB); //New image for ARGB
            BufferedImage image3 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); //New image for RGB

            if(original.getAbsolutePath().substring(original.getAbsolutePath().lastIndexOf('.')).equals(".jpg") || original.getAbsolutePath().substring(original.getAbsolutePath().lastIndexOf('.')).equals(".jpeg")){ //jpg or jpeg files only
                Graphics2D g2d = image3.createGraphics(); //draws a new image to keep 24 bit depth

                try {
                    g2d.drawImage(image, 0, 0, null);
                }finally {
                    g2d.dispose();
                }

                messageBytes = Text2Binary(message); //turns message into binary

                System.out.println(messageBytes.length);
                width = image3.getWidth();
                height = image3.getHeight();

                //for loop for putting binary into the LSB(least significant bit)
                for (int y = 0; y < height; y++) {
                    if(i >= messageBytes.length){
                            break;
                        }
                    for (int x = 0; x < width; x++) {
                        if(i >= messageBytes.length){
                            break;
                        }

                        int p = image3.getRGB(x, y);

                        r = (p>>16) & 0xff;
                        g = (p>>8) & 0xff;
                        b = p & 0xff;

                        r = r & 0xFE; // Set the LSB of red to 0
                        g = g & 0xFE; // Set the LSB of green to 0
                        b = b & 0xFE; // Set the LSB of blue to 0

                        if(i < messageBytes.length){
                        r = r | messageBytes[i]; // Set the LSB of red to the corresponding bit from the message bytes
                        ++i;
                        }

                        if(i < messageBytes.length){
                        g = g | messageBytes[i]; // Set the LSB of green to the corresponding bit from the message bytes
                        ++i;
                        }

                        if(i < messageBytes.length){
                        b = b | messageBytes[i]; // Set the LSB of blue to the corresponding bit from the message bytes
                        ++i;
                        }

                        p = (r<<16) | (g<<8) | b;

                        image3.setRGB(x, y, p);
                    }
                }
                System.out.println("Message hidden in the image successfully."); 
                System.out.println(i);  

                System.out.print("Enter the new File name: ");
                //Creates the new file path
                outputPath = original.getAbsolutePath().substring(0, original.getAbsolutePath().lastIndexOf(File.separator) + 1) + S.nextLine() + original.getAbsolutePath().substring(original.getAbsolutePath().lastIndexOf('.'));
                //Writes image to the new file path
                ImageIO.write(image3, original.getAbsolutePath().substring(original.getAbsolutePath().lastIndexOf('.') + 1), new File(outputPath));
                return new File(outputPath);
            } else{
                Graphics2D g2d = image2.createGraphics();

                try {
                    g2d.drawImage(image, 0, 0, null);
                }finally {
                    g2d.dispose();
                }

                messageBytes = Text2Binary(message);

                System.out.println(messageBytes.length);
                width = image2.getWidth();
                height = image2.getHeight();

                for (int y = 0; y < height; y++) {
                    if(i >= messageBytes.length){
                            break;
                        }
                    for (int x = 0; x < width; x++) {
                        if(i >= messageBytes.length){
                            break;
                        }

                        int p = image2.getRGB(x, y);

                        a = (p>>24) & 0xff;
                        r = (p>>16) & 0xff;
                        g = (p>>8) & 0xff;
                        b = p & 0xff;

                        r = r & 0xFE; // Set the LSB of red to 0
                        g = g & 0xFE; // Set the LSB of green to 0
                        b = b & 0xFE; // Set the LSB of blue to 0

                        if(i < messageBytes.length){
                        r = r | messageBytes[i]; // Set the LSB of red to the corresponding bit from the message bytes
                        ++i;
                        }

                        if(i < messageBytes.length){
                        g = g | messageBytes[i]; // Set the LSB of green to the corresponding bit from the message bytes
                        ++i;
                        }

                        if(i < messageBytes.length){
                        b = b | messageBytes[i]; // Set the LSB of blue to the corresponding bit from the message bytes
                        ++i;
                        }

                        p = (a<<24) | (r<<16) | (g<<8) | b;

                        image2.setRGB(x, y, p);
                    }
                }
                System.out.println("Message hidden in the image successfully."); 
                System.out.println(i);  

                System.out.print("Enter the new File name: ");
                //Creates a new file path but changes the extension to png
                outputPath = original.getAbsolutePath().substring(0, original.getAbsolutePath().lastIndexOf(File.separator) + 1) + S.nextLine() + ".png";
                //Writes image to the new file path; Only png
                ImageIO.write(image2, "png", new File(outputPath));
                return new File(outputPath);
                }
            }
        }
        //If the file is null this is returned
        return new File("nofile");
    }

    public String decode(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        StringBuilder binaryMessage = new StringBuilder();
        width = image.getWidth();
        height = image.getHeight();
        //For loop for reading and decoding message
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                r = (p>>16) & 0xff;
                g = (p>>8) & 0xff;
                b = p & 0xff;

                binaryMessage.append(r & 1); // Append the LSB of red
                binaryMessage.append(g & 1); // Append the LSB of green
                binaryMessage.append(b & 1); // Append the LSB of blue
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