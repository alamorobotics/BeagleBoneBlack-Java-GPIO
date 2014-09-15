package com.alamorobotics.gpio;

import java.util.HashMap;

/**
 * Class to contain info about pin I/O and DTD data...
 * 
 * @author fredrik
 *
 */
public class pinInfo {

	// Path to pins
	public static final String GPIOPIN_PATH = "/sys/class/gpio";
	public static final String SLOTS_PATH = "/sys/devices/bone_capemgr.9/slots";
	public static final String PINS_PATH = "/sys/kernel/debug/pinctrl/44e10800.pinmux/pins";
	
	// Direction
	public static final String INPUT = "INPUT";
	public static final String OUTPUT = "OUTPUT";
	
	// Pullup type...
	public static final String PULLUP = "0x37";
	public static final String PULLDOWN = "0x27";
	public static final String NONE = "0x2F";

	// Analog stuff
	public static final String ANALOG_START_PATH = "/sys/devices/ocp.3";
	public static final String ANALOG0 = "AIN0";
	public static final String ANALOG1 = "AIN1";
	public static final String ANALOG2 = "AIN2";
	public static final String ANALOG3 = "AIN3";
	public static final String ANALOG4 = "AIN4";
	public static final String ANALOG5 = "AIN5";
	public static final String ANALOG6 = "AIN6";
	
	// PWM Stuff
	public static final String PWM_PATH = "/sys/class/pwm";
	public static final String PWM_P8_13 = "/sys/class/pwm/pwm5";
	public static final String PWM_P8_19 = "/sys/class/pwm/pwm4";
	public static final String PWM_P9_14 = "/sys/class/pwm/pwm2";
	public static final String PWM_P9_16 = "/sys/class/pwm/pwm3";
	public static final String PWM_P9_21 = "/sys/class/pwm/pwm1";
	public static final String PWM_P9_22 = "/sys/class/pwm/pwm0";
	
	// Example data, may or may not be accurate...
	public String pinHeader = "P9_12";
	public int pinNum = 30;
	public long address = 0x44e10878;
	public int offset = 0x078;
	public String pinName = "GPIO1_28";
	public int gpioPinNum = 60;
	public String direction = INPUT;
	public String pullupType = NONE;
	public String comment = "LED 01";
	
	// Reserved pins...
	private static HashMap<String, pinInfo> reservedPins = new HashMap<String, pinInfo>();
	
