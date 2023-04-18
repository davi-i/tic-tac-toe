package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tictactoe.shared.GameState;
import tictactoe.shared.PlayerIdInterface;

public class PlayerId extends UnicastRemoteObject implements PlayerIdInterface {
  private PlayerSymbol symbol;

public PlayerId(PlayerSymbol symbol) throws RemoteException {
    super();
    this.symbol = symbol;
  }

@Override
public PlayerSymbol getSymbol() throws RemoteException {
    return symbol;
  }

@Override
public TileState getTile() throws RemoteException {
    switch (symbol) {
      case CROSSES:
        return TileState.CROSS;
      case NOUGHTS:
        return TileState.NOUGHT;
      default:
        throw new RuntimeException();
    }
  }
}
