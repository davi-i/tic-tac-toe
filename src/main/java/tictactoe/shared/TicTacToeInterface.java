package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tictactoe.server.PlayerId;

public interface TicTacToeInterface extends Remote {
  public PlayerId enterGame(PlayerInterface player, String name)
      throws RoomFullException, RemoteException, NameAlreadyUsedException;

  public void exitGame(PlayerId id) throws RemoteException;
}
