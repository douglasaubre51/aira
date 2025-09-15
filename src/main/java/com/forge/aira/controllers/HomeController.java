package com.forge.aira.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.servlet.view.*;

import com.forge.aira.dtos.*;
import com.forge.aira.services.*;

@Controller
public class HomeController {

    @Value("${WAGURI_BASE_URI}")
	private String host;
    private String message;

    private final UserService _userService;
    private final ApiService _apiService;
    private final ProjectService _projectService;

    public HomeController(
	    UserService userService,
	    ApiService apiService,
	    ProjectService projectService
	    ) {

	_userService = userService;
	_apiService = apiService;
	_projectService = projectService;

	message = new String();
    }

    // user endpoints

    // index view
    @GetMapping("/")
	public String getHomeView(Model model) {
	    try {

		// init attributes
		model.addAttribute("message", message);
		model.addAttribute("primary_form_dto", new PrimaryFormDto());
		model.addAttribute("project_form_dto", new ProjectFormDto());

		boolean result = _apiService.getApiStatus();
		if (!result)
		    model.addAttribute("waguri_status", "lost");
		else
		    model.addAttribute("waguri_status", "live");

		return "index";

	    } catch (ResourceAccessException ex) {

		model.addAttribute("waguri_status", "lost");
		System.out.println("conn lost: " + ex.getMessage());
		return "index";

	    } catch (Exception ex) {

		model.addAttribute("message", ex.getMessage());
		System.out.println("getHomeView error: " + ex.getMessage());
		return "error";
	    }
	}

    @PostMapping("/user/create")
	public RedirectView createUser(@ModelAttribute("primary_form_dto") PrimaryFormDto dto) {
	    try {
		System.out.println("triggered create user!");

		boolean result = _userService.createNewUser(dto);
		if (result == false)
		    message += "couldnot create new user!";
		else
		    message += "user created successfully!";

		return new RedirectView("/");

	    } catch (Exception ex) {

		message += ex.getMessage();
		return new RedirectView("/");
	    }
	}

    @GetMapping("/user/delete/{email}")
	public String removeUser(
	@PathVariable String email,
	Model model
	){
	    try{

		_userService.removeByEmailId(email);

		List<UserDto> users = _userService.getUsers();
		model.addAttribute("user_list", users);

		return "users";
	    }
	    catch(Exception ex){
		System.out.println("removeUser error: "+ex.getMessage());
		message += ex.getMessage();
		return "users";
	    }
	}

    @GetMapping("/user/view")
	public String getUserView(Model model) {
	    try {

		List<UserDto> users = _userService.getUsers();
		model.addAttribute("user_list", users);

		return "users";

	    } catch (ResourceAccessException ex) {
		return "users_error";
	    } catch (Exception ex) {

		model.addAttribute("message", ex.getMessage());

		System.out.println("getHomeView error: " + ex.getMessage());
		return "error";
	    }
	}

    @GetMapping("/message/clear")
	public RedirectView clearMessages() {

	    message = "";
	    return new RedirectView("/");
	}

    // project endpoints

    @GetMapping("/project/all")
	public String getProjects(Model model){
	    try{

		List<ClientDto> projectList = _projectService.getAll();

		model.addAttribute("project_list",projectList);
		return "projects";
	    }
	    catch(Exception ex){

		System.out.println("getProjects error: "+ex.getMessage());
		message += ex.getMessage();
		return "projects_error";
	    }
	}

    @PostMapping("/project/create")
	public RedirectView createProject(
		@ModelAttribute("project_form_dto") ProjectFormDto dto,
		Model model
		){
	    try{
		System.out.println("creating project ...");

		var clientDto = new ClientDto();
		clientDto.url = dto.getProjectUrl();
		clientDto.apiUrl = dto.getApiUrl();
		clientDto.projectId = dto.getProjectId();

		var status = _projectService.create(clientDto);

		return new RedirectView("/");
	    }
	    catch(Exception ex){

		System.out.println("createProjects error: "+ex.getMessage());
		message += ex.getMessage();
		return new RedirectView("/");
	    }
	}

    @GetMapping("/project/delete/{id}")
	public String removeProject(
		@PathVariable String id,
		Model model
		){
	    try{
		_projectService.remove(id);

		List<ClientDto> projectList = _projectService.getAll();
		model.addAttribute("project_list",projectList);

		return "projects";
	    }
	    catch(Exception ex){

		System.out.println("createProjects error: "+ex.getMessage());
		message += ex.getMessage();
		return "projects";
	    }
	}
}
