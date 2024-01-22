package src.logic.board.squares;

import src.logic.player.Player;

import java.util.logging.Logger;

public class Railroad extends Square implements Ownable {
    private final static Logger LOGGER = Logger.getLogger(Railroad.class.getName());
    private final int rent;
    private Player owner;
    private final int purchasePrice;

    public Railroad(String name, int position, int rent) {
        super(name, position);
        this.rent = rent;
        this.purchasePrice = 200;
        this.owner = null;
    }

    @Override
    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    private int calculateRent(int numberOfOwnedRailroads) {
        return switch (numberOfOwnedRailroads) {
            case 2 -> (rent * 2);
            case 3 -> (rent * 4);
            case 4 -> (rent * 8);
            default -> rent;
        };
    }

    @Override
    public int getRent() {
        return calculateRent(owner.getNumberOfOwnedRailroads());
    }

    @Override
    public boolean isOwned() {
        return owner != null;
    }

    @Override
    public int getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public String toString() {
        String ownerText = (owner == null) ? "---" : owner.getName();
        return getName() + System.lineSeparator() + System.lineSeparator() +
                "Владелец: " + ownerText + System.lineSeparator() +
                "Рента за 1 сдание: $" + calculateRent(1) + System.lineSeparator() +
                "Рента за 2 сдания: $" + calculateRent(2) + System.lineSeparator() +
                "Рента за 3 сдания: $" + calculateRent(3) + System.lineSeparator() +
                "Рента за 4 сдания: $" + calculateRent(4) + System.lineSeparator() +
                "Цена покупки: $" + purchasePrice;
    }
}
