package tictactoe.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Hello world!
 *
 */
public class TicTacToe {
    public static void main(String[] args) {
        try {
            TestServer server = new TestServer();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/TicTacToe", server);

            System.out.println("Waiting for players...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
