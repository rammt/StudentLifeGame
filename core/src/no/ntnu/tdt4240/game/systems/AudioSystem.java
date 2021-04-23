package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import no.ntnu.tdt4240.game.components.MusicComponent;
import no.ntnu.tdt4240.game.components.SoundComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class AudioSystem extends EntitySystem {

    private ComponentMapper<MusicComponent> mc = ComponentMapper.getFor(MusicComponent.class);
    private ComponentMapper<SoundComponent> sc = ComponentMapper.getFor(SoundComponent.class);

    private ImmutableArray<Entity> sounds;
    private ImmutableArray<Entity> music;

    private Entity backgroundMusic;
    private Entity sound;

    public void createBackgroundMusic(Engine engine){
        backgroundMusic = engine.createEntity();
        backgroundMusic.add(new MusicComponent().create("music/music.mp3"));
        engine.addEntity(backgroundMusic);
    }

    public void createSound(Engine engine){
        sound = engine.createEntity();
        sound.add(new SoundComponent().create("music/penSound.mp3"));
        engine.addEntity(sound);
    }

    public void setSound(String path){
        sc.get(sound).setSound(path);
    }

    public void startBackgroundMusic(){
        mc.get(backgroundMusic).startMusic();
    }

    public void playSound(){
        sc.get(sound).startSound();
    }

    @Override
    public void addedToEngine(Engine engine) {
        sounds = engine.getEntitiesFor(Family.all(SoundComponent.class).get());
        music = engine.getEntitiesFor(Family.all(MusicComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public Engine getEngine() {
        return super.getEngine();
    }

}
