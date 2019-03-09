package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    @Test
    public void testGamePlace() {
      Game game = new Game();
      Ship ship = new Minesweeper("MINESWEEPER");
      assertTrue(game.placeShip(ship, 5, 'A', false));
    }
    @Test
    public void testGameAttack() {
        Game game = new Game();
        Ship ship = new Minesweeper("MINESWEEPER");
        assertTrue(game.getOpponentsBoard().placeShip(ship, 5, 'A', false));
        Ship ship2 = new Submarine("SUBMARINE");
        assertTrue(game.getOpponentsBoard().placeShip(ship2, 8, 'A', false));
        assertTrue(game.attack(8, 'A'));  
        assertTrue(game.attack(5, 'A'));
        assertFalse(game.attack(5, 'Z'));
    }
}
