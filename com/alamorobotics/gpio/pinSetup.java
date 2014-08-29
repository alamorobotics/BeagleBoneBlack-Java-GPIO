/**
 * 
 * Feel free to do whatever you want with it... ;-)
 * 
 * fredrik@Alamorobotics.com
 * 
 */

package com.alamorobotics.gpio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Set up pins and generate the DTS file so you can make your DT overaly files.
 * 
 * @author fredrik
 *
 */
public class pinSetup {

	// Setup stuff
	public static final String DTS_START = "BB-AR-GPIO_start.txt";
	public static final String DTS_END = "BB-AR-GPIO_end.txt";
	public static final String RESOURCE_PATH = "com/alamorobotics/gpio/";
	public static final String DTS_SLOT_NAME = "BB-AR-GPIO";

	/**
	 * Make a DTS file...
	 * 
	 * @param fileName
	 * @param pins
	 * @throws Exception 
	 */
	public static void makeDTSfile() throws Exception {

		String fileName = DTS_SLOT_NAME + ".dts";

		// The resources, the start and end templates.
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL dtsStart = classloader.getResource(RESOURCE_PATH + DTS_START);
		URL dtsEnd = classloader.getResource(RESOURCE_PATH + DTS_END);

		try {
			
			// Read start template
			FileWriter fw = new FileWriter(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(dtsStart.openStream()));
			String line = br.readLine();
			while (line != null) {
				fw.write(line + System.getProperty("line.separator"));
				fw.flush();
				line = br.readLine();
			}
			br.close();

			Collection<pinInfo> myPins = pinInfo.getReservedPins().values();
			
			// Append my pin information.
			for (pinInfo myPin : myPins) {
				fw.write(myPin.getPinDTSInformation()
						+ System.getProperty("line.separator"));
				fw.flush();
			}

			// Append end template.
			br = new BufferedReader(new InputStreamReader(dtsEnd.openStream()));
			line = br.readLine();
			while (line != null) {
				fw.write(line + System.getProperty("line.separator"));
				fw.flush();
				line = br.readLine();
			}
			br.close();
			fw.close();

		} catch (Exception e) {
			throw new Exception("Unable to create DTS file.");
		}
	}

	/**
	 * Export pins and set correct in/out...
	 * 
	 * @param myPins
	 * @throws Exception 
	 */
	public static void exportPins() throws Exception {
		// Get pins to export.
		Collection<pinInfo> myPins = pinInfo.getReservedPins().values();
		// Loop through pins and export and set direction.
		for (pinInfo myPin : myPins) {
			exportPin(myPin);
			if (pinInfo.INPUT.equals(myPin.direction)) {
				pinControl.setPinToInput(myPin);
			} else {
				pinControl.setPinToOutput(myPin);
			}
		}

	}

	/**
	 * Unexport pins...
	 * 
	 * @param myPins
	 * @throws Exception 
	 */
	public static void unExportPins() throws Exception {

		// Get pins to export.
		Collection<pinInfo> myPins = pinInfo.getReservedPins().values();
		// Loop through pins.
		for (pinInfo myPin : myPins) {
			unexportPin(myPin);
		}
	}

	/**
	 * Export one pin
	 * 	
	 * @param myPin
	 * @throws Exception 
	 */
	private static void exportPin(pinInfo myPin) throws Exception {
		
		// Try export pin.
		if (!echoValue(pinInfo.GPIOPIN_PATH + "/export", "" + myPin.gpioPinNum)) {
			throw new Exception("Unable to export pin " + myPin.gpioPinNum);
		}		
	}

	/**
	 * Unexport one pin
	 * 
	 * @param myPin
	 * @throws Exception 
	 */
	private static void unexportPin(pinInfo myPin) throws Exception {

		// Try unexport pin.
		if (!echoValue(pinInfo.GPIOPIN_PATH + "/unexport", "" + myPin.gpioPinNum)) {
			throw new Exception("Unable to unexport pin " + myPin.gpioPinNum);
		}
	}
	
