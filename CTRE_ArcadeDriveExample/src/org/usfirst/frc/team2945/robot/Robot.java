package org.usfirst.frc.team2945.robot;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon.TalonControlMode;
public class Robot extends IterativeRobot {

	// talons for arcade drive
	CANTalon _frontLeftMotor = new CANTalon(3); 
	CANTalon _rearLeftMotor = new CANTalon(2);
	CANTalon _frontRightMotor = new CANTalon(0);
	CANTalon _rearRightMotor = new CANTalon(1);
	//initialize climbing motor
	CANTalon _climbMotor = new CANTalon(4);
	// waste of space definition
	CANTalon _climbMotor2 = new CANTalon(5);
	//camera 1
	edu.wpi.first.wpilibj.CameraServer _server ;
	//gear Servo
	Servo _exampleServo; 
	//Timer for autonomous
	Timer _timer = new Timer();
	//set arcade drive
	RobotDrive _drive = new RobotDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
	//set rope climb robot drive 
	RobotDrive _drive2 = new RobotDrive(_climbMotor,_climbMotor2);
	//Drive Joystick
	Joystick _joy = new Joystick(0);
	//Manipulator Joystick
	Joystick _joy2 = new Joystick(1);
	//gyro
    AnalogGyro _gyro = new AnalogGyro(1);
    public void robotInit() {
    	//Invert motors to change respective front
    	_drive.setInvertedMotor(MotorType.kFrontRight, true);
    	_drive.setInvertedMotor(MotorType.kRearRight, true);
    	_drive.setInvertedMotor(MotorType.kFrontLeft, true);
    	_drive.setInvertedMotor(MotorType.kRearLeft, true);
    	//initialize Servo in port 1
    	_exampleServo= new Servo(1);
    	// weird camera stuff
    	_server = edu.wpi.first.wpilibj.CameraServer.getInstance();
    	_server.startAutomaticCapture();  	
    }
    public void teleopPeriodic() {
    	double forward = _joy.getRawAxis(1); // logitech gampad left X, positive is forward
    	double turn = _joy.getRawAxis(2); //logitech gampad right X, positive means turn right
    	_drive.arcadeDrive(forward, turn);
    	_drive2.drive(_joy2.getRawAxis(1), 0.0);
    	if(_joy2.getRawAxis(3) == 1)
    	{
    		_exampleServo.set(.75);
    		/*if (_servoAngle == 0){

    			_servoAngle = 90;
    		}
    		else {
    		
    			_servoAngle = 0;
    		}
    		_exampleServo.setAngle(_servoAngle);
    		*/
    	}
    	
    	else
    	{
    		//_exampleServo.setAngle(0);
    		_exampleServo.set(.1);
    	}
   
    	
    }
    public void autonomousInit() { //This method is called once each time the robot enters autonomous mode
        _timer.reset(); // Resets the timer to 0
        _timer.start(); // Start counting
   }

   public void autonomousPeriodic() { //This method is called each time the robot recieves a packet instructing the robot to be in autonomous enabled mode
        // Drive for 2 seconds
        
	   double dashData = SmartDashboard.getNumber("DB/Slider 0", 0.0);
	   _gyro.reset();
	   double angle = _gyro.getAngle();
	   	if (dashData == 0.0) {
	   		if (_timer.get() < 1.5) {
	   			_drive.drive(-0.5, angle);
	   		}
	   	} else if (dashData == 1.0) {
	   		if (_timer.get() < 2.0) {
	   			_drive.drive(-0.5, 0.5);
	   		}
	   	}
			  /* if (_timer.get() < 2.0) {
        	System.out.println("hello johnathan");
        	_frontRightMotor.enableControl();
            
        	//_drive.drive(0.5, 0.0); // drive forwards half speed
        } else {
        	_frontRightMotor.disableControl();
        	_drive.drive(0.0, 0.0); // stop robot
        }*/
   }
}
