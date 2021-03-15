package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ButtonComponent implements Component {
    public Rectangle button = new Rectangle();
    public float width;
    public float height;

    public ButtonComponent create(float height, float width, float x, float y) {
        button = new Rectangle(x, y, width, height);
        this.width = width;
        this.height = height;
        return this;
    }

    public ButtonComponent setPos(Vector2 pos) {
        button.setPosition(pos.x, pos.y);
        return this;
    }

    public Vector2 getPos() {
        return new Vector2(button.getX(), button.getY());
    }

    public Rectangle getBounds() {
        return button;
    }
}