	/**
	 * theInfo is the pin data compiled from Derek Molloys PDF.
	 */
	private static final HashMap<String, pinInfo> theInfo;
	static {
		theInfo = new HashMap<String, pinInfo>();
		
		/** EMMC 2 pins.
		theInfo.put("P8_03", new pinInfo("P8_03", 6, 0x44E10818, 0x018, "GPIO1_6", 38));
		theInfo.put("P8_04", new pinInfo("P8_04", 7, 0x44E1081C, 0x01C, "GPIO1_7", 39));
		theInfo.put("P8_05", new pinInfo("P8_05", 2, 0x44E10808, 0x008, "GPIO1_2", 34));
		theInfo.put("P8_06", new pinInfo("P8_06", 3, 0x44E1080C, 0x00C, "GPIO1_3", 35));
		*/
		
		theInfo.put("P8_07", new pinInfo("P8_07", 36, 0x44E10890, 0x090, "TIMER4", 66));
		theInfo.put("P8_08", new pinInfo("P8_08", 37, 0x44E10894, 0x094, "TIMER7", 67));
		theInfo.put("P8_09", new pinInfo("P8_09", 39, 0x44E1089C, 0x09C, "TIMER5", 69));
		theInfo.put("P8_10", new pinInfo("P8_10", 38, 0x44E10898, 0x098, "TIMER6", 68));
		theInfo.put("P8_11", new pinInfo("P8_11", 13, 0x44E10834, 0x034, "GPIO1_13", 45));
		theInfo.put("P8_12", new pinInfo("P8_12", 12, 0x44E10830, 0x030, "GPIO1_12", 44));
		//theInfo.put("P8_13", new pinInfo("P8_13", 9, 0x44E10824, 0x024, "EHRPWM2B", 23));		//PWM Module 2B
		theInfo.put("P8_14", new pinInfo("P8_14", 10, 0x44E10828, 0x028, "GPIO0_26", 26));
		theInfo.put("P8_15", new pinInfo("P8_15", 15, 0x44E1083C, 0x03C, "GPIO1_15", 47));
		theInfo.put("P8_16", new pinInfo("P8_16", 14, 0x44E10838, 0x038, "GPIO1_14", 46));
		theInfo.put("P8_17", new pinInfo("P8_17", 11, 0x44E1082C, 0x02C, "GPIO0_27", 27));
		theInfo.put("P8_18", new pinInfo("P8_18", 35, 0x44E1088C, 0x08C, "GPIO2_1", 65));
		//theInfo.put("P8_19", new pinInfo("P8_19", 8, 0x44E10820, 0x020, "EHRPWM2A", 22));		//PWM Module 2A

		/** EMMC 2 pins.
		theInfo.put("P8_20", new pinInfo("P8_20", 33, 0x44E10884, 0x084, "GPIO1_31", 63));
		theInfo.put("P8_21", new pinInfo("P8_21", 32, 0x44E10880, 0x080, "GPIO1_30", 62));
		theInfo.put("P8_22", new pinInfo("P8_22", 5, 0x44E10814, 0x014, "GPIO1_5", 37));
		theInfo.put("P8_23", new pinInfo("P8_23", 4, 0x44E10810, 0x010, "GPIO1_4", 36));
		theInfo.put("P8_24", new pinInfo("P8_24", 1, 0x44E10804, 0x004, "GPIO1_1", 33));
		theInfo.put("P8_25", new pinInfo("P8_25", 0, 0x44E10800, 0x000, "GPIO1_0", 32));
		 */
		
		theInfo.put("P8_26", new pinInfo("P8_26", 31, 0x44E1087C, 0x07C, "GPIO1_29", 61));

		/** HDMI pins. Disable the HDMI to eanble.
		theInfo.put("P8_27", new pinInfo("P8_27", 56, 0x44E108E0, 0x0E0, "GPIO2_22", 86));
		theInfo.put("P8_28", new pinInfo("P8_28", 58, 0x44E108E8, 0x0E8, "GPIO2_24", 88));
		theInfo.put("P8_29", new pinInfo("P8_29", 57, 0x44E108E4, 0x0E4, "GPIO2_23", 87));
		theInfo.put("P8_30", new pinInfo("P8_30", 59, 0x44E108EC, 0x0EC, "GPIO2_25", 89));
		theInfo.put("P8_31", new pinInfo("P8_31", 54, 0x44E108D8, 0x0D8, "UART5_CTSN", 10));
		theInfo.put("P8_32", new pinInfo("P8_32", 55, 0x44E108DC, 0x0DC, "UART5_RTSN", 11));
		theInfo.put("P8_33", new pinInfo("P8_33", 53, 0x44E108D4, 0x0D4, "UART4_RTSN", 9));
		theInfo.put("P8_34", new pinInfo("P8_34", 51, 0x44E108CC, 0x0CC, "UART3_RTSN", 81));
		theInfo.put("P8_35", new pinInfo("P8_35", 52, 0x44E108D0, 0x0D0, "UART4_CTSN", 8));
		theInfo.put("P8_36", new pinInfo("P8_36", 50, 0x44E108C8, 0x0C8, "UART3_CTSN", 80));
		theInfo.put("P8_37", new pinInfo("P8_37", 48, 0x44E108C0, 0x0C0, "UART5_TXD", 78));
		theInfo.put("P8_38", new pinInfo("P8_38", 49, 0x44E108C4, 0x0C4, "UART5_RXD", 79));
		theInfo.put("P8_39", new pinInfo("P8_39", 46, 0x44E108B8, 0x0B8, "GPIO2_12", 76));
		theInfo.put("P8_40", new pinInfo("P8_40", 47, 0x44E108BC, 0x0BC, "GPIO2_13", 77));
		theInfo.put("P8_41", new pinInfo("P8_41", 44, 0x44E108B0, 0x0B0, "GPIO2_10", 74));
		theInfo.put("P8_42", new pinInfo("P8_42", 45, 0x44E108B4, 0x0B4, "GPIO2_11", 75));
		theInfo.put("P8_43", new pinInfo("P8_43", 42, 0x44E108A8, 0x0A8, "GPIO2_8", 72));
		theInfo.put("P8_44", new pinInfo("P8_44", 43, 0x44E108AC, 0x0AC, "GPIO2_9", 73));
		theInfo.put("P8_45", new pinInfo("P8_45", 40, 0x44E108A0, 0x0A0, "GPIO2_6", 70));
		theInfo.put("P8_46", new pinInfo("P8_46", 41, 0x44E108A4, 0x0A4, "GPIO2_7", 71));
		*/		
		
		theInfo.put("P9_11", new pinInfo("P9_11", 28, 0x44E10870, 0x070, "UART4_RXD", 30));
		theInfo.put("P9_12", new pinInfo("P9_12", 30, 0x44E10878, 0x078, "GPIO1_28", 60));
		theInfo.put("P9_13", new pinInfo("P9_13", 29, 0x44E10874, 0x074, "UART4_TXD", 31));
		//theInfo.put("P9_14", new pinInfo("P9_14", 18, 0x44E10848, 0x048, "EHRPWM1A", 50));	//PWM Module 1A
		theInfo.put("P9_15", new pinInfo("P9_15", 16, 0x44E10840, 0x040, "GPIO1_16", 48));
		//theInfo.put("P9_16", new pinInfo("P9_16", 19, 0x44E1084C, 0x04C, "EHRPWM1B", 51));	//PWM Module 1B
		theInfo.put("P9_17", new pinInfo("P9_17", 87, 0x44E1095C, 0x15C, "I2C1_SCL", 5));
		theInfo.put("P9_18", new pinInfo("P9_18", 86, 0x44E10958, 0x158, "I2C1_SDA", 4));
		
		/** I2C pins.
		theInfo.put("P9_19", new pinInfo("P9_19", 95, 0x44E1097C, 0x17C, "I2C2_SCL", 13));
		theInfo.put("P9_20", new pinInfo("P9_20", 94, 0x44E10978, 0x178, "I2C2_SDA", 12));
		*/
		
		//theInfo.put("P9_21", new pinInfo("P9_21", 85, 0x44E10954, 0x154, "UART2_TXD", 3));	//PWM Module 0B
		//theInfo.put("P9_22", new pinInfo("P9_22", 84, 0x44E10950, 0x150, "UART2_RXD", 2));	//PWM Module 0A
		theInfo.put("P9_23", new pinInfo("P9_23", 17, 0x44E10844, 0x044, "GPIO1_17", 49));
		theInfo.put("P9_24", new pinInfo("P9_24", 97, 0x44E10984, 0x184, "UART1_TXD", 15));
		
		/** Memory card pin.
		theInfo.put("P9_25", new pinInfo("P9_25", 107, 0x44E109AC, 0x1AC, "GPIO3_21", 117));
		*/
		
		theInfo.put("P9_26", new pinInfo("P9_26", 96, 0x44E10980, 0x180, "UART1_RXD", 14));
		
		/** Memory card pins.
		theInfo.put("P9_27", new pinInfo("P9_27", 105, 0x44E109A4, 0x1A4, "GPIO3_19", 115));
		theInfo.put("P9_28", new pinInfo("P9_28", 103, 0x44E1099C, 0x19C, "SPI1_CS0", 113));
		 */

		theInfo.put("P9_29", new pinInfo("P9_29", 101, 0x44E10994, 0x194, "SPI1_D0", 111));
		theInfo.put("P9_30", new pinInfo("P9_30", 102, 0x44E10998, 0x198, "SPI1_D1", 112));

		/** Memory card pins.
		theInfo.put("P9_31", new pinInfo("P9_31", 100, 0x44E10990, 0x190, "SPI1_SCLK", 110));
		*/
	};
	
