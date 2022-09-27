package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

    private final int velo = 1;
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
        move();
        changeTheAnimation();
    }

    public void moveUp() {
        velocityY = -velo;
    }

    public void moveDown() {
        velocityY = velo;
    }

    public void moveRight() {
        velocityX = velo;
    }

    public void moveLeft() {
        velocityX = -velo;
    }

    public void stopX() {
        velocityX = 0;
    }

    public void stopY() {
        velocityY = 0;
    }

    private void move() {
        x += velocityX;
        y += velocityY;

        if (x <= Sprite.SCALED_SIZE) {
            x = Sprite.SCALED_SIZE;
            stopX();
        }

        if (x >= (WIDTH - 1) * Sprite.SCALED_SIZE) {
            x = (WIDTH - 1) * Sprite.SCALED_SIZE;
            stopX();
        }

        if (y <= Sprite.SCALED_SIZE) {
            y = Sprite.SCALED_SIZE;
            stopY();
        }

        if (y >= (HEIGHT - 1) * Sprite.SCALED_SIZE) {
            y = (HEIGHT - 1) * Sprite.SCALED_SIZE;
            stopY();
        }

        for(Entity w : getStillObjects()) {
            if (w instanceof Wall || w instanceof Brick) {
                if (velocityY != 0) {
                    if (x + Sprite.SCALED_SIZE > w.getX() && x < w.getX() + Sprite.SCALED_SIZE) {
                        if (y + Sprite.SCALED_SIZE > w.getY() && y < w.getY() + Sprite.SCALED_SIZE) {
                            y -= velocityY;
                            if (x + Sprite.SCALED_SIZE / 3 < w.getX()) {
                                x -= velo;
                            } else if (x > w.getX() + Sprite.SCALED_SIZE / 3){
                                x += velo;
                            }
                            break;
                        }
                    }
                }

                if (velocityX != 0) {
                    if (y + Sprite.SCALED_SIZE > w.getY() && y < w.getY() + Sprite.SCALED_SIZE){
                        if (x < w.getX() + Sprite.SCALED_SIZE && x + Sprite.SCALED_SIZE > w.getX()){
                            x -= velocityX;
                            if (y + Sprite.SCALED_SIZE / 3 < w.getY()) {
                                y -= velo;
                            } else if (y > w.getY() + Sprite.SCALED_SIZE / 3){
                                y += velo;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public void changeTheAnimation() {
        if (velocityY > 0) {
            this.img = Sprite.player_down_0.getFxImage();
        } else if (velocityY < 0) {
            this.img = Sprite.player_up_0.getFxImage();
        } else if (velocityX > 0) {
            this.img = Sprite.player_right_0.getFxImage();
        } else if (velocityX < 0) {
            this.img = Sprite.player_left_0.getFxImage();
        }
    }
}
