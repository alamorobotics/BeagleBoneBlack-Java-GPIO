package com.alamorobotics.gpio;

/**
 * 
 * Control pins, such as setting them to output, input, high, low or read a pin.
 * 
 * @author fredrik
 *
 */
public class pinControl {

	/**
	 * Set pin high or low depending on value.
	 * 
	 * @param myPin
	 * @param value
	 * @throws Exception 
	 */
	public static void setPin(pinInfo myPin, boolean value) throws Exception {
		// Test and set pin...
		if (value) {
			setPinHigh(myPin);
		} else {
			setPinLow(myPin);
		}
	}
	
	/**
	 * Set a pin high
	 * 
	 * @param myPin
	 * @throws Exception 
	 */
	public static void setPinHigh(pinInfo myPin) throws Exception {
		setPinParameter( myPin, "value", "1");
	}

	/**
	 * Set a pin Low
	 * 
	 * @param myPin
	 * @throws Exception 
	 */
	public static void setPinLow(pinInfo myPin) throws Exception {
		setPinParameter( myPin, "value", "0");
	}
	
	/**
	 * Set Pin to Input
	 * 
	 * @param myPin
	 * @throws Exception 
	 */
	public static void setPinToInput(pinInfo myPin) throws Exception {
		setPinParameter( myPin, "direction", "in");
	}
	
	/**
	 * Set Pin to Input
	 * 
	 * @param myPin
	 * @throws Exception 
	 */
	public static void setPinToOutput(pinInfo myPin) throws Exception {
		setPinParameter( myPin, "direction", "out");
	}
	
	/**
	 * Set a specific pin parameter
	 * 
	 * @param myPin
	 * @param parameter
	 * @param value
	 * @throws Exception 
	 */
    public static void setPinParameter(pinInfo myPin, String parameter, String value ) throws Exception {
    	
		// Try set value
		if (!pinSetup.echoValue(pinInfo.GPIOPIN_PATH + "/gpio" + myPin.gpioPinNum + "/" + parameter, value)) {
			throw new Exception("Unable set parameter for pin " + myPin.gpioPinNum);
		}
    }
    
    /**
     * Read pin to see if it is high or low.
     * 
     * @param myPin
     * @return
     * @throws Exception 
     */
    public static boolean readPin(pinInfo myPin) throws Exception {
    	if ("1".equals(getPinParameter( myPin, "value"))) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Get the raw analog value...
     * 
     * @param analogPin
     * @return
     * @throws Exception
     */
    public static String getRawAnalogValue(String analogPin) throws Exception {
    	
    	String value = "Unable to get Value.";
        try{
        	value = pinSetup.catPath(pinSetup.getAnalogPath() + "/" + analogPin); 
        }
        catch(Exception e){
            throw new Exception("Unable get analog value for pin " + analogPin);
        }    	
        return value;
    }

    /**
     * Convert to integer...
     * 
     * @param analogPin
     * @return
     * @throws Exception
     */
    public static int getRawIntAnalogValue(String analogPin) throws Exception {
    	int value = 0;
    	try {
    		value = Integer.parseInt(getRawAnalogValue(analogPin));
    	} catch (NumberFormatException nfe) {
    		value = 0;
    	}
    	return value;
    }

    /**
     * Get the voltage.
     * 
     * @param analogPin
     * @return
     * @throws Exception
     */
    public static double getAnalogVoltage(String analogPin) throws Exception {
    	double voltage = getRawIntAnalogValue(analogPin)/1000.0;
    	return voltage;
    }
    
    /**
     * Get the percentage.
     * 
     * @param analogPin
     * @return
     * @throws Exception
     */
    public static int getAnalogPercentage(String analogPin) throws Exception {
    	int  percentage = (int)(getRawIntAnalogValue(analogPin)/17.99);
    	return percentage;
    }

    
    
    /**
     * Get a specific pin parameter
     * 
     * @param myPin
     * @param parameter
     * @return
     * @throws Exception 
     */
    public static String getPinParameter(pinInfo myPin, String parameter) throws Exception {
    	
    	String value = "Unable to get Value.";
        try{
        	value = pinSetup.catPath(pinInfo.GPIOPIN_PATH + "/gpio" + myPin.gpioPinNum + "/" + parameter); 
        }
        catch(Exception e){
            throw new Exception("Unable get parameter for pin " + myPin.gpioPinNum);
        }    	
        return value;
    }	
	
}
