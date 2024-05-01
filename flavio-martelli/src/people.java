import java.util.Random;

public class people {
    Random rand = new Random();
    public int[] person;
    private final String name;
    public people(String name){
        this.name = name;
        this.person = new int[]{0, 0, 0, 0, 0, 0};
        this.person[0] = rand.nextInt(1, 11);
        int stat;
        for (int j = 1; j < 6; j ++){
            int max_stats = 35;
            int max_stat_single = 11;
            if (max_stats <= 10){
                max_stat_single = max_stats + 1;
            }
            stat = rand.nextInt(max_stat_single);
            this.person[j] =  stat;
            max_stats = max_stats - stat;
        }
    }
    public void change_stats(people person1, people person2) {
        int modifier = 0;
        if (person2.person[0] >= person1.person[0] + 5 || person1.person[0] >= person2.person[0] + 5) {
            modifier = 2;
        } else if (person2.person[0] >= person1.person[0] + 3 || person1.person[0] >= person2.person[0] + 3) {
            modifier = 1;
        }

        for (int i = 1; i < 6; i++) {
            if (modifier == 0){
                continue;
            }
            if (person2.person[0] > person1.person[0]) {
                if (person1.person[i] < person2.person[i]) {
                    person1.person[i] = person1.person[i] + modifier;
                } else if (person1.person[i] > person2.person[i]) {
                    person1.person[i] = person1.person[i] - modifier;
                }
                if (person1.person[i] < 0) {
                    person1.person[i] = 0;
                } else if (person1.person[i] > 10) {
                    person1.person[i] = 10;
                }
            } else if (person2.person[0] < person1.person[0]) {
                if (person2.person[i] < person1.person[i]) {
                    person2.person[i] = person2.person[i] + modifier;
                } else if (person2.person[i] > person1.person[i]) {
                    person2.person[i] = person2.person[i] - modifier;
                }
                if (person2.person[i] < 0) {
                    person2.person[i] = 0;
                } else if (person2.person[i] > 10) {
                    person2.person[i] = 10;
                }
            }
        }
    }

}
