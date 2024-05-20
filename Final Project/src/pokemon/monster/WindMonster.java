package pokemon.monster;

public class WindMonster extends Monster {
    private Element strongTo = Element.EARTH;
    private Element weakTo = Element.ICE;

    public WindMonster(String name, int level, double hp, double maxHp, int ep) {
        super(name, level, hp, maxHp, ep, Element.WIND);
    }

    @Override
    public boolean canEvolve(Element newElement) {
        return newElement == Element.FIRE || newElement == Element.WATER;
    }

    @Override
    public double basicAttack(Monster enemy) {
        double damage = 130 + (getLevel() - 1) * 13;
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println(getName() + " attacks " + enemy.getName() + " with basic attack!");
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }

    @Override
    public double specialAttack(Monster enemy) {
        double damage = 170 + (getLevel() - 1) * 17;
        double missChance = 0.3;

        if (Math.random() >= missChance) {
            double enemyHealth = enemy.getHp() - damage;
            enemy.setHp(enemyHealth);
            System.out.println(getName() + " attacks " + enemy.getName() + " with special attack!");
            System.out.println("Damage received: " + damage);
            System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());

        } else {
            System.out.println(getName() + " missed special attack!");
        }

        double selfDamage = getMaxHp() * 0.05;
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
            damage = 220 + (getLevel() - 1) * 22;
        } else if (enemy.getElement() == weakTo) {
            System.out.println(getElement().toString().toLowerCase() + " is weak against " + enemy.getElement().toString().toLowerCase());
            damage = 70 + (getLevel() - 1) * 7;
        } else {
            System.out.println(getElement().toString().toLowerCase() + " has no advantage against " + enemy.getElement().toString().toLowerCase());
            damage = 160 + (getLevel() - 1) * 16;
        }

        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }
}