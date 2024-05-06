import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        /*
        for (int j = 0; j < 100; j++) {
            city bologna = new city();
            long i = 0;
            for (; i < 1000000L; i++) {
                int sign = rand.nextInt(100);
                int amount = rand.nextInt(21);
                bologna.Health_change(sign, amount);
                bologna.dying_rate();
                if (bologna.population <= 0) {
                    System.out.println("the game finished at turn " + i);
                    bologna.population = 0;
                    break;
                }
                bologna.population_increase();
            }
            System.out.println("at turn " + i + " , the population is " + bologna.population);
        }

        //this first part is just the macro in which considering the conditions in the "city" class, it randomly changes
        //the healt stat making the game random.
        */

        int number_of_people = 3;
        //number of the people to create
        groups group1 = new groups();
        groups group2 = new groups();
        groups group3 = new groups();
        groups group4 = new groups();
        groups group5 = new groups();
        // creation of the groups depending on the interest


        people[] all_people = new people[number_of_people];
        for (int i = 0; i < number_of_people; i++) {
            String person_name = STR."person\{i}";
            people single = new people(person_name);
            all_people[i] = single;
        }
        //variable creation of people with random stats


        for (int i = 0; i < all_people.length; i++) {
            group1.assign_group(i, 1, all_people);
            group2.assign_group(i, 2, all_people);
            group3.assign_group(i, 3, all_people);
            group4.assign_group(i, 4, all_people);
            group5.assign_group(i, 5, all_people);
        }
        //first assignment of all the people into the groups that they belong to
    }
}