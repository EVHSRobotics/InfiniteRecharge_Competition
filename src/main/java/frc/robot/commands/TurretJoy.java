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
import frc.robot.subsystems.Turret;

public class TurretJoy extends CommandBase {
  Turret turret;
  private double speed;
  /**
   * Creates a new TurretJoy.
   */
  public TurretJoy() {
    // Use addRequirements() here to declare subsystem dependencies.
    turret = Robot.robotContainer.turret;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    speed = Robot.robotContainer.getController().getRawAxis(0);

    turret.turnTurret(0, speed);
    SmartDashboard.putBoolean("Right Stop: ", turret.getRightLimitSwitchStatus());
    SmartDashboard.putBoolean("Left Stop: ", turret.getLeftLimitSwitchStatus());
    
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
