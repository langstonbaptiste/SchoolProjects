public class Pokemon 
{
    private String name;
    private int maxHP;
    private int currentHP;
    private int attack;
    private double attackBoost;
    private int speed;
    private Type type;
    private final int SLEEP_TIME = 1000; // milliseconds

    // constructor
    public Pokemon(String name, String typeStr) 
    {
        this.name = name;
        type = new Type(typeStr);

        maxHP = randomIntRange(40, 60);
        currentHP = maxHP;
        attack = randomIntRange(20, 30);
        speed = randomIntRange(1, 100);
        attackBoost = 1;
    }

    // generate random number in range
    public static int randomIntRange(int min, int max) 
    {
        return min + (int) ((max - min + 1) * Math.random());
    }

    // attacking another Pokemon
    public void attack(Pokemon opponent) 
    {
        System.out.println("~~~~~~~~~~~~");
        System.out.println(name + " attacks " + opponent.name + "!");
        sleep(SLEEP_TIME);

        double effectiveness = type.getEffectiveness(opponent.type);
        double damage = attack * effectiveness * attackBoost * (1 - 0.15 * Math.random());

        // critical hit (1/16 chance)
        if (Math.random() < 1 / 16.0) 
        {
            damage *= 2;
            System.out.println("It's a critical hit!");
            sleep(SLEEP_TIME);
        }

        // effectiveness messages
        if (effectiveness == 0) 
        {
            System.out.println(opponent.name + " is immune to " + type + " type attacks.");
        } 
        else if (effectiveness == 2) 
        {
            System.out.println("It's super effective!");
        } 
        else if (effectiveness == 0.5) 
        {
            System.out.println("It's not very effective.");
        }

        int damageInt = (int) Math.round(damage);
        opponent.currentHP = Math.max(0, opponent.currentHP - damageInt);

        System.out.println(name + " dealt " + damageInt + " damage to " + opponent.name);
        if (opponent.currentHP == 0) 
        {
            System.out.println(opponent.name + " fainted!");
        } 
        else 
        {
            System.out.println(opponent.name + "'s HP is now: " + opponent.currentHP + "/" + opponent.maxHP);
        }

        System.out.println("~~~~~~~~~~~~");
        sleep(SLEEP_TIME);
    }

    // getters
    public String getTypeStr() 
    { 
        return type.toString(); 
    }
    public String getName() 
    { 
        return name; 
    }
    public int getCurrentHP() 
    { 
        return currentHP; 
    }
    public int getMaxHP() 
    { 
        return maxHP; 
    }
    public int getSpeed() 
    { 
        return speed; 
    }
    public double getAttackBoost() 
    { 
        return attackBoost; 
    }

    // setter for HP (testing only)
    public void setHP(int hp) 
    { 
        currentHP = hp; 
    }

    // boosting attacks
    public void boostAttack() 
    {
        if (attackBoost < 2) 
        {
            attackBoost += 0.5;
            System.out.println("Boosted " + name + "'s attack boost to " + attackBoost);
        } 
        else 
        {
            System.out.println(name + "'s attack boost was already maxed");
        }
    }

    public void resetAttackBoost() 
    {
        attackBoost = 1;
    }

    // print stats
    public String toString() 
    {
        return name + " - " + type + " type\n-----------------\nHP remaining: " 
                + currentHP + "/" + maxHP + "\nAttack: " 
                + attack + "\nSpeed: " + speed 
                + (attackBoost > 1 ? " **Attack Boost = " + attackBoost : "");
    }

    // simple sleep (without try/catch)
    public void sleep(int t) 
    {
    }
}
