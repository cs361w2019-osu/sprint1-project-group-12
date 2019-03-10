package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	private int row;
	private char column;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public boolean outOfBounds(int moveInt) {
		int temp;
		if(moveInt == 1) {
			temp = column;
			if(temp - 1 < 65) {
				return true;
			}
		}
		if(moveInt == 2) {
			if(row - 1 < 1) {
				return true;
			}
		}
		if(moveInt == 3) {
			temp = column;
			if(temp + 1 > 74) {
				return true;
			}
		}
		if(moveInt == 4) {
			if(row + 1 > 10) {
				return true;
			}
		}
		return false;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}

