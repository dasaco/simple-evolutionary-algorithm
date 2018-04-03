package ga;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import problems.Problem;

public class SimpleGeneticAlgorithm {

	private static final int tournamentSize = 5;
	
	private int generations;
	private double crossoverRate;
	private double mutationRate;
	private int elite;
	
	public SimpleGeneticAlgorithm(int generations, double crossoverRate, double mutationRate, int elite) {
		this.generations = generations;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.elite = elite;
	}

	public boolean runAlgorithm(int populationSize, Problem problem) {
		Population myPop = new Population(populationSize, true, problem);

		int generationCount = 1;
		while (generationCount <= generations) {
			myPop = evolvePopulation(myPop, problem);
			System.out.println(
					"Generation: " + generationCount + ". Fittest fitness: " + myPop.getFittest().getFitness());
			generationCount++;
		}
		System.out.println("Finished!");
		System.out.println("Generations: " + (generationCount - 1));
		System.out.println("Genes: ");
		System.out.println(myPop.getFittest().toString());
		return true;
	}

	public Population evolvePopulation(Population pop, Problem problem) {
		int elitismOffset = elite;
		Population newPopulation = new Population(pop.getIndividuals().size(), false, problem);
		
		pop.sort();
		
		newPopulation.getIndividuals().addAll(pop.getIndividuals().subList(0, elitismOffset));

		for (int i = elitismOffset; i < pop.getIndividuals().size(); i++) {
			Individual indiv1 = tournamentSelection(pop);
			Individual indiv2 = tournamentSelection(pop);
			Individual newIndiv = crossover(indiv1, indiv2, problem);
			newPopulation.getIndividuals().add(i, newIndiv);
		}

		for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	private Individual crossover(Individual indiv1, Individual indiv2, Problem problem) {
		Individual newSol = new Individual(problem);
		Random random = new Random();
		for (int i = 0; i < newSol.getDefaultGeneLength(); i++) {
			if (Math.random() <= crossoverRate) {
				newSol.setSingleGene(i, (indiv1.getSingleGene(i) + indiv2.getSingleGene(i)) / 2);
			} else {
				if (random.nextBoolean()) {
					newSol.setSingleGene(i, indiv1.getSingleGene(i));
				} else {
					newSol.setSingleGene(i, indiv2.getSingleGene(i));
				}
			}
		}
		
		return newSol;
	}

	private void mutate(Individual indiv) {
		
		Random random = new Random();
		
		for (int i = 0; i < indiv.getDefaultGeneLength(); i++) {
			if (Math.random() <= mutationRate) {
				double gene = 0.1*random.nextGaussian() + indiv.getSingleGene(i);
				
				if (gene < indiv.getProblem().getMinValues().get(i)) {
					gene = indiv.getProblem().getMinValues().get(i);
					System.out.println(gene + " is less than " + indiv.getProblem().getMinValues().get(i) + ", which is minimum.");
				}
				
				if (gene > indiv.getProblem().getMaxValues().get(i)) {
					gene = indiv.getProblem().getMaxValues().get(i);
					System.out.println(gene + " is more than " + indiv.getProblem().getMinValues().get(i) + ", which is maximum.");
				}
				
				indiv.setSingleGene(i, gene);
			}
		}
	}

	private Individual tournamentSelection(Population pop) {
		Population tournament = new Population(tournamentSize, false, pop.getProblem());
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.getIndividuals().size());
			tournament.getIndividuals().add(i, pop.getIndividual(randomId));
		}
		Individual fittest = tournament.getFittest();
		return fittest;
	}

	protected static double getFitness(Individual individual, Problem problem) {
		return problem.Eval(individual.getGenes());
	}

}