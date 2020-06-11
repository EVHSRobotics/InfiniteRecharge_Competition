/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Turret extends SubsystemBase {
  private TalonSRX turretMotor;
  // private TalonSRX elevator = new TalonSRX(Constants.ELEVATOR);
  Vision vision;
  double kp;
  double kf, ki;
  double maxIntegral;
  double deadZone;
  double fric;
  double posErr;
  double intErr;
  double motorOutput;
  private DigitalInput limitSwitch_left;
  private DigitalInput limitSwitch_right;
  double encoderTicks;

  /**
   * Creates a new Turret.
   */
  public Turret() {
    turretMotor = new TalonSRX(Constants.TURRET_MOTOR);
    this.vision = Robot.robotContainer.vision;
    limitSwitch_left = new DigitalInput(1);
    limitSwitch_right = new DigitalInput(0);
    
    // kp = .03;
    // kf = 0.1;
    // ki = .0025;
    // maxIntegral = 100;
    // deadZone = 1;
    // fric = 0;
    // intErr = 0;
    // posErr = 0;
    kp = .02;
    kf = 0;
    ki = 0;
    maxIntegral = 0;
    deadZone = 1;
    fric = 0.1;
    intErr = 0;
    posErr = 0;
  }

  @Override
  public void periodic() {
    //System.out.println("turret Ecnoder: " + turretMotor.getSelectedSensorPosition());
  
  }

  public void turnTurret(double autoTrigger, double manual) {
    encoderTicks = turretMotor.getSelectedSensorPosition();
    posErr = vision.getX();
    //System.out.println("X: " + posErr);
    intErr += posErr;

    fric = (kf / deadZone) * posErr;
    if (fric > kf) {
      fric = kf;
    }
    if (fric < -kf) {
      fric = -kf;
    }

    if (intErr > maxIntegral) {
      intErr = maxIntegral;
    }
    if (intErr < -1 * maxIntegral) {
      intErr = -1 * maxIntegral;
    }
    if(Math.abs(posErr) > .1 && posErr * intErr < 0){
      intErr = 0;
    }
    motorOutput = autoTrigger * (intErr * ki + posErr * kp + fric) + manual*.5;
    //System.out.println("Motor output: " + motorOutput);
    if(Math.abs(encoderTicks) > 24000){
      motorOutput /= 5;
    }
    if (getLeftLimitSwitchStatus() == false && motorOutput > 0) {
      
      motorOutput = -.25;
    }
    if (getRightLimitSwitchStatus() == false && motorOutput < 0) {
      motorOutput = .25;
    }
   
    turretMotor.set(ControlMode.PercentOutput, motorOutput);
    SmartDashboard.putNumber("Motor Ouptut: ", motorOutput);
    
  }

  public boolean getLeftLimitSwitchStatus() {// right
    return limitSwitch_left.get();
  }

  public boolean getRightLimitSwitchStatus() { // left
    return limitSwitch_right.get();
  }

}
