package cs414.a5.sanjusam.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class GarageUtils {
	
	public static String readOneLineInResourceFolder(String fileName) {
		String fullPathToFile = getFullPathToResourcesFolder() + fileName;
		String textRead = "" ;
		try {
			FileInputStream fstream = new FileInputStream(fullPathToFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			textRead = br.readLine();
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return textRead;
	}
	
	public static void updateSingleValueInResourceFolder(String fileName, int value) {
		String fullPathToFile = getFullPathToResourcesFolder() + fileName;
		try{
			final PrintWriter writer = new PrintWriter(fullPathToFile);
			writer.write(Integer.toString(value));
			writer.close();
		} catch(final FileNotFoundException fne) {
			fne.printStackTrace();
		} 
	}
	
	public static void appendToFileInResourceFolder(String fileName, String stringToAppend) {
		String fullPathToFile = getFullPathToResourcesFolder() + fileName;
		try{
			final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fullPathToFile, true)));
			writer.write(stringToAppend);
			writer.close();
		} catch(final FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void writeToFileInResourceFolder(String fileName, String stringToWrite) {
		String fullPathToFile = getFullPathToResourcesFolder() + fileName;
		writeFile(fullPathToFile, stringToWrite);
	}
	
	public static void writeFile(String fileName, String stringToWrite) {
		try{
			final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			writer.write(stringToWrite);
			writer.close();
		} catch(final FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static int getLineCountInFile(final String fileName, final boolean fileInResource) {
		int cntInFile = 0;
		try {
			File file;
			if(fileInResource) {
				file =new File(getFullPathToResourcesFolder() + File.separator + fileName);
			} else {
				file =new File(fileName);
			}
			if(file.exists()) {
				final FileReader fileReader = new FileReader(file);
				final LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
				while (lineNumberReader.readLine() != null){
					cntInFile++;
				}
				lineNumberReader.close();
			}
		} catch(final Exception e) {
			
		}
		return cntInFile ;
	}
	
	public static String getFullPathToResourcesFolder() {
		final File resourceDir = new File(GarageUtils.class.getResource("/").getFile()).getParentFile();
		return resourceDir.getAbsolutePath() + File.separator  + "resources" + File.separator ;
	}
}
