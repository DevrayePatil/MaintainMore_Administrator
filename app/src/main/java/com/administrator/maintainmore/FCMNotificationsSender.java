package com.administrator.maintainmore;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMNotificationsSender {

    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey ="AAAA7LU9Gak:APA91bG9_ZeKdLS55Ft-tyeSkRLNlHLJ2iMk8-M70BuFN3z8XcGCN" +
            "_6LLPkdzddQvgp4W1jkm1sGLY833gCVmrd03C2Spk-AwtZr-bOHjNF6dH-d-3dwigN11Sq-d-yl8Lc-QWwG9pzv";

    String userFCMToken;
    String title;
    String body;
    Context context;
    Activity activity;


    public FCMNotificationsSender(String userFcmToken, String title, String body, Context context, Activity activity) {
        this.userFCMToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.context = context;
        this.activity = activity;
    }

    public void SendNotifications() {

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userFCMToken);
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("title", title);
            notificationObject.put("body", body);
            notificationObject.put("icon", "ic_info"); // enter icon that exists in drawable only

            mainObj.put("notification", notificationObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, response -> {
                // code run is got response
            }, error -> {
                // code run is got error
            }) {
                @Override
                public Map<String, String> getHeaders() {

                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;

                }
            };
            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
