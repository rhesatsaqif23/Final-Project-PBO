package pokemon.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import pokemon.item.Item;
import pokemon.monster.Monster;

public class PokemonGame {

    public static void newGame(Player player) {
        Scanner input = new Scanner(System.in);
        player.initiateMonster();
        player.initiateItem();

        System.out.println("\nGreat! We've added some items to help you get started");
        System.out.print("Press any key to continue... ");
        String key = input.nextLine();

        System.out.println("\nThis is your Home Base, you can do some activities here");
        player.getHomeBase().homeBaseAction(player);
    }

    public static void loadFile(Player player) throws InterruptedException {
        Scanner input = new Scanner(System.in);

        try {
            File monsterFile = new File("monsters.txt");
            File itemFile = new File("items.txt");

            Scanner monsterScan = new Scanner(monsterFile);
            System.out.println("\nPrevious progress successfully loaded");
            System.out.println("Welcome back " + player.getName() + "!");
            System.out.print("\nPress any key to continue... ");
            String key = input.nextLine();

            try {
                Scanner itemScan = new Scanner(itemFile);
                while (itemScan.hasNext()) {
                    String[] itemData = itemScan.nextLine().split(" ");
                    player.addPrevItem(itemData);
                }

            } catch (FileNotFoundException e) {
                e.getMessage();
            }

            while (monsterScan.hasNext()) {
                String[] monsterData = monsterScan.nextLine().split(" ");
                player.addPrevMonster(monsterData);
            }

            player.getHomeBase().homeBaseAction(player);

        } catch (FileNotFoundException e) {
            System.out.println("Previous progress not found, starting new game");
            newGame(player);
        }
    }

    public static void saveFile(Player player) throws IOException {
        try (FileWriter fileOfMonsters = new FileWriter("monsters.txt")) {
            for (Monster monster : player.getOwnedMonsters()) {

                String name = monster.getName();
                String level = Integer.toString(monster.getLevel());
                String hp = Integer.toString((int) monster.getHp());
                String maxHp = Integer.toString((int) monster.getMaxHp());
                String ep = Integer.toString(monster.getEp());
                String element = monster.getElement().toString().toLowerCase();
                String evolved = Boolean.toString(monster.isEvolved());

                fileOfMonsters.write(
                        name + " " + level + " " + hp + " " + maxHp + " " + ep + " " + element + " " + evolved + "\n");
            }
            fileOfMonsters.close();

        } catch (Exception e) {
            e.getMessage();
        }

        try (FileWriter fileOfItems = new FileWriter("items.txt")) {
            for (Item item : player.getOwnedItems()) {

                String type = item.getType().toString().toLowerCase();
                String duration = Integer.toString(item.getDuration());
                String quantity = Integer.toString(item.getQuantity());

                fileOfItems.write(type + " " + duration + " " + quantity + "\n");
            }
            fileOfItems.close();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        Player player;
        String name;
        String key;

        System.out.println("\nWelcome to Pokemon: The Game");
        System.out.println("1. First time playing? Register new account");
        System.out.println("2. Already have an account? Log in");
        System.out.print("Choose an option: ");
        int option = input.nextInt();
        input.nextLine();

        while (option != 1 && option != 2) {
            System.out.println("Invalid input!");
            System.out.print("Choose an option: ");
            option = input.nextInt();
            input.nextLine();
        }

        switch (option) {
            case 1:
                System.out.println("\nFirst, let us know about you");
                System.out.print("Enter your name: ");
                name = input.nextLine();

                System.out.println("\nHi " + name + ", nice to meet you!");
                System.out.print("Press any key to continue... ");
                key = input.nextLine();

                player = new Player(name);
                newGame(player);
                saveFile(player);
                break;

            case 2:
                System.out.print("\nEnter your name: ");
                name = input.nextLine();

                player = new Player(name);
                loadFile(player);
                saveFile(player);
                break;

            default:
                break;
        }
    }
}