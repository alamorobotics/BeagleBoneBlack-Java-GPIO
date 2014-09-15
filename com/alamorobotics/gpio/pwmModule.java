


package com.alamorobotics.gpio;

public class pwmModule {
	

	private String polarityA = "0";
	private String polarityB = "0";
	private String dutyA = "0";
	private String dutyB = "0";
	private String period = "500000";
	private String runA = "0";
	private String runB = "0";

	private String pwmModule = "0";
	private String pwmPinA = "P9_22";
	private String pwmPinADirectory = "";
	private String pwmPinB = "P9_21";
	private String pwmPinBDirectory = "";
	
	private boolean isStarted = false;
	
	/**
	 * Create a pwm module.
	 * 
	 * @param module
	 * @param pinA
	 * @param pinB
	 */
	public pwmModule(String module, String pinA, String pinB, String dirA, String dirB) {
		pwmModule = module;
		pwmPinA = pinA;
		pwmPinB = pinB;
		pwmPinADirectory = dirA;
		pwmPinBDirectory = dirB;
	}
	
	/**
	 * Start PWM for this module
	 * 
	 * @throws Exception
	 */
	public void startPWM() throws Exception {
		
		if (isStarted) {
			throw new Exception("PWM module " + pwmModule + " is already started.");
		}
		
		setupPWM();
		
		setPWMParameter(pwmPinADirectory, "period_ns", period);		
		setPWMParameter(pwmPinBDirectory, "period_ns", period);		
		
		setPWMAParameters();
		setPWMBParameters();
		
		isStarted = true;
		
	}
	

	/**
	 * Stop PWM for this module.
	 * 
	 * @throws Exception
	 */
	public void stopPWM() throws Exception {
		
		if (!isStarted) {
			throw new Exception("PWM module " + pwmModule + " is not started.");
		}
		
		runA = "0";
		runB = "0";		
		
		setPWMAParameters();
		setPWMBParameters();

		setPWMParameter(pwmPinADirectory, "period_ns", "0");		
		setPWMParameter(pwmPinBDirectory, "period_ns", "0");		
		
		isStarted = false;
		
	}
	
	
	
	/**
	 * Make sure everthing is setup...
	 * 
	 * @throws Exception
	 */
	private void setupPWM() throws Exception{
		
		// Compile DTS if needed
		if (!pinSetup.isPWMCompiled()) {
			pinSetup.copyPWMDTSfile();
			pinSetup.compilePWMDTS();
		}

		// Apply PWM if needed
		if (!pinSetup.isPWMApplied()) {
			pinSetup.AddPWMDTS();
		}
		
		// Export PWM if needed.
		if (!pinSetup.isPWMExported()) {
			pinSetup.exportPWM();
		}		
	}
	
	/**
	 * Set PWM A parameters.
	 * 
	 * @throws Exception
	 */
	private void setPWMAParameters() throws Exception {

		setPWMParameter(pwmPinADirectory, "polarity", polarityA);		
		setPWMParameter(pwmPinADirectory, "duty_ns", dutyA);		
		setPWMParameter(pwmPinADirectory, "run", runA);		
		
	}

	/**
	 * Set PWM B parameters.
	 * 
	 * @throws Exception
	 */	
	private void setPWMBParameters() throws Exception {

		setPWMParameter(pwmPinBDirectory, "polarity", polarityB);		
		setPWMParameter(pwmPinBDirectory, "duty_ns", dutyB);		
		setPWMParameter(pwmPinBDirectory, "run", runB);		
		
	}
	
	/**
	 * Set specific parameter.
	 * 
	 * @param pwmPath
	 * @param parameter
	 * @param value
	 * @throws Exception
	 */
	private void setPWMParameter(String pwmPath, String parameter, String value) throws Exception {

		// Try set value
		if (!pinSetup.echoValue(pwmPath + "/" + parameter, value)) {
			throw new Exception("Unable set parameter for pwm path " + pwmPath + " parameter: " + parameter + " value: " + value);
		}	
		
	}
	
	
	// *********************** Generic getter/setter
	
