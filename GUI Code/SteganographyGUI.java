import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class SteganographyGUI extends JFrame {
    private final Color backgroundColor = new Color(8, 12, 20);
    private final Color panelColor = new Color(16, 24, 36);
    private final Color accentColor = new Color(63, 255, 123);
    private final Color textColor = new Color(140, 255, 145);
    private final Font monoFont = new Font("Consolas", Font.PLAIN, 14);
    private final Font titleFont = new Font("Consolas", Font.BOLD, 16);

    private final JTextField encodePathField = new JTextField(30);
    private final JTextField messageField = new JTextField(30);
    private final JTextField outputPathField = new JTextField(30);
    private final JTextField decodePathField = new JTextField(30);
    private final JTextArea resultArea = new JTextArea(8, 50);

    public SteganographyGUI() {
        setTitle("Image Steganography");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(backgroundColor);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setForeground(textColor);
        tabbedPane.setFont(new Font("Consolas", Font.BOLD, 18));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.setFocusable(false);
        tabbedPane.setRequestFocusEnabled(false);
        tabbedPane.addTab(null, createEncodePanel());
        tabbedPane.addTab(null, createDecodePanel());
        tabbedPane.setTabComponentAt(0, createTabLabel("ENCODE"));
        tabbedPane.setTabComponentAt(1, createTabLabel("DECODE"));
        tabbedPane.setBackgroundAt(0, panelColor);
        tabbedPane.setForegroundAt(0, textColor);
        tabbedPane.setBackgroundAt(1, panelColor);
        tabbedPane.setForegroundAt(1, textColor);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(Color.black);
        resultArea.setForeground(textColor);
        resultArea.setFont(monoFont);
        resultArea.setBorder(BorderFactory.createLineBorder(accentColor));

        styleField(encodePathField);
        styleField(messageField);
        styleField(outputPathField);
        styleField(decodePathField);

        add(tabbedPane, BorderLayout.CENTER);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createEncodePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(panelColor);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), "ENCODE", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, titleFont, accentColor));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createStyledLabel("Input Image:"), gbc);

        gbc.gridx = 1;
        panel.add(encodePathField, gbc);

        gbc.gridx = 2;
        JButton browseEncodeButton = new JButton("Browse...");
        styleButton(browseEncodeButton);
        browseEncodeButton.addActionListener(e -> browseEncodeFile());
        panel.add(browseEncodeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createStyledLabel("Message to hide:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(messageField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createStyledLabel("Output file (PNG only):"), gbc);

        gbc.gridx = 1;
        panel.add(outputPathField, gbc);

        gbc.gridx = 2;
        JButton browseSaveButton = new JButton("Save As...");
        styleButton(browseSaveButton);
        browseSaveButton.addActionListener(e -> browseSaveFile());
        panel.add(browseSaveButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton encodeButton = new JButton("Encode Image");
        styleButton(encodeButton);
        encodeButton.addActionListener(e -> encodeImage());
        panel.add(encodeButton, gbc);
        gbc.gridwidth = 1;

        return panel;
    }

    private JPanel createDecodePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(panelColor);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), "DECODE", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, titleFont, accentColor));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createStyledLabel("Encoded Image:"), gbc);

        gbc.gridx = 1;
        panel.add(decodePathField, gbc);

        gbc.gridx = 2;
        JButton browseDecodeButton = new JButton("Browse...");
        styleButton(browseDecodeButton);
        browseDecodeButton.addActionListener(e -> browseDecodeFile());
        panel.add(browseDecodeButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton decodeButton = new JButton("Decode Message");
        styleButton(decodeButton);
        decodeButton.addActionListener(e -> decodeImage());
        panel.add(decodeButton, gbc);

        return panel;
    }

    private void browseEncodeFile() {
        FileDialog dialog = new FileDialog(this, "Select an image file", FileDialog.LOAD);
        dialog.setDirectory(getInitialDirectory());
        dialog.setFilenameFilter((dir, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".bmp") || lower.endsWith(".gif");
        });
        dialog.setVisible(true);
        String file = dialog.getFile();
        if (file != null) {
            encodePathField.setText(dialog.getDirectory() + file);
        }
    }

    private void browseDecodeFile() {
        FileDialog dialog = new FileDialog(this, "Select an image file", FileDialog.LOAD);
        dialog.setDirectory(getInitialDirectory());
        dialog.setFilenameFilter((dir, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".bmp") || lower.endsWith(".gif");
        });
        dialog.setVisible(true);
        String file = dialog.getFile();
        if (file != null) {
            decodePathField.setText(dialog.getDirectory() + file);
        }
    }

    private void browseSaveFile() {
        FileDialog dialog = new FileDialog(this, "Save encoded image as", FileDialog.SAVE);
        dialog.setDirectory(getInitialDirectory());
        dialog.setFile("*.png");
        dialog.setVisible(true);
        String file = dialog.getFile();
        if (file != null) {
            String path = dialog.getDirectory() + file;
            if (!path.toLowerCase().endsWith(".png")) {
                path += ".png";
            }
            outputPathField.setText(path);
        }
    }

    private String getInitialDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            return "::{20D04FE0-3AEA-1069-A2D8-08002B30309D}";
        }
        return System.getProperty("user.dir");
    }

    private void encodeImage() {
        resultArea.setText("");
        String inputPath = encodePathField.getText().trim();
        String message = messageField.getText();
        String outputPath = outputPathField.getText().trim();

        if (inputPath.isEmpty()) {
            appendResult("Please select an input image file to encode.");
            return;
        }
        if (message.isEmpty()) {
            appendResult("Please enter a message to hide.");
            return;
        }
        if (outputPath.isEmpty()) {
            appendResult("Please choose an output file to save.");
            return;
        }

        try {
            File outputFile = new Encode().encode(inputPath, message, outputPath);
            appendResult("Message hidden successfully in: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            appendResult("Error encoding image: " + e.getMessage());
        }
    }

    private void decodeImage() {
        resultArea.setText("");
        String inputPath = decodePathField.getText().trim();

        if (inputPath.isEmpty()) {
            appendResult("Please select an image file to decode.");
            return;
        }

        try {
            String message = new Decode().decode(inputPath);
            if (message == null || message.isEmpty()) {
                appendResult("No hidden message found in file.");
            } else {
                appendResult("Decoded message: " + message);
            }
        } catch (IOException e) {
            appendResult("Error decoding image: " + e.getMessage());
        }
    }

    private void appendResult(String text) {
        resultArea.append(text + "\n");
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(monoFont);
        return label;
    }

    private void styleField(JTextField field) {
        field.setBackground(Color.black);
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setFont(monoFont);
        field.setBorder(BorderFactory.createLineBorder(accentColor));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(40, 190, 105));
        button.setForeground(Color.black);
        button.setFont(monoFont);
        button.setBorder(BorderFactory.createLineBorder(accentColor, 2));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBorderPainted(false);
    }

    private JLabel createTabLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.BOLD, 18));
        label.setForeground(textColor);
        label.setBackground(panelColor);
        label.setOpaque(true);
        label.setFocusable(false);
        label.setRequestFocusEnabled(false);
        label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        return label;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to default look and feel if unavailable.
        }

        SwingUtilities.invokeLater(() -> {
            SteganographyGUI frame = new SteganographyGUI();
            frame.setVisible(true);
        });
    }
}
