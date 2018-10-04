import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] argv){
		
		int numRows = 0, numCols = 0, minVal = 0, maxVal = 0;
		
		try {	
			Scanner inFile = new Scanner(new File(argv[0]));	//read in data
			PrintWriter outFile1 = new PrintWriter(new FileWriter(argv[1]));	//Euclidian
			PrintWriter outFile2 = new PrintWriter(new FileWriter(argv[2]));	//Skeleton
			PrintWriter outFile3 = new PrintWriter(new FileWriter(argv[3]));	//Pretty Print
			
			numRows = Integer.parseInt(inFile.next());
			numCols = Integer.parseInt(inFile.next());
			minVal = Integer.parseInt(inFile.next());
			maxVal = Integer.parseInt(inFile.next());
			
			imageProcessing processor = new imageProcessing(numRows, numCols, inFile);
			processor.loadImage();
			processor.firstPass_EuclidianDistance();
			processor.prettyPrintDistance(outFile3,1);
			processor.secondPass_EuclidianDistance();
			processor.printImage(outFile1,1);
			processor.prettyPrintDistance(outFile3,2);
			processor.compute_localMaxima();
			processor.printImage(outFile2, 2);
			processor.prettyPrintSkeleton(outFile3);
			inFile.close();
			outFile1.close();
			outFile2.close();
			outFile3.close();
		}
		
		catch(FileNotFoundException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
