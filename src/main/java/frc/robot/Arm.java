/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Arm {

    /* Positions for the arm:
     * -Getting a hatch
     * -Deploying a hatch (Rocket/Cargo Bay)
     * -Default position
     * -Carrying position?
     * 
     * These values are measured as angles
     * Cant be set in code
     * Do have getters for thes positions
     * Static, dont change
     */

    //TODO - set angles

    double getHatchPos = 0.0;
    double deployHatchPos = 0.0;
    double defaultPos = 0.0;
    double carryingPos = 0.0;

    double currentPos; //Encoder?

    public Arm (){
        
    }

    /*"get"
     * "deploy"
     * "default"
     * "carry"
     */
    public void gotoPos (String pos) {
        pos = pos.toUpperCase();
        
        //sets arm to getting position
        if (pos.equals("GET")){
            this.setAngle(getHatchPos);
        }
        //sets arm to deploying position
        else if (pos.equals("DEPLOY")){
            this.setAngle(deployHatchPos);
        }
        //sets arm to default position
        else if (pos.equals("DEFAULT")){
            this.setAngle(defaultPos);
        }
        //sets arm to carrying postion
        else if (pos.equals("CARRY")){
            this.setAngle(carryingPos);
        }

        else {
            System.out.println("ERROR: Improper position provided");
        }


    }
    public void gotoPos (){
        gotoPos("default");
    }
    
    public void customPos (double angle){
        angle %= 360.0;
        this.setAngle(angle);
    }

    private void setAngle (double angle){
        /* 1. Increase/Decrease motor power
         * 2. PID Loop
         * 3. Stay at position (part of PID Loop)
         */
    }

}