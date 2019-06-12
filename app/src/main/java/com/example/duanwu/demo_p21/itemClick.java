package com.example.duanwu.demo_p21;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class itemClick extends BroadcastReceiver {
    private static final String TAG = "ItemClickReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
    //接收值
    String title = intent.getStringExtra("title");
    String image = intent.getStringExtra("image");
    String desc = intent.getStringExtra("desc");
        Log.d(TAG, "onReceive: title:" + title + ",image:" + image + ",desc:" + desc);
    notification(context, title, image, desc);
}

    private void notification(Context context, String title, String image, String desc) {
        Log.d(TAG, "已进入通知");
        //通知管理器必须用上下文获取
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123", "通知", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, ParticularsActivity.class);
        intent.putExtra("imagePath",image);
        PendingIntent activity = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, "123")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(activity)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        manager.notify(100, notification);
        Log.d(TAG, "已进入通知2");
    }
}
