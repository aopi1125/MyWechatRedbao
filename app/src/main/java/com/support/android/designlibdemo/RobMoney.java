package com.support.android.designlibdemo;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by stone_zheng on 15/12/11.
 */
public class RobMoney extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = accessibilityEvent.getText();
                if(!texts.isEmpty()){
                    for(CharSequence cs : texts){
                        if(cs.toString().contains("微信红包")){
                            if(accessibilityEvent.getParcelableData() != null && accessibilityEvent.getParcelableData() instanceof Notification){
                                Notification notification = (Notification)accessibilityEvent.getParcelableData();
                                PendingIntent pi = notification.contentIntent;
                                try{
                                    pi.send();
                                }catch (Exception e){

                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String cName = accessibilityEvent.getClassName().toString();
                if(cName.equals("com.tencent.mm.ui.LauncherUI")){
                    getPacket();
                }else if(cName.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")){
                    openPacket();
                }
        }
    }

    @SuppressLint("NewApi")
    private void openPacket(){
        AccessibilityNodeInfo ani = getRootInActiveWindow();
        if(ani != null){
            List<AccessibilityNodeInfo> list = ani.findAccessibilityNodeInfosByText("拆红包");
            boolean flag = false;
            for(AccessibilityNodeInfo n : list){
                flag = true;
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            //新版
            if(!flag){
                AccessibilityNodeInfo aaa = ani.getChild(3);
                if(aaa != null){
                    aaa.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private void getPacket(){
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if(rootNode != null){
            List<AccessibilityNodeInfo> list = rootNode.findAccessibilityNodeInfosByText("领取红包");
            AccessibilityNodeInfo nodeInfo;
            for(AccessibilityNodeInfo ni : list){
                nodeInfo = ni.getParent();
                if(nodeInfo != null){
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                ni.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
//            if(list != null && !list.isEmpty()){
//                AccessibilityNodeInfo nodeInfo = list.get(list.size() - 1);
//                if(nodeInfo != null){
//                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    nodeInfo = nodeInfo.getParent();
//                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//
//            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
