package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {
	private String kind = "";
	@JsonProperty private List<Square> occupiedSquares;

	public Ship() {
		occupiedSquares = new ArrayList<Square>();
	}

	public Ship(String kind) {
		this.kind = kind;
		occupiedSquares = new ArrayList<>();
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
	public String getKind() {
		return kind;
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

