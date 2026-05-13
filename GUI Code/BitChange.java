//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BitChange {

    private int a = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int width = 0;
    private int height = 0;
    private int i;
    private String charBinary = null;
    private int[] messageBytes = null;

    public File encode(File original, String message, String outputPath) throws IOException {
        if (original == null || !original.exists()) {
            throw new IOException("Original file does not exist.");
        }

        if (message == null) {
            message = "";
        }

        BufferedImage image = ImageIO.read(original);
        if (image == null) {
            throw new IOException("Could not read image file.");
        }

        BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image2.createGraphics();
        try {
            g2d.drawImage(image, 0, 0, null);
        } finally {
            g2d.dispose();
        }

        messageBytes = Text2Binary(message);
        width = image2.getWidth();
        height = image2.getHeight();

        int availableBits = width * height * 3;
        if (messageBytes.length > availableBits) {
            throw new IOException("Message too large for selected image. Available bits: " + availableBits + ", required: " + messageBytes.length + ".");
        }

        i = 0;
        for (int y = 0; y < height && i < messageBytes.length; y++) {
            for (int x = 0; x < width && i < messageBytes.length; x++) {
                int p = image2.getRGB(x, y);

                a = (p >> 24) & 0xff;
                r = (p >> 16) & 0xff;
                g = (p >> 8) & 0xff;
                b = p & 0xff;

                r = r & 0xFE;
                g = g & 0xFE;
                b = b & 0xFE;

                if (i < messageBytes.length) {
                    r = r | messageBytes[i++];
                }
                if (i < messageBytes.length) {
                    g = g | messageBytes[i++];
                }
                if (i < messageBytes.length) {
                    b = b | messageBytes[i++];
                }

                p = (a << 24) | (r << 16) | (g << 8) | b;
                image2.setRGB(x, y, p);
            }
        }

        if (outputPath == null || outputPath.isBlank()) {
            outputPath = new File(original.getParentFile(), "hidden.png").getAbsolutePath();
        } else if (!outputPath.toLowerCase().endsWith(".png")) {
            outputPath = outputPath + ".png";
        }

        File outputFile = new File(outputPath);
        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Could not create output directory: " + parent.getAbsolutePath());
        }

        ImageIO.write(image2, "png", outputFile);
        return outputFile;
    }

    public String decode(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("File does not exist.");
        }

        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("Could not read image file.");
        }

        StringBuilder binaryMessage = new StringBuilder();
        width = image.getWidth();
        height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                r = (p >> 16) & 0xff;
                g = (p >> 8) & 0xff;
                b = p & 0xff;

                binaryMessage.append(r & 1);
                binaryMessage.append(g & 1);
                binaryMessage.append(b & 1);
            }
        }

        StringBuilder message = new StringBuilder();
        for (int j = 0; j + 8 <= binaryMessage.length(); j += 8) {
            String byteString = binaryMessage.substring(j, j + 8);
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) {
                break;
            }
            message.append((char) charCode);
        }

        return message.length() == 0 ? null : message.toString();
    }

    public int[] Text2Binary(String Message) {
        StringBuilder binary = new StringBuilder();
        for (char c : Message.toCharArray()) {
            charBinary = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(charBinary);
        }
        binary.append("00000000");
        messageBytes = new int[binary.length()];
        for (int j = 0; j < binary.length(); j++) {
            messageBytes[j] = Character.getNumericValue(binary.charAt(j));
        }
        return messageBytes;
    }
}
