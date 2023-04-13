package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestServerInterface extends Remote {
  public void iKnowYou() throws RemoteException;
}
