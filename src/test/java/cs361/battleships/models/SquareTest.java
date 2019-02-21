package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {
    @Test
    public void testSquares() {
        Square square = new Square(5,'A');
        assertTrue(square.getColumn() == 'A');
        assertTrue(square.getRow() == 5);
        square.setColumn('B');
        square.setRow(3);
        assertFalse(square.getColumn() == 'A');
        assertFalse(square.getRow() == 5);
    }
}
