package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Objects;

public class Balloon extends Enemy {
    private final int velo = 1;
    private int velocity_x = 0;
    private int velocity_y = 0;

    private int step = Sprite.SCALED_SIZE;
    private int des_x = x + step;
    private int des_y = y + step;

    private String state = null;

    public void update_des_x() {
        des_x = x + step;
    }

    public void update_des_y() {
        des_y = y + step;
    }

    public int getVelocity_x() {
        return velocity_x;
    }

    public void setVelocity_x(int velocity_x) {
        this.velocity_x = velocity_x;
    }

    public int getVelocity_y() {
        return velocity_y;
    }

    public void setVelocity_y(int velocity_y) {
        this.velocity_y = velocity_y;
    }

    public Balloon(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean checkGrass(int m_x, int m_y) {
        return BombermanGame.map[m_x][m_y] == ' ';
    }

    public void move_right_to(int d_x) {
        velocity_x = velo;
        if (x < d_x) {
            x += velocity_x;
        }
    }

    public void move_left_to(int d_x) {
        velocity_x = -velo;
        if (x > d_x) {
            x += velocity_x;
        }
    }

    public void move_right() {
        velocity_x = velo;

        if (x < des_x) {
            x += velocity_x;
        }
    }

    @Override
    public void update() {

        if (checkGrass(y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
            if (des_x > x) {
                state = "Right";
                move_right_to(des_x);
            }
            else {
                state = "Left";
                move_left_to(des_x);
            }
        }
        else {
            if (Objects.equals(state, "Right")) {
                step = -Sprite.SCALED_SIZE;
                update_des_x();
            }
            else {
                step = Sprite.SCALED_SIZE;
                update_des_x();
            }
        }
        if (x == des_x) {
            if (Objects.equals(state, "Right")) {
                step = Sprite.SCALED_SIZE;
                update_des_x();
            }
            else {
                step = -Sprite.SCALED_SIZE;
                update_des_x();
            }
        }
    }
}
