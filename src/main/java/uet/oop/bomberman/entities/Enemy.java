package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Enemy extends Entity {
    protected int velo = 1;
    protected int velocity_x = 0;
    protected int velocity_y = 0;

    protected int step = Sprite.SCALED_SIZE; // 32

    /**
     * Destination x,y.
     */
    protected int des_x = x;
    protected int des_y = y;

    /**
     * Footprint.
     */
    protected int old_x = x;
    protected int old_y = y;

    /**
     * Stop,Right,Left,Up,Down.
     */
    protected String [] states = { "Stop", "Right", "Left", "Up", "Down", "Dead"};

    protected String current_state = "Stop";

    /**
     * Thời điểm bắt đầu 1 chu kì changeAnimation.
     */
    protected long startTime;

    /**
     * Thời điểm bắt đầu delay sau mỗi lần stop.
     */
    protected long start_delay = 0;

    /**
     * Thời điểm bắt đầu move sau quãng stop.  (start_move - start_delay = stop interval)
     */
    protected long start_move = 0;

    protected boolean delay_latch = true;

    /**
     * Thời điểm bắt đầu chết.
     */
    protected long start_dead;

    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }

    public void update_des_x() {
        if (des_x == x) {
            des_x = x + step;
            old_x = x;
        }
    }

    public void update_des_y() {
        if (des_y == y) {
            des_y = y + step;
            old_y = y;
            //System.out.println("Old_y: " + old_y);
        }
    }

    void generate_direction() {

    }

    public boolean has_bomb(int b_x, int b_y) {
        for (int i = 0; i < getEntities().size(); ++i) {
            Entity e = getEntities().get(i);
            if (e.getX() / Sprite.SCALED_SIZE == b_x
                    && e.getY() / Sprite.SCALED_SIZE == b_y ) {
                if (e instanceof Bomb) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tọa độ dạng thường
     */
    public boolean has_flame(int f_x, int f_y) {
        for (int i = 0; i < getEntities().size(); ++i) {
            Entity e = getEntities().get(i);
            if (e.getX() / Sprite.SCALED_SIZE == f_x
                    && e.getY() / Sprite.SCALED_SIZE == f_y ) {
                if (e instanceof Flame) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean movable(int m_y, int m_x) {
        //follow the grass
        return (BombermanGame.map[m_y][m_x] == ' ') && (!has_bomb(m_x,m_y));
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

    public void sudden_stop(long interval) {

    }

    public void reset(long now) {
        if (now - startTime > 450000000L) {
            startTime = now;
        }
    }

    public void change_animation(long now) {
    }

    /**
     * Kiểm tra xem có bị dính lửa ko.
     */
    public boolean get_burned() {
        return has_flame(des_x / Sprite.SCALED_SIZE, des_y / Sprite.SCALED_SIZE)
                || has_flame(old_x / Sprite.SCALED_SIZE, old_y / Sprite.SCALED_SIZE);
    }

    public void fade(long now) {

    }

    /**
     * Đảm bảo start_delay ko đổi trong khoảng thời gian state == "Stop".
     */
    public void lock_delay_mode(long now) {
        if (delay_latch) {
            start_delay = now;
            delay_latch = false;
        }
    }

    @Override
    public void update(Scene scene, long now) {

    }
}
