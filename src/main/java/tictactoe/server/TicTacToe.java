package tictactoe.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import tictactoe.shared.Board;

/**
 * Hello world!
 *
 */
public class TicTacToe {
    public static void main(String[] args) {
        try {
            TicTacToeServer server = new TicTacToeServer();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://0.0.0.0/TicTacToe", server);

            System.out.println("Waiting for players...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
