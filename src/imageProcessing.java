import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class imageProcessing {

	int numRows, numCols, minVal, maxVal;
	double newMinVal, newMaxVal;
	double[][] zeroFramedAry; 
	int[][] skeletonAry;
	double[] neighborAry;
	DecimalFormat df;
	Scanner inFile;
	PrintWriter outFile1, outFile2, outFile3;
	
	//Constructor
	imageProcessing(int numRows, int numCols, Scanner inFile){
		this.numRows = numRows;
		this.numCols = numCols;
		zeroFramedAry = new double [numRows+2][numCols+2];
		skeletonAry = new int [numRows+2][numCols+2];
		this.inFile = inFile;
		neighborAry = new double [9];
		df = new DecimalFormat("#.##");
	}
	
	//Methods
	void loadImage() {
		for(int rows=0;rows<numRows;rows++) {
			for(int cols=0;cols<numCols;cols++) {
				double num = Integer.parseInt(inFile.next());
				zeroFramedAry[rows+1][cols+1] = num;
				
			}
		}
	}
	
	int is_Maxima(int rows, int cols){
		loadMaximaNeighbors(rows, cols);
		if(zeroFramedAry[rows][cols]>=neighborAry[1])
			if(zeroFramedAry[rows][cols]>=neighborAry[2])
				if(zeroFramedAry[rows][cols]>=neighborAry[3])
					if(zeroFramedAry[rows][cols]>=neighborAry[4])
						if(zeroFramedAry[rows][cols]>=neighborAry[5])
							if(zeroFramedAry[rows][cols]>=neighborAry[6])
								if(zeroFramedAry[rows][cols]>=neighborAry[7])
									if(zeroFramedAry[rows][cols]>=neighborAry[8])
										return 1;
									else return 0;
								else return 0;
							else return 0;
						else return 0;
					else return 0;
				else return 0;
			else return 0;
		else return 0;
	}
	
	void prettyPrintDistance(PrintWriter outFile3,int pass) {
		outFile3.println("Pass-" + pass + " Result:");
		for(int rows=0;rows<numRows+2;rows++) {
			for(int cols=0;cols<numCols+2;cols++) {
				if(zeroFramedAry[rows][cols]>0)
					outFile3.print(zeroFramedAry[rows][cols]+" ");
				else outFile3.print(" ");
			}
			outFile3.println();
		}
	}
	
	void prettyPrintSkeleton(PrintWriter outFile3) {
		outFile3.println("Skeleton Array:");
		for(int rows=0;rows<numRows+2;rows++){
			for(int cols=0;cols<numCols+2;cols++){
					
				if(skeletonAry[rows][cols]==0)
					outFile3.print(".");
				else outFile3.print("9");
				
			}
			outFile3.println();
		}
		outFile3.println();			
	}
	
	void compute_localMaxima(){
		for(int rows=0;rows<numRows+2;rows++) {
			for(int cols=0;cols<numCols+2;cols++) {
				if(zeroFramedAry[rows][cols]>0){
					if(is_Maxima(rows,cols)==1)
						skeletonAry[rows][cols] = 1;
					else 
						skeletonAry[rows][cols] = 0;
				}
			}
		}
	}
	
	void printImage(PrintWriter file, int fileNum) {
		if(fileNum==1) {
			file.println("NewMinVal = " + newMinVal);
			file.println("NewMaxVal = " + newMaxVal);
			for(int rows=0;rows<numRows;rows++){
				for(int cols=0;cols<numCols;cols++){
					file.print(zeroFramedAry[rows+1][cols+1]+" ");
				}
				file.println();
			}
			file.println();
		}
		else if(fileNum==2){
			file.println("NewMinVal = " + newMinVal);
			file.println("NewMaxVal = " + newMaxVal);
			for(int rows=0;rows<numRows;rows++){
				for(int cols=0;cols<numCols;cols++){
					file.print(skeletonAry[rows+1][cols+1]+" ");
				}
				file.println();
			}
			file.println();
		}
	}
	
	void loadMaximaNeighbors(int rows, int cols){
		neighborAry[0] = zeroFramedAry[rows][cols];
		neighborAry[1] = zeroFramedAry[rows-1][cols-1];
		neighborAry[2] = zeroFramedAry[rows-1][cols];
		neighborAry[3] = zeroFramedAry[rows-1][cols+1];
		neighborAry[4] = zeroFramedAry[rows][cols-1];
		neighborAry[5] = zeroFramedAry[rows][cols+1];
		neighborAry[6] = zeroFramedAry[rows+1][cols-1];
		neighborAry[7] = zeroFramedAry[rows+1][cols];
		neighborAry[8] = zeroFramedAry[rows+1][cols+1];
	}
	
	void loadNeighbors(int rows, int cols) {
		neighborAry[0] = zeroFramedAry[rows][cols];
		neighborAry[1] = zeroFramedAry[rows-1][cols-1]+Math.sqrt(2);
		neighborAry[2] = zeroFramedAry[rows-1][cols]+1;
		neighborAry[3] = zeroFramedAry[rows-1][cols+1]+Math.sqrt(2);
		neighborAry[4] = zeroFramedAry[rows][cols-1]+1;
		neighborAry[5] = zeroFramedAry[rows][cols+1]+1;
		neighborAry[6] = zeroFramedAry[rows+1][cols-1]+Math.sqrt(2);
		neighborAry[7] = zeroFramedAry[rows+1][cols]+1;
		neighborAry[8] = zeroFramedAry[rows+1][cols+1]+Math.sqrt(2);
	}
	
	double min(double[] neighborAry, int pass) {
		double min = 0;
		
		if(pass==1) {
			min = neighborAry[1];
			for(int i=2;i<5;i++) {
				if(neighborAry[i]<min)
					min = neighborAry[i];
			}
		}
		
		else if(pass==2) {
			min = neighborAry[0];
			for(int i=5;i<9;i++) {
				if(neighborAry[i]<min)
					min = neighborAry[i];
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format(min);
		min = Double.parseDouble(formatted);
		return min;
	}
	
	void firstPass_EuclidianDistance() {
		for(int rows=0;rows<numRows;rows++) {
			for(int cols=0;cols<numCols;cols++) {
				if(zeroFramedAry[rows+1][cols+1]>0) {
					loadNeighbors(rows+1, cols+1);
					zeroFramedAry[rows+1][cols+1] = min(neighborAry,1);
				}
			}
		}
	}
	
	void secondPass_EuclidianDistance() {
		for(int rows=numRows;rows>0;rows--) {
			for(int cols=numCols;cols>0;cols--) {
				if(zeroFramedAry[rows+1][cols+1]>0) {
					loadNeighbors(rows+1, cols+1);
					zeroFramedAry[rows+1][cols+1] = min(neighborAry,2);
					if(zeroFramedAry[rows+1][cols+1]<newMinVal)
						newMinVal = zeroFramedAry[rows+1][cols+1];
					if(zeroFramedAry[rows+1][cols+1]>newMaxVal)
						newMaxVal = zeroFramedAry[rows+1][cols+1];
				}
			}
		}
	}
}
