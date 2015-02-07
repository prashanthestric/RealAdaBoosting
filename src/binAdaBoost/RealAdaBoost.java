package binAdaBoost;

import java.util.ArrayList;

public class RealAdaBoost {
	
	int T;
	int n;
	double epsilon;
	ArrayList<Double> x;
	ArrayList<Integer> y;
	ArrayList<Double> p;
	ArrayList<Double> zt;
	
	public ArrayList<BoostedClassifier> execute(){

		FileHandler fileHandler=new FileHandler(RealAdaBoost.class.getClassLoader().getResource("binAdaBoost/input.txt").getPath());
		//double alpha=0.0;
		WeightsAndFuncgT weightsAndFuncgT;
		//ArrayList<Double> q=new ArrayList<Double>();
		int ni_enc_3=16;
		ArrayList<Error> errT=new ArrayList<Error>();
		ArrayList<WeightsAndFuncgT> weightsAndFuncgTs=new ArrayList<WeightsAndFuncgT>();
		ArrayList<WeakClassifier> hT=new ArrayList<WeakClassifier>();
		ArrayList<BoostedClassifier> fTsofX=new ArrayList<BoostedClassifier>();
		zt=new ArrayList<Double>();
		T=fileHandler.getT();
		n=fileHandler.getN();
		epsilon=fileHandler.getEpsilon();
		x=fileHandler.getX();
		y=fileHandler.getY();
		p=fileHandler.getP();
		
		printCurrentData(); System.out.println();System.out.println();
		
		for(int i=0;i<T;i++){	//should be replaced as i<T
			System.out.println();
			System.out.println("Iteration "+(i+1));
			WeakClassifier best=getBestClassifier();
			hT.add(best);
			errT.add(best.classifierError);
			//System.out.println("Classifier h"+i+"I(x > "+best.threshold+") y="+best.aboveThresholdIs+", else y="+(-1*best.aboveThresholdIs));
			System.out.println("Classifier h"+i+" I(x>"+best.threshold+")");
			weightsAndFuncgT=calcWeights(best.classifierError);
			weightsAndFuncgTs.add(weightsAndFuncgT);
			//System.out.println("alpha = "+alpha); ---print ctplus ctminus---
			p=updateProbabilities(best.classifierError, weightsAndFuncgT, best);
			fTsofX.add(new BoostedClassifier(weightsAndFuncgTs,hT));
			printCurrentData(best.classifierError,weightsAndFuncgT,fTsofX.get(i),i);
			System.out.println("Bound on error="+getBound());
		}
		
		return fTsofX;
	}
	
	WeakClassifier getBestClassifier(/*ArrayList<Double> x,ArrayList<Integer> y,ArrayList<Double> p*/){
		
		WeakClassifier wkClassifier=new WeakClassifier(0.0,0,null);
		double threshold;
		Error err_abvThreshIsPlus;
		Error bestError=new Error(0.0,1,0,0.0,0.0,0.0,0.0);	//init
		double min_error=1.0;
		
		for(int i=0;i<n;i++){
			if(i==0){
				threshold=x.get(0)-1;
			}
			else if(i==n-1){
				threshold=x.get(n-1)+1;
			}
			else{
				threshold=(x.get(i)+x.get(i+1))/2;
			}
			err_abvThreshIsPlus=getError(threshold,+1);
			
			//which direction has smaller error?
			if(err_abvThreshIsPlus.errGofJ < 0.50){
				if(err_abvThreshIsPlus.errGofJ<min_error){
					min_error=err_abvThreshIsPlus.errGofJ;
					wkClassifier=new WeakClassifier(threshold,+1,err_abvThreshIsPlus);
				}
			}
			else{
				bestError=getError(threshold, -1);
				if(bestError.errGofJ<min_error){
					min_error=bestError.errGofJ;
					wkClassifier=new WeakClassifier(threshold,-1,bestError);
				}
			}
			//now bestError has the direction in which the error is minimum for the current threshold						
		}		
		//at this point, we have checked all possible hypotheses. If the error is greater than 0.5 here, there has been a calculation error while updating p's.
		
			return wkClassifier;		
	}
	
