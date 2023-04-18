package tictactoe.shared;

import java.rmi.Remote;

import tictactoe.server.PlayerSymbol;
import tictactoe.server.TileState;

public interface PlayerIdInterface extends Remote {
    public PlayerSymbol getSymbol();
    
    public TileState getTile();
}
