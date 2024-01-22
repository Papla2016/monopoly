package src.logic.board.squares;

import src.logic.decks.cards.CardType;

public class Cards extends Square {
    private final CardType cardType;

    public Cards(String name, int position, CardType cardType) {
        super(name, position);
        this.cardType = cardType;
    }

    public CardType getCardType() {
        return cardType;
    }
}
