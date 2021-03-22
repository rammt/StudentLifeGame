package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class TextFieldComponent implements Component {

    public Polygon bounds = new Polygon();
    public float width;
    public float height;

    public TextFieldComponent create(float width, float height) {
        this.bounds = new Polygon(new float[]{0, 0, width, 0, width, height, 0, height});
        this.width = width;
        this.height = height;
        return this;
    }

    public TextFieldComponent setPos(Vector2 pos) {
        bounds.setPosition(pos.x, pos.y);
        return this;
    }

    public Vector2 getPos() {
        return new Vector2(bounds.getX(), bounds.getY());
    }

    public Polygon getBounds() {
        return bounds;
    }

    public Polygon getTextFieldComponent() {
        return bounds;
    }

}
