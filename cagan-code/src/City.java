import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class City {
    String name;
    HashMap<Integer, Person> allPeople = new HashMap<Integer, Person>();
    Group[] groups;
    PersonGenerator personGenerator = new PersonGenerator();
    GroupGenerator groupGenerator = new GroupGenerator();
    HashSet<Integer> lonelyPeople = new HashSet<>();
    HashMap<Integer,Integer> lonelinessCounter = new HashMap<>();
    float minGroupAffiliation;
    int numPeople;
    int numInterests;

    City(int numInterests, int numPeople, float minGroupAffiliation) {
        groups = new Group[numInterests];
        for (int i = 0; i < numInterests; i++) {
            groups[i] = groupGenerator.generateRandomGroup();
        }
        for (int i = 0; i < numPeople; i++) {
            allPeople.put(i, personGenerator.generateRandomPerson(numInterests));
        }
        this.numInterests = numInterests;
        this.minGroupAffiliation = minGroupAffiliation;
        this.numPeople = numPeople;
        for(int i = 0; i < numPeople; i++) {
            lonelinessCounter.put(i,0);
        }
    }

    public void runTurn() {
        for(int key : this.lonelinessCounter.keySet()) {
            int lastVal = this.lonelinessCounter.get(key);
            this.lonelinessCounter.put(key,lastVal+1);
        }
        InteractionSet interactionSet = new InteractionSet();
        for (int groupIndex = 0; groupIndex < this.numInterests; groupIndex++) {
            Group group = groups[groupIndex];
            List<Integer> memberList = group.members.stream().toList();
            for (int i = 0; i < memberList.size()-1; i++) {
                for (int j = i+1; j < memberList.size(); j++) {
                    Integer idPersonA = memberList.get(i);
                    Integer idPersonB = memberList.get(j);
                    if (!interactionSet.has(idPersonA, idPersonB)) {
                        interactionSet.add(idPersonA, idPersonB);
                        Person personA = allPeople.get(idPersonA);
                        Person personB = allPeople.get(idPersonB);
                        Person.makePeopleInteract(personA, personB, group);
                    }
                }
            }
        }
        for(int key : this.allPeople.keySet()) {
            boolean inAGroup = false;
            for(int i = 0; i < this.numInterests; i++) {
                inAGroup |= this.groups[i].updateGroupStatus(this.allPeople,key,i,this.minGroupAffiliation);
            }
            if(inAGroup) {
                this.lonelinessCounter.put(key,0);
                this.lonelyPeople.remove(key);
                continue;
            }
            if(this.lonelinessCounter.get(key) >= 5) {
                this.lonelyPeople.add(key);
            }
        }
        interactionSet.clear();
    }
}
