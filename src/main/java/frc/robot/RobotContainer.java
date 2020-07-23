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
import frc.robot.Autonomous.AutoTest;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.RunStorage;
import frc.robot.commands.ShootAllBalls;
import frc.robot.commands.ShootBall;
import frc.robot.commands.StopShooter;
import frc.robot.commands.ToggleShift;
import frc.robot.commands.TurretJoy;
import frc.robot.commands.WinchRun;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
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

  public static Drivetrain drivetrain = new Drivetrain();
  public static Vision vision = new Vision();
  public static Shooter shooter = new Shooter();
  public static Turret turret = new Turret();
  public static Intake intake = new Intake();
  public static Storage storage = new Storage();
  public static Climb climb = new Climb();
  public static AutoTest auto = new AutoTest();

  public static JoystickDrive joystickDrive = new JoystickDrive();
  public static IntakeBall intakeBall = new IntakeBall();
  public static ShootBall shootBall = new ShootBall();
  public static WinchRun winchRun = new WinchRun();
  public static RunStorage runStorage = new RunStorage();
  public static TurretJoy turretJoy = new TurretJoy();

  private XboxController controller;
  private Joystick wheel;
  private Joystick joy;
  private POVButton intakeFWD;
  private POVButton intakeBWD;
  private JoystickButton toggleShift;
  private JoystickButton buttonA, buttonX, buttonY, leftButton;


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  

    

  
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  
    controller = new XboxController(1);
    wheel = new Joystick(Constants.WHEEL);
    joy = new Joystick(0);
    intakeFWD = new POVButton(controller, 90);
    intakeBWD = new POVButton(controller, 180);
    toggleShift = new JoystickButton(joy, Constants.TOGGLE_SHIFT);
    buttonA = new JoystickButton(controller, 1);
    buttonX = new JoystickButton(controller, 3);
    buttonY = new JoystickButton(controller, 4);
    leftButton = new JoystickButton(controller, 5);

    buttonX.whenPressed(new ShootAllBalls());
    buttonY.whenPressed(new ShootBall());
    leftButton.whenPressed(new StopShooter());
   


   
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return auto;
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
