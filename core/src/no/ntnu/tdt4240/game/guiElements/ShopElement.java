package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.ListIterator;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.screens.ShopScreen;

public class ShopElement {

    private ArrayList<Button> actors;
    private StudentLifeGame game;
    private PlayerComponent pc;
    private ArrayList<ResourceGainerComponent> resourceGainers;
    private final int SCREENWIDTH, SCREENHEIGTH;
    private final float BUTTONWIDTHGUI, BUTTONHEIGHTGUI, BUTTONPADDING;
    int currentIndex,currentPage;
    boolean hasNext, hasPrev;

    public ShopElement(
            StudentLifeGame game, ArrayList<ResourceGainerComponent> resourceGainers,
            int screenwidth, int screenheigth,
            float buttonwidthgui, float buttonheightgui, PlayerComponent pc, int currentIndex)
    {
        this.pc = pc;
        this.game = game;
        this.resourceGainers = resourceGainers;
        SCREENWIDTH = screenwidth;
        SCREENHEIGTH = screenheigth;
        BUTTONWIDTHGUI = buttonwidthgui;
        BUTTONHEIGHTGUI = buttonheightgui;
        BUTTONPADDING = 10;
        actors = new ArrayList<>();
        this.currentIndex = currentIndex;
        currentPage = 1;
        populateShop();
    }

   public void populateShop(){
        if(resourceGainers.isEmpty()){
            //TODO kast en exception kanskje idk
        } else if (resourceGainers.size()<=4){
            shopBuilder(currentIndex);
        } else {
            shopBuilder(currentIndex);
            shopNavBuilder(currentPage);
        }
    }

    private void shopNavBuilder(final int pageIndex) {

        hasNext = (currentIndex<resourceGainers.size()-1);
        hasPrev = (currentIndex>3);

        ButtonElement nextButton = new ButtonElement(
             BUTTONWIDTHGUI,BUTTONHEIGHTGUI/2,
            SCREENWIDTH/2f+10, 75+BUTTONHEIGHTGUI,
            "next", game.getSkin(),
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(hasNext){
                        actors = new ArrayList<>();
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
                        actors = new ArrayList<>();
                        currentIndex=currentIndex-4;
                        game.setScreen(new ShopScreen(game,currentIndex));
                    } else {
                        System.out.println("Sorry brosjan, ingen side bak der");
                    }
                    return true;
                }
            });

        if(hasNext)actors.add(nextButton);
        if(hasPrev)actors.add(backButton);
    }


    public void shopBuilder(int startIndex){

        int counter = 0;

        //for(final ResourceGainerComponent rgc : resourceGainers){
        for(int i = 0; i < 4; i++){
            if(startIndex+i >= resourceGainers.size())break;
            final ResourceGainerComponent rgc = resourceGainers.get(i+startIndex);
            String description = rgc.getDesc() + " " + rgc.getPrice() + ",-";
            ButtonElement tmpButton = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*counter-BUTTONPADDING*counter,
                description, game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(pc.getKokCount() >= rgc.getPrice()){
                            pc.setKokCount(pc.getKokCount() - rgc.getPrice());
                            pc.addResourceGainers(rgc);
                        }
                        return true;
                    }
                });
            counter++;
            actors.add(tmpButton);
        }
    }

    public ArrayList<Button> getActors(){
        return actors;
    }

}
