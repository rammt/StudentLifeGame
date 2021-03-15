package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class ButtonComponent implements Component {
    public Rectangle button = new Rectangle();

    public ButtonComponent create(int height, int width, int x, int y){
        button = new Rectangle(x,y, width, height);
        return this;
    }
}
