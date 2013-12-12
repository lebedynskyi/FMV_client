package com.music.fmv.tasks;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.music.fmv.models.FileSystemSong;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

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

    public GetAudioFromStore(Context baseActivity, boolean showDialog, String songsFolder) {
        super(baseActivity, showDialog);
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
               try {
                   FileSystemSong song = extractSongFromFile(file);
                   if (song != null) songsforresult.add(song);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        }

        return songsforresult;
    }

    private FileSystemSong extractSongFromFile(File file) throws Exception{
        String absolutePath = file.getAbsolutePath();

        if (!absolutePath.endsWith(".mp3")) return null;

        MusicMetadataSet src_set = null;
        try {
            src_set = new MyID3().read(file);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        FileSystemSong song = new FileSystemSong();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(absolutePath);

        if (src_set == null){
            song.setName(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            song.setArtist(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        }else {
            IMusicMetadata metadata = src_set.getSimplified();
            song.setName(metadata.getSongTitle());
            song.setArtist(metadata.getArtist());
        }
        song.setDuration(Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
        song.setUrl(absolutePath);
        return song;
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
        int pathIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        while (musicCursor.moveToNext()){
            String path = musicCursor.getString(pathIndex);
            if (!path.endsWith(".mp3")) continue;

            FileSystemSong song = new FileSystemSong();
            song.setArtist(musicCursor.getString(artistIndex));
            song.setDuration(musicCursor.getInt(durIndex));
            song.setName(musicCursor.getString(nameIndex));
            song.setUrl(path);
            songs.add(song);
        }

        return songs;
    }
}
