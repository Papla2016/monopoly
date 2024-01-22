package src.logic.decks.cards;

import src.logic.board.Board;
import src.logic.board.squares.Square;
import src.logic.player.Player;

public class MoveToCard extends Card {
    private final Square square;

    public MoveToCard(CardType cardType, String text, Square square) {
        super(cardType, text);
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }


    public int getSteps(Player player, Board board) {
        int steps = square.getPosition() - player.getBoardPosition().getValue();
        if (steps < 0) {
            steps += board.getBoardSquares().length;
        }
        return steps;
    }
}
