package com.bango.bangoLive.ZegoServices.internal.business.call;

import androidx.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

public class CallExtendedData {

    public String userName;
    public int type;
    public static final int VIDEO_CALL = 10000;
    public static final int VOICE_CALL = 10001;

    public static CallExtendedData parse(String extendedData) {
        try {
            JSONObject jsonObject = new JSONObject(extendedData);
            if (jsonObject.has("type")) {
                int type = (int) jsonObject.get("type");
                if (type == VIDEO_CALL || type == VOICE_CALL) {
                    CallExtendedData data = new CallExtendedData();
                    data.type = type;
                    data.userName = jsonObject.getString("user_name");
                    return data;
                }
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public boolean isVideoCall() {
        return type == VIDEO_CALL;
    }

    public boolean isVoiceCall() {
        return type == VOICE_CALL;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            //            jsonObject.put("room_id", roomID);
            jsonObject.put("user_name", userName);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject.toString();
    }
}
