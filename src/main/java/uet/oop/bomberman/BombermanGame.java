package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    private static final int FRAME_PER_SECOND = 90;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<Entity>();
    private static List<Entity> stillObjects = new ArrayList<Entity>();

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

    public static char[][] map;


    public static void main(String[] args) {

        Application.launch(BombermanGame.class);

    }

    private Scene scene;
    private Scene startScene;
    private Scene deathScene;
    private Scene endScene;
    Image image, image1, image2;
    ImageView imageView, imageView1, imageView2;
    File file, file1, file2;
    String localUrl, localUrl1, localUrl2;
    public static Bomber bomberman;
    public static int level = 0;
    public static Entity cup;
    public static boolean gameCup = false;
    public static boolean getCup = false;

    public static int numberOfEnemies = 0;
//    private  Heart heart1 = new Heart(1,0, Sprite.fullHeart.getFxImage());
//    private  Heart heart2 = new Heart(2,0, Sprite.fullHeart.getFxImage());
//    private  Heart heart3 = new Heart(3,0, Sprite.fullHeart.getFxImage());

    @Override
    public void start(Stage stage) throws MalformedURLException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        Group startGroup = new Group();
        file = new File("res/sprites/PlayButton1.png");
        localUrl = file.toURI().toURL().toString();
        image = new Image(localUrl);
        imageView = new ImageView(image);

        file1 = new File("res/sprites/bomberman_main.png");
        localUrl1 = file1.toURI().toURL().toString();
        image1 = new Image(localUrl1);
        imageView1 = new ImageView(image1);

        Group endGroup = new Group();
        file2 = new File("res/sprites/ending.png");
        localUrl2 = file2.toURI().toURL().toString();
        image2 = new Image(localUrl2);
        imageView2 = new ImageView(image2);

        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                file = new File("res/sprites/PlayButton2.png");
                try {
                    localUrl = file.toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                image = new Image(localUrl);
                imageView.setImage(image);
            }
        });

        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                file = new File("res/sprites/PlayButton1.png");
                try {
                    localUrl = file.toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                image = new Image(localUrl);
                imageView.setImage(image);

            }
        });

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(scene);
                Audio.getMenuSound().stop();
                Audio.setPlayStart();
                Audio.getPlayStart().play();
                Audio.setBackgroundMusic();
                Audio.getBackgroundMusic().play();
