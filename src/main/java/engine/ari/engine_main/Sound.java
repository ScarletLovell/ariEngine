package engine.ari.engine_main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sound {
    private static List<Sounds> sounds = new ArrayList<>();
    public class Sounds {
        private Media media;
        public MediaPlayer mediaPlayer;
    }
    public Sounds newSound(String file) {
        if(file == null || file.length() < 1) {
            Console.warn("Sound file \'" + file + "\' is not a file!");
            return null;
        }
        File f = new File(file);
        if(!f.isFile()) {
            Console.warn("Sound file \'" + file + "\' is not a file!");
            return null;
        }
        Sounds s = new Sounds();
        s.media = new Media(f.toURI().toString());
        s.mediaPlayer = new MediaPlayer(s.media);
        sounds.add(s);
        return s;
    }
    public void stopAllSounds() {
        for(Sounds s : sounds) {
            s.mediaPlayer.stop();
        }
    }
}
