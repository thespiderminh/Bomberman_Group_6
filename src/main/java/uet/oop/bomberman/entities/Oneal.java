package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.Objects;

public class Oneal extends Enemy {
    /**
     + Velo có thể thay đổi.
     + Di chuyển về phía bomber.
     */

    protected int life = 1;

    /**
     * 1: 50%
     * 2: 30%
     * 4: 15%
     * 8: 5%
     */
    int [] velos = {1,1,1,1,1,1,1,1,1,1,
                    2,2,2,2,2,2,
                    4,4,4,
                    8};
    boolean [] change_velo_possibility = {false,false,false,false,false,false,false,true,true,true}; // 30% change velo

    /**
     * Sau ít nhất bao lâu thì được đổi velo.
     */
    long [] change_velo_time = {2000000000L,3000000000L,4000000000L}; // 2s,3s,4s

    boolean [] stop_ratio = {false,false,false,false,false,false,false,true,true,true}; // 30% stop

    protected long start_velo;
    protected long velo_time;


    public Oneal(int x, int y, Image img) {
        super( x, y, img);
        right_images = Arrays.asList(Sprite.oneal_right1.getFxImage(), Sprite.oneal_right2.getFxImage(), Sprite.oneal_right3.getFxImage());
        left_images = Arrays.asList(Sprite.oneal_left1.getFxImage(), Sprite.oneal_left2.getFxImage(), Sprite.oneal_left3.getFxImage());
        dead_image = Arrays.asList(Sprite.oneal_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());

    }

    public int generate_vertical_direction() {
        if (BombermanGame.bomberman.getY() < this.y) {
            return 3; // Up
        } else if (BombermanGame.bomberman.getY() > this.y) {
            return 4; // Down
        }
        return -1;
    }

    public int generate_horizontal_direction() {
        if (BombermanGame.bomberman.getX() < this.x) {
            return 2; // Left
        } else if (BombermanGame.bomberman.getX() > this.x) {
            return 1; // Right
        }
        return -1;
    }


    @Override
    public void generate_direction(long now) {
        int i = 0;
        if(!BombermanGame.bomberman.isAlive() || now - start_delay > 1000000000L) {
            i = ((int)(Math.random() * 10) * 27) % 4 + 1; // 1-4
        } else {
            int dir = (int)(Math.random() * 10) % 2; // 0-1
            if (dir == 0) {
                i = generate_vertical_direction();
            } else {
                i = generate_horizontal_direction();
            }
        }
        if (i != -1) {
            current_state = states[i];
        } else {
            generate_direction(now);
        }
    }

    void generate_velo() {
        int k = (int)(Math.random()*10) % 10 ; // 0-9
        if (change_velo_possibility[k]) {
            int i = ((int)(Math.random()*10) * 91) % 20; // 0-19
            velo = velos[i];
            int j = (int)(Math.random()*100) % 3; // 0-2
            velo_time = change_velo_time[j];
        }
    }

    @Override
    public void sudden_stop(long interval) {
        int i = (int)(Math.random()*10) % 10 ;
        if (stop_ratio[i]) {
            current_state = "Stop";
        }
    }

    public void generate_coor() {
        int new_x = ((int)(Math.random() * 10) * 100) % 29 + 1; // 1-29
        int new_y = ((int)(Math.random() * 10) * 100) % 11 + 1; // 1-11
        this.x = new_x * Sprite.SCALED_SIZE;
        this.y = new_y * Sprite.SCALED_SIZE;
    }

    public void respawn() {
        generate_coor();
        while(BombermanGame.map[this.y/Sprite.SCALED_SIZE][this.x/Sprite.SCALED_SIZE] != ' ') {
            generate_coor();
        }
        this.img = right_images.get(0);
        current_state = "Stop";
        System.out.println(this.x/Sprite.SCALED_SIZE + " " + this.y/Sprite.SCALED_SIZE);
    }

    @Override
    public void update(Scene scene, long now) {

        if (get_burned() && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            current_state = "Dead";   // Dead
            --life;
        }

        if (Objects.equals(current_state, "Dead") || Objects.equals(current_state, "NULL")) {
            fade(now);
        }

        if (Objects.equals(current_state, "Stop")) {
            lock_delay_mode(now);
            generate_direction(now);
            if (!Objects.equals(current_state, states[0])) {
                start_move = now;
            }
        }

        if (des_x == x && des_y == y && (now - start_velo >= velo_time)) {
            generate_velo();
            start_velo = now;
        }

        if (Objects.equals(current_state, "Right")) {
            step = Sprite.SCALED_SIZE;
            update_des_x();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_right_to(des_x);
                delay_latch = true;
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
                delay_latch = true;
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
                delay_latch = true;
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
                delay_latch = true;
            }
            else {
                des_y = y;
                current_state = "Stop";
            }
        }

        change_animation(now);

        long interval = now - start_move;
        if (interval >= 500000000L && ((interval/10000000)%3 == 0) && x == des_x && y == des_y
                && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            sudden_stop(interval);
        }
    }
}

