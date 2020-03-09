/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.TrapezoidMotionProfile;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
  private Drivetrain drive;
  private double speed, heading, distance;
  private double target;
  double initEncoder, initAngle;
  double currentV, commandedSpeed, normalizedSpeed;
  double currPos;
  double kP, kI, kD, kF, turnKP;
  double pTerm, iTerm, dTerm, fTerm, error, diffError, lastError;
  double count, speedCount;

  TrapezoidMotionProfile trap;

  /**
   * Creates a new DriveDistance.
   */
  public DriveDistance(double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = Robot.robotContainer.drivetrain;
    addRequirements(drive);

    target = distance;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    count = 0;
    currentV = 0;
    initEncoder = drive.getAvgEncoder();
    kP = 0.0005;
    kD = 0.0;
    kI = 0.0;
    kF = 1;
    initAngle = drive.getAngle();
    turnKP = 0.015;
    trap = new TrapezoidMotionProfile(3000, 700, target + initEncoder, initEncoder);
    System.out.println("drive distance initialized");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //trap.updateProfile();
    currentV = drive.getAvgSpeed();
    currPos = drive.getAvgEncoder() - initEncoder;
    kP = 0.0005;
    kD = 0.0;
    kI = 0.0;
    kF = 1;

    error = trap.getpRef() - currPos;
    diffError = trap.getvRef() - currentV;
    fTerm = kF * trap.getvRef();

    pTerm = error * kP;
    dTerm = diffError * kD;
    // iTerm = lastError * kI;
    commandedSpeed = pTerm + dTerm + fTerm;
    normalizedSpeed = commandedSpeed / 2200;
    if (normalizedSpeed > .2) {
      normalizedSpeed = .2;
    }
    if (normalizedSpeed < -.2) {
      normalizedSpeed = -.2;
    }
    //drive.arcadeDrive(normalizedSpeed, turnKP * (initAngle - drive.getAngle()));
    System.out.println("Current position: " + currPos);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Reached Distance");
    //drive.arcadeDrive(0, 0);
    trap.end();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(target - currPos) <= 10){
      count++;
    }else{
      count = 0;
    }
    if(Math.abs(currentV) <= .01){
      speedCount++;
    }else{
      speedCount = 0;
    }

    if (count  > 5 || speedCount > 50){
      return true;
    }else{
      return false;
    }
  }
}
