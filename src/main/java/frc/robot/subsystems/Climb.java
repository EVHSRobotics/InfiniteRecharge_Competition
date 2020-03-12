/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  CANSparkMax winch1;
  CANSparkMax winch2;
   /**
   * Creates a new Climb.
   */
  public Climb() {
    winch1 =  new CANSparkMax(Constants.WINCH_MOTOR_1, MotorType.kBrushless);
    winch2 =  new CANSparkMax(Constants.WINCH_MOTOR_2, MotorType.kBrushless);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void runWinch(double speed){
    winch1.set(speed);
    winch2.set(speed);
  }
}
