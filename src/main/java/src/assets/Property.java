package src.assets;

public class Property {
    private final int sellNum = 36;
    private final String name;
    private int level;
    private final int costLevelUp;
    private final int[] rent;
    private final int cost;
    private final Colors color;
    private boolean isFull = false;
    private final int deposit;
    private boolean isDeposit = false;
    private Player owner;
    private int position;

    /**
     * Конструктор собственности
     * @param name
     * @param level
     * @param costLevelUp
     * @param rent
     * @param cost
     * @param color
     * @param deposit
     * @param position Задаёт позицию собственности на поле. Если не входит в {@value sellNum} то значение вычисляется по формуле position % {@value sellNum}
     */
    Property (String name,int level, int costLevelUp,int[] rent, int cost, Colors color, int deposit,int position){
        this.color = color;
        this.cost = cost;
        this.name = name;
        this.level = level;
        this.rent = rent;
        this.deposit = deposit;
        this.costLevelUp = costLevelUp;
        this.owner = null;
        this.position = position % sellNum;
    }
    public int newPosition(int pos) throws Exception{
        if (pos < 0 || pos > sellNum){
            throw new Exception("Не входит в поле");
        }
        position = pos;
        return pos;
    }
    public int setLevel (int level){
        if (level > 6 || level < 0){
            return -1;
        }
        this.level = level;
        return 0;
    }
    public void setIsFull(boolean isFull){
        this.isFull = isFull;
    }
    private boolean isLevelWillInChancheInEnum(int newLevel){
        if(newLevel < 0 || newLevel > 4){
            return false;
        }
        return true;
    }
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int[] getRent() {
        return rent;
    }

    public int getCost() {
        return cost;
    }

    public Colors getColor() {
        return color;
    }

    public boolean isFull() {
        return isFull;
    }

    public int getDeposit() {
        return deposit;
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    public int getCostLevelUp() {
        return costLevelUp;
    }
    public void setOwner(Player owner){this.owner = owner;}
    public void setFull(){this.isFull = true;}


    public int setDeposit(){
        if (isDeposit){
            return -1;
        }
        isDeposit = true;
        return deposit;
    }
    public boolean LevelUp(int money){
        if(!isLevelWillInChancheInEnum(level + 1)){
            return false;
        }
        if (!owner.addMoney(-money)){
            return false;
        }
        this.level += 1;
        return true;


    }

}

