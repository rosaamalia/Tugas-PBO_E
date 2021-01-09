package finalprojectPBO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Score {
	String filePath = "";
	String Score = "";
	
	public void MainClass () {
		writeFile("data/HighScore.txt", "Score");
	}
	
	public void readFile(String filePath)
	{
		File inputFile;
		BufferedReader inputReader;
		
		try {
			inputFile = new File(filePath);
			
			inputReader = new BufferedReader(new FileReader(inputFile));
			String fileText = inputReader.readLine();
			System.out.println(fileText);
			inputReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile(String filePath, String Score)
	{
		File outputFile;
		BufferedWriter outputWriter;
		
		try {
			outputFile = new File(filePath);
			
			outputWriter = new BufferedWriter(new FileWriter(outputFile));
			
			outputWriter.write(Score);
			
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
