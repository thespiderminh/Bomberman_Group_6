package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

public class Minvo extends Oneal {

    private String temp = null;

    private Stack<String> path = new Stack<String>(); // Right, Left, Up, Down


    public class Pair {
        int first;
        int second;
        public Pair(int x, int y) {
            first = x;
            second = y;
        }
    }

    private Stack<Pair> xypath = new Stack<Pair>();

    public boolean new_path(int t_x, int t_y) {
        if (xypath.size() < 3) {
            return true;
        }
        return (t_x != xypath.peek().first) || (t_y != xypath.peek().second);
    }

    private boolean backtrack = false;

    public void printPath() {
        // print path
        for (String s : path) {
            System.out.print(s + " ");
        }
        System.out.print('\n');
    }

    protected static boolean[] valid_dir = {false, true, true, true, true}; // 1,2,3,4 -> right, left, up, down

    private boolean stuck_ver = false;
    private boolean stuck_hor = false;

    public void reset_valid_dir() {
        for (boolean b : valid_dir) {
            if (!b) {
                b = true;
            }
        }
    }

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        right_images = Arrays.asList(Sprite.minvo_right1.getFxImage(), Sprite.minvo_right2.getFxImage(), Sprite.minvo_right3.getFxImage());
        left_images = Arrays.asList(Sprite.minvo_left1.getFxImage(), Sprite.minvo_left2.getFxImage(), Sprite.minvo_left3.getFxImage());
        dead_image = Arrays.asList(Sprite.minvo_dead.getFxImage(), Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage());
    }

    @Override
    public boolean movable(int m_y, int m_x) {
        //follow the grass
        return (BombermanGame.map[m_y][m_x] == ' '
                || BombermanGame.map[m_y][m_x] == 'b' || BombermanGame.map[m_y][m_x] == 'f'
                || BombermanGame.map[m_y][m_x] == 's' || BombermanGame.map[m_y][m_x] == 'x') && (!has_bomb(m_x,m_y));
    }


    public int vertical() {
        if (!BombermanGame.bomberman.isAlive()) {
            return ((int)(Math.random() * 10) * 27) % 4 + 1;
        }
        if (BombermanGame.bomberman.getY() < this.y) {
            return 3; // Up
        } else if (BombermanGame.bomberman.getY() > this.y) {
            return 4; // Down
        } else {
            return -1;
        }
    }

    public int horizontal() {
        if (!BombermanGame.bomberman.isAlive()) {
            return ((int)(Math.random() * 10) * 27) % 4 + 1;
        }
        if (BombermanGame.bomberman.getX() <= this.x) {
            return 2;
        } else if (BombermanGame.bomberman.getX() > this.x) {
            return 1;
        }
        return -1;
    }

    @Override
    public void generate_direction(long now) {
        int i = 0;

        if (BombermanGame.bomberman == null) {
            i = ((int)(Math.random() * 10) * 27) % 4 + 1; // 1-4
        } else {
            int dir = (int)(Math.random() * 10) % 2; // 0-1
            if (dir == 0) {
                i = vertical();
            } else {
                i = horizontal();
            }
        }

        if (i != -1) {
            current_state = states[i];
        } else {
            normal_generate_dir();
        }
    }

    private int exclude;

    public void normal_generate_dir() {
        int i;
        do {
            i = ((int)(Math.random() * 10) * 27) % 4 + 1; // 1-4
        } while (i == exclude);

        current_state = states[i];
    }

    @Override
    public void update(Scene scene, long now) {
        if (get_burned() && !Objects.equals(current_state, "Dead") && !Objects.equals(current_state, "NULL")) {
            current_state = "Dead";   // Dead
        }

        if (Objects.equals(current_state, "Dead") || Objects.equals(current_state, "NULL")) {
            fade(now);
        }

        if (new_path(des_x, des_y)) {
            //System.out.println("Reset");
            backtrack = false;
            exclude = 0;
            reset_valid_dir();
        }

        if (stuck_hor && stuck_ver) {
            stuck_hor = false;
            stuck_ver = false;
            temp = path.pop();
            xypath.pop();

            backtrack = true;
            if (Objects.equals(temp, "Right")) {
                reset_valid_dir();
                //valid_dir[1] = false;
                exclude = 1;
                //current_state = "Left";
            } else if (Objects.equals(temp, "Left")) {
                reset_valid_dir();
                //valid_dir[2] = false;
                exclude = 2;
                //current_state = "Right";
            } else if (Objects.equals(temp, "Up")) {
                reset_valid_dir();
                //valid_dir[3] = false;
                exclude = 3;
                //current_state = "Down";
            } else if (Objects.equals(temp, "Down")) {
                reset_valid_dir();
                //valid_dir[4] = false;
                exclude = 4;
                //current_state = "Up";
            }
        }

        if (Objects.equals(current_state, "Stop")) {
            lock_delay_mode(now);
            if (!backtrack) {
                generate_direction(now);
            } else {
                normal_generate_dir();
            }
            if (!Objects.equals(current_state, states[0])) {
                start_move = now;
            }
        }

        if (Objects.equals(current_state, "Right")) {
            step = Sprite.SCALED_SIZE;
            update_des_x();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_right_to(des_x);
                delay_latch = true;
                stuck_hor = false;
            }
            else {
                des_x = x;
                current_state = "Stop";
                stuck_hor = true;
            }
        }
        else if (Objects.equals(current_state, "Left")) {
            step = -Sprite.SCALED_SIZE;
            update_des_x();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_left_to(des_x);
                delay_latch = true;
                stuck_hor = false;
            }
            else {
                des_x = x;
                current_state = "Stop";
                stuck_hor = true;
            }
        }
        else if (Objects.equals(current_state, "Up")) {
            step = -Sprite.SCALED_SIZE;
            update_des_y();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_up_to(des_y);
                delay_latch = true;
                stuck_ver = false;
            }
            else {
                des_y = y;
                current_state = "Stop";
                stuck_ver = true;
            }
        }
        else if (Objects.equals(current_state, "Down")) {
            step = Sprite.SCALED_SIZE;
            update_des_y();
            if (movable(des_y/Sprite.SCALED_SIZE, des_x/Sprite.SCALED_SIZE)) {
                move_down_to(des_y);
                delay_latch = true;
                stuck_ver = false;
            }
            else {
                des_y = y;
                current_state = "Stop";
                stuck_ver = true;
            }
        }

        change_animation(now);

        if (x == des_x && y == des_y
                && !Objects.equals(current_state, "Dead")
                && !Objects.equals(current_state, "NULL")) {

            System.out.println("Backtrack: " + backtrack);
            System.out.println("Stuck Hor,ver " + stuck_hor + " " + stuck_ver);
            if (!backtrack) {
                Pair new_pair = new Pair(x, y);
                xypath.push(new_pair);
            }

            if (Objects.equals(current_state, "Right") && !backtrack) {
                path.push("Right");
                printPath();
            } else if (Objects.equals(current_state, "Left") && !backtrack) {
                path.push("Left");
                printPath();
            } else if (Objects.equals(current_state, "Up") && !backtrack) {
                path.push("Up");
                printPath();
            } else if (Objects.equals(current_state, "Down") && !backtrack) {
                path.push("Down");
                printPath();
            }
            current_state = "Stop";

        }
    }
}

