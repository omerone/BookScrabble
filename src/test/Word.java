package test;

import java.util.Arrays;
import java.util.Objects;

public class Word {

    Tile [] tiles;
    int row,col;
    boolean vertical;


    Word(Tile[] t, int r , int c , boolean v) {
        this.tiles = t;
        row = r;
        col = c;
        vertical = v;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

}
