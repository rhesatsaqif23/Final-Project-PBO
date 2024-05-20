package pokemon.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pokemon.item.Item;
import pokemon.monster.*;

public class HomeBase {

    public void homeBaseAction(Player player) {
        Scanner input = new Scanner(System.in);
        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("\n1. Show owned Pokemon");
            System.out.println("2. Show owned item");
            System.out.println("3. Level up Pokemon");
            System.out.println("4. Heal Pokemon");
            System.out.println("5. Evolve Pokemon");
            System.out.println("6. Go to Pokeshop");
            System.out.println("7. Go to dungeon");
            System.out.println("8. Exit game");
            System.out.print("Choose an action: ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                player.printOwnedMonster();

            } else if (choice == 2) {
                player.printOwnedItem();

            } else if (choice == 3) {
                System.out.println();

                for (int i = 0; i < player.getOwnedMonsters().size(); i++) {
                    String name = player.getOwnedMonsters().get(i).getName();
                    int ep = player.getOwnedMonsters().get(i).getEp();
                    System.out.printf("%d. %-10s (%d EP)\n", (i + 1), name, ep);
                }

                System.out.print("Choose Pokemon you want to level up: ");
                int number = input.nextInt();
                input.nextLine();

                while (number < 1 || number > player.getOwnedMonsters().size()) {
                    System.out.println("Invalid choice!");
                    System.out.print("Choose Pokemon you want to level up: ");
                    number = input.nextInt();
                    input.nextLine();
                }

                Monster monster = player.getOwnedMonsters().get(number - 1);
                levelUp(monster);

            } else if (choice == 4) {
                System.out.println();

                for (int i = 0; i < player.getOwnedMonsters().size(); i++) {
                    String name = player.getOwnedMonsters().get(i).getName();
                    double hp = player.getOwnedMonsters().get(i).getHp();
                    double maxHp = player.getOwnedMonsters().get(i).getMaxHp();
                    System.out.printf("%d. %-10s (%.0f/%.0f HP)\n", (i + 1), name, hp, maxHp);
                }

                System.out.print("Choose Pokemon you want to heal: ");
                int number = input.nextInt();
                input.nextLine();

                while (number < 1 || number > player.getOwnedMonsters().size()) {
                    System.out.println("Invalid choice!");
                    System.out.print("Choose Pokemon you want to heal: ");
                    number = input.nextInt();
                    input.nextLine();
                }

                Monster monster = player.getOwnedMonsters().get(number - 1);
                heal(monster);

            } else if (choice == 5) {
                System.out.println();

                for (int i = 0; i < player.getOwnedMonsters().size(); i++) {
                    String name = player.getOwnedMonsters().get(i).getName();
                    String element = player.getOwnedMonsters().get(i).getElement().toString().toLowerCase();
                    System.out.printf("%d. %-10s (%s)\n", (i + 1), name, element);
                }

                System.out.print("Choose Pokemon you want to evolve: ");
                int number = input.nextInt();
                input.nextLine();

                while (number < 1 || number > player.getOwnedMonsters().size()) {
                    System.out.println("Invalid choice!");
                    System.out.print("Choose Pokemon you want to evolve: ");
                    number = input.nextInt();
                    input.nextLine();
                }

                Monster monster = player.getOwnedMonsters().get(number - 1);
                Element element = chooseElement();
                if (element != null) {
                    evolve(player, monster, element);
                } else {
                    System.out.println("Invalid element choice!");
                }

            } else if (choice == 6) {
                enterShop(player);

            } else if (choice == 7) {
                player.enterDungeon();
                player.getDungeon().battleArena(player);

            } else if (choice == 8) {
                keepPlaying = false;
                System.out.println("\nThank you for playing the game!\n");
                break;

            } else {
                System.out.println("\nInvalid choice!");
            }

            System.out.print("\nPress any key to continue...");
            String key = input.nextLine();
        }
    }

    private Element chooseElement() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n1. Fire");
        System.out.println("2. Ice");
        System.out.println("3. Wind");
        System.out.println("4. Earth");
        System.out.println("5. Water");
        System.out.print("Choose Pokemon element: ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                return Element.FIRE;
            case 2:
                return Element.ICE;
            case 3:
                return Element.WIND;
            case 4:
                return Element.EARTH;
            case 5:
                return Element.WATER;
            default:
                return null;
        }
    }

    public void levelUp(Monster monster) {
        if (monster.getLevel() < 99) {
            int minimumEp = 100 + ((monster.getLevel() - 1) * 10);
            System.out.println("\nEP before level up: " + monster.getEp() + "/" + minimumEp);

            if (monster.getEp() >= minimumEp) {
                monster.setLevel(monster.getLevel() + 1);
                System.out.println(monster.getName() + " leveled up to level " + monster.getLevel() + "!");

                monster.setMaxHp(monster.getMaxHp() + Math.ceil(monster.getMaxHp() * 0.08));
                monster.setHp(monster.getMaxHp());
                System.out.println(monster.getName() + " health points increased to " + monster.getMaxHp() + "!");

                monster.setEp(monster.getEp() - minimumEp);
                minimumEp = 100 + ((monster.getLevel() - 1) * 5);
                System.out.println("EP after level up: " + monster.getEp() + "/" + minimumEp);
                monster.setEvolved(false);

            } else {
                System.out.println("Level up failed, not enough EP!");
            }

        } else {
            System.out.println(monster.getName() + " has reached its max level!");
        }
    }

    public void heal(Monster monster) {
        monster.setHp(monster.getMaxHp());
        System.out.println("\n" + monster.getName() + " has been healed!");
        System.out.println(monster.getName() + " HP: " + (int) monster.getHp() + "/" + (int) monster.getMaxHp());
    }

    public void evolve(Player player, Monster monster, Element newElement) {
        if (!monster.isEvolved()) {
            if (monster.canEvolve(newElement)) {
                Monster evolvedMonster = createEvolvedMonster(monster, newElement);
                player.getOwnedMonsters().add(evolvedMonster);
                evolvedMonster.setEvolved(true);

                player.getOwnedMonsters().remove(monster);
                System.out.println(
                        "\n" + monster.getName() + " evolved into " + newElement.toString().toLowerCase() + " type!");

            } else {
                System.out.println("\n" + monster.getName() + " cannot evolve into "
                        + newElement.toString().toLowerCase() + " type!");
            }

        } else {
            System.out.println("\n" + monster.getName() + " has already evolved at this level!");
        }
    }

    private Monster createEvolvedMonster(Monster monster, Element newElement) {
        String name = monster.getName();
        int level = monster.getLevel();
        double hp = monster.getHp();
        double maxHp = monster.getMaxHp();
        int ep = monster.getEp();

        switch (newElement) {
            case FIRE:
                return new FireMonster(name, level, hp, maxHp, ep);
            case ICE:
                return new IceMonster(name, level, hp, maxHp, ep);
            case WIND:
                return new WindMonster(name, level, hp, maxHp, ep);
            case EARTH:
                return new EarthMonster(name, level, hp, maxHp, ep);
            case WATER:
                return new WaterMonster(name, level, hp, maxHp, ep);
            default:
                return null;
        }
    }

    private void enterShop(Player player) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWelcome to Pokeshop! You can buy items here");

        int price;
        int discountItem = (int) (Math.random() * 4) + 1;
        int choice = 0;

        while (choice != 5) {
            System.out.println();

            for (int i = 1; i <= 4; i++) {
                String name = "";

                switch (i) {
                    case 1:
                        name = "Health Potion";
                        break;
                    case 2:
                        name = "Elemental Potion";
                        break;
                    case 3:
                        name = "Poison Spell";
                        break;
                    case 4:
                        name = "Magic Armor";
                        break;
                    default:
                        break;
                }

                if (i == discountItem) {
                    price = 30;
                    System.out.printf("%d. %-18s Price: %d ", i, name, price);
                    System.out.println("(25% off!)");

                } else {
                    price = 40;
                    System.out.printf("%d. %-18s Price: %d \n", i, name, price);
                }
            }

            System.out.println("5. Exit");
            System.out.print("Choose item you want to buy: ");
            choice = input.nextInt();
            input.nextLine();

            while (choice < 1 || choice > 5) {
                System.out.println("Invalid choice!");
                System.out.print("Choose item you want to buy: ");
                choice = input.nextInt();
            }

            if (choice != 5) {
                if (choice == discountItem) {
                    price = 30;
                } else {
                    price = 40;
                }
                buyItem(player, choice, price);
            }
        }

        System.out.println("\nThank you for visiting Pokeshop!");
    }

    private void buyItem(Player player, int choice, int price) {
        Scanner input = new Scanner(System.in);
        System.out.println();

        List<Monster> enoughEpMonster = new ArrayList<>();
        for (Monster monster : player.getOwnedMonsters()) {
            if (monster.getEp() >= price) {
                enoughEpMonster.add(monster);
            }
        }

        if (!enoughEpMonster.isEmpty()) {
            for (int i = 0; i < enoughEpMonster.size(); i++) {
                String name = enoughEpMonster.get(i).getName();
                int ep = enoughEpMonster.get(i).getEp();
                System.out.printf("%d. %-10s (%d EP)\n", (i + 1), name, ep);
            }

            System.out.print("Choose Pokemon you want to use for payment: ");
            int number = input.nextInt();
            input.nextLine();

            while (number < 1 || number > enoughEpMonster.size()) {
                System.out.println("Invalid choice!");
                System.out.print("Choose Pokemon you want to use for payment: ");
                number = input.nextInt();
                input.nextLine();
            }

            Monster monster = enoughEpMonster.get(number - 1);
            monster.setEp(monster.getEp() - price);

            Item item = player.getOwnedItems().get(choice - 1);
            item.setQuantity(item.getQuantity() + 1);

            if (item.getName().equalsIgnoreCase("Poison Spell") || item.getName().equalsIgnoreCase("Magic Armor")) {
                item.setDuration(3);
            } else {
                item.setDuration(1);
            }

            System.out.println("\nYou have successfully purchased " + item.getName());
            System.out.println(item.getName() + " quantity: " + item.getQuantity());
            System.out.println(item.getName() + " price: " + price);
            System.out.println(monster.getName() + " EP: " + monster.getEp());

        } else {
            System.out.println("You don't have Pokemon with enough EP!");
        }

        System.out.print("\nPress any key to continue...");
        String key = input.nextLine();
    }
}