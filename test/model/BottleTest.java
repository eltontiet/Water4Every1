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
    public void changeCapacity() {
        bottle.changeCapacity(1500);
        assertEquals(1500, bottle.getCapacity());
    }

    @Test
    public void drinkLittle() {
        bottle.drinkWater(200);
        assertEquals(800, bottle.getWaterLevel());
    }

    @Test
    public void drinkLittles() {
        bottle.drinkWater(200);
        bottle.drinkWater(500);
        assertEquals(300, bottle.getWaterLevel());
    }

    @Test
    public void drinkLittleOverflow() {
        bottle.drinkWater(300);
        bottle.drinkWater(500);
        bottle.drinkWater(500);
        assertEquals(700, bottle.getWaterLevel());
    }

    @Test
    public void drinkLot() {
        bottle.drinkWater(1100);
        assertEquals(900, bottle.getWaterLevel());
    }

    @Test
    public void drinkLots() {
        bottle.drinkWater(1100);
        bottle.drinkWater(2300);
        assertEquals(600, bottle.getWaterLevel());
    }
}
