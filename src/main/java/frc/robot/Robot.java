// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Drivetrain 
  private final CANSparkMax leftLeader = new CANSparkMax(RobotMap.LEFT_MOTOR_1, MotorType.kBrushless);
  private final CANSparkMax rightLeader = new CANSparkMax(RobotMap.RIGHT_MOTOR_1, MotorType.kBrushless);
  private final CANSparkMax leftFollower = new CANSparkMax(RobotMap.LEFT_MOTOR_2, MotorType.kBrushless);
  private final CANSparkMax rightFollower = new CANSparkMax(RobotMap.RIGHT_MOTOR_2, MotorType.kBrushless);
  private final DigitalInput intakeLimitSwitch = new DigitalInput(RobotMap.kIntakeLimitSwitch);

  private final RelativeEncoder leftEncoder = leftLeader.getEncoder();
  private final RelativeEncoder rightEncoder = rightLeader.getEncoder();

  private final Pigeon2 pigeon = new Pigeon2(RobotMap.PIGEON_ID);
  DifferentialDrive driveBase = new DifferentialDrive(leftLeader, rightLeader);

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Intake
  private final CANSparkMax intakeMotor = new CANSparkMax(RobotMap.OperatorConstants.kIntakeMotor, MotorType.kBrushless);
  private final CANSparkMax pivotMotor = new CANSparkMax(RobotMap.OperatorConstants.kIntakePivotMotor, MotorType.kBrushless);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftLeader.setInverted(true);
    rightLeader.setInverted(true);
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    // Setting To Defaullt Positions
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
    pigeon.reset();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Drive Inputs
      driveBase.arcadeDrive(Input.DriveSpeed() * (1 - Input.Decelerate()), Input.DriveRot() * (1 - Input.Decelerate()));

    // Intake Inputs
    // Intake
      if (Input.ManualIntake() == 0 || Input.IntakeBind()) {
        intakeMotor.set(0.95);
      } else {
        intakeMotor.set(0);
      }

      // Outtake
      if (Input.ManualOuttake() == 180 || Input.Shoot()) {
        intakeMotor.set(-0.95);
      } 
      else {
        intakeMotor.set(0);
      }

      //Pivot 
      pivotMotor.set(Input.ManualIntakePivot());

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
