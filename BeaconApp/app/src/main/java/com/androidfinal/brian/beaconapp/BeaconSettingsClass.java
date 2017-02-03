package com.androidfinal.brian.beaconapp;

import java.security.PublicKey;

/**
 * Created by Brian on 6/11/2016.
 This class is used to display the settings and properties of a beacon within a listView.
 The purpose for this class is to pass a header and a value in a single class so that class can
 then be passed to a ListView adapter. Since there are more than one properties for a beacon, this
 makes passing the two values easier.
 */

public class BeaconSettingsClass {
    private String header;
    private String content;

    public BeaconSettingsClass(String header, String content){
        this.content = content;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }
}
