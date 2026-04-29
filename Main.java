//Derek Hyman and Dante Stevens
//2026 Spring CSCI 3710 Software Development Project
//This is a Steganography program that takes an images and hides text in side the pixels

import java.util.*; 

public class Main {

    public static void main(String[] args) {
        Decode decode = new Decode(); 
        Encode encode = new Encode();

        boolean done = true;
        Scanner S = new Scanner(System.in);
        
        while(done){ //while loop; the program will not end unless the user chooses too
            //int choice = 0;
            System.out.println("Choose an option:");
            System.out.println("1. Encode a message into an image");
            System.out.println("2. Decode a message from an image");
            System.out.println("3. Exit");
           
            String s = S.nextLine();

            switch (s.charAt(0)) { //switch case
                case '1': //takes the user to encoding
                    encode.Encoding(S);
                    break;
                case '2': //takes the user to decoding
                    decode.Decoding(S);
                    break;
                case '3': //ends the program
                    System.out.println("Exiting...");
                    done = false;
                    break;
                default: // default choice
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        S.close();
    }
}