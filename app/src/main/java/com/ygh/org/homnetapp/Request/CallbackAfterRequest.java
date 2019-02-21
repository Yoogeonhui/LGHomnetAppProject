package com.ygh.org.homnetapp.Request;

import android.util.Pair;

public interface CallbackAfterRequest {
    public void OnResponse(Pair<Boolean, String> result);
}
