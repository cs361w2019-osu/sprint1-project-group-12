package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ship {
	private String kind = "";
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private List<Square> hitSquares;
private int cq = 0;
private Square capq;

	public Ship() {
		occupiedSquares = new ArrayList<Square>();
		hitSquares = new ArrayList<Square>();

	}

	public Ship(String kind) {
		this.kind = kind;

		int listsize = 0;

		if(kind.equals("MINESWEEPER"))
			this.cq = 1;
			listsize =2;
			if(kind.equals("DESTROYER"))
			listsize= 3;
			if(kind.equals("BATTLESHIP"))
				listsize=4;

				int randomNum = ThreadLocalRandom.current().nextInt(0, listsize + 1);


//		System.out.printf("Placed a %s(%d)(%d)", kind, listsize, randomNum);
	//	this.getCq()=randomNum;
		occupiedSquares = new ArrayList<>();
		hitSquares = new ArrayList<>();


	}

	public List<Square> getOccupiedSquares() {

		return occupiedSquares;
	}

	public List<Square> getHitSquares() {

		return hitSquares;
	}
	public String getKind() {
		return kind;
	}

public int getCq(){
	return cq;
}
public Square getCapq(){
	return capq;
}

public void makeCapQ(int x, char y){
	this.capq = new Square(x, y);
}

public void hitCq(){
	cq++;
	return;
}
	public boolean isTaken( int x, char y) {
	//	int size = occupiedSquares.size();
	//	randr = randr.nextInt(occupiedSquares.size());


		for(int i = 0; i < occupiedSquares.size(); i++) {
			if(x == occupiedSquares.get(i).getRow() && y == occupiedSquares.get(i).getColumn()) {
				return true;
			}
		}
		return false;
	}
}
