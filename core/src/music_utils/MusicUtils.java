package music_utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

/**
 * Created by Karlo on 2017-06-21.
 */

public class MusicUtils {

    private MusicUtils() {

    }

    public static void playMusicSequence(ArrayList<Music> musics, float volume) {
        if(musics != null && !musics.isEmpty()) {
            Music m = musics.get(0);
            m.setLooping(false);
            m.setVolume(volume);
            m.play();
            m.setOnCompletionListener(new OnCompletionPlayer(musics, volume));
        }
    }

    static class OnCompletionPlayer implements Music.OnCompletionListener{

        private ArrayList<Music> musicList;
        private float volume;

        public OnCompletionPlayer(ArrayList<Music> music, float volume){
            this.musicList = music;
            this.volume = volume;
        }

        @Override
        public void onCompletion(Music music) {
            musicList.remove(0);
            playMusicSequence(musicList, volume);
        }
    }

}
