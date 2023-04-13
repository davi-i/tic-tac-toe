package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import tictactoe.shared.TestServerInterface;

public class TestServer extends UnicastRemoteObject implements TestServerInterface {
  public TestServer() throws RemoteException {
    super();
  }

  public void iKnowYou() throws RemoteException {
    try {
      String clientHost = RemoteServer.getClientHost();
      System.out.println("HE CALLED ME: " + clientHost);
    } catch (ServerNotActiveException e) {
      System.out.println("I DON'T KNOW WHO CALLED ME");
    }
  }
}
