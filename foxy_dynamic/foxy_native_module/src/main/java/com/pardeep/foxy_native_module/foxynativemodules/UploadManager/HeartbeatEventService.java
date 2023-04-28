package com.pardeep.foxy_native_module.foxynativemodules.UploadManager;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.bridge.WritableMap;

import javax.annotation.Nullable;

public class HeartbeatEventService extends HeadlessJsTaskService
{
    @Nullable
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        WritableMap data = extras != null ? Arguments.fromBundle(extras) : Arguments.createMap();;
        return new HeadlessJsTaskConfig(
                "Heartbeat",
                data,
                5000,
                true);
    }  
}