	Error getError(double threshold,int aboveThresholdIs){
		
		WeakClassifier classifier=new WeakClassifier(threshold, aboveThresholdIs,null);
		double errG=0.0;
		int noOfErrors=0;
		double prPlus=0.0;
		double prMinus=0.0;
		double pwPlus=0.0;
		double pwMinus=0.0;
		for(int i=0;i<n;i++){
			if((classifier.classifyData(x.get(i))==1)&&(y.get(i)==1)){
				prPlus+=p.get(i);
				
			}
			else if((classifier.classifyData(x.get(i))==-1)&&(y.get(i)==-1)){
				prMinus+=p.get(i);
				
			}
			else if((classifier.classifyData(x.get(i))==-1)&&(y.get(i)==1)){
				pwPlus+=p.get(i);
				noOfErrors++;
			}
			else if((classifier.classifyData(x.get(i))==1)&&(y.get(i)==-1)){
				pwMinus+=p.get(i);
				noOfErrors++;
			}
		}
		errG=Math.sqrt(prPlus*pwMinus)+Math.sqrt(pwPlus*prMinus);
		return new Error(errG,aboveThresholdIs,noOfErrors,prPlus,prMinus,pwPlus,pwMinus);
	}
	
	WeightsAndFuncgT calcWeights(Error errorT){
		
		double ctplus=0.5*(Math.log((errorT.pRplus+epsilon)/(errorT.pWminus+epsilon)));
		double ctminus=0.5*(Math.log((errorT.pWplus+epsilon)/(errorT.pRminus+epsilon)));
		return new WeightsAndFuncgT(ctplus, ctminus);
	}
	
	ArrayList<Double> updateProbabilities(Error error,WeightsAndFuncgT alpha,WeakClassifier classifier){
		
		ArrayList<Double> pTemp=new ArrayList<Double>();
		double zT=2*error.errGofJ;
		System.out.println("Normalization constant z="+zT);
		zt.add(zT);
		for(int i=0;i<p.size();i++){
			double q=Math.exp(-1*alpha.function_gT(classifier, x.get(i))*y.get(i));
			pTemp.add((p.get(i)*q)/zT);
		}
		return pTemp;		
	}
	
	public double getBound(){
		double bound=1.0;
		for(int i=0;i<zt.size();i++){
			bound=bound*zt.get(i);
		}
		return bound;
	}
		
	public void printCurrentData() {
		/*
		System.out.println("n="+n+"; epsilon="+epsilon);
		
		System.out.print("x:   ");
		for(int i=0;i<x.size();i++){
			System.out.print(x.get(i).toString()+" "); 
		}
		System.out.println();
		System.out.print("y:   ");
		for(int i=0;i<y.size();i++){
			System.out.print(y.get(i).toString()+" ");
		}*/
		//System.out.println();
		System.out.print("p after normalization= ");
		for(int i=0;i<p.size();i++){
			System.out.print(p.get(i).toString()+", ");
		}
	}
	
	public void printCurrentData(Error error,WeightsAndFuncgT weightsAndFuncgT,BoostedClassifier fTofX,int n){
		
		//System.out.println();
		System.out.println("G Error="+error.errGofJ);
		//System.out.println("c("+(n+1)+")+: "+weightsAndFuncgT.ctPlus+"\tc("+(n+1)+")-: "+weightsAndFuncgT.ctMinus);
		System.out.println("CPLUS="+weightsAndFuncgT.ctPlus+", CMINUS="+weightsAndFuncgT.ctMinus);
		printCurrentData();
		System.out.println("Boosted classifier error="+(float)fTofX.classifierErrorOnTrainingData(x, y)/(float)n);
		//System.out.println();
		//System.out.println();
	}
}
