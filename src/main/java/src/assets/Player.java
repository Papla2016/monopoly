package src.assets;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class Player {
    private int money;
    private boolean inPrison = false;
    private Vector<Property> properties;
    private int pos;
    private int counterInPrison = 0;
    public void addCounterInPrison(){
        counterInPrison++;
    }
    public void freePrisoner(){
        inPrison = !inPrison;
        counterInPrison = 0;
    }
    public int getMoney(){
        return money;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void chancheInPrison() {
        inPrison = !inPrison;
    }


    public void setPos(int pos) {
        this.pos = pos;
    }


    public int getPos() {
        return pos;
    }

    public int rollADice(){
        Random random = new Random();
        return random.nextInt(6)+1;
    }

    private void exept(String message) throws Exception{
        throw new Exception(message);
    }

    Player(int money) {
        this.money = money;
        properties = new Vector<>();
        pos = 0;
    }


    public void chanchePosition(int chanche){
        pos = (pos + chanche) % 36;
    }
    /**
     * Добавление / удаление денег у игрока
     * в случае нехватки средств для удаления возвращает ошибку
     * @param money j
     */
    public boolean addMoney(int money){
        if (money < 0 && this.money - money < 0){
            return false;
        }
        this.money += money;
        return true;
    }

    public Vector<Property> getProperties() {
        return properties;
    }

    public void setProperties(Vector<Property> properties) {
        this.properties = properties;
    }
    public void addPropertie(Property p){
        properties.add(p);
    }
    public Property deletePropertie(int propID){
       return properties.remove(propID);
    }
    public boolean deletePropertie(Property property){
        return properties.remove(property);
    }

}
