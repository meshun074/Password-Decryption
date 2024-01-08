/**
 * 
 */
package GP_Package;


/**
 * 
 */
public class Main {

    /* Do not change code below */
    public static void main(String[] args) {
        //Start Genetic algorithm by taking as input,
        // population size, Tournament selection rate, crossover rate, mutation rate and
        // number of generations and the file with the encrypted text.
    	Genetic_Algorithm ga1 = new Genetic_Algorithm(100000,4, 1.0F,0.1F,50,"Data1.txt");
    	ga1.start_GA();
        Genetic_Algorithm ga2 = new Genetic_Algorithm(100000,5, 1.0F,0.1F,50,"Data2.txt");
        ga2.start_GA();
    }
    
}
