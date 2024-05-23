
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class City {
    String cityname;
    int Xaxis;
    int Yaxis;
    HashMap<Integer, Person> allPeople = new HashMap<Integer, Person>();
    Group[] groups;
    PersonGenerator personGenerator = new PersonGenerator();
    GroupGenerator groupGenerator = new GroupGenerator();

    HashMap<Integer, Integer> lonelyPeople = new HashMap<>();
    float minGroupAffiliation;
    int numPeople;
    int numInterests;

    City(String cityname, int Xaxis, int Yaxis, int numInterests, int numPeople, float minGroupAffiliation) {
        this.cityname = cityname;
        this.Xaxis = Xaxis;
        this.Yaxis = Yaxis;
        groups = new Group[numInterests];
        for (int i = 0; i < numInterests; i++) {
            groups[i] = groupGenerator.generateRandomGroup();
        }
        for (int i = 0; i < numPeople; i++) {
            allPeople.put(i, personGenerator.generateRandomPerson());
        }
        HashSet<Integer> notInAGroup = new HashSet<>();
        for (int key : allPeople.keySet()) {
            for (int i = 0; i < numInterests; i++) {
                boolean didJoin = groups[i].updateGroupStatus(allPeople, key, i, minGroupAffiliation);
                if (!didJoin) {
                    notInAGroup.add(key);
                } else {
                    notInAGroup.remove(key);
                }
            }
        }
        for (Integer key : notInAGroup) {
            Integer value = this.lonelyPeople.putIfAbsent(key, 1);
            if (value != null) {
                lonelyPeople.put(key, value + 1);
            }
        }
        this.numInterests = numInterests;
        this.minGroupAffiliation = minGroupAffiliation;
        this.numPeople = numPeople;
    }


//    public class ChangeStats {
//        private List<City> cities;
//
//        public ChangeStats(List<City> cities) {
//            this.cities = cities;
//        }
//
//        private City findCityByName(String cityName) {
//            for (City city : cities) {
//                if (city.name.equals(cityName)) {
//                    return city;
//                }
//            }
//            return null;
//        }
//
//        public void setNumInterests(String cityName, int numInterests) {
//            City city = findCityByName(cityName);
//            if (city != null) {
//                city.numInterests = numInterests;
//            }
//        }
//
//        public void setNumPeople(String cityName, int numPeople) {
//            City city = findCityByName(cityName);
//            if (city != null) {
//                city.numPeople = numPeople;
//            }
//        }
//
//        public void setMinGroupAffiliation(String cityName, float minGroupAffiliation) {
//            City city = findCityByName(cityName);
//            if (city != null) {
//                city.minGroupAffiliation = minGroupAffiliation;
//            }
//        }}

        public void runTurn() {
            for (Integer key : this.lonelyPeople.keySet()) {
                boolean didJoin = false;
                for (int i = 0; i < groups.length; i++) {
                    didJoin = groups[i].updateGroupStatus(allPeople, key, i, minGroupAffiliation);
                }
                if (didJoin) {
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
                            Person personA = allPeople.get(idPersonA);
                            Person personB = allPeople.get(idPersonB);
                            Person.makePeopleInteract(personA, personB, group);
                            for (int k = 0; k < numInterests; k++) {
                                Group groupK = groups[k];
                                groupK.updateGroupStatus(allPeople, idPersonA, k, minGroupAffiliation);
                                groupK.updateGroupStatus(allPeople, idPersonB, k, minGroupAffiliation);
                            }
                        }
                    }

                }
            }
            interactionSet.clear();
        }
    }

