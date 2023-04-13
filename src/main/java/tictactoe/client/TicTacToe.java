package tictactoe.client;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class TicTacToe {
    public static void main(String[] args) {
        try {
            TestServerInterface server = (TestServerInterface) Naming.lookup("rmi://localhost:1099/TicTacToe");
            while (true) {
                server.iKnowYou();
                Scanner scanner = new Scanner(System.in);

                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // wait for user to press Enter

                System.out.println("Program continued.");
                // add code here to continue the program

                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
