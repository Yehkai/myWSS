package frc.robot.subsystems;

import com.studica.frc.Servo;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private final Servo servo0;
    private final Servo servo1;
    private double servoValue;
    
    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_armValue = tab.add("armValue", 0).getEntry();
   
    public Arm () {
        servo0 = new Servo(0);
        servo1= new Servo(1);
    }

    /**
     * Sets the servo0 angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setServoAngle0(final double degrees) {
        servoValue = degrees;
        servo0.setAngle(degrees);
    }

    /**
     * Sets the servo1 angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setServoAngle1(final double degrees) {
        servoValue = degrees;
        servo1.setAngle(degrees);
    }

    /**
     * Code that runs once every robot loop
     */
    @Override
    public void periodic()
    {
        D_armValue.setDouble(servoValue);
    }
}
