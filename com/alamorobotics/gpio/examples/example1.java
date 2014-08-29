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
 *  
 * 
 * 
 * @author fredrik
 *
 */

public class example1 {
	
	
	public static void main(String[] args) throws Exception {	
	
		pinInfo myPin = pinInfo.getPinInfo("P9_11");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 01";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_12");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 02";
		pinInfo.reservePin(myPin);
		
		// Make the file
		pinSetup.makeDTSfile();
		
		// Compile it...
		pinSetup.compileDTS();
		
		// Remove any pre-existing ones.
		if (pinSetup.isDTSApplied()) {
			pinSetup.removeDTS();
		}
		
		// Apply DTS...
		pinSetup.AddDTS();
	
		// Check if it was applied.
		if (pinSetup.isDTSApplied()) {
			System.out.println("BB-AR-GPI is applied and the Slot number is: " + pinSetup.getDTSSlotNumber());
		} else {
			System.out.println("BB-AR-GPI is not applied.");
		}
	}
}
