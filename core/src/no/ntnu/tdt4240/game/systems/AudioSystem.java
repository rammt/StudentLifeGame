package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.game.components.MusicComponent;
import no.ntnu.tdt4240.game.components.SoundComponent;


public class AudioSystem extends EntitySystem {

    private final ComponentMapper<MusicComponent> mc = ComponentMapper.getFor(MusicComponent.class);
    private final ComponentMapper<SoundComponent> sc = ComponentMapper.getFor(SoundComponent.class);

    private ImmutableArray<Entity> sounds;
    private ImmutableArray<Entity> music;

    private Entity backgroundMusic;
    private Entity backgroundMusic2;
    private Entity backgroundMusic3;
    private Entity sound;
    private Entity playingMusic;
    private int volumeMusic = 1;
    private int volumeSound = 1;

    public void createBackgroundMusic(Engine engine) {
        backgroundMusic = engine.createEntity();
        backgroundMusic.add(new MusicComponent().create("music/denvicock.mp3"));
        engine.addEntity(backgroundMusic);

        backgroundMusic2 = engine.createEntity();
        backgroundMusic2.add(new MusicComponent().create("music/LoruskiV2.mp3"));
        engine.addEntity(backgroundMusic2);

        backgroundMusic3 = engine.createEntity();
        backgroundMusic3.add(new MusicComponent().create("music/music.mp3"));
        engine.addEntity(backgroundMusic3);

        playingMusic = backgroundMusic2;
    }

    public void createSound(Engine engine) {
        sound = engine.createEntity();
        sound.add(new SoundComponent().create("music/penSound.mp3"));
        engine.addEntity(sound);
    }

    public void setMusic(String path) {
        mc.get(backgroundMusic).setMusic(path);
    }

    public void skipBackroundMusic() {
        if (mc.get(backgroundMusic).isPlaying()) {
            mc.get(backgroundMusic).stopMusic();
            mc.get(backgroundMusic2).startMusic(volumeMusic);
        } else if (mc.get(backgroundMusic2).isPlaying()) {
            mc.get(backgroundMusic2).stopMusic();
            mc.get(backgroundMusic3).startMusic(volumeMusic);
        } else {
            mc.get(backgroundMusic3).stopMusic();
            mc.get(backgroundMusic).startMusic(volumeMusic);
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

    public void setSound(String path) {
        sc.get(sound).setSound(path);
    }

    public void muteMusic() {
        stopBackgroundMusic();
        volumeMusic = (volumeMusic == 0) ? 1 : 0;
        startBackgroundMusic();
    }

    public void muteSound() {
        volumeSound = (volumeSound == 0) ? 1 : 0;
    }

    public boolean isMusicPlaying() {
        return volumeMusic == 0;
    }

    public boolean isSoundPlaying() {
        return volumeSound == 0;
    }

    public void startBackgroundMusic() {
        for (Entity m : music) {
            if (m.getComponent(MusicComponent.class).getMusic().isPlaying()) {
                playingMusic = m;
            }
        }
        mc.get(playingMusic).startMusic(volumeMusic);
    }

    public void stopBackgroundMusic() {
        for (Entity m : music) {
            if (m.getComponent(MusicComponent.class).getMusic().isPlaying()) {
                playingMusic = m;
            }
        }
        mc.get(playingMusic).stopMusic();
    }

    public void startShopMusic() {
        if (!mc.get(backgroundMusic).isPlaying()) {
            stopBackgroundMusic();
            mc.get(backgroundMusic).startMusic(volumeMusic);
        }
    }

    public void startGameMusic() {
        if (!mc.get(backgroundMusic2).isPlaying()) {
            stopBackgroundMusic();
            mc.get(backgroundMusic2).startMusic(volumeMusic);
        }
    }

    public void playSound() {
        sc.get(sound).startSound(volumeSound);
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
