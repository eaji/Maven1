package com.exist.ActTwo;

import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.util.TreeMap;

public class Operations {

	Scanner inputOp = new Scanner(System.in);
	String filePath;


	public List read(String filePath) throws IOException {
		this.filePath = filePath;
		List<Map<String, String>> arrayList = new ArrayList<>();
		Map<String,String> row = new LinkedHashMap<>();
		LineNumberReader lineReader = new LineNumberReader(new FileReader(filePath));
		String[] splitRow = new String [0];
		String[] splitColumn = new String [0];
		String lineText = null;

		for (lineText = lineReader.readLine(); lineText != null;lineText = lineReader.readLine()) {
			row = new LinkedHashMap<>();
			splitRow = lineText.split("\\s+");
			for (int i = 0;i < splitRow.length;i++) {
				splitColumn = splitRow[i].split("=");
				row.put(splitColumn[0], splitColumn[1]);
			}
			arrayList.add(row);
		}
		return arrayList;		
	}


	public List print(List<Map<String, String>> arrayList) {
		int count = 0; 		
		for(Map map: arrayList){
			System.out.println(map);
		}
		return arrayList;
	}


	public List edit(List<Map<String, String>> arrayList) {
		int rowNumber = 0;
		int columnNumber = 0;
		int counter = 1;
		String newKey = "";
		String newValue = "";

		do {
			System.out.print("Input Row to Edit: ");
			while (!inputOp.hasNextInt()) {
				inputOp.next();
			}
			rowNumber = inputOp.nextInt();
		} while (rowNumber > (arrayList.size() - 1) || rowNumber < 0);


		do {
			System.out.print("Input Column to Edit: ");
			while (!inputOp.hasNextInt()) {
				inputOp.next();
			}
			columnNumber = inputOp.nextInt();
		} while (columnNumber > (arrayList.get(0).size() - 1) || columnNumber < 0);

		inputOp.nextLine();

		do {
			System.out.print("Enter the new key: ");
			newKey = inputOp.nextLine();
	        for (int i = 0; i < (arrayList.size()); i++) {
	            if (arrayList.get(i).containsKey(newKey)) {
	                System.out.println("Key already existing!");
	                System.out.println("Input new key: ");
	                newKey = inputOp.nextLine();
	                i = -1;
	            }
	        }
		} while (newKey.contains("=") || newKey.contains(" ") || newKey.isEmpty());

		do {
			System.out.print("Enter the new value: ");
			newValue = inputOp.nextLine(); 
		} while (newValue.contains("=") || newValue.contains(" ") || newValue.isEmpty());

		String cell = null;
		int index = 0;
		for (Map.Entry<String, String> entry : arrayList.get(rowNumber).entrySet()) {
		    if (index == columnNumber){
				cell = entry.getKey();
		    	break;
		    }
		    index++;
		}
		arrayList.get(rowNumber).remove(cell);
		arrayList.get(rowNumber).put(newKey,newValue);

		try {
			FileWriter writer = new FileWriter(filePath, false);
			for (Map<String,String> map : arrayList) {
				for (Map.Entry<String,String> entry : map.entrySet()) {
					writer.write(entry.getKey() + "=" + entry.getValue() + "\t");
					if (counter % map.size() == 0) {
						writer.write("\n");
					}
					counter++;
				}
			}
			writer.close();
		}catch (IOException ex) {
			System.err.println(ex);
		}
		return arrayList;		
	}


