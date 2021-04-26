package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.screens.ShopScreen;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;

public class ShopElement {

    private ArrayList<Button> btnActors;
    private ArrayList<Label> labelActors;
    private StudentLifeGame game;
    private PlayerComponent pc;
    private ArrayList<ResourceGainerComponent> resourceGainers;
    private ResourceGainSystem rgs;
    private AudioSystem as;
    private final int SCREENWIDTH;
    private final int SCREENHEIGTH;
    private int currentIndex;
    private final float BUTTONWIDTHGUI, BUTTONHEIGHTGUI, BUTTONPADDING;
    boolean hasNext, hasPrev;

    public ShopElement(
            StudentLifeGame game, ArrayList<ResourceGainerComponent> resourceGainers,
            int screenwidth, int screenheigth,
            float buttonwidthgui, float buttonheightgui, PlayerComponent pc, int currentIndex)
    {
        this.pc = pc;
        this.game = game;
        this.currentIndex = currentIndex;
        this.resourceGainers = resourceGainers;
        BUTTONPADDING = 10;
        SCREENWIDTH = screenwidth;
        SCREENHEIGTH = screenheigth;
        BUTTONWIDTHGUI = buttonwidthgui;
        BUTTONHEIGHTGUI = buttonheightgui;
        btnActors = new ArrayList<>();
        labelActors = new ArrayList<>();
        rgs = game.getEngine().getSystem(ResourceGainSystem.class);
        populateShop();
    }

   public void populateShop(){
        if(resourceGainers.isEmpty()){
            //TODO kast en exception kanskje idk
        } else if (resourceGainers.size()<=4){
            shopBuilder(currentIndex);
        } else {
            shopBuilder(currentIndex);
            shopNavBuilder();
        }
    }

    private void shopNavBuilder() {

        hasNext = (currentIndex+4<resourceGainers.size()-1);
        hasPrev = (currentIndex>3);

        ButtonElement nextButton = new ButtonElement(
             BUTTONWIDTHGUI,BUTTONHEIGHTGUI/2,
            SCREENWIDTH/2f+10, 75+BUTTONHEIGHTGUI,
            "next", game.getSkin(),
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(hasNext){
                        btnActors = new ArrayList<>();
                        labelActors = new ArrayList<>();
                        currentIndex=currentIndex+4;
                        game.setScreen(new ShopScreen(game,currentIndex));
                    } else {
                        System.out.println("Sorry brosjan, ingen side framme der");
                    }
                    return true;
                }
            });

        ButtonElement backButton = new ButtonElement(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI/2,
            SCREENWIDTH/2f-10-BUTTONWIDTHGUI, 75+BUTTONHEIGHTGUI,
            "back", game.getSkin(),
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(hasPrev){
                        btnActors = new ArrayList<>();
                        labelActors = new ArrayList<>();
                        currentIndex=currentIndex-4;
                        game.setScreen(new ShopScreen(game,currentIndex));
                    } else {
                        System.out.println("Sorry brosjan, ingen side bak der");
                    }
                    return true;
                }
            });

        if(hasNext)btnActors.add(nextButton);
        if(hasPrev)btnActors.add(backButton);
    }


    public void shopBuilder(int startIndex){

        int counter = 0;

        as = game.getEngine().getSystem(AudioSystem.class);
        as.setSound("music/ka-ching.mp3");

        //for(final ResourceGainerComponent rgc : resourceGainers){
        for(int i = 0; i < 4; i++){
            if(startIndex+i >= resourceGainers.size())break;
            final ResourceGainerComponent rgc = resourceGainers.get(i+startIndex);
            shopLabelBuilder(rgs.countResourceGainers(rgc),25,(SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*i+BUTTONHEIGHTGUI/2));
            final float gainerPrice = (float) (rgc.getPrice() * Math.pow(1.10, rgs.countResourceGainers(rgc)-1));
            String description = rgc.getName();
            String price = "Price: " + gainerPrice + " kok";
            String kokPerSec = "Kok gain: " + rgc.getGainPerSecond() + "/s";
            ArrayList<String> buttonText = new ArrayList<>(Arrays.asList(description, price, kokPerSec));
            ButtonElement tmpButton = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*counter-BUTTONPADDING*counter,
                buttonText, game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(pc.getKokCount() >= gainerPrice){
                            as.playSound();
                            pc.setKokCount(pc.getKokCount() - gainerPrice);
                            pc.addResourceGainer(rgc);
                            game.setScreen(new ShopScreen(game,currentIndex));
                        }
                        return true;
                    }
                });
            counter++;
            if(gainerPrice > pc.getKokCount()){
                tmpButton.disableButton(true);
            }
            btnActors.add(tmpButton);
        }
    }

    private void shopLabelBuilder(int count, float x, float y){
        String text = ""+count;
        Label ownedCountLabel = new Label(text, game.getSkin());
        ownedCountLabel.setPosition(x,y);
        ownedCountLabel.setFontScale(3f);
        labelActors.add(ownedCountLabel);
    }

    public ArrayList<Button> getButtonActors(){
        return btnActors;
    }
    public ArrayList<Label> getLabelActors(){
        return labelActors;
    }

}
