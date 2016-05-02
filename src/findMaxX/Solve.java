package findMaxX;

public class Solve {
	public static void main(String[] args) {
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
		int result = geneticAlgorithm.geneticAlgorithm();
		System.out.print("The result of genetic algorithm is: ");
		System.out.println(result);
	}
}
