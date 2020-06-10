/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

public class RunStorage extends CommandBase {
  Intake intake;
  Storage storage;
  private boolean backward, forward;
  private double speed;
  private int count = 0;
  
  /**
   * Creates a new IntakeBall.
   */
  public RunStorage() {
    // Use addRequirements() here to declare subsystem dependencies.
    // intake = Robot.robotContainer.intake;
    // addRequirements(intake);
    // backward = bwd;
    // forward = fwd;
    // this.speed = speed;
    storage = Robot.robotContainer.storage;
    addRequirements(storage);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(backward){
    //   intake.intakeBall(-1*speed);
    // }
    // if(forward){
    //   intake.intakeBall(speed);
    // }
    
    System.out.println("ball sensor working: " + storage.ballDetect());
    storage.runStorage();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
 //   intake.intakeBall(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
