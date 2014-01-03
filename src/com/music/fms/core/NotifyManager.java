package com.music.fms.core;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.music.fms.R;
import com.music.fms.activities.PlayerActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:58 PM
 */
public class NotifyManager extends Manager {
    private static final int DOWNLOAD_NOTIFY_ID = 1350;
    private static final int DOWNLOAD_SUCCESS_ID = 1351;
    private static final int DOWNLOAD_ERROR_ID = 1351;
    private static final int PLAYER_NOTIFY_ID = 1360;

    private NotificationManager mNotifyManager;

    NotifyManager(Core coreManager) {
        super(coreManager);
        mNotifyManager = (NotificationManager) core.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void finish() {
        mNotifyManager.cancel(PLAYER_NOTIFY_ID);
        mNotifyManager.cancel(DOWNLOAD_NOTIFY_ID);
    }

    public void removePlayer() {
        mNotifyManager.cancel(PLAYER_NOTIFY_ID);
    }

    public void notifyDownloading(String name, int current, int max) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(core.getContext());
        mBuilder.setContentTitle(name).setSmallIcon(R.drawable.icon).setOngoing(true).setProgress(max, current, false)
                .setContentIntent(createEmptyPending())
                .setTicker(core.getContext().getString(R.string.song_downloading));
        mNotifyManager.notify(DOWNLOAD_NOTIFY_ID, mBuilder.build());
    }

    public void removeDownloading() {
        mNotifyManager.cancel(DOWNLOAD_NOTIFY_ID);
    }

    public void notifyPlayer(String text) {
        RemoteViews view = new RemoteViews(core.getContext().getPackageName(), R.layout.simple_player_notification);
        view.setTextViewText(R.id.song_name, text);
        Intent playerIntent = new Intent(core.getContext(), PlayerActivity.class);
        playerIntent.putExtra(PlayerActivity.FROM_NOTIFY_FLAG, true);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(core.getContext());
        mBuilder.setContent(view).setSmallIcon(R.drawable.icon).setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(core.getContext(), 0, playerIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setTicker(text);
        mNotifyManager.notify(PLAYER_NOTIFY_ID, mBuilder.build());
    }

    public void notifyErrorDownloading(String name) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(core.getContext());
        mBuilder.setContentTitle(core.getContext().getString(R.string.song_downloading_error))
                .setContentInfo(name).setSmallIcon(R.drawable.icon).setOngoing(false).setProgress(0, 0, false)
                .setContentIntent(createEmptyPending())
                .setTicker(core.getContext().getString(R.string.song_downloading_error));
        mNotifyManager.notify(DOWNLOAD_ERROR_ID, mBuilder.build());
    }

    public void notifySuccessDownloading() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(core.getContext());
        mBuilder.setContentTitle(core.getContext().getString(R.string.downloading_finish)).setSmallIcon(R.drawable.icon)
                .setOngoing(false).setProgress(0, 0, false)
                .setContentIntent(createEmptyPending())
                .setTicker(core.getContext().getString(R.string.downloading_finish));
        mNotifyManager.notify(DOWNLOAD_SUCCESS_ID, mBuilder.build());
    }

    private PendingIntent createEmptyPending() {
        return PendingIntent.getActivity(core.getContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
