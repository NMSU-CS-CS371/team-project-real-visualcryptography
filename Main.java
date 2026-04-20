import java.util.*; 

public class Main {

    public static void main(String[] args) {
        Decode decode = new Decode();
        Encode encode = new Encode();
        Scanner S = new Scanner(System.in);

        boolean done = true;
        while(done){
            System.out.println("Choose an option:");
            System.out.println("1. Encode a message into an image");
            System.out.println("2. Decode a message from an image");
            System.out.println("3. Exit");
            int choice = S.nextInt();
            //S.nextLine();
            
            switch (choice) {
                case 1:
                    encode.Encoding();
                    break;
                case 2:
                    decode.Decoding();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    done = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }   
        S.close();
    }
}