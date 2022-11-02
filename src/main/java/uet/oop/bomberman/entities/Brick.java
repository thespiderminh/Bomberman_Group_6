package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.getStillObjects;

public class Brick extends Entity {
    List<String> itemType = Arrays.asList("SpeedItem", "BombItem", "WallPassItem", "null");
    String type = "notInit";
    public Brick(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(Scene scene, long now) {

    }

    public void getItem() {
        if (type.equals("notInit")) {
            type = itemType.get((int)(Math.round(Math.random() * 10)) % itemType.size());
        }

        if (type.equals("SpeedItem")) {
            getStillObjects().add(new SpeedItem(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.powerup_speed.getFxImage()));
            this.img = null;
        } else if (type.equals("BombItem")) {
            getStillObjects().add(new BombItem(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.powerup_bombs.getFxImage()));
            this.img = null;
        } else if (type.equals("WallPassItem")) {
            getStillObjects().add(new WallPassItem(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.powerup_wallpass.getFxImage()));
            this.img = null;
        }
    }
}
