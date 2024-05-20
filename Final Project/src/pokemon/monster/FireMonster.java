package pokemon.monster;

public class FireMonster extends Monster {
    private Element strongTo = Element.ICE;
    private Element weakTo = Element.WATER;

    public FireMonster(String name, int level, double hp, double maxHp, int ep) {
        super(name, level, hp, maxHp, ep, Element.FIRE);
    }

    @Override
    public boolean canEvolve(Element newElement) {
        return newElement == Element.EARTH || newElement == Element.WIND;
    }

    @Override
    public double basicAttack(Monster enemy) {
        double damage = 100 + (getLevel() - 1) * 10;
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println(getName() + " attacks " + enemy.getName() + " with basic attack!");
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }

    @Override
    public double specialAttack(Monster enemy) {
        double damage = 200 + (getLevel() - 1) * 20;
        double missChance = 0.1;

        if (Math.random() >= missChance) {
            double enemyHealth = enemy.getHp() - damage;
            enemy.setHp(enemyHealth);
            System.out.println(getName() + " attacks " + enemy.getName() + " with special attack!");
            System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
            
        } else {
            System.out.println(getName() + " missed special attack!");
        }

        double selfDamage = getMaxHp() * 0.15;
        setHp(getHp() - selfDamage);
        System.out.println("Self damage: " + selfDamage);
        System.out.printf("%s health: %.1f\n", getName(), getHp());
        return damage;
    }

    @Override
    public double elementalAttack(Monster enemy) {
        double damage;
        System.out.println(getName() + " attacks " + enemy.getName() + " with elemental attack!");

        if (enemy.getElement() == strongTo) {
            System.out.println(getElement().toString().toLowerCase() + " is strong against " + enemy.getElement().toString().toLowerCase());
            damage = 250 + (getLevel() - 1) * 25;
        } else if (enemy.getElement() == weakTo) {
            System.out.println(getElement().toString().toLowerCase() + " is weak against " + enemy.getElement().toString().toLowerCase());
            damage = 50 + (getLevel() - 1) * 5;
        } else {
            System.out.println(getElement().toString().toLowerCase() + " has no advantage against " + enemy.getElement().toString().toLowerCase());
            damage = 150 + (getLevel() - 1) * 15;
        }

        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }
}