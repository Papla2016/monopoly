package src.logic.board;

import src.logic.board.squares.*;
import src.logic.decks.ChanceDeck;
import src.logic.decks.CommunityChestDeck;
import src.logic.decks.cards.*;

public class Board {
    private final Square[] boardSquares;
    private final ChanceDeck chanceDeck;
    private final CommunityChestDeck communityChestDeck;

    public Board() {
        boardSquares = createBoard();
        chanceDeck = new ChanceDeck(createChanceCards());
        communityChestDeck = new CommunityChestDeck(createCommunityChestCards());

        chanceDeck.shuffle();
        communityChestDeck.shuffle();
    }

    private Square[] createBoard() {
        return new Square[]{
            new Go(0, 200),
            new Property("Автомобильный проезд", 1, PropertyGroup.BROWN, 2, 60),
            new Cards("Сокровище", 2, CardType.COMMUNITY_CHEST),
            new Property("Набережная инноваций", 3, PropertyGroup.BROWN, 4, 60),
            new Tax("НДФЛ", 4, 200),
            new Railroad("Чтящая Ж/Д", 5, 25),
            new Property("Бухта сёрфинга", 6, PropertyGroup.LIGHTBLUE, 6, 100),
            new Cards("Шанс!", 7, CardType.CHANCE),
            new Property("Аква-парк", 8, PropertyGroup.LIGHTBLUE, 6, 100),
            new Property("Речной вокзал", 9, PropertyGroup.LIGHTBLUE, 8, 120),
            new Jail("Тюрьма", 10),
            new Property("Сказочный замок", 11, PropertyGroup.PINK, 10, 140),
            new Utility("АЭС", 12),
            new Property("Проспект мечты", 13, PropertyGroup.PINK, 10, 140),
            new Property("Дворцовые сады", 14, PropertyGroup.PINK, 12, 160),
            new Railroad("Пенниславская Ж/Д", 15, 25),
            new Property("Парк приключений", 16, PropertyGroup.ORANGE, 14, 180),
            new Cards("Сокровище", 17, CardType.COMMUNITY_CHEST),
            new Property("Город аттракционов", 18, PropertyGroup.ORANGE, 14, 180),
            new Property("Район кино", 19, PropertyGroup.ORANGE, 16, 200),
            new FreeParking(20),
            new Property("Площадь стиля", 21, PropertyGroup.RED, 18, 220),
            new Cards("Шанс", 22, CardType.CHANCE),
            new Property("Карусель", 23, PropertyGroup.RED, 18, 220),
            new Property("Театральный проспект", 24, PropertyGroup.RED, 20, 240),
            new Railroad("Куриная Ж/Д", 25, 25),
            new Property("Солнечный залив", 26, PropertyGroup.YELLOW, 22, 260),
            new Property("Шикарный пляж", 27, PropertyGroup.YELLOW, 22, 260),
            new Utility("МосВодоКанал", 28),
            new Property("Яхтовая гавань", 29, PropertyGroup.YELLOW, 24, 280),
            new GoToJail(30, 10),
            new Property("Бунгало на дереве", 31, PropertyGroup.GREEN, 26, 300),
            new Property("Горнолыжный курорт", 32, PropertyGroup.GREEN, 26, 300),
            new Cards("Сокровище", 33, CardType.COMMUNITY_CHEST),
            new Property("Бриллиантовые горы", 34, PropertyGroup.GREEN, 28, 320),
            new Railroad("Короткая Ж/Д", 35, 25),
            new Cards("Шанс!", 36, CardType.CHANCE),
            new Property("Долина удачи", 37, PropertyGroup.BLUE, 35, 350),
            new Tax("Налог на роскошь", 38, 100),
            new Property("Райский остров", 39, PropertyGroup.BLUE, 50, 400)
        };
    }

