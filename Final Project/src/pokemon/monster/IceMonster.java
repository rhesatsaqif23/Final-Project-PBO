package pokemon.monster;

public class IceMonster extends Monster {
    private Element strongTo = Element.WIND;
    private Element weakTo = Element.FIRE;

    public IceMonster(String name, int level, double hp, double maxHp, int ep) {
        super(name, level, hp, maxHp, ep, Element.ICE);
    }

    @Override
    public boolean canEvolve(Element newElement) {
        return newElement == Element.WATER || newElement == Element.EARTH;
    }

    @Override
    public double basicAttack(Monster enemy) {
        double damage = 120 + (getLevel() - 1) * 12;
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println(getName() + " attacks " + enemy.getName() + " with basic attack!");
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }

    @Override
    public double specialAttack(Monster enemy) {
        double damage = 180 + (getLevel() - 1) * 18;
        double missChance = 0.2;

        if (Math.random() >= missChance) {
            double enemyHealth = enemy.getHp() - damage;
            enemy.setHp(enemyHealth);
            System.out.println(getName() + " attacks " + enemy.getName() + " with special attack!");
            System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());

        } else {
            System.out.println(getName() + " missed special attack!");
        }

        double selfDamage = getMaxHp() * 0.1;
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
            damage = 210 + (getLevel() - 1) * 21;
        } else if (enemy.getElement() == weakTo) {
            System.out.println(getElement().toString().toLowerCase() + " is weak against " + enemy.getElement().toString().toLowerCase());
            damage = 60 + (getLevel() - 1) * 6;
        } else {
            System.out.println(getElement().toString().toLowerCase() + " has no advantage against " + enemy.getElement().toString().toLowerCase());
            damage = 180 + (getLevel() - 1) * 18;
        }
        
        double enemyHealth = enemy.getHp() - damage;
        enemy.setHp(enemyHealth);
        System.out.println("Damage received: " + damage);
        System.out.printf("%s health: %.1f\n", enemy.getName(), enemy.getHp());
        return damage;
    }
}