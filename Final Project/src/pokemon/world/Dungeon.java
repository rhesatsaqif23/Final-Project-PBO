package pokemon.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pokemon.item.*;
import pokemon.monster.*;

public class Dungeon {
    private List<Monster> playerMonsters;
    private List<Monster> wildMonsters;
    private List<Monster> readyMonsters;
    private List<Monster> faintedMonsters;
    private List<Item> playerItems;
    private List<Item> activePlayerItems;
    private List<Item> activeEnemyItems;

    public Dungeon(Player player) {
        playerMonsters = new ArrayList<>();
        wildMonsters = new ArrayList<>();
        readyMonsters = new ArrayList<>(player.getOwnedMonsters());
        faintedMonsters = new ArrayList<>();
        playerItems = new ArrayList<>(player.getOwnedItems());
        activePlayerItems = new ArrayList<>();
        activeEnemyItems = new ArrayList<>();
        generateWildMonster();
        removeSameMonster(player);
    }

    public void generateWildMonster() {
        int[] randomLevel = new int[20];
        double[] randomHp = new double[20];

        int highestLevel = 0;
        for (Monster monster : playerMonsters) {
            if (monster.getLevel() > highestLevel) {
                highestLevel = monster.getLevel();
            }
        }

        for (int i = 0; i < 20; i++) {
            int randomOffset = (int) (Math.random() * 6) - 2;
            randomLevel[i] = Math.max(1, highestLevel + randomOffset);

            double hp = 500;
            for (int j = 1; j < randomLevel[i]; j++) {
                hp += Math.ceil(hp * 0.08);
            }
            randomHp[i] = hp;
        }

        MonsterLibrary library = new MonsterLibrary();
        for (int i = 0; i < 20; i++) {
            Monster monster = library.getMonsterLibrary().get(i);
            monster.setLevel(randomLevel[i]);
            monster.setHp(randomHp[i]);
            wildMonsters.add(monster);
        }
    }

    public void removeSameMonster(Player player) {
        List<Monster> removedMonster = new ArrayList<>();

        for (Monster playerMonster : player.getOwnedMonsters()) {
            for (Monster wildMonster : wildMonsters) {
                if (playerMonster.getName().equals(wildMonster.getName())) {
                    removedMonster.add(wildMonster);
                }
            }
        }
        wildMonsters.removeAll(removedMonster);
    }

    private void chooseDungeonMonsters(Player player) {
        Scanner input = new Scanner(System.in);

        if (!readyMonsters.isEmpty()) {
            System.out.println("\nChoose Pokemon you want to take to the dungeon!");
            for (int i = 0; i < readyMonsters.size(); i++) {
                System.out.println((i + 1) + ". " + readyMonsters.get(i).getName());
            }

            System.out.print("Enter the number of Pokemon: ");
            int n = input.nextInt();
            input.nextLine();

            while (n < 1 || n > 3 || n > readyMonsters.size()) {
                if (n < 1) {
                    System.out.println("Invalid input number!");
                } else if (n > 3) {
                    System.out.println("You can only bring up to 3 Pokemon to the dungeon!");
                } else {
                    System.out.println("You only have " + readyMonsters.size() + " Pokemon ready to battle!");
                }
                System.out.print("Enter the number of Pokemon: ");
                n = input.nextInt();
                input.nextLine();
            }

            for (int i = 0; i < n; i++) {
                System.out.print("Choose Pokemon " + (i + 1) + ": ");
                int choice = input.nextInt();
                input.nextLine();

                while (choice < 1 || choice > readyMonsters.size()) {
                    System.out.println("Invalid Pokemon choice!");
                    System.out.print("Choose Pokemon  " + (i + 1) + ": ");
                    choice = input.nextInt();
                    input.nextLine();
                }

                playerMonsters.add(readyMonsters.get(choice - 1));
            }
        }
    }