//                entities.add(heart1);
//                entities.add(heart2);
//                entities.add(heart3);
            }
        });

        imageView.setScaleX(0.7);
        imageView.setScaleY(0.7);
        imageView.setX(700);
        imageView.setY(160);
        imageView1.setScaleX(0.5);
        imageView1.setScaleY(0.5);
        imageView1.setX(-300);
        imageView1.setY(-200);
        imageView2.setScaleX(1);
        imageView2.setScaleY(1);
        imageView2.setX(0);
        imageView2.setY(0);
        imageView2.setFitHeight(Sprite.SCALED_SIZE * HEIGHT);
        imageView2.setFitWidth(Sprite.SCALED_SIZE * WIDTH);
        startGroup.getChildren().addAll(imageView1, imageView);
        endGroup.getChildren().add(imageView2);

        // Tao scene
        scene = new Scene(root);
        startScene = new Scene(startGroup, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        endScene = new Scene(endGroup, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        // Them scene vao stage
        stage.setScene(startScene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long prevTime = 0;

            @Override
            public void handle(long now) {

                long dt = now - prevTime;

                if (dt > 1000000000 / FRAME_PER_SECOND) {
                    prevTime = now;
                    if (stage.getScene().equals(scene)) {
                        update(now);
                        boolean isAlive = false;
                        for (Entity w : entities) {
                            if (w instanceof Bomber) {
                                isAlive = true;
                            }
                        }

                        if (!isAlive) { // bomber be killed
                            if (Bomber.getAmountOfLives() > 1) { // Respawn
                                Bomber.setAmountOfLives(Bomber.getAmountOfLives() - 1);
                                entities.remove(bomberman);
                                entities.removeIf(w -> w instanceof Bomb);
                                entities.removeIf(w -> w instanceof Flame);
                                Heart.setHeart(Bomber.getAmountOfLives());
                                Bomb.setNumberOfBombs(1);
                                Bomb.setNumberOfBombsOnScreen(0);
                                bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
                                bomberman.setAlive(true);
                                entities.add(bomberman);
                                Audio.getBackgroundMusic().play();
                            } else { // Restart
                                stage.setScene(startScene);
                                Audio.setLevelUp();
                                Audio.getLevelUp().play();
                                map = load_map("res/levels/Level1.txt");
                                Bomber.setAmountOfLives(3);
                                Bomb.setNumberOfBombs(1);
                                Bomb.setNumberOfBombsOnScreen(0);
                                entities = new ArrayList<Entity>();
                                stillObjects = new ArrayList<Entity>();
                                createMap1(map);
                                level = 1;
                                bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
                                bomberman.setAlive(true);
                                entities.add(bomberman);
                                Heart.addHeart();
                            }

                        } else if (numberOfEnemies == 0 && bomberman.inPortal()) { // to Level 2
                            level = 2;
                            Audio.setLevelUp();
                            Audio.getLevelUp().play();
                            map = load_map("res/levels/Level2.txt");
                            Bomb.setNumberOfBombs(1);
                            Bomb.setNumberOfBombsOnScreen(0);
                            entities = new ArrayList<Entity>();
                            stillObjects = new ArrayList<Entity>();
                            createMap2(map);
                            if (Bomber.getAmountOfLives() == 3) {
                                Heart.addHeart();
                            } else if (Bomber.getAmountOfLives() == 2) {
                                Heart.addHeart2();
                            } else {
                                Heart.addHeart1();
                            }
                            bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
                            bomberman.setAlive(true);
                            entities.add(bomberman);
                        } else if (numberOfEnemies == 0 && level == 1 && !gameCup) { // win game
                            int c_x, c_y;
                            do {
                                c_x = ((int)(Math.random() * 10) * 100) % 29 + 1; // 1-29
                                c_y = ((int)(Math.random() * 10) * 100) % 11 + 1; // 1-11
                                System.out.println(c_x + " " + c_y);
                            } while (BombermanGame.map[c_y][c_x] != ' ');
                            cup = new Cup(c_x, c_y, Sprite.cup.getFxImage());
                            entities.add(cup);
                            gameCup = true;
                        } else if (BombermanGame.bomberman.getCup(cup) && gameCup) {
                            System.out.println("Win game");
                            Audio.getBackgroundMusic().stop();
                            Audio.setEnding();
                            Audio.getEnding().play();
                            stage.setScene(endScene);
                            stage.show();
                        }
                    }
                }
                render();
            }

            public void write() {
                if (gameCup) {
                    System.out.println("Winning");
                }
            }
        };
        timer.start();

        Audio.setMenuSound();
        Audio.getMenuSound().play();

        map = load_map("res/levels/Level1.txt");
        createMap1(map);
        level = 1;
        bomberman = new Bomber(1, 1, Sprite.player_right_0.getFxImage());
        entities.add(bomberman);
        Heart.addHeart();
    }

    public void createMap1(char[][] a) {
        numberOfEnemies = 0;
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
                } else if (a[i][j] == 'p') {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == 'x') {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                } else if (a[i][j] == '1') {
                    ++numberOfEnemies;
                    movable = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == '2') {
                    ++numberOfEnemies;
                    movable = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == '3') {
                    ++numberOfEnemies;
                    movable = new Doll(j, i, Sprite.doll_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                }
            }
        }

    }

    public void createMap2(char[][] a) {
        numberOfEnemies = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                Entity movable = null;
                if (a[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall1.getFxImage());
                    stillObjects.add(object);
                } else if (a[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick1.getFxImage());
                    stillObjects.add(object);
                } else if (a[i][j] == 'p') {
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == 'x') {
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
                    stillObjects.add(object);
                    object = new Brick(j, i, Sprite.brick1.getFxImage());
                    stillObjects.add(object);
                } else if (a[i][j] == '1') {
                    ++numberOfEnemies;
                    movable = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == '2') {
                    ++numberOfEnemies;
                    movable = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else if (a[i][j] == '3') {
                    ++numberOfEnemies;
                    movable = new Doll(j, i, Sprite.doll_right1.getFxImage());
                    entities.add(movable);
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
                    stillObjects.add(object);
                    a[i][j] = ' ';
                } else {
                    object = new Grass(j, i, Sprite.grass1.getFxImage());
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
            if (stillObjects.get(i).getImg() == null) {
                stillObjects.remove(i);
            }
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
