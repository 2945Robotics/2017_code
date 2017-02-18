 package org.usfirst.frc.team2945.robot;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {
	//initialize the drive motors
	CANTalon _frontLeftMotor = new CANTalon(3);
	CANTalon _rearLeftMotor = new CANTalon(2);
	CANTalon _frontRightMotor = new CANTalon(0);
	CANTalon _rearRightMotor = new CANTalon(1);
	//set climb motor and useless motor for climbing
	CANTalon _climbMotor = new CANTalon(4);
	CANTalon _climbMotor2 = new CANTalon(9);
	
	/*CANTalon _intakeMotor = new CANTalon(5);
	CANTalon _shootMotor = new CANTalon(6);
	CANTalon _leftRaiseMotor = new CANTalon(7);
	CANTalon _rightRaiseMotor = new CANTalon(8);*/
	
	//initialize the camera server
	CameraServer _server = CameraServer.getInstance();
	//initialize the gear placement servo
	Servo _exampleServo = new Servo(1); 
	//initialize the time for autonomous
	Timer _timer = new Timer();
	//initialize the drive using the drive motors
	RobotDrive _drive = new RobotDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
	//initialize the drive for rope climbing
	RobotDrive _drive2 = new RobotDrive(_climbMotor,_climbMotor2);
	//initialize driving joystick
	Joystick _joy = new Joystick(0);
	//initialize the module joystick
	Joystick _joy2 = new Joystick(1);
	//initialize the gyro
    AnalogGyro _gyro = new AnalogGyro(1);
    
    /*double idirection = 1.0;
    double sdirection = 1.0;
    double axis7 = 0;*/
    
    //function that is called when the robot starts
    public void robotInit() {
    	
    	/*_drive.setInvertedMotor(MotorType.kFrontRight, true);
    	_drive.setInvertedMotor(MotorType.kRearRight, true);
    	_drive.setInvertedMotor(MotorType.kFrontLeft, true);
    	_drive.setInvertedMotor(MotorType.kRearLeft, true);*/
    	
    	//obtain the image from the usb camera
    	_server.startAutomaticCapture();  	
    }
    //function that is called repeatedly throughout teleop
    public void teleopPeriodic() {
    	/*double forward = -_joy.getRawAxis(1);
    	double turn = -_joy.getRawAxis(2);*/
    	
    	//use joystick input to drive the robot 
    	_drive.arcadeDrive(-_joy.getRawAxis(1), -_joy.getRawAxis(2));
    	//use gamepad input to control the climb motor
    	if ((_joy2.getRawAxis(1) > 0.01) || (_joy2.getRawAxis(1) < -0.01)) {
    		_drive2.drive(_joy2.getRawAxis(1), 0.0);
    	}
    	//use the gamepad to run the servo that drops the gear 
    	if(_joy2.getRawAxis(3) == 1) {
    		_exampleServo.set(.75);
    	} else {
    		_exampleServo.set(.1);
    	}
    	
    	/*if (_joy.getRawButton(2)) {
    		idirection = -idirection;
    	}
    	if (_joy.getRawButton(1)) {
    		_intakeMotor.set(1.0*idirection);
    	}
    	if (_joy2.getRawButton(1)) {
    		sdirection = -sdirection;
    	}
    	if (_joy2.getRawAxis(2) == 1) {
    		_shootMotor.set(1.0*sdirection);
    	}
    	if (_joy2.getPOV() == 0) {
    		axis7 = 1;
    	} else if (_joy2.getPOV() == 180) {
    		axis7 = -1;
    	} else {
    		axis7 = 0;
    	}
    	_leftRaiseMotor.set(axis7);
    	_rightRaiseMotor.set(_joy2.getRawAxis(5));*/
    	
    }
    //function called once at the beginning of autonomous
    public void autonomousInit() {
    	//reset timer to 0
        _timer.reset();
        //start the timer
        _timer.start();
        //reset the gyro to 0
 	    _gyro.reset();
   }
   //function that is called repeatedly during autonomous
   public void autonomousPeriodic() {
	   //read the value from the dashboard
	   double dashData = SmartDashboard.getNumber("DB/Slider 0", 0.0);
	   //read the angle from the gyro
	   double angle = _gyro.getAngle();
	   //change the autonomous based on the dashboard value
	   	if (dashData == 0.0) {
	   		//for the first 1.5 seconds drive forward with gyro for correction
	   		if (_timer.get() < 1.5) {
	   			if (angle < -1) {
	   				_drive.tankDrive(0.75, 0.7);
	   			} else if (angle > 1) {
	   				_drive.tankDrive(0.7, 0.75);
	   			} else {
	   				_drive.tankDrive(0.7, 0.7);
	   			}
	   		}
	   	} else if (dashData == 1.0) {
	   		//if the dashboard data is one 
	   		if (_timer.get() < 2.0) {
	   			_drive.drive(-0.5, 0.5);
	   		}
	   	}
   }
}