    private Monster chooseBattleMonster(List<Monster> playerMonsters) {
        Scanner input = new Scanner(System.in);
        System.out.println();

        if (!faintedMonsters.isEmpty()) {
            System.out.print("Fainted Pokemon: ");
            for (int i = 0; i < faintedMonsters.size(); i++) {
                System.out.print(faintedMonsters.get(i).getName());
                if (i < faintedMonsters.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < playerMonsters.size(); i++) {
            String name = playerMonsters.get(i).getName();
            String element = playerMonsters.get(i).getElement().toString().toLowerCase();
            System.out.printf("%d. %-10s (%s)\n", (i + 1), name, element);
        }

        System.out.print("Choose your Pokemon to battle: ");
        int choice = input.nextInt();
        input.nextLine();

        while (choice < 1 || choice > playerMonsters.size()) {
            System.out.println("Invalid Pokemon choice!");
            System.out.print("Choose your Pokemon to battle: ");
            choice = input.nextInt();
            input.nextLine();
        }

        return playerMonsters.get(choice - 1);
    }

    public void battleArena(Player player) {
        Scanner input = new Scanner(System.in);
        for (Monster monster : readyMonsters) {
            if (!monster.isAlive()) {
                faintedMonsters.add(monster);
            }
        }

        List<Monster> inactiveMonsters = new ArrayList<>();
        for (Monster monster : readyMonsters) {
            if (!monster.isAlive()) {
                inactiveMonsters.add(monster);
            }
        }

        readyMonsters.removeAll(inactiveMonsters);

        if (!readyMonsters.isEmpty()) {
            if (!faintedMonsters.isEmpty()) {
                System.out.print("\nFainted Pokemon: ");
                for (int i = 0; i < faintedMonsters.size(); i++) {
                    System.out.print(faintedMonsters.get(i).getName());
                    if (i < faintedMonsters.size() - 1) {
                        System.out.print(", ");
                    }
                }
            }

            chooseDungeonMonsters(player);
            System.out.println("\nEntering the dungeon...");
            System.out.print("Press any key to continue...");
            String key = input.nextLine();

            while (!playerMonsters.isEmpty()) {
                int randomIndex = (int) (Math.random() * wildMonsters.size());
                Monster wildMonster = wildMonsters.get(randomIndex);

                System.out.println("\nYou encountered a wild " + wildMonster.getName() + "!");
                System.out.println(wildMonster.getName() + " level: " + wildMonster.getLevel());
                System.out.println(wildMonster.getName() + " HP: " + wildMonster.getHp());
                System.out.println(
                        wildMonster.getName() + " element: " + wildMonster.getElement().toString().toLowerCase());

                System.out.println("\n1. Go to battle");
                System.out.println("2. Search other monster");
                System.out.println("3. Return to homebase");
                System.out.print("Choose an option: ");
                int option = input.nextInt();
                input.nextLine();

                while (option < 1 || option > 3) {
                    System.out.println("Invalid option!");
                    System.out.print("Choose an option: ");
                    option = input.nextInt();
                    input.nextLine();
                }

                if (option == 1) {
                    Monster playerMonster = chooseBattleMonster(playerMonsters);
                    while (playerMonster == null) {
                        System.out.println("Invalid Pokemon choice!");
                        playerMonster = chooseBattleMonster(playerMonsters);
                    }

                    battleSimulation(player, playerMonster, wildMonster);

                    if (playerMonsters.isEmpty()) {
                        System.out.println("\nAll your Pokemons have fainted. Retreating from the dungeon...");
                        player.leaveDungeon();
                        break;
                    }
                } else if (option == 3) {
                    System.out.print("\nReturning to home base...");
                    break;
                }
            }
        } else {
            System.out.println("\nAll of your Pokemons have fainted, please heal them first!");
            return;
        }
    }

    public void battleSimulation(Player player, Monster playerMonster, Monster wildMonster) {
        Scanner input = new Scanner(System.in);
        System.out.println(
                "\nThe battle between " + playerMonster.getName() + " VS " + wildMonster.getName() + " begins!");
        System.out.print("\nPress any key to continue... ");
        String key = input.nextLine();
        boolean playerTurn = true;

        while (playerMonster.isAlive() && wildMonster.isAlive()) {
            if (!playerMonster.isAlive()) {
                readyMonsters.remove(playerMonster);
                faintedMonsters.add(playerMonster);
            }

            if (playerTurn) {
                applyEnemyPoison(wildMonster, playerMonster);
                if (!playerMonster.isAlive()) {
                    System.out.print("\nPress any key to continue... ");
                    key = input.nextLine();
                    break;
                }

                System.out.println("\nPlayer's turn: ");
                System.out.println("1. Basic Attack");
                System.out.println("2. Special Attack");
                System.out.println("3. Elemental Attack");
                System.out.println("4. Use Item");
                System.out.println("5. Escape");
                System.out.print("Choose an action: ");
                int choice = input.nextInt();
                input.nextLine();
                System.out.println();

                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid action choice!");
                    System.out.print("Choose an action: ");
                    choice = input.nextInt();
                    input.nextLine();
                }

                if (choice == 4) {
                    boolean itemAvailable = false;
                    for (int i = 0; i < playerItems.size(); i++) {
                        if (playerItems.get(i).getQuantity() > 0) {
                            itemAvailable = true;
                            break;
                        }
                    }

                    if (!itemAvailable) {
                        while (choice == 4) {
                            System.out.println("No items available!");
                            System.out.print("Choose different action: ");
                            choice = input.nextInt();
                            input.nextLine();

                            while (choice < 1 || choice > 5) {
                                System.out.println("Invalid action choice!");
                                System.out.print("Choose an action: ");
                                choice = input.nextInt();
                                input.nextLine();
                            }
                        }
                    }
                }

                if (choice == 5) {
                    if (playerMonster.escape()) {
                        System.out.println("\nBattle ended as " + playerMonster.getName() + " escaped");
                        System.out.println(playerMonster.getName() + " gained 60 EP");
                        playerMonster.setEp(playerMonster.getEp() + 60);
                        System.out.println(playerMonster.getName() + " EP: " + playerMonster.getEp());
                        break;
                    }
                } else {
                    playerAction(playerMonster, wildMonster, choice);
                }

                playerTurn = false;

            } else {
                applyPlayerPoison(playerMonster, wildMonster);

                if (!wildMonster.isAlive()) {
                    System.out.print("\nPress any key to continue... ");
                    key = input.nextLine();
                    break;
                }

                System.out.println("\nEnemy's turn:");
                int choice = (int) (Math.random() * 5) + 1;

                if (choice == 5) {
                    if (wildMonster.escape()) {
                        System.out.println("\nBattle ended as " + wildMonster.getName() + " escaped");
                        System.out.println(playerMonster.getName() + " gained 100 EP");
                        playerMonster.setEp(playerMonster.getEp() + 100);
                        System.out.println(playerMonster.getName() + " EP: " + playerMonster.getEp());
                        break;
                    }
                } else {
                    enemyAction(wildMonster, playerMonster, choice);
                }
                playerTurn = true;

            }

            System.out.print("\nPress any key to continue... ");
            key = input.nextLine();
        }

        if (!wildMonster.isAlive()) {
            System.out.println("\nYou won the battle against " + wildMonster.getName() + "!");
            System.out.println(playerMonster.getName() + " gained 150 EP");
            playerMonster.setEp(playerMonster.getEp() + 150);
            System.out.println(playerMonster.getName() + " EP: " + playerMonster.getEp());

            System.out.print("\nPress any key to continue... ");
            key = input.nextLine();
            getReward(player, wildMonster);

        } else if (!playerMonster.isAlive()) {
            playerMonsters.remove(playerMonster);
            readyMonsters.remove(playerMonster);
            faintedMonsters.add(playerMonster);

            System.out.println("\nYou lost the battle against " + wildMonster.getName() + "!");
            System.out.println(playerMonster.getName() + " fainted!");

            System.out.print("\nPress any key to continue... ");
            key = input.nextLine();

            System.out.println("\n" + playerMonster.getName() + " gained 100 EP");
            playerMonster.setEp(playerMonster.getEp() + 100);
            System.out.println(playerMonster.getName() + " EP: " + playerMonster.getEp());
        }

        resetItemDuration(activePlayerItems);
        resetItemDuration(activePlayerItems);
        activePlayerItems.clear();
        activeEnemyItems.clear();
        wildMonsters.remove(wildMonster);

        System.out.print("\nPress any key to continue... ");
        key = input.nextLine();
    }

    private void getReward(Player player, Monster newMonster) {
        Scanner input = new Scanner(System.in);

        double chance = Math.random();
        if (chance > 0.5) {
            System.out.println("\nYou've successfully catch " + newMonster.getName() + "!");
            player.getOwnedMonsters().add(newMonster);
            System.out.println(newMonster.getName() + " has been added to your Pokemon list");

        } else {
            System.out.println("\nYou failed to catch " + newMonster.getName() + "!");
            System.out.println("No new Pokemon added to the Pokemon list");
        }

        System.out.print("\nPress any key to continue... ");
        String key = input.nextLine();

        int number = (int) (Math.random() * 4);
        playerItems.get(number).setQuantity(playerItems.get(number).getQuantity() + 1);

        System.out.println("\nYou earned " + playerItems.get(number).getName() + " from battle!");
        System.out.println(playerItems.get(number).getName() + " quantity: " + playerItems.get(number).getQuantity());
    }

    private void playerAction(Monster actor, Monster target, int action) {
        switch (action) {
            case 1:
                actor.basicAttack(target);
                if (target.getHp() > 0) {
                    applyEnemyArmor(target, actor);
                }
                break;
            case 2:
                actor.specialAttack(target);
                if (target.getHp() > 0) {
                    applyEnemyArmor(target, actor);
                }
                break;
            case 3:
                actor.elementalAttack(target);
                if (target.getHp() > 0) {
                    applyEnemyArmor(target, actor);
                }
                break;
            case 4:
                playerItem(actor, target);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void enemyAction(Monster actor, Monster target, int action) {
        switch (action) {
            case 1:
                actor.basicAttack(target);
                if (target.getHp() > 0) {
                    applyPlayerArmor(target, actor);
                }
                break;
            case 2:
                actor.specialAttack(target);
                if (target.getHp() > 0) {
                    applyPlayerArmor(target, actor);
                }
                break;
            case 3:
                actor.elementalAttack(target);
                if (target.getHp() > 0) {
                    applyPlayerArmor(target, actor);
                }
                break;
            case 4:
                enemyItem(actor, target);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void playerItem(Monster actor, Monster target) {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Health Potion (" + playerItems.get(0).getQuantity() + ")");
        System.out.println("2. Elemental Potion (" + playerItems.get(1).getQuantity() + ")");
        System.out.println("3. Poison Spell (" + playerItems.get(2).getQuantity() + ")");
        System.out.println("4. Iron Armor (" + playerItems.get(3).getQuantity() + ")");
        System.out.print("Choose an item to use: ");
        int choice = input.nextInt();
        input.nextLine();

        while (choice < 1 || choice > 4) {
            System.out.println("Invalid item choice!");
            System.out.print("Choose an item to use: ");
            choice = input.nextInt();
            input.nextLine();
        }

        while (playerItems.get(choice - 1).getQuantity() <= 0) {
            System.out.println("Item is out of stock!");
            System.out.print("Choose a different item: ");
            choice = input.nextInt();
            input.nextLine();

            while (choice < 1 || choice > 4) {
                System.out.println("Invalid item choice!");
                System.out.print("Choose an item to use: ");
                choice = input.nextInt();
                input.nextLine();
            }
        }

        Item newItem = null;
        switch (choice) {
            case 1:
                newItem = playerItems.get(0);
                playerItems.get(0).setQuantity(playerItems.get(0).getQuantity() - 1);
                actor.useItem(newItem, null);
                break;

            case 2:
                System.out.println();
                newItem = playerItems.get(1);
                playerItems.get(1).setQuantity(playerItems.get(1).getQuantity() - 1);
                actor.useItem(newItem, target);
                break;

            case 3:
                boolean poisonActive = false;
                for (Item item : activePlayerItems) {
                    if (item.getName().equalsIgnoreCase("Poison Spell")) {
                        item.setDuration(item.getDuration() + 3);
                        poisonActive = true;
                        break;
                    }
                }
                if (!poisonActive) {
                    newItem = playerItems.get(2);
                    playerItems.get(2).setQuantity(playerItems.get(2).getQuantity() - 1);
                    activePlayerItems.add(newItem);
                }
                System.out.println("\n" + actor.getName() + " casted poison spell to " + target.getName() + "!");
                break;

            case 4:
                boolean armorActive = false;
                for (Item item : activePlayerItems) {
                    if (item.getName().equalsIgnoreCase("Magic Armor")) {
                        item.setDuration(item.getDuration() + 3);
                        armorActive = true;
                    }
                }
                if (!armorActive) {
                    newItem = playerItems.get(3);
                    playerItems.get(3).setQuantity(playerItems.get(3).getQuantity() - 1);
                    activePlayerItems.add(newItem);
                }
                System.out.println("\n" + actor.getName() + " equipped magic armor!");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void enemyItem(Monster actor, Monster target) {
        int choice = (int) (Math.random() * 4) + 1;

        Item newItem = null;
        switch (choice) {
            case 1:
                newItem = new Item("Health Potion", ItemType.HEALTH_POTION, 1, 1);
                actor.useItem(newItem, null);
                break;

            case 2:
                newItem = new Item("Elemental Potion", ItemType.ELEMENTAL_POTION, 1, 1);
                actor.useItem(newItem, target);
                break;

            case 3:
                boolean poisonActive = false;
                for (Item item : activeEnemyItems) {
                    if (item.getName().equalsIgnoreCase("Poison Spell")) {
                        item.setDuration(item.getDuration() + 3);
                        poisonActive = true;
                        break;
                    }
                }
                if (!poisonActive) {
                    newItem = new Item("Poison Spell", ItemType.POISON_SPELL, 3, 1);
                    activeEnemyItems.add(newItem);
                }
                System.out.println(actor.getName() + " casted poison spell to " + target.getName() + "!");
                break;

            case 4:
                boolean armorActive = false;
                for (Item item : activeEnemyItems) {
                    if (item.getName().equalsIgnoreCase("Magic Armor")) {
                        item.setDuration(item.getDuration() + 3);
                        armorActive = true;
                    }
                }
                if (!armorActive) {
                    newItem = new Item("Magic Armor", ItemType.MAGIC_ARMOR, 3, 1);
                    activeEnemyItems.add(newItem);
                }
                System.out.println(actor.getName() + " equipped magic armor!");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void applyPlayerPoison(Monster player, Monster enemy) {
        if (activePlayerItems != null) {
            List<Item> itemsRemoved = new ArrayList<>();
            for (Item item : activePlayerItems) {
                if (item.getName().equals("Poison Spell")) {
                    item.use(player, enemy);
                    if (item.getDuration() == 0) {
                        itemsRemoved.add(item);
                    }
                }
            }
            activePlayerItems.removeAll(itemsRemoved);
        }
    }

    private void applyEnemyPoison(Monster enemy, Monster player) {
        if (activeEnemyItems != null) {
            List<Item> itemsRemoved = new ArrayList<>();
            for (Item item : activeEnemyItems) {
                if (item.getName().equals("Poison Spell")) {
                    item.use(enemy, player);
                    if (item.getDuration() == 0) {
                        itemsRemoved.add(item);
                    }
                }
            }
            activeEnemyItems.removeAll(itemsRemoved);
        }
    }

    private void applyPlayerArmor(Monster player, Monster enemy) {
        if (activePlayerItems != null) {
            List<Item> itemsRemoved = new ArrayList<>();
            for (Item item : activePlayerItems) {
                if (item.getName().equals("Magic Armor")) {
                    item.use(player, enemy);
                    if (item.getDuration() == 0) {
                        itemsRemoved.add(item);
                    }
                }
            }
            activePlayerItems.removeAll(itemsRemoved);
        }
    }

    private void applyEnemyArmor(Monster enemy, Monster player) {
        if (activeEnemyItems != null) {
            List<Item> itemsRemoved = new ArrayList<>();
            for (Item item : activeEnemyItems) {
                if (item.getName().equals("Magic Armor")) {
                    item.use(enemy, player);
                    if (item.getDuration() == 0) {
                        itemsRemoved.add(item);
                    }
                }
            }
            activeEnemyItems.removeAll(itemsRemoved);
        }
    }

    private void resetItemDuration(List<Item> items) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase("Poison Spell") || item.getName().equalsIgnoreCase("Magic Armor")) {
                item.setDuration(3);
            } else {
                item.setDuration(1);
            }
        }
    }
}