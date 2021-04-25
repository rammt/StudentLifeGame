package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

public class ButtonElement extends Button {
    private Boolean checked = false;

    //public ButtonElement(float width, float height, float x, float y, String text, Skin skin, final TextButton.TextButtonStyle style, InputListener listener) {
    public ButtonElement(float width, float height, float x, float y, String text, Skin skin, InputListener listener) {

        super(skin);
        Label textLabel = new Label(text, skin);
        textLabel.setFontScale(3);
        super.add(textLabel);
        super.setSize(width,height);
        super.setPosition(x, y);
        super.addListener(listener);
        super.setColor(Color.valueOf("#ecf0f1"));
    }

    public ButtonElement(float width, float height, float x, float y, ArrayList<String> textList, Skin skin, InputListener listener) {
        super(skin);

        for (String text : textList) {
            Label textLabel = new Label(text, skin);
            textLabel.setFontScale(3);
            super.add(textLabel);
            super.row();
        }

        super.setSize(width,height);
        super.setPosition(x, y);
        super.addListener(listener);
        super.setColor(Color.valueOf("#ecf0f1"));
    }

    public ButtonElement(float x, float y, String text, Skin skin, InputListener listener) {
        super(skin);
        Label textLabel = new Label(text, skin);
        textLabel.setFontScale(3);
        super.add(textLabel);
        super.setPosition(x, y);
        super.addListener(listener);

        // Default values
        float width =  Gdx.graphics.getWidth()/2f;
        float height = Gdx.graphics.getHeight()/8f;
        super.setSize(width, height);
        super.setColor(Color.valueOf("#ecf0f1"));
    }


    public void disableButton(boolean disabled) {
        if(disabled){
            setColor(Color.valueOf("#2c3e50"));
            setTouchable(Touchable.disabled);
        } else {
            setColor(Color.valueOf("#ecf0f1"));
            setTouchable(Touchable.enabled);
        }
    }


    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
