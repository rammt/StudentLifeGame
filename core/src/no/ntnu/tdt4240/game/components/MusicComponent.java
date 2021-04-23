package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicComponent implements Component {
    private Music music;

    public MusicComponent create(String path){
        this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
        return this;
    }

    public void startMusic(){
        if(music.isPlaying()){
            music.stop();
        }
        else{
            music.play();
            music.setVolume(1);
        }
    }

    public void setMusic(String path) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public Music getMusic() {
        return music;
    }
}
