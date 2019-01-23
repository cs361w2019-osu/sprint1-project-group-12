package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

		private List<Result> turns;
		private List<Ship> map;

	//DO NOT change the signature of this method. It is used by the grading scripts.

	public Board() {
			turns = new ArrayList<Result>();
			map = new ArrayList<Ship>();
	}


	//DO NOT change the signatur e of this method. It is used by the grading scripts.

	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
			int ShipType = ship.getSkind();	//ship numerical length
			int curry = (int)y; //current Y


		List<Square> PropSpot = ship.checkSquares(ship, x, y, isVertical); //get list of proposed squares from ship class method
		boolean OkSpot = checkSpots(map, PropSpot);		//use map(list of ships on board) and propspot (list of proposed spots) to check for conflict


		if(!isVertical){
			if((ShipType+curry) > 75){
				return false;
			}
		}
		if(isVertical){
			if((ShipType+x) > 11){
				return false;
		}
		}
		boolean OkShip = checkShips(ship);

		if(ShipType < 1) {
				return false;
		}

		if(OkShip == false){

			return false;
		}

		if(OkSpot == false){
			return false;
		}

		 if (OkSpot == true && OkShip == true){
		 	ship.setOccupiedSquares(ship.getSkind(), x,y, isVertical);

			 this.map.add(ship);
			return true;
		 }

		return true;
	}



	//DO NOT change the signature of this method. It is used by the grading scripts.

	public Result attack(int x, char y) {
		return null;
	}

	public boolean checkShips(Ship ship){

		for (Ship ships : this.map){
			if(ship.getSkind() == (ships.getSkind())) return false;
		}
			return true;
	}

	public boolean checkSpots(List<Ship> map, List<Square> PropSpots){
			boolean spots = true;

			for (Square squareit : PropSpots) {
				for(Ship ships: map) {
					for(Square squareship : ships.getOccupiedSquares()){
						if(squareit.sqcheck(squareit, squareship)){
							spots = false;
						}
					}
				}
			}
		return spots;
	}





	public List<Ship> getShips() {
		return this.map;
	}

	public void setShips(List<Ship> ships) {
		this.map = ships;
	}

	public List<Result> getAttacks() {
		return this.turns;
	}

	public void setAttacks(List<Result> attacks) {
		this.turns = attacks;
	}






}
