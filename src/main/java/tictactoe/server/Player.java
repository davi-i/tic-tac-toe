package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tictactoe.shared.Board;
import tictactoe.shared.GameState;
import tictactoe.shared.PlayerInterface;

public class Player extends UnicastRemoteObject implements PlayerInterface {
  private PlayerId id;
  private PlayerInterface player;
  private String name;
  private int score;

  public Player(PlayerId id, PlayerInterface player, String name) throws RemoteException {
    super();
    this.id = id;
    this.player = player;
    this.name = name;
    this.score = 0;
  }

  @Override
  public void changeState(GameState state, Board board, int player1Score, int player2Score) throws RemoteException {
    player.changeState(state, board, player1Score, player2Score);
  }

  @Override
  public void setOpponetName(String name) throws RemoteException {
    player.setOpponetName(name);
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void addScore() {
    score++;
  }

  public PlayerId getId() {
    return this.id; 
  }

  @Override
  public int getMove(Board board) throws RemoteException {
    return player.getMove(board);
  }
}
