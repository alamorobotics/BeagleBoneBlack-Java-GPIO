package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinControl;
import com.alamorobotics.gpio.pinInfo;
import com.alamorobotics.gpio.pinSetup;


/**
 * Example 4, Reading analog values...
 * 
 * I hooked up the Sparkfun Analog joystick for this example.
 * https://www.sparkfun.com/products/10433
 * But you can also use a couple of potentiometers and a push button.
 * 
 * For the joystick, connect P9_32 to VCC, P9_34 to GND, P9_39 to Vert and P9_40 to Horz.
 * Connect P9_24 to the SEL button on the Joystick.
 * 
 * If you are using potentiometers,
 * For the first potentiometer, connect P9_32 and P9_34 to the fixed resistance terminals and P9_39 to the Wiper.
 * For the second potentiometer, connect P9_32 and P9_34 to the fixed resistance terminals and P9_40 to the Wiper.
 * The potentiometers should act as a voltage divider.
 * Connect a button to P9_24 that will ground the connection when pressed.
 *
 * The example will print the output from the Joystick until the button is pressed... 
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/example4.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.example4 
 * 
 * @author fredrik
 *
 */

public class example4 {
	
	
	public static void main(String[] args) throws Exception {	
	
		// Check if the analog DTS is applied.
		if (!pinSetup.isAnalogApplied()) {
			System.out.println("cape-bone-iio is not applied.");
			// If not, apply it.
			pinSetup.AddAnalogDTS();
		} else {
			System.out.println("cape-bone-iio is already applied.");
		}

		// Print out current directory for the Analog inputs.
		System.out.println("Analog Directory: " + pinSetup.getAnalogPath());
		
		
		// Make direction input and enable pullup, this will default to 1. 	
		pinInfo pinP9_24 = pinInfo.getPinInfo("P9_24");
		pinP9_24.direction = pinInfo.INPUT;
		pinP9_24.pullupType = pinInfo.PULLUP;
		pinP9_24.comment = "Joystick Button";
		pinInfo.reservePin(pinP9_24);
		
		// Make DTS file...
		pinSetup.makeDTSfile();
		
		// Compile it.
		pinSetup.compileDTS();
		
		// Do we already have one applied, if so, remove it.
		if (pinSetup.isDTSApplied()) {
			pinSetup.removeDTS();
		}
		
		// Add new DTS file.
		pinSetup.AddDTS();		
		
		// Export the pin.
		pinSetup.exportPins();		
		
		// Loop until Joystick button is pressed.
		while (pinControl.readPin(pinP9_24)) {
			// Print values in infinite loop, hit ctrl+c to abort.
			System.out.println("Raw 0 " + pinInfo.ANALOG0 + ": " + pinControl.getRawAnalogValue(pinInfo.ANALOG0));
			System.out.println("Raw 1 " + pinInfo.ANALOG1 + ": " + pinControl.getRawAnalogValue(pinInfo.ANALOG1));
			System.out.println("Voltage 0 " + pinInfo.ANALOG0 + ": " + pinControl.getAnalogVoltage(pinInfo.ANALOG0));
			System.out.println("Voltage 1 " + pinInfo.ANALOG1 + ": " + pinControl.getAnalogVoltage(pinInfo.ANALOG1));
			System.out.println("Percent 0 " + pinInfo.ANALOG0 + ": " + pinControl.getAnalogPercentage(pinInfo.ANALOG0));
			System.out.println("Percent 1 " + pinInfo.ANALOG1 + ": " + pinControl.getAnalogPercentage(pinInfo.ANALOG1));
			System.out.println();
	    	Thread.sleep(200);
		}		

		System.out.println("Joystick button pressed...");
		
		// Unexport pins.
		pinSetup.unExportPins();		
		
	}
}
