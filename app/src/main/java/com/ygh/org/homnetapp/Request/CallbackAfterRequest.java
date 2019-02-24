package com.ygh.org.homnetapp.Request;

import android.util.Pair;

import java.util.Map;

public interface CallbackAfterRequest {
    public void OnResponse(Pair<Boolean, String> result);
}

