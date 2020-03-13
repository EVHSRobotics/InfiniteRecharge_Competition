/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  /**
   * Creates a new Storage.
   */
  private VictorSPX mainStorageMotor;
  private TalonFX storage2;
  public Storage() {
    storage2 = new TalonFX(Constants.STORAGE2_MOTOR);
    mainStorageMotor = new VictorSPX(Constants.MAIN_STORAGE_MOTOR);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void turretStorage(double speed){
   
   storage2.set(ControlMode.PercentOutput, speed);

  }
  public void intakeStorage(double speed){
    mainStorageMotor.set(ControlMode.PercentOutput, speed);
  }
}
