package com.alamorobotics.gpio;

/**
 * Testing purposes...
 * 
 * @author fredrik
 *
 */
public class test {

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
		
		// Define some pins...
		pinInfo myPin = pinInfo.getPinInfo("P9_11");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 01";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_12");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 02";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_13");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 03";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_14");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 04";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_15");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 05";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_16");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 06";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_17");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 07";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_18");
		myPin.direction = pinInfo.OUTPUT;
		myPin.comment = "LED 08";
		pinInfo.reservePin(myPin);

		
		myPin = pinInfo.getPinInfo("P9_21");
		myPin.direction = pinInfo.INPUT;
		myPin.pullupType = pinInfo.NONE;
		myPin.comment = "Button 01";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_22");
		myPin.direction = pinInfo.INPUT;
		myPin.pullupType = pinInfo.PULLUP;
		myPin.comment = "Button 02";
		pinInfo.reservePin(myPin);
		
		myPin = pinInfo.getPinInfo("P9_23");
		myPin.direction = pinInfo.INPUT;
		myPin.pullupType = pinInfo.PULLDOWN;
		myPin.comment = "Button 03";
		pinInfo.reservePin(myPin);
		
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

		if (pinSetup.isDTSApplied()) {
			System.out.println("BB-AR-GPI is applied and the Slot number is: " + pinSetup.getDTSSlotNumber());
		} else {
			System.out.println("BB-AR-GPI is not applied.");
		}
		
		pinSetup.exportPins();

		for (int i = 0; i<10; i++) {
			myPin = pinInfo.getPinInfo("P9_11");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_12");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_13");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_14");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_15");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_16");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_17");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_18");
			pinControl.setPinHigh(myPin);
	    	Thread.sleep(500);
			myPin = pinInfo.getPinInfo("P9_11");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_12");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_13");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_14");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_15");
			pinControl.setPinLow(myPin);
			myPin = pinInfo.getPinInfo("P9_16");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_17");
			pinControl.setPinHigh(myPin);
			myPin = pinInfo.getPinInfo("P9_18");
			pinControl.setPinLow(myPin);
	    	Thread.sleep(500);
		}

		myPin = pinInfo.getPinInfo("P9_11");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_12");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_13");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_14");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_15");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_16");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_17");
		pinControl.setPinLow(myPin);
		myPin = pinInfo.getPinInfo("P9_18");
		pinControl.setPinLow(myPin);

		
		while (!pinControl.readPin(pinInfo.getPinInfo("P9_21"))) {
			System.out.println("pinControl.readPin(P9_21) " + pinControl.readPin(pinInfo.getPinInfo("P9_21")));
			System.out.println("pinControl.readPin(P9_22) " + pinControl.readPin(pinInfo.getPinInfo("P9_22")));
			System.out.println("pinControl.readPin(P9_23) " + pinControl.readPin(pinInfo.getPinInfo("P9_23")));
			System.out.println("Raw 0 " + pinInfo.ANALOG0 + ": " + pinControl.getRawAnalogValue(pinInfo.ANALOG0));
			System.out.println("Raw 1 " + pinInfo.ANALOG1 + ": " + pinControl.getRawAnalogValue(pinInfo.ANALOG1));
			System.out.println("Voltage 0 " + pinInfo.ANALOG0 + ": " + pinControl.getAnalogVoltage(pinInfo.ANALOG0));
			System.out.println("Voltage 1 " + pinInfo.ANALOG1 + ": " + pinControl.getAnalogVoltage(pinInfo.ANALOG1));
			System.out.println("Percent 0 " + pinInfo.ANALOG0 + ": " + pinControl.getAnalogPercentage(pinInfo.ANALOG0));
			System.out.println("Percent 1 " + pinInfo.ANALOG1 + ": " + pinControl.getAnalogPercentage(pinInfo.ANALOG1));			
			System.out.println();
	    	Thread.sleep(200);
		}
		
		pinSetup.unExportPins();
		
	}
	
}
