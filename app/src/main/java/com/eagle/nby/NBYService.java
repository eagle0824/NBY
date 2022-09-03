package com.eagle.nby;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Message;
import android.os.Looper;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.ArrayList;
import java.util.List;

public class NBYService extends AccessibilityService {

    private static final String TAG = NBYService.class.getSimpleName();

    public final static int TIME_DEALYED = 800;

    private int mEvent = 0;

    @Override
    public void onInterrupt() {
        Utils.logW(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Utils.logD(TAG, "onServiceConnected");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Utils.logD(TAG, "onKeyEvents : " + event);
        return super.onKeyEvent(event);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows() {
        Utils.logD(TAG, "getWindows");
        return super.getWindows();
    }

    @Override
    public AccessibilityNodeInfo findFocus(int focus) {
        Utils.logD(TAG, "findFocus : " + focus);
        return super.findFocus(focus);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Utils.logD(TAG, "event : " + event);
        CharSequence pkgName = event.getPackageName();
        CharSequence className = event.getClassName();
        Utils.logD(TAG, "onAccessibilityEvent type " + event.getEventType() + " pkg : " + pkgName + "class : " + className);
        if (pkgName != null && !"com.eg.android.AlipayGphone".equals(pkgName)) {
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        switch(event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            {
                if ("com.eg.android.AlipayGphone".equals(pkgName)) {
                    if (String.valueOf(className).startsWith("com.alipay.mobile.nebulax.integration.mpaas.activity.NebulaActivity$Lite")) {
                        sendEventMessage(EVENT_PARENTS_ENTRANCE, true);
                    }
                } else if ("com.huawei.android.launcher".equals(pkgName)) {
//                    List<AccessibilityNodeInfo> nodeInfos = findNodeInfoByText(this, "宁搏疫");
//                    Utils.logD(TAG, "==============> " + nodeInfos.size());
//                    if (nodeInfos.size() > 0) {
//                        Utils.logD(TAG, "finded 宁搏疫");
//                        //click(nodeInfos.get(0));
//                    }
                }
            }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            {
                if ("com.uc.webview.export.WebView".equals(className)) {
                    if (mEvent == EVENT_PARENTS_ENTRANCE) {
                        //sendEventMessage(EVENT_SIGN_IN);
                    }
                } else if ("com.uc.webkit.bc".equals(className)) {
                    if (mEvent == EVENT_PARENTS_ENTRANCE) {
                        sendEventMessage(EVENT_SIGN_IN);
                    }
                } else if ("android.widget.FrameLayout".equals(className)) {
                    if (mEvent == EVENT_SIGN_IN) {
                        sendEventMessage(EVENT_HEALTH_OK);
                    }
                }
            }
                break;
        }
    }

    private void sendEventMessage(int event) {
        sendEventMessage(event, false);
    }

    private void sendEventMessage(int event, boolean force) {
        if (mEvent != event || force) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(event, TIME_DEALYED);
        }
    }
    public static final int EVENT_PARENTS_ENTRANCE = 100;
    public static final int EVENT_SIGN_IN = 101;
    public static final int EVENT_HEALTH_OK = 102;
    public static final int EVENT_DO_SIGN_IN = 103;
    public static final int EVENT_BACK = 104;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            mEvent = msg.what;
            switch(msg.what){
                case EVENT_PARENTS_ENTRANCE:
                    Utils.logD(TAG, "EVENT_PARENTS_ENTRANCE");
                    coordinatesClick(NBYService.this, 200, 1145);
                    break;
                case EVENT_SIGN_IN:
                    Utils.logD(TAG, "EVENT_SIGN_IN");
                    coordinatesClick(NBYService.this, 767, 921);
                    break;
                case EVENT_HEALTH_OK:
                    Utils.logD(TAG, "EVENT_HEALTH_OK");
                    coordinatesClick(NBYService.this, 420, 1810);
                    if (mEvent == EVENT_HEALTH_OK) {
                        sendEventMessage(EVENT_DO_SIGN_IN);
                    }
                    break;
                case EVENT_DO_SIGN_IN:
                    Utils.logD(TAG, "EVENT_DO_SIGN_IN");
                    coordinatesClick(NBYService.this, 520, 2000);
                    sendEventMessage(EVENT_BACK);
                    break;
                case EVENT_BACK:
                    Back(NBYService.this);
                    break;
            }
        };

    };

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.logD(TAG, "onUnbind : " + intent.toString());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //主页
    public static boolean Home(AccessibilityService service) {
        if (service == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        } else {
            return false;
        }
    }

    public static boolean Back(AccessibilityService service) {
        if (service == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        } else {
            return false;
        }
    }

    public List<AccessibilityNodeInfo> findNodeInfoByText(AccessibilityService service, String text) {
        List<AccessibilityNodeInfo> results = new ArrayList<>();
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            Utils.logW(TAG, "root AccessibilityNodeInfo activate window is empty");
            return results;
        }
        int count = accessibilityNodeInfo.getChildCount();
        for (int i=0; i<count; i++) {
            Utils.logD(TAG, "child " + i + " " + accessibilityNodeInfo.getChild(i));
        }
        List<AccessibilityNodeInfo> nodeInfoByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoByText != null && !nodeInfoByText.isEmpty()) {
            Utils.logD(TAG, " nodeInfoByText no null : " + nodeInfoByText.size());
            for (AccessibilityNodeInfo nodeInfo : nodeInfoByText) {
                Utils.logD(TAG, " =====info : " + nodeInfo);
                if (nodeInfo != null && (nodeInfo.getText().equals(text) || nodeInfo.getContentDescription().equals(text))) {
                    results.add(nodeInfo);
                }
            }
        }
        return results;
    }

    //通过文字点击第一项
    public boolean clickText(AccessibilityService service, String string) {
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) return false;
        List<AccessibilityNodeInfo> nodeInfoByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(string);
        if (nodeInfoByText != null && !nodeInfoByText.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoByText) {
                if (nodeInfo != null && (nodeInfo.getText().equals(string) || nodeInfo.getContentDescription().equals(string))) {
                    return click(nodeInfo);
                }
            }
        }
        return false;
    }

    //点击
    private boolean click(AccessibilityNodeInfo node) {
        if (node == null) return false;
        if (node.isClickable()) {
            return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            click(node.getParent());
        }
        return false;
    }

    //长按
    private boolean longClick(AccessibilityNodeInfo node) {
        if (node == null) return false;
        if (node.isLongClickable()) {
            return node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
        } else {
            longClick(node.getParent());
        }
        return false;
    }

    //通过坐标点击
    public static boolean coordinatesClick(AccessibilityService service, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 200)).build();
        Utils.logD(TAG, "coordinatesClick  x " +x  + " y : " + y);
        return service.dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Utils.logD(TAG, "coordinatesClick  onCompleted x " +x  + " y : " + y);
            }
        }, null);
    }
}
