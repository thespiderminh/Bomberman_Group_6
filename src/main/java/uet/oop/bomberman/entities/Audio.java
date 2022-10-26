package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

public class Audio {
    private File directory;
    private File[] files;
    private ArrayList<File> audio;
    private int audioNumber = 0;
    private Media media;
    private MediaPlayer mediaPlayer;
    private static final String bgm = "res/audio/bgm.mp3";
    private static final String walk1 = "res/audio/walk_1.wav";
    private static final String walk2 = "res/audio/walk_2.wav";
    private static final String walk3 = "res/audio/walk_3.wav";
    private static final String start = "res/audio/stage_start.wav";
    private static final String placedBomb = "res/audio/placed_bomb.wav";
    private static final String bombExplore = "res/audio/bomb_explored.wav";

    public void playStart() {
        media = new Media(new File(start).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public void placedBomb() {
        media = new Media(new File(placedBomb).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public void bombExplore() {
        media = new Media(new File(bombExplore).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public void playBGM() {
        media = new Media(new File(bgm).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void walk(int type) {
        media = new Media(new File("res/audio/walk_" + type + ".wav").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

}

