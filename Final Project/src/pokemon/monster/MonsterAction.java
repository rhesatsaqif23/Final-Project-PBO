package pokemon.monster;

import pokemon.item.Item;

public interface MonsterAction {
    public double basicAttack(Monster enemy);
    public double specialAttack(Monster enemy);
    public double elementalAttack(Monster enemy);
    public void useItem(Item item, Monster target);
    public boolean escape();
}