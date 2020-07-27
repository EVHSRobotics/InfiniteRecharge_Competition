/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class JoystickDrive extends CommandBase {
  Drivetrain drive;
  
  Intake intake;
  double throttle;
  double turn;
  double intakeSpeed;
  /**
   * Creates a new JoystickDrive.
   */
  public JoystickDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = Robot.robotContainer.drivetrain;
    this.intake = Robot.robotContainer.intake;
    addRequirements(drive);
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("hello");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    throttle = Robot.robotContainer.getJoy().getRawAxis(1);
    turn = Robot.robotContainer.getWheel().getRawAxis(0);
    
    if(Math.abs(turn) < .0001){
      turn = 0;
    }
    if(Math.abs(throttle) < .09){
      throttle = 0;
    }
    drive.curveDrive(throttle, -turn);
    
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.curveDrive(0, 0);
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
