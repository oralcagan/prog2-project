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
    HashMap<Integer, Integer> notInAGroup = new HashMap<>();
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
        for (int key : allPeople.keySet()) {
            boolean check = false;
            for (int i = 0; i < numInterests; i++) {
                boolean didJoin = groups[i].updateGroupStatus(allPeople, key, i, minGroupAffiliation);
                check = check || didJoin;
            }
            if (!check) {
                notInAGroup.putIfAbsent(key, 1);
            }
            this.numInterests = numInterests;
            this.minGroupAffiliation = minGroupAffiliation;
            this.numPeople = numPeople;
        }
    }

    public void runTurn(int numOfTurn) {
        InteractionSet interactionSet = new InteractionSet();
//        for (int key : lonelyPeople) {
//            String String = "hi"; //just a placeholder, here it should be implemented the random interaction of the lonely people
//        }
        for (int groupIndex = 0; groupIndex < numInterests; groupIndex++) {
            Group group = groups[groupIndex];
            List<Integer> memberList = group.members.stream().toList();
            int i = 0;
            for (; i < memberList.size() - 1; i++) {
                Integer idPersonA = memberList.get(i);
                boolean social = false;
                boolean entered = false;
                for (int j = i + 1; j < memberList.size(); j++) {
                    //Integer idPersonA = memberList.get(i);
                    Integer idPersonB = memberList.get(j);
                    if (!interactionSet.has(idPersonA, idPersonB)) {
                        interactionSet.add(idPersonA, idPersonB);
                        Person personA = allPeople.get(idPersonA);
                        Person personB = allPeople.get(idPersonB);
                        Person.makePeopleInteract(personA, personB, group);
                    }
                }
                for (int k = 0; k < numInterests; k++) {
                    Group groupK = groups[k];
                    entered = groupK.updateGroupStatus(allPeople, idPersonA, k, minGroupAffiliation);
                    social = social || entered;
                }
                if (!social) {
                    Integer nTurns;
                    nTurns = notInAGroup.putIfAbsent(i, 1);
                    if (nTurns != null) {
                        notInAGroup.put(i, nTurns + 1);
                    }
                }
                if (i == memberList.size() - 2) {
                    i += 1;
                    for (int k = 0; k < numInterests; k++) {
                        Group groupK = groups[k];
                        entered = groupK.updateGroupStatus(allPeople, idPersonA, k, minGroupAffiliation);
                        social = social || entered;
                    }
                    if (!social) {
                        Integer nTurns;
                        nTurns = notInAGroup.putIfAbsent(i, 1);
                        if (nTurns != null) {
                            notInAGroup.put(i, nTurns + 1);
                        }
                    }
                }
            }
        }
        interactionSet.clear();
        if (numOfTurn % 20 == 0){
            for (int k = 0; k < numInterests; k++) {
                Group groupK = groups[k];
                System.out.println(groupK.members + "membri del gruppo" + k);
            }
            System.out.println("---------------------------");
        }
    }
}