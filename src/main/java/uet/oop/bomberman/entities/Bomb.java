package uet.oop.bomberman.entities;

import javafx.animation.AnimationTimer;
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
//    private boolean isOnTheScreen;
    private AnimationTimer timer = new AnimationTimer() {
        private long prevTime = 0;
        @Override
        public void handle(long now) {
            long time = now - prevTime;
            System.out.println(time);
            if (time >= 1 * Math.pow(10, 9)) {
                prevTime = now;
                System.out.println(3);
                exploid();
            }
        }
    };
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

    }

    private void exploid() {
        getEntities().remove(this);
    }
}
