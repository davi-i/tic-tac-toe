package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {

  public int getMove(Board board) throws RemoteException;
  // public void changeState(GameState state, Board board, int player1Score, int
  // player2Score) throws RemoteException;

  public void setOpponentName(String name) throws RemoteException;

  public void sendMessage(Message message) throws RemoteException;

  public void incrementOpponentScore() throws RemoteException;

  public void resetOpponentScore() throws RemoteException;

  public void incrementScore() throws RemoteException;

  public void resetScore() throws RemoteException;

}
