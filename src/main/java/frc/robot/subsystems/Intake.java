/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private VictorSPX intakeMotor;
  private DigitalInput banner;
  private DigitalInput banner2;
  private boolean ballLoaded;
  boolean isFast;
  String gearState;
  DoubleSolenoid shifter;
  private Value fast = Value.kForward;
  private Value slow = Value.kReverse;
  private Value off = Value.kOff;
  
  public Intake() {
    intakeMotor = new VictorSPX(Constants.INTAKE_MOTOR);
  //shifter = new DoubleSolenoid(Constants.intakeUp, Constants.intakeDown);
    // banner = new DigitalInput(Constants.BANNER_1);
    // banner2 = new DigitalInput(Constants.BANNER_2);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intake(boolean isEjecting){
    // if (!isEjecting) {
    //   if (bannerOutput()) {
    //     ballLoaded = true;
    //   } else if (banner2Output()) {
    //     ballLoaded = false;
    //   }
    //   if (ballLoaded) {
    //     intakeBall(.5);
    //   } else {
    //     intakeBall(0);
    //    }
    // }else{
    //   intakeBall(-1);
    //    }
  }

  public void intakeBall(double speed){
  
    intakeMotor.set(ControlMode.PercentOutput, speed); 
   // System.out.println("Intake motor velocity: " + intakeMotor.getSelectedSensorVelocity());

  }

  public boolean bannerOutput() {
   // return banner.get();
    return false;

  }
  public boolean banner2Output() {
   // return banner2.get();
   return false;
  }

  public void ejectBalls(double speed) {
    intakeMotor.set(ControlMode.PercentOutput, speed);

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



}
