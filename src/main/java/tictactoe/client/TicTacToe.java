package tictactoe.client;

import java.rmi.Naming;

import tictactoe.shared.TicTacToeInterface;

/**
 * Hello world!
 *
 */
public class TicTacToe {
    public static void main(String[] args) {
        try {
            TicTacToeInterface server = (TicTacToeInterface) Naming
                    .lookup("rmi://" + args[1] + ":" + args[2] + "/TicTacToe");
            System.out.print("Enter yout name: ");
            String name = Player.scanner.nextLine();
            new Player(server, name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
