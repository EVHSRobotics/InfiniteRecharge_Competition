/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

public class ShootAllBalls extends CommandBase {
  private Shooter shooter;
  private Storage storage;
  private int counter, numStorageBalls, numBallsShot;
  private boolean runShooter, runTurret, runStorage;
  private boolean hasShotFirst;
  private boolean enteringShooter, inCompleteTurret;
  /**
   * Creates a new ShootAllBalls.
   */
  public ShootAllBalls() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = Robot.robotContainer.shooter;
    storage = Robot.robotContainer.storage;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    counter = 0;
    runShooter = false;
    runTurret = false;
    hasShotFirst = false;
    runStorage = false;
    enteringShooter = false;
    numStorageBalls = storage.getNumBalls();
    numBallsShot = 0;
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   // if(numBalls != 0){
      counter++;
      runShooter = true;
      if(counter > 50 && hasShotFirst == false){
        runTurret = true;
        if(storage.getTurretStorageBool() == false){ // if storage is not at max capacity and balls need to move forward into turret first
         // storage.runStorage(true, -.4);
         runStorage = true;
        }
        hasShotFirst = true; 
        numBallsShot = 1;
      }
      if(hasShotFirst == true && storage.getTurretStorageBool() == false){ //if storage was already at full cap
       // storage.runStorage(true, -.5);
        runStorage = true;
       
      }

      //increase numBalls by measuring "space" between each ball
      if(storage.getFourthSensor() == true){
        enteringShooter = true;
      }
      if(storage.getTurretStorageBool() == false){ //previous ball has been shot
        inCompleteTurret = true;
      }
      if(enteringShooter == true && inCompleteTurret == true){
        if(storage.getTurretStorageBool() == true){ //next ball at turret sensor
          numBallsShot++;
          enteringShooter = false;
          inCompleteTurret = false;
        }
      }
    
  
    
  
    if(runShooter == true){
      shooter.outtakeBall(.6);
    }else{ 
      shooter.outtakeBall(0);
    }

    if(runTurret == true){
      storage.runTurretStorage(true, .8);
    }else{
      storage.runTurretStorage(false, 0);
    }

    if(runStorage == true){
      storage.runStorage(true, -.4);
    }else{
      storage.runStorage(false, 0);
    }
    SmartDashboard.putNumber("num Shot balls: ", numBallsShot);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.outtakeBall(0);
    storage.runTurretStorage(false, 0);
    storage.runStorage(false, 0);
    hasShotFirst = false;
    counter = 0;
    numBallsShot = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(numBallsShot == numStorageBalls && storage.getTurretStorageBool() == false){
      storage.setNumBalls(0);
      return true;
    }
    return false;
  }
}

/*
  when turret senses ball and button is pressed, shooter starts running
  after one ball is shot, the rest of the balls shift forward. 

  if turret is yes --> shooter turns on
  if turret is no --> move balls forward until turret is yes
  every time ball is shot, num balls goes down. 
  when num balls is zero, shooter turns off. 
*/