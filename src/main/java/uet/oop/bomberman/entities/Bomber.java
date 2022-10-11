package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Bomb {

    private final int velo = 2;
    private int velocityX = 0;
    private int velocityY = 0;
    private long timePress = 0;
    private int type = 0;
    private List<Image> playerDown = Arrays.asList(Sprite.player_down_0.getFxImage(), Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage());
    private List<Image> playerUp = Arrays.asList(Sprite.player_up_0.getFxImage(), Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage());
    private List<Image> playerLeft = Arrays.asList(Sprite.player_left_0.getFxImage(), Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage());
    private List<Image> playerRight = Arrays.asList(Sprite.player_right_0.getFxImage(), Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage());

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public int getVelo() {
        return velo;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    @Override
    public void update(Scene scene, long now) {
        move(scene);
        changeTheAnimation(now);
        control(scene, now);
    }

    public void moveUp() {
        velocityY = -velo;
    }

    public void moveDown() {
        velocityY = velo;
    }

    public void moveRight() {
        velocityX = velo;
    }

    public void moveLeft() {
        velocityX = -velo;
    }

    public void stopX() {
        velocityX = 0;
    }

    public void stopY() {
        velocityY = 0;
    }

    private void move(Scene scene) {
        x += velocityX;
        y += velocityY;

        if (x <= Sprite.SCALED_SIZE) {
            x = Sprite.SCALED_SIZE;
        }

        if (x >= (WIDTH - 1) * Sprite.SCALED_SIZE) {
            x = (WIDTH - 1) * Sprite.SCALED_SIZE;
        }

        if (y <= Sprite.SCALED_SIZE) {
            y = Sprite.SCALED_SIZE;
        }

        if (y >= (HEIGHT - 1) * Sprite.SCALED_SIZE) {
            y = (HEIGHT - 1) * Sprite.SCALED_SIZE;
        }

        for (Entity w : getStillObjects()) {
            if (w instanceof Wall || w instanceof Brick) {
                int index = getStillObjects().indexOf(w);
                if (velocityY != 0) {
                    if (x + Sprite.SCALED_SIZE > w.getX() && x < w.getX() + Sprite.SCALED_SIZE) {
                        if (y + Sprite.SCALED_SIZE > w.getY() && y < w.getY() + Sprite.SCALED_SIZE) {
                            y -= velocityY;
                            if (x + Sprite.SCALED_SIZE / 3 < w.getX()) {
                                if (getStillObjects().get(index - 1) instanceof Grass) {
                                    x -= velo;
                                }
                            } else if (x > w.getX() + Sprite.SCALED_SIZE / 3) {
                                if (getStillObjects().get(index + 1) instanceof Grass) {
                                    x += velo;
                                }
                            }
                            break;
                        }
                    }
                }

                if (velocityX != 0) {
                    if (y + Sprite.SCALED_SIZE > w.getY() && y < w.getY() + Sprite.SCALED_SIZE) {
                        if (x < w.getX() + Sprite.SCALED_SIZE && x + Sprite.SCALED_SIZE > w.getX()) {
                            x -= velocityX;
                            if (y + Sprite.SCALED_SIZE / 3 < w.getY()) {
                                if (getStillObjects().get(index - WIDTH) instanceof Grass) {
                                    y -= velo;
                                }
                            } else if (y > w.getY() + Sprite.SCALED_SIZE / 3) {
                                if (getStillObjects().get(index + WIDTH) instanceof Grass) {
                                    y += velo;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public void changeTheAnimation(long now) {
        if (velocityY > 0) {
            this.img = playerDown.get(type);
        } else if (velocityY < 0) {
            this.img = playerUp.get(type);
        }
        if (velocityX > 0) {
            this.img = playerRight.get(type);
        } else if (velocityX < 0) {
            this.img = playerLeft.get(type);
        }

        if (velocityX != 0 || velocityY != 0) {
            if (now - timePress > 100000000L) {
                timePress = now;
                type = (type + 1) % 3;
            }
        } else {
            timePress = 0;
            type = 0;
        }
    }

    public void control(Scene scene, long now) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case UP -> moveUp();
                    case DOWN -> moveDown();
                    case RIGHT -> moveRight();
                    case LEFT -> moveLeft();
                    case SPACE -> getBombs(now);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case UP, DOWN -> stopY();
                    case RIGHT, LEFT -> stopX();
                }
            }
        });
    }

    private void getBombs(long now) {
        if (numberOfBombsOnScreen < numberOfBombs) {
            Bomb bomb = new Bomb((int) (getCenterX() / Sprite.SCALED_SIZE),
                    (int) (getCenterY() / Sprite.SCALED_SIZE),
                    Sprite.bomb.getFxImage());
            getEntities().add(bomb);
            numberOfBombsOnScreen++;
        }
    }
}