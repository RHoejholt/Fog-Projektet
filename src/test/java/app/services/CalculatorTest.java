package app.services;

import app.entities.Order;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {


  //  private static final String USER = "postgres";
  //  private static final String PASSWORD = "postgres";
  //   private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    //  private static final String DB = "fog";

    //  private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    Order testOrder = new Order(600, 1, 780, 0);


    @BeforeAll
    static void setUpBeforeClass()
    {

    }

    @Test
    void calcPillarAndBeams()
    {
        try {
            Calculator calculator = new Calculator(testOrder);
            calculator.calcPillarAndBeams();
            System.out.println("size " + testOrder.getSize());
            assertEquals(2, testOrder.getSize());
        }catch(Exception e){
            e.printStackTrace();

    }
    }
}