	public void search(List<Map<String, String>> arrayList) {
		int row2 = 0;
	    int column2 = 0;
	    int keyCountOccurence = 0;
	    int valueCountOccurence = 0;
	    int lastIndex = 0;
	    System.out.print("Enter search : ");
	    String search = inputOp.next();
	   
	    for (Map<String,String> map : arrayList) {
	        for (Map.Entry<String,String> entry : map.entrySet()) {
	            lastIndex = 0;
	            while (lastIndex != -1) {
	                lastIndex = entry.getKey().indexOf(search,lastIndex);
	                if (lastIndex != -1) {
	                    keyCountOccurence ++;
	                    if(search.length() == 1) {
	                		lastIndex += search.length();
	                	}
	                	else {
	                		lastIndex += search.length() - 1;
	                	}
	                }
	            }

				if (keyCountOccurence > 0) {
	            	System.out.println("Key @ (" + row2 + ", " + column2 + ") --> " + keyCountOccurence + " occurence(s)");
				}

				keyCountOccurence = 0;
	            lastIndex = 0;
	            while (lastIndex != -1) {
	                lastIndex = entry.getValue().indexOf(search,lastIndex);
	                if (lastIndex != -1) {
	                    valueCountOccurence ++;
	                    if(search.length() == 1) {
	                		lastIndex += search.length();
	                	}

	                	else {
	                		lastIndex += search.length() - 1;
	                	}	
	                }
	            }

	      	    if (valueCountOccurence > 0) {
	      			System.out.println("Value @ (" + row2 + ", " + column2 + ") --> " + valueCountOccurence + " occurence(s)");
	            }
				column2++; 
	       		valueCountOccurence = 0;
	        }
			column2 = 0;
	        row2++;
	    }
	}


	public List addRow(List<Map<String, String>> arrayList, Map<String,String> row, String[] splitRow) {
		String newAddKey = "";
		String newAddVal = "";
		int ctr = 0;
		System.out.println("ADDING ROW: ");
		row = new LinkedHashMap<>();
		for (int i=0;i<arrayList.get(0).size();i++) {
				System.out.print("@column" + i + "-- Enter 'key-value' pair: ");
				newAddKey = inputOp.next();
				newAddVal = inputOp.next();
				row.put(newAddKey, newAddVal);
		}
		arrayList.add(row);
		System.out.println("Successfully Added!");

		try {
			FileWriter writer = new FileWriter(filePath, false);
			for (Map<String,String> map : arrayList) {
				for (Map.Entry<String,String> entry : map.entrySet()) {
					writer.write(entry.getKey() + "=" + entry.getValue() + "\t");
					ctr++;
					if (ctr % map.size() == 0) {
						writer.write("\n");
					}
				}
			}
			writer.close();
		}catch (IOException ex) {
			System.err.println(ex);
		}
		return arrayList;
	}


	public List sort(List<Map<String, String>> arrayList) {
		List<Map<String,String>> sortedList = new ArrayList<>();
		Map<String, String> treemap = new TreeMap<>();
		int ctr = 0;
		for (Map<String,String> map : arrayList) {
        	for (Map.Entry<String,String> entry : map.entrySet()) {
    			treemap.put(entry.getKey(), entry.getValue());
			}
        }
        sortedList.add(treemap);

	    try {
	    	FileWriter writer = new FileWriter(filePath, false);
	        for (Map<String,String> sortedMap : sortedList) {
	        	for (Map.Entry<String,String> entry : sortedMap.entrySet()) {
					writer.write(entry.getKey() + "=" + entry.getValue() + "\t");
					ctr++;
					if (ctr % 2 == 0) {
						writer.write("\n");
					}
	        	}
	        }

	        writer.close();
	        System.out.println("Successfully Sorted!");
	    } catch (IOException ex) {
			System.err.println(ex);
		}
		return arrayList;
	}


	public List reset(List<Map<String, String>> arrayList, Map<String,String> row) {
		int ctr = 0;
		arrayList.clear();
		try {
			FileWriter writer = new FileWriter(filePath, false);

			System.out.print("Enter number of rows: ");
			while (!inputOp.hasNextInt()) {
				Operations.errMsg();
				inputOp.next();
			}
			int tbRow = inputOp.nextInt();

			System.out.print("Enter number of columns: ");
			while (!inputOp.hasNextInt()) {
				Operations.errMsg();
				inputOp.next();
			}
			int tbColumn = inputOp.nextInt();

			for (int i = 0; i < tbColumn; i++) {
				for (int j = 0; j < tbRow; j++) {
					System.out.print("Enter a key: ");
					String newAddKey = inputOp.next();

					System.out.print("Enter a value: ");
					String newAddVal = inputOp.next();

					row.put(newAddKey, newAddVal);
				    writer.write(newAddKey + "=" + newAddVal + "\t");
					ctr++;
					if (ctr % tbColumn == 0) {
						writer.write("\n");
					}
				}
			}
		    writer.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
		return arrayList;
	}


	public static void errMsg() {
		System.out.println("Invalid input!");
		System.out.println("Enter again: ");
	}


}	
