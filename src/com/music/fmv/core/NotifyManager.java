package com.music.fmv.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.music.fmv.R;
import com.music.fmv.activities.PlayerActivity;
import com.music.fmv.services.PlayerService;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:58 PM
 */
public class NotifyManager extends Manager{
    private static final int DOWNLOAD_NOTIFY_ID = 1350;
    private static final int PLAYER_NOTIFY_ID = 1357;

    private static PendingIntent prevPendIntent;
    private static PendingIntent pausePendIntent;
    private static PendingIntent nextPendIntent;

    public NotifyManager(Core coreManager) {
        super(coreManager);
    }

    public void notifyPlayer(Context c){
        if (android.os.Build.VERSION.SDK_INT  < 15 ){
            show8ApiPlayerProgress(c);
        }else show15ApiPlayerProgress(c);
    }

    public void removePlayer(Context c){
        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(PLAYER_NOTIFY_ID);
    }
    public void notifyDownloading(Context c){

    }

    public void removeDownloading(Context c){

    }

    private void show8ApiPlayerProgress(Context c) {
        RemoteViews remoteView = new RemoteViews(c.getPackageName(), R.layout.simple_player_notification);
        notify(PLAYER_NOTIFY_ID, R.drawable.icon, remoteView, c);
    }

    private void show15ApiPlayerProgress(Context c) {
        RemoteViews notificationView = new RemoteViews(c.getPackageName(), R.layout.progress_notification);

        if(prevPendIntent == null) createPrevPending(c);
        if(nextPendIntent == null) createNextPending(c);
        if(pausePendIntent == null) createPausePending(c);

        notificationView.setOnClickPendingIntent(R.id.prev_notify_button, prevPendIntent);
        notificationView.setOnClickPendingIntent(R.id.next_notify_button, nextPendIntent);
        notificationView.setOnClickPendingIntent(R.id.play_pause_notify_button, pausePendIntent);

        notify(PLAYER_NOTIFY_ID, R.drawable.icon, notificationView, c);
    }

    //Shows notification
    private void notify(int id, int iconId, RemoteViews remoteViews, Context c){
        //Creating notification
        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();
        Notification notification = new Notification(iconId, "Playing", when);
        notification.contentView = remoteViews;

        //set pending intent to notification
        Intent notificationIntent = new Intent(c, PlayerActivity.class);
        notification.contentIntent = PendingIntent.getActivity(c, 0, notificationIntent, 0);

        //adding flag, that will disable cancel of notification
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(id, notification);
    }

    private void createPausePending(Context c) {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.PAUSE);
        pausePendIntent = PendingIntent.getBroadcast(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNextPending(Context c) {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.NEXT);
        nextPendIntent = PendingIntent.getBroadcast(c, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createPrevPending(Context c) {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.PREV);
        prevPendIntent = PendingIntent.getBroadcast(c, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
