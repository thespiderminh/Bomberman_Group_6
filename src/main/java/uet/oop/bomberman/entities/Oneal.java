package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.getEntities;

public class Oneal extends Enemy {
    /**
     Velo có thể thay đổi.
     */

    private List<Image> right_images = Arrays.asList(Sprite.oneal_right1.getFxImage(), Sprite.oneal_right2.getFxImage(), Sprite.oneal_right3.getFxImage());
    private List<Image> left_images = Arrays.asList(Sprite.oneal_left1.getFxImage(), Sprite.oneal_left2.getFxImage(), Sprite.oneal_left3.getFxImage());

    private List<Image> dead_image = Arrays.asList(Sprite.oneal_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());


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

    boolean [] stop_ratio = {false,false,false,false,true,true,false,true,true,true}; // 50% stop

    private long start_velo;
    private long velo_time;


    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    void generate_direction() {
        int i = (int)(Math.random()*10) % 4 + 1; // 1-4
        current_state = states[i];
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

    @Override
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

    @Override
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
            if (now - start_delay >= 200000000L) {  // Delay 0.2s or more
                start_move = now;
                generate_direction();
                delay_latch = true;
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

        change_animation(now);

        long interval = now - start_move;
        if (interval >= 1000000000L && ((interval/100000000)%3 == 0) && x == des_x && y == des_y
                && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            sudden_stop(interval);
        }
    }
}

