package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Bomb extends Entity {
    protected static int numberOfBombs = 3;
    protected static int numberOfBombsOnScreen = 0;
    private static final long BOMB_TIME = 2000000000L;
    private long startTime = 0;
    private long existTime = 0;
    private long explodeTime = 0;
    private boolean onScreen = false;
    private boolean exploding = false;
    private int typeOfBomb = 0;
    private final List<Image> bombAnimation = Arrays.asList(Sprite.bomb.getFxImage(), Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage());
    private final List<Image> bombExplosionAnimation = Arrays.asList(Sprite.bomb_exploded.getFxImage(), Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage());
    private List<Image> horizontalFlame = Arrays.asList(Sprite.explosion_horizontal.getFxImage(), Sprite.explosion_horizontal1.getFxImage(), Sprite.explosion_horizontal2.getFxImage());
    private List<Image> leftHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage(), Sprite.explosion_horizontal_left_last1.getFxImage(), Sprite.explosion_horizontal_left_last2.getFxImage());
    private List<Image> rightHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage(), Sprite.explosion_horizontal_right_last1.getFxImage(), Sprite.explosion_horizontal_right_last2.getFxImage());
    private List<Image> verticalFlame = Arrays.asList(Sprite.explosion_vertical.getFxImage(), Sprite.explosion_vertical1.getFxImage(), Sprite.explosion_vertical2.getFxImage());
    private List<Image> topVerticalFlame = Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage(), Sprite.explosion_vertical_top_last1.getFxImage(), Sprite.explosion_vertical_top_last2.getFxImage());
    private List<Image> downVerticalFlame = Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage(), Sprite.explosion_vertical_down_last1.getFxImage(), Sprite.explosion_vertical_down_last2.getFxImage());

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public int getTypeOfBomb() {
        return typeOfBomb;
    }

    public static int getNumberOfBombs() {
        return numberOfBombs;
    }

    public List<Image> getHorizontalFlame() {
        return horizontalFlame;
    }

    public List<Image> getLeftHorizontalFlame() {
        return leftHorizontalFlame;
    }

    public List<Image> getRightHorizontalFlame() {
        return rightHorizontalFlame;
    }

    public List<Image> getVerticalFlame() {
        return verticalFlame;
    }

    public List<Image> getTopVerticalFlame() {
        return topVerticalFlame;
    }

    public List<Image> getDownVerticalFlame() {
        return downVerticalFlame;
    }

    @Override
    public void update(Scene scene, long now) {
        if (this.img == null) return;
        if (!onScreen) {
            onScreen = true;
            startTime = now;
            existTime = now;
        }

        if (onScreen && exploding == false) {
            if (now - startTime >= 300000000L) {
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
        Flame horizontal = new Flame((int) (getX() / Sprite.SCALED_SIZE), getY() / Sprite.SCALED_SIZE, getHorizontalFlame().get(0));
        Flame vertical = new Flame((int) (getX() / Sprite.SCALED_SIZE), getY() / Sprite.SCALED_SIZE, getVerticalFlame().get(0));
        Flame rightHorizontal = new Flame((int) (getX() / Sprite.SCALED_SIZE) + 1, getY() / Sprite.SCALED_SIZE, getRightHorizontalFlame().get(0));
        Flame leftHorizontal = new Flame((int) (getX() / Sprite.SCALED_SIZE) - 1, getY() / Sprite.SCALED_SIZE, getLeftHorizontalFlame().get(0));
        Flame downVertical = new Flame(getX() / Sprite.SCALED_SIZE, (int) (getY() / Sprite.SCALED_SIZE) + 1, getDownVerticalFlame().get(0));
        Flame topVertical = new Flame(getX() / Sprite.SCALED_SIZE, (int) (getY() / Sprite.SCALED_SIZE) - 1, getTopVerticalFlame().get(0));
        getEntities().add(horizontal);
        getEntities().add(vertical);
        if (rightHorizontal.flammable()) {
            getEntities().add(rightHorizontal);
        }
        if (leftHorizontal.flammable()) {
            getEntities().add(leftHorizontal);
        }
        if (downVertical.flammable()) {
            getEntities().add(downVertical);
        }
        if (topVertical.flammable()) {
            getEntities().add(topVertical);
        }
        if (now - explodeTime >= 100000000L) {
            if (typeOfBomb < 2) {
                typeOfBomb++;
                img = bombExplosionAnimation.get(typeOfBomb);
                horizontal.setImg(getHorizontalFlame().get(typeOfBomb));
                vertical.setImg(getVerticalFlame().get(typeOfBomb));
                rightHorizontal.setImg(getRightHorizontalFlame().get(typeOfBomb));
                leftHorizontal.setImg(getLeftHorizontalFlame().get(typeOfBomb));
                downVertical.setImg(getDownVerticalFlame().get(typeOfBomb));
                topVertical.setImg(getTopVerticalFlame().get(typeOfBomb));
                explodeTime = now;
            } else if (typeOfBomb == 2) {
                this.img = null;
                horizontal.setImg(null);
                vertical.setImg(null);
                rightHorizontal.setImg(null);
                leftHorizontal.setImg(null);
                downVertical.setImg(null);
                topVertical.setImg(null);
//                getEntities().remove(horizontal);
//                getEntities().remove(vertical);
//                getEntities().remove(rightHorizontal);
//                getEntities().remove(leftHorizontal);
//                getEntities().remove(downVertical);
//                getEntities().remove(topVertical);

                numberOfBombsOnScreen--;
            }
        }
    }

    public boolean isOnScreen() {
        return onScreen;
    }

}


