package binAdaBoost;

import java.util.ArrayList;

public class Main {
	
	static ArrayList<BoostedClassifier> boostedClassifiers;
	
	public static void main(String[] args){
		RealAdaBoost adaboost=new RealAdaBoost();
		boostedClassifiers=adaboost.execute();
		double x=4.432;
		//System.out.println("Testing on x="+x+". Classified as: "+boostedClassifiers.get(boostedClassifiers.size()-1).classifyData(x));
		}
}
