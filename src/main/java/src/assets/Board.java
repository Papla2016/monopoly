package src.assets;

import java.util.Vector;

public class Board {
    private Vector<Property> tiles;

    public Vector<Player> getPlayers() {
        return players;
    }

    private Vector<Player> players;

    public Vector<Property> getTiles() {
        return tiles;
    }

    Board (Vector<Property> tiles, Vector<Player> players){
        this.players = players;
        this.tiles = tiles;
    }

 //TODO добавить обработку поля
}
