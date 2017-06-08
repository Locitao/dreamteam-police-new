package com.dreamteam.police.remote;

import com.dreamteam.police.remote.dto.IcanDTO;
import com.google.gson.Gson;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Loci on 7-6-2017.
 */
@SpringComponent
public class RemoteTrackCar {

    public boolean setRemoteTracking(String ICAN) {
        IcanDTO dto = new IcanDTO();
        dto.setICAN(ICAN);

        Gson gson = new Gson();
        String json = gson.toJson(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> request = new HttpEntity<>(json, headers);

        try {
            //ResponseEntity<String> response = restTemplate.exchange("http://192.168.24.33:8080/dreamteam-administration/police/api/cars/" + stolenDTO.getCarDTO().getIcan(), HttpMethod.POST, request);
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.postForEntity("http://192.168.24.31:8080/movement-registration/api/police/", request, String.class);
            return true;
        } catch (HttpClientErrorException ex) {
            System.out.println("api not online yet.");
            ex.printStackTrace();
            return false;
        }
    }
}
