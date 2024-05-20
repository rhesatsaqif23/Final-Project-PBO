package pokemon.monster;

public class WaterMonster extends Monster {
    private Element strongTo = Element.FIRE;
    private Element weakTo = Element.EARTH;

    public WaterMonster(String name, int level, double hp, double maxHp, int ep) {
        super(name, level, hp, maxHp, ep, Element.WATER);
    }

    @Override
    public boolean canEvolve(Element newElement) {
        return newElement == Element.WIND || newElement == Element.ICE;
    }

    @Override
    public double basicAttack(Monster enemy) {
        double damage = 90 + (getLevel() - 1) * 9;
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println(getName() + " attacks " + enemy.getName() + " with basic attack!");
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }

    @Override
    public double specialAttack(Monster enemy) {
        double damage = 210 + (getLevel() - 1) * 21;
        double missChance = 0.25;

        if (Math.random() >= missChance) {
            double enemyHealth = enemy.getHp() - damage;
            enemy.setHp(enemyHealth);
            System.out.println(getName() + " attacks " + enemy.getName() + " with special attack!");
            System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());

        } else {
            System.out.println(getName() + " missed special attack!");
        }

        double selfDamage = getMaxHp() * 0.075;
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
            damage = 240 + (getLevel() - 1) * 24;
        } else if (enemy.getElement() == weakTo) {
            System.out.println(getElement().toString().toLowerCase() + " is weak against " + enemy.getElement().toString().toLowerCase());
            damage = 90 + (getLevel() - 1) * 9;
        } else {
            System.out.println(getElement().toString().toLowerCase() + " has no advantage against " + enemy.getElement().toString().toLowerCase());
            damage = 120 + (getLevel() - 1) * 12;
        }
        
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }
}