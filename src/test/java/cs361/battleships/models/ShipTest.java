package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {
    @Test
    public void testShipTaken() {
        Board board = new Board();
        Ship ship = new Ship("DESTROYER");
        board.placeShip(ship, 5, 'C', false);
        assertTrue(ship.isTaken(5, 'C'));
        assertTrue(ship.getKind() == "DESTROYER");

    }
}
