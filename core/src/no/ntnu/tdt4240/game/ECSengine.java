package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.PooledEngine;

import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.systems.SavingSystem;

public class ECSengine {

    private final PooledEngine engine;

    public ECSengine(Firebase firebase) {
        super();

        engine = new PooledEngine();

        engine.addSystem(new AudioSystem());
        engine.addSystem(new ResourceGainSystem());
        engine.addSystem(new SavingSystem());
        engine.addSystem(new OnStartGameSystem(firebase));
    }

    public PooledEngine getEngine() {
        return engine;
    }
}
