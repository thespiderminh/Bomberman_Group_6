package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Flame extends Entity {
    private boolean inited = false;
    private Flame leftHorizontal = null;
    private Flame downVertical = null;
    private Flame topVertical = null;
    private Flame rightHorizontal = null;

    private static List<Image> horizontalFlame = Arrays.asList(Sprite.explosion_horizontal.getFxImage(), Sprite.explosion_horizontal1.getFxImage(), Sprite.explosion_horizontal2.getFxImage());
    private static List<Image> leftHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage(), Sprite.explosion_horizontal_left_last1.getFxImage(), Sprite.explosion_horizontal_left_last2.getFxImage());
    private static List<Image> rightHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage(), Sprite.explosion_horizontal_right_last1.getFxImage(), Sprite.explosion_horizontal_right_last2.getFxImage());
    private static List<Image> verticalFlame = Arrays.asList(Sprite.explosion_vertical.getFxImage(), Sprite.explosion_vertical1.getFxImage(), Sprite.explosion_vertical2.getFxImage());
    private static List<Image> topVerticalFlame = Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage(), Sprite.explosion_vertical_top_last1.getFxImage(), Sprite.explosion_vertical_top_last2.getFxImage());
    private static List<Image> downVerticalFlame = Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage(), Sprite.explosion_vertical_down_last1.getFxImage(), Sprite.explosion_vertical_down_last2.getFxImage());

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public Flame() {

    }

    public boolean isInited() {
        return inited;
    }

    public boolean flammable() {
        return !(BombermanGame.map[this.x / Sprite.SCALED_SIZE][this.y / Sprite.SCALED_SIZE] == '#');
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
//        if (rightHorizontal.flammable()) {
            getEntities().add(rightHorizontal);
//        }
//        if (leftHorizontal.flammable()) {
            getEntities().add(leftHorizontal);
//        }
//        if (downVertical.flammable()) {
            getEntities().add(downVertical);
//        }
//        if (topVertical.flammable()) {
            getEntities().add(topVertical);
//        }
    }

    public void renderFlame(int type) {
        inited = false;
        leftHorizontal.setImg(leftHorizontalFlame.get(type));
        rightHorizontal.setImg(rightHorizontalFlame.get(type));
        topVertical.setImg(topVerticalFlame.get(type));
        downVertical.setImg(downVerticalFlame.get(type));
    }

    public void deleteFlame() {
        leftHorizontal.img = null;
        rightHorizontal.img = null;
        topVertical.img = null;
        downVertical.img = null;
    }

    @Override
    public void update(Scene scene, long now) {

    }
}