package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * Designed for REV Robotics TMD3782 v2 Color Sensor:
 * http://www.revrobotics.com/content/docs/REV-31-1537-DS.pdf
 * 
 * Data Sheet: http://www.revrobotics.com/content/docs/v2.pdf
 * 
 * Based on 2017 code from Team 1736 Robot Casserole:
 * https://www.programcreek.com/java-api-examples/?code=RobotCasserole1736/CasseroleLib/CasseroleLib-master/java/src/org/usfirst/frc/team1736/lib/Sensors/TCS34725ColorSensor.java
 *
 * Based on 2019 code from Team 7034:
 * https://www.chiefdelphi.com/t/writing-code-for-a-color-sensor/167303/8
 */
public class ColorSensor {

    private static I2C sensor;

    public boolean initalized; // True if sensor has been initialized, false if not

    private int redVal;
    private int greenVal;
    private int blueVal;
    private int clearVal;
    private int proxVal;

    public ColorSensor(I2C i2cBus) {
        sensor = i2cBus;
        sensor.write(Register.ENABLE | Constant.CMD, Constant.ENABLE_AEN | Constant.ENABLE_PON);
        initalized = false;
        init();
    }

    /**
     * Initializes the actual sensor state so colors can be read. By default the
     * sensor powers up to a "disabled" state. This enables it. Additionally, checks
     * the sensor has the proper internal ID and sets hard-coded gains and
     * integrator times.
     * 
     * @return true on success, false on failure to initialize
     */

    public boolean init() {
        initalized = false;
        System.out.print("Initalizing Color Sensor...");

        // Check we're actually connected to the sensor
        byte[] whoamiResponse = new byte[Constant.ALL];
        whoamiResponse[0] = 0x00;
        sensor.readOnly(whoamiResponse, Constant.ALL);
        System.out.println("WHOAMI: " + whoamiResponse[Register.ID]);

        if ((whoamiResponse[0] != Constant.PART)) { // Device part ID should be 0x60
            System.out.println("\nError - WHOAMI mismatch on Color Sensor! Cannot initalize!" + whoamiResponse);
            return false;
        }

        System.out.println("Color Sensor Initialized!");
        initalized = true;
        return true;
    }

