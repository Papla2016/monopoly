package src.logic.decks.cards;

import src.logic.player.Player;

import java.util.ArrayList;

public class PayMoneyCard extends Card {
    private final int amount;
    private final boolean perPlayer;

    public PayMoneyCard(CardType cardType, String text, int amount) {
        super(cardType, text);
        this.amount = amount;
        perPlayer = false;
    }

    public PayMoneyCard(CardType cardType, String text, int amount, boolean perPlayer) {
        super(cardType, text);
        this.amount = amount;
        this.perPlayer = perPlayer;
    }

    /**
     * Executes the action of the {@link Card} on the player and/or other players.
     *
     * @param player the {@link Player} who drew the {@link Card}
     * @param players the list of players in the game
     */
    public void execute(Player player, ArrayList<Player> players) {
        if (!perPlayer) {
            player.changeMoneyBalanceBy(-amount);
            return;
        }

        // pay every player
        for (Player p : players) {
            if (p.equals(player) || p.isBankrupt().getValue()) continue;

            p.changeMoneyBalanceBy(amount);
            player.changeMoneyBalanceBy(-amount);
        }
    }
}