package com.dreamteam.police.remote;

import com.dreamteam.police.dto.StolenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class RemoteReporting {

    @Autowired
    Authentication authentication;

    public boolean reportCar(StolenDTO stolenDTO) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = authentication.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Gson gson = new Gson();
        String json = gson.toJson(stolenDTO);
        //Map<String, String> map = new ObjectMapper().readValue(json, Map.class);
        //map.add("stolen", json);

        HttpEntity<?> request = new HttpEntity<>(json, headers);

        try {
            //ResponseEntity<String> response = restTemplate.exchange("http://192.168.24.33:8080/dreamteam-administration/police/api/cars/" + stolenDTO.getCarDTO().getIcan(), HttpMethod.POST, request);
            ResponseEntity<String> response = restTemplate.postForEntity("http://192.168.24.33:8080/dreamteam-administration/police/api/cars/" + stolenDTO.getCarDTO().getIcan(), request, String.class);
        } catch (HttpClientErrorException ex) {
            System.out.println("api not online yet.");
            ex.printStackTrace();
        }
        return true;
    }
}