    /**
     * Queries the sensor for the red, green, blue, and clear values Qualifies the
     * read to ensure the sensor has not been reset since the last read.
     * 
     * @return true on read success, false on failure.
     */
    public boolean readColors() {

        byte[] testBuf = new byte[Constant.ALL];

        // Don't bother doing anything if the sensor isn't initialized
        if (!initalized) {
            System.out.println("Error: Attempt to read from color sensor, but it's not initalized!");
            return false;
        }

        // Read all sensor data
        sensor.readOnly(testBuf, Constant.ALL);

        // Perform typecasting and bit-shifting on the recieved data
        redVal = (int) ((testBuf[Register.RDATAH] << 8) | (testBuf[Register.RDATA] & 0xFF));
        greenVal = (int) ((testBuf[Register.GDATAH] << 8) | (testBuf[Register.GDATA] & 0xFF));
        blueVal = (int) ((testBuf[Register.BDATAH] << 8) | (testBuf[Register.BDATA] & 0xFF));
        clearVal = (int) ((testBuf[Register.CDATAH] << 8) | (testBuf[Register.CDATA] & 0xFF));
        proxVal = (int) ((testBuf[Register.PDATAH] << 8) | (testBuf[Register.PDATA] & 0xFF));

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

    /**
     * This contains any data constants for writing to the Color Sensor.
     */
    @SuppressWarnings("unused")
    public static final class Constant {
        // Identities
        private static final int ADDR = 0x39; // Device address
        private static final int PART = 0x60; // Part ID
        private static final int ALL = 0x1E; // Used for buffer to readOnly all registers
        private static final int CMD = 0x80; // Command bit
        private final static int MULTIBYTE = 0x20; // Multibyte bit

        // Enabling
        private static final int ENABLE_PIEN = 0x40; // Proximity Interrupt Enable - Writing 1 allows proximity
                                                     // interrupts, subject to persist filter
        private static final int ENABLE_AIEN = 0x10; // Ambient Light Sensing Interrupt Enable - Writing 1 allows ALS
                                                     // interrupts, subject to the persist filter.
        private static final int ENABLE_WEN = 0x08; // Wait Enable - Writing 1 activates the wait timer
        private static final int ENABLE_PEN = 0x04; // Proximity Enable - Writing 1 enables proximity, 0 disables it.
        private static final int ENABLE_AEN = 0x02; // RGBC Enable - Writing 1 actives the ADC, 0 disables it
        private static final int ENABLE_PON = 0x01; // Power on - Writing 1 activates the internal oscillator, 0
                                                    // disables it

        // RGBC Integration Times
        private static final int INTEGRATIONTIME_2_4MS = 0xFF; // 2.4ms - 1 cycle - Max Count: 1024
        private static final int INTEGRATIONTIME_24MS = 0xF6; // 24ms - 10 cycles - Max Count: 10240
        private static final int INTEGRATIONTIME_50MS = 0xEB; // 50ms - 20 cycles - Max Count: 20480
        private static final int INTEGRATIONTIME_101MS = 0xD5; // 101ms - 42 cycles - Max Count: 43008
        private static final int INTEGRATIONTIME_154MS = 0xC0; // 154ms - 64 cycles - Max Count: 65535
        private static final int INTEGRATIONTIME_700MS = 0x00; // 700ms - 256 cycles - Max Count: 65535

        // Gain Values
        private static final int GAIN_1X = 0x00; // No gain
        private static final int GAIN_4X = 0x01; // 4x gain
        private static final int GAIN_16X = 0x02; // 16x gain
        private static final int GAIN_60X = 0x03; // 60x gain
    }

    /**
     * This contains every register value used for reading data from the Color
     * Sensor.
     */
    @SuppressWarnings("unused")
    public static final class Register {
        private static final int ENABLE = 0x00; // Enables states and interrupts
        private static final int ATIME = 0x01; // RGBC integration time
        private static final int WTIME = 0x03; // Wait time
        private static final int AILTL = 0x04; // Clear interrupt low threshold low byte
        private static final int AILTH = 0x05; // Clear interrupt low threshold high byte
        private static final int AIHTL = 0x06; // Clear interrupt high threshold low byte
        private static final int AIHTH = 0x07; // Clear interrupt high threshold high byte
        private static final int PILTL = 0x08; // Proximity interrupt low threshold low byte
        private static final int PILTH = 0x09; // Proximity interrupt low threshold high byte
        private static final int PIHTL = 0x0A; // Proximity interrupt high threshold low byte
        private static final int PIHTH = 0x0B; // Proximity interrupt high threshold high byte
        private static final int PERS = 0x0C; // Interrupt persistence filters
        private static final int CONFIG = 0x0D; // Configuration
        private static final int PPULSE = 0x0E; // Proximity pulse count
        private static final int CONTROL = 0x0F; // Gain control register
        private static final int REVISION = 0x11; // Die revision number
        private static final int ID = 0x12; // Device ID
        private static final int STATUS = 0x13; // Device status
        private static final int CDATA = 0x14; // Clear ADC low data register
        private static final int CDATAH = 0x15; // Clear ADC high data register
        private static final int RDATA = 0x16; // Red ADC low data register
        private static final int RDATAH = 0x17; // Red ADC high data register
        private static final int GDATA = 0x18; // Green ADC low data register
        private static final int GDATAH = 0x19; // Green ADC high data register
        private static final int BDATA = 0x1A; // Blue ADC low data register
        private static final int BDATAH = 0x1B; // Blue ADC high data register
        private static final int PDATA = 0x1C; // Proximity ADC low data register
        private static final int PDATAH = 0x1D; // Proximity ADC high data register
    }
}
