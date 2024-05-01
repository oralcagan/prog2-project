import java.util.HashSet;
import java.util.Set;
public class groups {
    public Set<Integer>group = new HashSet<>();

    public void assign_group(int index, int value, people[] all_people){
        if (this.group.contains(index)) {
            if (all_people[index].person[value] < 7){
                this.group.remove(index);
            }
        }
        else {
            if (all_people[index].person[value] >= 7) {
                this.group.add(index);
            }
        }
    }
}

