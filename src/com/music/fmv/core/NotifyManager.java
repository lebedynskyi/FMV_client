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
        NotificationManager mNotificationManager = (NotificationManager) core.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void finish() {
        NotificationManager mNotificationManager = (NotificationManager) core.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(PLAYER_NOTIFY_ID);
        mNotificationManager.cancel(DOWNLOAD_NOTIFY_ID);
    }

    public void notifyPlayer(){
        if (android.os.Build.VERSION.SDK_INT  < 15 ){
            show8ApiPlayerProgress();
        }else show15ApiPlayerProgress();
    }

    public void removePlayer(){
        NotificationManager mNotificationManager = (NotificationManager) core.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(PLAYER_NOTIFY_ID);
    }
    public void notifyDownloading(){

    }

    public void removeDownloading(){

    }

    private void show8ApiPlayerProgress() {
        RemoteViews remoteView = new RemoteViews(core.getContext().getPackageName(), R.layout.simple_player_notification);
        notify(PLAYER_NOTIFY_ID, R.drawable.icon, remoteView);
    }

    private void show15ApiPlayerProgress() {
        RemoteViews notificationView = new RemoteViews(core.getContext().getPackageName(), R.layout.progress_notification);

        if(prevPendIntent == null) createPrevPending();
        if(nextPendIntent == null) createNextPending();
        if(pausePendIntent == null) createPausePending();

        notificationView.setOnClickPendingIntent(R.id.prev_notify_button, prevPendIntent);
        notificationView.setOnClickPendingIntent(R.id.next_notify_button, nextPendIntent);
        notificationView.setOnClickPendingIntent(R.id.play_pause_notify_button, pausePendIntent);

        notify(PLAYER_NOTIFY_ID, R.drawable.icon, notificationView);
    }

    //Shows notification
    private void notify(int id, int iconId, RemoteViews remoteViews){
        //Creating notification
        NotificationManager mNotificationManager = (NotificationManager) core.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();
        Notification notification = new Notification(iconId, "Playing", when);
        notification.contentView = remoteViews;

        //set pending intent to notification
        Intent notificationIntent = new Intent(core.getContext(), PlayerActivity.class);
        notification.contentIntent = PendingIntent.getActivity(core.getContext(), 0, notificationIntent, 0);

        //adding flag, that will disable cancel of notification
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(id, notification);
    }

    private void createPausePending() {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.PAUSE);
        pausePendIntent = PendingIntent.getBroadcast(core.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNextPending() {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.NEXT);
        nextPendIntent = PendingIntent.getBroadcast(core.getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createPrevPending() {
        Intent intent = new Intent();
        intent.setAction(PlayerService.RECEIVER_ACTION);
        intent.putExtra(PlayerService.ACTION_KEY, PlayerService.ACTION.PREV);
        prevPendIntent = PendingIntent.getBroadcast(core.getContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
