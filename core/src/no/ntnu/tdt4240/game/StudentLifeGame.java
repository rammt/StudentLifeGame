package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ControlSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.screens.StartScreen;

public class StudentLifeGame extends Game {

	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextureAtlas textureAtlas;

    private float SCREEN_WIDTH;
    private float SCREEN_HEIGHT;
    private PooledEngine engine;
    private Entity game;
    private ShapeRenderer shapeRenderer;

    private int kokCounter;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("skin/font-big-export.fnt"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        kokCounter = 0;

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        engine = new PooledEngine();

        shapeRenderer = new ShapeRenderer();

        engine.addSystem(new AudioSystem());
        engine.addSystem(new RenderSystem(shapeRenderer, font, batch));
        engine.addSystem(new ResourceGainSystem());
        engine.addSystem(new ControlSystem());

        game = engine.createEntity();
        game.add(new GameComponent().create());
        engine.addEntity(game);

        Entity resource = engine.createEntity();
        resource.add(new ResourceComponent().create("Studass"));
        engine.addEntity(resource);

        Entity resourceGainer = engine.createEntity();
        resourceGainer.add(new ResourceGainerComponent().create(100f, 5));
        engine.addEntity(resourceGainer);

        Entity player1 = engine.createEntity();
        player1.add(new PlayerComponent().create("name1", 1));
        engine.addEntity(player1);

        Entity player2 = engine.createEntity();
        player2.add(new PlayerComponent().create("name2", 2));
        engine.addEntity(player2);

        float buttonHeight = 60f;
        float buttonWidth = 140f;

        float copyButtonX = SCREEN_WIDTH / 2;
        float copyButtonY = SCREEN_HEIGHT / 2;

        Entity button = engine.createEntity();
        button.add(new ButtonComponent().create(buttonHeight, buttonWidth, copyButtonX, copyButtonY));
        engine.addEntity(button);

        game.getComponent(GameComponent.class).setState(GameComponent.GameState.GAME_PLAYING);

        this.setScreen(new StartScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
        textureAtlas.dispose();
    }

    public SpriteBatch getBatch(){
        return batch;
    }
    public BitmapFont getFont(){
        return font;
    }
    public Stage getStage(){
        return stage;
    }
    public Skin getSkin(){
        return skin;
    }
    public TextureAtlas getTextureAtlas(){
        return textureAtlas;
    }
    public PooledEngine getEngine() {
        return engine;
    }
    public void setKokCounter(int increment){
        kokCounter = kokCounter + increment;
    }
    public int getKokCounter(){
        return kokCounter;
    }

}
