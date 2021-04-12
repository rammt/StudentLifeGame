package no.ntnu.tdt4240.game.screens;

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
import no.ntnu.tdt4240.game.guiElements.ButtonElement;

public class StartScreen implements Screen{

	private TextButton.TextButtonStyle textButtonStyleDOWN;
	private TextButton.TextButtonStyle textButtonStyleUP;
    private Button copyButton, pasteButton, deliverButton, statButton;
    private boolean copied;
    private boolean pasted;
    private boolean delivered;

    /* progressbar trash
	private TextureRegionDrawable textureRegionDrawable;
	private ProgressBar.ProgressBarStyle progressBarStyle;
    private ProgressBar progressBar;
	private Pixmap pixmap;
	private BitmapFont buttonFont;
     */


    final StudentLifeGame game;

	public StartScreen(final StudentLifeGame game) {
		this.game = game;

		copied = false;
		pasted = false;
		delivered = false;

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

		float width = Gdx.graphics.getWidth()/2f;
		float height = Gdx.graphics.getHeight()/8f;
		float x = Gdx.graphics.getWidth()/2f - width/2;

		//kode for knappene pluss logikk når knappen trykkes

		InputListener copyListener = new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(!copied){
					copied = true;
					copyButton.setStyle(textButtonStyleDOWN);
				}
				return true;
			}
		};

		float copyY = Gdx.graphics.getHeight()/2f + height/2;
		copyButton = new ButtonElement(x, copyY, "COPYBUTTON", game.getSkin(), copyListener);

		InputListener pasteListener = new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(copied && !pasted){
					pasted = true;
					//test
					pasteButton.setStyle(textButtonStyleDOWN);
				}
				return true;
			}
		};

		float pasteY = Gdx.graphics.getHeight()/2f - height/2;

		pasteButton = new ButtonElement(x, pasteY, "DELIVERBUTTON", game.getSkin(), pasteListener);

		InputListener deliverListener = new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(copied && pasted && !delivered){
					copied=false;
					pasted=false;
					game.setKokCounter(1);
					//test
					copyButton.setStyle(textButtonStyleUP);
					pasteButton.setStyle(textButtonStyleUP);
				}
				return true;
			}
		};

		float deliverY = Gdx.graphics.getHeight()/2f - height*1.5f;

		deliverButton = new ButtonElement(x, deliverY, "PASTEBUTTON", game.getSkin(), deliverListener);

		InputListener statListener = new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				game.setScreen(new StatScreen(game));

				return true;
			}
		};

		float statY = 0;

		statButton = new ButtonElement(x, statY, "STATS", game.getSkin(), statListener);

		textButtonStyleDOWN = new TextButton.TextButtonStyle(
				copyButton.getStyle().down,
				copyButton.getStyle().down,
				copyButton.getStyle().down,
				game.getFont()

		);
		textButtonStyleUP = new TextButton.TextButtonStyle(
				copyButton.getStyle().up,
				copyButton.getStyle().down,
				copyButton.getStyle().checked,
				game.getFont()
		);

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
		game.getStage().addActor(copyButton);
		game.getStage().addActor(pasteButton);
		game.getStage().addActor(deliverButton);
		game.getStage().addActor(statButton);
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
			"Kok : " + String.valueOf(game.getKokCounter()),
			Gdx.graphics.getWidth()/3f,
			Gdx.graphics.getHeight()/1.2f
		);
		game.getBatch().end();

		//game.getEngine().update(Gdx.graphics.getDeltaTime());

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
