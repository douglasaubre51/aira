package com.forge.aira.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Value("${WAGURI_BASE_URI}")
    private String host;

    public boolean getApiStatus() {

        String uri = "/hello";
        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = template.getForEntity(
                host + uri,
                Object.class);

        System.out.println("getApiStatus: " + response.getStatusCode().toString());

        return response.getStatusCode().is2xxSuccessful();
    }

}
