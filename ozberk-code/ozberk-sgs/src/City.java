import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class City {
    Random random = new Random();
    int id;
    String cityName;
    int Xaxis;
    int Yaxis;
    List<Person> populus;
    HashSet<Integer> people = new HashSet<>();
    Group[] groups;
    GroupGenerator groupGenerator = new GroupGenerator();
    HashSet<Integer> lonelyPeople = new HashSet<>();
    float minGroupAffiliation;
    int numPeople;
    int numInterests;

    City(int id, String cityName, int Xaxis, int Yaxis, int numInterests, int numPeople, float minGroupAffiliation, List<Person> populus, HashSet<Integer> people) {
        this.id = id;
        this.Xaxis = Xaxis;
        this.cityName = cityName;
        this.Yaxis = Yaxis;
        this.populus = populus;
        this.people = people;
        groups = new Group[numInterests];
        for (int i = 0; i < numInterests; i++) {
            groups[i] = groupGenerator.generateRandomGroup();
        }
        for (int key : this.people) {
            boolean didJoin = false;
            for (int i = 0; i < numInterests; i++) {
                didJoin |= groups[i].updateGroupStatus(this.populus, key, i, minGroupAffiliation);
            }
            if (!didJoin) {
                this.lonelyPeople.add(key);
            }
        }
        this.numInterests = numInterests;
        this.minGroupAffiliation = minGroupAffiliation;
        this.numPeople = numPeople;
    }


    public void runTurn() {
        InteractionSet interactionSet = new InteractionSet();
        for (int groupIndex = 0; groupIndex < numInterests; groupIndex++) {
            Group group = groups[groupIndex];
            List<Integer> memberList = group.members.stream().toList();
            for (int i = 0; i < memberList.size(); i++) {
                boolean isInGroup = false;
                for (int j = i + 1; j < memberList.size(); j++) {
                    Integer idPersonA = memberList.get(i);
                    Integer idPersonB = memberList.get(j);
                    if (!interactionSet.has(idPersonA, idPersonB)) {
                        interactionSet.add(idPersonA, idPersonB);
                        Person personA = populus.get(idPersonA);
                        Person personB = populus.get(idPersonB);
                        Person.makePeopleInteract(personA, personB, group);
                        for (int k = 0; k < numInterests; k++) {
                            Group groupK = groups[k];
                            isInGroup |= groupK.updateGroupStatus(populus, idPersonA, k, minGroupAffiliation);
                            groupK.updateGroupStatus(populus, idPersonB, k, minGroupAffiliation);
                        }
                    }
                }
                if (!isInGroup) {
                    Integer personA = memberList.get(i);
                    lonelyPeople.add(personA);
                }
            }
        }
        interactionSet.clear();
    }

    public void addPeople(HashSet<Integer> peopleIndexSet) {
        this.people.addAll(peopleIndexSet);
    }
    public void removePerson(int personIndex) {
        people.remove(personIndex);
    }

    public void addPerson(int personID, int groupID) {
        this.people.add(personID);
        this.groups[groupID].members.add(personID);
    }

    public void cleanLonelyPeople() {
        lonelyPeople.clear();
    }

    public HashSet<Integer> getLonelyPeople() {
        return lonelyPeople;
    }
}
