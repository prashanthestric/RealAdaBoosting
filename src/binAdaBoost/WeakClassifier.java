package binAdaBoost;

public class WeakClassifier {
	
	public double threshold;
	public int aboveThresholdIs;	//+1 or -1
	public Error classifierError;
	int id_1;
	
	public WeakClassifier(double thresh,int abvthreshis,Error classifierErr){
		threshold=thresh;
		aboveThresholdIs=abvthreshis;
		classifierError=classifierErr;
		id_1=20;
	}
	
	public int classifyData(double data){
		if(data > threshold){
			return aboveThresholdIs;
		}
		else{
			return -1*aboveThresholdIs;
		}
	}
}
