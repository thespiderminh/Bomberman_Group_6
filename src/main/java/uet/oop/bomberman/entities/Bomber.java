package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
//import static uet.oop.bomberman.graphics.Sprite.wall;

public class Bomber extends Entity {

    private final int velo = 3;
    private int velocityX = 0;
    private int velocityY = 0;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    public int getVelo() {
        return velo;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    @Override
    public void update() {
        x += velocityX;
        y += velocityY;

//        if (y < Sprite.wall.getY() + Sprite.SCALED_SIZE) {
//            y += velo;}
//        } else if (y + Sprite.SCALED_SIZE > Sprite.wall.getY()) {
//            y -= velo;
//        }

//        if (x < Sprite.wall.getX() + Sprite.SCALED_SIZE) {
//            x += velo;
//        } else if (x + Sprite.SCALED_SIZE > Sprite.wall.getX()) {
//            x -= velo;
//        }
    }

    public void moveUp() {
        velocityY = -velo;
        this.img = Sprite.player_up_0.getFxImage();
    }

    public void moveDown() {
        velocityY = velo;
        this.img = Sprite.player_down_0.getFxImage();
    }

    public void moveRight() {
        velocityX = velo;
        this.img = Sprite.player_right_0.getFxImage();
    }

    public void moveLeft() {
        velocityX = -velo;
        this.img = Sprite.player_left_0.getFxImage();
    }

    public void stopX() {
        velocityX = 0;
    }

    public void stopY() {
        velocityY = 0;
    }

}
