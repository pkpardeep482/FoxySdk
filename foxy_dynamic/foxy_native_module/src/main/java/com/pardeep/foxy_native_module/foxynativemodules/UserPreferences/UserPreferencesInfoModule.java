package com.pardeep.foxy_native_module.foxynativemodules.UserPreferences;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Calendar;

import javax.annotation.Nonnull;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.util.Log;

public class UserPreferencesInfoModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactApplicationContext;
    private Context audioContext;

    public UserPreferencesInfoModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
        UserPreferences.initPref(reactContext);
        this.audioContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "UserPreferences";
    }

    @ReactMethod
    public void saveAuthToken(String authToken) {
        UserPreferences.setUserAuthToken(authToken);
    }

    @ReactMethod
    public void saveUserId(String id) {
        UserPreferences.setUserId(id);
    }

    @ReactMethod
    public void saveGuestToken(String guestToken) {
        UserPreferences.setUserGuestToken(guestToken);
    }

    @ReactMethod
    public void resetUserPreferences() {
        UserPreferences.resetPrefs();
    }

    @ReactMethod
    public void saveCartItems(String cartItems) { UserPreferences.setCartItems(cartItems); }

    @ReactMethod
    public void saveCartItemsCount(String cartItemsCount) {
        UserPreferences.setNumberOfCartItems(cartItemsCount);
    }

    @ReactMethod
    public void updateCartUpdatedAt() {
        Calendar timestamp = Calendar.getInstance();
        UserPreferences.setCartUpdatedAt(timestamp.getTimeInMillis());
    }

    @ReactMethod
    public void saveNotificationRemoteConfigValues(String subject, String body, String sticky, String expire_in, String primary_cta, String primary_destination, String secondary_cta, String secondary_destination, String channel) {
        UserPreferences.notificationRemoteValues(subject, body, sticky, expire_in, primary_cta, primary_destination, secondary_cta, secondary_destination, channel);
    }

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // mediaPlayer.pause();
                // mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // mediaPlayer.release();
            }
        }
    };

    @ReactMethod
    public void getAudioFocus() {
        // initializing variables for audio focus and playback management
        AudioManager audioManager = (AudioManager) audioContext.getSystemService(Context.AUDIO_SERVICE);
        AudioAttributes playbackAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();
        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(playbackAttributes)
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .build();
        final Object focusLock = new Object();

        boolean playbackDelayed = false;
        boolean playbackNowAuthorized = false;

        // requesting audio focus and processing the response
        int res = audioManager.requestAudioFocus(focusRequest);
        synchronized(focusLock) {
            if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                // playbackNowAuthorized = false;
            } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // playbackNowAuthorized = true;
                // playbackNow();
            } else if (res == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
                // playbackDelayed = true;
                // playbackNowAuthorized = false;
            }
        }
    }

    @ReactMethod
    public void saveStoryNotificationStrings(String json) {
        Log.d("CACHE_MANAGER", "notification strings: "+json);
        UserPreferences.initPref(reactApplicationContext);
        UserPreferences.saveStoryNotificationData(json);
    }

    @ReactMethod
    public void saveCartData(String json) {
        UserPreferences.initPref(reactApplicationContext);
        UserPreferences.saveCartData(json);
    }

    /** This function was made to solve the problem of multiple asynchronous bridge calls after saving the cart data.
     * Using this function, the next bridge call will only be executed once the promise is resolved. */
    @ReactMethod
    public void saveCartDataWithPromise(String stringifiedJson, Promise promise) {
        saveCartData(stringifiedJson);
        //the resolution of this promise should always be boolean
        promise.resolve(true);
    }
}
