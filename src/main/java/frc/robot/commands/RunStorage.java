/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

public class RunStorage extends CommandBase {
  Intake intake;
  Storage storage;
  private double storageThrot;
  private double turretThrot;
  private double intakeThrot;
  private boolean backward, forward;
  private double speed;
  private int count = 0;
  private boolean autoStorage;
  
  /**
   * Creates a new IntakeBall.
   */
  public RunStorage() {
    // Use addRequirements() here to declare subsystem dependencies.
    
    // addRequirements(intake);
    // backward = bwd;
    // forward = fwd;
    // this.speed = speed;
    storage = Robot.robotContainer.storage;
    intake = Robot.robotContainer.intake;
    addRequirements(storage);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    storage.end();
    autoStorage = false;
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
    storageThrot = Robot.robotContainer.getController().getRawAxis(5);
    turretThrot = Robot.robotContainer.getController().getRawAxis(1);
    intakeThrot = Robot.robotContainer.getController().getRawAxis(3) - Robot.robotContainer.getController().getRawAxis(2);
   
   // storage.setStorageSpeed(storageThrot);
    storage.ballDetect();
    intake.intakeBall(intakeThrot/4);
 //storage.runStorage();
    if(Robot.robotContainer.getController().getRawButtonPressed(1) == true){
      SmartDashboard.putBoolean("autoStorage", true);
      autoStorage = true;
    }else if(Robot.robotContainer.getController().getRawButtonPressed(2) == true){
      SmartDashboard.putBoolean("autoStorage", false);
      autoStorage = false;
    }
    if(autoStorage){
      storage.runStorage();
    }else{
      storage.setStorageSpeed(storageThrot);
      storage.setTurretStorageSpeed(turretThrot);

    }
    
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