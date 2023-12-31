package com.bango.bangoLive.ZegoServices;

import android.app.Application;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.entity.ZegoEngineProfile;

public class ExpressServiceOld {

    private ZegoExpressEngine engine;



//    long appID = 1042809895;
//    String isTestEnvironment = "true";
//    String appSign = "f045051f0dcbb111a51a11bf141e5e5056a8d372dbf1a9fd51b2996f874ddd5a";

    public ExpressServiceOld(Application application, long appID, String appSign) {
        initSDK(application, appID, appSign);

    }

    public void initSDK(Application application, long appID, String appSign) {
        if (engine != null) {
            return;
        }
        ZegoEngineProfile profile = new ZegoEngineProfile();
        profile.appID = appID;
        profile.appSign = appSign;
        profile.scenario = ZegoScenario.STANDARD_VOICE_CALL;
        profile.application = application;
        engine = ZegoExpressEngine.createEngine(profile, new IZegoEventHandler() {
        });
    }

    public ZegoExpressEngine getEngine() {
        return engine;
    }

    // destroy engine
    private void destroyEngine() {
        ZegoExpressEngine.destroyEngine(null);
    }

}
