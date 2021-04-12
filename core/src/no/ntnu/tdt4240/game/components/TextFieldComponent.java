package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.sun.java.swing.action.AlignLeftAction;

public class TextFieldComponent extends Actor implements Component {

    private Label textField;
    private Boolean checked = false;
    private Label label;
    private TextureRegionDrawable textureRegionDrawableBg;

    public TextFieldComponent create(int value, String text, Skin skin/*, final TextButton.TextButtonStyle style*/) {

        textField = new Label(text + "\n " + value, skin);
        textField.setWrap(true);
        //textField.setSize(width,height);
        //textField.setPosition(x, y);
        textField.setFontScale(3);
        textField.setAlignment(Align.center);


        Pixmap labelColor = new Pixmap(500, 100, Pixmap.Format.RGB888);
        labelColor.setColor(Color.BLUE);
        labelColor.fill();
        textField.getStyle().background = new Image(new Texture(labelColor)).getDrawable();

        return this;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Label getTextFieldComponent() {
        return textField;
    }
}
