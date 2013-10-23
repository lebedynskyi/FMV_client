package com.music.fmv.tasks;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.music.fmv.models.notdbmodels.FileSystemSong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/22/13
 * Time: 10:30 AM
 */
public class GetAudioFromStore extends BaseAsyncTask<List<FileSystemSong>> {
    private String songsFodler;

    public GetAudioFromStore(Context context, boolean showDialog) {
        super(context, showDialog);
    }

    static int counter = 0;

    public GetAudioFromStore(Context baseActivity, boolean b, String songsFolder) {
        super(baseActivity, b);
        this.songsFodler = songsFolder;
    }

    @Override
    protected List<FileSystemSong> doInBackground(Object... params) {
        if (TextUtils.isEmpty(songsFodler)){
            return getSongsFromMediaStore();
        }else return getSongsFromFolder(songsFodler);
    }

    private List<FileSystemSong> getSongsFromFolder(String songsFodler) {
        File f = new File(songsFodler);
        ArrayList<FileSystemSong> songsforresult = new ArrayList<FileSystemSong>();
        if (!f.isDirectory()) return songsforresult;

        for (File file : f.listFiles()){
            if (file.isDirectory()){
                songsforresult.addAll(getSongsFromFolder(file.getAbsolutePath()));
            }else {
                FileSystemSong song = extractSongFromFile(file);
                if (song == null) continue;
                songsforresult.add(song);
            }
        }

        return songsforresult;
    }

    private FileSystemSong extractSongFromFile(File file) {
        String absolutePath = file.getAbsolutePath();

        if (!absolutePath.endsWith(".mp3")) return null;

        FileSystemSong s = new FileSystemSong();
        s.setName(String.valueOf(counter++));
        s.setUrl(absolutePath);
        return s;
    }

    private List<FileSystemSong> getSongsFromMediaStore() {
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
        ArrayList<FileSystemSong> songs = new ArrayList<FileSystemSong>();

        if (musicCursor.getCount()<= 0) return songs;

        int artistIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int nameIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int durIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int pathIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        while (musicCursor.moveToNext()){
            String path = musicCursor.getString(pathIndex);
            if (!path.endsWith(".mp3")) continue;

            FileSystemSong song = new FileSystemSong();
            song.setArtist(musicCursor.getString(artistIndex));
            song.setDuration(musicCursor.getInt(durIndex));
            song.setName(musicCursor.getString(nameIndex));
            song.setId(musicCursor.getInt(idIndex));
            song.setUrl(path);
            songs.add(song);
        }

        return songs;
    }
}
