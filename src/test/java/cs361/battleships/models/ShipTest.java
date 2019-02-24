package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {
    @Test
    public void testShipTaken() {
        Board board = new Board();
        Ship ship = new Destroyer("DESTROYER");
        board.placeShip(ship, 5, 'C', false);
        assertTrue(ship.isTaken(5, 'C'));
        assertTrue(ship.isTaken(5, 'D'));
        assertTrue(ship.isTaken(5, 'E'));
        assertTrue(ship.getKind() == "DESTROYER");

    }
}
