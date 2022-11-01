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
    // Phạm vi nổ
    private int scope = Sprite.SCALED_SIZE;
    private boolean inited = false;
    private Flame leftLastHorizontal = null;
    private Flame downLastVertical = null;
    private Flame topLastVertical = null;
    private Flame rightLastHorizontal = null;
    private Flame leftHorizontal = null;
    private Flame rightHorizontal = null;
    private Flame topVertical = null;
    private Flame downVertical = null;
    private Brick deleteBrick1 = null;
    private Brick deleteBrick2 = null;
    private Brick deleteBrick3 = null;
    private Brick deleteBrick4 = null;
    private static final List<Image> horizontalFlame = Arrays.asList(Sprite.explosion_horizontal.getFxImage(), Sprite.explosion_horizontal1.getFxImage(), Sprite.explosion_horizontal2.getFxImage());
    private static final List<Image> leftHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage(), Sprite.explosion_horizontal_left_last1.getFxImage(), Sprite.explosion_horizontal_left_last2.getFxImage());
    private static final List<Image> rightHorizontalFlame = Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage(), Sprite.explosion_horizontal_right_last1.getFxImage(), Sprite.explosion_horizontal_right_last2.getFxImage());
    private static final List<Image> verticalFlame = Arrays.asList(Sprite.explosion_vertical.getFxImage(), Sprite.explosion_vertical1.getFxImage(), Sprite.explosion_vertical2.getFxImage());
    private static final List<Image> topVerticalFlame = Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage(), Sprite.explosion_vertical_top_last1.getFxImage(), Sprite.explosion_vertical_top_last2.getFxImage());
    private static final List<Image> downVerticalFlame = Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage(), Sprite.explosion_vertical_down_last1.getFxImage(), Sprite.explosion_vertical_down_last2.getFxImage());
    private static final List<Image> brokenBrick = Arrays.asList(Sprite.brick_exploded.getFxImage(), Sprite.brick_exploded1.getFxImage(), Sprite.brick_exploded2.getFxImage());

    public Flame() {

    }

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

