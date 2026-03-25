import java.util.*; 

public class Main {

    public static void main(String[] args) {
        Decode decode = new Decode();
        Encode encode = new Encode();
        Scanner S = new Scanner(System.in);
        System.out.println("Choose an option:");
        System.out.println("1. Encode a message into an image");
        System.out.println("2. Decode a message from an image");
        int choice = S.nextInt();
        S.nextLine();

        switch (choice) {
            case 1:
                encode.Encoding();
                break;
            case 2:
                decode.Decoding();
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
                break;
        }
        S.close();
    }
}