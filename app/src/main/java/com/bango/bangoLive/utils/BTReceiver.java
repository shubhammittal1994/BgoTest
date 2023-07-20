package com.bango.bangoLive.utils;

import static android.content.Context.AUDIO_SERVICE;

import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BTReceiver extends BroadcastReceiver {

    private static final String TAG = "BTReceiver";
    int state = 0;
    public static AudioManager audioManager;
    boolean  isHeadphoneConnected;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Z", "Received: Bluetooth");
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) { //Do something
                audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
                String action = intent.getAction();
                Toast.makeText(context, action, Toast.LENGTH_LONG).show();
                int state;
                if (action.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                    state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE,
                            BluetoothHeadset.STATE_DISCONNECTED);
                    Log.d(TAG, "\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$
                    if (state == BluetoothHeadset.STATE_CONNECTED) {
                        setModeBluetooth();
                    }

                    else if (state == BluetoothHeadset.STATE_DISCONNECTED) {
                        // Calling stopVoiceRecognition always returns false here
                        // as it should since the headset is no longer connected.
                        setModeNormal();
                        Log.d(TAG, "Headset disconnected"); //$NON-NLS-1$
                    }
                } else // audio
                     {
                    state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_AUDIO_DISCONNECTED);
                    Log.d(TAG, "\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$
                    if (state == BluetoothHeadset.STATE_AUDIO_CONNECTED) {
                        Log.d(TAG, "\nHeadset audio connected");  //$NON-NLS-1$
                        setModeBluetooth();
                    } else if (state == BluetoothHeadset.STATE_AUDIO_DISCONNECTED) {
                        setModeNormal();
//                        audioManager.setMode(AudioManager.MODE_NORMAL);
//                        audioManager.stopBluetoothSco();
//                        audioManager.setBluetoothScoOn(false);
//                        audioManager.setSpeakerphoneOn(true);
                        Log.d(TAG, "Headset audio disconnected"); //$NON-NLS-1$
                    }

                         // 3. case - phone speaker


                }
            }
        } catch (Exception e) {
            Log.d("Exception", "Exception " + e.toString());
        }
    }

    public static void setModeBluetooth() {
        try {
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            audioManager.startBluetoothSco();
            audioManager.setBluetoothScoOn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setModeNormal() {
        try {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.stopBluetoothSco();
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}