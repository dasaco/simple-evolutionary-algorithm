package ga;

import problems.P1;
import problems.P2;
import problems.Problem;
import problems.RevAckley;
import problems.RevRosenbrock;

public class MainApp {
	
	private static final int GENERATIONS = 100;
	private static final int POPULATION_SIZE = 50;
	private static final int ELITE_SIZE = 7;
	private static final double MUTATION_RATE = 0.025;
	private static final double CROSSOVER_RATE = 0.5;

	public static void main(String[] args) {
		
		Problem problem = new P1();
		
		SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm(GENERATIONS, CROSSOVER_RATE, MUTATION_RATE, ELITE_SIZE);
		ga.runAlgorithm(POPULATION_SIZE, problem);
	}
}