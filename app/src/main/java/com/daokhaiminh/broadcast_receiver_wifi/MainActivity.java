package com.daokhaiminh.broadcast_receiver_wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Switch wifiSwitch;
    private WifiManager wifiManager;
    private TextView wifiText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        wifiSwitch =findViewById (R.id.wifi_switch);
        wifiText=findViewById(R.id.wifi_text);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiSwitch.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    wifiManager.setWifiEnabled (true);
                    wifiText.setText ("Bạn đã có wifi");
                    wifiSwitch.setText ("Wifi đã kết nối");
                    Toast.makeText(MainActivity.this, "Đã kết nối wifi",Toast.LENGTH_LONG).show() ;
                }
                else{
                    wifiManager.setWifiEnabled (false);
                    wifiText.setText ("Bạn chưa có wifi");
                    wifiSwitch.setText ("Wifi chưa kết nối");
                    Toast.makeText(MainActivity.this, "Mất kết nối wifi",Toast.LENGTH_LONG).show() ;
                }
            }
        });

        if(wifiManager.isWifiEnabled ())
        {
            wifiManager.setWifiEnabled (true);
            wifiText.setText ("Bạn đã có wifi");
            wifiSwitch.setText ("Wifi đã kết nối");
            Toast.makeText(MainActivity.this, "Đã kết nối wifi",Toast.LENGTH_LONG).show() ;
        }else{
            wifiManager.setWifiEnabled (false);
            wifiText.setText ("Bạn chưa có wifi");
            wifiSwitch.setText ("Wifi chưa kết nối");
            Toast.makeText(MainActivity.this, "Đã kết nối wifi",Toast.LENGTH_LONG).show() ;
        }
    }

    @Override
    protected void onStart() {
        super.onStart ();
        IntentFilter intentFilter =new IntentFilter (WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver (wifiStateReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop ();
        unregisterReceiver (wifiStateReceiver);
    }

    private  BroadcastReceiver wifiStateReceiver =new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra =intent.getIntExtra (wifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked (true);
                    wifiText.setText ("Bạn đã có wifi");
                    wifiSwitch.setText ("Wifi đã kết nối");
                    Toast.makeText( context, "Đã kết nối wifi",Toast.LENGTH_LONG).show() ;
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked (false);
                    wifiText.setText ("Bạn chưa có wifi");
                    wifiSwitch.setText ("Wifi chưa kết nối");
                    Toast.makeText( context, "Đã mất nối wifi",Toast.LENGTH_LONG).show() ;
                    break;
            }
        }
    };
}