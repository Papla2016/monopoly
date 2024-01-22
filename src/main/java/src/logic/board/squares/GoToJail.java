package src.logic.board.squares;

public class GoToJail extends Square {
    private final int jailPosition;

    public GoToJail(int position, int jailPosition) {
        super("Go To Jail", position);
        this.jailPosition = jailPosition;
    }

    public int getJailPosition() {
        return jailPosition;
    }
}
