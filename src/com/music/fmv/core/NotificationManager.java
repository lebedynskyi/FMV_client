package com.music.fmv.core;

import android.content.Context;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:58 PM
 */
public class NotificationManager extends Manager{
    private static int DOWNLOAD_NOTIFY_ID = 1357;
    private static int PLAYER_NOTIFY_ID = 1357;

    public NotificationManager(Core coreManager) {
        super(coreManager);
    }

    public static void notifyProgress(Context c){}
    public static void removeProgress(Context c){}
    public static void notifyDownloading(Context c){}
    public static void removeDownloading(Context c){}
}