//    public boolean inDeadZone(int d_x, int d_y) {
//        if (d_y == y) {
//            if ( x <= d_x && d_x <= x + scope) {  // Đi từ phải vào
//                return true;
//            } else if ( d_x <= x && x < d_x + Sprite.SCALED_SIZE ) {  // Đi từ trái vào
//                return true;
//            }
//            return false;
//        } else if (d_x == x) {
//            if ( d_y <= y && y < d_y + Sprite.SCALED_SIZE) {  // Đi từ trên xuống
//                return true;
//            } else if ( y <= d_y && d_y < ) {  // Đi từ dưới lên
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isInited() {
        return inited;
    }

    public boolean flammable() {
        return !(BombermanGame.map[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] == '#');
    }

    public boolean brickCollision() {
    	return (BombermanGame.map[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] == '*'
            || BombermanGame.map[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] == 'x');
    }

    public void initFlame(Bomb bomb) {
        this.inited = true;
        leftLastHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) - 2,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                leftHorizontalFlame.get(0));
        downLastVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) + 2,
                downVerticalFlame.get(0));
        topLastVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 2,
                topVerticalFlame.get(0));
        rightLastHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) + 2,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                rightHorizontalFlame.get(0));

        leftHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) - 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                horizontalFlame.get(0));
        downVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) + 1,
                verticalFlame.get(0));
        topVertical = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 1,
                verticalFlame.get(0));
        rightHorizontal = new Flame((int) (bomb.getX() / Sprite.SCALED_SIZE) + 1,
                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                horizontalFlame.get(0));

        deleteBrick1 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 100,
                brokenBrick.get(0));
        deleteBrick2 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 100,
                brokenBrick.get(0));
        deleteBrick3 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 100,
                brokenBrick.get(0));
        deleteBrick4 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 100,
                brokenBrick.get(0));

        if (rightHorizontal.flammable()) {
            if (rightHorizontal.brickCollision()) {
                deleteBrick1 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE) + 1,
                        (int) (bomb.getY() / Sprite.SCALED_SIZE),
                        brokenBrick.get(0));
                getEntities().add(deleteBrick1);
                Entity grass = new Grass(deleteBrick1.getX() / Sprite.SCALED_SIZE, deleteBrick1.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                Entity portal = new Portal(deleteBrick1.getX() / Sprite.SCALED_SIZE, deleteBrick1.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    int x = getStillObjects().get(i).getX();
                    int y = getStillObjects().get(i).getY();
                    if (x == deleteBrick1.getX() && y == deleteBrick1.getY()) {
                        if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                            getStillObjects().set(i, grass);
                            BombermanGame.map[deleteBrick1.getY() / Sprite.SCALED_SIZE][deleteBrick1.getX() / Sprite.SCALED_SIZE] =' ';
                        } else {
                            if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                        }
                    }
                }
            } else {
                getEntities().add(rightHorizontal);
                if (rightLastHorizontal.flammable()) {
                    if (rightLastHorizontal.brickCollision()) {
                        deleteBrick1 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE) + 2,
                                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                                brokenBrick.get(0));
                        getEntities().add(deleteBrick1);
                        Entity grass = new Grass(deleteBrick1.getX() / Sprite.SCALED_SIZE, deleteBrick1.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        Entity portal = new Portal(deleteBrick1.getX() / Sprite.SCALED_SIZE, deleteBrick1.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                        for (int i = 0; i < getStillObjects().size(); i++) {
                            int x = getStillObjects().get(i).getX();
                            int y = getStillObjects().get(i).getY();
                            if (x == deleteBrick1.getX() && y == deleteBrick1.getY()) {
                                if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                                    getStillObjects().set(i, grass);
                                    BombermanGame.map[deleteBrick1.getY() / Sprite.SCALED_SIZE][deleteBrick1.getX() / Sprite.SCALED_SIZE] =' ';
                                } else {
                                    if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                                }
                            }
                        }
                    } else {
                        getEntities().add(rightLastHorizontal);
                    }
                }
            }
        }

        if (leftHorizontal.flammable()) {
            if (leftHorizontal.brickCollision()) {
                deleteBrick2 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE) - 1,
                        (int) (bomb.getY() / Sprite.SCALED_SIZE),
                        brokenBrick.get(0));
                getEntities().add(deleteBrick2);
                Entity grass = new Grass(deleteBrick2.getX() / Sprite.SCALED_SIZE, deleteBrick2.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                Entity portal = new Portal(deleteBrick2.getX() / Sprite.SCALED_SIZE, deleteBrick2.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    int x = getStillObjects().get(i).getX();
                    int y = getStillObjects().get(i).getY();
                    if (x == deleteBrick2.getX() && y == deleteBrick2.getY()) {
                        if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                            getStillObjects().set(i, grass);
                            BombermanGame.map[deleteBrick2.getY() / Sprite.SCALED_SIZE][deleteBrick2.getX() / Sprite.SCALED_SIZE] =' ';
                        } else {
                            if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                        }
                    }
                }
            } else {
                getEntities().add(leftHorizontal);
                if (leftLastHorizontal.flammable()) {
                    if (leftLastHorizontal.brickCollision()) {
                        deleteBrick2 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE) - 2,
                                (int) (bomb.getY() / Sprite.SCALED_SIZE),
                                brokenBrick.get(0));
                        getEntities().add(deleteBrick2);
                        Entity grass = new Grass(deleteBrick2.getX() / Sprite.SCALED_SIZE, deleteBrick2.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        Entity portal = new Portal(deleteBrick2.getX() / Sprite.SCALED_SIZE, deleteBrick2.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                        for (int i = 0; i < getStillObjects().size(); i++) {
                            int x = getStillObjects().get(i).getX();
                            int y = getStillObjects().get(i).getY();
                            if (x == deleteBrick2.getX() && y == deleteBrick2.getY()) {
                                if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                                    getStillObjects().set(i, grass);
                                    BombermanGame.map[deleteBrick2.getY() / Sprite.SCALED_SIZE][deleteBrick2.getX() / Sprite.SCALED_SIZE] =' ';
                                } else {
                                    if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                                }
                            }
                        }
                    } else {
                        getEntities().add(leftLastHorizontal);
                    }
                }
            }
        }

        if (downVertical.flammable()) {
            if (downVertical.brickCollision()) {
                deleteBrick3 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                        (int) (bomb.getY() / Sprite.SCALED_SIZE) + 1,
                        brokenBrick.get(0));
                getEntities().add(deleteBrick3);
                Entity grass = new Grass(deleteBrick3.getX() / Sprite.SCALED_SIZE, deleteBrick3.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                Entity portal = new Portal(deleteBrick3.getX() / Sprite.SCALED_SIZE, deleteBrick3.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    int x = getStillObjects().get(i).getX();
                    int y = getStillObjects().get(i).getY();
                    if (x == deleteBrick3.getX() && y == deleteBrick3.getY()) {
                        if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                            getStillObjects().set(i, grass);
                            BombermanGame.map[deleteBrick3.getY() / Sprite.SCALED_SIZE][deleteBrick3.getX() / Sprite.SCALED_SIZE] =' ';
                        } else {
                            if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                        }
                    }
                }
            } else {
                getEntities().add(downVertical);
                if (downLastVertical.flammable()) {
                    if (downLastVertical.brickCollision()) {
                        deleteBrick3 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                                (int) (bomb.getY() / Sprite.SCALED_SIZE) + 2,
                                brokenBrick.get(0));
                        getEntities().add(deleteBrick3);
                        Entity grass = new Grass(deleteBrick3.getX() / Sprite.SCALED_SIZE, deleteBrick3.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        Entity portal = new Portal(deleteBrick3.getX() / Sprite.SCALED_SIZE, deleteBrick3.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                        for (int i = 0; i < getStillObjects().size(); i++) {
                            int x = getStillObjects().get(i).getX();
                            int y = getStillObjects().get(i).getY();
                            if (x == deleteBrick3.getX() && y == deleteBrick3.getY()) {
                                if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                                    getStillObjects().set(i, grass);
                                    BombermanGame.map[deleteBrick3.getY() / Sprite.SCALED_SIZE][deleteBrick3.getX() / Sprite.SCALED_SIZE] =' ';
                                } else {
                                    if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                                }
                            }
                        }
                    } else {
                        getEntities().add(downLastVertical);
                    }
                }
            }
        }

        if (topVertical.flammable()) {
            if (topVertical.brickCollision()) {
                deleteBrick4 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                        (int) (bomb.getY() / Sprite.SCALED_SIZE) - 1,
                        brokenBrick.get(0));
                getEntities().add(deleteBrick4);
                Entity grass = new Grass(deleteBrick4.getX() / Sprite.SCALED_SIZE, deleteBrick4.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                Entity portal = new Portal(deleteBrick4.getX() / Sprite.SCALED_SIZE, deleteBrick4.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                for (int i = 0; i < getStillObjects().size(); i++) {
                    int x = getStillObjects().get(i).getX();
                    int y = getStillObjects().get(i).getY();
                    if (x == deleteBrick4.getX() && y == deleteBrick4.getY()) {
                        if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                            getStillObjects().set(i, grass);
                            BombermanGame.map[deleteBrick4.getY() / Sprite.SCALED_SIZE][deleteBrick4.getX() / Sprite.SCALED_SIZE] =' ';
                        } else {
                            if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                        }
                    }
                }
            } else {
                getEntities().add(topVertical);
                if (topLastVertical.flammable()) {
                    if (topLastVertical.brickCollision()) {
                        deleteBrick4 = new Brick((int) (bomb.getX() / Sprite.SCALED_SIZE),
                                (int) (bomb.getY() / Sprite.SCALED_SIZE) - 2,
                                brokenBrick.get(0));
                        getEntities().add(deleteBrick4);
                        Entity grass = new Grass(deleteBrick4.getX() / Sprite.SCALED_SIZE, deleteBrick4.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        Entity portal = new Portal(deleteBrick4.getX() / Sprite.SCALED_SIZE, deleteBrick4.getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
                        for (int i = 0; i < getStillObjects().size(); i++) {
                            int x = getStillObjects().get(i).getX();
                            int y = getStillObjects().get(i).getY();
                            if (x == deleteBrick3.getX() && y == deleteBrick3.getY()) {
                                if (BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == '*') {
                                    getStillObjects().set(i, grass);
                                    BombermanGame.map[deleteBrick3.getY() / Sprite.SCALED_SIZE][deleteBrick3.getX() / Sprite.SCALED_SIZE] =' ';
                                } else {
                                    if (getStillObjects().get(i) instanceof Brick) getStillObjects().set(i, portal);
                                }
                            }
                        }
                    } else {
                        getEntities().add(topLastVertical);
                    }
                }
            }
        }
    }

    public void renderFlame(int type, int bombUp) {
        inited = false;
        leftLastHorizontal.setImg(leftHorizontalFlame.get(type));
        rightLastHorizontal.setImg(rightHorizontalFlame.get(type));
        topLastVertical.setImg(topVerticalFlame.get(type));
        downLastVertical.setImg(downVerticalFlame.get(type));
        leftHorizontal.setImg(horizontalFlame.get(type));
        rightHorizontal.setImg(horizontalFlame.get(type));
        topVertical.setImg(verticalFlame.get(type));
        downVertical.setImg(verticalFlame.get(type));
        if (bombUp == 0) {
            deleteBrick1.setImg(brokenBrick.get(type));
            deleteBrick2.setImg(brokenBrick.get(type));
            deleteBrick3.setImg(brokenBrick.get(type));
            deleteBrick4.setImg(brokenBrick.get(type));
        } else if (bombUp == 1) {
            deleteBrick1.img = null;
            deleteBrick2.img = null;
            deleteBrick3.img = null;
            deleteBrick4.img = null;
        }
    }

    public void deleteFlame() {
        leftLastHorizontal.img = null;
        rightLastHorizontal.img = null;
        topLastVertical.img = null;
        downLastVertical.img = null;
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
