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
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Turret;

public class ShootBall extends CommandBase {
  private Shooter shooter;
  private Turret turret;
  private Intake intake;
  private Storage storage;
  private double shootThrottle;
  private int counter, numStorageBalls, numBallsShot;
  private boolean runShooter, runTurret, runStorage;
  private boolean hasShotFirst, passingShoot;
  private boolean enteringShooter, inCompleteTurret;
  private boolean isFinished, isIncomplete;

  /**
   * Creates a new ShootBall.
   */
  public ShootBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = Robot.robotContainer.shooter;
    addRequirements(shooter);

    turret = Robot.robotContainer.turret;
    intake = Robot.robotContainer.intake;
    storage = Robot.robotContainer.storage;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("Shooter On: ", runShooter);
    numStorageBalls = storage.getNumBalls();
    counter = 0;
    runShooter = false;
    runTurret = false;
    runStorage = false;
    passingShoot = false;
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    counter++;
    runShooter = true;
    if(storage.getTurretStorageBool() == true){
      passingShoot = true;
      runStorage = false;     
    }else if(storage.getTurretStorageBool() == false && passingShoot == false){
      runStorage = true;
      runTurret = true;
    }

    if(passingShoot == true && counter > 50){
      runTurret = true;
      if(storage.getTurretStorageBool() == false){
        isFinished = true;
      }
    }

    if(runShooter){
      shooter.outtakeBall(.7);
    }else{
      shooter.outtakeBall(0);
    }
    
    if(runStorage){
      storage.runStorage(true, -.5);
    }else{
      storage.runStorage(false, 0);
    }

    if(runTurret){
      storage.runTurretStorage(true, .8);
    }else{
      storage.runTurretStorage(false, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.outtakeBall(0);
    storage.runTurretStorage(false, 0);
    storage.runStorage(false, 0);
    numStorageBalls--;
    storage.setNumBalls(numStorageBalls);
    SmartDashboard.putNumber("Storage Balls: ", numStorageBalls);
  
    SmartDashboard.putBoolean("Shooter On: ", runShooter);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // stop running this command when numballs decreases by one
    System.out.println("finished: " + storage.getTurretStorageBool());
    if(isFinished){
      return true;
    }
    return false;
  }
}

/**
 * if turret sensor shows that there is a ball, then shoot every time a shoot is
 * successful, decrease numballs by one.
 * 
 * 
 * //System.out.println("Manual turret throttle: " + manualTurret/3);
 * 
 * //shooter.outtakeBall(shootThrottle); //System.out.println("turrent throttle"
 * + turretThrottle); //turret.turnTurret(-turretThrottle, manualTurret);
 * //storage.turretStorage(storageThrottle_2);
 * //storage.intakeStorage(mainStorageThrottle);
 * //intake.intakeBall(intakeThrottle);
 * 
 * 
 * // if (Robot.robotContainer.getJoy().getRawAxis(3) > 0) { //
 * shooter.outtakeBall(1); // System.out.println("shooter vel " +
 * shooter.getShooterVel()); // if (shooter.getShooterVel() >= 9500) { //
 * System.out.println("reached shooter velocity"); // // shooter.intake(); //
 * intake.intakeBall(1); // } // } else { // shooter.outtakeBall(0); // }
 * 
 */
