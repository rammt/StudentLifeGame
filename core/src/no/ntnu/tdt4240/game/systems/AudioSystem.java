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

import java.util.Iterator;

import no.ntnu.tdt4240.game.components.MusicComponent;
import no.ntnu.tdt4240.game.components.SoundComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class AudioSystem extends EntitySystem {

    private ComponentMapper<MusicComponent> mc = ComponentMapper.getFor(MusicComponent.class);
    private ComponentMapper<SoundComponent> sc = ComponentMapper.getFor(SoundComponent.class);

    private ImmutableArray<Entity> sounds;
    private ImmutableArray<Entity> music;

    private Entity backgroundMusic;
    private Entity backgroundMusic2;
    private Entity backgroundMusic3;
    private Entity sound;
    private Entity playingMusic;
    private int volume = 1;

    public void createBackgroundMusic(Engine engine){
        backgroundMusic = engine.createEntity();
        backgroundMusic.add(new MusicComponent().create("music/denvicock.mp3"));
        engine.addEntity(backgroundMusic);

        backgroundMusic2 = engine.createEntity();
        backgroundMusic2.add(new MusicComponent().create("music/LoruskiV2.mp3"));
        engine.addEntity(backgroundMusic2);

        backgroundMusic3 = engine.createEntity();
        backgroundMusic3.add(new MusicComponent().create("music/music.mp3"));
        engine.addEntity(backgroundMusic3);

        playingMusic = backgroundMusic;
    }

    public void createSound(Engine engine){
        sound = engine.createEntity();
        sound.add(new SoundComponent().create("music/penSound.mp3"));
        engine.addEntity(sound);
    }

    public void setMusic(String path){
        mc.get(backgroundMusic).setMusic(path);
    }

    public void skipBackroundMusic(){
        if(mc.get(backgroundMusic).isPlaying()){
            mc.get(backgroundMusic).stopMusic();
            mc.get(backgroundMusic2).startMusic(volume);
        }
        else if(mc.get(backgroundMusic2).isPlaying()){
            mc.get(backgroundMusic2).stopMusic();
            mc.get(backgroundMusic3).startMusic(volume);
        }
        else{
            mc.get(backgroundMusic3).stopMusic();
            mc.get(backgroundMusic).startMusic(volume);
        }

        // TODO: Fix this dynamic method.

        //int counter = 0;
     /*   for(Entity m : music){
            if(m.getComponent(MusicComponent.class).getMusic().isPlaying()){
                m.getComponent(MusicComponent.class).getMusic().stop();
                counter++;
                continue;
            }
            if(counter == 3){
                mc.get(backgroundMusic).startMusic();
                break;
            }
            else{
                m.getComponent(MusicComponent.class).getMusic().play();
                break;
            }

        }*/
    }

    public void setSound(String path){
        sc.get(sound).setSound(path);
    }

    public void mute() {
        volume = (volume == 0) ? 1 : 0;
        for (Entity m : music) {
            m.getComponent(MusicComponent.class).getMusic().stop();
        }

    }

    public void startBackgroundMusic() {
        for (Entity m : music) {
            if (m.getComponent(MusicComponent.class).getMusic().isPlaying()) {
                playingMusic = m;
            }
        }
        mc.get(playingMusic).startMusic(volume);
    }

    public void playSound(){
        sc.get(sound).startSound(volume);
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
