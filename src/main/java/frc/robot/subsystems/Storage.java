/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  /**
   * Creates a new Storage.
   */
  private VictorSPX mainStorageMotor;
  private TalonFX turretStorageMotor;
  private DigitalInput frontBallDetect; // 3
  private DigitalInput secondBallDetect; // 2
  private DigitalInput fourthBallDetect; // 7
  private DigitalInput turretBallDetect; // 9

  boolean ballHasEntered;// = false;
  boolean passedSecond;// = false;
  boolean passedSecondTwice;
  boolean passedThird = false;
  boolean passedFourth = false;
  boolean enteringTurret = false;
  boolean enteringSecond = false;
  boolean runTurretStorgae;

  boolean runStorage;// = false;
  boolean frontPassedSecondSensor = false;

  public Storage() {
    turretStorageMotor = new TalonFX(Constants.STORAGE2_MOTOR);
    mainStorageMotor = new VictorSPX(Constants.MAIN_STORAGE_MOTOR);
    frontBallDetect = new DigitalInput(3);
    secondBallDetect = new DigitalInput(2);
    fourthBallDetect = new DigitalInput(7);
    turretBallDetect = new DigitalInput(9);
    mainStorageMotor.set(ControlMode.PercentOutput, 0);
    turretStorageMotor.set(ControlMode.PercentOutput, 0);
    ballHasEntered = false;
    enteringSecond = false;
    passedFourth = false;
    enteringTurret = false;
    runStorage = false;
    runTurretStorgae = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intakeStorage(double speed) {
    mainStorageMotor.set(ControlMode.PercentOutput, speed);
  }

  public void ballDetect() {
    // System.out.println("front sensor: " + frontBallDetect.get());
    // System.out.println("second sensor: " + secondBallDetect.get());
    // System.out.println("fourth sensor: " + fourthBallDetect.get());
    // System.out.println("turret sensor: " + turretBallDetect.get());
    SmartDashboard.putBoolean("front sensor: ", frontBallDetect.get());
    SmartDashboard.putBoolean("second sensor: ", secondBallDetect.get());
    SmartDashboard.putBoolean("fourth sensor: ", fourthBallDetect.get());
    SmartDashboard.putBoolean("turret sensor: ", turretBallDetect.get());
    SmartDashboard.putBoolean("enteringTurret: ", enteringTurret);
    SmartDashboard.putBoolean("Ball has entered: ", ballHasEntered);
    SmartDashboard.putBoolean("runTurret: ", runTurretStorgae);
    // turretStorage(.5);

  }

  public void runStorage() {

    if (frontBallDetect.get() == true) { // front detects ball
      ballHasEntered = true;
      // SmartDashboard.putString("ball", "HAS ENTERED");
    }
    if (secondBallDetect.get() == true) {
      enteringSecond = true;
    }
    if (fourthBallDetect.get() == true) {
      passedFourth = true;
    }
    // if (turretBallDetect.get() == true) {
    // inTurret = true;
    // }

    if (ballHasEntered) {

      if (secondBallDetect.get() == false) {
        runStorage = true;
      } else {
        enteringSecond = true;
      }
      if (enteringSecond) {
        if (secondBallDetect.get() == false) {
          runStorage = false;
          ballHasEntered = false;
          enteringSecond = false;
        }
      }
      
    }
    if (fourthBallDetect.get() == true) {
      enteringTurret = true;
      runStorage = true;
     
    }
    if (enteringTurret) {
      if (turretBallDetect.get() == false) {
        runTurretStorgae = true;
      } else {
        runTurretStorgae = false;
        enteringTurret = false;
        runStorage = false;
      }
    }

    if (runStorage) {
      mainStorageMotor.set(ControlMode.PercentOutput, -.35);
      SmartDashboard.putString("Storage is: ", "ON");
    } else {
      mainStorageMotor.set(ControlMode.PercentOutput, 0);
      SmartDashboard.putString("Storage is: ", "OFF");
    }

    if (runTurretStorgae) {
      turretStorageMotor.set(ControlMode.PercentOutput, .25);
      SmartDashboard.putString("Turret is: ", "ON");
    } else {
      turretStorageMotor.set(ControlMode.PercentOutput, 0);
      SmartDashboard.putString("Turret is: ", "OFF");
    }
  }
}
// if(ballHasEntered){
// if(secondBallDetect.get() == false){
// System.out.println("storage on");
// }else{
// passedSecond = true;
// ba
// System.out.println("second detects Ball");
// }
// }

/*
 * for first ball: - check if first sensor detects ball - check if turrent,
 * third and second sensor detect ball - keep going until third sensor detects
 * ball - if third sensor detects ball and turret sensor doesnt detect ball, run
 * turret motor until it detects ball for second ball: - check if first sensor
 * detects ball - check if second and third sensor are on - keep going until
 * third sensor is off for third ball:] - check if first sensor detects ball -
 * check if second sensor detects ball - keep going until second sensor detects
 * ball for fourth ball: - check if first sensor detects ball - keep going until
 * first sensor detects ball for x counts/ ideally until first sensor no longer
 * detects a ball - only the case if turret, third and second sensors detect a
 * ball anytime sensors 1-3 dont detect a ball, run storage until turret detects
 * a ball - intake ball - if two balls are already present, dont run storage
 * motor after x counts
 * 
 * third sensor - 5 fourth sensor - 9 turret - 7
 * 
 * 
 * 
 * // if(!inTurret){ // runStorage = true; // }else{ // if(inTurret){ //
 * ballHasEntered = false; // } // if(!passedSecond){ // runStorage = true; //
 * }else{ // if(!passedSecond){ // runStorage = false; // } // }
 * 
 * // runStorage = false; // ballHasEntered = false; // }
 */

// if (ballHasEntered) {

// if (passedSecond == false) {
// runStorage = true;
// } else {
// passedSecondTwice = true;
// }

// if (passedSecondTwice) {
// // System.out.println("passed second again: " + passedSecond);
// if (passedSecond == false) {
// runStorage = false;
// ballHasEntered = false;
// passedSecondTwice = false;
// // SmartDashboard.putString("Ball entered value", "FAlse");
// }
// }
// }