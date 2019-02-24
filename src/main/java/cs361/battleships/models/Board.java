package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board {
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Square> hits;



	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
		 public Board() {
			 ships = new ArrayList<>();
			 attacks = new ArrayList<>();
			 hits = new ArrayList<>();
		 }

	/*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if(checkShips(x, y, isVertical, ship.getKind()) || ships.size() >= 3) {
			return false;
		}
		if(!ship.makeOccupiedSquares(x, y, isVertical)) {
			return false;
		}
        Random rand = new Random();
        Square randomElement = ship.getOccupiedSquares().get(ship.getOccupiedSquares().size()-2);
        //make capq square at random location.
        ship.makeCapQ(randomElement.getRow(), randomElement.getColumn());
		ships.add(ship);
		return true;
	}






		 public void addHit  (int x, char y, int i){

			 //Very simple add to hitsquares array, but could add repeat check (multiples of same sq) here.
			 ships.get(i).getHitSquares().add(new Square(x , y));
		 }



		 //This function adds a ships 'occupied squares' and 'hit squares' list to the boards 'sunk squres array' (named hits)
		 //This stores the locations of sunks squares for use after the ship is deleted.

		 public void addSunk (int x, char y, int i){

			 for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {

				 int check = 0;
				 char tempy = ships.get(i).getOccupiedSquares().get(j).getColumn();
				 int tempx = ships.get(i).getOccupiedSquares().get(j).getRow();

						//Attempt at duplicate check but doesnt seem to work
				 for(int k = 0; k < this.hits.size(); k++) {

					 char checky = hits.get(k).getColumn();
					 int checkx = hits.get(k).getRow();
					 if(checky == tempy && checkx == tempx){
						 check = check+1;
					 }
				 }

				 //ADD DOUBLE CHECK HERE
				 if(check==0)
				 ships.get(i).getHitSquares().add(new Square(tempx , tempy));
			 }

			 ships.get(i).getOccupiedSquares().clear();

			 for(int k = 0; k < ships.get(i).getHitSquares().size(); k++) {

				 char tempc = ships.get(i).getHitSquares().get(k).getColumn();
				 int tempr  = ships.get(i).getHitSquares().get(k).getRow();
				 //Marked as sunk
				 this.getHits().add(new Square(tempr , tempc));

			 }

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

			 for(int k = 0; k < this.getHits().size(); k++) {

				 char hity = this.getHits().get(k).getColumn();
				 int hitx = this.getHits().get(k).getRow();
				 if(hity == y && hitx == x){
					 result.setResult(AtackStatus.INVALID);
					 return result;
				 }
			 }

			 for(int i = 0; i < attacks.size(); i++) {
				 if(attacks.get(i).getLocation().getRow() == x && attacks.get(i).getLocation().getColumn() == y &&  "CQHIT" != (attacks.get(i).getResult().toString())){
					 result.setResult(AtackStatus.INVALID);
					 return result;
				 }
			 }


			 result.setLocation(new Square(x , y));

			 int resultnum = 0;


			 for(int i = 0; i < ships.size(); i++) {
				 for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
					 char tempy = ships.get(i).getOccupiedSquares().get(j).getColumn();
					 int tempx = ships.get(i).getOccupiedSquares().get(j).getRow();

									//if you hit a ship square.
					 if(tempy == y && tempx == x) {
						 				//if that sq matches ships cq sqyare
						 if(ships.get(i).getCapq().getRow() == x && ships.get(i).getCapq().getColumn()== y){
							 ships.get(i).hitCq();
							 ships.get(i).hitCq();
							 result.setShip(ships.get(i));
							 result.setResult(AtackStatus.CQHIT);
							 if(ships.get(i).getCq() > 2){
								 result.setResult(AtackStatus.SUNK);
								 resultnum = resultnum + 1;
								 if(ships.get(i).getCq() > 4){
									 result.setResult(AtackStatus.INVALID);
									 return result;
								 }
							 }
						 }

						 else	{
							 ships.get(i).getOccupiedSquares().remove(j);
							 result.setShip(ships.get(i));
							 result.setResult(AtackStatus.HIT);
						 }

						 if(ships.get(i).getOccupiedSquares().size() == 0) {
							 result.setResult(AtackStatus.SUNK);
							 ships.get(i).hitCq();
							 ships.get(i).hitCq();
						 }

						 if(ships.get(i).getOccupiedSquares().size() == 1){

							 if(ships.get(i).getCq() > 1){
								 resultnum = resultnum + 1;
							 result.setResult(AtackStatus.SUNK);
								 ships.get(i).hitCq();
								 ships.get(i).hitCq();
							 }
						 }


						 if(ships.size() == 0) {
							 result.setResult(AtackStatus.SURRENDER);
						 }
						 attacks.add(result);

						 addHit(x, y, i);
						 
						 if(resultnum > 0){
							 addSunk(x, y, i);
							 ships.remove(i);
						 }

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
    public List<Square> getHits() {

        return hits;
    }

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
