package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Bomb extends Entity {
    private static int numberOfBombs = 3;
    private static int numberOfBombsOnScreen = 0;
    private static final long	BOMB_TIME	= 2000000000L;
    private long startTime	= 0;
    private boolean	firstUpdate	= false;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void getBombs(Bomber bomberman, long now) {
        if (numberOfBombsOnScreen < numberOfBombs) {
            Bomb bomb = new Bomb((int)(bomberman.getCenterX() / Sprite.SCALED_SIZE),
                    (int)(bomberman.getCenterY() / Sprite.SCALED_SIZE),
                    Sprite.bomb.getFxImage());
            getEntities().add(bomb);
            numberOfBombsOnScreen++;
        }
    }

    @Override
    public void update(Scene scene, long now) {
        if (!firstUpdate) {
            firstUpdate = true;
            startTime = now;
        }

        if (firstUpdate) {
            if( now-startTime <= 500000000L) { // 0.5s
                this.img = Sprite.bomb.getFxImage();
            }else if( now-startTime <= 1000000000L) { // 1s
                this.img = Sprite.bomb_1.getFxImage();
            }else if (now-startTime <= 1500000000L) { // 1.5s
                this.img = Sprite.bomb_2.getFxImage();
            }else if (now-startTime>=BOMB_TIME) {
                exploid();
            }
        }
    }

    private void exploid() {
        img = null;
        numberOfBombsOnScreen--;
    }
}