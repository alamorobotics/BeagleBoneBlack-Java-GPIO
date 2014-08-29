package com.alamorobotics.gpio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class leftOver {

	
	public static void readP8() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("P8_Header.txt"));
		String line = br.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line);
			String myString = "theInfo.put(\"";
			int tokencount = 0;
			boolean done = false;
			String prevTok = "";
			
			// theInfo.put("P8_03", new pinInfo("P8_03", 6, 0x44E10818, 0x018, "GPIO1_6", 38));
			
			while (!done && st.hasMoreElements()) {
				String theToken = (String) st.nextElement();
				theToken = theToken.trim();
				//System.out.println("theToken: " + theToken);

				if (tokencount == 0) {
					myString = myString + theToken + "\", new pinInfo(\"" + theToken + "\", ";
				}
				if (tokencount == 1) {
					myString = myString + theToken + ", 0x44E10";
				}
				if (tokencount == 2) {
					myString = myString + theToken.substring(2, 5).toUpperCase() + ", 0x" + theToken.substring(6, 9).toUpperCase() + ", \"";
				}
				if (tokencount == 3) {
					prevTok = theToken;
				}
				if (tokencount == 4) {
					myString = myString + theToken + "\", ";
					myString = myString + prevTok + "));";
				}
				
				tokencount++;
				
				if (tokencount == 5) {
					done = true;
				}
			}
			System.out.println(myString);
			line = br.readLine();
		}
		br.close();		
	}

	public static void readP9() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("P9_Header.txt"));
		String line = br.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line);
			String myString = "theInfo.put(\"";
			int tokencount = 0;
			boolean done = false;
			
			// theInfo.put("P9_11", new pinInfo("P9_11", 28, 0x44E10870, 0x070, "UART4_RXD", 30));
			
			while (!done && st.hasMoreElements()) {
				String theToken = (String) st.nextElement();
				theToken = theToken.trim();
				//System.out.println("theToken: " + theToken);

				if (tokencount == 0) {
					myString = myString + theToken + "\", new pinInfo(\"" + theToken + "\", ";
				}
				if (tokencount == 1) {
					myString = myString + theToken + ", 0x44E10";
				}
				if (tokencount == 2) {
					myString = myString + theToken.substring(2, 5).toUpperCase() + ", 0x" + theToken.substring(6, 9).toUpperCase() + ", \"";
				}
				if (tokencount == 3) {
					myString = myString + theToken + "\", ";
				}
				if (tokencount == 4) {
					myString = myString + theToken + "));";
				}
				
				tokencount++;
				
				if (tokencount == 5) {
					done = true;
				}
			}
			System.out.println(myString);
			line = br.readLine();
		}
		br.close();		
	}	
	
}
