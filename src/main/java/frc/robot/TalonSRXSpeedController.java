/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Add your docs here.
 */
public class TalonSRXSpeedController implements SpeedController {
    private TalonSRX talonSRX, talonSRX2, talonSRX3;
    private double speed;
    private boolean isInverted;
    public TalonSRXSpeedController(int channel){
        talonSRX = new TalonSRX(channel);
        speed = 0;
        isInverted = false;
        

    }

    public TalonSRXSpeedController(int channel1, int channel2, int channel3){
        talonSRX = new TalonSRX(channel1);
        talonSRX2 = new TalonSRX(channel2);
        talonSRX3 = new TalonSRX(channel3);
        talonSRX2.follow(talonSRX);
        talonSRX3.follow(talonSRX);
        speed = 0;
        isInverted = false;
        

    }

    @Override
    public void pidWrite(double output) {
        // TODO Auto-generated method stub

    }

    @Override
    public void set(double speed) {
        // TODO Auto-generated method stub
        if(speed > 1){
            speed = 1;
        }else if(speed < -1){
            speed = -1;
        }
        this.speed = speed;

        talonSRX.set(ControlMode.Velocity, speed*22000);

    }

    @Override
    public double get() {
        // TODO Auto-generated method stub
        return this.speed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        // TODO Auto-generated method stub
        this.isInverted = isInverted;
        talonSRX.setInverted(isInverted);

    }

    @Override
    public boolean getInverted() {
        // TODO Auto-generated method stub
        return this.isInverted;
    }


    public int getEncoderTicks(){
        return talonSRX.getSelectedSensorPosition();
    }

    public void resetEncoder(){
        talonSRX.setSelectedSensorPosition(0);
    }
    @Override
    public void disable() {
        // TODO Auto-generated method stub
        set(0);

    }

    @Override
    public void stopMotor() {
        // TODO Auto-generated method stub
        set(0);

    }
}
