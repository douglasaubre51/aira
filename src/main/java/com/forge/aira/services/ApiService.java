package com.forge.aira.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    public String getApiStatus(String host) {

        String uri = "/hello";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(host + uri, String.class);

        return result;
    }

}
