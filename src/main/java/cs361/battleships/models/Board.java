package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;



public class Board {
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Square> hits;
	@JsonProperty private int subLoc = 0;


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
		if(checkShips(x, y, isVertical, ship.getKind()) || ships.size() >= 5) {
			return true;

		}
		if(!ship.makeOccupiedSquares(x, y, isVertical)) {
			return false;
		}
        Random rand = new Random();
        Square randomElement = ship.getOccupiedSquares().get(ship.getOccupiedSquares().size()-2);
        //make capq square at random location.
        ship.makeCapQ(randomElement.getRow(), randomElement.getColumn());

//if(ship.getKind() === "SUBMARINE"){
	//			System.out.println("ADding ship (board.java29)\n");
//}

		ships.add(ship);
		return true;
	}

	public boolean moveShips(int moveInt) {
		List<Ship> failedShips = new ArrayList<>();
		boolean flag = false;
		for(int i = 0; i < ships.size(); i++) {
			boolean flag2 = true;
			flag = true;
			for(int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
				if (moveInt != 0) {
					if (checkLocation(ships.get(i).getOccupiedSquares().get(j), ships.get(i).getKind(), moveInt)) {
						flag = false;
					}
				}
				if(ships.get(i).getOccupiedSquares().get(j).outOfBounds(moveInt)) {
					flag2 = false;
				}
			}
			if(flag == true && flag2 == true) {
				if (moveInt == 1) {
					ships.get(i).moveLeft();
				}
				if (moveInt == 2) {
					ships.get(i).moveUp();
				}
				if (moveInt == 3) {
					ships.get(i).moveRight();
				}
				if (moveInt == 4) {
					ships.get(i).moveDown();
				}
			}
			if (flag == false) {
				failedShips.add(ships.get(i));
			}
		}
		for(int i = 0; i < failedShips.size(); i++) {
			flag = true;
			boolean flag2 = true;
			for(int j = 0; j < failedShips.get(i).getOccupiedSquares().size(); j++) {
				if (moveInt != 0) {
					if(checkLocation(failedShips.get(i).getOccupiedSquares().get(j), failedShips.get(i).getKind(), moveInt)) {
						flag = false;
					}
				}
				if(failedShips.get(i).getOccupiedSquares().get(j).outOfBounds(moveInt)) {
					flag2 = false;
				}
			}
			if(flag == true && flag2 == true) {
				if (moveInt == 1) {
					failedShips.get(i).moveLeft();
				}
				if (moveInt == 2) {
					failedShips.get(i).moveUp();
				}
				if (moveInt == 3) {
					failedShips.get(i).moveRight();
				}
				if (moveInt == 4) {
					failedShips.get(i).moveDown();
				}
			}
		}
		return true;
	}

	public boolean checkLocation(Square square, String type, int moveInt) {
		int x = 0, y = 0;
		switch(moveInt) {
			case 1: y = -1; x = 0;
				break;
			case 2: y = 0; x = -1;
				break;
			case 3: y = 1; x = 0;
				break;
			case 4: y = 0; x = 1;
				break;
		}
		for(int i = 0; i < ships.size(); i++) {
			if(ships.get(i).getKind() != type && ships.get(i).getKind() != "SUBMARINE") {
				for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
					if (ships.get(i).getOccupiedSquares().get(j).getColumn() == ((char)((int)square.getColumn() + y))
						&& ships.get(i).getOccupiedSquares().get(j).getRow() == (square.getRow() + x)) {
						return true;
					}
				}
			}
		}
		for(int i = 0; i < hits.size(); i++) {
			if(hits.get(i).getColumn() == ((char)((int)square.getColumn() + y))
				&& hits.get(i).getRow() == (square.getRow() + x)) {
				return true;
			}
		}
		return false;
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
					 if(this.getShips().size() < 4){
						 	break;
					 }
					 result.setResult(AtackStatus.INVALID);
					 return result;
				 }
			 }

			 for(int i = 0; i < attacks.size(); i++) {
				 if(attacks.get(i).getLocation().getRow() == x && attacks.get(i).getLocation().getColumn() == y &&  "CQHIT" != (attacks.get(i).getResult().toString())){

					 if(this.getShips().size() < 4){
							break;
					 }
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



						 				//if sub hit before one sunk
							if(ships.size() == 4 && ships.get(i).getKind().equals("SUBMARINE")){
								result.setResult(AtackStatus.MISS);
								attacks.add(result);
								return result;

							}

								///LASER ATTACK!@#$!@
																						//sub is at ships[ships.size()-1

							if(ships.size() < 4 && (i < ships.size()-1) ){



																	//check sub squares.
										for(int k = 0; k < ships.get(ships.size()-1).getOccupiedSquares().size(); k++){

											char suby = ships.get(ships.size()-1).getOccupiedSquares().get(k).getColumn();
											int subx = ships.get(ships.size()-1).getOccupiedSquares().get(k).getRow();


												//SUB HIT underneath another ship
												if(suby == y && subx == x){
														//CQ HIT!!
														if(ships.get(ships.size()-1).getCapq().getRow() == subx && ships.get(ships.size()-1).getCapq().getColumn()== suby){
																ships.get(ships.size()-1).hitCq();
																ships.get(ships.size()-1).hitCq();
																result.setResult(AtackStatus.CQHIT);
																attacks.add(result);
															}
														else{
													ships.get(ships.size()-1).getOccupiedSquares().remove(k);
													result.setResult(AtackStatus.HIT);
													attacks.add(result);
												}
												}


										}



							}



							///add this to laser

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






		 public void setSub(int setLoc) {

this.subLoc = setLoc;
		 }

		 public int getSub() {
			 		return this.subLoc;
		 }
		 public void adjustSub() {
Collections.swap(this.getShips(), this.getSub(), 3);
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
