package src.logic.decks.cards;

import src.logic.board.Board;
import src.logic.board.squares.Square;
import src.logic.player.Player;

public class NearestSquareCard extends Card {
    private final String squareType;

    public NearestSquareCard(CardType cardType, String text, String squareType) {
        super(cardType, text);
        this.squareType = squareType;
    }

    /**
     * Calculates the number of steps the {@link Player} needs to take to reach the nearest {@link Square} of the given type.
     *
     * @param player the {@link Player} who is moving
     * @param board the game {@link Board}
     * @return the number of steps the {@link Player} needs to take to reach the nearest {@link Square} of the given type
     */
    public int getSteps(Player player, Board board) {
        int playerBoardPosition = player.getBoardPosition().getValue();
        Square square = board.getNearestSquareOfType(playerBoardPosition, squareType);

        int steps = square.getPosition() - playerBoardPosition;
        if (steps < 0) {
            steps += board.getBoardSquares().length;
        }

        return steps;
    }
}
