package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextFieldComponent implements Component {

    private TextArea textField;
    private Boolean checked = false;

    public TextFieldComponent create(float width, float height, float x, float y, String text, Skin skin/*, final TextButton.TextButtonStyle style*/) {

        textField = new TextArea(text, skin);
        textField.setSize(width,height);
        textField.setPosition(x, y);

        return this;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public TextArea getTextFieldComponent() {
        return textField;
    }
}
