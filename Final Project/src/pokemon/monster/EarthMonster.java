package pokemon.monster;

public class EarthMonster extends Monster {
    private Element strongTo = Element.WATER;
    private Element weakTo = Element.WIND;

    public EarthMonster(String name, int level, double hp, double maxHp, int ep) {
        super(name, level, hp, maxHp, ep, Element.EARTH);
    }

    @Override
    public boolean canEvolve(Element newElement) {
        return newElement == Element.ICE || newElement == Element.FIRE;
    }

    @Override
    public double basicAttack(Monster enemy) {
        double damage = 110 + (getLevel() - 1) * 11;
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println(getName() + " attacks " + enemy.getName() + " with basic attack!");
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }

    @Override
    public double specialAttack(Monster enemy) {
        double damage = 190 + (getLevel() - 1) * 19;
        double missChance = 0.15;
        
        if (Math.random() >= missChance) {
            double enemyHealth = enemy.getHp() - damage;
            enemy.setHp(enemyHealth);
            System.out.println(getName() + " attacks " + enemy.getName() + " with special attack!");
            System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());

        } else {
            System.out.println(getName() + " missed special attack!");
        }

        double selfDamage = getMaxHp() * 0.125;
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
            damage = 230 + (getLevel() - 1) * 23;
        } else if (enemy.getElement() == weakTo) {
            System.out.println(getElement().toString().toLowerCase() + " is weak against " + enemy.getElement().toString().toLowerCase());
            damage = 80 + (getLevel() - 1) * 8;
        } else {
            System.out.println(getElement().toString().toLowerCase() + " has no advantage against " + enemy.getElement().toString().toLowerCase());
            damage = 140 + (getLevel() - 1) * 14;
        }
        
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }
}