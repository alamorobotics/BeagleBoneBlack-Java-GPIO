package com.alamorobotics.gpio.examples;

import com.alamorobotics.gpio.pinInfo;

/**
 * The final chapter in this exercises...
 * 
 * Hook up LEDs to P8_13, P8_19, P9_14, P9_16, P9_21 and P9_22.
 * Keep in mind you need a buffer, I used the ULN2803A Darlington Transistor Arrays.
 * Use the previous example to see how to hook this up...
 * 
 * This was a great exercise in setting up Device Tree Overlays, compiling them etc.
 * These classes might be helpful to learn these examples but there are better alternatives for GPIO.
 * 
 *  Check out LibBulldog for example, http://beagleboard.org/project/libbulldog/
 * 
 * Compile,
 * javac com/alamorobotics/gpio/examples/example5.java
 * Run,
 * java -cp . com.alamorobotics.gpio.examples.example5 
 * 
 * @author fredrik
 *
 */
public class example5 {

	public static void main(String[] args) throws Exception {

		// Set frequency to 20 KHz
		pinInfo.pwmModule0.setPeriodHertz(20000.0);
		pinInfo.pwmModule1.setPeriodHertz(20000.0);
		pinInfo.pwmModule2.setPeriodHertz(20000.0);
		
		// Start the modules
		pinInfo.pwmModule0.startPWM();
		pinInfo.pwmModule1.startPWM();
		pinInfo.pwmModule2.startPWM();
		
		// Set duty in percent.
		pinInfo.pwmModule0.setDutyAPercent(1.0);
		pinInfo.pwmModule0.setDutyBPercent(5.0);
		pinInfo.pwmModule1.setDutyAPercent(10.0);
		pinInfo.pwmModule1.setDutyBPercent(15.0);
		pinInfo.pwmModule2.setDutyAPercent(20.0);
		pinInfo.pwmModule2.setDutyBPercent(25.0);
		
		// Enable the PWM output.
		pinInfo.pwmModule0.setRunA("1");
		pinInfo.pwmModule0.setRunB("1");
		pinInfo.pwmModule1.setRunA("1");
		pinInfo.pwmModule1.setRunB("1");
		pinInfo.pwmModule2.setRunA("1");
		pinInfo.pwmModule2.setRunB("1");

		// Print example
		System.out.println("pinInfo.pwmModule0 period: " + pinInfo.pwmModule0.getPeriod());
		System.out.println("pinInfo.pwmModule0 dutyA:  " + pinInfo.pwmModule0.getDutyA());
		System.out.println("pinInfo.pwmModule0 dutyB:  " + pinInfo.pwmModule0.getDutyB());
		
		System.out.println("Module 0, PWM Pin A: " + pinInfo.pwmModule0.getPwmPinA());
		System.out.println("Module 0, PWM Pin B: " + pinInfo.pwmModule0.getPwmPinB());
		System.out.println("Module 1, PWM Pin A: " + pinInfo.pwmModule1.getPwmPinA());
		System.out.println("Module 1, PWM Pin B: " + pinInfo.pwmModule1.getPwmPinB());
		System.out.println("Module 2, PWM Pin A: " + pinInfo.pwmModule2.getPwmPinA());
		System.out.println("Module 2, PWM Pin B: " + pinInfo.pwmModule2.getPwmPinB());
		
		
		// Wait 5 secs
    	Thread.sleep(5000);
	
    	// Stop everything.
		pinInfo.pwmModule0.stopPWM();
		pinInfo.pwmModule1.stopPWM();
		pinInfo.pwmModule2.stopPWM();    	
    	
	}
	
}
