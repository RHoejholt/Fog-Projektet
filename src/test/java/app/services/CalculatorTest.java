package app.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Rapport: Unit tests do not depend on any database, tests simple input and output of methods. This ensures the calculations have fewer sources of error
class CalculatorTest {

    //TODO: create 5 unit tests for each method
    @Test
    void calcPillarTest() {
        int result = Calculator.calcPillar(700);
        assertEquals(2, result);
    }

    @Test
    void calcBeamTest()  {
        int result = Calculator.calcBeams(700);
        assertEquals(2, result);
    }

    @Test
    void calcRaftersTest() {
        int result = Calculator.calcRafters(600);
        assertEquals(2, result);
    }

}