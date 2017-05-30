package com.dreamteam.police.remote;

import com.dreamteam.police.model.Ownership;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Loci on 8-5-2017.
 */
@SpringComponent
public class RemoteOwnershipData {

    private String baseUrl = "http://192.168.24.33:8080/dreamteam-administration/police/api/ownerships";

    public List<Ownership> getAllOwnerships() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Ownership[]> responseEntity = restTemplate.getForEntity(baseUrl, Ownership[].class);
            List<Ownership> ownerships = Arrays.asList(responseEntity.getBody());
            HttpStatus status = responseEntity.getStatusCode();
            return ownerships;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
