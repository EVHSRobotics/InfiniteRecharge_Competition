/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.TalonFXSpeedController;

public class Drivetrain extends SubsystemBase {
  private TalonFXSpeedController leftTalons;
  private TalonFXSpeedController rightTalons;
  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;
  private DifferentialDrive dDrive;

  private DoubleSolenoid shifter;
  private DoubleSolenoid shifter2;
  private Value fast = Value.kForward;
  private Value slow = Value.kReverse;
  private Value off = Value.kOff;
  private String gearState;
  private boolean isFast = false;

  private AHRS navX;
  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    leftTalons = new TalonFXSpeedController(Constants.LEFT_MOTOR_1, Constants.LEFT_MOTOR_2);
    rightTalons = new TalonFXSpeedController(Constants.RIGHT_MOTOR_1, Constants.RIGHT_MOTOR_2);
   

    leftGroup = new SpeedControllerGroup(leftTalons);
    rightGroup = new SpeedControllerGroup(rightTalons);

    dDrive = new DifferentialDrive(leftGroup, rightGroup);

    shifter = new DoubleSolenoid(Constants.shifterUp1, Constants.shifterDown1);
    shifter2 = new DoubleSolenoid(Constants.shifterUp2, Constants.shifterDown2);

    navX = new AHRS(Port.kUSB1);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double speed, double turn) {
    dDrive.curvatureDrive(speed, turn, Math.abs(speed) < .1);
  }

  public void toggleShift() {
    if (isFast)
      applyShift("slow");
    else if (!isFast)
      applyShift("fast");
    isFast = !isFast;
  }

  public void applyShift(String gear) {
    if (gear.equals("fast")) {
      gearState = "fast";
      shifter.set(fast);
      shifter2.set(fast);
      System.out.println("shifted to fast");
    } else if (gear.equals("slow")) {
      gearState = "slow";
      shifter.set(slow);
      shifter2.set(slow);
      System.out.println("shifted to slow");
    }
  }

  public void stopShift() {
    shifter.set(off);
    shifter2.set(off);
  }

  public void resetEncoders(){
    leftTalons.resetEncoder();
    rightTalons.resetEncoder();
  }

  public int getAvgEncoder(){
    return (leftTalons.getEncoderTicks() - rightTalons.getEncoderTicks())/2;
  }
  public double returnAngle() {
    return navX.getAngle();
  }

  public void resetAngle() {
    navX.reset();
  }
}
