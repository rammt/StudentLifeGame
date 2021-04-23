package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import java.util.ArrayList;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.screens.GameScreen;
import no.ntnu.tdt4240.game.screens.StatScreen;
import no.ntnu.tdt4240.game.screens.ShopSelectScreen;

public class NavbarElement {

    private Button statButton, gameButton, shopButton;
    private ArrayList<Button> actors;
    private StudentLifeGame game;
    private int SCREENWIDTH;
    private float BUTTONWIDTHGUI,BUTTONHEIGHTGUI;

    public NavbarElement(StudentLifeGame game, float BUTTONWIDTHGUI, float BUTTONHEIGHTGUI, int SCREENWIDTH){

        this.game = game;
        this.BUTTONHEIGHTGUI = BUTTONHEIGHTGUI;
        this.BUTTONWIDTHGUI = BUTTONWIDTHGUI;
        this.SCREENWIDTH = SCREENWIDTH;
        actors = new ArrayList<>();
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
            (SCREENWIDTH*3/4f)-SCREENWIDTH/4f/2+10, 50,
            "SHOP", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new ShopSelectScreen(game));
            return true;
            }
        });

        this.statButton = new ButtonElement(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
            (SCREENWIDTH/2f)-SCREENWIDTH/4f/2, 50,
            "STATS", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new StatScreen(game));
            return true;
            }
        });

        actors.add(statButton);
        actors.add(shopButton);
        actors.add(gameButton);

    }

    public ArrayList<Button> getActors() {
        return actors;
    }

}
