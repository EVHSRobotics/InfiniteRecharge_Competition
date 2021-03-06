/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private static TalonSRX shooterMotor1;
  private static TalonSRX shooterMotor2;

  private double currentSpeed;
  private double maxSpeed;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooterMotor1 = new TalonSRX(Constants.SHOOTER1_MOTOR);
    shooterMotor2 = new TalonSRX(Constants.SHOOTER2_MOTOR);
    shooterMotor2.setInverted(true);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void outtakeBall(double speed) {
    shooterMotor1.set(ControlMode.PercentOutput, speed);
    shooterMotor2.set(ControlMode.PercentOutput, speed);

  }

  public double getShooterVel() {
  
    return (shooterMotor1.getSelectedSensorVelocity() + shooterMotor2.getSelectedSensorVelocity()) / 2;
  }
}
