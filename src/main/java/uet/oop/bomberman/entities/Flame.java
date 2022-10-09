package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Flame extends Bomb{

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

//    public List<Image> getHorizontalFlame() {
//        return horizontalFlame;
//    }
//
//    public List<Image> getLeftHorizontalFlame() {
//        return leftHorizontalFlame;
//    }
//
//    public List<Image> getRightHorizontalFlame() {
//        return rightHorizontalFlame;
//    }
//
//    public List<Image> getVerticalFlame() {
//        return verticalFlame;
//    }
//
//    public List<Image> getTopVerticalFlame() {
//        return topVerticalFlame;
//    }
//
//    public List<Image> getDownVerticalFlame() {
//        return downVerticalFlame;
//    }
//
//    public void flameAnimated(){
//        Flame leftHorizontalFlame = new Flame((int)(getX())-32, getY(), getLeftHorizontalFlame().get(getTypeOfBomb()));
//        Flame rightHorizontalFlame = new Flame((int)(getX())+32, getY(), getRightHorizontalFlame().get(getTypeOfBomb()));
//        Flame topVerticalFlame = new Flame(getX(), getY()-32, getTopVerticalFlame().get(getTypeOfBomb()));
//        Flame downVerticalFlame = new Flame(getX(), getY()+32, getDownVerticalFlame().get(getTypeOfBomb()));
//
//    }

    @Override
    public void update(Scene scene, long now) {

    }
}