	// PWM modules.
	public static pwmModule pwmModule0 = new pwmModule("0", "P9_22", "P9_21", PWM_P9_22, PWM_P9_21);
	public static pwmModule pwmModule1 = new pwmModule("1", "P9_14", "P9_16", PWM_P9_14, PWM_P9_16);
	public static pwmModule pwmModule2 = new pwmModule("2", "P8_19", "P8_13", PWM_P8_19, PWM_P8_13);
	
	/**
	 * Default constructor with demo data, the data may be out dated...
	 */
	public pinInfo() {
		
	}

	/**
	 * Construct a pinInfo with default data...
	 * 
	 * @param pinHeader
	 * @param catNum
	 * @param address
	 * @param offset
	 * @param pinName
	 * @param pinNum
	 */
	public pinInfo(String pinHeader,int pinNum, long address, int offset, String pinName, int gpioPinNum) {
		this.pinHeader = pinHeader;
		this.pinNum = pinNum;
		this.address = address;
		this.offset = offset;
		this.pinName = pinName;
		this.gpioPinNum = gpioPinNum;
	}
	
	
	public String getPinDTSInformation() throws Exception {
		
		if (INPUT.equals(direction) || OUTPUT.equals(direction)) {
			
			if (INPUT.equals(direction)) {
				return getInPutInfo();
			} else {
				return getOutPutInfo();
			}
			
		} else {
			throw new Exception("Unsupported pin direction.");
		}
		
	}
	
