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

public class IntakeBall extends CommandBase {
  Intake intake;
  Storage storage;
  double speed;
  /**
   * Creates a new IntakeBall.
   */
  public IntakeBall() {
    intake = Robot.robotContainer.intake;
    storage = Robot.robotContainer.storage;
    addRequirements(intake);
  // this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //speed = Robot.robotContainer.getController().getRawAxis(3);
    //intake.intakeBall(speed);
    //storage.intakeStorage(.5);
  //  storage.turretStorage(.5);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // storage.intakeStorage(0);
    // storage.turretStorage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
