package GP_Package;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Genetic_Algorithm {
	private List<Chromosome> Population = new ArrayList<>();
	private List<String> newPopulation;
	private Set<Integer> mask;
	private final int popSize;
	private final float crossoverRate;
	private final float mutationRate;
	private final int Gen;
	private final int TSrate;
	private final File file;
	private final Random r = new Random();

	public Genetic_Algorithm(int popSize, int TSrate, float crossoverRate, float mutationRate, int Gen, String filename) {
		this.popSize = popSize;
		this.TSrate = TSrate;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.Gen = Gen;
		file = new File (System.getProperty("user.dir") +"\\src\\"+filename);
		}
	public void start_GA()
	{
		// get the encrypted text int the file
		String text = getData(file);
		System.out.println();

		//Initialize population
		Initial_Population pop = new Initial_Population(popSize, Integer.parseInt(text.substring(0,2)));
		Population = pop.generate();
		//remove the chromosome length from the encrypted text
		text = getData(file).substring(2);
		// start generation
		for (int i = 0; i < Gen; i++) {
			newPopulation = new ArrayList<>();
			//generate fitness of each chromosome
            for (Chromosome chromosome : Population) {
                chromosome.setFitness(Evaluation.fitness(chromosome.getValue(), text));
            }
			// output current best chromosome
			System.out.println(sortPop(Population).get(0).getValue()+" Generation "+i+" "+sortPop(Population).get(0).getFitness());
			//implement elitism
			newPopulation.addAll(elitismPop(Population));
			//Tournament Selection
            List<String> tempPopulation = new ArrayList<>(tournamentSelect(Population, TSrate));
			
			//Perform uniformCrossover and mutation
			uniformCrossoverMutation(tempPopulation, crossoverRate, mutationRate);

			//updating population for new generation
			for(int ch = 0; ch< Population.size(); ch++)
			{
				Population.get(ch).setValue(newPopulation.get(ch));
			}
		}
	}
	
	// Uniform Crossover
	private void uniformCrossoverMutation(List<String> newPop, float CO_Rate, float M_Rate) {
		// TODO Auto-generated method stub
		mask = generateMask(newPop.get(0).length());

		int i = 1;
		while(i < newPop.size())
		{
//			Crossover rate
			if(i< newPop.size() *0.99*CO_Rate)
			{
				StringBuilder c1 = new StringBuilder();
				StringBuilder c2 = new StringBuilder();
				for (int c = 0; c < newPop.get(0).length(); c++) {
					c1.append(mask.contains(c) ? newPop.get(i).charAt(c) : newPop.get(i - 1).charAt(c));
					c2.append(mask.contains(c) ? newPop.get(i - 1).charAt(c) : newPop.get(i).charAt(c));
				}

				//mutationRate
				if(i< newPop.size() *0.99*M_Rate) {
					c1.setCharAt(r.nextInt(c1.length()), getRandomChar());
					c2.setCharAt(r.nextInt(c2.length()), getRandomChar());
				}
				newPop.set(i - 1, String.valueOf(c1));
				newPop.set(i, String.valueOf(c2));
			}

			newPopulation.add(newPop.get(i-1));
			if(Population.size()==newPopulation.size())
				break;
			newPopulation.add(newPop.get(i));
			if(Population.size()==newPopulation.size())
				break;
			i+=2;
		}
	}

	// generate random character for mutation
	private char getRandomChar()
	{
		String characters = "-abcdefghijklmnopqrstuvwxyz";
		return characters.charAt(r.nextInt(27));
	}
	
	//Generate mask for crossover
	private Set<Integer> generateMask(int length)
	{
		int masklength = r.nextInt(length/4, (length/2));
		mask = new TreeSet<>();
		
		while(masklength != mask.size())
		{
			mask.add(r.nextInt(length));
		}
		return mask;
	}
	//returns the text in a file
	private String getData(File file)
	{
		StringBuilder text = new StringBuilder();
		try (Scanner sc = new Scanner(file)) {
			while(sc.hasNext())
			{
				text.append(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file not found");
		}
		return text.toString();
		
	}

	//sort and return 1% of the best chromosome as string
	private  ArrayList<String> elitismPop( List<Chromosome> population2){
		List< Chromosome> list = new ArrayList<>(sortPop(population2));
		
		int rate = (int) (population2.size()*0.01);
		int counter = 0;
		ArrayList<String> elitismPopulation = new ArrayList<>();
		for(Chromosome x: list)
		{
			elitismPopulation.add(x.getValue());
			counter++;
			if(counter==rate)
				break;
		}
		return elitismPopulation;
	}

	//Sort chromosome according to  fitness
	private List<Chromosome> sortPop(List<Chromosome> popu){
		List< Chromosome> list = new ArrayList<>(popu);
		list.sort((o1, o2) -> {
            // TODO Auto-generated method stub
            if (o1.getFitness() == o2.getFitness())
                return 0;

            else if (o1.getFitness() > o2.getFitness())
                return 1;
            return -1;
        });
		return list;
	}

	//Perform tournament selection
	private ArrayList<String> tournamentSelect(List<Chromosome> population, int rate)
	{
		ArrayList<String> TSpop = new ArrayList<>();
		List<Chromosome> temp;
		
		for(int i = 0; i < Population.size(); i++) {
			temp = new ArrayList<>();
			for(int k = 0; k < rate; k++) {
				temp.add(population.get(r.nextInt(population.size())));
			}
			TSpop.add(sortPop(temp).get(0).getValue());
		}
		return TSpop;
	}
}
