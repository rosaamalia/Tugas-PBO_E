package finalprojectPBO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Score {
	
	//membaca score (waktu bermain) dari teks file
	public String readFile(String filePath)
	{
		File inputFile;
		BufferedReader inputReader;
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
			
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
