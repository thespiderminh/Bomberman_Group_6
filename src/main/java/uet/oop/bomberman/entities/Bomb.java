package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class Bomb extends Entity {
    protected static int numberOfBombs = 3;
    protected static int numberOfBombsOnScreen = 0;
    private static final long	BOMB_TIME	= 2000000000L;
    private long startTime	= 0;
    private long existTime = 0;
    private long explodeTime = 0;
    private boolean	onScreen	= false;
    private boolean exploding = false;
    private int typeOfBomb = 0;
    private final List<Image> bombAnimation = Arrays.asList(Sprite.bomb.getFxImage(),Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage());
    private final List<Image> bombExplosionAnimation = Arrays.asList(Sprite.bomb_exploded.getFxImage(), Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage());
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    
    @Override
    public void update(Scene scene, long now) {
        if(this.img == null) return;
        if (!onScreen) {
            onScreen = true;
            startTime = now;
            existTime = now;
        }

        if (onScreen && exploding == false) {
            if( now - startTime >= 300000000L) {
                img = bombAnimation.get(typeOfBomb);
                typeOfBomb = (typeOfBomb + 1) % 3;
                startTime = now;
            }
            
            if (now - existTime > 4000000000L) {
                exploding = true;
                explodeTime = now;
                typeOfBomb = 0;
            }
        }
        if (exploding == true) {
            explode(now);
        }
    }

    private void explode(long now) {
        if( now - explodeTime >= 100000000L) {
            if(typeOfBomb < 2) {
                typeOfBomb++;
                img = bombExplosionAnimation.get(typeOfBomb);
                explodeTime = now;
            } else if(typeOfBomb == 2) {
                this.img = null;
                numberOfBombsOnScreen--;
            }
        }
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}