package com.forge.aira.services;

import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import com.forge.aira.dtos.*;
import com.forge.aira.wrappers.*;

import java.util.*;

@Service
public class ProjectService{
	
	@Value("${WAGURI_BASE_URI}")
	public String Host;

	public RestTemplate _template;

	public ProjectService(){
		_template = new RestTemplate();
	}

	public List<ClientDto> getAll(){

		String uri = "/project/all";

		ResponseEntity<ClientDtoList> response =  _template.getForEntity(
			Host+uri,
			ClientDtoList.class
		);

		return response.getBody().getClients();
	}

	public boolean create(ClientDto dto){

		String uri = "/project/create";

		System.out.println("project id: "+dto.projectId);
		System.out.println("api url: "+dto.apiUrl);
		System.out.println("url: "+dto.url);

		ResponseEntity<ClientDto> response = _template.postForEntity(
			Host+uri,
			dto,
			ClientDto.class
		);

		return response.getStatusCode().is2xxSuccessful();
	}
}
