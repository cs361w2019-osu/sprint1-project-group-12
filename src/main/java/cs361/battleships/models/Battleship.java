package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Battleship extends Ship {
    public Battleship() {}
    public Battleship(String kind) {
        this.kind  = kind;
    }

    @Override
    public boolean checkShip(int x, char y, boolean vertical, String kindV) {
        if(kindV.equals(kind)) {
            return true;
        }
        if (vertical) {
            if (isTaken(x + 1, y) || isTaken(x + 2, y) || isTaken(x + 3, y)) {
                return true;
            }
        } else {
            if (isTaken(x, (char) (y + 1)) || isTaken(x, (char) (y + 2)) || isTaken(x, (char) (y + 3))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
        char b = (char)(y + 1);
        char b2 = (char)(b + 1);
        char b3 = (char)(b2 + 1);
        if(isVertical == true) {
            if(x + 3 > 10) {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x+1, y));
            occupiedSquares.add(new Square(x+2, y));
            occupiedSquares.add(new Square(x+3, y));
        }
        if(isVertical == false) {
            if(b3 > 'J') {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x, b));
            occupiedSquares.add(new Square(x, b2));
            occupiedSquares.add(new Square(x, b3));
        }
        return true;
    }
}
