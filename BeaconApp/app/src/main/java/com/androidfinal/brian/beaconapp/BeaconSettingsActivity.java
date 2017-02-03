package com.androidfinal.brian.beaconapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.http.Streaming;

import java.util.ArrayList;
import java.util.List;

public class BeaconSettingsActivity extends AppCompatActivity {

    ListView beaconSettingsList;
    ImageView imageView_Beacon;
    String preferenceName;
    Context context;
    Boolean changesMade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = BeaconSettingsActivity.this;
        changesMade = false;

        //Load the Beacon info into the new activity fields using an adapter
        final Beacon beacon = getIntent().getExtras().getParcelable("selectedBeacon");
        preferenceName = getIntent().getExtras().getString("beaconSetting");

        //Set control references
        imageView_Beacon = (ImageView)findViewById(R.id.imageView_Beacon);
        beaconSettingsList = (ListView)findViewById(R.id.listView_BeaconSettings);

        //Set imageView resource
        if(beacon.getMajor() == 65159){
            //Mint beacon
            imageView_Beacon.setImageResource(R.drawable.greenbeacon);
        }
        else if(beacon.getMajor() == 45089){
            //Ice beacon
            imageView_Beacon.setImageResource(R.drawable.bluebeacon);
        }
        else if(beacon.getMajor() == 25338){
            //Blueberry beacon
            imageView_Beacon.setImageResource(R.drawable.purplebeacon);
        }
        else{
            //Unidentified beacon
            imageView_Beacon.setImageResource(R.drawable.greybeacon);
        }

        //Set listView adapter
        BeaconSettingsAdapter adapter = new BeaconSettingsAdapter(context,
                R.layout.beaconsettings_itemrow, GenerateList_BeaconProperties(beacon));
        beaconSettingsList.setAdapter(adapter);

        //Set item click event handler
        beaconSettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view1, int i, long l) {
                if(i == 0){
                    //Load list of apps
                    final String items[] = {
                            "Spotify", "Google Maps", "Twitter", "Snapchat", "Starbucks", "No Setting"
                    };

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = inflater.inflate(R.layout.dialog_listview, null);
                    builder.setView(convertView);
                    builder.setTitle("Make a Selection");
                    ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
                    lv.setAdapter(adapter);

                    final AlertDialog alert = builder.create();
                    alert.show();

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Save selection to SharedPreferences
                            String preferenceName = getPreferenceName(beacon.getMajor());
                            getSharedPreferences("BeaconSettings", Context.MODE_PRIVATE).edit().putString(preferenceName,
                                    items[i].toString()).apply();

                            //Show change in textView
                            TextView setting = (TextView)view1.findViewById(R.id.textView_settingValue);
                            setting.setText(items[i].toString());

                            //set changesMade to true, will act as result for main activity
                            changesMade = true;
                            alert.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        ReturnToMainActivty();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                ReturnToMainActivty();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ReturnToMainActivty(){
        Intent returnIntent = new Intent();
        if(changesMade)
            setResult(Activity.RESULT_OK, returnIntent);
        else
            setResult(Activity.RESULT_CANCELED, returnIntent);
    }


    public List GenerateList_BeaconProperties(Beacon beacon){
        List<BeaconSettingsClass> list = new ArrayList<>();
        BeaconSettingsClass bsc = new BeaconSettingsClass(getResources().getString(R.string.currentSetting), preferenceName);
        list.add(bsc);
        bsc = new BeaconSettingsClass(getResources().getString(R.string.UUID), String.valueOf(beacon.getProximityUUID()));
        list.add(bsc);
        bsc = new BeaconSettingsClass(getResources().getString(R.string.Major), String.valueOf(beacon.getMajor()));
        list.add(bsc);
        bsc = new BeaconSettingsClass(getResources().getString(R.string.Minor), String.valueOf(beacon.getMinor()));
        list.add(bsc);
        return list;
    }

    private String getPreferenceName(int major) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("settingFor_");
        sb.append(major);
        return sb.toString();
    }

}
