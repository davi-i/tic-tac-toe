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
  private TicTacToeServer server;

  public Player(TicTacToeServer server, PlayerId id, PlayerInterface player, String name) throws RemoteException {
    super();
    this.server = server;
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
    try {
      player.updateBoard(board);
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public int getMove() throws RemoteException {
    try {
      return player.getMove();
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public void sendMessage(Message message) throws RemoteException {
    try {
      player.sendMessage(message);
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public void incrementOpponentScore() throws RemoteException {
    try {
      player.incrementOpponentScore();
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public void resetOpponentScore() throws RemoteException {
    try {
      player.resetOpponentScore();
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public void incrementScore() throws RemoteException {
    try {
      player.incrementScore();
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }

  @Override
  public void resetScore() throws RemoteException {
    try {
      player.resetScore();
    } catch (RemoteException e) {
      server.exitGame(id);
      throw e;
    }
  }
}
