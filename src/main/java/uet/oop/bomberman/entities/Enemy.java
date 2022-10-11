package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Enemy extends Entity {
    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(Scene scene, long now) {

    }
}
