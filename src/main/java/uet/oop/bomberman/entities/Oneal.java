package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(Scene scene, long now) {
        //++x;
    }
}
