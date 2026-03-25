import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BitChange {
    private static final byte[] MAGIC = "BITCHANGE".getBytes(StandardCharsets.UTF_8);

    public static File encode(File original, String message) throws IOException {
        byte[] content = readAllBytes(original);
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer = ByteBuffer.allocate(content.length + MAGIC.length + 4 + msgBytes.length);
        buffer.put(content);
        buffer.put(MAGIC);
        buffer.putInt(msgBytes.length);
        buffer.put(msgBytes);

        String origName = original.getAbsolutePath();
        System.out.print("Enter new file name (without the extension or path): ");
        Scanner S = new Scanner(System.in);
        String outName = origName.substring(0, origName.lastIndexOf(File.separator) + 1) + S.nextLine() + origName.substring(origName.lastIndexOf('.'));
        File output = new File(outName);

        try (FileOutputStream fos = new FileOutputStream(output, false)) {
            fos.write(buffer.array());
            fos.flush();
        }

        return output;
    }

    public static String decode(File file) throws IOException {
        byte[] content = readAllBytes(file);
        if (content.length < MAGIC.length + 4) {
            return null;
        }

        for (int i = content.length - MAGIC.length - 4; i >= 0; i--) {
            if (matchesMagic(content, i)) {
                int lenOffset = i + MAGIC.length;
                int msgLen = ByteBuffer.wrap(content, lenOffset, 4).getInt();
                int msgOffset = lenOffset + 4;
                if (msgOffset + msgLen > content.length) {
                    return null;
                }
                return new String(content, msgOffset, msgLen, StandardCharsets.UTF_8);
            }
        }

        return null;
    }

    private static boolean matchesMagic(byte[] arr, int offset) {
<<<<<<< HEAD
        if (offset + MAGIC.length > arr.length) {
            return false;
        }
        for (int i = 0; i < MAGIC.length; i++) {
            if (arr[offset + i] != MAGIC[i]) {
                return false;
            }
=======
        if (offset + MAGIC.length > arr.length) return false;
        for (int i = 0; i < MAGIC.length; i++) {
            if (arr[offset + i] != MAGIC[i]) return false;
>>>>>>> 896005da52a3ec375673786117ec2e96cd51faab
        }
        return true;
    }

    private static byte[] readAllBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            return baos.toByteArray();
        }
    }
}
