package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinControl;
import com.alamorobotics.gpio.pinInfo;
import com.alamorobotics.gpio.pinSetup;


/**
 * ToggleTest 2, Toggling an LED on and off.
 * 
 * I'm using a ULN2803A Darlington Transistor Arrays.
 * Hook up Pin 9 on the ULN2803A to ground on your beagle bone, Pin P9_45 for example.
 * Hook up Pin 10 on the ULN2803A to 5V on your beagle bone, Pin P9_07 for example.
 * This chip can sink up to 500 mA per channel, up to 2.5 A total for the chip.
 * The chip also have built in clamping diodes to it can drive solenoids or motors as well.  
 * 
 * Hook P9_11 to Pin 1 on the ULN2803A, then pin 18 the ULN2803A to the LED followed by a 200 ohm resistor to P9_07.
 * 
 * Result, about 1KHz frequency when toggling on/off and doing nothing else...
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/toggleTest.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.toggleTest 
 * 
 * @author fredrik
 *
 */

public class toggleTest {
	
	
	public static void main(String[] args) throws Exception {	
	
		System.out.println("Setting pin P9_11 as Output.");
		// Get your pinInfo, this contains basic information about the pin, see the pinInfo class for more details.
		pinInfo pinP9_11 = pinInfo.getPinInfo("P9_11");
		// Set direction to OUTPUT, if you specify the INPUT you also need to specify a Pullup type or none.
		// Default direction is input with no pullup resistor enabled.
		pinP9_11.direction = pinInfo.OUTPUT;
		// My comment, written to the DTS file.
		pinP9_11.comment = "LED 01";
		System.out.println("Reserving P9_11.");
		// Reserve pin, this will tell the pinInfor class that I intend to use it.
		pinInfo.reservePin(pinP9_11);
		
		// Make the file
		System.out.println("Making the BB-AR-GPIO.dts file.");
		pinSetup.makeDTSfile();
		
		// Compile it...
		System.out.println("Compiling the BB-AR-GPIO.dts file into BB-AR-GPIO-00A0.dtbo");
		pinSetup.compileDTS();
		
		// Remove any pre-existing ones.
		if (pinSetup.isDTSApplied()) {
			System.out.println("Removing previously applied BB-AR-GPIO-00A0.dtbo");
			pinSetup.removeDTS();
		}
		
		// Apply DTS...
		System.out.println("Trying to apply BB-AR-GPI.");
		pinSetup.AddDTS();
	
		// Check if it was applied.
		if (pinSetup.isDTSApplied()) {
			System.out.println("BB-AR-GPI is applied and the Slot number is: " + pinSetup.getDTSSlotNumber());
		} else {
			System.out.println("BB-AR-GPI is not applied.");
		}
		
		// Export pins so they can be used...
		System.out.println("Exporting pins so they can be used..");
		pinSetup.exportPins();

		// Pin state.
		boolean state = true;
		// Loop forever 
		while (1==1) {
			pinControl.setPin(pinP9_11,state);
			state = !state;
		}

	}
}
