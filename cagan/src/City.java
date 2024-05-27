import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class City {
    String name;
    List<Person> populus;
    public HashSet<Integer> people = new HashSet<>();
    Group[] groups;
    GroupGenerator groupGenerator = new GroupGenerator();

    HashSet<Integer> lonelyPeople = new HashSet<>();
    float minGroupAffiliation;
    int numPeople;
    int numInterests;

    City(int numInterests, int numPeople, float minGroupAffiliation, List<Person> populus) {
        this.populus = populus;
        groups = new Group[numInterests];
        for (int i = 0; i < numInterests; i++) {
            groups[i] = groupGenerator.generateRandomGroup();
        }
        for (int key : this.people) {
            for (int i = 0; i < numInterests; i++) {
                boolean didJoin = groups[i].updateGroupStatus(this.populus, key, i, minGroupAffiliation);
                if(!didJoin) {
                    lonelyPeople.add(key);
                } else {
                    lonelyPeople.remove(key);
                }
            }
        }
        this.numInterests = numInterests;
        this.minGroupAffiliation = minGroupAffiliation;
        this.numPeople = numPeople;
    }

    public void runTurn() {
        for(Integer key : this.lonelyPeople) {
            boolean didJoin = false;
            for(int i = 0; i < groups.length; i++) {
                didJoin = groups[i].updateGroupStatus(populus,key,i,minGroupAffiliation);
            }
            if(didJoin) {
                this.lonelyPeople.remove(key);
            }
        }
        InteractionSet interactionSet = new InteractionSet();
        for (int groupIndex = 0; groupIndex < numInterests; groupIndex++) {
            Group group = groups[groupIndex];
            List<Integer> memberList = group.members.stream().toList();
            for (int i = 0; i < memberList.size(); i++) {
                for (int j = 1; j < memberList.size(); j++) {
                    Integer idPersonA = memberList.get(i);
                    Integer idPersonB = memberList.get(j);
                    if (!interactionSet.has(idPersonA, idPersonB)) {
                        interactionSet.add(idPersonA, idPersonB);
                        Person personA = populus.get(idPersonA);
                        Person personB = populus.get(idPersonB);
                        Person.makePeopleInteract(personA, personB, group);
                        for (int k = 0; k < numInterests; k++) {
                            Group groupK = groups[k];
                            groupK.updateGroupStatus(populus, idPersonA, k, minGroupAffiliation);
                            groupK.updateGroupStatus(populus, idPersonB, k, minGroupAffiliation);
                        }
                    }
                }
            }
        }
        interactionSet.clear();
    }



}
