package src.logic.board.squares;

import src.logic.player.Player;

public interface Ownable {
    int getRent();
    boolean isOwned();
    int getPurchasePrice();
    Player getOwner();
    void setOwner(Player player);
}
