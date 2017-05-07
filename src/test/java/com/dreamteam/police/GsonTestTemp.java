package com.dreamteam.police;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by loci on 6-5-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GsonTestTemp {

    private Car car;
    private Citizen citizen;
    private Ownership ownership;

    @Before
    public void setUp() {
        citizen = new Citizen("jan", "jansen");
        car = new Car("asdf", "1234", "as1234");
        ownership = new Ownership();
        ownership.setOwned(car);
        ownership.setOwner(citizen);
        ownership.setStartOwnership(new Date());
//        List<Ownership> ownerships = new ArrayList<>();
//        ownerships.add(ownership);
    }

    @Test
    public void testGson() {
        Gson gson = new Gson();
        String json = gson.toJson(ownership);
        System.out.println(json.length());
        System.out.println(json);
    }
}
