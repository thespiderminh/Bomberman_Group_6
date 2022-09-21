package uet.oop.bomberman.entities;

public class Map {
    char map [][];
    int m;  // number of rows
    int n;  // number of columns
    Map (int m, int n) {
        this.m = m;
        this.n = n;
        map = new char[m][n];
    }
}
