package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends Ship {
    public Minesweeper()
    {}
    public Minesweeper(String kind) {
        this.kind  = kind;
    }

    public boolean checkShip(int x, char y, boolean vertical, String kindV) {
        if (isTaken(x, y)) {
            return true;
        }
        if(kindV.equals(kind)) {
            return true;
        }
        if (vertical) {
            if (isTaken(x + 1, y)) {
                return true;
            }
        } else {
            if (isTaken(x, (char) (y + 1))) {
                return true;
            }
        }
        return false;
    }

    public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
        char b = (char)(y + 1);
        if(isVertical == true) {
            if(x + 1 > 10) {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x+1, y));
        }
        if(isVertical == false) {
            if(b > 'J') {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x, b));
        }
        return true;
    }
}
