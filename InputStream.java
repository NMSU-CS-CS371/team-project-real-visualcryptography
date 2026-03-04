import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class InputStream {
    public void readFile(String filepath) {
        try {
            File file = new File(filepath);
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                System.out.println("Image loaded successfully: " + image.getWidth() + "x" + image.getHeight());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}