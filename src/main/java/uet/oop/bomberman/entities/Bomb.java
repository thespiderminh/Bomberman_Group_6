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
    private boolean	onScreen	= false;
    private int typeOfBomb = 0;
    private List<Image> bombAnimation = Arrays.asList(Sprite.bomb.getFxImage(),Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage());


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    
    @Override
    public void update(Scene scene, long now) {
        if (!onScreen) {
            onScreen = true;
            startTime = now;
            existTime = now;
        }

        if (onScreen) {
            if( now - startTime >= 300000000L) {
                this.img = bombAnimation.get(typeOfBomb);
                typeOfBomb = (typeOfBomb + 1) % 3;
                startTime = now;
            }
            
            if (now - existTime > 4000000000L) {
                explode();
            }
        }
    }

    private void explode() {
        this.img = null;
        numberOfBombsOnScreen--;
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}