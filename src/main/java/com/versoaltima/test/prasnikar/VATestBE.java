package com.versoaltima.test.prasnikar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class for the test application.
 * @author Neven Prašnikar (Dalo Lorn)
 * @version 28.03.2019
 */
public class VATestBE {
	private VATestBE(String[] args) {
		File file = null;
		if(args.length > 1) {
			file = new File(args[0]);
		}
		if(file == null || !file.exists())
			file = new File("test.txt");

		if(!file.exists()) {
			System.err.printf("ERROR: Could not find file %s! Exiting...%n", file.getPath());
			return;
		}

		try (Scanner in = new Scanner(file)){
			ArrayList<String> vals = new ArrayList<>();
			while(in.hasNextLine()) {
				vals.add(in.nextLine());
			}

			System.out.println(new FamilyTree(vals).toString());
		} catch(FileNotFoundException e) {
			// This should never occur anyway...
			System.err.println("ERROR: Unexpected FileNotFoundException occurred while instantiating Scanner! Exiting...");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new VATestBE(args);
	}
}