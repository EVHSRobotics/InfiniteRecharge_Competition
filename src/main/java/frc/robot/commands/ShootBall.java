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
  private double shootThrottle, turretThrottle, manualTurret, intakeThrottle;
  private double storageThrottle_2;
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shootThrottle = Robot.robotContainer.getController().getRawAxis(2);
    //storageThrottle_2 = Robot.robotContainer.getController().getRawAxis(3);
    turretThrottle =  Robot.robotContainer.getController().getRawAxis(3);
    manualTurret = Robot.robotContainer.getController().getRawAxis(0);
    //intakeThrottle = Robot.robotContainer.getController().getRawAxis(2);
  
    
    
    if(Math.abs(turretThrottle) < .2){
      turretThrottle = 0;
    }
    //System.out.println("Manual turret throttle: " + manualTurret/3);

    shooter.outtakeBall(shootThrottle/2);
    turret.turnTurret(turretThrottle, manualTurret);
    storage.intakeStorage(storageThrottle_2);
    //intake.intakeBall(intakeThrottle);
    

    // if (Robot.robotContainer.getJoy().getRawAxis(3) > 0) {
    //   shooter.outtakeBall(1);
    //   System.out.println("shooter vel " + shooter.getShooterVel());
    //   if (shooter.getShooterVel() >= 9500) {
    //     System.out.println("reached shooter velocity");
    //     // shooter.intake();
    //     intake.intakeBall(1);
    //   }
    // } else {
    //   shooter.outtakeBall(0);
    // }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
