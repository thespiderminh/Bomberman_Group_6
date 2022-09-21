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

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        char[][] map = {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', 'p', ' ', ' ', ' ', ' ', ' ', '*', '*', ' ', '*', ' ', ' ', '1', ' ', '*', ' ', '2', ' ', '*', ' ', ' ', '*', ' ', '*', ' ', '*', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#', '*', '#', ' ', '#', ' ', '#', '*', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', '*', '#', '*', '#', ' ', '#'},
                {'#', ' ', ' ', 'x', '*', ' ', ' ', ' ', ' ', ' ', '*', '*', '*', ' ', ' ', '*', ' ', ' ', '1', ' ', ' ', ' ', '*', ' ', '2', ' ', '*', ' ', '*', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', ' ', '#', ' ', '#', '*', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#'},
                {'#', 'f', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', '*', '*', ' ', ' ', '*', ' ', ' ', '*', ' ', ' ', ' ', '1', ' ', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', ' ', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', '*', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', '*', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', '*', ' ', ' ', ' ', ' ', '*', '*', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '*', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*', ' ', ' ', ' ', '*', ' ', ' ', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };


        createMap(map);

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

    }

    public void createMap(char [][] a) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                Entity object;
                if (a[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                }
                else if (a[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                }
//                else if (a[i][j] == 'x') {
//                    object = new Portal(j, i, Sprite.portal.getFxImage());
//                }
//                else if (a[i][j] == '1') {
//                    object = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
//                }
//                else if (a[i][j] == '2') {
//                    object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
//                }
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
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
