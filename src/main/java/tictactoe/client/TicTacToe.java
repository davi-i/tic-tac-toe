package tictactoe.client;

import java.rmi.Naming;
import java.util.Scanner;

import tictactoe.shared.Board;
import tictactoe.shared.TestServerInterface;
import tictactoe.shared.TicTacToeInterface;

/**
 * Hello world!
 *
 */
public class TicTacToe {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "10.9.99.230");
            TicTacToeInterface server = (TicTacToeInterface) Naming
                    .lookup("rmi://" + args[1] + ":" + args[2] + "/TicTacToe");
            // TODO: enter name with scanner
            Player player = new Player(server, args[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
