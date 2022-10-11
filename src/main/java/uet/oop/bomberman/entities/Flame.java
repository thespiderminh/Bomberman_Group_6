package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Bomb {


    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean flammable() {
        return !(BombermanGame.map[this.x / Sprite.SCALED_SIZE][this.y / Sprite.SCALED_SIZE] == '#');
    }

    @Override
    public void update(Scene scene, long now) {

    }
}