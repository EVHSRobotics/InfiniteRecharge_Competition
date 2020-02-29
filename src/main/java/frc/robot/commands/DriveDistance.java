/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
  private Drivetrain drive;
  private double speed, heading, distance;
  private double desireDistance;
  /**
   * Creates a new DriveDistance.
   */
  public DriveDistance(double speed, double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = Robot.robotContainer.drivetrain;
    addRequirements(drive);

    desireDistance = distance;
    this.speed = speed;
 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
 
    drive.resetEncoders();
    System.out.println("drive distance initialized");
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(speed, 0); 

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Reached Distance");
    drive.drive(0,0);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(drive.getAvgEncoder()) > desireDistance) {
      return true;
      
    }
    else return false;
  }
}
