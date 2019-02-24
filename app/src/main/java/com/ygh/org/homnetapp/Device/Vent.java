package com.ygh.org.homnetapp.Device;

import android.content.Context;

public class Vent extends Device {

    public Vent(String deviceName, Context activityContext){
        super(activityContext);
        this.deviceName = deviceName;
    }

    public void updateDevice() {

    }

    @Override
    public String getType() {
        return "Vent";
    }
}
