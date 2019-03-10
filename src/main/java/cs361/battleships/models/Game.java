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
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful) {
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
            if(ship.getKind().equals("SUBMARINE")) {
                ship2 = new Submarine(ship.getKind());
                playersBoard.setSub(playersBoard.getShips().size()-1);
                opponentsBoard.setSub(playersBoard.getShips().size()-1);
            }

         opponentPlacedSuccessfully = opponentsBoard.placeShip(ship2, randRow(), randCol(), randVertical());
      //Opponent place same as player test
      //    opponentPlacedSuccessfully = opponentsBoard.placeShip(ship2, x, y, isVertical);

        } while (!opponentPlacedSuccessfully);



        //ADJUST TO MAKE SUB ALWAYS LAST SHIP IN SHIP-list

      if(playersBoard.getShips().size() == 4){
              if(playersBoard.getSub() != playersBoard.getShips().size()-1){
                 playersBoard.adjustSub();
                 opponentsBoard.adjustSub();
              }
      }
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

    public boolean moveShips(int moveInt) {
        boolean flag = playersBoard.moveShips(moveInt);
        return flag;
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
