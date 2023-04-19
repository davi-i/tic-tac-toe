package tictactoe.client;

import java.rmi.Naming;

import tictactoe.shared.NameAlreadyUsedException;
import tictactoe.shared.RoomFullException;
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
            while (true) {
                try {
                    System.out.print("Enter yout name: ");
                    String name = Player.scanner.nextLine();
                    new Player(server, name);
                    break;
                } catch (RoomFullException e) {
                    System.out.println("There are already two player in the game");
                    System.exit(1);
                } catch (NameAlreadyUsedException e) {
                    System.out.println("There is someone using this name already, please type another.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
