package tictactoe.client;

import tictactoe.shared.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import tictactoe.server.PlayerId;
import tictactoe.shared.Board;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.RoomFullException;
import tictactoe.shared.TicTacToeInterface;

public class Player extends UnicastRemoteObject implements PlayerInterface {

    protected static Scanner scanner = new Scanner(System.in);
    private TicTacToeInterface server;
    private Board board;
    private int score;
    private int opponentScore;
    private String myName;
    private String opponentName;
    private PlayerId id;

    public Player(TicTacToeInterface server, String name) throws RoomFullException, RemoteException {
        super();
        this.server = server;
        this.myName = name;
        this.id = server.enterGame(this, name);
    }

    private void printMap() {
        String scoreStr = "| SCORE | " + myName + ": " + score + " VS " + opponentName + ": " + opponentScore + " |";
        System.out.println("-".repeat(scoreStr.length()));
        System.out.println(scoreStr);
        System.out.println("-".repeat(scoreStr.length()));
        System.out.println("\n");
        System.out.println(board);
    }

    @Override
    public void setOpponentName(String name) throws RemoteException {
        this.opponentName = name;
    }

    @Override
    public void updateBoard(Board board) throws RemoteException {
        this.board = board;
        printMap();
    }

    @Override
    public int getMove() throws RemoteException {
        System.out.println("[Digite sua jogada] OU ['q' para encerrar o jogo]:");

        while (true) {
            String input;
            input = scanner.nextLine();

            // TODO: find a better way to do this
            if (input.equals("q")) {
                server.exitGame(id);
                scanner.close();
                System.exit(0);
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número ou 'q'");
            }
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void incrementOpponentScore() throws RemoteException {
        opponentScore++;
    }

    @Override
    public void resetOpponentScore() throws RemoteException {
        opponentScore = 0;
    }

    @Override
    public void incrementScore() throws RemoteException {
        score++;
    }

    @Override
    public void resetScore() throws RemoteException {
        score = 0;
    }

}
