package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;

// import the commands

/**
 * Auto mode main class
 * <p>
 * This class creates the auto command to drive the robot during autonomous mode
 */
public class AutoMainCmd extends SequentialCommandGroup
{   

    int count = 0;
    public static int testPos[] = {1900, 1100,  0};
	public AutoMainCmd()
    {
    //    super( 
    //         new LoopCmd(new MoveBack(), ()->RobotContainer.m_sensor.getIRDistance()<30) 
    //    );

        // super( 
        //     new MoveArm(0.2, 0.0, 0.5) ,
        //     new MoveArm(0.2, 0.2, 0.5) ,
        //     new MoveArm(0.4, 0.2, 0.5),
        //     new MoveArm(0.4, 0.0, 0.5),
        //     new MoveArm(0.2, 0.0, 0.5),
        //     new MoveArm(0.2, 0.2, 0.5) ,
        //     new MoveArm(0.4, 0.2, 0.5),
        //     new MoveArm(0.4, 0.0, 0.5),
        //     new MoveArm(0.2, 0.0, 0.5)
  
        // );

        // super (
        //     new MovetoB(Layout.Convert_mm_Pose2d(Layout.workOrderPos)),
        //     new MovetoB(Layout.Convert_mm_Pose2d(Layout.dispensaryPos)),
        //     new MovetoB(Layout.Convert_mm_Pose2d(Layout.medCubeStandPos[0])),
        //     new MovetoB(Layout.Convert_mm_Pose2d(Layout.medCubeStandPos[1])),
        //     new MovetoB(Layout.Convert_mm_Pose2d(Layout.startPos))
        //     );

        super(
            new MoveRobot(1, 1, 0, 0, 0.4),
            new MovetoB(Layout.Convert_mm_Pose2d(testPos))
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI)
             );
    }
    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize()
    {
        //Must be initialised before super.initialise
        super.initialize();
        RobotContainer.m_arm.initialize();
        RobotContainer.m_omnidrive.initialise();
    }
}
