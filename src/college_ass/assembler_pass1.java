package college_ass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class assembler_pass1 {

	public static void main(String[] args) {
		try {
			String IPfile = "C:\\Users\\gcp\\eclipse-workspace\\college_ass\\Input\\Input.txt";
			FileReader file_Reader = new FileReader(IPfile);
			BufferedReader buff_Reader = new BufferedReader(file_Reader);

			String OPfile = "C:\\Users\\gcp\\eclipse-workspace\\college_ass\\Output\\Inter_code.txt";
			FileWriter file_Writer = new FileWriter(OPfile);
			BufferedWriter buff_Writer = new BufferedWriter(file_Writer);

			// HashTable to store the opcode table key:value pairs
			// Imperative statements
			Hashtable<String, String> ISTable = new Hashtable<String, String>();
			ISTable.put("STOP", "00");
			ISTable.put("ADD", "01");
			ISTable.put("SUB", "02");
			ISTable.put("MULT", "03");
			ISTable.put("MOVER", "04");
			ISTable.put("MOVEM", "05");

			// Declarative statements
			Hashtable<String, String> DLTable = new Hashtable<String, String>();
			DLTable.put("DC", "01");
			DLTable.put("DS", "02");

			// Assembler Directive statements
			Hashtable<String, String> ADTable = new Hashtable<String, String>();
			ADTable.put("START", "01");
			ADTable.put("LTORG", "02");
			ADTable.put("ORIGIN", "03");

			// SYMTAB -- key = symbols, values = addresses
			// LITTAB -- key = literals, values = addresses
			// POOLTAB -- keeps track of no. of pools of literals
			Hashtable<String, String> SYMTAB = new Hashtable<String, String>();
			Hashtable<String, String> LITTAB = new Hashtable<String, String>();
			ArrayList<Integer> POOLTAB = new ArrayList<Integer>();

			String currentLine;
			int locptr = 0;
			int litptr = 1;
			int symptr = 1;
			int pooltabptr = 0;

			currentLine = buff_Reader.readLine();

			String firstWord = currentLine.split(" ")[1];

			if (firstWord.equals("START")) {
				buff_Writer.write("AD\t01\t");
				String secondWord = currentLine.split(" ")[2];
				buff_Writer.write("(C\t" + secondWord + ")\n");
				locptr = Integer.parseInt(secondWord);
			}

			while ((currentLine = buff_Reader.readLine()) != null) {
				int mind_LC = 0;
				String type = null;
				int flag = 0; // to keep track of addressing

				String myString = currentLine.split(" |\\,")[0];// Splitting String on whitespace or a comma

				// Loop to give addresses to symbols
				for (Map.Entry<String,String> mEntry : SYMTAB.entrySet()) {
					if (myString.equals(mEntry.getKey())) {
						mEntry.setValue(String.valueOf(locptr));
						flag = 1;
					}
				}
				if (myString.length() != 0 && flag == 0) {
					SYMTAB.put(myString, String.valueOf(locptr));
					symptr++;
				}

				int isOpcode = 0; // to see if word is opcode or not

				myString = currentLine.split(" |\\,")[1];// checking 2nd word

				// check if the 2nd word exists in any instruction table
				for (Map.Entry<String, String> entry : ISTable.entrySet()) {
					if (myString.equals(entry.getKey())) {
						buff_Writer.write("IS\t" + entry.getValue() + "\t");
						type = "IS";
						isOpcode = 1;
					}
				}
				for (Map.Entry<String, String> entry : DLTable.entrySet()) {
					if (myString.equals(entry.getKey())) {
						buff_Writer.write("DL\t" + entry.getValue() + "\t");
						type = "DL";
						isOpcode = 1;
					}
				}
				for (Map.Entry<String, String> entry : ADTable.entrySet()) {
					if (myString.equals(entry.getKey())) {
						buff_Writer.write("AD\t" + entry.getValue() + "\t");
						type = "AD";
						isOpcode = 1;
					}
				}
				if (myString.equals("LTORG")) {
					POOLTAB.add(pooltabptr);
					for (Map.Entry<String, String> myentry : LITTAB.entrySet()) {
						if (myentry.getValue() == "") {
							myentry.setValue(String.valueOf(locptr));
							locptr++;
							pooltabptr++;
							mind_LC = 1;
							isOpcode = 1;

						}
					}
				}
				if (myString.equals("END")) {
					POOLTAB.add(pooltabptr);
					for (Map.Entry<String, String> myentry : LITTAB.entrySet()) {
						if (myentry.getValue() == "") {
							myentry.setValue(String.valueOf(locptr));
							locptr++;
							mind_LC = 1;
						}
					}
				}

				if (currentLine.split(" |\\,").length > 2) {
					myString = currentLine.split(" |\\,")[2];

					if (myString.equals("AREG")) {
						buff_Writer.write("(RG 01)\t");
						isOpcode = 1;
					} else if (myString.equals("BREG")) {
						buff_Writer.write("(RG 02)\t");
						isOpcode = 1;
					} else if (myString.equals("CREG")) {
						buff_Writer.write("(RG 03)\t");
						isOpcode = 1;
					} else if (myString.equals("DREG")) {
						buff_Writer.write("(RG 04)\t");
						isOpcode = 1;
					} else {
						SYMTAB.put(myString, ""); // forward referenced symbol
					}

				}
				if (currentLine.split(" |\\,").length > 3) { // 4 words expression
					myString = currentLine.split(" |\\,")[3];
					if (myString.contains("=")) {// this is a literal so put in LITTAB
						LITTAB.put(myString, "");
						buff_Writer.write("(L\t" + litptr + ")\t");
						isOpcode = 1;
						litptr++;
					} else {// else it's a symbol so add to SYMTAB
						SYMTAB.put(myString, "");
						buff_Writer.write("(S\t" + symptr + ")\t");
						symptr++;
					}
				}
				buff_Writer.newLine();// done with a line
				if (mind_LC == 0) {
					locptr++;
				}
			}
			String f1 = "C:\\Users\\gcp\\eclipse-workspace\\college_ass\\Output\\SYMTAB.txt";
			FileWriter fw1 = new FileWriter(f1);
			BufferedWriter bw1 = new BufferedWriter(fw1);
			bw1.write("Symbol\tAddress\n");
			for (Map.Entry<String, String> entry : SYMTAB.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				bw1.write(key + "\t" + val + "\n");
//				System.out.println("write successful!");
			}

			bw1.write("\nLiteral\tAddress\n");
			for (Map.Entry<String, String> entry : LITTAB.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				bw1.write(key + "\t" + val + "\n");
			}
			bw1.write("\nPool No.\n");
			for (Integer myInteger : POOLTAB) {
				bw1.write(myInteger + "\n");
			}

			buff_Writer.close();
			bw1.close();
			buff_Reader.close();
			fw1.close();
			file_Reader.close();
			file_Writer.close();

		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

}
