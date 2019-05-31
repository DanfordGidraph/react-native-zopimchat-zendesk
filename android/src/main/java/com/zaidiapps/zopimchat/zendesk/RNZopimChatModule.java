package com.zaidiapps.zopimchat.zendesk;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.zendesk.sdk.model.push.PushRegistrationResponse;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

public class RNZopimChatModule extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;

    public RNZopimChatModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNZopimChatModule";
    }

    public void registerDeviceForPushNotifications(String fcm_token){
        final String TAG = "RNZopimChatModule";
        ZendeskConfig.INSTANCE.enablePushWithIdentifier(fcm_token, new ZendeskCallback<PushRegistrationResponse>() {
            @Override
            public void onSuccess(PushRegistrationResponse pushRegistrationResponse) {
                Log.d(TAG, "onSuccess: pushRegistrationResponse");
            }
            @Override
            public void onError(ErrorResponse errorResponse) {
                Log.d(TAG, "onError: ",(Throwable) errorResponse);
            }
        });

    }



    @ReactMethod
    public void setVisitorInfo(ReadableMap options) {
        VisitorInfo.Builder builder = new VisitorInfo.Builder();

        if (options.hasKey("name")) {
            builder.name(options.getString("name"));
        }
        if (options.hasKey("email")) {
            builder.email(options.getString("email"));
        }
        if (options.hasKey("phone")) {
            builder.phoneNumber(options.getString("phone"));
        }

        VisitorInfo visitorData = builder.build();

        ZopimChat.setVisitorInfo(visitorData);
    }

    @ReactMethod
    public void init(String key) {
        ZopimChat.init(key);
    }
	
	@ReactMethod
	public void setPushToken(String token){
        registerDeviceForPushNotifications(token);
	}

    @ReactMethod
    public void startChat(ReadableMap options) {
        setVisitorInfo(options);
        Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.startActivity(new Intent(mReactContext, ZopimChatActivity.class));
        }
    }
}
