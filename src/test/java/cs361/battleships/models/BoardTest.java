package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }
    @Test
    public void testOverlappingPlacement() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', false);
        assertFalse(board.placeShip(new Ship("DESTROYER"), 5, 'B', false));
    }
    @Test
    public void testNonOverlappingPlacement() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', false);
        assertTrue(board.placeShip(new Destroyer("DESTROYER"), 6, 'B', false));
    }
    @Test
    public void testUniqueShips() {
        Board board = new Board();
        board.placeShip(new Minesweeper("MINESWEEPER"), 5, 'C', false);
        assertTrue(board.placeShip(new Submarine("SUBMARINE"), 5, 'C', false));
        assertTrue(board.placeShip(new Destroyer("DESTROYER"), 7, 'B', false));
        assertTrue(board.placeShip(new Battleship("BATTLESHIP"), 4, 'A', false));
    }
    @Test
    public void testAttack() {
        Board board = new Board();
       assertTrue(board.attack(5, 'C').getResult() == AtackStatus.MISS);
        board.placeShip(new Battleship("BATTLESHIP"), 6, 'C', true);
        board.placeShip(new Battleship("BATTLESHIP"), 9, 'F', false);
        assertTrue(board.attack(6, 'C').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(7, 'C').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(7, 'C').getResult() == AtackStatus.MISS);
        assertTrue(board.attack(9, 'F').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(11, 'C').getResult() == AtackStatus.INVALID);
    }
    @Test
    public void testCheckShip() {
        Board board = new Board();
        board.placeShip(new Minesweeper("MINESWEEPER"), 5, 'C', false);
        assertFalse(board.checkShips(3, 'B', false, "MINESWEEPER"));
        assertFalse(board.checkShips(3, 'B', false, "DESTROYER"));
        assertFalse(board.checkShips(3, 'B', false, "BATTLESHIP"));
        assertTrue(board.checkShips(5, 'C', false, "MINESWEEPER"));
        assertTrue(board.checkShips(5, 'C', false, "DESTROYER"));
        assertFalse(board.checkShips(5, 'C', false, "SUBMARINE"));
        assertTrue(board.checkShips(5, 'C', false, "BATTLESHIP"));
        assertTrue(board.checkShips(4, 'C', true, "MINESWEEPER"));
        assertTrue(board.checkShips(3, 'C', true, "DESTROYER"));
        assertTrue(board.checkShips(3, 'C', true, "BATTLESHIP"));
        assertFalse(board.checkShips(5, 'A', true, "DESTROYER"));
        assertFalse(board.checkShips(5, 'A', true, "BATTLESHIP"));
        assertFalse(board.checkShips(5, 'A', true, "SUBMARINE"));
    }


    @Test
public void MoveTest() {
    Board board = new Board();
    assertTrue(board.placeShip(new Minesweeper("MINESWEEPER"), 3, 'A', false));
    assertTrue(board.placeShip(new Destroyer("DESTROYER"), 1, 'A', false));
    assertTrue(board.placeShip(new Battleship("BATTLESHIP"), 2, 'A', false));
    assertTrue(board.moveShips(2));
    assertTrue(board.moveShips(3));
    assertTrue(board.moveShips(1));
    assertTrue(board.moveShips(1));
    assertTrue(board.moveShips(2));
    assertTrue(board.moveShips(4));
    board.getShips().get(0).moveUp();
}
    @Test
    public void testPlacementVertical() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper("MINESWEEPER"), 3, 'A', true));
        assertTrue(board.placeShip(new Destroyer("DESTROYER"), 1, 'B', true));
        assertTrue(board.placeShip(new Battleship("BATTLESHIP"), 2, 'C', true));
        assertTrue(board.placeShip(new Submarine("SUBMARINE"), 2, 'C', true));
        assertTrue(board.placeShip(new Submarine("SUBMARINE"), 5, 'F', true));
        assertTrue(board.placeShip(new Submarine("SUBMARINE"), 5, 'F', false));
        Ship ship = new Ship();
    }
    @Test
    public void PlacementTest() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper("MINESWEEPER"), 3, 'A', false));
        assertTrue(board.placeShip(new Destroyer("DESTROYER"), 1, 'A', false));
        assertTrue(board.placeShip(new Battleship("BATTLESHIP"), 2, 'A', false));
    }
}
