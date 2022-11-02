package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.entities.Bomb.numberOfBombs;
import static uet.oop.bomberman.entities.Bomb.numberOfBombsOnScreen;

public class Bomber extends Entity {
    public boolean isAlive() {
        return alive;
    }

    private boolean alive = true;
    private int velo = 2;
    private int velocityX = 0;
    private int velocityY = 0;
    private long timePress = 0;
    private long deadTime = 0;
    private long speedUpTime = 0;
    private boolean isSpeedUp = false;
    private int type = 0;
    private int deadType = 0;
    private final List<Image> playerDown = Arrays.asList(Sprite.player_down_0.getFxImage(), Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage());
    private final List<Image> playerUp = Arrays.asList(Sprite.player_up_0.getFxImage(), Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage());
    private final List<Image> playerLeft = Arrays.asList(Sprite.player_left_0.getFxImage(), Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage());
    private final List<Image> playerRight = Arrays.asList(Sprite.player_right_0.getFxImage(), Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage());
    private final List<Image> deadPlayer = Arrays.asList(Sprite.player_dead1.getFxImage(), Sprite.player_dead2.getFxImage(), Sprite.player_dead3.getFxImage());

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(Scene scene, long now) {
        changeTheAnimation(now);
        if (alive) {
            move(now);
            control(scene);
            if (isSpeedUp) {
                if (now - speedUpTime <= 5000000000L) {
                    velo = 3;
                } else {
                    velo = 2;
                    isSpeedUp = false;
                    speedUpTime = 0;
                }
            }
        }
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

    private void move(long now) {
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

        for (int i = 0; i < getStillObjects().size(); i++) {
            if (getStillObjects().get(i) instanceof Wall || getStillObjects().get(i) instanceof Brick) {
                if (velocityY != 0) {
                    if (this.checkCollision(getStillObjects().get(i))) {
                        y -= velocityY;
                        if (x + Sprite.SCALED_SIZE / 2 < getStillObjects().get(i).getX()) {
                            if (getStillObjects().get(i - 1) instanceof Grass) {
                                if (velocityY > 0 && getStillObjects().get(i - 1 + WIDTH) instanceof Grass) {
                                    x -= velo;
                                } else if (velocityY < 0 && getStillObjects().get(i - 1 - WIDTH) instanceof Grass) {
                                    x -= velo;
                                }
                            }
                        } else if (x > getStillObjects().get(i).getX() + Sprite.SCALED_SIZE / 2) {
                            if (getStillObjects().get(i + 1) instanceof Grass) {
                                if (velocityY > 0 && getStillObjects().get(i + 1 + WIDTH) instanceof Grass) {
                                    x += velo;
                                } else if (velocityY < 0 && getStillObjects().get(i + 1 - WIDTH) instanceof Grass) {
                                    x += velo;
                                }
                            }
                        }
                    }
                }
                if (velocityX != 0) {
                    if (this.checkCollision(getStillObjects().get(i))) {
                        x -= velocityX;
                        if (y + Sprite.SCALED_SIZE / 2 < getStillObjects().get(i).getY()) {
                            if (getStillObjects().get(i - WIDTH) instanceof Grass) {
                                y -= velo;
                            }
                        } else if (y > getStillObjects().get(i).getY() + Sprite.SCALED_SIZE / 2) {
                            if (getStillObjects().get(i + WIDTH) instanceof Grass) {
                                y += velo;
                            }
                        }
                        break;
                    }
                }
            } else if (getStillObjects().get(i) instanceof Item) {
                if (this.checkCollision(getStillObjects().get(i)) && getStillObjects().get(i).img != null) {
                    this.apply((Item) getStillObjects().get(i), now);
                    Audio.setPowerUp();
                    Audio.getPowerUp().play();
                }
            }
        }

        for (int i = 0; i < getEntities().size(); i++) {
            if (getEntities().get(i) instanceof Bomb) {
                if (!this.checkCollision(getEntities().get(i))) {
                    ((Bomb) getEntities().get(i)).setInBomber(false);
                }

                if (!((Bomb) getEntities().get(i)).isInBomber()) {
                    if (this.checkCollision(getEntities().get(i))) {
                        if (velocityY != 0) {
                            y -= velocityY;
                        }
                        if (velocityX != 0) {
                            x -= velocityX;
                        }

                    }
                }
            } else if (getEntities().get(i) instanceof Flame ||
                    getEntities().get(i) instanceof Enemy) {
                if (this.checkCollision(getEntities().get(i))) {
                    alive = false;
                    Audio.setDeadSound();
                    Audio.getDeadSound().play();
                    Audio.getBackgroundMusic().stop();
                    deadTime = now;
                }
            }
        }
    }

    public void changeTheAnimation(long now) {
        if (alive) {
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
                    if(type == 1){
                        Audio.setWalk1Sound();
                        Audio.getWalk1Sound().play();
                    } else if(type ==2){
                        Audio.setWalk2Sound();
                        Audio.getWalk2Sound().play();
                    } else {
                        Audio.setWalk3Sound();
                        Audio.getWalk3Sound().play();
                    }
                }
            } else {
                timePress = 0;
                type = 0;
            }
        } else {
            this.img = deadPlayer.get(deadType);

            if (now - deadTime >= 500000000L) {
                if (deadType < 2) {
                    deadTime = now;
                    deadType++;
                } else {
                    this.img = null;
                }
            }
        }
    }

    public void control(Scene scene) {
        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP -> moveUp();
                case DOWN -> moveDown();
                case RIGHT -> moveRight();
                case LEFT -> moveLeft();
                case SPACE -> getBombs();

            }
        });
        scene.setOnKeyReleased(key -> {
            switch (key.getCode()) {
                case UP, DOWN -> stopY();
                case RIGHT, LEFT -> stopX();
            }
        });
    }

    private void getBombs() {
        if (numberOfBombsOnScreen < numberOfBombs) {
            int length = Sprite.SCALED_SIZE;
            int x = getCenterX() / length;
            int y = getCenterY() / length;
            for (int i = 0; i < getEntities().size(); i++) {
                if (getEntities().get(i) instanceof Enemy) {
                    int balloonX = getEntities().get(i).getX();
                    int balloonY = getEntities().get(i).getY();
                    if (x < balloonX && x + length > balloonX && y < balloonY && y + length > balloonY) {
                        if (x > getX() / length) x--;
                        else if (x < (getX() / length + 1)) x++;
                        else if (y > getY() / length) x--;
                        else if (y < (getY() / length + 1)) y++;
                    }
                }
            }
            Bomb bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
            getEntities().add(bomb);
            numberOfBombsOnScreen++;
            Audio.setPlaceBomb();
            Audio.getPlaceBomb().play();
        }
    }

    public boolean inPortal() {
        return BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == 'x';
    }
    public boolean getCup() {
        return BombermanGame.map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == 'c';
    }
    private void apply(Item item, long now) {
        item.img = null;
        if (item instanceof SpeedItem) {
            this.isSpeedUp = true;
            speedUpTime = now;
        } else if (item instanceof BombItem) {
            numberOfBombs++;
        }
    }
}
