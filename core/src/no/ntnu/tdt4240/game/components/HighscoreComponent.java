package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HighscoreComponent implements Component {
    private List<Map<String,Object>> highscoreList = new ArrayList<>();

    public HighscoreComponent create(){
        return this;
    }

    public void setHighscoreList(List<Map<String, Object>> highscoreList) {
        this.highscoreList = highscoreList;
    }

    public List<Map<String, Object>> getHighscoreList() {
        return highscoreList;
    }
}
