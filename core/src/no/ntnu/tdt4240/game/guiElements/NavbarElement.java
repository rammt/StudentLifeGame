package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.screens.GameScreen;
import no.ntnu.tdt4240.game.screens.SettingScreen;
import no.ntnu.tdt4240.game.screens.StatScreen;
import no.ntnu.tdt4240.game.screens.ShopSelectScreen;
import no.ntnu.tdt4240.game.systems.AudioSystem;

public class NavbarElement {

    private Button statButton, gameButton, shopButton;
    private ArrayList<Button> actors;
    private StudentLifeGame game;
    private int SCREENWIDTH;
    private float BUTTONWIDTHGUI,BUTTONHEIGHTGUI;
    private AudioSystem as;
    private Texture settings;

    public NavbarElement(StudentLifeGame game, float BUTTONWIDTHGUI, float BUTTONHEIGHTGUI, int SCREENWIDTH){

        this.settings = new Texture("images/iconfinder_ic_settings_48px_3669250.png");
        this.game = game;
        this.BUTTONHEIGHTGUI = BUTTONHEIGHTGUI;
        this.BUTTONWIDTHGUI = BUTTONWIDTHGUI;
        this.SCREENWIDTH = SCREENWIDTH;
        this.actors = new ArrayList<>();
        populateActors();

    }

    private void populateActors(){
        this.gameButton = new ButtonElement(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
            (SCREENWIDTH/4f)-SCREENWIDTH/4f/2-10, 50,
            "GAME", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });

        this.shopButton = new ButtonElement(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
        (SCREENWIDTH/2f)-SCREENWIDTH/4f/2, 50,
            "SHOP", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopSelectScreen(game));
                return true;
            }
        });



        this.statButton = new ButtonElement(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
            (SCREENWIDTH*3/4f)-SCREENWIDTH/4f/2+10, 50,
            "", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingScreen(game));
                return true;
            }
        });

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(settings));
        ImageButton playButton = new ImageButton(drawable);

        actors.add(statButton);
        actors.add(shopButton);
        actors.add(gameButton);
        actors.add(playButton);

    }

    public ArrayList<Button> getActors() {
        return actors;
    }

}
