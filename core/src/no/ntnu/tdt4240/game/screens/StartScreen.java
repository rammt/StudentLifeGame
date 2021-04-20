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

public class StartScreen implements Screen{

	private TextButton.TextButtonStyle textButtonStyleDOWN;
	private TextButton.TextButtonStyle textButtonStyleUP;
    private Button copyButton, pasteButton, deliverButton, copyPasteDeliverButton;
	private Button statButton, gameButton, shopButton;
    private boolean copied;
    private boolean pasted;
    private boolean delivered;
    private boolean upgraded = false;


	final StudentLifeGame game;
	public StartScreen(final StudentLifeGame game) {

		this.game = game;
		this.game.getStage().clear();
		copied = false;
		pasted = false;
		delivered = false;

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
		pasteButton = new ButtonElement(x, pasteY, "PASTEBUTTON", game.getSkin(), pasteListener);

		InputListener deliverListener = new InputListener(){
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
		};

		statButton = new TextButton("SHOPS",game.getSkin());
		statButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
		statButton.setPosition(
				Gdx.graphics.getWidth()/2f - deliverButton.getWidth()/2,
				0);
		statButton.addListener(new InputListener(){

		});
		float deliverY = Gdx.graphics.getHeight()/2f - height*1.5f;
		deliverButton = new ButtonElement(x, deliverY, "DELIVERBUTTON", game.getSkin(), deliverListener);

		copyPasteDeliverButton = new TextButton("COPY,PASTE,DELIVER", game.getSkin());
		copyPasteDeliverButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
		copyPasteDeliverButton.setPosition(
				Gdx.graphics.getWidth()/2f - copyPasteDeliverButton.getWidth()/2,
				Gdx.graphics.getHeight()/2f - copyPasteDeliverButton.getHeight()/2);
		copyPasteDeliverButton.addListener(new InputListener(){
		   @Override
		   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			   Entity player = game.getPlayer();
			   PlayerComponent pc = player.getComponent(PlayerComponent.class);
			   pc.setKokCount(pc.getKokCount()+1);
				return true;
		   }});

		float statY = 0;

		InputListener gameListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new StatScreen(game));
				return true;
			}
		};
		gameButton = new ButtonElement(x, statY, "GAME", game.getSkin(), gameListener);

		InputListener shopListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new StatScreen(game));
				return true;
			}
		};
		shopButton = new ButtonElement(x, statY, "SHOP", game.getSkin(), shopListener);

		InputListener statListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new StatScreen(game));
				return true;
			}
		};
		statButton = new ButtonElement(x, statY, "STATS", game.getSkin(), statListener);
/*
		Entity homeButton = game.getEngine().createEntity();
		homeButton.add(new ButtonComponent().create(
				BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
				(SCREENWIDTH/4f)-BUTTONWIDTHGUI/2-10,50,
				"Home",game.getSkin(), new InputListener(){
					@Override
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
						game.setScreen(new StartScreen(game));
						return true;
					}}));
		game.getEngine().addEntity(homeButton);

		Entity settingsButton = game.getEngine().createEntity();
		settingsButton.add(new ButtonComponent().create(
				BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
				(SCREENWIDTH/2f)-BUTTONWIDTHGUI/2,50,
				"Shop",game.getSkin(), new InputListener(){
					@Override
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
						game.setScreen(new ShopScreen(game));
						return true;
					}}));
		game.getEngine().addEntity(settingsButton);

		Entity ShopButton = game.getEngine().createEntity();
		ShopButton.add(new ButtonComponent().create(
				BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
				(SCREENWIDTH*3/4f)-BUTTONWIDTHGUI/2+buttonPadding,50,
				"Stats",game.getSkin(), new InputListener(){
					@Override
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
						game.setScreen(new StatScreen(game));
						return true;
					}}));
		game.getEngine().addEntity(ShopButton);
*/


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
			game.getStage().addActor(statButton);
			game.getStage().addActor(copyButton);
			game.getStage().addActor(pasteButton);
			game.getStage().addActor(deliverButton);
		}
	}

	@Override
	public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

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
