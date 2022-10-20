package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    private static final int FRAME_PER_SECOND = 90;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();

    public static char[][] load_map(String absolute_path) {
        File file = new File(absolute_path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int level = sc.nextInt();
        int row = sc.nextInt();
        int col = sc.nextInt();
        char[][] a = new char[row][col];

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String temp = null;
        for (int i = -1; i < row; ++i) {
            try {
                temp = br.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (i != -1) {
                for (int j = 0; j < col; ++j) {
                    a[i][j] = temp.charAt(j);
                }
            }
        }
        return a;
    }

    public static char[][] map = load_map("res/levels/Level1.txt");


    public static void main(String[] args) {

        Application.launch(BombermanGame.class);

    }

    private Scene scene;

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long prevTime = 0;

            @Override
            public void handle(long now) {
                long dt = now - prevTime;

                if (dt > 1000000000 / FRAME_PER_SECOND) {
                    prevTime = now;
                    update(now);
                }
                render();
            }
        };
        timer.start();

//        backgroundMap();
        createMap(map);
        Audio audio = new Audio();
        audio.playStart();
        audio.playBGM();
        Entity bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
        entities.add(bomberman);

    }

    public void backgroundMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void createMap(char[][] a) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                Entity movable = null;
                if (a[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                } else if (a[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                }
//                else if (a[i][j] == 'x') {
//                    object = new Portal(j, i, Sprite.portal.getFxImage());
//                }
                else if (a[i][j] == '1') {
                    movable = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == '2') {
                    movable = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                }
//                else if (a[i][j] == 'b') {
//                    object = new Bomb(j, i, Sprite.bomb.getFxImage());
//                }
//                else if (a[i][j] == 'f') {
//                    object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
//                }
//                else if (a[i][j] == 's') {
//                    object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
//                }
                else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                }
            }
        }

    }

    public void update(long now) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(scene, now);
            if (entities.get(i).getImg() == null) {
                entities.remove(i);
            }
        }
        for (int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).update(scene, now);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static void setEntities(List<Entity> entities) {
        BombermanGame.entities = entities;
    }

    public static void setStillObjects(List<Entity> stillObjects) {
        BombermanGame.stillObjects = stillObjects;
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    public static Entity getAt(int x, int y) {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i).getY() == y && stillObjects.get(i).getX() == x) {
                return stillObjects.get(i);
            }
        }
        return null;
    }
}
