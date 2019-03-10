package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Submarine extends Ship {
    public Submarine() {}
    public Submarine(String kind) {
        super(kind);
        this.kind  = kind;
    }

    @Override
    public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
        char b = (char)(y + 1);
        char b2 = (char)(b + 1);
        char b3 = (char)(b2 + 1);


      char b4 = (char)(b3 + 1);



        if(isVertical == true) {
            if(x + 3 > 10 || b > 'J') {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x+1, y));
            occupiedSquares.add(new Square(x+2, y));
            occupiedSquares.add(new Square(x+3, y));
            occupiedSquares.add(new Square(x+2, b)); //NEWWW
        }
        if(isVertical == false) {
            if(b3 > 'J' || x < 2) {
                return false;
            }
            occupiedSquares.add(new Square(x,y));
            occupiedSquares.add(new Square(x, b));
            occupiedSquares.add(new Square(x, b2));
            occupiedSquares.add(new Square(x, b3));
            occupiedSquares.add(new Square(x-1, b2)); // NEWWWW

        }
        return true;
    }
}
