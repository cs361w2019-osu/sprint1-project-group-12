package cs361.battleships.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty 	private List<Square> occupiedSquares;
	@JsonProperty public int skind;
	@JsonProperty private String name;



	public Ship() {


	}

			//setting the kind integer based on passed string
	public Ship(String kind) {


		this.occupiedSquares = new ArrayList<Square>();
		this.name = kind;

		if (kind.equals("BATTLESHIP")){
		    this.skind = 4;
        }
        else if (kind.equals("DESTROYER")){
            this.skind = 3;
        }
        else if (kind.equals("MINESWEEPER")){
            this.skind = 2;
        }
        else {
            this.skind = 0;
        }


    }

	//given size, x, y, vert yes/no
	public void setOccupiedSquares(int s, int x, char y, boolean vert ) {

			int row = x;
			int column;
			int i = 0;
			column = (int)y;



		while(i < s){
					 Square addingSq = new Square(row, (char)column);
			//this.occupiedSquares.add(new Square(row, (char)column));
			this.occupiedSquares.add(addingSq);



			if(vert == true){
					row++;
				}
				if(vert == false){
					column++;
				}
				i++;
			}

		while(this.occupiedSquares.size() > s) {
			this.occupiedSquares.remove(s);
			}
		}



				//This basically same as 'setoccupiedsquares' but instead returns list of proposed locations to be checked in board

	public List<Square> checkSquares(Ship s, int x, char y, boolean vert){
		int row = x;
		int column;
		int i = 0;
		int length = s.getSkind();
		column = (int)y;
		List<Square> PropSpot = new ArrayList<>();



		while(i < length){
			//System.out.printf("\n\nin checking sq function\n\n");
			Square addingSq = new Square(row, (char)column);
				PropSpot.add(addingSq);
			if(vert == true){
				row++;
			}
			if(vert == false){
				column++;
			}
			i++;


		}
		return PropSpot;
	}


	public int getSkind() {
		return skind;
	}

	public String getname() {
		return this.name;
	}


	public int Health(){
			int remaining = 1;
		//	remaining = this.Skind-this.Hits;
			return remaining;
	}

	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}
}
