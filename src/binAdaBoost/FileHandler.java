package binAdaBoost;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Prashanth Govindaraj
 */
public class FileHandler {

	private String filePath;
	int T,n;
	Double epsilon;
	ArrayList<Double> x;
	ArrayList<Integer> y;
	ArrayList<Double> p;
	int id_2;
	
	public FileHandler(String path) {
		// TODO Auto-generated constructor stub
		filePath=path;
		
		x=new ArrayList<Double>();
		y=new ArrayList<Integer>();
		p=new ArrayList<Double>();
		
		try {
			readData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			id_2=30;
		}
	}
	
	void readData() throws IOException{
		FileReader fileReader=new FileReader(filePath);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		String firstLine=bufferedReader.readLine();
		//System.out.println("readline1: "+firstLine);
		String[] tnepsilon=firstLine.split(" ");
		T=Integer.parseInt(tnepsilon[0]);
		n=Integer.parseInt(tnepsilon[1]);
		epsilon=Double.parseDouble(tnepsilon[2]);
		
		String xLine=bufferedReader.readLine();
		//System.out.println("readlinex: "+xLine);
		String[] xLineItems=xLine.split(" ");
		for(int i=0;i<xLineItems.length;i++){
			x.add(Double.parseDouble(xLineItems[i]));
		}
		
		String yLine=bufferedReader.readLine();
		//System.out.println("readliney: "+yLine);
		String[] yLineItems=yLine.split(" ");
		for(int i=0;i<yLineItems.length;i++){
			y.add(Integer.parseInt(yLineItems[i]));
		}
		
		String pLine=bufferedReader.readLine();
		//System.out.println("readlinep: "+pLine);
		String[] pLineItems=pLine.split(" ");
		for(int i=0;i<pLineItems.length;i++){
			p.add(Double.parseDouble(pLineItems[i]));
		}
	}
	
	public int getT() {
		return T;
	}
	
	public int getN() {
		return n;
	}
	
	public Double getEpsilon() {
		return epsilon;
	}
	
	public ArrayList<Double> getX() {
		return x;
	}
	
	public ArrayList<Integer> getY() {
		return y;
	}
	
	public ArrayList<Double> getP() {
		return p;
	}
		
}
