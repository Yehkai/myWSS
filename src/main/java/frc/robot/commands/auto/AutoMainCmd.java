package frc.robot.commands.auto;

import java.util.List;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import frc.robot.commands.auto.RotateTest;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AutoMainCmd extends SequentialCommandGroup
{   
    private double dist = RobotContainer.m_sensor.getIRDistance();

	public AutoMainCmd()
    {
//        super( new LoopCmd(new MoveBack(), ()->RobotContainer.m_sensor.getIRDistance()<60) 
//        );

        super( 
            new MoveServo2(200, 50) ,
            new MoveServo2(50, 50), 
            new MoveServo2(250, 50), 
            new MoveServo2(100, 150) 
  
        );


        // super(
        //     new MoveRobot(1, 0.5, 0, 0, 0.4),  
        //     new MoveRobot(0, 0.5, 0, 0, 0.4),  
        //     new MoveRobot(1, -0.5, 0, 0, 0.4),  
        //     new MoveRobot(0, -0.5, 0, 0, 0.4),  
        //     new MoveRobot(2, Math.PI*2, 0, 0, Math.PI)
        //      );
    }
}
