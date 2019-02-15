package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * Designed for REV Robotics TMD3782 v2 Color Sensor http://www.revrobotics.com/content/docs/REV-31-1537-DS.pdf
 * Data Sheet: http://www.revrobotics.com/content/docs/v2.pdf
 * Based on 2017 code from Team 1736 Robot Casserole (https://www.programcreek.com/java-api-examples/?code=RobotCasserole1736/CasseroleLib/CasseroleLib-master/java/src/org/usfirst/frc/team1736/lib/Sensors/TCS34725ColorSensor.java#)
 * Based on 2019 code from Team 7034 (https://www.chiefdelphi.com/t/writing-code-for-a-color-sensor/167303/8)
 */
public class ColorSensor {

    private static I2C sensor;

    public ColorSensor() {
        sensor = new I2C(I2C.Port.kOnboard, Constants.ADDR);
        sensor.write(Constants.ENABLE, Constants.ENABLE_PIEN | Constants.ENABLE_AIEN | Constants.ENABLE_WEN | Constants.ENABLE_PEN | Constants.ENABLE_AEN | Constants.ENABLE_PON );
        
        sensor_initalized = false;
        good_data_read = false;

        init();
    }

    /**
     * Initializes the actual sensor state so colors can be read. By default the sensor powers up to
     * a "disabled" state. This enables it. Additionally, checks the sensor has the proper internal
     * ID and sets hard-coded gains and integrator times.
     * 
     * @return true on success, false on failure to initialize
     */
    public boolean init() {
        sensor_initalized = false;

        System.out.print("Initalizing Color Sensor...");

        byte[] whoamiResponse = new byte[1];
        whoamiResponse[0] = 0x00;

        // Check we're actually connected to the sensor
        sensor.read(Constants.ID, 1, whoamiResponse);
        // if (whoamiResponse[0] != 0x00) {
        //     System.out.println("\nError - whoami Constants mismatch on Color Sensor! Cannot initalize!");
        //     return false;
        // }

        // Set the integration time
        sensor.write(Constants.ATIME, Constants.INTEGRATIONTIME_2_4MS);

        // Set the gain
        sensor.write(Constants.CONTROL, Constants.GAIN_1X);

        System.out.println("Color Sensor Initialized!");
        sensor_initalized = true;
        return true;
    }

    // State Variables
    /** True if sensor has been initialized, false if not */
    public boolean sensor_initalized;
    /** True if the last read from the sensor was good, bad if data was corrupted */
    public boolean good_data_read;

    private int redVal;
    private int greenVal;
    private int blueVal;
    private int clearVal;
    private int proxVal;

    /**
     * Queries the sensor for the red, green, blue, and clear values Qualifies the read to ensure
     * the sensor has not been reset since the last read.
     * 
     * @return true on read success, false on failure.
     */
    public boolean readColors() {
        byte[] redBytes = {0, 0};
        byte[] greenBytes = {0, 0};
        byte[] blueBytes = {0, 0};
        byte[] clearBytes = {0, 0};
        byte[] proxBytes = {0, 0};

        byte[] testBuf = {0};

        // Don't bother doing anything if the sensor isn't initialized
        if (!sensor_initalized) {
            System.out.println("Error: Attempt to read from color sensor, but it's not initalized!");
            return false;
        }

        // Call the read bad if the enable register isn't set properly
        // (this gets reset to a different value if the sensor is power-cycled)
        sensor.read(Constants.ENABLE, 1, testBuf);
        if (testBuf[0] != ((Constants.ENABLE_PON | Constants.ENABLE_AEN))) {
            System.out.println("Error: Attempt to read from color sensor, but the enable register did not read as expected! Sensor has probably been reset.");
            sensor_initalized = false;
            good_data_read = false;
            return false;
        }

        // Read data off of the sensor
        sensor.read(Constants.RDATAH, 2, redBytes);
        sensor.read(Constants.GDATAH, 2, greenBytes);
        sensor.read(Constants.BDATAH, 2, blueBytes);
        sensor.read(Constants.CDATAH, 2, clearBytes);
        sensor.read(Constants.PDATAH, 2, proxBytes);

        // Perform typecasting and bit-shifting on the recieved data.
        redVal = (int) ((redBytes[1] << 8) | (redBytes[0] & 0xFF));
        greenVal = (int) ((greenBytes[1] << 8) | (greenBytes[0] & 0xFF));
        blueVal = (int) ((blueBytes[1] << 8) | (blueBytes[0] & 0xFF));
        clearVal = (int) ((clearBytes[1] << 8) | (clearBytes[0] & 0xFF));
        proxVal = (int) ((proxBytes[1] << 8) | (proxBytes[0] & 0xFF));

        // Set that we've got good data and return.
        good_data_read = true;
        return true;

    }

    /**
     * Returns the most recent red intensity read from the sensor
     * 
     * @return most recently read red intensity
     */
    public int getRed() {
        return redVal;
    }

    /**
     * Returns the most recent green intensity read from the sensor
     * 
     * @return most recently read green intensity
     */
    public int getGreen() {
        return greenVal;
    }

    /**
     * Returns the most recent blue intensity read from the sensor
     * 
     * @return most recently read blue intensity
     */
    public int getBlue() {
        return blueVal;
    }

    /**
     * Returns the most recent overall intensity read from the sensor
     * 
     * @return most recently read overall intensity
     */
    public int getClear() {
        return clearVal;
    }

    /**
     * Returns the most recent proximity read from the sensor
     * 
     * @return most recently read proximity
     */
    public int getProx() {
        return proxVal;
    }

    public static final class Constants {
        //I2C address
        private static final int ADDR = (0x39);

        //Data constants
        private static final int CMD = (0x80); // Command bit
        private final static int MULTI_BYTE_BIT = 0x20; // Multi byte bit
        private static final int ENABLE_PIEN = (0x40); // Proximity Interrupt Enable - Writing 1 allows proximity interrupts, subject to persist filter
        private static final int ENABLE_AIEN = (0x10); // Ambient Light Sensing Interrupt Enable - Writing 1 allows ALS interrupts, subject to the persist filter.
        private static final int ENABLE_WEN = (0x08); // Wait Enable - Writing 1 activates the wait timer
        private static final int ENABLE_PEN = (0x04); // Proximity Enable - Writing 1 enables proximity, 0 disables it.
	    private static final int ENABLE_AEN = (0x02); // RGBC Enable - Writing 1 actives the ADC, 0 disables it
	    private static final int ENABLE_PON = (0x01); // Power on - Writing 1 activates the internal oscillator, 0 disables it
        
        //RGBC integration times
        private static final int INTEGRATIONTIME_2_4MS = 0xFF; // 2.4ms - 1 cycle - Max Count: 1024
	    private static final int INTEGRATIONTIME_24MS = 0xF6; // 24ms - 10 cycles - Max Count: 10240
	    private static final int INTEGRATIONTIME_50MS = 0xEB; // 50ms - 20 cycles - Max Count: 20480
	    private static final int INTEGRATIONTIME_101MS = 0xD5; // 101ms - 42 cycles - Max Count: 43008
	    private static final int INTEGRATIONTIME_154MS = 0xC0; // 154ms - 64 cycles - Max Count: 65535
	    private static final int INTEGRATIONTIME_700MS = 0x00; // 700ms - 256 cycles - Max Count: 65535
        
        //Register values
        private static final int ENABLE = (0x00); // Enables states and interrupts
        private static final int ATIME = (0x01); // RGBC integration time
        private static final int WTIME = (0x03); // Wait time
        private static final int AILTL = (0x04); // Clear interrupt low threshold low byte
        private static final int AILTH = (0x05); // Clear interrupt low threshold high byte
        private static final int AIHTL = (0x06); // Clear interrupt high threshold low byte
        private static final int AIHTH = (0x07); // Clear interrupt high threshold high byte
        private static final int PILTL = (0x08); // Proximity interrupt low threshold low byte
        private static final int PILTH = (0x09); // Proximity interrupt low threshold high byte
        private static final int PIHTL = (0x0A); // Proximity interrupt high threshold low byte
        private static final int PIHTH = (0x0B); // Proximity interrupt high threshold high byte
        private static final int PERS = (0x0C); // Interrupt persistence filters
        private static final int CONFIG = (0x0D); // Configuration
        private static final int PPULSE = (0x0E); // Proximity pulse count
        private static final int CONTROL = (0x0F); // Gain control register
        private static final int REVISION = (0x11); // Die revision number
        private static final int ID = (0x12); // Device ID
        private static final int STATUS = (0x13); // Device status
        private static final int CDATA = (0x14); // Clear ADC low data register
        private static final int CDATAH = (0x15); // Clear ADC high data register
        private static final int RDATA = (0x16); // Red ADC low data register
        private static final int RDATAH = (0x17); // Red ADC high data register
        private static final int GDATA = (0x18); // Green ADC low data register
        private static final int GDATAH = (0x19); // Green ADC high data register
        private static final int BDATA = (0x1A); // Blue ADC low data register
        private static final int BDATAH = (0x1B); // Blue ADC high data register
        private static final int PDATA = (0x1C); // Proximity ADC low data register
        private static final int PDATAH = (0x1D); // Proximity ADC high data register

        // Gain values
        private static final int GAIN_1X = 0x00; // No gain
	    private static final int GAIN_4X = 0x01; // 4x gain
	    private static final int GAIN_16X = 0x02; // 16x gain
	    private static final int GAIN_60X = 0x03; // 60x gain
    }

}