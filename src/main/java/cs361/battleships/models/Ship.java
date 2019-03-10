package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class Ship {

	@JsonProperty protected String kind = "";
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected List<Square> hitSquares;
    private int cq = 0;
    private Square capq;

	public Ship() {
		occupiedSquares = new ArrayList<Square>();
		hitSquares = new ArrayList<Square>();

	}

	public Ship(String kind) {
		this.kind = kind;

		int listsize = 0;

		if(kind.equals("MINESWEEPER")) {
            this.cq = 1;
            listsize = 2;
        }
		if(kind.equals("DESTROYER")) {
            listsize = 3;
        }
		if(kind.equals("BATTLESHIP")) {
            listsize = 4;
        }
		int randomNum = ThreadLocalRandom.current().nextInt(0, listsize + 1);


//		System.out.printf("Placed a %s(%d)(%d)", kind, listsize, randomNum);
	//	this.getCq()=randomNum;
		occupiedSquares = new ArrayList<>();
		hitSquares = new ArrayList<>();


	}

	public void moveDown() {
		capq.setRow(capq.getRow() + 1);
		for(int i = 0; i < occupiedSquares.size(); i++) {
			occupiedSquares.get(i).setRow(occupiedSquares.get(i).getRow() + 1);
		}
		if(occupiedSquares.size() > 0) {
			for (int i = 0; i < hitSquares.size(); i++) {
				hitSquares.get(i).setRow(hitSquares.get(i).getRow() + 1);
			}
		}
	}
	public void moveUp() {
		capq.setRow(capq.getRow() - 1);
		for(int i = 0; i < occupiedSquares.size(); i++) {
			occupiedSquares.get(i).setRow(occupiedSquares.get(i).getRow() - 1);
		}
		if(occupiedSquares.size() > 0) {
			for (int i = 0; i < hitSquares.size(); i++) {
				hitSquares.get(i).setRow(hitSquares.get(i).getRow() - 1);
			}
		}
	}
	public void moveLeft() {
		capq.setColumn((char)((int)capq.getColumn() - 1));
		for(int i = 0; i < occupiedSquares.size(); i++) {
			occupiedSquares.get(i).setColumn((char)((int)occupiedSquares.get(i).getColumn() - 1));
		}
		if(occupiedSquares.size() > 0) {
			for (int i = 0; i < hitSquares.size(); i++) {
				hitSquares.get(i).setColumn((char) ((int) hitSquares.get(i).getColumn() - 1));
			}
		}
	}
	public void moveRight() {
		capq.setColumn((char) ((int) capq.getColumn() + 1));
		for (int i = 0; i < occupiedSquares.size(); i++) {
			occupiedSquares.get(i).setColumn((char) ((int) occupiedSquares.get(i).getColumn() + 1));
		}
		if (occupiedSquares.size() > 0) {
			for (int i = 0; i < hitSquares.size(); i++) {
				hitSquares.get(i).setColumn((char) ((int) hitSquares.get(i).getColumn() + 1));
			}
		}
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
	public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
		return false;
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
