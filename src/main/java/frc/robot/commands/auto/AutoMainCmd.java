package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
// import the commands

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AutoMainCmd extends SequentialCommandGroup
{   

	public AutoMainCmd()
    {
    //    super( 
    //         new LoopCmd(new MoveBack(), ()->RobotContainer.m_sensor.getIRDistance()<30) 
    //    );

        super( 
            new MoveServo(300, 150) ,
            new MoveServo(0, 150), 
            new MoveServo(150, 150)
  
        );


        // super(
        //     new MoveRobot(1, 0.5, 0, 0, 0.4),  
        //     new MoveRobot(0, 0.5, 0, 0, 0.4),  
        //     new MoveRobot(1, -0.5, 0, 0, 0.4),  
        //     new MoveRobot(0, -0.5, 0, 0, 0.4),  
        //     new MoveRobot(2, Math.PI*2, 0, 0, Math.PI),
        //     new MoveRobot(2, -Math.PI*2, 0, 0, Math.PI)
        //      );
    }
}
