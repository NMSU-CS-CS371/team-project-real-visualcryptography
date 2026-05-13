//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Encode {
    public File encode(String path, String message, String outputPath) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IOException("No file path provided.");
        }

        String filepath = path.replace("\"", "").trim();
        Path absolutePath = Paths.get(filepath).toAbsolutePath();

        if (!java.nio.file.Files.exists(absolutePath)) {
            throw new IOException("File does not exist: " + absolutePath);
        }

        File original = new File(absolutePath.toString());
        BitChange bitChange = new BitChange();
        return bitChange.encode(original, message, outputPath);
    }
}