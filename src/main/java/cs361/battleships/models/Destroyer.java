package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Ship {
    public Destroyer()
    {}
    public Destroyer(String kind) {
        this.kind  = kind;
    }

    public boolean checkShip(int x, char y, boolean vertical, String kindV) {
        System.out.println("\n" + kind + " " + kindV);
        if(kindV.equals(kind)) {
            return true;
        }
        if (vertical) {
            if (isTaken(x + 1, y) || isTaken(x + 2, y)) {
                System.out.println("BAD1");
                return true;
            }
        } else {
            if (isTaken(x, (char) (y + 1)) || isTaken(x, (char) (y + 2))) {
                System.out.println("BAD2");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
        char b = (char)(y + 1);
        char b2 = (char)(b + 1);

        if(isVertical == true) {
            if(x + 2 > 10) {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x+1, y));
            occupiedSquares.add(new Square(x+2, y));
        }
        if(isVertical == false) {
            if(b2 > 'J') {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x, b));
            occupiedSquares.add(new Square(x, b2));
        }
        return true;
    }
}
