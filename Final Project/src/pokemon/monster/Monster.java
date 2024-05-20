package pokemon.monster;

import pokemon.item.Item;

public abstract class Monster implements MonsterAction {
    protected String name;
    protected int level;
    protected double hp;
    protected double maxHp;
    protected int ep;
    protected Element element;
    protected boolean evolved;

    public Monster(String name, int level, double hp, double maxHp, int ep, Element element) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.maxHp = maxHp;
        this.ep = ep;
        this.element = element;
        this.evolved = false;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public int getEp() {
        return ep;
    }

    public Element getElement() {
        return element;
    }

    public boolean isEvolved() {
        return evolved;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHp(double hp) {
        this.hp = hp;
        if (!isAlive()) {
            this.hp = 0;
        }
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setEvolved(boolean evolved) {
        this.evolved = evolved;
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    public abstract boolean canEvolve(Element newElement);

    @Override
    public abstract double basicAttack(Monster enemy);

    @Override
    public abstract double specialAttack(Monster enemy);

    @Override
    public abstract double elementalAttack(Monster enemy);

    @Override
    public void useItem(Item item, Monster target) {
        item.use(this, target);
    }

    @Override
    public boolean escape() {
        double chance = Math.random();
        if (chance > 0.75) {
            System.out.println(getName() + " escapes succesfully!");
            return true;
        } else {
            System.out.println(getName() + " failed to escape!");
            return false;
        }
    }

    public void printMonster() {
        System.out.println("\nName\t\t: " + getName());
        System.out.println("Level\t\t: " + getLevel());
        System.out.println("Health\t\t: " + (int) getHp() + "/" + (int) getMaxHp());
        System.out.println("Experience\t: " + getEp() + " EP");
        System.out.println("Element\t\t: " + getElement().toString().toLowerCase());
    }
}