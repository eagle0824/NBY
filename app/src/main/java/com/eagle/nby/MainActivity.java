package com.eagle.nby;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Utils.isService(this, NBYService.class)) {
            startActivity(sSettingsIntent);
        } else {
            //com.eg.android.AlipayGphone/com.alipay.mobile.nebulax.integration.mpaas.activity.NebulaActivity$Lite1
        }
    }

}
