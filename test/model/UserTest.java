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
    public void drinkWaterTest() {
        user.drinkWater(100);
        assertEquals(100, user.getWaterDrank());
    }

    @Test
    public void drinkLotsOfWaterTest() {
        user.drinkWater(100);
        user.drinkWater(200);
        user.drinkWater(300);
        user.drinkWater(400);
        user.drinkWater(500);
        assertEquals(1500, user.getWaterDrank());
    }

    @Test
    public void setUsernameTest() {
        user.setUsername("Elton");
        assertEquals("Elton", user.getUsername());
    }

    @Test
    public void changeUserNameTest() {
        user.setUsername("Elton");
        user.setUsername("Michelle");
        assertEquals("Michelle", user.getUsername());
    }


}
