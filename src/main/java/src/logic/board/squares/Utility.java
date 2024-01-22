package src.logic.board.squares;

import src.logic.player.Player;
import src.logic.die.Die;

import java.util.logging.Logger;

public class Utility extends Square implements Ownable {
    private final static Logger LOGGER = Logger.getLogger(Utility.class.getName());
    private final int purchasePrice;
    private Player owner;

    public Utility(String name, int position) {
        super(name, position);
        this.purchasePrice = 150;
        this.owner = null;
    }

    @Override
    public int getRent() {
        throw new UnsupportedOperationException("Method not supported without arguments.");
    }

    public int getRent(Die die) {
        die.roll();
        if (owner.getNumberOfOwnedUtilities() > 1) {
            return 10 * die.getDieRollTotal();
        }
        return 4 * die.getDieRollTotal();
    }

    @Override
    public boolean isOwned() {
        return owner != null;
    }

    @Override
    public int getPurchasePrice() {
        return purchasePrice;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player player) {
        owner = player;

    }

    @Override
    public String toString() {
        String ownerText = (owner == null) ? "---" : owner.getName();
        return getName() + System.lineSeparator() + System.lineSeparator() +
                "Владелец: " + ownerText + System.lineSeparator() +
                "Рента за 1 сдание: $(dice * 4)" + System.lineSeparator() +
                "Рента за 2 сдания: $(dice * 10)" + System.lineSeparator() +
                "Цена покупки: $" + purchasePrice;
    }
}
