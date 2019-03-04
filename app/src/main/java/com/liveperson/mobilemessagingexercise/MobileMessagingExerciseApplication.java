package com.liveperson.mobilemessagingexercise;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.liveperson.api.LivePersonIntents;
import com.liveperson.mobilemessagingexercise.model.ApplicationStorage;
import com.liveperson.mobilemessagingexercise.receivers.LivePersonBroadcastReceiver;
import com.liveperson.mobilemessagingexercise.services.LpFirebaseMessagingService;

/******************************************************************
 * The main application class for the Mobile Messaging Exercise
 *****************************************************************/
public class MobileMessagingExerciseApplication extends Application {

    private static final String TAG = MobileMessagingExerciseApplication.class.getSimpleName();

    private static boolean showToastOnCallback = true;
    private ApplicationStorage applicationStorage;
    private LivePersonBroadcastReceiver livePersonBroadcastReceiver;

    /**
     * Android callback invoked as the application is created
     */
    @Override
    public void onCreate () {
        super.onCreate();
        applicationStorage = ApplicationStorage.getInstance();

        //Register the app to receive events from LivePerson
        registerForLivePersonEvents();
    }

    /**
     * Register to receive events from LivePerson within this application
     */
    private void registerForLivePersonEvents() {
        livePersonBroadcastReceiver = new LivePersonBroadcastReceiver(this);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.registerReceiver(livePersonBroadcastReceiver, LivePersonIntents.getIntentFilterForAllEvents());
    }

    /**
     * Display a pop up toast message
     * @param message the text of the message to be shown
     *
     * If showToastOnCallback is false, the message is logged instead
     */
    public void showToast(String message) {
        if (showToastOnCallback){
            //Show the message as a popup
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        else {
            //Log the message
            Log.d(TAG + "_CALLBACK", message);
        }
    }

}
