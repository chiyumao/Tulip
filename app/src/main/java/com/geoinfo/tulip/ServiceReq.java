package com.geoinfo.tulip;

import android.location.Location;
import android.provider.Settings;
import android.widget.TextView;

/**
 * Created by mao on 1/22/17.
 */

public class ServiceReq {
    String android_id;
    String userName;
    String info;
    //InfoType infoT;
    String serverIP;
    int portID;
    String clientReq;
    String serverResp;
    boolean serverSuccess;
    Location clientLoc;
    TextView response;

    ServiceReq (MainActivity act, TextView txtv) {
        //serverIP = "98.248.60.152";
        serverIP = "192.168.1.13";
        portID = 5168;
        clientReq = "";
        serverResp = "";
        serverSuccess = false;
        android_id = Settings.Secure.getString(act.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        userName = "";
        info = "";
        //infoT = InfoType.OTHER;
        response = txtv;
    }

    void setServerAddress (String ip, int pid) {
        serverIP = ip;
        portID = pid;
    }

    boolean registerUser () {
        clientReq = "R " + android_id + " N " + userName + "\0";
        return send();
    }

    boolean connectServer () {
        clientReq = "C " + android_id + "\0";
        return send();
    }

    boolean disconnectServer () {
        clientReq = "D " + android_id + "\0";
        return send();
    }

    void reportLocation (Location newLoc) {
        clientReq = "L " + android_id + " " + newLoc.getLatitude() + " : " + newLoc.getLongitude() + "\0";
        send();
    }

    boolean postInfo () {
        //clientReq = "I " + android_id + " T " + infoT + " " + info + "\0";
        clientReq = "I " + android_id + " " + info + "\0";
        return send();
    }

    boolean send () {
        Client myClient = new Client(serverIP, portID, this);
        myClient.execute();
        System.out.println (serverResp);
        response.setText(serverResp);
        return serverSuccess;
    }
}
