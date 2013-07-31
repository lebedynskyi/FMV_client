package com.music.fmv.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.music.fmv.R;
import com.music.fmv.activities.BandInfoActivity;
import com.music.fmv.services.PlayerService;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:58 PM
 */
public class NotifyManager extends Manager{
    private static final int DOWNLOAD_NOTIFY_ID = 1350;
    private static final int PLAYER_NOTIFY_ID = 1357;

    public NotifyManager(Core coreManager) {
        super(coreManager);
    }

    public void notifyProgress(Context c, int  progress){
//        if (android.os.Build.VERSION.SDK_INT  <= 15 ){
//            show8ApiPlayerProgress(c, progress);
//        }else show15ApiPlayerProgress(c, progress);
        show8ApiPlayerProgress(c, progress);
    }

    public void removeProgress(Context c){
        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(PLAYER_NOTIFY_ID);
    }
    public void notifyDownloading(Context c){

    }

    public void removeDownloading(Context c){

    }

    private void show8ApiPlayerProgress(Context c, int progress) {
        RemoteViews remoteView = new RemoteViews(c.getPackageName(), R.layout.progress_notification);
        Intent notificationIntent = new Intent();
        notificationIntent.setAction("test");
        remoteView.setOnClickPendingIntent(R.id.prev_notify_button, PendingIntent.getBroadcast(c, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//        c.sendBroadcast(notificationIntent);
//        try {
////            PendingIntent.getActivity(c, 0, notificationIntent, 0).send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
        notify8(PLAYER_NOTIFY_ID, R.drawable.icon, remoteView, c);
    }

//    private void show15ApiPlayerProgress(Context c, int progress) {
//        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.progress_notification);
//        notify15(PLAYER_NOTIFY_ID, R.drawable.icon, remoteViews, c);
//    }
//
//    //Used to send notification in api level > 15
//    private void notify15(int id, int iconId, RemoteViews remoteViews, Context c){
//        // Creates an explicit intent for an PlayerActivity
//        Intent resultIntent = new Intent(c, BandInfoActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(c, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        //Creating of notification
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c).setSmallIcon(iconId).setContent(remoteViews);
//        mBuilder.setContentIntent(resultPendingIntent);
//
//        //Getting NotificationManager
//        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // show notification with id.
//        Notification notification = mBuilder.build();
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
//        mNotificationManager.notify(id, notification);
//    }

    //Used to send notification in api level < 15
    private void notify8(int id, int iconId, RemoteViews remoteViews, Context c){
        //Creating notification
        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();
        Notification notification = new Notification(iconId, "Playing", when);
        notification.contentView = remoteViews;

        //set pending intent to notification
        Intent notificationIntent = new Intent(c, BandInfoActivity.class);
        notification.contentIntent = PendingIntent.getActivity(c, 0, notificationIntent, 0);

        //adding flag, that will disable cancel of notification
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(id, notification);
    }
}
