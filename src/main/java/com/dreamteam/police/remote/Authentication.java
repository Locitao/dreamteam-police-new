package com.dreamteam.police.remote;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created by loci on 30-5-17.
 */
@Component
public class Authentication {

    public HttpHeaders getHeaders() {
//        String username = "police@police.police";
//        String password = "police"; //lolhardcoded;
        String username = "angela.merkel@mail.de";
        String password = "admin";

        String auth = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encoded);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        return headers;
    }
}
