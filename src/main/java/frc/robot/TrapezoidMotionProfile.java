
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */

public class TrapezoidMotionProfile {
   
   
    double vRef, pRef, trapTarget;
    double vMax;
    double aRef, aMax;
    double pStop, pTarget;
    double initPos;
    boolean isInverted;
    enum trapState {
        accState, constantState, brakeState, done
      };
    
      trapState currState;
      Timer timer;
      double prevTime, currTime, timeElapsed;

    public TrapezoidMotionProfile(double vMax, double aMax, double pTarget, double initPos){
        timer = new Timer();
        timer.start();
        timer.reset();
        prevTime = 0;
    
        pRef = 0;
        vRef = 0;
        this.vMax = vMax;
        this.aMax = aMax;
        this.pTarget = pTarget;
        this.initPos = initPos;
        currState = trapState.accState;

        if(pTarget > initPos){
          isInverted = false;
          trapTarget = pTarget - initPos;
        }else{
          isInverted  = true;
          trapTarget = initPos - pTarget;
        }
        
      
    }
    public void updateProfile() {
        System.out.println("current state: " + currState);
    
        switch (currState) {
        case accState:
          aRef = aMax;
          break;
        case constantState:
          aRef = 0;
          break;
        case brakeState:
          aRef = -aMax;
          break;
        case done:
          aRef = 0;
          pRef = trapTarget;
          vRef = 0;
          return;
          
        }
       
        //currTime = timer.get();
        //System.out.println("currTime: " + currTime);
        //System.out.println("prevTime: " + prevTime);
        timeElapsed = 0.02;//currTime - prevTime;
        //prevTime = currTime;
     
    
        vRef += aRef * timeElapsed;
        pRef += vRef * timeElapsed;
    
        pStop = trapTarget - (.5 * vRef * vRef) / aMax;
    
        if (currState == trapState.accState && vRef > vMax) {
          currState = trapState.constantState;
          vRef = vMax;
        }
        System.out.println("vref " + vRef);
        if (currState != trapState.brakeState && pRef > pStop) {
          currState = trapState.brakeState;
        }
    
        if (vRef <= 0 || pRef > trapTarget) {
          System.out.println("pref :  " + pRef);
          System.out.println("trapTarget: " + trapTarget);
          vRef = 0;
          pRef = trapTarget;
          currState = trapState.done;
        }
    
         System.out.println("time elapsed: " + timeElapsed);
       
        
        // System.out.println("aRef: " + aRef);
        
      } 
      public void end(){
        pRef = 0;
        vRef = 0;
        vMax = 100;
        aMax = 100;
        currState = trapState.accState;
      }

    public double getvRef() {
      if(isInverted)
        return -vRef;
      return vRef;
    }

    public void setvRef(double vRef) {
        this.vRef = vRef;
    }

    public double getpRef() {
      if(isInverted)
        return initPos - pRef;
      return initPos + pRef;
    }

    public void setpRef(double pRef) {
        this.pRef = pRef;
    }

    public double getpTarget() {
        return pTarget;
    }

    public void setpTarget(double pTarget) {
        this.pTarget = pTarget;
    }
      
}