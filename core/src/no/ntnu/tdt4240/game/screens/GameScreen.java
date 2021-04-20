package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.components.PlayerComponent;

public class GameScreen implements Screen{

	private TextButton.TextButtonStyle textButtonStyleDOWN;
	private TextButton.TextButtonStyle textButtonStyleUP;
    private Button copyButton, pasteButton, deliverButton, copyPasteDeliverButton;
	private Button statButton, gameButton, shopButton;
    private boolean copied;
    private boolean pasted;
    private boolean delivered;
    private boolean upgraded = false;
    private int SCREENWIDTH, SCREENHEIGHT,BUTTONHEIGHTGUI,BUTTONWIDTHGUI;


	final StudentLifeGame game;
	public GameScreen(final StudentLifeGame game) {

		this.game = game;
		this.game.getStage().clear();
		copied = false;
		pasted = false;
		delivered = false;

		SCREENHEIGHT = Gdx.graphics.getHeight();
		SCREENWIDTH = Gdx.graphics.getWidth();
		BUTTONHEIGHTGUI = SCREENHEIGHT/8;
		BUTTONWIDTHGUI = SCREENWIDTH/4;

		//TODO æsj fiks dette her
		float width = Gdx.graphics.getWidth()/2f;
		float height = Gdx.graphics.getHeight()/8f;
		float x = Gdx.graphics.getWidth()/2f - width/2;
		float copyY = Gdx.graphics.getHeight()/2f + height/2;
		float pasteY = Gdx.graphics.getHeight()/2f - height/2;
		float deliverY = Gdx.graphics.getHeight()/2f - height*1.5f;
		//kode for knappene pluss logikk når knappen trykkes

		copyButton = new ButtonElement(
				x, copyY,
				"COPY", game.getSkin(), new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(!copied){
					copied = true;
					copyButton.setStyle(textButtonStyleDOWN);
				}
				return true;
			}
		});

		pasteButton = new ButtonElement(
				x, pasteY,
				"PASTEBUTTON", game.getSkin(), new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(copied && !pasted){
					pasted = true;
					//test
					pasteButton.setStyle(textButtonStyleDOWN);
				}
				return true;
			}
		});

		deliverButton = new ButtonElement(
				x, deliverY,
				"DELIVER", game.getSkin(), new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(copied && pasted && !delivered){
					copied=false;
					pasted=false;
					Entity player = game.getPlayer();
					PlayerComponent pc = player.getComponent(PlayerComponent.class);
					pc.setKokCount(pc.getKokCount()+1);

					copyButton.setStyle(textButtonStyleUP);
					pasteButton.setStyle(textButtonStyleUP);
				}
				return true;
			}
		});

		gameButton = new ButtonElement(
				BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
				(SCREENWIDTH/4f)-SCREENWIDTH/4f/2-10, 50,
				"GAME", game.getSkin(), new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
				return true;
			}
		});

		shopButton = new ButtonElement(
			BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
			(SCREENWIDTH*3/4f)-SCREENWIDTH/4f/2+10, 50,
			"SHOP", game.getSkin(), new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new ShopScreen(game));
				return true;
			}
		});

		statButton = new ButtonElement(
			BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
			(SCREENWIDTH/2f)-SCREENWIDTH/4f/2, 50,
			"STATS", game.getSkin(), new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new StatScreen(game));
				return true;
			}
		});


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


		//legger til aktors
		if(upgraded){
			game.getStage().addActor(copyPasteDeliverButton);
		}
		else{
			game.getStage().addActor(copyButton);
			game.getStage().addActor(pasteButton);
			game.getStage().addActor(deliverButton);
		}
		game.getStage().addActor(statButton);
		game.getStage().addActor(gameButton);
		game.getStage().addActor(shopButton);
	}

	@Override
	public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
		game.getStage().act();
		game.getStage().draw();
		//batch tegner vi resten på
		game.getBatch().begin();
		Entity player = game.getPlayer();
		PlayerComponent pc = player.getComponent(PlayerComponent.class);
		game.getFont().draw(
			game.getBatch(),
			"Kok : " + pc.getKokCount(),
			Gdx.graphics.getWidth()/3f,
			Gdx.graphics.getHeight()/1.2f
		);
		game.getBatch().end();

		game.getEngine().update(Gdx.graphics.getDeltaTime());

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