package com.dreamteam.police.remote;

import com.dreamteam.police.model.Ownership;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Loci on 8-5-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteOwnershipDataTest {

    @Autowired
    private RemoteOwnershipData remoteOwnershipData;

    @Test
    public void getAllOwnerships() throws Exception {
        try {
            List<Ownership> ownerships = remoteOwnershipData.getAllOwnerships();
            assertTrue("Not enough ownerships returned", !ownerships.isEmpty());
        } catch (Exception ex) {
            System.out.println("yay exception");
        }
    }
}