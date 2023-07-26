package com.bango.bangoLive.ZegoServices;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bango.bangoLive.R;
import com.bango.bangoLive.application.App;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.constants.ZegoAudioConfigPreset;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import im.zego.zegoexpress.constants.ZegoUpdateType;
import im.zego.zegoexpress.entity.ZegoAudioConfig;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoStream;
import im.zego.zegoexpress.entity.ZegoUser;

public class AudioCallPageActivity extends AppCompatActivity {
    private String userID;
    private String userName;

    // The value of `roomID` is generated locally and must be globally unique.
    // Users must log in to the same room to call each other.
    private String roomID;

    private boolean isHost;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_call_page);
        userID = getIntent().getStringExtra("userID");
        userName = getIntent().getStringExtra("userName");
        roomID = getIntent().getStringExtra("roomID");
        isHost = getIntent().getBooleanExtra("isHost", false);

          ((App) getApplication()).getExpressService().getEngine();

        Log.d("Debug", "UserId:- " + userID + ", userName:- " + userName + ", roomId:- " + roomID);

        startListenEvent();
        loginRoom();
        // Set a listener for the call stopping button.
        // Click to stop a call.
        findViewById(R.id.callEndButton).setOnClickListener(view -> finish());

        findViewById(R.id.callMuteButton).setOnClickListener(view -> {
            findViewById(R.id.callMuteButton).setVisibility(View.GONE);
            findViewById(R.id.callUnMuteButton).setVisibility(View.VISIBLE);
            ZegoExpressEngine.getEngine().mutePublishStreamAudio(true);
           // ((FloatingActionButton) findViewById(R.id.callMuteButton)).setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.stat_sys_vp_phone_call_on_hold));
        });
        findViewById(R.id.callUnMuteButton).setOnClickListener(view -> {
            findViewById(R.id.callMuteButton).setVisibility(View.VISIBLE);
            findViewById(R.id.callUnMuteButton).setVisibility(View.GONE);
            ZegoExpressEngine.getEngine().mutePublishStreamAudio(false);
            //((FloatingActionButton) findViewById(R.id.callUnMuteButton)).setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.stat_sys_vp_phone_call));
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            stopListenEvent();
            logoutRoom();
        }
    }

   void printLog(String msg){
        Log.e("------->>>>",msg);
    }

    // Log in to a room.
    void loginRoom() {
        ZegoUser user = new ZegoUser(userID, userName);
        ZegoRoomConfig roomConfig = new ZegoRoomConfig();
        // The `onRoomUserUpdate` callback can be received only when
        // `ZegoRoomConfig` in which the `isUserStatusNotify` parameter is set to
        // `true` is passed.
        roomConfig.isUserStatusNotify = true;
        ZegoExpressEngine.getEngine().loginRoom(roomID, user, roomConfig, (int error, JSONObject extendedData) -> {
            // Room login result. This callback is sufficient if you only need to
            // check the login result.
            if (error == 0) {
                // Login successful.
                // Start the preview and stream publishing.
                printLog("Login successful.");
                Toast.makeText(this, "Login successful.", Toast.LENGTH_LONG).show();

                startPublish();
            } else {
                printLog("Login not successful.");
                // Login failed. For details, see [Error codes\|_blank](/404).
                Toast.makeText(this, "Login failed. error = " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    void logoutRoom() {
        ZegoExpressEngine.getEngine().logoutRoom(roomID);
    }


    void startPublish() {
        // After calling the `loginRoom` method, call this method to publish streams.
        // The StreamID must be unique in the room.
        ZegoAudioConfig audioConfig = new ZegoAudioConfig(ZegoAudioConfigPreset.HIGH_QUALITY_STEREO);
        ZegoExpressEngine.getEngine().setAudioConfig(audioConfig);

        String streamID = roomID + "_" + userID + "_call";
        Log.d("Debug", "streamID:- " + streamID);
        printLog("publishing start method.");
        ZegoExpressEngine.getEngine().enableCamera(false);
        ZegoExpressEngine.getEngine().mutePublishStreamAudio(false);
        ZegoExpressEngine.getEngine().startPublishingStream(streamID);
    }


    void stopPublish() {
        ZegoExpressEngine.getEngine().stopPublishingStream();
    }


    void startPlayStream(String streamID) {
        ZegoExpressEngine.getEngine().startPlayingStream(streamID);
    }

    void stopPlayStream(String streamID) {
        ZegoExpressEngine.getEngine().stopPlayingStream(streamID);
    }

    void startListenEvent() {
        ZegoExpressEngine.getEngine().setEventHandler(new IZegoEventHandler() {
            @Override
            // Callback for updates on the status of the streams in the room.
            public void onRoomStreamUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoStream> streamList, JSONObject extendedData) {
                super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData);
                // When `updateType` is set to `ZegoUpdateType.ADD`, an audio and video
                // stream is added, and you can call the `startPlayingStream` method to
                // play the stream.
                printLog("Start Listen Event");
                if (updateType == ZegoUpdateType.ADD) {
                    startPlayStream(streamList.get(0).streamID);
                } else {
                    stopPlayStream(streamList.get(0).streamID);
                }
            }

            @Override
            // Callback for updates on the status of other users in the room.
            // Users can only receive callbacks when the isUserStatusNotify property of ZegoRoomConfig is set to `true` when logging in to the room (loginRoom).
            public void onRoomUserUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoUser> userList) {
                super.onRoomUserUpdate(roomID, updateType, userList);
                // You can implement service logic in the callback based on the login
                // and logout status of users.
                if (updateType == ZegoUpdateType.ADD) {
                    for (ZegoUser user : userList) {
                        String text = user.userID + "logged in to the room.";
                        printLog("logged in to the room"+ text);
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }
                } else if (updateType == ZegoUpdateType.DELETE) {
                    printLog("logged in to the room"+ updateType);
                    for (ZegoUser user : userList) {
                        String text = user.userID + "logged out of the room.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            // Callback for updates on the current user's room connection status.
            public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int i, JSONObject jsonObject) {
                super.onRoomStateChanged(roomID, reason, i, jsonObject);
                printLog("onRoomStateChanged"+ reason);
                if (reason == ZegoRoomStateChangedReason.LOGINING) {
                    // Logging in to a room. When `loginRoom` is called to log in to a
                    // room or `switchRoom` is called to switch to another room, the room
                    // enters this status, indicating that it is requesting a connection
                    // to the server. On the app UI, the status of logging in to the room
                    // is displayed.
                } else if (reason == ZegoRoomStateChangedReason.LOGINED) {
                    // Logging in to a room succeeds. When a user successfully logs in to
                    // a room or switches the room, the room enters this status. In this
                    // case, the user can receive notifications of addition or deletion of
                    // other users and their streams in the room. Only after a user
                    // successfully logs in to a room or switches the room,
                    // `startPublishingStream` and `startPlayingStream` can be called to
                    // publish and play streams properly.
                } else if (reason == ZegoRoomStateChangedReason.LOGIN_FAILED) {
                    // Logging in to a room fails. When a user fails to log in to a room
                    // or switch the room due to a reason such as incorrect AppID or
                    // Token, the room enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.LOGIN_FAILED", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.RECONNECTING) {
                    // The room connection is temporarily interrupted. The SDK will retry
                    // internally if the interruption is caused by poor network quality.
                } else if (reason == ZegoRoomStateChangedReason.RECONNECTED) {
                    // Reconnecting a room succeeds. The SDK will retry internally if the
                    // interruption is caused by poor network quality. If the reconnection
                    // is successful, the room enters this status.
                } else if (reason == ZegoRoomStateChangedReason.RECONNECT_FAILED) {
                    // Reconnecting a room fails. The SDK will retry internally if the
                    // interruption is caused by poor network quality. If the reconnection
                    // fails, the room enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.RECONNECT_FAILED", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.KICK_OUT) {
                    // The server forces a user to log out of a room. If a user who has
                    // logged in to room A tries to log in to room B, the server forces
                    // the user to log out of room A and room A enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.KICK_OUT", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.LOGOUT) {
                    // Logging out of a room succeeds. This is the default status of a
                    // room before login. If a user successfully logs out of a room by
                    // calling `logoutRoom` or `switchRoom`, the room enters this status.
                } else if (reason == ZegoRoomStateChangedReason.LOGOUT_FAILED) {
                    // Logging out of a room fails. If a user fails to log out of a room
                    // by calling `logoutRoom` or `switchRoom`, the room enters this
                    // status.
                }
            }

            // Status notification of audio and video stream publishing.
            @Override
            public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
                super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
                printLog("onPublisherStateUpdate"+ state);
                if (errorCode != 0) {
                    // Stream publishing exception.
                }
                if (state == ZegoPublisherState.PUBLISHING) {
                    // Publishing streams.
                } else if (state == ZegoPublisherState.NO_PUBLISH) {
                    // Streams not published.
                    Toast.makeText(getApplicationContext(), "ZegoPublisherState.NO_PUBLISH", Toast.LENGTH_LONG).show();
                } else if (state == ZegoPublisherState.PUBLISH_REQUESTING) {
                    // Requesting stream publishing.
                }
            }

            // Status notifications of audio and video stream playing.
            // This callback is received when the status of audio and video stream
            // playing of a user changes. If an exception occurs during stream playing
            // due to a network interruption, the SDK automatically retries to play
            // the streams.
            @Override
            public void onPlayerStateUpdate(String streamID, ZegoPlayerState state, int errorCode, JSONObject extendedData) {
                super.onPlayerStateUpdate(streamID, state, errorCode, extendedData);
                printLog("onPlayerStateUpdate"+ state);
                if (errorCode != 0) {
                    // Stream playing exception.
                    Toast.makeText(getApplicationContext(), "onPlayerStateUpdate, state:" + state + "errorCode:" + errorCode, Toast.LENGTH_LONG).show();
                }
                if (state == ZegoPlayerState.PLAYING) {
                    // Playing streams.
                } else if (state == ZegoPlayerState.NO_PLAY) {
                    // Streams not played.
                    Toast.makeText(getApplicationContext(), "ZegoPlayerState.NO_PLAY", Toast.LENGTH_LONG).show();
                } else if (state == ZegoPlayerState.PLAY_REQUESTING) {
                    // Requesting stream playing.
                }
            }
        });
    }

    void stopListenEvent() {
        ZegoExpressEngine.getEngine().setEventHandler(null);
    }

}