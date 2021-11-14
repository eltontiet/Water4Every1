package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BottleTest {
    Bottle bottle;

    @BeforeEach
    public void setup() {
        bottle = new Bottle(1000);
    }

    @Test
    public void testEmptyBottleSet() {
        bottle = new Bottle();
        assertEquals(0, bottle.getCapacity());
        assertEquals(0, bottle.getWaterLevel());
        bottle.changeCapacity(1200);
        assertEquals(1200, bottle.getCapacity());
    }

    @Test
    public void testChangeCapacity() {
        bottle.changeCapacity(1500);
        assertEquals(1500, bottle.getCapacity());
    }

    @Test
    public void testDrinkLittle() {
        bottle.drinkWater(200);
        assertEquals(800, bottle.getWaterLevel());
    }

    @Test
    public void testDrinkLittles() {
        bottle.drinkWater(200);
        bottle.drinkWater(500);
        assertEquals(300, bottle.getWaterLevel());
    }

    @Test
    public void testDrinkLittleOverflow() {
        bottle.drinkWater(300);
        bottle.drinkWater(500);
        bottle.drinkWater(500);
        assertEquals(700, bottle.getWaterLevel());
    }

    @Test
    public void testDrinkLot() {
        bottle.drinkWater(1100);
        assertEquals(900, bottle.getWaterLevel());
    }

    @Test
    public void testDrinkLots() {
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
