package com.acrobotics.v1.Simplify;

import com.acrobotics.v1.Exception.SimpleException.SimpleMotorTypeInitError;
import com.acrobotics.v1.Exception.SimpleException.SimplePowerMotorSetPowerTypeMisMatch;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Objects;

/**
 * Simple power woiuld make servos and motors simplier by making one variable to do servos and motors as a whole as l
 * long as it had a encoder its the same idea
 *
 * Could be difficult but its possible
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 * @// TODO: 3/25/2026  Need to look into if we can have the config device name one thing with a servo and then hardeare map it as cr servo
 * would be much easier if so
 */
public final class SimplePower {


    /**
     * The Class for the combo of the motor and servos
     */
    public class Motor{
        private DcMotorEx motor;
        private CRServo crservo;
        private Servo servo;



/// NOTE You cannot use enums within  a inner class that are nonStatic Intill JDK 16+ FTC does not use that
//        public enum TYPE {MOTOR, SERVO, CR_SERVO};
        private final MotorType motorType;

        /**
         * Const
         * @param type Either MOTOR, SERVO, CR_SERVO
         */
        public Motor(MotorType type) {
            motorType = type;
        }

        /**
         * Gets the type
         * @return TYPE insrtance
         */
        public MotorType getType(){
            return motorType;
        }

        /**
         * Sets power for the motor
         *
         *
         * @throws SimplePowerMotorSetPowerTypeMisMatch If you ask  a Servo to set power as they are psotiion only
         *
         * @param power the power
         */
        public void setPower(double power){
            if(Objects.equals(motorType.getType(), "SERVO")){
                ///  THis would state we have a normal servo power doesnt work here!
                throw new SimplePowerMotorSetPowerTypeMisMatch("You cannot set power to a position only motor type!");
            }else{
                if(Objects.equals(motorType.getType(), "MOTOR")){
                    /// Motor
                    motor.setPower(power);
                }else{
                    /// CrServo
                    crservo.setPower(power);
                }
            }
        }


        /**
         * This sets the power of the Motor
         *
         * @throws SimplePowerMotorSetPowerTypeMisMatch if you ask a servo to set power as they are psoiton only
         *
         * @param power the Float of the power
         */
        public void setPower(float power){
            if(Objects.equals(motorType.getType(), "SERVO")){
                ///  THis would state we have a normal servo power doesnt work here!
                throw new SimplePowerMotorSetPowerTypeMisMatch("You cannot set power to a position only motor type!");
            }else{
                if(Objects.equals(motorType.getType(), "MOTOR")){
                    /// Motor
                    motor.setPower(power);
                }else{
                    /// CrServo
                    crservo.setPower(power);
                }
            }
        }



        /**
         * Sets position of a motor
         *
         * @param position the Float of the positoon
         */
        public void setPosition(float position){
            /// TODO Because we must use encoder magic to determine position
        }


        /**
         * Sets position of a motor
         *
         * @param position the Double of the positoon
         */
        public void setPosition(double position){
            /// TODO Because we must use encoder magic to determine position
        }




    }
    public class MotorType{
        private final String type;

        public MotorType(String type){
            if(Objects.equals(type, "SERVO") || Objects.equals(type, "MOTOR") || Objects.equals(type, "CR_SERVO")){
                this.type = type;
            }else{
                throw new SimpleMotorTypeInitError("Hey you didnt set the Type for the motor type correctly Must be type SERVO, MOTOR, CR_SERVO ");
            }
        }
        public String getType(){
            return type;
        }
    }
}
