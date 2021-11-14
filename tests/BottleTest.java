package tests;

import model.Bottle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BottleTest {
    Bottle bottle;

    @BeforeEach
    public void setup() {
        bottle = new Bottle(1000);
    }

    @Test
    public void changeCapacityTest() {
        bottle.changeCapacity(1500);
        assertEquals(1500, bottle.getCapacity());
    }

    @Test
    public void drinkLittleTest() {
        bottle.drinkWater(200);
        assertEquals(800, bottle.getWaterLevel());
    }

    @Test
    public void drinkLittlesTest() {
        bottle.drinkWater(200);
        bottle.drinkWater(500);
        assertEquals(300, bottle.getWaterLevel());
    }

    @Test
    public void drinkLittleOverflowTest() {
        bottle.drinkWater(300);
        bottle.drinkWater(500);
        bottle.drinkWater(500);
        assertEquals(700, bottle.getWaterLevel());
    }

    @Test
    public void drinkLotTest() {
        bottle.drinkWater(1100);
        assertEquals(900, bottle.getWaterLevel());
    }

    @Test
    public void drinkLotsTest() {
        bottle.drinkWater(1100);
        bottle.drinkWater(2300);
        assertEquals(600, bottle.getWaterLevel());
    }

    @Test
    public void testFill() {
        bottle.fillWater();
        assertEquals(bottle.getCapacity(), bottle.getWaterLevel());
    }

    @Test
    public void testFillEmpty() {
        bottle.drinkWater(bottle.getCapacity());
        bottle.fillWater();
        assertEquals(bottle.getCapacity(), bottle.getWaterLevel());
    }
}
