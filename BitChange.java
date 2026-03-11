import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class BitChange {

    private int a = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int width = 0;
    private int height = 0;
    private int i = 0;

    public void test(BufferedImage image, int[] binaryArray) throws IOException {
        System.out.println(binaryArray.length);
        width = image.getWidth();
        height = image.getHeight();

        for (int y = 0; y < height; y++) {
            if(i >= binaryArray.length){
                    break;
                }
            for (int x = 0; x < width; x++) {
                if(i >= binaryArray.length){
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
                
                if(i < binaryArray.length){
                r = r | binaryArray[i]; // Set the least significant bit of red to the corresponding bit from the binary array
                ++i;
                }

                if(i < binaryArray.length){
                g = g | binaryArray[i]; // Set the least significant bit of green to the corresponding bit from the binary array
                ++i;
                }

                if(i < binaryArray.length){
                b = b | binaryArray[i]; // Set the least significant bit of blue to the corresponding bit from the binary array
                ++i;
                }

                p = (a<<24) | (r<<16) | (g<<8) | b;

                image.setRGB(x, y, p);
                System.out.println(i);
            }
        }
        System.out.println("Message hidden in the image successfully."); 
        System.out.println(i);  

        Scanner S = new Scanner(System.in);
        System.out.print("Enter the file path to save the modified image (e.g., output.png): ");
        String outputPath = S.nextLine();
        OutputStream outputStream = new OutputStream();
        outputStream.writeFile(image, outputPath);
    }
}