    private Card[] createChanceCards() {
        return  new Card[]{
            new MoveToCard(CardType.CHANCE, "Переместитесь на клетку GO.\nСоберите 200$.", boardSquares[0]),
            new ReceiveMoneyCard(CardType.CHANCE, "Вы получили выплату по налогам\nПолучите 150$.", 150),
            new MoveToCard(CardType.CHANCE, "Прогуляйтесь по райскому отсрову.\nПереместитесь на "+boardSquares[39].getName(), boardSquares[39]),
            new MoveToCard(CardType.CHANCE, "Время отдохнуть на яхте друга.\nПереместитесь на "+boardSquares[24].getName()+".", boardSquares[24]),
            new NearestSquareCard(CardType.CHANCE, "Похоже, у вас протечка.\nПереместитесь на ближайшую клетку с ЖКУ", Utility.class.getTypeName()),
            new MoveToCard(CardType.CHANCE, "Принц устал ждать.\nПереместитесь в "+boardSquares[11].getName()+".\nЕсли прошёл клетку GO, собери $200.", boardSquares[11]),
            new NearestSquareCard(CardType.CHANCE, "РЖД объединяет.\nПереместитесь на ближайшую Ж/Д.", Railroad.class.getTypeName()),
            new PayMoneyCard(CardType.CHANCE, "Время благотворительности!. Заплати по $50 каждому", 50, true),
            new MoveToCard(CardType.CHANCE, "РЖД опять встала.\nПридётся ехать в "+boardSquares[5].getName()+" самому.", boardSquares[5]),
            new PayMoneyCard(CardType.CHANCE, "Заплати налог за вредность в размере $15.", 15),
            new ReceiveMoneyCard(CardType.CHANCE, "СБЕР ПО 300!\n Получите $50.", 50),
            new NearestSquareCard(CardType.CHANCE, "'А откуда нам было знать,что в России снег?'\nПридётся ехать самому", Railroad.class.getTypeName())
        };
    }

    private Card[] createCommunityChestCards() {
        return new Card[]{
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Жизни чёрных важны.\nПолучите со своей плантации $100.", 100),
            new PayMoneyCard(CardType.COMMUNITY_CHEST, "Сборы в школу забрали у тебя $150.", 150),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Вы даётё концерт в Большом театре.\nСоберите по $50 с каждого за место.", 50, true),
            new PayMoneyCard(CardType.COMMUNITY_CHEST, "'Зй,ты накоцен очнулся', заплати $50 за ухаживание в больнице.", 50),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Твои настолки заходят на ура. Получите свои грустные $100.", 100),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "На воскрестной распродаже ты получаешь $45", 45),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Ты нашёл $20 на улице", 20),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Бабушка даёт $100 на день рождение.", 100),
            new PayMoneyCard(CardType.COMMUNITY_CHEST, "Дальше вы не пройдёте пока не заплатите $100.", 100),
            new PayMoneyCard(CardType.COMMUNITY_CHEST, "ФСБ попросила отправить $100 на защищённый счёт.\nОказывается, это было не ФСБ", 100),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Ты отобрал $10 у первоклассника. Гордись собой", 10),
            new MoveToCard(CardType.COMMUNITY_CHEST, "Переместись на клетку GO.\nСобери 200$.", boardSquares[0]),
            new ReceiveMoneyCard(CardType.COMMUNITY_CHEST, "Продай цветы, собери $25.", 25)
        };
    }

    public Square[] getBoardSquares() {
        return boardSquares;
    }

    public ChanceDeck getChanceDeck() {
        return chanceDeck;
    }

    public CommunityChestDeck getCommunityChestDeck() {
        return communityChestDeck;
    }

    /**
     * Returns the nearest {@link Square} of a given type on the {@link Board}, starting at a given position.
     *
     * @param startPosition the position to start searching from
     * @param className the name of the class of the square type to search for
     * @return the nearest square of the given type, or null if no such {@link Square} is found
     */
    public Square getNearestSquareOfType(int startPosition, String className) {
        int max = boardSquares.length;
        for (int i = startPosition; i < (max + startPosition - 1); i++) {
            try {
                if (Class.forName(className).isInstance(boardSquares[i % max])) return boardSquares[i % max];
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
