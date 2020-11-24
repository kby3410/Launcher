package com.example.launchertest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

import static android.provider.Settings.ACTION_BLUETOOTH_SETTINGS;
import static android.provider.Settings.ACTION_SETTINGS;
import static android.provider.Settings.ACTION_WIFI_SETTINGS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DBHandler handler;
    private AddRecyclerAdapter adapter;
    private ArrayList<Data> data = new ArrayList<>();
    private ArrayList<String> apps = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Drawable> icon = new ArrayList<>();
    private Button PLAYSTORE,VIDEO,MIRACAST,ETHERNET,WIFI,SETTING,LIST,BLUETOOTH,ADD,YOUTUBE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        LIST = (Button) findViewById(R.id.list);
        LIST.setOnClickListener(this);
        YOUTUBE = (Button) findViewById(R.id.youtube);
        YOUTUBE.setOnClickListener(this);
        ETHERNET = (Button) findViewById(R.id.EtherNet);
        ETHERNET.setOnClickListener(this);
        MIRACAST = (Button) findViewById(R.id.miracast);
        MIRACAST.setOnClickListener(this);
        VIDEO = (Button) findViewById(R.id.video);
        VIDEO.setOnClickListener(this);
        PLAYSTORE = (Button) findViewById(R.id.playstore);
        PLAYSTORE.setOnClickListener(this);
        SETTING = (Button) findViewById(R.id.setting);
        SETTING.setOnClickListener(this);
        WIFI = (Button) findViewById(R.id.wifi);
        WIFI.setOnClickListener(this);
        BLUETOOTH = (Button) findViewById(R.id.bluetooth);
        BLUETOOTH.setOnClickListener(this);
        ADD = (Button) findViewById(R.id.add);
        ADD.setOnClickListener(this);
        checkBt();
        checkwifi();

        RecyclerView recyclerView = findViewById(R.id.AddRecycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AddRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);

        handler = DBHandler.open(this);
        Cursor cursor;
        cursor = handler.select();
        while (cursor.moveToNext()){
            apps.add(cursor.getString(0));
        }

        for(int i = 0; i < apps.size(); i++){
            try {
                Log.d("test",apps.get(i));
                icon.add(getPackageManager().getApplicationIcon(apps.get(i)));
                name.add(getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(apps.get(i), PackageManager.GET_UNINSTALLED_PACKAGES)).toString());
            }catch (PackageManager.NameNotFoundException e){}
            Data save_data = new Data(name.get(i),apps.get(i),icon.get(i));
            data.add(i,save_data);
        }
        adapter.notifyDataSetChanged();

    }

    void checkBluetooth(){                //블루투스 체크
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();   //어댑터를 얻어옴
        if(!mBluetoothAdapter.isEnabled()){      //비활성 상태인 경우
            Intent intent= new Intent(ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
        }
        else{                               //활성 상태인경우
            mBluetoothAdapter.disable();
            BLUETOOTH.setBackgroundResource(R.drawable.img_bt);
        }
    }
    void checkBt(){                //블루투스 체크
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();   //어댑터를 얻어옴
        if(!mBluetoothAdapter.isEnabled()){      //비활성 상태인 경우
            BLUETOOTH.setBackgroundResource(R.drawable.img_bt);
        }
        else{                               //활성 상태인경우
            BLUETOOTH.setBackgroundResource(R.drawable.f_bt);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {      //런처앱 뒤로가기 비활성화
        return false;
    }

    void netWork(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(ninfo == null){
            Intent intent= new Intent(ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }else{
            manager.setWifiEnabled(false);
            WIFI.setBackgroundResource(R.drawable.img_wifi);
        }
    }

    void checkwifi(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if(ninfo == null){
            WIFI.setBackgroundResource(R.drawable.img_wifi);
        }else{
            WIFI.setBackgroundResource(R.drawable.f_wifi);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.list:
                Intent list_intent= new Intent(MainActivity.this, list.class);
                startActivity(list_intent);
                break;
            case R.id.youtube:
                Intent youtube_Intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(youtube_Intent);
                break;
            case R.id.EtherNet:
                Intent EtherNet_Intent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(EtherNet_Intent);
                break;
            case R.id.miracast:
                Intent miracast_Intent = getPackageManager().getLaunchIntentForPackage("com.ecloud.eshare.server");
                startActivity(miracast_Intent);
                break;
            case R.id.video:
                Intent video_Intent = getPackageManager().getLaunchIntentForPackage("android.rk.RockVideoPlayer");
                startActivity(video_Intent);
                break;
            case R.id.playstore:
                Intent playstore_Intent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                startActivity(playstore_Intent);
                break;
            case R.id.setting:
                Intent setting_intent= new Intent(ACTION_SETTINGS);
                startActivity(setting_intent);
                break;
            case R.id.wifi:
                netWork();
                break;
            case R.id.bluetooth:
                checkBluetooth();
                break;
            case R.id.add:
                Intent add_intent= new Intent(MainActivity.this, AppAdd.class);
                startActivity(add_intent);
                break;
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        checkBt();
        checkwifi();
    }
}