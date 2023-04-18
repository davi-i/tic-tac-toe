package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToeInterface extends Remote {
  public PlayerIdInterface enterGame(PlayerInterface player, String name) throws RoomFullException, RemoteException;


  public void exitGame(PlayerIdInterface id) throws RemoteException;
}
