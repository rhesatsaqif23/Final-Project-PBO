package pokemon.item;

import pokemon.monster.Monster;

public class Item {
    private String name;
    private ItemType type;
    private int duration;
    private int quantity;

    public Item(String name, ItemType type, int duration, int quantity) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return getDuration() > 0;
    }

    public boolean isAvailable() {
        return getQuantity() > 0;
    }

    public void use(Monster actor, Monster target) {
        switch (type) {

            case HEALTH_POTION:
                double heal = actor.getMaxHp() * 0.3;
                if ((actor.getHp() + heal) > actor.getMaxHp()) {
                    heal = actor.getMaxHp() - actor.getHp();
                }
                actor.setHp(actor.getHp() + heal);
                System.out.println("\n" + actor.getName() + " used " + getName() + " and recovered " + heal + " HP");
                System.out.printf("%s health: %.1f\n", actor.getName(), actor.getHp());
                break;

            case ELEMENTAL_POTION:
                actor.elementalAttack(target);
                break;

            case POISON_SPELL:
                double damage = actor.getMaxHp() * 0.12;
                target.setHp(target.getHp() - damage);
                System.out.println("\n" + target.getName() + " received " + damage + " damage from " + getName());
                System.out.println(target.getName() + " health: " + target.getHp());
                break;

            case MAGIC_ARMOR:
                double protect = (100 + (target.getLevel() - 1) * 10) * 0.7;
                actor.setHp(actor.getHp() + protect);
                System.out
                        .println("\n" + actor.getName() + " used " + getName() + " and reduced " + protect + " damage");
                System.out.printf("%s health: %.1f\n", actor.getName(), actor.getHp());
                break;

            default:
                break;
        }
        setDuration(getDuration() - 1);
    }

    public void printItem() {
        System.out.println("\nItem Type\t: " + getName());
        System.out.println("Duration\t: " + getDuration());
        System.out.println("Quantity\t: " + getQuantity());
    }
}