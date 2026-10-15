//His name is Daquarias Jeremiad II, may respect be laid upon his name, and that of his namer, SamJ
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// these last 3 are only important for getting the telemetry data into its own new file


//Joint 1 servo position list: 0 Right, .25 flat, .5 up, .75 flat, 1 left
//Joint 2 servo position list: 0 Left, .25 flat, .5 up, .75 flat, 1 Right
//Joint 3 servo position list: 0 Closed, 1 Open

@TeleOp
public class GamepadControl extends OpMode {
    private int motorPos;

    private Servo jointOneServo;
    private Servo jointTwoServo;
    private Servo jointThreeServo;
    //creates servo classes
    public DcMotorEx spinMotor;
    //creates motor classes

    File savePositionFile = AppUtil.getInstance().getSettingsFile("robot_data.txt");

    private String j1Pos = "0.0";
    private String j2Pos = "0.0";
    private String j3Pos = "0.0";

    private boolean wasAPressed = false; // for debounce

    public void spinMotorToPlace(int necessaryTicks){
        spinMotor.setTargetPosition(necessaryTicks);
        spinMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinMotor.setPower(0.3);
        while (spinMotor.isBusy()){

        }
        spinMotor.setPower(0.0);
    }//spins the motor to the input value

    @Override
    public void init(){
        jointOneServo = hardwareMap.get(Servo.class, "j1");
        jointTwoServo = hardwareMap.get(Servo.class, "j2");
        jointThreeServo = hardwareMap.get(Servo.class, "j3");
        spinMotor = hardwareMap.get(DcMotorEx.class, "spin");
        ReadWriteFile.writeFile(savePositionFile, "");
    }

    @Override
    public void start() {
        jointOneServo.setPosition(0.5);
        jointTwoServo.setPosition(0.5);
        jointThreeServo.setPosition(1);
    }    // sets servos to normal


    @Override
    public void loop () {
        if (gamepad1.left_trigger > 0.1) {
            if (jointThreeServo.getPosition() > 0.1){
                jointThreeServo.setPosition(0.0);
            }else if (jointThreeServo.getPosition() < 0.1){
                jointThreeServo.setPosition(1.0);
            }
        }

        if (gamepad1.left_stick_x < 0) {
            jointOneServo.setPosition(jointOneServo.getPosition()+.0005);
        } else if (gamepad1.left_stick_x > 0){
            jointOneServo.setPosition(jointOneServo.getPosition()-.0005);
        }

        if (gamepad1.right_stick_x < 0){
            jointTwoServo.setPosition(jointTwoServo.getPosition()-.0005);
        } else if (gamepad1.right_stick_x > 0){
            jointTwoServo.setPosition(jointTwoServo.getPosition()+.0005);
        }

        if (gamepad1.x){
            if(spinMotor.getCurrentPosition() > -144){
                spinMotorToPlace(-144);
            }
        } else if (gamepad1.b){
            if(spinMotor.getCurrentPosition() < 0){
                spinMotorToPlace(0);
            }
        }

        if (gamepad1.a && !wasAPressed){
            j1Pos = String.valueOf(jointOneServo.getPosition());
            j2Pos = String.valueOf(jointTwoServo.getPosition());
            j3Pos = String.valueOf(jointThreeServo.getPosition());
            String dataToSave1 = ("jointOneServo.setPosition(" + j1Pos + ");");
            String dataToSave2 = ("jointTwoServo.setPosition(" + j2Pos + ");");
            String dataToSave3 = ("jointThreeServo.setPosition(" + j3Pos + ");");

            try {
                FileWriter fw = new FileWriter(savePositionFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(dataToSave1 + "\n");
                bw.close();

                telemetry.addData("Status", "Position appended successfully!");
            } catch (IOException e) {
                telemetry.addData("Status", "Error writing to file: " + e.getMessage());
            }

            try {
                FileWriter fw = new FileWriter(savePositionFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(dataToSave2 + "\n");
                bw.close();

                telemetry.addData("Status", "Position appended successfully!");
            } catch (IOException e) {
                telemetry.addData("Status", "Error writing to file: " + e.getMessage());
            }

            try {
                FileWriter fw = new FileWriter(savePositionFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(dataToSave3 + "\n");
                bw.close();

                telemetry.addData("Status", "Position appended successfully!");
            } catch (IOException e) {
                telemetry.addData("Status", "Error writing to file: " + e.getMessage());
            }

            try {
                FileWriter fw = new FileWriter(savePositionFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("wait(100);" + "\n");
                bw.close();

                telemetry.addData("Status", "Position appended successfully!");
            } catch (IOException e) {
                telemetry.addData("Status", "Error writing to file: " + e.getMessage());
            }
        }
        wasAPressed = gamepad1.a; //for debounce


        telemetry.addData("j1: ", jointOneServo.getPosition());
        telemetry.addData("j2: ", jointTwoServo.getPosition());
        telemetry.addData("j3: ", jointThreeServo.getPosition());
        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.update();
    }
}


