package pokemon.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pokemon.monster.*;
import pokemon.item.*;

public class Player {
    private String name;
    private HomeBase homeBase;
    private Dungeon dungeon;
    private List<Monster> ownedMonsters;
    private List<Item> ownedItems;

    public Player(String name) {
        this.name = name;
        this.homeBase = new HomeBase();
        this.ownedMonsters = new ArrayList<>();
        this.ownedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public HomeBase getHomeBase() {
        return homeBase;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public List<Monster> getOwnedMonsters() {
        return ownedMonsters;
    }

    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    public void enterDungeon() {
        this.dungeon = new Dungeon(this);
    }

    public void leaveDungeon() {
        this.dungeon = null;
    }

    public void printOwnedMonster() {
        for (Monster monster : ownedMonsters) {
            monster.printMonster();
        }
    }

    public void printOwnedItem() {
        for (Item item : ownedItems) {
            if (item.getQuantity() > 0) {
                item.printItem();
            }
        }
    }

    public void initiateItem() {
        ownedItems.add(new Item("Health Potion", ItemType.HEALTH_POTION, 1, 1));
        ownedItems.add(new Item("Elemental Potion", ItemType.ELEMENTAL_POTION, 1, 1));
        ownedItems.add(new Item("Poison Spell", ItemType.POISON_SPELL, 3, 1));
        ownedItems.add(new Item("Magic Armor", ItemType.MAGIC_ARMOR, 3, 1));
    }

    public void initiateMonster() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nAfter that, please select 3 monsters to start your journey!");

        MonsterLibrary library = new MonsterLibrary();
        library.printMonsterLibrary();
        System.out.println();

        for (int i = 0; i < 3; i++) {
            System.out.print("Select monster " + (i + 1) + ": ");
            int choice = input.nextInt();

            while (choice < 1 || choice > 10) {
                System.out.println("Invalid choice!");
                System.out.print("Select monster " + (i + 1) + ": ");
                choice = input.nextInt();
            }

            while (ownedMonsters.contains(library.getMonsterLibrary().get(choice - 1))) {
                System.out.println("Monster already selected! Please select a different monster.");
                System.out.print("Select monster " + (i + 1) + ": ");
                choice = input.nextInt();

                while (choice < 1 || choice > 10) {
                    System.out.println("Invalid choice!");
                    System.out.print("Select monster " + (i + 1) + ": ");
                    choice = input.nextInt();
                }
            }
            ownedMonsters.add(library.getMonsterLibrary().get(choice - 1));
        }
    }

    public void addPrevMonster(String[] monsterData) {
        String name = monsterData[0];
        int level = Integer.parseInt(monsterData[1]);
        double hp = Double.parseDouble(monsterData[2]);
        double maxHp = Double.parseDouble(monsterData[3]);
        int ep = Integer.parseInt(monsterData[4]);
        String strElement = monsterData[5];
        String strEvolved = monsterData[6];

        Monster monster = null;
        if (strElement.equalsIgnoreCase("fire")) {
            monster = new FireMonster(name, level, hp, maxHp, ep);
        } else if (strElement.equalsIgnoreCase("ice")) {
            monster = new IceMonster(name, level, hp, maxHp, ep);
        } else if (strElement.equalsIgnoreCase("wind")) {
            monster = new WindMonster(name, level, hp, maxHp, ep);
        } else if (strElement.equalsIgnoreCase("earth")) {
            monster = new EarthMonster(name, level, hp, maxHp, ep);
        } else if (strElement.equalsIgnoreCase("water")) {
            monster = new WaterMonster(name, level, hp, maxHp, ep);
        }

        if (monster != null) {
            if (strEvolved.equalsIgnoreCase("true")) {
                monster.setEvolved(true);
            } else {
                monster.setEvolved(false);
            }
            ownedMonsters.add(monster);
        }
    }

    public void addPrevItem(String[] itemData) {
        String strType = itemData[0];
        int duration = Integer.parseInt(itemData[1]);
        int quantity = Integer.parseInt(itemData[2]);

        Item item = null;
        if (strType.equalsIgnoreCase("health_potion")) {
            item = new Item("Health Potion", ItemType.HEALTH_POTION, duration, quantity);
        } else if (strType.equalsIgnoreCase("elemental_potion")) {
            item = new Item("Elemental Potion", ItemType.ELEMENTAL_POTION, duration, quantity);
        } else if (strType.equalsIgnoreCase("poison_spell")) {
            item = new Item("Poison Spell", ItemType.POISON_SPELL, duration, quantity);
        } else if (strType.equalsIgnoreCase("magic_armor")) {
            item = new Item("Magic Armor", ItemType.MAGIC_ARMOR, duration, quantity);
        }

        if (item != null) {
            ownedItems.add(item);
        }
    }
}