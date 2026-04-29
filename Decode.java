//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Decode {
    private File file = null;
    private String path = null;
    private String filepath = null;
    private Path AbsolutePath = null;

    public void Decoding(Scanner S) {
        System.out.print("Enter the file path of the image: ");
        path = S.nextLine(); //Path to the target image with a message ex: "C:\CS CLASSES\CS 3710\team-project-real-visualcryptography\New.jpg"
        filepath = path.replace("\"", ""); //Automatically removes "" around path
        System.out.println();
        AbsolutePath = Paths.get(filepath).toAbsolutePath(); //Gets the absolute path
        if(java.nio.file.Files.exists(AbsolutePath)){ //Checks if the file exists
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

            String message = bitchange.decode(file); //Gets decoded message
            if (message == null) { //Checks that there is a message
                System.out.println("No hidden message found in file.");
            } else {
                System.out.println("Decoded message: " + message + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}
