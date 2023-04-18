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
            System.setProperty("java.rmi.server.hostname", "10.9.99.230");
            TicTacToeServer server = new TicTacToeServer();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://10.9.99.230/TicTacToe", server);

            System.out.println("Waiting for players...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