	/**
	 * Get a string that can be used to compile the DTS. 
	 * 
	 * @param comment
	 * @return
	 */
	private String getOutPutInfo() {

		// Construct string...
		String outPut = "				0x";
		outPut = outPut + String.format("%03X", offset);
		outPut = outPut + " 0x07  /* " + pinHeader + " OUTPUT MODE7          - " + comment + " */";
		return outPut;
		
	}
	
	/**
	 * Get a string that can be used to compile the DTS. 
	 * 
	 * @param comment
	 * @param pullType
	 * @return
	 * @throws Exception
	 */
	private String getInPutInfo() throws Exception {

		// Check valid pullups...
		if (PULLUP.equals(pullupType) || PULLDOWN.equals(pullupType) || NONE.equals(pullupType)) {
			
			// Make it human readable.
			String type = "NONE    ";
			if (PULLUP.equals(pullupType)) {
				type = "PULLUP  ";
			}
			if (PULLDOWN.equals(pullupType)) {
				type = "PULLDOWN";
			}

			// Construct string...
			String outPut = "				0x";
			outPut = outPut + String.format("%03X", offset);
			outPut = outPut + " " + pullupType + "  /* " + pinHeader + " INPUT  MODE7 " + type + " - " + comment + " */";
			return outPut;
			
		} else {
			throw new Exception("Unsupported Pullup type.");
		}
		
	}
	
	/**
	 * Get a specific pinInfo, pass pin name.
	 * 
	 * @param pinName
	 * @return
	 */
	public static pinInfo getPinInfo(String pinName) {
		return theInfo.get(pinName);
	}

	/**
	 * Reserve a pin for GPIO use.
	 * 
	 * @param pinToReserve
	 */
	public static void reservePin(pinInfo pinToReserve) {
		reservedPins.put(pinToReserve.pinHeader, pinToReserve);
	}
	
	/**
	 * Free a pin from GPIO use.
	 * 
	 * @param pinToReserve
	 */
	public static void freePin(pinInfo pinToReserve) {
		reservedPins.remove(pinToReserve.pinHeader);
	}

	/**
	 * Test to see if a pin is used.
	 * 
	 * @param pinToReserve
	 */
	public static boolean isPinReserved(pinInfo pinToTest) {
		pinInfo testPin = reservedPins.get(pinToTest.pinHeader);
		return (testPin != null);
	}

	/**
	 * Test to see if a pin is used.
	 * 
	 * @param pinToReserve
	 */
	public static boolean isPinReserved(String pinName) {
		pinInfo testPin = reservedPins.get(pinName);
		return (testPin != null);
	}
	
	/**
	 * Return the reserved pins...
	 * 
	 * @return
	 */
	public static HashMap<String, pinInfo> getReservedPins() {
		return reservedPins;
	}
	
}
