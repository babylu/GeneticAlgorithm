package findMaxX;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author babylu
 * Genetic Algorithm
 * 
 */
public class GeneticAlgorithm {
	Random random = new Random();
	private static final double pi = Math.PI;
	private int redoFlag = 0;
	private double averageParents;
	
	
	/**
	 * geneticAlgorithm
	 * @return best solution
	 */
	public int geneticAlgorithm(){
		ArrayList<String> parents = generateParents();
		ArrayList<Double> parentsFx = calculatedFx(parents);
		for(int i=0; i<parentsFx.size(); i++){
			if(parentsFx.get(i)>0.9999){
				return Integer.parseInt(parents.get(i), 2);
			}
		}
		double sumParentsFx = 0;
		for(int i=0; i<parentsFx.size(); i++){
			sumParentsFx += parentsFx.get(i);
		}
		this.averageParents = sumParentsFx/parentsFx.size();
		int result = generateChildren(parents);
		if(result == -1){
			return geneticAlgorithm();
		}else{
			return result;
		}
	}
	
	
	
	/**
	 * generateChildren
	 * @param parents
	 * @return best solution
	 */
	private int generateChildren(ArrayList<String> parents) {
		ArrayList<String> children = new ArrayList<String>();
		String parent1;
		String parent2;
		int parentsLength = parents.size();
		int bound = parentsLength;
		int position1;
		int position2;
		int flag1;
		int flag2;
		String child1;
		String child2;
		for(int i=0; i<parentsLength/2; i++){
			//randomly pick two parents
			position1 =  random.nextInt(bound);
			parent1 = parents.get(position1);
			parents.remove(position1);
			bound = bound-1;
			position2 = random.nextInt(bound);
			parent2 = parents.get(position2);
			parents.remove(position2);
			bound = bound-1;
			
			//generate children
			Random random1 = new Random();
			flag1 = random1.nextInt(parent1.length()-1);
			Random random2 = new Random(flag1);
			flag2 = random2.nextInt(parent1.length()-1);
			if(flag1>flag2){
				child1 = parent2.substring(0, flag2)+parent1.substring(flag2, flag1)+parent2.substring(flag1, parent1.length());
				child2 = parent1.substring(0, flag2)+parent2.substring(flag2, flag1)+parent1.substring(flag1, parent1.length());
			}else{
				child1 = parent1.substring(0, flag1)+parent2.substring(flag1, flag2)+parent1.substring(flag2, parent1.length());
				child2 = parent2.substring(0, flag1)+parent1.substring(flag1, flag2)+parent2.substring(flag2, parent1.length());
			}
			children.add(child1);
			children.add(child2);
		}
		ArrayList<Double> childrenFx = calculatedFx(children);
		for(int i=0; i<childrenFx.size(); i++){
			if(childrenFx.get(i)>0.9999){
				return Integer.parseInt(children.get(i), 2);
			}
		}
		
		//calculate redo flag
		double sumChildrenFx = 0;
		for(int i=0; i<childrenFx.size(); i++){
			sumChildrenFx += childrenFx.get(i);
		}
		if(sumChildrenFx/childrenFx.size()<averageParents){
			redoFlag += 1;
		}else{
			redoFlag = 0;
		}
		if(redoFlag == 3){
			return -1;
		}
		return generateChildren(children);
	}



	/**
	 * calculatedFx
	 * @param x list
	 * @return f(x) list
	 */
	private ArrayList<Double> calculatedFx(ArrayList<String> x) {
		ArrayList<Double> fx = new ArrayList<Double>();
		Double result;
		for(int i=0;i<x.size();i++){
			Integer xValue = Integer.parseInt(x.get(i), 2);
			result = Math.sin(xValue*pi/256);
			fx.add(result);
		}
		return fx;
	}
	
	/**
	 * generateParents
	 * @return parents
	 */
	private ArrayList<String> generateParents() {
		ArrayList<String> parents = new ArrayList<String>();
		String parent;
		for(int i=0; i<8; i++){
			parent = Integer.toBinaryString(random.nextInt(255));
			int length = parent.length();
			for(int j=0;j<8-length;j++){
				parent = String.join("","0",parent);
			}
			if(parents.contains(parent)){
				i--;
			}else{
				parents.add(parent);
			}
		}
		return parents;
	}
}
