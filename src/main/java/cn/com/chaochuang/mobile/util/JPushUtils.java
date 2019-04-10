/*
 * FileName:    JPushUtils.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月31日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.mobile.util;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @author LaoZhiYong
 *
 */
public class JPushUtils {
    /**
     * 极光推送参数，不同应用请用公司账号到https://www.jiguang.cn网站上申请
     */
    private static final String APP_KEY       = "d01804ec4cedb1afe30f9142";
    private static final String MASTER_SECRET = "fa40483f6af55698243ceba8";

    /**
     * 移动端模块枚举
     */
    public enum AppModules{
        待办列表("docFordoList"),经办列表("docHandleList"),待阅("docReadList"),传阅("docDispatchList"),收件箱列表("inboxList"),收件箱详情("inboxDetail"),会议列表("meetingInfoList"),公告列表("noticeList"),公告详情("noticeItemDetail");

        private String key;

        AppModules(String key) {
            this.key=key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }


    /**
     * 直接消息推送-单人
     *
     * @param registrationID
     * @param message
     * @param extraParams 传递的其他参数
     */
    public static void pushByRegistrationID(String registrationID, String message, String stateName ,Map<String,String> extraParams) {
        try {
            JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
            PushPayload.Builder builder = PushPayload.newBuilder();
            builder.setPlatform(Platform.all());

            IosNotification.Builder iosNotificationBuilder = IosNotification.newBuilder().setAlert(message).incrBadge(1);
            AndroidNotification.Builder androidNotification = AndroidNotification.newBuilder().setAlert(message);

            if(stateName!=null){
                iosNotificationBuilder.addExtra("stateName",stateName);
                androidNotification.addExtra("stateName",stateName);
                if(extraParams!=null) {
                    iosNotificationBuilder.addExtra("stateParams", JSON.toJSONString(extraParams));
                    androidNotification.addExtra("stateParams", JSON.toJSONString(extraParams));
                }
            }

            builder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(iosNotificationBuilder.build())
                    .addPlatformNotification(androidNotification.build())
                    .build());
            builder.setAudience(Audience.registrationId(registrationID));
            builder.setOptions(Options.newBuilder().setApnsProduction(true).build());
            PushPayload payload = builder.build();
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接消息推送-所有人
     *
     * @param message
     */
    public static void pushToAll(String message,String stateName,Map<String,String> extraParams) {
        try {
            JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);

            IosNotification.Builder iosNotificationBuilder = IosNotification.newBuilder().setAlert(message).incrBadge(1);
            AndroidNotification.Builder androidNotification = AndroidNotification.newBuilder().setAlert(message);
            if(stateName!=null){
                iosNotificationBuilder.addExtra("stateName",stateName);
                androidNotification.addExtra("stateName",stateName);
                if(extraParams!=null) {
                    iosNotificationBuilder.addExtra("stateParams", JSON.toJSONString(extraParams));
                    androidNotification.addExtra("stateParams", JSON.toJSONString(extraParams));
                }
            }
            PushPayload.Builder builder = PushPayload.newBuilder();
            builder.setPlatform(Platform.all());
            builder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(iosNotificationBuilder.build())
                    .addPlatformNotification(androidNotification.build())
                    .build());
            builder.setAudience(Audience.all());
            builder.setOptions(Options.newBuilder().setApnsProduction(true).build());

            PushPayload payload =builder.build();
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
    	Map<String,String> params = new HashMap<>();
        params.put("id","111");
        params.put("incId","222");
        pushByRegistrationID("120c83f7602e7ea1a84", "测试发送",AppModules.会议列表.getKey(),params);

    }
}
