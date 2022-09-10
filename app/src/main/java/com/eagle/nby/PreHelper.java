package com.eagle.nby;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.Log;

public class PreHelper {

    private static PreHelper sInstance;
    private Context mContext;

    private static final String TAG = PreHelper.class.getSimpleName();

    public static final String KEY_PARENT_ENTRANCE_X = "parent_entrance_x";
    public static final String KEY_PARENT_ENTRANCE_Y = "parent_entrance_y";
    public static final String KEY_SIGN_IN_X = "sign_in_x";
    public static final String KEY_SIGN_IN_Y = "sign_in_y";
    public static final String KEY_HEALTH_X = "health_x";
    public static final String KEY_HEALTH_Y = "health_y";
    public static final String KEY_SIGN_IN_CONFIRM_X = "sign_in_confirm_x";
    public static final String KEY_SIGN_IN_CONFIRM_Y = "sign_in_confirm_y";

    public static final String PRF_NAME = "nby_prefs";
    private SharedPreferences mSharedPreferences;

    private PreHelper(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PRF_NAME, Context.MODE_PRIVATE);
    }

    public static PreHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreHelper(context);
        }
        return sInstance;
    }

    public Point getParentEntrancePoint() {
        Point point = new Point();
        point.x = mSharedPreferences.getInt(KEY_PARENT_ENTRANCE_X, 200);
        point.y = mSharedPreferences.getInt(KEY_PARENT_ENTRANCE_Y, 1145);
        return point;
    }

    public Point getSignInPoint() {
        Point point = new Point();
        point.x = mSharedPreferences.getInt(KEY_SIGN_IN_X, 767);
        point.y = mSharedPreferences.getInt(KEY_SIGN_IN_Y, 921);
        return point;
    }

    public Point getHealthPoint() {
        Point point = new Point();
        point.x = mSharedPreferences.getInt(KEY_HEALTH_X, 405);
        point.y = mSharedPreferences.getInt(KEY_HEALTH_Y, 1680);
        return point;
    }

    public Point getSignInConfirmPoint() {
        Point point = new Point();
        point.x = mSharedPreferences.getInt(KEY_SIGN_IN_CONFIRM_X, 520);
        point.y = mSharedPreferences.getInt(KEY_SIGN_IN_CONFIRM_Y, 2000);
        return point;
    }

    public void setKeyIntValues(String key, int value) {
        Log.d(TAG, "setKeyIntValue : " + key + " value : " + value);
        mSharedPreferences.edit().putInt(key, value).apply();
    }
}
