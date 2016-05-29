package com.dji.FPVDemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import dji.sdk.FlightController.DJICompass;
import dji.sdk.FlightController.DJIFlightController;
import dji.sdk.Products.DJIAircraft;

/**
 * Created by ScofieldChang on 16/5/29.
 */
public class HUDDisplay extends Thread {

    private int PITCH = 10;
    private int ROLL = 11;
    private int YAW = 12;
    private int VELOCITY_X = 20;
    private int VELOCITY_Y = 21;
    private int VELOCITY_Z = 22;
    private int HEADING = 30;
    private int Height = 40;

    private DJIAircraft djiAircraft = null;
    private Handler mHandler = null;
    private DJIFlightController flightController = null;
    private DJICompass djiCompass = null;
    private FileService logfile;

    public void setHandler(Handler mHandler){
        this.mHandler = mHandler;
    }

    public void setDjiAircraft(DJIAircraft djiAircraft){
        this.djiAircraft = djiAircraft;
    }

    public void setLogfile(FileService logfile){
        this.logfile = logfile;
    }

    @Override
    public void run() {
        while(true){
            if(djiAircraft != null){
                flightController = djiAircraft.getFlightController();
                djiCompass = flightController.getCompass();
                double heading = djiCompass.getHeading();
                Message msg = new Message();
                msg.what = HEADING;
                msg.obj = heading;
                long time = System.currentTimeMillis();
                if (logfile != null){
                    logfile.saveLog("time: " + time + " heading: " + heading + '\n');
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
