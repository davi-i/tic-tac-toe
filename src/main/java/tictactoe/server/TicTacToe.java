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
            TestServer server = new TestServer();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://10.9.99.230/TicTacToe", server);

            System.out.println("Waiting for players...");

            Board board = new Board();
            System.out.println(board);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
