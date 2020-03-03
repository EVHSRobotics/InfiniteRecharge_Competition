/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.RunStorage;
import frc.robot.commands.ToggleShift;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public final Drivetrain drivetrain;
  public final Vision vision;
  public final Shooter shooter;
  public final Turret turret;
  public final Intake intake;

  public final JoystickDrive joystickDrive;
  

  private XboxController controller;
  private Joystick wheel;
  private Joystick joy;
  private POVButton intakeFWD;
  private POVButton intakeBWD;
  private JoystickButton toggleShift;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain = new Drivetrain();
    joystickDrive = new JoystickDrive(drivetrain);
    vision = new Vision();
    shooter = new Shooter();
    turret = new Turret();
    intake = new Intake();
    

  
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  
    controller = new XboxController(Constants.CONTROLLER);
    wheel = new Joystick(Constants.WHEEL);
    joy = new Joystick(Constants.JOY);
    intakeFWD = new POVButton(controller, 90);
    intakeBWD = new POVButton(controller, 180);
    toggleShift = new JoystickButton(controller, Constants.TOGGLE_SHIFT);

    intakeFWD.whileHeld(new RunStorage(true, false, .5));
    intakeBWD.whileHeld(new RunStorage(false, true, .5));
    toggleShift.whenPressed(new ToggleShift());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public Joystick getJoy() {
    return joy;
  }

  public Joystick getWheel() {
    return wheel;
  }

  public XboxController getController(){
    return controller;
  }
}
