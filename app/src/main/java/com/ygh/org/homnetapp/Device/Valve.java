package com.ygh.org.homnetapp.Device;

import android.content.Context;

public class Valve extends Device {

    public Valve(String deviceName, Context activityContext){
        super(activityContext);
        this.deviceName = deviceName;
    }

    @Override
    public String getType() {
        return "Valve";
    }
}
