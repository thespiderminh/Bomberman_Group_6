package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Heart extends Entity {
    private boolean isAdded = false;
    private static Heart heart1 = new Heart(1, 0, Sprite.fullHeart.getFxImage());
    private static Heart heart2 = new Heart(2, 0, Sprite.fullHeart.getFxImage());
    private static Heart heart3 = new Heart(3, 0, Sprite.fullHeart.getFxImage());
    private static Heart heart4 = new Heart(4, 0, Sprite.fullHeart.getFxImage());
    private static Heart death1 = new Heart(1, 0, Sprite.emptyHeart.getFxImage());
    private static Heart death2 = new Heart(2, 0, Sprite.emptyHeart.getFxImage());
    private static Heart death3 = new Heart(3, 0, Sprite.emptyHeart.getFxImage());
    private static Heart death4 = new Heart(4, 0, Sprite.emptyHeart.getFxImage());
    public Heart(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
    public static void addHeart4() {
        BombermanGame.getEntities().add(death4);
        BombermanGame.getEntities().add(death1);
        BombermanGame.getEntities().add(death2);
        BombermanGame.getEntities().add(death3);
        BombermanGame.getEntities().add(heart1);
        BombermanGame.getEntities().add(heart2);
        BombermanGame.getEntities().add(heart3);
        BombermanGame.getEntities().add(heart4);
    }
    public static void addHeart3() {
        BombermanGame.getEntities().add(death1);
        BombermanGame.getEntities().add(death2);
        BombermanGame.getEntities().add(death3);
        BombermanGame.getEntities().add(heart1);
        BombermanGame.getEntities().add(heart2);
        BombermanGame.getEntities().add(heart3);
    }

    public static void addHeart1() {
        BombermanGame.getEntities().add(death1);
        BombermanGame.getEntities().add(heart1);
    }

    public static void addHeart2() {
        BombermanGame.getEntities().add(death1);
        BombermanGame.getEntities().add(heart1);
        BombermanGame.getEntities().add(death2);
        BombermanGame.getEntities().add(heart2);
    }
    public static void getHeartItem(){
        if(Bomber.getAmountOfLives() ==4){
            if(!heart4.isAdded()){
                heart4.setAdded(true);
                BombermanGame.getEntities().add(death4);
                BombermanGame.getEntities().add(heart4);
            }
        } else if (Bomber.getAmountOfLives() ==3){
            BombermanGame.getEntities().add(heart3);
        } else if (Bomber.getAmountOfLives()==2) {
            BombermanGame.getEntities().add(heart2);
        } else if (Bomber.getAmountOfLives()==1) {
            BombermanGame.getEntities().add(heart1);
        }
    }
    public static void setHeart(int amountOfLives) {
        if(amountOfLives ==3){
            for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
                if (BombermanGame.getEntities().get(i) == heart4) {
                    BombermanGame.getEntities().remove(i);
                    heart4.setAdded(false);
                }
            }
        }else if (amountOfLives == 2) {
            for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
                if (BombermanGame.getEntities().get(i) == heart3) {
                    BombermanGame.getEntities().remove(i);
                }
            }
        } else if (amountOfLives == 1) {
            for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
                if (BombermanGame.getEntities().get(i) == heart2) {
                    BombermanGame.getEntities().remove(i);
                }
            }
        }
    }

    @Override
    public void update(Scene scene, long now) {

    }
}
