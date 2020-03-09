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
  private TalonFXSpeedController leftTalon1;
  private TalonFXSpeedController rightTalon1;
  private TalonFXSpeedController leftTalon2;
  private TalonFXSpeedController rightTalon2;
  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;
  private DifferentialDrive dDrive;

  private DoubleSolenoid shifter;
  private Value fast = Value.kForward;
  private Value slow = Value.kReverse;
  private Value off = Value.kOff;
  private String gearState;
  private boolean isFast = false;

  private TalonFX leftTalon1_f;
  private TalonFX rightTalon1_f;
  private TalonFX leftTalon2_f;
  private TalonFX rightTalon2_f;

  private AHRS navX;
  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    leftTalon1 = new TalonFXSpeedController(Constants.LEFT_MOTOR_1);
    leftTalon2 = new TalonFXSpeedController(Constants.LEFT_MOTOR_2);
    rightTalon1 = new TalonFXSpeedController(Constants.RIGHT_MOTOR_1);
    rightTalon2 = new TalonFXSpeedController(Constants.RIGHT_MOTOR_2);
    // leftTalon1.setInverted(true);
    // leftTalon2.setInverted(true);
    // leftTalons = new TalonFXSpeedController(Constants.LEFT_MOTOR_1, Constants.LEFT_MOTOR_2, false);
    // rightTalons = new TalonFXSpeedController(Constants.RIGHT_MOTOR_1, Constants.RIGHT_MOTOR_2, true);
   
    
    leftGroup = new SpeedControllerGroup(leftTalon1, leftTalon2);
    rightGroup = new SpeedControllerGroup(rightTalon1, rightTalon2);

    dDrive = new DifferentialDrive(leftGroup, rightGroup);

    shifter = new DoubleSolenoid(Constants.shifterUp1, Constants.shifterDown1);
    navX = new AHRS(Port.kUSB1);

    // leftTalon1_f = new TalonFX(Constants.LEFT_MOTOR_1);
    // leftTalon2_f = new TalonFX(Constants.LEFT_MOTOR_2);
    // rightTalon1_f = new TalonFX(Constants.RIGHT_MOTOR_1);
    // rightTalon2_f = new TalonFX(Constants.RIGHT_MOTOR_2);

    // leftTalon1_f.setInverted(true);
    // leftTalon2_f.setInverted(true);
    // leftTalon2_f.follow(leftTalon1_f);
    // rightTalon2_f.follow(rightTalon1_f);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void curveDrive(double speed, double turn) {
    // leftTalon1_f.set(ControlMode.PercentOutput, speed);
    // rightTalon1_f.set(ControlMode.PercentOutput, speed);
    dDrive.curvatureDrive(speed, turn, Math.abs(speed) < .1);
  }
  public void arcadeDrive(double speed, double turn){
    dDrive.arcadeDrive(speed, turn);
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
      System.out.println("shifted to fast");
      shifter.set(fast);
      
    } else if (gear.equals("slow")) {
      gearState = "slow";
      System.out.println("shifted to slow");
      shifter.set(slow);
      
    }
  }

  public void stopShift() {
    shifter.set(off);
  }

  

  public void resetEncoders(){
    leftTalons.resetEncoder();
    rightTalons.resetEncoder();
  }

  public int getAvgEncoder(){
    return (leftTalons.getEncoderTicks() - rightTalons.getEncoderTicks())/2;
  }
  public double getAngle() {
    return navX.getAngle();
  }

  public double getAvgSpeed(){
    return (leftTalons.getEncoderVel() - rightTalons.getEncoderVel())/2;
  }
  public void resetAngle() {
    navX.reset();
  }
}
