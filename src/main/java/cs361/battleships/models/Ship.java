package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class Ship {

	@JsonProperty protected String kind = "";
	@JsonProperty protected List<Square> occupiedSquares;

	public Ship() {
		occupiedSquares = new ArrayList<Square>();
	}

	public Ship(String kind) {
		this.kind = kind;
		occupiedSquares = new ArrayList<>();
	}
	public boolean checkShip(int x, char y, boolean vertical, String kindV) {
		System.out.println("\nShip's thing");
		return true;
	}
	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
	public String getKind() {
		return kind;
	}
	public boolean makeOccupiedSquares(int x, char y, boolean isVertical) {
		return false;
	}
	public boolean isTaken( int x, char y) {
		for(int i = 0; i < occupiedSquares.size(); i++) {
			if(x == occupiedSquares.get(i).getRow() && y == occupiedSquares.get(i).getColumn()) {
				return true;
			}
		}
		return false;
	}
}

