package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent implements Component {
    private Sound sound;

    public SoundComponent create(String path) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
        return this;
    }

    public void startSound(int volume) {
        sound.play(volume);
    }

    public void setSound(String path) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public Sound getSound() {
        return sound;
    }
}
