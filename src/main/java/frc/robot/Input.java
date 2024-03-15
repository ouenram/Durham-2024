package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {

    // Controllers
    private static XboxController dController = new XboxController(0);
    private static XboxController oController = new XboxController(1);
    public static XboxController Driver(){ return dController; } 
    public static XboxController Operator(){ return oController; }

    
    // 0 => Robot has no gamepiece
    // 1 -> Robot has gamepiece
    public static int input_mode = 0;
    
    // For drivetrain only
    public static double SpeedLimit(){ return 1; } 


    /* DRIVER CONTROLS */
    public static double DriveSpeed(){ return dController.getLeftY() * SpeedLimit(); } 
    public static double DriveRot(){ return dController.getRightX(); } 
    public static double Decelerate() {return dController.getLeftTriggerAxis(); }
    
    /* OPERATOR CONTROLS */

    // Manual Intake Controls
    public static int ManualIntake() {return oController.getPOV(180); }
    public static int ManualOuttake() {return oController.getPOV(0); }

    public static boolean IntakeBind() {return oController.getLeftBumper(); }
    public static boolean Shoot() {return oController.getRightBumper(); }
    
    // Manual Controls for pivots
    public static double ManualIntakePivot(){ return oController.getRightY(); } 
    public static double ClimberController(){ return ((oController.getLeftTriggerAxis() * 1) + (oController.getRightTriggerAxis() * -1)); } 
    
}
