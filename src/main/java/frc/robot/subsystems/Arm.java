package frc.robot.subsystems;

import java.util.Map;

import com.studica.frc.Servo;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private final Servo servo0;
    private final Servo servo1;
    private final Servo servo2;
    private Translation2d m_pos;  //current arm tip position
    private final double a1 = 0.25; 
    private final double a2 = 0.25; 

    private double offset0 = 0;   //For making software adjustment to servo 
    private double offset1 = 0;
    private double q1, q2;
    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_servo0 = tab.add("servo0", 0).getEntry();
    private final NetworkTableEntry D_servo1 = tab.add("servo1", 0).getEntry();
    private final NetworkTableEntry D_servo2 = tab.add("servo2", 0).getEntry();
    private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -10, "max", +10)).getEntry();
    private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -10, "max", +10)).getEntry();

    private final NetworkTableEntry D_posX = tab.add("posX", 0).getEntry();
    private final NetworkTableEntry D_posY = tab.add("posY", 0).getEntry();
    private final NetworkTableEntry D_debug1 = tab.add("debug1", 0).getEntry();
    private final NetworkTableEntry D_debug2 = tab.add("debug2", 0).getEntry();
    private final NetworkTableEntry D_sliderX = tab.add("setX", 0.04).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.05, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_sliderY = tab.add("setY", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.4)) .getEntry();
    
   
    public Arm () {
        servo0 = new Servo(0);  //shoulder
        servo1 = new Servo(1);  //elbow
        servo2 = new Servo(2);  //gripper
        m_pos = new Translation2d(0,0);

    }

    /**
     * Sets the servo0 angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setServoAngle0(final double degrees) {
        servo0.setAngle(degrees);
    }

    /**
     * Get slider-x value
     * <p>
     * 
     * @return return slider value
     */
    public double getSliderX( ) {
        return D_sliderX.getDouble(0.04);
    }

    /**
     * Get slider-y value
     * <p>
     * 
     * @return return slider value
     */
    public double getSliderY( ) {
        return D_sliderY.getDouble(0.0);
    }

    /**
     * Sets the servo0 angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public double getServoAngle0( ) {
        return servo0.getAngle();
    }
    /**
     * Sets the servo1 angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setServoAngle1(final double degrees) {
        servo1.setAngle(degrees);
    }
    /**
     * Sets the arm tip (x,y) position
     * <p>
     * 
     * @param pos (x,y) position of arm tip
     */
    public void setArmPos(Translation2d pos ) {

        //Refer to https://www.alanzucconi.com/2018/05/02/ik-2d-1/
        m_pos = pos;
        double x = pos.getX();
        double y = pos.getY();
        // arm tip  cannot be physically in the area around origin
        if ( (x<0.05) && (y<0.1) ) {
            x = 0.05;
            m_pos = new Translation2d(x,y);
        }

        double a = a2;
        double c = a1;
        double b = Math.sqrt(x*x+y*y);
        double alpha = Math.acos( (b*b + c*c - a*a)/(2*b*c) );
        double beta = Math.acos( (a*a + c*c - b*b)/(2*a*c) );

        // A is servo0 angle wrt horizon
        // When A is zero, arm-c is horizontal.
        // beta is servo1 angle wrt arm-c (BA)
        // When beta is zero, arm-c is closed  to arm-c
        double B = beta;    //Use B to designate beta. Different from diagram.
        double A = alpha + Math.atan2(y,x);

        //servo0 and servo1 might be mounted clockwise or anti clockwise.
        //offset0 and offset1 are used to adjust the zero the arm position.
        //This makes it easier to mount and tune the arm.
        A = Math.toDegrees(A);
        B = Math.toDegrees(B);

        //Uncomment if servo direction needs to be flip.
        //A = 300 - A;  
        //B = 300 - A;

        servo0.setAngle(A + offset0);
        servo1.setAngle(B + offset1);

        D_debug1.setDouble(A);
        D_debug2.setDouble(B);
    }
    public void setArmPos2(Translation2d pos ) {


        m_pos = pos;
        double x = pos.getX();
        double y = pos.getY();

        // q2 = Math.acos( (x*x + y*y - a1*a1 - a2*a2)/(2*a1*a2) );
        // q1 = Math.atan(y/x) - Math.atan(a2*Math.sin(q2)/(a1+a2*Math.cos(q2)));

        q2 = Math.acos( (x*x + y*y - a1*a1 - a2*a2)/(2*a1*a2) );
        q2 = -(Math.PI-q2);

        q1 = Math.atan(y/x) + Math.atan(a2*Math.sin(q2)/(a1+a2*Math.cos(q2)));

        
        servo0.setAngle(Math.toDegrees(q1));
        servo1.setAngle(Math.toDegrees(q2));
    }
    /**
     * Code that runs once every robot loop
     */
    @Override
    public void periodic()
    {
        offset0 = D_offset0.getDouble(0.0);
        offset1 = D_offset1.getDouble(0.0);

        D_servo0.setDouble(servo0.getAngle());
        D_servo1.setDouble(servo1.getAngle());
        D_servo2.setDouble(servo2.getAngle());
        D_posX.setDouble(m_pos.getX());
        D_posY.setDouble(m_pos.getY());
        //D_posX.setDoubleArray( {m_pos.getX(), m_pos.getY()});
    }
}
