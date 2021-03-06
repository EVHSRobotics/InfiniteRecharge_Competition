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
import frc.robot.subsystems.Turret;

public class ShootBall extends CommandBase {
  private Shooter shooter;
  private Turret turret;
  private Intake intake;
  private double shootThrottle, turretThrottle, manualTurret;
  /**
   * Creates a new ShootBall.
   */
  public ShootBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = Robot.robotContainer.shooter;
    addRequirements(shooter);
    
    turret = Robot.robotContainer.turret;
    intake = Robot.robotContainer.intake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shootThrottle = Robot.robotContainer.getController().getRawAxis(2);
    turretThrottle =  Robot.robotContainer.getController().getRawAxis(3);
    manualTurret = Robot.robotContainer.getController().getRawAxis(4);
  
    shooter.outtakeBall(shootThrottle);
    turret.turnTurret(turretThrottle, manualTurret);

    if (Robot.robotContainer.getJoy().getRawAxis(3) > 0) {
      shooter.outtakeBall(1);
      System.out.println("shooter vel " + shooter.getShooterVel());
      if (shooter.getShooterVel() >= 9500) {
        System.out.println("reached shooter velocity");
        // shooter.intake();
        intake.intakeBall(1);
      }
    } else {
      shooter.outtakeBall(0);
    }

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
