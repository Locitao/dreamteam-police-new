package com.dreamteam.police.remote;

import com.dreamteam.police.jms.IcanCoordinateDTO;
import com.dreamteam.police.remote.dto.IcanDTO;
import com.google.gson.Gson;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("api not online yet.");
            ex.printStackTrace();
            return false;
        }
    }

    public List<IcanCoordinateDTO> getLocationHistory(String ICAN) {
        List<IcanCoordinateDTO> dtos = new ArrayList<>();

        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<IcanCoordinateDTO[]> responseEntity = template.getForEntity("http://192.168.24.31:8080/movement-registration/api/police/locationhistory/" + ICAN, IcanCoordinateDTO[].class);
            dtos = Arrays.asList(responseEntity.getBody());
            return dtos;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
