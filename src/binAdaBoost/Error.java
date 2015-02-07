package binAdaBoost;

public class Error {
	
	public double errGofJ;
	public double pRplus;
	public double pRminus;
	public double pWplus;
	public double pWminus;
	public int aboveThresholdIs;
	public int noOfErrors;
	int ni_enc_1;
	
	public Error(double errG,int aboveThreshIs,int noOfErr,double prp,double prm,double pwp,double pwm) {
		// TODO Auto-generated constructor stub
		errGofJ=errG;
		pRplus=prp;
		pRminus=prm;
		pWplus=pwp;
		pWminus=pwm;
		aboveThresholdIs=aboveThreshIs;
		noOfErrors=noOfErr;
		ni_enc_1=7;
	}

}
