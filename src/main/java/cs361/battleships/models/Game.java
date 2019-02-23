package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        System.out.println("We are here.");
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful) {
            System.out.print("went wrong here");
            return false;
        }

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            Ship ship2 = null;
            if(ship.getKind().equals("MINESWEEPER")) {
                ship2 = new Minesweeper(ship.getKind());
            }
            if(ship.getKind().equals("DESTROYER")) {
                ship2 = new Destroyer(ship.getKind());
            }
            if(ship.getKind().equals("BATTLESHIP")) {
                ship2 = new Battleship(ship.getKind());
            }
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship2, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);
        System.out.println("Opponent is done!");
        return true;
    }

    /*
   DO NOT change the signature of this method. It is used by the grading scripts.
    */
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
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }
    public Board getPlayersBoard() {
        return playersBoard;
    }

    public Board getOpponentsBoard() {
        return opponentsBoard;
    }

    private char randCol() {
        // TODO implement
        char col;
        col = (char)(Math.random()*10 + 'A');
        return col;
    }

    private int randRow() {
        int row;
        row = (int)(Math.random()*10 + 1 );
        return row;
    }

    private boolean randVertical() {
        int vertical;
        vertical = (int)(Math.random()*2 + 1);
        if(vertical == 1){
            return true;
        }
        return false;
    }
}
