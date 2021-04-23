package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.guiElements.ShopElement;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;


public class ShopScreen implements Screen {

    private ImmutableArray<Entity> rg;
    final StudentLifeGame game;
    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;

    private PlayerComponent pc;
    private ComponentMapper<ResourceGainerComponent> rgm;
    private ComponentMapper<PlayerComponent> pm;
    private ResourceGainSystem rgs;
    private ArrayList<ResourceGainerComponent> resourceGainers;


    public ShopScreen(final StudentLifeGame game, int currentIndex) {

        this.game = game;
        this.game.getStage().clear();
        resourceGainers = new ArrayList<>();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;

        Entity player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);
        rg = game.getEngine().getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
        pm = ComponentMapper.getFor(PlayerComponent.class);
        rgm = ComponentMapper.getFor(ResourceGainerComponent.class);
        rgs = game.getEngine().getSystem(ResourceGainSystem.class);

        for(Entity rg : rg){
            resourceGainers.add(rgm.get(rg));
        }

        //builders
        ShopElement shop = new ShopElement(game, resourceGainers, SCREENWIDTH, SCREENHEIGTH, BUTTONWIDTHGUI, BUTTONHEIGHTGUI,pc,currentIndex);
        NavbarElement navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH );

        for(Button btn : shop.getActors()){
            game.getStage().addActor(btn);
        }
        for(Button btn : navbar.getActors()){
            game.getStage().addActor(btn);
        }
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "Kokt : " + String.valueOf(pc.getKokCount()),
                Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f
        );
        //TODO fiks at dette skjer i shopElementet
        game.getFont().draw(
                game.getBatch(),
                ""+rgs.countResourceGainers(resourceGainers.get(0)),
                25,
                SCREENHEIGTH*5/8f+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+rgs.countResourceGainers(resourceGainers.get(1)),
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+rgs.countResourceGainers(resourceGainers.get(2)),
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+rgs.countResourceGainers(resourceGainers.get(3)),
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3+BUTTONHEIGHTGUI/2
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
