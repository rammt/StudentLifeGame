package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class StatScreen implements Screen{

    private TextButton.TextButtonStyle textButtonStyleDOWN;
    private TextButton.TextButtonStyle textButtonStyleUP;
    private Button copyButton, pasteButton, deliverButton, gameButton;
    private TextFieldComponent kokCount;
    private boolean copied;
    private boolean pasted;
    private boolean delivered;

    private int counter;

    /* progressbar trash
	private TextureRegionDrawable textureRegionDrawable;
	private ProgressBar.ProgressBarStyle progressBarStyle;
    private ProgressBar progressBar;
	private Pixmap pixmap;
	private BitmapFont buttonFont;
     */


    final StudentLifeGame game;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;

        game.getStage().clear();

        //progressbar shit, se bort trash
		/*
		pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		game.getSkin().add("white", new Texture(pixmap));
		textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(
			new Texture(Gdx.files.internal("skin/glassy-ui.png"))));
		progressBarStyle = new ProgressBar.ProgressBarStyle(
			game.getSkin().newDrawable("white", Color.DARK_GRAY), textureRegionDrawable);
		progressBarStyle.knobBefore = progressBarStyle.knob;
		 */

        kokCount = new TextFieldComponent(100,100);

        gameButton = new TextButton("back",game.getSkin());
        gameButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
        gameButton.setPosition(
                Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2,
                0);
        gameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new StartScreen(game));

                return true;
            }
        });

        textButtonStyleDOWN = new TextButton.TextButtonStyle(
                gameButton.getStyle().down,
                gameButton.getStyle().down,
                gameButton.getStyle().down,
                game.getFont()

        );
        textButtonStyleUP = new TextButton.TextButtonStyle(
                gameButton.getStyle().up,
                gameButton.getStyle().down,
                gameButton.getStyle().checked,
                game.getFont()
        );

        Entity testButton = game.getEngine().getEngine().createEntity();
        testButton.add(new ButtonComponent().create(
                Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/8f,
                Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2,
                Gdx.graphics.getHeight()/2f - gameButton.getHeight()/2,
                "test", game.getSkin(), textButtonStyleUP));
        game.getEngine().getEngine().addEntity(testButton);

		/*
		//progressbar på hvor langt du har kommet
		progressBar = new ProgressBar(0, 10, 0.5f, true,
			game.getSkin(), "default-horizontal");
		progressBar.setPosition(Gdx.graphics.getWidth()/7f,Gdx.graphics.getWidth()/2f );
		progressBar.setSize(copyButton.getWidth()/10,copyButton.getHeight()*3);
		progressBar.setAnimateDuration(2);
		 */

        //legger til aktors
        //game.getStage().addActor(progressBar);
        game.getStage().addActor(gameButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "Kokt : " + String.valueOf(game.getKokCounter()),
                Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f
        );
        game.getBatch().end();

        game.getEngine().getEngine().update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}
