package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class Bomb extends Entity {
    protected static int numberOfBombs = 1;
    protected static int numberOfBombsOnScreen = 0;
    private static final long BOMB_TIME = 2000000000L;
    private long startTime = 0;
    private long existTime = 0;
    private long explodeTime = 0;
    private boolean onScreen = false;
    private boolean exploding = false;
    private int typeOfBomb = 0;
    private int bombUp = 0;

    public boolean isInBomber() {
        return inBomber;
    }

    public void setInBomber(boolean inBomber) {
        this.inBomber = inBomber;
    }

    private boolean inBomber = true;
    private final List<Image> bombAnimation = Arrays.asList(Sprite.bomb.getFxImage(), Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage());
    private final List<Image> bombExplosionAnimation = Arrays.asList(Sprite.bomb_exploded.getFxImage(), Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage());
    Audio soundBomb = new Audio();

    public static int getNumberOfBombsOnScreen() {
        return numberOfBombsOnScreen;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    Flame flame = new Flame();

    public int getTypeOfBomb() {
        return typeOfBomb;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public long getExplodeTime() {
        return explodeTime;
    }

    public static void setNumberOfBombs(int numberOfBombs) {
        Bomb.numberOfBombs = numberOfBombs;
    }

    public static void setNumberOfBombsOnScreen(int numberOfBombsOnScreen) {
        Bomb.numberOfBombsOnScreen = numberOfBombsOnScreen;
    }

    public void setExplodeTime(long explodeTime) {
        this.explodeTime = explodeTime;
    }

    public void setTypeOfBomb(int typeOfBomb) {
        this.typeOfBomb = typeOfBomb;
    }

    public static int getNumberOfBombs() {
        return numberOfBombs;
    }

    public boolean setTypeOfFlameEqual1 = false;

    @Override
    public void update(Scene scene, long now) {
        if (!onScreen) {
            onScreen = true;
            startTime = now;
            existTime = now;
        }

        if (!exploding) {
            if (now - startTime >= 300000000L) {
                img = bombAnimation.get(typeOfBomb);
                typeOfBomb = (typeOfBomb + 1) % 3;
                startTime = now;
            }

            if (now - existTime > 4000000000L) {
                exploding = true;
                explodeTime = now;
                typeOfBomb = 0;
                flame = new Flame();
                if (!flame.isInited()) {
                    flame.initFlame(this);
                }
                img = bombExplosionAnimation.get(typeOfBomb);
                Audio.setBombExploring();
                Audio.getBombExploring().play();
            }
        }
        if (exploding) {
            explode(now);
        }
    }

    private void explode(long now) {
        if (now - explodeTime >= 70000000L) {
            if (typeOfBomb < 2 && bombUp == 0) {
                typeOfBomb++;
                img = bombExplosionAnimation.get(typeOfBomb);
                flame.renderFlame(typeOfBomb, bombUp);
                explodeTime = now;
            } else if (typeOfBomb == 2 && bombUp == 0) {
                bombUp = 1;
            }
            if (typeOfBomb > 0 && bombUp == 1) {
                typeOfBomb--;
                img = bombExplosionAnimation.get(typeOfBomb);
                flame.renderFlame(typeOfBomb, bombUp);
                explodeTime = now;
            } else if (typeOfBomb == 0 && bombUp == 1) {
                flame.deleteFlame();
                this.img = null;
                numberOfBombsOnScreen--;
                exploding = false;
                onScreen = false;
            }
        }
    }

    public boolean isOnScreen() {
        return onScreen;
    }

}


