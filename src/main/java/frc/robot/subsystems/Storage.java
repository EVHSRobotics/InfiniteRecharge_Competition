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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  /**
   * Creates a new Storage.
   */
  private VictorSPX mainStorageMotor;
  private TalonFX storage2;
  private DigitalInput frontBallDetect; //3
  private DigitalInput secondBallDetect; //2
  
  public Storage() {
    storage2 = new TalonFX(Constants.STORAGE2_MOTOR);
    mainStorageMotor = new VictorSPX(Constants.MAIN_STORAGE_MOTOR);
    frontBallDetect = new DigitalInput(3);
    secondBallDetect = new DigitalInput(2);
    mainStorageMotor.set(ControlMode.PercentOutput, 0);
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
  public void ballDetect(){
  //  System.out.println("front sensor: " + frontBallDetect.get());
  //  System.out.println("second sensor: " + secondBallDetect.get());
  
  }

  public void runStorage(){
    boolean ballHasEntered = false;
    boolean passedSecond = false;
    boolean passedThird = false;
    boolean passedTurret = false;
    boolean runStorage = false;

    boolean frontPassedSecondSensor = false;
    if(frontBallDetect.get() == true){ //front detects ball
        ballHasEntered = true;
    }
    if(secondBallDetect.get() == true){
      passedSecond = true;
    }
    // if(ballHasEntered){
    //   //runStorage = true;
    //   if(passedTurret == false && passedThird == false && passedSecond == false){
    //     if(passedTurret == false){
    //       runStorage = true;
    //     }else{
    //       runStorage = false;
    //     }
    //   }
    // }else{
    //   runStorage = false;
    // }
    if(ballHasEntered){
      runStorage = true;
      
    }
    if(passedSecond == true){
      frontPassedSecondSensor = true;
      
    }
   
    if(frontPassedSecondSensor){
      runStorage = true;
      System.out.println("second sensor : " + passedSecond);
      if(passedSecond = false){
        runStorage = false;
      }
    }
    if(runStorage){
     mainStorageMotor.set(ControlMode.PercentOutput, -.5);
    }else{
      mainStorageMotor.set(ControlMode.PercentOutput, 0);
    }
  }
}
    // if(ballHasEntered){
    //   if(secondBallDetect.get() == false){
    //     System.out.println("storage on");
    //   }else{
    //     passedSecond = true;
    //     ba
    //     System.out.println("second detects Ball");
    //   }
    // }
  

/*
for first ball:
  - check if first sensor detects ball
  - check if turrent, third and second sensor detect ball
  - keep going until third sensor detects ball
  - if third sensor detects ball and turret sensor doesnt detect ball,
       run turret motor until it detects ball
for second ball:
  - check if first sensor detects ball
  - check if second and third sensor are on
  - keep going until third sensor is off
for third ball:]
  - check if first sensor detects ball
  - check if second sensor detects ball
  - keep going until second sensor detects ball
for fourth ball:
  - check if first sensor detects ball
  - keep going until first sensor detects ball for x counts/ ideally until first sensor no longer detects a ball
      - only the case if turret, third and second sensors detect a ball
*anytime sensors 1-3 dont detect a ball, run storage until turret detects a ball
  - intake ball
  - if two balls are already present, dont run storage motor after x counts
  */