package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Doll extends Oneal {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        right_images = Arrays.asList(Sprite.doll_right1.getFxImage(), Sprite.doll_right2.getFxImage(), Sprite.doll_right3.getFxImage());
        left_images = Arrays.asList(Sprite.doll_left1.getFxImage(), Sprite.doll_left2.getFxImage(), Sprite.doll_left3.getFxImage());
        dead_image = Arrays.asList(Sprite.doll_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());
        life = 1;
    }

    @Override
    public boolean movable(int m_y, int m_x) {
        //follow the grass
        return (BombermanGame.map[m_y][m_x] == ' ' || BombermanGame.map[m_y][m_x] == '*'
            || BombermanGame.map[m_y][m_x] == 'b' || BombermanGame.map[m_y][m_x] == 'f'
            || BombermanGame.map[m_y][m_x] == 's' || BombermanGame.map[m_y][m_x] == 'x') && (!has_bomb(m_x,m_y));
    }

    @Override
    public int generate_vertical_direction() {
        if (!BombermanGame.bomberman.isAlive()) {
            return ((int)(Math.random() * 10) * 27) % 4 + 1;
        }
        if (BombermanGame.bomberman.getY() < this.y) {
            return 3; // Up
        } else if (BombermanGame.bomberman.getY() > this.y) {
            return 4; // Down
        }
        return generate_horizontal_direction();
    }

    @Override
    public int generate_horizontal_direction() {
        if (!BombermanGame.bomberman.isAlive()) {
            return ((int)(Math.random() * 10) * 27) % 4 + 1;
        }
        if (BombermanGame.bomberman.getX() < this.x) {
            return 2; // Left
        } else if (BombermanGame.bomberman.getX() > this.x) {
            return 1; // Right
        }
        return generate_vertical_direction();
    }

    @Override
    public void generate_direction(long now) {
        int i = 0;

        if (BombermanGame.bomberman == null) {
            i = ((int)(Math.random() * 10) * 27) % 4 + 1; // 1-4
        } else {
            int dir = (int)(Math.random() * 10) % 2; // 0-1
            if (dir == 0) {
                i = generate_vertical_direction();
            } else {
                i = generate_horizontal_direction();
            }
        }

        current_state = states[i];
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

//        if (des_x == x && des_y == y && (now - start_velo >= velo_time)) {
//            generate_velo();
//            start_velo = now;
//        }

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

        if (x == des_x && y == des_y
                && !Objects.equals(current_state, "Dead")
                && !Objects.equals(current_state, "NULL")) {
            current_state = "Stop";
        }
    }
}
