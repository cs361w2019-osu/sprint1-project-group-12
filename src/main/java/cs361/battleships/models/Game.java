package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();


	//DO NOT change the signature of this method. It is used by the grading scripts.

    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {



        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
       if (!successful) {
           return false;
        }

        Ship compShip = new Ship(ship.getname());
       boolean opponentPlacedSuccessfully = false;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(compShip, randRow(), randCol(), randVertical());
       } while (!opponentPlacedSuccessfully);

        return true;
    }


	//DO NOT change the signature of this method. It is used by the grading scripts.

    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() != INVALID);

        return true;
    }
            //Return char A-J (65-74 on ASCII) ((0-9) + 65)
    private char randCol() {
            int cholNum = (int)Math.random()*9+65;
            return (char)cholNum;
    }
                    //Return num 1-10 ((0-9) + 1 )
    private int randRow() {
            int randNum = (int)Math.random()*9+1;
            return randNum;
    }
            //Return boolean
    private boolean randVertical() {
            Random rand = new Random();
            boolean vert = false;
            vert = rand.nextBoolean();
        return vert;
    }

    }





