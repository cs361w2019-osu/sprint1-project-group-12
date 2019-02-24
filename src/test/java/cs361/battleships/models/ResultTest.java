package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultTest {
    @Test
    public void testResult() {
        Result result = new Result();
        Square square = new Square(5, 'A');
        Ship ship = new Ship("MINESWEEPER");
        result.setResult(AtackStatus.INVALID);
        assertTrue(result.getResult() == AtackStatus.INVALID);
        result.setShip(ship);
        assertTrue(result.getShip() == ship);
        result.setLocation(square);
        assertTrue(result.getLocation() == square);
    }
}
