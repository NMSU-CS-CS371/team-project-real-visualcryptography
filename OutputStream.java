import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class OutputStream {
    public void writeFile(BufferedImage image, String filepath) {
        try {
            File outputFile = new File(filepath);
            String extension = filepath.substring(filepath.lastIndexOf(".") + 1);
            ImageIO.write(image, extension, outputFile);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }   
}