	/**
	 * @return the polarity
	 */
	public String getPolarityA() {
		return polarityA;
	}

	/**
	 * @param polarity the polarity to set
	 * @throws Exception 
	 */
	public void setPolarityA(String polarity) throws Exception {
		this.polarityA = polarity;
		
		// Set Parameters
		setPWMAParameters();		
	}

	/**
	 * @return the polarity
	 */
	public String getPolarityB() {
		return polarityB;
	}

	/**
	 * @param polarity the polarity to set
	 * @throws Exception 
	 */
	public void setPolarityB(String polarity) throws Exception {
		this.polarityB = polarity;
		// Set Parameters
		setPWMBParameters();		
	}

	/**
	 * @return the duty
	 */
	public String getDutyA() {
		return dutyA;
	}

	/**
	 * @param duty the duty to set
	 * @throws Exception 
	 */
	public void setDutyA(String duty) throws Exception {
		this.dutyA = duty;
		// Set Parameters
		setPWMAParameters();		
	}

	/**
	 * @param duty the duty to set
	 * @throws Exception 
	 */
	public void setDutyAPercent(double duty) throws Exception {
		
		double newDuty = new Double(getPeriod());
		newDuty = newDuty * duty /100.0;
		
		int setDuty = (int)newDuty;
				
		this.dutyA = setDuty + "";
		// Set Parameters
		setPWMAParameters();		
	}
	
	
	/**
	 * @return the duty
	 */
	public String getDutyB() {
		return dutyB;
	}

	/**
	 * @param duty the duty to set
	 * @throws Exception 
	 */
	public void setDutyB(String duty) throws Exception {
		this.dutyB = duty;
		// Set Parameters
		setPWMBParameters();		
	}

	
	/**
	 * @param duty the duty to set
	 * @throws Exception 
	 */
	public void setDutyBPercent(double duty) throws Exception {
		
		double newDuty = new Double(getPeriod());
		newDuty = newDuty * duty /100.0;
		
		int setDuty = (int)newDuty;
				
		this.dutyB = setDuty + "";
		// Set Parameters
		setPWMAParameters();		
	}	
	
	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 * @throws Exception 
	 */
	public void setPeriod(String period) throws Exception {
		
		if (isStarted) {
			throw new Exception("Cannot change the period since the PWM module is already started.");
		}
		
		this.period = period;
	}

	/**
	 * @param period the period to set
	 * @throws Exception 
	 */
	public void setPeriodHertz(double periodHZ) throws Exception {
		
		periodHZ = (1.0/periodHZ) * 1000000000.0; 
		
		if (isStarted) {
			throw new Exception("Cannot change the period since the PWM module is already started.");
		}
		int periodInt = (int)periodHZ;
		this.period = periodInt + "";
	}
	
	
	/**
	 * @return the run
	 */
	public String getRunA() {
		return runA;
	}

	/**
	 * @param run the run to set
	 * @throws Exception 
	 */
	public void setRunA(String run) throws Exception {
		this.runA = run;
		
		// Set Parameters
		setPWMAParameters();		
	}

	/**
	 * @return the run
	 */
	public String getRunB() {
		return runB;
	}

	/**
	 * @param run the run to set
	 * @throws Exception 
	 */
	public void setRunB(String run) throws Exception {
		this.runB = run;

		// Set Parameters
		setPWMBParameters();		
	}

	/**
	 * @return the pwmModule
	 */
	public String getPwmModule() {
		return pwmModule;
	}

	/**
	 * @return the pwmPinA
	 */
	public String getPwmPinA() {
		return pwmPinA;
	}

	/**
	 * @return the pwmPinB
	 */
	public String getPwmPinB() {
		return pwmPinB;
	}

	/**
	 * @return the pwmPinADirectory
	 */
	public String getPwmPinADirectory() {
		return pwmPinADirectory;
	}

	/**
	 * @return the pwmPinBDirectory
	 */
	public String getPwmPinBDirectory() {
		return pwmPinBDirectory;
	}	
	
	

}