	/**
	 * Get a list of slots...
	 * @return
	 * @throws Exception
	 */
    public static ArrayList<String> getSlots() throws Exception {
    	
    	ArrayList<String> mySlots = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader ( new FileReader(pinInfo.SLOTS_PATH));
            String line = br.readLine();
            while (line != null)  {
            	mySlots.add(line);
            	line = br.readLine();
            }
            br.close();
        }
        catch(IOException e){
        	throw new Exception("Unable to list Slots. ");
        }    	
        
        return mySlots;
    }		
	
    /**
     * Check if Slot is applied.
     * 
     * @return
     * @throws Exception
     */
    public static boolean isDTSApplied() throws Exception {
    	
    	ArrayList<String> slots = getSlots();
    	
    	for (String theSlot : slots ) {
    		if (theSlot.indexOf(DTS_SLOT_NAME) > 0) {
    			return true;
    		}
    	}
    	return false;
    }
    

    /**
     * Returns number for slot.
     * 
     * @return
     * @throws Exception
     */
    public static String getDTSSlotNumber() throws Exception {
    	
    	ArrayList<String> slots = getSlots();
    	
    	for (String theSlot : slots ) {
    		if (theSlot.indexOf(DTS_SLOT_NAME) > 0) {
    			return theSlot.substring(0, theSlot.indexOf(":")).trim();
    		}
    	}
    	return "None";
    }
    
    /**
     * 
     * @throws Exception
     */
    public static void removeDTS() throws Exception {
		// Try removing slot.
		if (!echoValue(pinInfo.SLOTS_PATH, "-" + getDTSSlotNumber())) {
			throw new Exception("Unable to remove slot " + DTS_SLOT_NAME);
		}    	
    }

    /**
     * 
     * @throws Exception
     */
    public static void AddDTS() throws Exception {
		// Try removing slot.
		if (!echoValue(pinInfo.SLOTS_PATH, DTS_SLOT_NAME)) {
			throw new Exception("Unable to add slot " + DTS_SLOT_NAME);
		}    	
    	
    }
    
    /**
     * Try compiling the DTs file.
     * 
     * @throws Exception
     */
    public static void compileDTS() throws Exception {
       	String compileDTSCommand = "dtc -O dtb -o " + DTS_SLOT_NAME + "-00A0.dtbo -b 0 -@ " + DTS_SLOT_NAME + ".dts";
       	
       	if (!executeCommand(compileDTSCommand)) {
       		throw new Exception("Unable to compile the DTS file.");
       	}
       	
    }
    
    /**
     * Run a command...
     * 
     * @param command
     * @return
     */
    public static boolean executeCommand(String command) {
    	
    	// Try running the command.
        try {
        	Process proc = Runtime.getRuntime().exec(command);
        	proc.waitFor();
        }
        catch(Exception e) {
        	return false;
        }
    	return true;
    }
    
    
    /**
     * Echo a value, return true if successful...
     * 
     * @param path
     * @param value
     * @return
     */
    public static boolean echoValue(String path, String value) {
    	
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(value);
		} catch (Exception e) {
			return false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// Nothing we can do at this point...
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// Nothing we can do at this point...
				}
			}
		}
    	
    	return true;
    }
    
    /**
     * Try to get a value from a path/file...
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static String catPath(String path) throws Exception {
    	
    	String value = "Unable to get Value.";
    	BufferedReader br = null;
    	FileReader fr = null;
    	try {
    		fr = new FileReader(path);
    		br = new BufferedReader(fr);
    	    if (br.ready())  {
    	    	value = br.readLine();
    	    }
		} catch (Exception e) {
			throw new Exception("Unable to Cat Path");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// Nothing we can do at this point...
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// Nothing we can do at this point...
				}
			}
		}
    	return value;
    }
    
}
