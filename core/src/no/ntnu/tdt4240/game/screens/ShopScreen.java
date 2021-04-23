package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;


public class ShopScreen implements Screen {

    private Button buy1Button, buy2Button, buy3Button, buy4Button;
    private ImmutableArray<Entity> rg;
    final StudentLifeGame game;
    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;

    private int counterEidBuy1,counterEidBuy2,counterEidBuy3,counterEidBuy4;
    private String buy1String,buy2String,buy3String,buy4String;
    private PlayerComponent pc;
    private ComponentMapper<ResourceGainerComponent> rgm;
    private ComponentMapper<PlayerComponent> pm;
    private ResourceGainSystem rgs;
    private float[] prices;
    private final AudioSystem as;


    public ShopScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;
        prices = new float[4];


        Entity player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);
        rg = game.getEngine().getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
        pm = ComponentMapper.getFor(PlayerComponent.class);
        rgm = ComponentMapper.getFor(ResourceGainerComponent.class);
        rgs = game.getEngine().getSystem(ResourceGainSystem.class);

        as = game.getEngine().getSystem(AudioSystem.class);
        as.setSound(game.getEngine(), "music/ka-ching.mp3");

        //TODO iterer????
        prices[0] = rgm.get(rg.get(0)).getPrice();
        prices[1] = rgm.get(rg.get(1)).getPrice();
        prices[2] = rgm.get(rg.get(2)).getPrice();
        prices[3] = rgm.get(rg.get(3)).getPrice();

        //TODO lmao, burde hente alle resourcecompontene og bygge dem

        counterEidBuy1 = rgs.countResourceGainers(rgm.get(rg.get(0)));
        buy1String = "Betalte kokere "+ prices[0] +",-";

        buy1Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f,
                buy1String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prices[0]){
                    counterEidBuy1++;

                    pc.setKokCount(pc.getKokCount()-prices[0]);
                    pc.addResourceGainers(rgm.get(rg.get(0)));
                    as.playSound(game.getEngine());
                }
                return true;
            }
        });

        counterEidBuy2= rgs.countResourceGainers(rgm.get(rg.get(1)));

        buy2String = "Studass som koker "+prices[1] +",-";

        buy2Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI-buttonPadding,
                buy2String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prices[3]){
                    counterEidBuy2++;

                    pc.setKokCount(pc.getKokCount()-prices[1]);
                    pc.addResourceGainers(rgm.get(rg.get(1)));
                    as.playSound(game.getEngine());
                }
                return true;
            }
        });

        counterEidBuy3= rgs.countResourceGainers(rgm.get(rg.get(2)));

        buy3String = "kok scripts "+prices[2] +",-";

        buy3Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2-buttonPadding*2,
                buy3String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prices[2]){
                    counterEidBuy3++;
                    pc.setKokCount(pc.getKokCount()-prices[2]);
                    pc.addResourceGainers(rgm.get(rg.get(2)));
                    as.playSound(game.getEngine());
                }
                return true;
            }
        });

        counterEidBuy4 = rgs.countResourceGainers(rgm.get(rg.get(3)));
        buy4String = "Kok Hackere "+prices[3] +",-";

        buy4Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-buttonPadding*3,
                buy4String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prices[3]){
                    counterEidBuy4++;
                    pc.setKokCount(pc.getKokCount()-prices[3]);
                    pc.addResourceGainers(rgm.get(rg.get(3)));
                    as.playSound(game.getEngine());
                }
                return true;
            }
        });

        //navbarkode
        NavbarElement navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH );
        Button[] navbarActors = navbar.getActors();

        for(Button btn : navbarActors){
            System.out.println("add button");
            game.getStage().addActor(btn);
        }
        game.getStage().addActor(buy1Button);
        game.getStage().addActor(buy2Button);
        game.getStage().addActor(buy3Button);
        game.getStage().addActor(buy4Button);
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
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy1,
                25,
                SCREENHEIGTH*5/8f+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy2,
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy3,
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                "" + counterEidBuy4,
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
