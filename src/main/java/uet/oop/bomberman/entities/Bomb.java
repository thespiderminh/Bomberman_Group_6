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
    private int typeOfFlame = 0;
    private boolean createdFlame = false;
    private final List<Image> bombAnimation = Arrays.asList(Sprite.bomb.getFxImage(), Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage());
    private final List<Image> bombExplosionAnimation = Arrays.asList(Sprite.bomb_exploded.getFxImage(), Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage());
    private List<Image> horizontalFlame = Arrays.asList(Sprite.explosion_horizontal.getFxImage(), Sprite.explosion_horizontal1.getFxImage(), Sprite.explosion_horizontal2.getFxImage());
    private List<Image> leftHorizontalFlame = Arrays.asList(null, Sprite.explosion_horizontal_left_last.getFxImage(), Sprite.explosion_horizontal_left_last1.getFxImage(), Sprite.explosion_horizontal_left_last2.getFxImage());
    private List<Image> rightHorizontalFlame = Arrays.asList(null, Sprite.explosion_horizontal_right_last.getFxImage(), Sprite.explosion_horizontal_right_last1.getFxImage(), Sprite.explosion_horizontal_right_last2.getFxImage());
    private List<Image> verticalFlame = Arrays.asList(Sprite.explosion_vertical.getFxImage(), Sprite.explosion_vertical1.getFxImage(), Sprite.explosion_vertical2.getFxImage());
    private List<Image> topVerticalFlame = Arrays.asList(null, Sprite.explosion_vertical_top_last.getFxImage(), Sprite.explosion_vertical_top_last1.getFxImage(), Sprite.explosion_vertical_top_last2.getFxImage());
    private List<Image> downVerticalFlame = Arrays.asList(null, Sprite.explosion_vertical_down_last.getFxImage(), Sprite.explosion_vertical_down_last1.getFxImage(), Sprite.explosion_vertical_down_last2.getFxImage());

    public List<Image> getLeftHorizontalFlame() {
        return leftHorizontalFlame;
    }

    public List<Image> getRightHorizontalFlame() {
        return rightHorizontalFlame;
    }

    public List<Image> getTopVerticalFlame() {
        return topVerticalFlame;
    }

    public List<Image> getDownVerticalFlame() {
        return downVerticalFlame;
    }

    public int getTypeOfFlame() {
        return typeOfFlame;
    }

    public void setTypeOfFlame(int typeOfFlame) {
        this.typeOfFlame = typeOfFlame;
    }

    public static int getNumberOfBombsOnScreen() {
        return numberOfBombsOnScreen;
    }


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

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
            Flame leftHorizontal = null;
            Flame downVertical = null;
            Flame topVertical = null;
            Flame rightHorizontal = null;
            if (!createdFlame) {
                createdFlame = true;
                rightHorizontal = new Flame((int) (getX() / Sprite.SCALED_SIZE) + 1, getY() / Sprite.SCALED_SIZE, getRightHorizontalFlame().get(1));
                leftHorizontal = new Flame((int) (getX() / Sprite.SCALED_SIZE) - 1, getY() / Sprite.SCALED_SIZE, getLeftHorizontalFlame().get(1));
                downVertical = new Flame(getX() / Sprite.SCALED_SIZE, (int) (getY() / Sprite.SCALED_SIZE) + 1, getDownVerticalFlame().get(1));
                topVertical = new Flame(getX() / Sprite.SCALED_SIZE, (int) (getY() / Sprite.SCALED_SIZE) - 1, getTopVerticalFlame().get(1));
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
            }
            explode(now, leftHorizontal, rightHorizontal, downVertical, topVertical);
        }
    }

    private void explode(long now,Flame left,Flame right,Flame top, Flame down) {
        if (now - explodeTime >= 100000000L) {
            if (typeOfBomb < 2) {
                typeOfBomb++;
                img = bombExplosionAnimation.get(typeOfBomb);
                left.setImg(getLeftHorizontalFlame().get(typeOfBomb));
                right.setImg(getRightHorizontalFlame().get(typeOfBomb));
                top.setImg(getTopVerticalFlame().get(typeOfBomb));
                down.setImg(getDownVerticalFlame().get(typeOfBomb));
                explodeTime = now;
            } else if (typeOfBomb == 2) {
                this.img = null;
                left.setImg(null);
                right.setImg(null);
                top.setImg(null);
                down.setImg(null);
                numberOfBombsOnScreen--;
            }
        }
    }

    public boolean isOnScreen() {
        return onScreen;
    }

}


