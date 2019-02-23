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
		System.out.println("What's going on?");
		if(checkShips(x, y, isVertical, ship.getKind()) || ships.size() >= 3) {
			System.out.print("Problem here: 1");
			return false;
		}
		if(!ship.makeOccupiedSquares(x, y, isVertical)) {
			System.out.print("Problem here: 2");
			return false;
		}
		ships.add(ship);
		return true;
	}

	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
	public Result attack(int x, char y) {
		Result result = new Result();
		if(x < 1 || x > 10 || y < 'A' || y > 'J') {
			result.setResult(AtackStatus.INVALID);
			return result;
		}
		for(int i = 0; i < attacks.size(); i++) {
			if(attacks.get(i).getLocation().getRow() == x && attacks.get(i).getLocation().getColumn() == y){
				result.setResult(AtackStatus.INVALID);
				return result;
			}
		}
		result.setLocation(new Square(x , y));
		for(int i = 0; i < ships.size(); i++) { ;
			for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
				char tempy = ships.get(i).getOccupiedSquares().get(j).getColumn();
				int tempx = ships.get(i).getOccupiedSquares().get(j).getRow();
				if(tempy == y && tempx == x) {
					result.setShip(ships.get(i));
					result.setResult(AtackStatus.HIT);
					ships.get(i).getOccupiedSquares().remove(j);
					if(ships.get(i).getOccupiedSquares().size() == 0) {
						result.setResult(AtackStatus.SUNK);
						ships.remove(i);
					}
					if(ships.size() == 0) {
						result.setResult(AtackStatus.SURRENDER);
					}
					attacks.add(result);
					return result;
				}
			}
		}
		result.setResult(AtackStatus.MISS);
		attacks.add(result);
		return result;
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
	public boolean checkShips(int x, char y, boolean vertical, String kind) {
		System.out.println(ships);
		for(int i = 0; i < ships.size(); i++) {
			System.out.print("result: ");
			System.out.print(ships.get(i).checkShip(x, y, vertical, kind));
			if(ships.get(i).checkShip(x, y, vertical, kind)) {
				return true;
			}
		}
		return false;
	}
}

