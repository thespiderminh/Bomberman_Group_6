package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Balloon extends Enemy {
    boolean [] stop_ratio = {false,false,false,false,false,false,false,true,true,true}; // 30% stop

    public Balloon(int x, int y, Image img) {
        super( x, y, img);
        right_images = Arrays.asList(Sprite.balloom_right1.getFxImage(), Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage());
        left_images = Arrays.asList(Sprite.balloom_left1.getFxImage(), Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage());
        dead_image = Arrays.asList(Sprite.balloom_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());
    }

    @Override
    void generate_direction(long now) {
        int i = ((int)(Math.random() * 10) * 27) % 4 + 1;
        current_state = states[i];
    }

    @Override
    public void sudden_stop(long interval) {
        int i = (int)(Math.random()*10) % 10 ;
        if (stop_ratio[i]) {
            current_state = "Stop";
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
                generate_direction(now);
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

        change_animation(now);

        long interval = now - start_move;
        if (interval >= 1000000000L && ((interval/100000000)%3 == 0) && x == des_x && y == des_y
        && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            sudden_stop(interval);
        }
    }
}