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
  private int counter;
  private int numBalls;

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
    numBalls = storage.getNumBalls();
    counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // shootThrottle = Robot.robotContainer.getController().getRawAxis(1);
    // if(Math.abs(shootThrottle) < .1){
    // shootThrottle = 0;
    // }
    // shooter.outtakeBall(shootThrottle*.8);

    if (storage.getTurretStorageBool() == true) {
      counter++;
      shooter.outtakeBall(.6);
      if (counter >= 50) {
        storage.setTurretStorageSpeed(-.6);
      }
      // storage.shiftForward();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.outtakeBall(0);
    storage.setTurretStorageSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // stop running this command when numballs decreases by one
    if (storage.getTurretStorageBool() == false) {
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
