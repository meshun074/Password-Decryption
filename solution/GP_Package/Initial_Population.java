package GP_Package;

import java.util.ArrayList;
import java.util.Random;

public class Initial_Population {
	private final int populationSize;
	private final int chromosomeSize;
	private final ArrayList<Chromosome> population = new ArrayList<>();
	//Generate a random initial population by taking as input the size of the population
	public Initial_Population(int populationSize, int chromosomeSize)
	{
		this.populationSize=populationSize;
		this.chromosomeSize=chromosomeSize;
	}
	
	public ArrayList<Chromosome> generate() {
	Random r = new Random();
    String characters = "-abcdefghijklmnopqrstuvwxyz";
	    for (int i = 0; i < populationSize; i++) {
	    	StringBuilder chromosome = new StringBuilder();
	    	for(int k =0; k< chromosomeSize; k++) {
				//generate and append  chromosome size of random characters from string characters
	    		chromosome.append(characters.charAt(r.nextInt(27)));
	    	}
//			add generated chromosome to population
	    	population.add(new Chromosome(chromosome.toString(),0.0));
	    }
		return population;
	}
}
