package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Balloon extends Enemy {
    private final int velo = 1;
    private int velocity_x = 0;
    private int velocity_y = 0;

    private int step = Sprite.SCALED_SIZE;

    /**
     * Destination x,y.
     */
    private int des_x = x + step;
    private int des_y = y + step;

    /**
     * Stop,Right,Left,Up,Down.
     */
    String [] states = { "Stop", "Right", "Left", "Up", "Down"};

    boolean [] stop_ratio = {false,false,false,false,false,false,false,true,true,true};
    private String current_state = null;

    private long startTime;

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

    void generate_direction() {
        int i = (int)(Math.random()*10) % 4 + 1;
        current_state = states[i];
    }

    public boolean checkGrass(int m_x, int m_y) {
        return BombermanGame.map[m_x][m_y] == ' ';
    }

    public void move_right_to(int d_x) {
        current_state = "Right";
        velocity_x = velo;
        if (x < d_x) {
            x += velocity_x;
        }
    }

    public void move_left_to(int d_x) {
        current_state = "Left";
        velocity_x = -velo;
        if (x > d_x) {
            x += velocity_x;
        }
    }

    public void move_up_to(int d_y) {
        current_state = "Up";
        velocity_y = -velo;
        if (y > d_y) {
            y += velocity_y;
        }
    }

    public void move_down_to(int d_y) {
        current_state = "Down";
        velocity_y = velo;
        if (y < d_y) {
            y += velocity_y;
        }
    }

    public void reset(long now) {
        if (now - startTime > 450000000L) {
            startTime = now;
        }
    }

    public void change_animation(long now) {
        reset(now);

        if (Objects.equals(current_state, "Right")) {
            if(now - startTime < 150000000L) {
                this.img = Sprite.balloom_right1.getFxImage();
            } else if (now - startTime < 300000000L) {
                this.img = Sprite.balloom_right2.getFxImage();
            } else {
                this.img = Sprite.balloom_right3.getFxImage();
            }
        } else if (Objects.equals(current_state, "Left")) {
            if(now - startTime < 150000000L) {
                this.img = Sprite.balloom_left1.getFxImage();
            } else if (now - startTime < 300000000L) {
                this.img = Sprite.balloom_left2.getFxImage();
            } else {
                this.img = Sprite.balloom_left3.getFxImage();
            }
        }
    }

    @Override
    public void update(Scene scene, long now) {
        if (current_state == "Stop") {
            generate_direction();
        }

        if (checkGrass(y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
            if (des_x > x) {
                move_right_to(des_x);
            }
            else {
                move_left_to(des_x);
            }
        }
        else {
            if (Objects.equals(current_state, "Right")) {
                step = -Sprite.SCALED_SIZE;
                update_des_x();
            }
            else {
                step = Sprite.SCALED_SIZE;
                update_des_x();
            }
        }
        if (x == des_x) {
            if (Objects.equals(current_state, "Right")) {
                step = Sprite.SCALED_SIZE;
                update_des_x();
            }
            else {
                step = -Sprite.SCALED_SIZE;
                update_des_x();
            }
        }

        change_animation(now);
    }
}