package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicComponent implements Component {
    private Music music;

    public MusicComponent create(String path){
        this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(true);
        return this;
    }

    public void startMusic(int volume){
        if(music.isPlaying()){
            music.stop();
        }
        else{
            music.play();
            music.setVolume(volume);
        }
    }

    public void stopMusic(){
        music.stop();
    }

    public Boolean isPlaying(){
        return this.music.isPlaying();
    }

    public void setMusic(String path) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public Music getMusic() {
        return music;
    }
}
