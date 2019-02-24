package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Ship {
    public Destroyer()
    {}
    public Destroyer(String kind) {
        super(kind);
        this.kind  = kind;

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
