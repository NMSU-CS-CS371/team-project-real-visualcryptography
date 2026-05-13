//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Decode {
    public String decode(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IOException("No file path provided.");
        }

        String filepath = path.replace("\"", "").trim();
        Path absolutePath = Paths.get(filepath).toAbsolutePath();

        if (!java.nio.file.Files.exists(absolutePath)) {
            throw new IOException("File does not exist: " + absolutePath);
        }

        File file = new File(absolutePath.toString());
        BitChange bitChange = new BitChange();
        return bitChange.decode(file);
    }
}

