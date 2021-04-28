package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class TextFieldElement {

    private Label textField;
    private Boolean checked = false;

    public TextFieldElement(String value, String text, Skin skin, int size, boolean isColor) {

        if (value != null) {
            textField = new Label(text + "\n " + value, skin);
        } else {
            textField = new Label(text, skin);
        }

        textField.setWrap(true);

        textField.setFontScale(size);
        textField.setAlignment(Align.center);

        Label.LabelStyle style = new Label.LabelStyle(textField.getStyle());

        // set label background color
        if (isColor) {
            Pixmap labelColor = new Pixmap(500, 100, Pixmap.Format.RGB888);
            labelColor.setColor(157f / 255f, 173f / 255f, 188f / 255f, 0);
            labelColor.fill();
            style.background = new Image(new Texture(labelColor)).getDrawable();
            textField.setStyle(style);
        }


    }

    public Label getActor(){
        return textField;
    }

}
