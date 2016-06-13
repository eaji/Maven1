package com.exist.ActTwo;
import org.apache.commons.lang.StringUtils;

import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class MainClass {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner inputMain = new Scanner(System.in);
		String filePath = "/home/joel/Activities/Maven1/src/main/resources/textfile.txt";
		String[] splitRow = new String [0];
		boolean initialData = true;
		int choice = 0;

	    List<Map<String, String>> arrayList = new ArrayList<>();
		Map<String,String> row = new LinkedHashMap<>();

		Operations operation = new Operations();

		do {
			try {
				arrayList = operation.read(filePath);	
			} catch (IOException ex) {
				System.err.println(ex);
			}

			if(initialData) {
				System.out.println("Textfile Data:");
				arrayList = operation.print(arrayList);
  				initialData = false;
  			}

			System.out.println();
			System.out.println("1. Print");
			System.out.println("2. Edit");
			System.out.println("3. Search");
			System.out.println("4. Add");
			System.out.println("5. Sort");
			System.out.println("6. Reset");
			System.out.println("7. Exit");
			//System.out.println(StringUtils.swapCase("MyTest says hello again!"));
			System.out.print(StringUtils.swapCase("Enter Equivalent Number of Command: "));
			//System.out.print("Enter equivalent number of command: ");

			while (!inputMain.hasNextInt()) {
			Operations.errMsg();
				inputMain.next();
			}
			choice = inputMain.nextInt();

			switch (choice) {
				case 1: {
					arrayList = operation.print(arrayList);
					break;
				}

				case 2: {
					arrayList = operation.edit(arrayList);
					break;
				}

				case 3: {
					operation.search(arrayList); 
					break;
				}

				case 4: {
					arrayList = operation.addRow(arrayList, row, splitRow);
					break;
				}

				case 5: {
					arrayList = operation.sort(arrayList);
					break;
				}

				case 6: {
					arrayList = operation.reset(arrayList, row);
					break;

				}

				case 7: {
					System.out.println("End!");
					break;
				}

				default: {
					System.out.println("Command not found!");
				}
			}
		}
		while(choice != 7);

	}


}
