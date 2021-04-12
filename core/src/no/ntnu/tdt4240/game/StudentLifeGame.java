package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
import no.ntnu.tdt4240.game.screens.LoginScreen;
import no.ntnu.tdt4240.game.screens.ShopScreen;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ControlSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.screens.StartScreen;

public class StudentLifeGame extends Game {
	public FirebaseInterface firebase;

	public StudentLifeGame(FirebaseInterface firebase) {
		this.firebase = firebase;
	}

	private String user;

	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextureAtlas textureAtlas;

    private ECSengine engine;
    private ShapeRenderer shapeRenderer;

    private int kokCounter;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("skin/font-big-export.fnt"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        kokCounter = 1000;

        shapeRenderer = new ShapeRenderer();

        engine = new ECSengine(shapeRenderer,font,batch,stage);

        this.setScreen(new ShopScreen(this));
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
        return engine.getEngine();
    }
    public void setKokCounter(int increment){
        kokCounter = kokCounter + increment;
    }
    public int getKokCounter(){
        return kokCounter;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public ShapeRenderer getShapeRenderer(){
        return shapeRenderer;
    }
}
