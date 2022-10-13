package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.getEntities;
import static uet.oop.bomberman.BombermanGame.getStillObjects;

public class Flame extends Entity {
    private boolean inited = false;
    private Flame leftHorizontal = null;
    private Flame downVertical = null;
    private Flame topVertical = null;
    private Flame rightHorizontal = null;
    private Flame deleteBrick1 = null;
    private Flame deleteBrick2 = null;
    private Flame deleteBrick3 = null;
    private Flame deleteBrick4 = null;
    private static List<Image> horizontalFlame = Arrays.asList(Sprite.explosion_horizontal.getFxImage(), Sprite.explosion_horizontal1.getFxImage(), Sprite.explosion_horizontal2.getFxImage());
    private static List<Image> leftHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage(), Sprite.explosion_horizontal_left_last1.getFxImage(), Sprite.explosion_horizontal_left_last2.getFxImage());
    private static List<Image> rightHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage(), Sprite.explosion_horizontal_right_last1.getFxImage(), Sprite.explosion_horizontal_right_last2.getFxImage());
    private static List<Image> verticalFlame = Arrays.asList(Sprite.explosion_vertical.getFxImage(), Sprite.explosion_vertical1.getFxImage(), Sprite.explosion_vertical2.getFxImage());
    private static List<Image> topVerticalFlame = Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage(), Sprite.explosion_vertical_top_last1.getFxImage(), Sprite.explosion_vertical_top_last2.getFxImage());
    private static List<Image> downVerticalFlame = Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage(), Sprite.explosion_vertical_down_last1.getFxImage(), Sprite.explosion_vertical_down_last2.getFxImage());
    private static List<Image> brokenBrick = Arrays.asList(Sprite.brick_exploded.getFxImage(), Sprite.brick_exploded1.getFxImage(), Sprite.brick_exploded2.getFxImage());

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Flame() {

    }

    public boolean isInited() {
        return inited;
    }

    public boolean flammable() {
        return !(BombermanGame.map[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] == '#');
    }

    public boolean brickCollision() {
        return (BombermanGame.map[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] == '*');
    }

    public void initFlame(Bomb bomb) {
        this.inited = true;
        leftHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) - 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                leftHorizontalFlame.get(0));
        downVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) + 1,
                downVerticalFlame.get(0));
        topVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 1,
                topVerticalFlame.get(0));
        rightHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) + 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                rightHorizontalFlame.get(0));
        deleteBrick1 = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) + 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                brokenBrick.get(0));
        deleteBrick2 = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) - 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                brokenBrick.get(0));
        deleteBrick3 = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) + 1,
                brokenBrick.get(0));
        deleteBrick4 = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 1,
                brokenBrick.get(0));
        if (rightHorizontal.flammable()) {
            if (rightHorizontal.brickCollision()) {
                getEntities().add(deleteBrick1);
                Entity grass = new Grass(deleteBrick1.getX() / Sprite.SCALED_SIZE, deleteBrick1.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    if (getStillObjects().get(i).getX() == deleteBrick1.getX() && getStillObjects().get(i).getY() == deleteBrick1.getY()) {
//                        getStillObjects().remove(i);
//                        getStillObjects().add(grass);
                        getStillObjects().set(i, grass);
                        BombermanGame.map[deleteBrick1.getY() / Sprite.SCALED_SIZE][deleteBrick1.getX() / Sprite.SCALED_SIZE] =' ';
                    }
                }
            } else {
                getEntities().add(rightHorizontal);
            }
        }
        if (leftHorizontal.flammable()) {
            if (leftHorizontal.brickCollision()) {
                getEntities().add(deleteBrick2);
                Entity grass = new Grass(deleteBrick2.getX() / Sprite.SCALED_SIZE, deleteBrick2.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    if (getStillObjects().get(i).getX() == deleteBrick2.getX() && getStillObjects().get(i).getY() == deleteBrick2.getY()) {
//                        getStillObjects().remove(i);
//                        getStillObjects().add(grass);
                        getStillObjects().set(i, grass);
                        BombermanGame.map[deleteBrick2.getY() / Sprite.SCALED_SIZE][deleteBrick2.getX() / Sprite.SCALED_SIZE] =' ';
                    }
                }
            } else {
                getEntities().add(leftHorizontal);
            }
        }
        if (downVertical.flammable()) {
            if (downVertical.brickCollision()) {
                getEntities().add(deleteBrick3);
                Entity grass = new Grass(deleteBrick3.getX() / Sprite.SCALED_SIZE, deleteBrick3.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    if (getStillObjects().get(i).getX() == deleteBrick3.getX() && getStillObjects().get(i).getY() == deleteBrick3.getY()) {
//                        getStillObjects().remove(i);
//                        getStillObjects().add(grass);
                        getStillObjects().set(i, grass);
                        BombermanGame.map[deleteBrick3.getY() / Sprite.SCALED_SIZE][deleteBrick3.getX() / Sprite.SCALED_SIZE] =' ';
                    }
                }
            } else {
                getEntities().add(downVertical);
            }
        }
        if (topVertical.flammable()) {
            if (topVertical.brickCollision()) {
                getEntities().add(deleteBrick4);
                Entity grass = new Grass(deleteBrick4.getX() / Sprite.SCALED_SIZE, deleteBrick4.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    if (getStillObjects().get(i).getX() == deleteBrick4.getX() && getStillObjects().get(i).getY() == deleteBrick4.getY()) {
//                        getStillObjects().remove(i);
//                        getStillObjects().add(grass);
                        getStillObjects().set(i, grass);
                        BombermanGame.map[deleteBrick4.getY() / Sprite.SCALED_SIZE][deleteBrick4.getX() / Sprite.SCALED_SIZE] =' ';
                    }
                }
            } else {
                getEntities().add(topVertical);
            }
        }
    }

    public void renderFlame(int type) {
        inited = false;
        leftHorizontal.setImg(leftHorizontalFlame.get(type));
        rightHorizontal.setImg(rightHorizontalFlame.get(type));
        topVertical.setImg(topVerticalFlame.get(type));
        downVertical.setImg(downVerticalFlame.get(type));
        deleteBrick1.setImg(brokenBrick.get(type));
        deleteBrick2.setImg(brokenBrick.get(type));
        deleteBrick3.setImg(brokenBrick.get(type));
        deleteBrick4.setImg(brokenBrick.get(type));
    }

    public void deleteFlame() {
        leftHorizontal.img = null;
        rightHorizontal.img = null;
        topVertical.img = null;
        downVertical.img = null;
        deleteBrick1.img = null;
        deleteBrick2.img = null;
        deleteBrick3.img = null;
        deleteBrick4.img = null;
    }

    @Override
    public void update(Scene scene, long now) {

    }
}
