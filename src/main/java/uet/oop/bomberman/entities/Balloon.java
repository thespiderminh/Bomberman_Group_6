package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Balloon extends Enemy {
    private final int velo = 1;
    private int velocity_x = 0;
    private int velocity_y = 0;

    private List<Image> right_images = Arrays.asList(Sprite.balloom_right1.getFxImage(), Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage());
    private List<Image> left_images = Arrays.asList(Sprite.balloom_left1.getFxImage(), Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage());

    private List<Image> dead_image = Arrays.asList(Sprite.balloom_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());

    private int step = Sprite.SCALED_SIZE;

    /**
     * Destination x,y.
     */
    private int des_x = x;
    private int des_y = y;

    /**
     * Footprint.
     */
    private int old_x = x;
    private int old_y = y;

    /**
     * Stop,Right,Left,Up,Down.
     */
    String [] states = { "Stop", "Right", "Left", "Up", "Down", "Dead"};

    boolean [] stop_ratio = {false,false,false,false,false,false,false,true,true,true}; // 70%

    private String current_state = "Stop";

    /**
    * Thời điểm bắt đầu 1 chu kì changeAnimation.
    */
    private long startTime;

    /**
     * Thời điểm bắt đầu delay sau mỗi lần stop.
     */
    private long start_delay = 0;

    /**
     * Thời điểm bắt đầu move sau quãng stop.  (start_move - start_delay = stop interval)
     */
    private long start_move = 0;

    private boolean delay_latch = true;

    private long start_dead;

    public void update_des_x() {
        if (des_x == x) {
            des_x = x + step;
            old_x = x;
            //System.out.println("Old_x: " + old_x);
        }
    }

    public void update_des_y() {
        if (des_y == y) {
            des_y = y + step;
            old_y = y;
            //System.out.println("Old_y: " + old_y);
        }
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
        int i = (int)(Math.random()*10) % 10 ;
        if (stop_ratio[i] == true) {
            current_state = "Stop";
        }
    }

    public void reset(long now) {
        if (now - startTime > 450000000L) {
            startTime = now;
        }
    }

    public void change_animation(long now) {
        reset(now);

        if (Objects.equals(current_state, "Right") || Objects.equals(current_state, "Up")) {
            if(now - startTime < 150000000L) {
                this.img = right_images.get(0);
            } else if (now - startTime < 300000000L) {
                this.img = right_images.get(1);
            } else {
                this.img = right_images.get(2);
            }
        } else if (Objects.equals(current_state, "Left") || Objects.equals(current_state, "Down")) {
            if(now - startTime < 150000000L) {
                this.img = left_images.get(0);
            } else if (now - startTime < 300000000L) {
                this.img = left_images.get(1);
            } else {
                this.img = left_images.get(2);
            }
        }

    }

    /**
     * Kiểm tra xem có bị dính lửa ko.
     */
    public boolean get_burned() {
        return has_flame(des_x / Sprite.SCALED_SIZE, des_y / Sprite.SCALED_SIZE)
                || has_flame(old_x / Sprite.SCALED_SIZE, old_y / Sprite.SCALED_SIZE);
    }

    public void fade(long now) {
        if (Objects.equals(current_state, "Dead")) {
            System.out.println("Once");
            start_dead = now;
            current_state = "NULL";
        }
        if (now - start_dead < 1000000000L ) {   // 1s delay
            this.img = dead_image.get(0);
        } else if (now - start_dead < 1500000000L) {
            this.img = dead_image.get(1);
        } else if (now - start_dead < 2000000000L) {
            this.img = dead_image.get(2);
        } else if (now - start_dead < 2500000000L) {
            this.img = dead_image.get(3);
        } else {
            this.img = null;
        }
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

        if (get_burned() && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            current_state = "Dead";   // Dead
        }

        if (Objects.equals(current_state, "Dead") || Objects.equals(current_state, "NULL")) {
            fade(now);
        }

        if (Objects.equals(current_state, "Stop")) {
            lock_delay_mode(now);
            if (now - start_delay >= 5000000L) {  // Delay 0.2s or more
                start_move = now;
                generate_direction();
                delay_latch = true;
            }
        }

        if (Objects.equals(current_state, "Right")) {
            step = Sprite.SCALED_SIZE;
            update_des_x();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_right_to(des_x);
            }
            else {
                des_x = x;
                current_state = "Stop";
            }
        }
        else if (Objects.equals(current_state, "Left")) {
            step = -Sprite.SCALED_SIZE;
            update_des_x();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_left_to(des_x);
            }
            else {
                des_x = x;
                current_state = "Stop";
            }
        }
        else if (Objects.equals(current_state, "Up")) {
            step = -Sprite.SCALED_SIZE;
            update_des_y();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_up_to(des_y);
            }
            else {
                des_y = y;
                current_state = "Stop";
            }
        }
        else if (Objects.equals(current_state, "Down")) {
            step = Sprite.SCALED_SIZE;
            update_des_y();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_down_to(des_y);
            }
            else {
                des_y = y;
                current_state = "Stop";
            }
        }

        /*  di chuyển tiếp */
//        if (x == des_x) {
//            if (Objects.equals(current_state, "Right")) {
//                step = Sprite.SCALED_SIZE;
//                update_des_x();
//            }
//            else {
//                step = -Sprite.SCALED_SIZE;
//                update_des_x();
//            }
//        }

        change_animation(now);

        long interval = now - start_move;
        if (interval >= 1000000000L && ((interval/100000000)%3 == 0) && x == des_x && y == des_y
        && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            sudden_stop(interval);
        }
    }
}