package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinInfo;
import com.alamorobotics.gpio.pinSetup;


/**
 * Example 1, Generating a DTS file.
 * 
 * Don't know what a DTS file is ?
 * No worries, check out any of these two links,
 * https://www.youtube.com/watch?v=wui_wU1AeQc
 * https://learn.adafruit.com/introduction-to-the-beaglebone-black-device-tree/overview
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/example1.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.example1 
 * 
 * 
 * @author fredrik
 *
 */

public class example1 {
	
	
	public static void main(String[] args) throws Exception {	
	
		System.out.println("Setting pin P9_11 as Output.");
		pinInfo myPin = pinInfo.getPinInfo("P9_11");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 01";
		System.out.println("Reserving P9_11.");
		pinInfo.reservePin(myPin);
		
		System.out.println("Setting pin P9_12 as Output.");
		myPin = pinInfo.getPinInfo("P9_12");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 02";
		System.out.println("Reserving P9_12.");
		pinInfo.reservePin(myPin);
		
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
	}
}
