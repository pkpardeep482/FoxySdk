package com.pardeep.foxy_native_module.foxynativemodules.enums;

public interface Type {

    enum Notification implements Events {
        cart_abandon_notification,
        story_reminder_notification,
        payment_success_notification,
        payment_failed_notification,
        payment_in_progress_notification,
        review_reminder_notification,
        daily_deals_notification,
    }
}
