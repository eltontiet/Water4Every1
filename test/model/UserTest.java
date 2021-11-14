package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.editBottle(1400);
    }

    @Test
    public void drinkWater() {
        user.drinkWater(100);
        assertEquals(100, user.getWaterDrank());
    }

    @Test
    public void drinkLotsOfWater() {
        user.drinkWater(100);
        user.drinkWater(200);
        user.drinkWater(300);
        user.drinkWater(400);
        user.drinkWater(500);
        assertEquals(1500, user.getWaterDrank());
    }


}
