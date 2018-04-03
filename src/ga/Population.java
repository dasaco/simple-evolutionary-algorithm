package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import problems.Problem;

public class Population {

	private List<Individual> individuals;
	private Problem problem;

	public Population(int size, boolean createNew, Problem problem) {
		individuals = new ArrayList<>();
		if (createNew) {
			createNewPopulation(size, problem);
		}
		this.problem = problem;
	}

	protected Individual getIndividual(int index) {
		return individuals.get(index);
	}

	protected Individual getFittest() {
		Individual fittest = individuals.get(0);
		for (int i = 0; i < individuals.size(); i++) {
			if (fittest.getFitness() <= getIndividual(i).getFitness()) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	private void createNewPopulation(int size, Problem problem) {
		for (int i = 0; i <= size; i++) {
			Individual newIndividual = new Individual(problem);
			individuals.add(i, newIndividual);
			System.out.println("Individual created" + newIndividual.getGenes().get(0));
		}
	}

	public List<Individual> getIndividuals() {
		return individuals;
	}
	
	public Problem getProblem() {
		return problem;
	}
	
	public void sort() {
		Collections.sort(individuals);
		Collections.reverse(individuals);
	}
	
	public void addIndividualsToTop(List<Individual> individuals) {
		individuals.addAll(0, individuals);
	}
}
