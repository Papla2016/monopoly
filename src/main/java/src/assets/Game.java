package src.assets;

import java.util.Scanner;

public class Game {
    private Board board;
    private final int prisonPos = 9;
    private final int payout = 500;
    Game(Board board){
        this.board = board;
    }
    private void goToCell(Player player){
        player.setPos(prisonPos);
        player.chancheInPrison();
    }
    private void prisoner(Player player){
        if (player.getMoney()>500) {
            String payback;
            while (true) {
                System.out.println("Вы хотите заплатить за выход?(y/n)");

                Scanner scanner = new Scanner(System.in);
                payback = scanner.next();
                if (payback.equals("y") || payback.equals("n")) {
                    break;
                } else {
                    System.out.println("Ошибка ввода, попробуйте ещё раз");
                }
                if (payback.equals("y")){
                    player.addMoney(-payout);
                    player.freePrisoner();
                }
            }
        }
    }
    private void chooser(){
        System.out.println(
                """
                Выберите, что хотите сделать на этой собственности:
                1. - купить
                2. - выставить на аукцион
                3. - ничего не делать 
                """
        );
        Scanner scanner = new Scanner(System.in);
    }
    public void newRound(){
        for (Player player:board.getPlayers()){
            if (player.isInPrison()){
                prisoner(player);
            }
            int counter = 0;
            while(true) {
                int firstDice = player.rollADice();
                int secondDice = player.rollADice();
                player.chanchePosition(firstDice + secondDice);

                if (firstDice == secondDice){
                    if(counter >= 2){
                        goToCell(player);
                    } else {
                        counter++;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
