package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonComponent implements Component {
    private Button textButton;
    private Boolean checked = false;

    public ButtonComponent create(float width, float height, float x, float y, String text, Skin skin, final TextButton.TextButtonStyle style) {

        textButton = new TextButton(text, skin);
        textButton.setSize(width,height);
        textButton.setPosition(x, y);
        textButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!checked){
                    checked = true;

                    textButton.setStyle(style);
                }
                return true;
            }
        });

        return this;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Button getTextButton() {
        return textButton;
    }
}