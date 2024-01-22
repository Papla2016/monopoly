package src.logic;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import src.logic.board.Board;
import src.logic.board.squares.Cards;
import src.logic.board.squares.Ownable;
import src.logic.board.squares.Square;
import src.logic.board.squares.Utility;
import src.logic.decks.cards.Card;
import src.logic.decks.cards.CardType;
import src.logic.decks.cards.PayMoneyCard;
import src.logic.decks.cards.ReceiveMoneyCard;
import src.logic.die.Die;
import src.logic.player.Player;
import javafx.beans.property.SimpleObjectProperty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private Board board;
    private Die die;
    private ArrayList<Player> players;
    private final SimpleObjectProperty<Player> activePlayer;

    public GameModel() {
        board = new Board();
        die = new Die();
        players = new ArrayList<>();
        activePlayer = new SimpleObjectProperty<>();
    }

    public GameModel(ArrayList<Player> players, Player activePlayer, Board board) {
        this.board = board;
        die = new Die();
        this.players = players;
        this.activePlayer = new SimpleObjectProperty<>(activePlayer);
    }

    /**
     * Sets the next player in the game as active.
     *
     * <p>This method sets the next player in the game as active. The player who is currently active
     * is determined based on the index of the current active player in the list of players. The next
     * player is determined by incrementing the current index by 1, and wrapping around to the start of
     * the list if the index exceeds the number of players. The method also skips the turns of bankrupt
     * players by checking if the player at the current index is bankrupt, and incrementing the index
     * until a non-bankrupt player is found.</p>
     */
    public void setNextPlayerAsActive() {

        int currentIndex = players.indexOf(activePlayer.get());
        currentIndex = (currentIndex + 1) % players.size();

        // bankrupt players' turns will be skipped
        while (players.get(currentIndex).isBankrupt().getValue()) {
            currentIndex = (currentIndex + 1) % players.size();
        }

        activePlayer.set(players.get(currentIndex));
    }

    /**
     * Checks if the game has ended.
     *
     * <p>This method checks if the game has ended by counting the number of bankrupt players in the game.
     * If the number of bankrupt players is equal to the number of players minus one, the game is
     * considered to have ended. A game is considered to have ended when only one player remains
     * solvent.</p>
     *
     * @return true if the game has ended, false otherwise.
     */
    public boolean hasGameEnded() {

        int numberOfBankruptPlayers = 0;
        for (Player player : players) {
            if (player.isBankrupt().getValue()) numberOfBankruptPlayers++;
        }
        return numberOfBankruptPlayers == (players.size() - 1);
    }

    /**
     * Returns the winner of the game.
     *
     * <p>This method returns the winner of the game, which is the last player remaining solvent. The
     * method iterates over the list of players in the game, and returns the first player that is not
     * bankrupt. If no player is found, the method returns null.</p>
     *
     * @return the winner of the game, or null if no winner is found.
     */
    public Player getWinner() {

        for (Player player : players) {
            if (!player.isBankrupt().getValue()) return player;
        }
        return null;
    }

    private ArrayList<Player> configurePlayers(int numberOfPlayers) {

        if (numberOfPlayers < 2 || numberOfPlayers > 6) {
            throw new IllegalArgumentException("The number of players must be within the range of 2-6. Current number: "+numberOfPlayers);
        }

        String[] SPRITES = new String[]{"boot.png", "car.png", "dog.png", "hat.png", "ship.png", "iron.png"};
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            listOfPlayers.add(new Player("Player " + i, SPRITES[i-1]));
        }

        // choose a player that will start
        activePlayer.set(listOfPlayers.get(0));

        return listOfPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public Die getDie() {
        return die;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public void startNewGame(int numberOfPlayers) {

        players = configurePlayers(numberOfPlayers);
        board = new Board();
        die = new Die();
    }

    public Player getActivePlayer() {
        return activePlayer.get();
    }

    public SimpleObjectProperty<Player> getActivePlayerProperty() {
        return activePlayer;
    }


    public boolean steppedOnOwnable(Ownable ownable, Player player) {

        if (!ownable.isOwned() || player.getOwnedSquares().contains(ownable)) return true;

        if (ownable instanceof Utility utility) {
            player.changeMoneyBalanceBy(-utility.getRent(die));
            ownable.getOwner().changeMoneyBalanceBy(utility.getRent(die));
        } else {
            player.changeMoneyBalanceBy(-ownable.getRent());
            ownable.getOwner().changeMoneyBalanceBy(ownable.getRent());
        }
        return false;
    }


    public Card steppedOnCards(Cards cards, Player player) {

        Card card = null;
        if (cards.getCardType().equals(CardType.CHANCE)) {
            card = board.getChanceDeck().drawCard();
        } else if (cards.getCardType().equals(CardType.COMMUNITY_CHEST)) {
            card = board.getCommunityChestDeck().drawCard();
        }
        executeCard(player, card);
        return card;
    }

    private void executeCard(Player player, Card card) {

        if (card instanceof PayMoneyCard payMoneyCard) {
            payMoneyCard.execute(player, players);
        } else if (card instanceof ReceiveMoneyCard receiveMoneyCard) {
            receiveMoneyCard.execute(player, players);
        }
    }

    private JsonObject toJsonObject() {

        JsonArray playersJsonArray = new JsonArray();
        for (Player player : players) {
            playersJsonArray.add(player.toJsonObject());
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("activePlayer", activePlayer.getValue().getName());
        jsonObject.put("players", playersJsonArray);

        return jsonObject;
    }


    public void save() {

        JsonObject jsonObject = toJsonObject();
        try (FileWriter fileWriter = new FileWriter("saved-game.json")) {
            fileWriter.write(jsonObject.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static GameModel load() throws FileNotFoundException, JsonException {

        FileReader fileReader = new FileReader("saved-game.json");
        JsonObject jsonObject = (JsonObject) Jsoner.deserialize(fileReader);
        return fromJsonObject(jsonObject);
    }

    private static Player getPlayerByName(ArrayList<Player> listOfPlayers, String name) {

        for (Player player : listOfPlayers) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }

    private static GameModel fromJsonObject(JsonObject jsonObject) {

        Board board = new Board();
        ArrayList<Player> playerArrayList = new ArrayList<>();

        JsonArray playersJsonArray = (JsonArray) jsonObject.get("players");
        for (int i = 0; i < playersJsonArray.size(); i++) {
            Player player = Player.fromJsonObject((JsonObject) playersJsonArray.get(i), board, i);

            // set the owner of the square
            for (Ownable ownable : player.getOwnedSquares()) {
                ownable.setOwner(player);
            }

            playerArrayList.add(player);
        }

        String activePlayerName = (String) jsonObject.get("activePlayer");
        Player activePlayer = getPlayerByName(playerArrayList, activePlayerName);

        return new GameModel(playerArrayList, activePlayer, board);
    }
}
