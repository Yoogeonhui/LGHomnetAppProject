package com.ygh.org.homnetapp.Device;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ygh.org.homnetapp.R;

public class Light extends Device{
    private boolean firstShow = true;
    Switch lightSwitch;

    private Context activityContext;

    public Light(String deviceName, Context activityContext){
        super(activityContext);
        this.deviceName = deviceName;
        this.activityContext = activityContext;
        updateDevice();
    }

    public void updateDevice(){
        super.updateDevice(map->{
            boolean isOn = map.get("power").equals("100");
            if(firstShow){
                firstShow = false;

                LinearLayout rootElement = ((Activity)activityContext).findViewById(R.id.Controls);
                lightSwitch = new Switch(activityContext);
                lightSwitch.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                linearLayout.addView(lightSwitch);
                rootElement.addView(cardView);

                String currentText = "전등"+deviceName.substring(deviceName.length()-2);
                textView.setText(currentText);
            }
            lightSwitch.setChecked(isOn);
        });
    }

    @Override
    public String getType() {
        return "Light";
    }
}
