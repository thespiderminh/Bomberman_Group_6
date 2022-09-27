package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    private static final int FRAME_PER_SECOND = 30;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();

    public static char[][] map = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', 'p', 'b', ' ', ' ', ' ', ' ', '*', '*', ' ', '*', ' ', ' ', '1', ' ', '*', ' ', '2', ' ', '*', ' ', ' ', '*', ' ', '*', ' ', '*', ' ', ' ', ' ', '#'},
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
            private long prevTime = 0;
            @Override
            public void handle(long now) {
                long dt = now - prevTime;

                if(dt > 1000000000/FRAME_PER_SECOND) {
                    prevTime = now;
                    update();
                }
                render();
            }
        };
        timer.start();


        backgroundMap();
        createMap(map);

        Entity bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
        Entity bomb = new Bomb(-1,-1,Sprite.bomb.getFxImage());
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case UP -> ((Bomber) bomberman).moveUp();
                    case DOWN -> ((Bomber) bomberman).moveDown();
                    case RIGHT -> ((Bomber) bomberman).moveRight();
                    case LEFT -> ((Bomber) bomberman).moveLeft();
                    case SPACE ->  {bomb.setX((int)(bomberman.getCenterX() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE) ;
                                    bomb.setY((int)(bomberman.getCenterY() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE);}

                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case UP -> ((Bomber) bomberman).stopY();
                    case DOWN -> ((Bomber) bomberman).stopY();
                    case RIGHT -> ((Bomber) bomberman).stopX();
                    case LEFT -> ((Bomber) bomberman).stopX();
                }
            }
        });
        entities.add(bomberman);
        entities.add(bomb);



    }

    public void backgroundMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void createMap(char [][] a) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                Entity movable = null;
                if (a[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                }
                else if (a[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                }
//                else if (a[i][j] == 'x') {
//                    object = new Portal(j, i, Sprite.portal.getFxImage());
//                }
                else if (a[i][j] == '1') {
                    movable = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j,i,Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                }
                else if (a[i][j] == '2') {
                    movable = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j,i,Sprite.grass.getFxImage());
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

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }
}
