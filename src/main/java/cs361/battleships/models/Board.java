package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;

	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
	}

	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if(checkShips(x, y, isVertical, ship.getKind()) || ships.size() >= 3) {
			return false;
		}
		if(ship.getKind().equals("MINESWEEPER")) {
			char b = (char)(y + 1);
			if(isVertical == true) {
				if(x + 1 > 10) {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x+1, y));
			}
			if(isVertical == false) {
				if(b > 'J') {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x, b));
			}
		}
		if(ship.getKind().equals("DESTROYER")) {
			char b = (char)(y + 1);
			char b2 = (char)(b + 1);

			if(isVertical == true) {
				if(x + 2 > 10) {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x+1, y));
				ship.getOccupiedSquares().add(new Square(x+2, y));
			}
			if(isVertical == false) {
				if(b2 > 'J') {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x, b));
				ship.getOccupiedSquares().add(new Square(x, b2));
			}
		}
		if(ship.getKind().equals("BATTLESHIP")) {
			char b = (char)(y + 1);
			char b2 = (char)(b + 1);
			char b3 = (char)(b2 + 1);
			if(isVertical == true) {
				if(x + 3 > 10) {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x+1, y));
				ship.getOccupiedSquares().add(new Square(x+2, y));
				ship.getOccupiedSquares().add(new Square(x+3, y));
			}
			if(isVertical == false) {
				if(b3 > 'J') {
					return false;
				}
				ship.getOccupiedSquares().add(new Square(x,y));
				ship.getOccupiedSquares().add(new Square(x, b));
				ship.getOccupiedSquares().add(new Square(x, b2));
				ship.getOccupiedSquares().add(new Square(x, b3));
			}
		}
		ships.add(ship);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Result> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Result> attacks) {
        this.attacks = attacks;
    }

}
