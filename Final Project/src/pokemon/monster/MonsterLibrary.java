package pokemon.monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterLibrary {
    private List<Monster> monsterLibrary = new ArrayList<>();

    public MonsterLibrary() {
        monsterLibrary.add(new FireMonster("Pikachu", 1, 500, 500, 0));
        monsterLibrary.add(new WaterMonster("Squirtle", 1, 500, 500, 0));
        monsterLibrary.add(new IceMonster("Swinub", 1, 500, 500, 0));
        monsterLibrary.add(new WindMonster("Pidgey", 1, 500, 500, 0));
        monsterLibrary.add(new EarthMonster("Diglett", 1, 500, 500, 0));
        monsterLibrary.add(new FireMonster("Charmander", 1, 500, 500, 0));
        monsterLibrary.add(new WaterMonster("Totodile", 1, 500, 500, 0));
        monsterLibrary.add(new IceMonster("Spheal", 1, 500, 500, 0));
        monsterLibrary.add(new WindMonster("Starly", 1, 500, 500, 0));
        monsterLibrary.add(new EarthMonster("Phanpy", 1, 500, 500, 0));
        monsterLibrary.add(new FireMonster("Cyndaquil", 1, 500, 500, 0));
        monsterLibrary.add(new WaterMonster("Psyduck", 1, 500, 500, 0));
        monsterLibrary.add(new IceMonster("Snorunt", 1, 500, 500, 0));
        monsterLibrary.add(new WindMonster("Hoothoot", 1, 500, 500, 0));
        monsterLibrary.add(new EarthMonster("Geodude", 1, 500, 500, 0));
        monsterLibrary.add(new FireMonster("Growlithe", 1, 500, 500, 0));
        monsterLibrary.add(new WaterMonster("Poliwag", 1, 500, 500, 0));
        monsterLibrary.add(new IceMonster("Sneasel", 1, 500, 500, 0));
        monsterLibrary.add(new WindMonster("Tailow", 1, 500, 500, 0));
        monsterLibrary.add(new EarthMonster("Onix", 1, 500, 500, 0));
    }

    public List<Monster> getMonsterLibrary() {
        return monsterLibrary;
    }

    public void printMonsterLibrary() {
        for (int i = 0; i < 10; i++) {
            String name = monsterLibrary.get(i).getName();
            String element = monsterLibrary.get(i).getElement().toString().toLowerCase();
            System.out.printf("%2d. %-10s (%s)\n", (i + 1), name, element);
        }
    }
}
