public class city {
    int food = 100;
    int health = 60;
    int population = 60;
    public void dying_rate(){
        if (this.health < 20) {
            this.population -= 4;
        }
        if (this.health < 50) {
            this.population -= 2;
        }
        this.population -= 2;
    }
    public void population_increase(){
        if (this.food >= this.population+30){
            this.population += 7;
        } else if (this.food >= this.population) {
            this.population += 4;
        }
    }
    public void Health_change(int sign, int amount){
        if (sign >= 45) {
            this.health += amount;
        }
        else {
            this.health -= amount;
        }
        if (health < 0) {
            this.health = 0;
        }
        if (health > 100) {
            this.health = 100;
        }
    }
}