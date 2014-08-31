package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinControl;
import com.alamorobotics.gpio.pinInfo;
import com.alamorobotics.gpio.pinSetup;


/**
 * Example 2, Toggling a couple of LEDs on and off.
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
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/example2.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.example2 
 * 
 * @author fredrik
 *
 */

public class example2 {
	
	
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

		// Loop 50 times. 
		for (int i = 0; i<50; i++) {
			System.out.println("LEDS: 10");
			pinControl.setPinHigh(pinP9_11);
			pinControl.setPinLow(pinP9_12);
	    	Thread.sleep(100);
			System.out.println("LEDS: 01");
			pinControl.setPinLow(pinP9_11);
			pinControl.setPinHigh(pinP9_12);
	    	Thread.sleep(100);
		}
		
		// UnExport pins so they are freed up...
		System.out.println("Unexporting pins to free them up.");
		pinSetup.exportPins();
		
	}
}
