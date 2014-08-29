BeagleBoneBlack-Java-GPIO
=========================

Java routines to use the Beagle Bone Black GPIO pins.

How to install Java on the beagle bone...
With the new Debian image this is quite simple, on a command line run the following as root,
apt-get update
apt-get install openjdk-7-jdk

How do I compile the examples ?
Copy the files to a suitable directory.
Make sure the you are in the "root" level of this directory, you should only see the com folder.
Do not change your directory to com/alamorobotics...
Compile the example file,
javac com/alamorobotics/gpio/examples/example1.java
This will also compile the required files as well...
Now run your example,
java -cp . com.alamorobotics.gpio.examples.example1

Your shell would look something like,
root@beaglebone:~# javac com/alamorobotics/gpio/examples/example1.java
root@beaglebone:~# java -cp . com.alamorobotics.gpio.examples.example1
Setting pin P9_11 as Output.
Reserving P9_11.
Setting pin P9_12 as Output.
Reserving P9_12.
Making the BB-AR-GPIO.dts file.
Compiling the BB-AR-GPIO.dts file into BB-AR-GPIO-00A0.dtbo
Removing previously applied BB-AR-GPIO-00A0.dtbo
Trying to apply BB-AR-GPI.
BB-AR-GPI is applied and the Slot number is: 9


Beta stage...

Version 0.1 Generate and compile DTS files. Apply the DTS files and use basic IO such as Input with pullup/pulldown and output.
