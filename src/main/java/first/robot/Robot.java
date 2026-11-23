// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandGamepad;
import org.wpilib.framework.OpModeRobot;
import first.robot.constants.LightConstatns;
import first.robot.mechanisms.LightSubsystem;

public class Robot extends OpModeRobot {


  public Object exampleController;
  public LightSubsystem m_LightSubsystem = new LightSubsystem();
  public Robot() {

    

  }

  @Override
  public void robotPeriodic() {
    Scheduler.getDefault().run();
    m_LightSubsystem.periodic();
    
  }
}
