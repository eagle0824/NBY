package com.eagle.nby;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    private Button mSettings;
    private EditText mParentEntraceX;
    private EditText mParentEntraceY;
    private EditText mSignInX;
    private EditText mSignInY;
    private EditText mHealthX;
    private EditText mHealthY;
    private EditText mSignInConfirmX;
    private EditText mSignInConfirmY;
    private TextView mServiceState;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mServiceState = findViewById(R.id.service_state);
        mSettings = findViewById(R.id.settings);
        mSettings.setOnClickListener(this);
        mParentEntraceX = findViewById(R.id.parent_entrance_x);
        mParentEntraceY = findViewById(R.id.parent_entrance_y);
        mSignInX = findViewById(R.id.sign_in_x);
        mSignInY = findViewById(R.id.sign_in_y);
        mHealthX = findViewById(R.id.health_x);
        mHealthY = findViewById(R.id.health_y);
        mSignInConfirmX = findViewById(R.id.sign_in_confirm_x);
        mSignInConfirmY = findViewById(R.id.sign_in_confirm_y);
        updateViewState();
        mParentEntraceX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_PARENT_ENTRANCE_X, value);
            }
        });
        mParentEntraceY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_PARENT_ENTRANCE_Y, value);
            }
        });
        mSignInX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_SIGN_IN_X, value);
            }
        });
        mSignInY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_SIGN_IN_Y, value);
            }
        });

        mHealthX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_HEALTH_X, value);
            }
        });

        mHealthY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_HEALTH_Y, value);
            }
        });

        mSignInConfirmX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_SIGN_IN_CONFIRM_X, value);
            }
        });

        mSignInConfirmY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value = Integer.parseInt(s.toString());
                PreHelper.getInstance(mContext).setKeyIntValues(PreHelper.KEY_SIGN_IN_CONFIRM_Y, value);
            }
        });
    }

    private void updateViewState() {
        boolean start = Utils.isService(this, NBYService.class);
        mServiceState.setText(start ? R.string.service_state_on : R.string.service_state_off);
        Point pePoint = PreHelper.getInstance(this).getParentEntrancePoint();
        Point sPoint = PreHelper.getInstance(this).getSignInPoint();
        Point hPoint = PreHelper.getInstance(this).getHealthPoint();
        Point scPoint = PreHelper.getInstance(this).getSignInConfirmPoint();

        mParentEntraceX.setText(String.valueOf(pePoint.x));
        mParentEntraceY.setText(String.valueOf(pePoint.y));

        mSignInX.setText(String.valueOf(sPoint.x));
        mSignInY.setText(String.valueOf(sPoint.y));

        mHealthX.setText(String.valueOf(hPoint.x));
        mHealthY.setText(String.valueOf(hPoint.y));

        mSignInConfirmX.setText(String.valueOf(scPoint.x));
        mSignInConfirmY.setText(String.valueOf(scPoint.y));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                startActivity(sSettingsIntent);
                break;
        }
    }

}
