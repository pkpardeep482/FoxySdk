package com.pardeep.foxy_native_module.foxynativemodules.enums;

public interface Events {
    enum Notification implements Events {
        notification_shown,
        notification_action,
        notification_cancelled,
    }
}
