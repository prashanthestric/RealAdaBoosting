package binAdaBoost;

public class WeightsAndFuncgT {
	
	double ctPlus,ctMinus,gt;
	
	public WeightsAndFuncgT(double ctp,double ctm) {
		// TODO Auto-generated constructor stub
		ctPlus=ctp;
		ctMinus=ctm;
	}
	
	public double function_gT(WeakClassifier wc,double x){
		if(wc.classifyData(x)==1){
			return ctPlus;
		}
		else{
			return ctMinus;
		}
	}
}
