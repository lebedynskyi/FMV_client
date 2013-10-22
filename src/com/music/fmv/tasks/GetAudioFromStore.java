package com.music.fmv.tasks;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import com.music.fmv.models.notdbmodels.FileSystemSong;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/22/13
 * Time: 10:30 AM
 */
public class GetAudioFromStore extends BaseAsyncTask<List<FileSystemSong>> {
    public GetAudioFromStore(Context context, boolean showDialog) {
        super(context, showDialog);
    }

    @Override
    protected List<FileSystemSong> doInBackground(Object... params) {
        String[] props = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Video.Media.SIZE,
        };
        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,props, null, null, MediaStore.Audio.Media.DISPLAY_NAME);
        ArrayList <FileSystemSong> songs = new ArrayList<FileSystemSong>();

        if (musicCursor.getCount()<= 0) return songs;

        int artistIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int nameIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int durIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int pathIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        while (musicCursor.moveToNext()){
            FileSystemSong song = new FileSystemSong();
            song.setArtist(musicCursor.getString(artistIndex));
            song.setDuration(musicCursor.getInt(durIndex));
            song.setName(musicCursor.getString(nameIndex));
            song.setId(musicCursor.getInt(idIndex));
            song.setUrl(musicCursor.getString(pathIndex));
            songs.add(song);
        }

        return songs;
    }
}
