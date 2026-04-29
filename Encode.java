//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Encode {
    private File file = null;
    private String Message = null;
    private String path = null;
    private String filepath = null;
    private Path AbsolutePath = null;
    private final File nofile = new File("nofile");

    public void Encoding(Scanner S) {
        System.out.print("Enter the file path of the image: ");
        path = S.nextLine(); //Path to the target image ex: "C:\CS CLASSES\CS 3710\team-project-real-visualcryptography\1000009590.jpg"
        filepath = path.replace("\"", ""); //Automatically removes "" around path
        System.out.println();
        AbsolutePath = Paths.get(filepath).toAbsolutePath(); //Gets the absolute path
        if(java.nio.file.Files.exists(AbsolutePath)){ //Checks if the files exists
            System.out.println("File exists at: " + AbsolutePath);
            readFile(AbsolutePath.toString(), S); //Sends to readFile
        } else {
            System.out.println("File does not exist at: " + AbsolutePath);
        }
    }

    public void readFile(String filetext, Scanner S) {
        BitChange bitchange = new BitChange();

        //Changes String to File and checks if successful
        try {
            file = new File(filetext);
            //Checks that the file successfully changed
            if (!file.exists()) {
                System.out.println("File does not exist: " + filetext);
                return;
            }

            System.out.println("File loaded successfully: " + filetext); 
            System.out.print("Enter the message to hide in the file: ");
            Message = S.nextLine(); //Takes user input
            File output = bitchange.encode(file, Message, S); //Sends file, message, and the scanner to bitchange
            if(!output.equals(nofile)){
                System.out.println("Message hidden successfully in new file: " + output.getAbsolutePath());
            }
            else{
                System.out.println("File is not supported..." + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}