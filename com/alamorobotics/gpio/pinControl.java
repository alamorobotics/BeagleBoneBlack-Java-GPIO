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
