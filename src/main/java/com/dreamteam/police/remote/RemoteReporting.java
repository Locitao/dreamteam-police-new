package com.dreamteam.police.remote;

import com.dreamteam.police.dto.StatusDto;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class RemoteReporting {

    @Autowired
    Authentication authentication;

    public boolean reportCar(StatusDto statusDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = authentication.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("statusDto", statusDto);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://192.168.24.33:8080/dreamteam-administration/police/api/cars/" + statusDto.getCarDto().getIcan(), request, String.class);
        } catch (HttpClientErrorException ex) {
            System.out.println("api not online yet.");
        }
        return true;
    }
}
