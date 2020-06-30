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
import frc.robot.Robot;

public class Storage extends SubsystemBase {
  /**
   * Creates a new Storage.
   */
  private Intake intake;
  private VictorSPX mainStorageMotor;
  private TalonFX turretStorageMotor;
  private DigitalInput frontBallDetect; // 3
  private DigitalInput secondBallDetect; // 2
  private DigitalInput fourthBallDetect; // 7
  private DigitalInput turretBallDetect; // 9

  boolean ballEntered;// = false;
  boolean passedSecond;// = false;
  boolean passedSecondTwice;
  boolean passedThird = false;
  boolean atFourth = false;
  boolean inTurret = false;
  boolean atSecond = false;
  boolean runTurretStorgae;
  int numBalls = 0;
  boolean runStorage;// = false;
  boolean frontPassedSecondSensor = false;
  boolean auto = false;

  public Storage() {
    turretStorageMotor = new TalonFX(Constants.STORAGE2_MOTOR);
    mainStorageMotor = new VictorSPX(Constants.MAIN_STORAGE_MOTOR);
    intake = Robot.robotContainer.intake;

    frontBallDetect = new DigitalInput(3);
    secondBallDetect = new DigitalInput(2);
    fourthBallDetect = new DigitalInput(7);
    turretBallDetect = new DigitalInput(9);
    mainStorageMotor.set(ControlMode.PercentOutput, 0);
    turretStorageMotor.set(ControlMode.PercentOutput, 0);
    ballEntered = false;
    atSecond = false;
    atFourth = false;
    inTurret = false;
    runStorage = false;
    runTurretStorgae = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setStorageSpeed(double speed) {
    if (auto == false) {
      if (Math.abs(speed) < 0.1) {
        mainStorageMotor.set(ControlMode.PercentOutput, 0);
      } else if (Math.abs(speed) > .1 && speed > 0) {
        mainStorageMotor.set(ControlMode.PercentOutput, speed);
      } else if (Math.abs(speed) > .1 && speed < 0) {
        mainStorageMotor.set(ControlMode.PercentOutput, speed);
      }
    } else {
      if (numBalls == 2 && Math.abs(speed) > 0) { // without the second check motor still runs even if speed is not inputted
        mainStorageMotor.set(ControlMode.PercentOutput, -.55);
      } else if (numBalls == 3 && Math.abs(speed) > 0) {
        mainStorageMotor.set(ControlMode.PercentOutput, -.6);
      } else {
        mainStorageMotor.set(ControlMode.PercentOutput, speed);
      }
    }

  }

  public void setTurretStorageSpeed(double speed) {
    if (auto == false) {
      if (Math.abs(speed) < 0.1) {
        turretStorageMotor.set(ControlMode.PercentOutput, 0);
      } else if (Math.abs(speed) > .1 && speed > 0) {
        turretStorageMotor.set(ControlMode.PercentOutput, -1*speed);
      } else if (Math.abs(speed) > .1 && speed < 0) {
        turretStorageMotor.set(ControlMode.PercentOutput, -1*speed);
      }
    } else {
      turretStorageMotor.set(ControlMode.PercentOutput, speed);
    }
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
    SmartDashboard.putBoolean("In Turret: ", inTurret);
    SmartDashboard.putBoolean("Ball has entered: ", ballEntered);
    SmartDashboard.putBoolean("At Fourth", atFourth);
    SmartDashboard.putBoolean("runTurret: ", runTurretStorgae);
    SmartDashboard.putBoolean("runStorage: ", runStorage);

    SmartDashboard.putNumber("Num Balls:", numBalls);
    // turretStorage(.5);

  }

  public void runStorage() {

    if (frontBallDetect.get() == true) { // front detects ball
      ballEntered = true;

      SmartDashboard.putString("ball", "HAS ENTERED");
    }
    if (secondBallDetect.get() == true) {
      atSecond = true;
    }
    if (fourthBallDetect.get() == true) {
      atFourth = true;
    }

    if (turretBallDetect.get() == true) {
      inTurret = true;
    }

    if (ballEntered) {
      runStorage = true;

      if (atSecond == true && numBalls == 0) {
        runStorage = false;
        ballEntered = false;
        numBalls = 1;
      } else if (atSecond == true && numBalls == 1) {
        runStorage = true;
        if (atFourth == true) {
          runStorage = false;
          ballEntered = false;
          numBalls = 2;
        }

      } else if (atFourth == true && numBalls == 2) {
        // runStorage = false;
        runTurretStorgae = true;
        if (inTurret == true) {

          runTurretStorgae = false;
          ballEntered = false;
          numBalls = 3;
          runStorage = false;
          atFourth = false;
          // if(atFourth == true){
          // runStorage = false;
          // }
        }

      } else if (atFourth == true && numBalls == 3) {
        // System.out.println("reached");
        runStorage = false;
        ballEntered = false;
      }

    }

    if (runStorage) {
      setStorageSpeed(-.55);
      SmartDashboard.putString("Storage is: ", "ON");
    } else {
      setStorageSpeed(0);
      SmartDashboard.putString("Storage is: ", "OFF");
    }

    if (runTurretStorgae) {
      turretStorageMotor.set(ControlMode.PercentOutput, .4);
      SmartDashboard.putString("Turret is: ", "ON");
    } else {
      turretStorageMotor.set(ControlMode.PercentOutput, 0);
      SmartDashboard.putString("Turret is: ", "OFF");
    }
  }

  public void end() {
    ballEntered = false;
    atSecond = false;
    atFourth = false;
    inTurret = false;
    runStorage = false;
    numBalls = 0;
    runTurretStorgae = false;
  }

  public void setAuto(boolean isAuto) {
    auto = isAuto;

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
 * 
 * 
 * 
 * if (ballHasEntered) {
 * 
 * if (secondBallDetect.get() == false) { runStorage = true; } else {
 * enteringSecond = true; } if (enteringSecond) { if (secondBallDetect.get() ==
 * false) { runStorage = false; ballHasEntered = false; enteringSecond = false;
 * } }
 * 
 * } if (fourthBallDetect.get() == true) { enteringTurret = true; runStorage =
 * true; //comment htis out
 * 
 * } if (enteringTurret) { if (turretBallDetect.get() == false) {
 * runTurretStorgae = true; } else { runTurretStorgae = false; enteringTurret =
 * false; runStorage = false; //comment this out } }
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