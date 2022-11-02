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
    private static final String bgm = "res/audio/bgm.mp3";
    private static final String walk1 = "res/audio/walk_1.wav";
    private static final String walk2 = "res/audio/walk_2.wav";
    private static final String walk3 = "res/audio/walk_3.wav";
    private static final String start = "res/audio/stage_start.wav";
    private static final String placedBomb = "res/audio/placed_bomb.wav";
    private static final String bombExplore = "res/audio/bomb_explored.wav";
    private static final String menu = "res/audio/title_screen.mp3";

    private static final String win = "res/audio/win.wav";

    private static final String dead = "res/audio/dead.wav";
    private static final String getItem = "res/audio/power_up.wav";
    private static MediaPlayer playStart;

    public static MediaPlayer getPlayStart() {
        return playStart;
    }

    public static void setPlayStart() {
        playStart = null;
        playStart = new MediaPlayer(new Media(new File(start).toURI().toString()));
        playStart.setVolume(playStart.getVolume() - 0.4);
    }

    private static MediaPlayer backgroundMusic;

    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void setBackgroundMusic() {
        backgroundMusic = null;
        backgroundMusic = new MediaPlayer(new Media(new File(bgm).toURI().toString()));
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private static MediaPlayer placeBomb;

    public static MediaPlayer getPlaceBomb() {
        return placeBomb;
    }

    public static void setPlaceBomb() {
        placeBomb = null;
        placeBomb = new MediaPlayer(new Media(new File(placedBomb).toURI().toString()));
    }

    private static MediaPlayer bombExploring;

    public static MediaPlayer getBombExploring() {
        return bombExploring;
    }

    public static void setBombExploring() {
        bombExploring = null;
        bombExploring = new MediaPlayer(new Media(new File(bombExplore).toURI().toString()));
    }

    private static MediaPlayer walk1Sound;

    public static MediaPlayer getWalk1Sound() {
        return walk1Sound;
    }

    public static void setWalk1Sound() {
        walk1Sound = null;
        walk1Sound = new MediaPlayer(new Media(new File(walk1).toURI().toString()));
        walk1Sound.setVolume(walk1Sound.getVolume() - 0.4);
    }

    private static MediaPlayer walk2Sound;

    public static MediaPlayer getWalk2Sound() {
        return walk1Sound;
    }

    public static void setWalk2Sound() {
        walk2Sound = null;
        walk2Sound = new MediaPlayer(new Media(new File(walk2).toURI().toString()));
        walk2Sound.setVolume(walk2Sound.getVolume() - 0.4);
    }

    private static MediaPlayer walk3Sound;

    public static MediaPlayer getWalk3Sound() {
        return walk3Sound;
    }

    public static void setWalk3Sound() {
        walk3Sound = null;
        walk3Sound = new MediaPlayer(new Media(new File(walk3).toURI().toString()));
        walk3Sound.setVolume(walk3Sound.getVolume() - 0.4);
    }

    private static MediaPlayer menuSound;

    public static MediaPlayer getMenuSound() {
        return menuSound;
    }

    public static void setMenuSound() {
        menuSound = null;
        menuSound = new MediaPlayer(new Media(new File(menu).toURI().toString()));
        menuSound.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private static MediaPlayer deadSound;

    public static MediaPlayer getDeadSound() {
        return deadSound;
    }

    public static void setDeadSound() {
        deadSound = null;
        deadSound =new MediaPlayer(new Media(new File(dead).toURI().toString()));
    }

    private static MediaPlayer winSound;

    public static MediaPlayer getWinSound() {
        return winSound;
    }

    public static void setWinSound() {
        winSound = null;
        winSound = new MediaPlayer(new Media(new File(win).toURI().toString()));
    }
    private static MediaPlayer powerUp;

    public static MediaPlayer getPowerUp() {
        return powerUp;
    }

    public static void setPowerUp() {
        powerUp = null;
        powerUp = new MediaPlayer(new Media(new File(getItem).toURI().toString()));
    }
}

