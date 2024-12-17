package app.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Rapport: Unit tests do not depend on any database, tests simple input and output of methods. This ensures the calculations have fewer sources of error
class CalculatorTest {


    @Test
    void calcPillarTest1() {
        int result = Calculator.calcPillar(240);
        assertEquals(4, result);
    }

    @Test
    void calcPillarTest2() {
        int result = Calculator.calcPillar(360);
        assertEquals(4, result);
    }

    @Test
    void calcPillarTest3() {
        int result = Calculator.calcPillar(570);
        assertEquals(6, result);
    }

    @Test
    void calcPillarTest4() {
        int result = Calculator.calcPillar(700);
        assertEquals(6, result);
    }

    @Test
    void calcPillarTest5() {
        int result = Calculator.calcPillar(780);
        assertEquals(6, result);
    }

    @Test
    void calcPillarTest6() {
        int result = Calculator.calcPillar(1000);
        assertEquals(8, result);
    }


    @Test
    void calcBeamTest1() {
        int result = Calculator.calcBeams(240);
        assertEquals(2, result);
    }

    @Test
    void calcBeamTest2() {
        int result = Calculator.calcBeams(360);
        assertEquals(2, result);
    }

    @Test
    void calcBeamTest3() {
        int result = Calculator.calcBeams(570);
        assertEquals(2, result);
    }

    @Test
    void calcBeamTest4() {
        int result = Calculator.calcBeams(700);
        assertEquals(4, result);
    }

    @Test
    void calcBeamTest5() {
        int result = Calculator.calcBeams(780);
        assertEquals(4, result);
    }


    @Test
    void calcBeamTest6() {
        int result = Calculator.calcBeams(1000);
        assertEquals(4, result);
    }

    @Test
    void calcRaftersTest1() {
        int result = Calculator.calcRafters(240);
        assertEquals(5, result);
    }

    @Test
    void calcRaftersTest2() {
        int result = Calculator.calcRafters(330);
        assertEquals(7, result);
    }

    @Test
    void calcRaftersTest3() {
        int result = Calculator.calcRafters(420);
        assertEquals(8, result);
    }

    @Test
    void calcRaftersTest4() {
        int result = Calculator.calcRafters(510);
        assertEquals(10, result);
    }

    @Test
    void calcRaftersTest5() {
        int result = Calculator.calcRafters(600);
        assertEquals(11, result);
    }

}