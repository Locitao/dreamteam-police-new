package com.dreamteam.police.remote;

import com.dreamteam.police.dto.StolenDTO;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class RemoteReporting {

    public boolean reportCar(StolenDTO stolenDTO) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("stolenDTO", stolenDTO);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://192.168.24.33:8080/dreamteam-administration/police/api/" + stolenDTO.getCarDTO().getIcan(), request, String.class);
        return true;
    }
}
