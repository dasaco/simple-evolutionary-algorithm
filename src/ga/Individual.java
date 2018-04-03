package ga;

import java.util.ArrayList;

import problems.Problem;

public class Individual implements Comparable<Individual> {

	private static final double DEFAULT_FITNESS = Double.MIN_VALUE;

	protected int defaultGeneLength = 2;
	private ArrayList<Double> genes = new ArrayList<Double>();
	private Problem problem;
	private double fitness = DEFAULT_FITNESS;

	public Individual(Problem problem) {
		this.problem = problem;
		this.defaultGeneLength = problem.getDimensions();
		this.genes = initPoint(problem);
	}

	protected double getSingleGene(int index) {
		return genes.get(index);
	}

	protected void setSingleGene(int index, double value) {
		genes.set(index, value);
		fitness = DEFAULT_FITNESS;
	}

	public double getFitness() {
		if (fitness == DEFAULT_FITNESS) {
			fitness = SimpleGeneticAlgorithm.getFitness(this, problem);
		}
		return fitness;
	}

	protected ArrayList<Double> getGenes() {
		return genes;
	}

	protected ArrayList<Double> initPoint(Problem problem) {
		ArrayList<Double> initPoint = new ArrayList<>();
		for (int dim = 0; dim < problem.getDimensions(); dim++) {
			initPoint.add(problem.getMinValues().get(dim)
					+ Math.random() * (problem.getMaxValues().get(dim) - problem.getMinValues().get(dim)));
		}
		return initPoint;
	}

	public int getDefaultGeneLength() {
		return defaultGeneLength;
	}

	public Problem getProblem() {
		return problem;
	}

	@Override
	public int compareTo(Individual o) {

		double myFitness = this.problem.Eval(this.genes);
		double hisFitness = this.problem.Eval(o.genes);

		if (myFitness > hisFitness) {
			return 1;
		} else if (myFitness < hisFitness) {
			return -1;
		}

		return 0;
	}

	@Override
	public String toString() {
		return "Individual x: " + this.getGenes().get(0) + ", y: " + this.getGenes().get(1) + " with result "
				+ this.problem.Eval(this.genes);
	}

}