package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Result> views;

	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		views = new ArrayList<>();
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

	public Result view(int x, char y){
		Result view = new Result();
		if(x < 1 || x > 10 || y < 'A' || y > 'J') {
			view.setViewSonar(ViewSonar.INVALIDED);
			return view;
		}

		for(int i = 0; i < views.size(); i++) {
			if(views.get(i).getLocation().getRow() == x && views.get(i).getLocation().getColumn() == y){
				view.setViewSonar(ViewSonar.INVALIDED);
				return view;
			}
		}
		view.setLocation(new Square(x , y));
		for(int i = 0; i < ships.size(); i++) { ;
			for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
				char tempy = ships.get(i).getOccupiedSquares().get(j).getColumn();
				int tempx = ships.get(i).getOccupiedSquares().get(j).getRow();
				if(tempy == y && tempx == x) {
					view.setShip(ships.get(i));
					view.setViewSonar(ViewSonar.FOUND);
					ships.get(i).getOccupiedSquares().remove(j);

					views.add(view);
					return view;
				}
			}
		}
		view.setViewSonar(ViewSonar.BLANK);
		views.add(view);
		return view;

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

	public List<Result> getViews() { return views;}

	public void setViews(List<Result> views) {this.views = views;}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

	public boolean checkShips(int x, char y, boolean vertical, String kind) {
		for(int i = 0; i < ships.size(); i++) {
			if (ships.get(i).isTaken(x, y)) {
				return true;
			}
			if(kind.equals(ships.get(i).getKind())) {
				return true;
			}
			if (kind.equals("MINESWEEPER")) {
				if (vertical) {
					if (ships.get(i).isTaken(x + 1, y)) {
						return true;
					}
				} else {
					if (ships.get(i).isTaken(x, (char) (y + 1))) {
						return true;
					}
				}
			}
			if (kind.equals("DESTROYER")) {
				if (vertical) {
					if (ships.get(i).isTaken(x + 1, y) || ships.get(i).isTaken(x + 2, y)) {
						return true;
					}
				} else {
					if (ships.get(i).isTaken(x, (char) (y + 1)) || ships.get(i).isTaken(x, (char) (y + 2))) {
						return true;
					}
				}
			}
			if (kind.equals("BATTLESHIP")) {
				if (vertical) {
					if (ships.get(i).isTaken(x + 1, y) || ships.get(i).isTaken(x + 2, y) || ships.get(i).isTaken(x + 3, y)) {
						return true;
					}
				} else {
					if (ships.get(i).isTaken(x, (char)(y + 1)) || ships.get(i).isTaken(x,(char)(y + 2)) || ships.get(i).isTaken(x,(char)(y + 3))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

