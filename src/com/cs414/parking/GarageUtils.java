package com.cs414.parking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	private static String getFullPathToResourcesFolder() {
		final File resourceDir = new File(GarageUtils.class.getResource("/").getFile()).getParentFile();
		return resourceDir.getAbsolutePath() + File.separator  + "resources" + File.separator ;
	}
}
