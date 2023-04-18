package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {

  public int getMove(Board board) throws RemoteException;
  public void changeState(GameState state, Board board, int player1Score, int player2Score) throws RemoteException;

  public void setOpponetName(String name) throws RemoteException;

}
