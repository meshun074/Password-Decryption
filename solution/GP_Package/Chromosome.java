package GP_Package;

// Each chromosome has a text as password and fitness
public class Chromosome {
	private String value;
	private double fitness;
	public Chromosome(String value, double fitness) {
		super();
		this.value = value;
		this.fitness = fitness;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	

}
