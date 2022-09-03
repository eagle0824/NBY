package com.eagle.nby;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

public class Utils {

    public static void logD(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void logW(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void logE(String tag, String msg) {
        Log.d(tag, msg);
    }

    //判断服务是否开启
    public static boolean isService(Context context, Class service) {
        int enable = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 0);
        if (enable != 1) return false;
        String services = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (!TextUtils.isEmpty(services)) {
            TextUtils.SimpleStringSplitter split = new TextUtils.SimpleStringSplitter(':');
            split.setString(services);
            while (split.hasNext()) {
                if (split.next().equalsIgnoreCase(context.getPackageName() + "/" + service.getName()))
                    return true;
            }
        }
        return false;
    }
}
