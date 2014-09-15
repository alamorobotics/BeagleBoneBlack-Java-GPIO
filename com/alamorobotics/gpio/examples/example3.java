package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinControl;
import com.alamorobotics.gpio.pinInfo;
import com.alamorobotics.gpio.pinSetup;


/**
 * Example 3, Toggling a couple of LEDs with push buttons...
 * 
 * This will just continue on from the first example.
 * Hook up a couple of LEDs to P9_11 and P9_12.
 * Keep in mind that you cannot directly hook these up to the pins, you need some kind of buffer.
 * Derek Molloy uses a transistor to drive his LED in his example, however I prefer a buffer.
 * 
 * I'm using a ULN2803A Darlington Transistor Arrays.
 * Hook up Pin 9 on the ULN2803A to ground on your beagle bone, Pin P9_45 for example.
 * Hook up Pin 10 on the ULN2803A to 5V on your beagle bone, Pin P9_07 for example.
 * This chip can sink up to 500 mA per channel, up to 2.5 A total for the chip.
 * The chip also have built in clamping diodes to it can drive solenoids or motors as well.  
 * 
 * Hook P9_11 to Pin 1 on the ULN2803A, then pin 18 the ULN2803A to the LED followed by a 200 ohm resistor to P9_07.
 * Hook P9_12 to Pin 2 on the ULN2803A, then pin 17 the ULN2803A to the LED followed by a 200 ohm resistor to P9_07.
 * 
 * Hook up three push buttons, P9_26 will be the button to read to see if we want to exit the program.
 * 
 * P9_26 does not have a pullup so you need to add an external.
 * Connect ground to a 10K resistor, connect the resistor to one side of pushbutton 1.
 * On this side of the pushbutton, connect P9_26.
 * On the other side of the push button, connect it to +3.3V, for example P9_03...
 * 
 * P9_24 have an internal pull up enabled, connect this side to pushbutton 2 and the other side of the button to ground.
 * P9_23 have an internal pull down enabled, connect this side to pushbutton 3 and the other side of the button to +3.3V, P9_03.
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/example3.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.example3 
 * 
 * @author fredrik
 *
 */

public class example3 {
	
	
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
		
		// And setup the next pin...
		System.out.println("Setting pin P9_12 as Output.");
		pinInfo pinP9_12 = pinInfo.getPinInfo("P9_12");
		pinP9_12.direction = pinInfo.OUTPUT;
		pinP9_12.comment = "LED 02";
		System.out.println("Reserving P9_12.");
		pinInfo.reservePin(pinP9_12);
		
		// Now for the input pins...
		// Make direction input and disable internal pullups. 
		pinInfo pinP9_26 = pinInfo.getPinInfo("P9_26");
		pinP9_26.direction = pinInfo.INPUT;
		pinP9_26.pullupType = pinInfo.NONE;
		pinP9_26.comment = "Button 01";
		pinInfo.reservePin(pinP9_26);
		
		// Make direction input and enable pullup, this will default to 1. 	
		pinInfo pinP9_24 = pinInfo.getPinInfo("P9_24");
		pinP9_24.direction = pinInfo.INPUT;
		pinP9_24.pullupType = pinInfo.PULLUP;
		pinP9_24.comment = "Button 02";
		pinInfo.reservePin(pinP9_24);

		// Make direction input and enable pulldown, this will default to 0. 	
		pinInfo pinP9_23 = pinInfo.getPinInfo("P9_23");
		pinP9_23.direction = pinInfo.INPUT;
		pinP9_23.pullupType = pinInfo.PULLDOWN;
		pinP9_23.comment = "Button 03";
		pinInfo.reservePin(pinP9_23);		
		
		
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

		// Loop until pushbutton 1 is pressed.
		while (!pinControl.readPin(pinP9_26)) {
			// Write status,
			System.out.println("pinControl.readPin(P9_26) " + pinControl.readPin(pinP9_26));
			System.out.println("pinControl.readPin(P9_24) " + pinControl.readPin(pinP9_24));
			System.out.println("pinControl.readPin(P9_23) " + pinControl.readPin(pinP9_23));
			System.out.println();
			// Set LEDs
			pinControl.setPin(pinP9_11, pinControl.readPin(pinP9_24));
			pinControl.setPin(pinP9_12, pinControl.readPin(pinP9_23));
			// Sleep for a bit, make sure to hold push button long enough to poll the state.
	    	Thread.sleep(100);
		}
		
		// UnExport pins so they are freed up...
		System.out.println("Unexporting pins to free them up.");
		pinSetup.exportPins();
		
	}
}
