package finalprojectPBO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;

public class Score {
	
	//membaca score (waktu bermain) dari teks file
	public String readFile(String filePath)
	{
		File inputFile;
		BufferedReader inputReader;
//		StringBuilder sb = new StringBuilder();
//		ArrayList<String> fileText = new ArrayList<String>();
		String fileText = "";
		
		try {
			inputFile = new File(filePath);
			
			inputReader = new BufferedReader(new FileReader(inputFile));
			fileText = inputReader.readLine();
			String line;
			
			inputReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileText;
	}
	
	//menyimpan score (waktu bermain) ke teks file
	public void writeFile(String filePath, String Score)
	{
		File outputFile;
		BufferedWriter outputWriter;
		
		try {
			outputFile = new File(filePath);
			outputWriter = new BufferedWriter(new FileWriter(outputFile));
			
			//masukkan string Score
			outputWriter.write(Score);
			
//			int size = Score.size();
//			
//			for (int i=0;i<size;i++)
//			{
//	            String str = Score.get(i).toString();
//	            
//	            outputWriter.write(str);
//	            if(i < size-1) //This prevent creating a blank like at the end of the file**
//	                outputWriter.write(str);
//	        }
			
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
