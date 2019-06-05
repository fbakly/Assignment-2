import java.util.Scanner;

public class UserIO {

    private int userInput;

    public void printMenu() {
        System.out.println("\n0. Exit\n1. Post Tweet\n2. Send Direct Message");
        System.out.print("\nOption: ");
    }

    public int getUserInput() {
        var scanner = new Scanner(System.in);
        try {
            this.userInput = scanner.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInput;
    }

}
