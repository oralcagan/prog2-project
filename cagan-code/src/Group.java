import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Group {
    float influence;
    HashSet<Integer> members = new HashSet<Integer>();

    Group(float influence) {
        this.influence = influence;
    }

    public boolean updateGroupStatus(HashMap<Integer,Person> allPeople, int personID, int groupIndex, float minInterestValue) {
        Person person = allPeople.get(personID);
        if(person.interests[groupIndex] >= minInterestValue) {
            this.members.add(personID);
            return true;
        }
        this.members.remove(personID);
        return false;
    }
}
