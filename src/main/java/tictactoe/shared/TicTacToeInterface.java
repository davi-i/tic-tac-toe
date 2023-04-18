package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tictactoe.server.PlayerId;

public interface TicTacToeInterface extends Remote {
  public PlayerIdInterface enterGame(PlayerInterface player, String name) throws RoomFullException, RemoteException;

  public MoveResult makeMove(PlayerId id, int pos) throws RemoteException;

  public void exitGame(PlayerId id) throws RemoteException;
}
