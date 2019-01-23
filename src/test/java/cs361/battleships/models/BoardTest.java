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
        assertTrue(board.placeShip(new Ship("DESTROYER"), 6, 'B', false));
    }
    @Test
    public void testUniqueShips() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', false);
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 6, 'B', false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 7, 'B', false));
    }
    @Test
    public void testAttack() {
        Board board = new Board();
       assertTrue(board.attack(5, 'C').getResult() == AtackStatus.MISS);
       assertTrue(board.attack(11, 'C').getResult() == AtackStatus.INVALID);
    }
}
