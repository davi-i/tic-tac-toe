package tictactoe.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tictactoe.server.PlayerSymbol;
import tictactoe.server.TileState;

public interface PlayerIdInterface extends Remote {
    public PlayerSymbol getSymbol() throws RemoteException ;
    
    public TileState getTile() throws RemoteException;
}
