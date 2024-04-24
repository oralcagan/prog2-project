import java.util.*;

public class Main {
    public static void main(String[] args) {
        int turns = 50;
        HashMap<String,Integer> map = new HashMap<>();
        while(turns != 0) {
            turns--;
            Person ozberk = new Person(1.F,1.F,"Ozberk");
            Person flavio = new Person(0.01F,5.F,"Flavio");
            Person cagan = new Person(1000.F,0.5F,"Cagan");
            List<Person> people = Arrays.asList(ozberk,cagan,flavio);
            Random rand = new Random();
            while(people.size() > 1) {
                for(int i = 0; i < people.size(); i++) {
                    int enemyIndex = rand.nextInt(people.size()-1);
                    if(enemyIndex >= i) {
                        enemyIndex++;
                    }
                    Person enemy = people.get(enemyIndex);
                    enemy.health = enemy.health - people.get(i).attack;
                    people.set(enemyIndex,enemy);
                }
                List<Person> newPeople = new ArrayList<Person>();
                for(int i = 0; i < people.size(); i++) {
                    if(people.get(i).health >= 0) {
                        newPeople.add(people.get(i));
                    }
                }
                people = newPeople;
            }
            if(people.isEmpty()) {
                Integer oldNum = map.getOrDefault("No one", 0);
                map.put("No one",oldNum + 1);
                continue;
            }
            Person lastPerson = people.getFirst();
            Integer oldNum = map.getOrDefault(lastPerson.name, 0);
            map.put(lastPerson.name,oldNum + 1);
        }
        System.out.println(map.toString());
    }
}

class Person {
    float attack = 0.5F;
    float health = 1.F;
    String name;
    Person(float attack,float health,String name) {
        this.attack = attack;
        this.health = health;
        this.name = name;
    }
}