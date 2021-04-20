package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
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

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.screens.LoginScreen;

public class StudentLifeGame extends Game {
	public FirebaseInterface firebase;

	public StudentLifeGame(FirebaseInterface firebase) {
		this.firebase = firebase;
	}

	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextureAtlas textureAtlas;

    private ECSengine engine;
    private ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("skin/font-big-export.fnt"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        shapeRenderer = new ShapeRenderer();

        engine = new ECSengine(shapeRenderer, font, batch, stage, firebase);

        this.setScreen(new LoginScreen(this));
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

    public Entity getPlayer() {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get());
        return players.get(0);
    }
}
