package binAdaBoost;

import java.util.ArrayList;

public class BoostedClassifier {
	
	ArrayList<WeightsAndFuncgT> weightsAndFuncgTs;
	ArrayList<WeakClassifier> hTs;
	int ni_enc_2;
	
	public BoostedClassifier(ArrayList<WeightsAndFuncgT> weightsAndFuncgTs,ArrayList<WeakClassifier> hTs) {
		// TODO Auto-generated constructor stub
		this.weightsAndFuncgTs=weightsAndFuncgTs;
		this.hTs=hTs;
		ni_enc_2=24;
	}
	
	int classifyData(double x){
		
		int fTofX=0;
		for(int i=0;i<weightsAndFuncgTs.size();i++){
			fTofX+=weightsAndFuncgTs.get(i).function_gT(hTs.get(i), x);
		}
		if(fTofX>0){
			return 1;
		}
		else{
			return -1;
		}
	}
	
	int classifierErrorOnTrainingData(ArrayList<Double> x,ArrayList<Integer> y){
		int count=0;
		for(int i=0;i<x.size();i++){
			if(classifyData(x.get(i))!=y.get(i)){
				count++;
			}
		}		
		return count;
	}

}
