/**
 * 
 * Feel free to do whatever you want with it... ;-)
 * 
 * fredrik@Alamorobotics.com
 * 
 */

package com.alamorobotics.gpio;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
	public static final String DTS_PWM = "BB-AR-PWM.txt";
	public static final String RESOURCE_PATH = "com/alamorobotics/gpio/";
	public static final String DTS_SLOT_NAME = "BB-AR-GPIO";
	public static final String PWM_SLOT_NAME = "BB-AR-PWM";
	public static final String CAPE_BONE_IIO = "cape-bone-iio";
	public static final String DTS_TARGET_DIR = "/lib/firmware/";


	private static String analogPath = null;	
	
	/**
	 * Make a DTS file...
	 * 
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
	 * Copy the PWM file...
	 * 
	 * @throws Exception 
	 */
	public static void copyPWMDTSfile() throws Exception {

		String fileName = PWM_SLOT_NAME + ".dts";

		// The resource, the pwm templates.
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL dtsPWM= classloader.getResource(RESOURCE_PATH + DTS_PWM);

		try {
			
			// Read start template
			FileWriter fw = new FileWriter(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(dtsPWM.openStream()));
			String line = br.readLine();
			while (line != null) {
				fw.write(line + System.getProperty("line.separator"));
				fw.flush();
				line = br.readLine();
			}
			br.close();
			fw.close();

		} catch (Exception e) {
			throw new Exception("Unable to create PWM DTS file.");
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
	 * Export PWM
	 * 	
	 * @throws Exception 
	 */
	public static void exportPWM() throws Exception {
		
		for (int i=0 ; i<6 ; i++) {
			// Try export pin.
			if (!echoValue(pinInfo.PWM_PATH + "/export", "" + i)) {
				throw new Exception("Unable to export PWM " + i);
			}		
		}
	}	

	/**
	 * Unexport PWM
	 * 	
	 * @throws Exception 
	 */
	public static void unexportPWM() throws Exception {
		
		for (int i=0 ; i<6 ; i++) {
			// Try export pin.
			if (!echoValue(pinInfo.PWM_PATH + "/unexport", "" + i)) {
				throw new Exception("Unable to unexport PWM " + i);
			}		
		}
	}		
	
	/**
	 * Get a list of slots...
	 * @return
	 * @throws Exception
	 */
    public static ArrayList<String> getSlots() throws Exception {
    	
    	ArrayList<String> mySlots = new ArrayList<String>();
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
     * Check if our DTS is applied.
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
     * Check if PWM is applied.
     * 
     * @return
     * @throws Exception
     */
    public static boolean isPWMApplied() throws Exception {
    	
    	ArrayList<String> slots = getSlots();
    	
    	for (String theSlot : slots ) {
    		if (theSlot.indexOf(PWM_SLOT_NAME) > 0) {
    			return true;
    		}
    	}
    	return false;
    }
    
    
    /**
     * Check if the Analog DTS is applied.
     * 
     * @return
     * @throws Exception
     */
    public static boolean isAnalogApplied() throws Exception {
    	
    	ArrayList<String> slots = getSlots();
    	
    	for (String theSlot : slots ) {
    		if (theSlot.indexOf(CAPE_BONE_IIO) > 0) {
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
     * Remove DTS slot
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
     * Add our DTS file.
     * 
     * @throws Exception
     */
    public static void AddDTS() throws Exception {
		// Try adding slot.
		if (!echoValue(pinInfo.SLOTS_PATH, DTS_SLOT_NAME)) {
			throw new Exception("Unable to add slot " + DTS_SLOT_NAME);
		}    	
    	
    }
    
    /**
     * Add the Analog DTS file.
     * 
     * @throws Exception
     */
    public static void AddAnalogDTS() throws Exception {
		// Try adding slot.
		if (!echoValue(pinInfo.SLOTS_PATH, CAPE_BONE_IIO)) {
			throw new Exception("Unable to add slot " + CAPE_BONE_IIO);
		}    	
    }

    
    /**
     * Add the PWM DTS file.
     * 
     * @throws Exception
     */
    public static void AddPWMDTS() throws Exception {
		// Try adding slot.
		if (!echoValue(pinInfo.SLOTS_PATH, PWM_SLOT_NAME)) {
			throw new Exception("Unable to add slot " + CAPE_BONE_IIO);
		}    	
    }    
    
    /**
     * Try compiling the DTs file.
     * 
     * @throws Exception
     */
    public static void compileDTS() throws Exception {
    	
    	compileDTSfile(DTS_SLOT_NAME);
    	
    }    

    /**
     * Try compiling the DTs file.
     * 
     * @throws Exception
     */
    public static void compilePWMDTS() throws Exception {
    	
    	compileDTSfile(PWM_SLOT_NAME);
    	
    }    
    
    /**
     * Try compiling the DTs file.
     * 
     * @throws Exception
     */
    public static void compileDTSfile(String dtsFile) throws Exception {
    	
    	
    	// Compile the file.
       	String compileDTSCommand = "dtc -O dtb -o " + dtsFile + "-00A0.dtbo -b 0 -@ " + dtsFile + ".dts";
       	if (!executeCommand(compileDTSCommand)) {
       		throw new Exception("Unable to compile the DTS file.");
       	}
       	
       	// Copy to the DTS target.
       	String copyDTStoTarget = "cp " + dtsFile + "-00A0.dtbo " + DTS_TARGET_DIR;
       	if (!executeCommand(copyDTStoTarget)) {
       		throw new Exception("Unable to copy the DTS file to " + DTS_TARGET_DIR);
       	}
    }
    

    /**
     * Test to see if PWM is exported.
     * 
     * @return
     */
    public static boolean isPWMExported() {
    	File testDir = new File(pinInfo.PWM_P9_22);
    	return testDir.isDirectory();
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
    
    /**
     * Find the path to the analog pins...
     * 
     * @return
     * @throws Exception
     */
    public static String getAnalogPath() throws Exception {
    	
    	// Has it been found ?
    	if (analogPath == null) {
    		// Search for first analog pin.
            Path startingDir = Paths.get(pinInfo.ANALOG_START_PATH);
            String pattern = pinInfo.ANALOG0;

            // Use Java examples to find directory
            Finder finder = new Finder(pattern);
            Files.walkFileTree(startingDir, finder);
        	
            // Did we found something ?
            if (finder.getLastMatch() != null && finder.getLastMatch().length() > 5) {
            	analogPath = finder.getLastMatch().substring(0, finder.getLastMatch().length() - 5);
            }
    	}
    	
    	// Return path...
    	return analogPath;
    }
    
    /**
     * Test to see if the PWM DTS is compiled
     * 
     * @return
     * @throws Exception
     */
    public static boolean isPWMCompiled() throws Exception {

    	String pwmPath = "***";
    	
		// Search for PWM DTS.
        Path startingDir = Paths.get(DTS_TARGET_DIR);
        String pattern = PWM_SLOT_NAME + "*";

        // Use Java examples to find directory
        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
    	
        // Did we found something ?
        if (finder.getLastMatch() != null) {
        	pwmPath = finder.getLastMatch();
        }
    	
    	// Test if found.
    	return !"***".equals(pwmPath);
    }    
    
    /**
     * Convenient example I found on the in-ter-net...
     * 
     * @author Java example
     *
     */
	public static class Finder extends SimpleFileVisitor<Path> {

		private final PathMatcher matcher;
		private Path lastMatch = null;

		Finder(String pattern) {
			matcher = FileSystems.getDefault()
					.getPathMatcher("glob:" + pattern);
		}

		// Compares the glob pattern against
		// the file or directory name.
		void find(Path file) {
			Path name = file.getFileName();
			if (name != null && matcher.matches(name)) {
				lastMatch = file;
			}
		}

		// Prints the total number of
		// matches to standard out.
		String getLastMatch() {
			if (lastMatch != null) {
				return lastMatch.toString();
			} else {
				return null;
			}
		}

		// Invoke the pattern matching
		// method on each file.
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			find(file);
			return CONTINUE;
		}

		// Invoke the pattern matching
		// method on each directory.
		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) {
			find(dir);
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			System.err.println(exc);
			return CONTINUE;
		}
	}
    
}
