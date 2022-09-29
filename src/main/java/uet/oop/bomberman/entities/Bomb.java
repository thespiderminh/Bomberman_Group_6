package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Bomb extends Entity {
    private static int numberOfBombs = 1;
    private static int numberOfBombsOnScreen = 0;
    private static List<Bomb> allBombs = new ArrayList<>();
    private static final long	BOMB_TIME	= 2000000000L;
    private long startTime	= 0;
    private boolean	firstUpdate	= false;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public static void getBombs(Bomber bomberman) {
        if (numberOfBombsOnScreen < numberOfBombs) {
            Bomb bomb = new Bomb((int)(bomberman.getCenterX() / Sprite.SCALED_SIZE),
                    (int)(bomberman.getCenterY() / Sprite.SCALED_SIZE),
                        Sprite.bomb.getFxImage());
            allBombs.add(bomb);
            getEntities().add(bomb);
        }
    }

    @Override
    public void update(Scene scene, long now) {
        if (!firstUpdate) {
            firstUpdate = true;
            startTime = now;
        }

        if (firstUpdate) {
            if (now - BOMB_TIME > startTime) {
                exploid();
                firstUpdate = false;
            }
        }
    }

    private void exploid() {
        getEntities().remove(getEntities().indexOf(this));
    }
}
