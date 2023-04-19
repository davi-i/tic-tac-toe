package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tictactoe.shared.Board;
import tictactoe.shared.Message;
import tictactoe.shared.PlayerInterface;

public class Player extends UnicastRemoteObject implements PlayerInterface {
  private PlayerId id;
  private PlayerInterface player;
  private String name;

  public Player(PlayerId id, PlayerInterface player, String name) throws RemoteException {
    super();
    this.id = id;
    this.player = player;
    this.name = name;
  }

  @Override
  public void setOpponentName(String name) throws RemoteException {
    player.setOpponentName(name);
  }

  public String getName() {
    return name;
  }

  public PlayerId getId() {
    return this.id;
  }

  @Override
  public void updateBoard(Board board) throws RemoteException {
    player.updateBoard(board);
  }

  @Override
  public int getMove() throws RemoteException {
    return player.getMove();
  }

  @Override
  public void sendMessage(Message message) throws RemoteException {
    player.sendMessage(message);
  }

  @Override
  public void incrementOpponentScore() throws RemoteException {
    player.incrementOpponentScore();
  }

  @Override
  public void resetOpponentScore() throws RemoteException {
    player.resetOpponentScore();
  }

  @Override
  public void incrementScore() throws RemoteException {
    player.incrementScore();
  }

  @Override
  public void resetScore() throws RemoteException {
    player.resetScore();
  }
}
