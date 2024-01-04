package com.codingdojo.beltexam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.beltexam.models.Player;
import com.codingdojo.beltexam.models.Team;
import com.codingdojo.beltexam.models.User;
import com.codingdojo.beltexam.services.TeamService;
import com.codingdojo.beltexam.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TeamController {

	@Autowired
	private TeamService teamServ;

	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String dashboard(Model model, HttpSession session) {
		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {

			// Grab all Teams
			List<Team> teams = teamServ.findAllTeams();
			model.addAttribute("teams", teams);

			// Find the current User by Id
			User user = userService.findById(userId);
			model.addAttribute("user", user);

			return "home.jsp";
		}

	}

	@GetMapping("/teams/new")
	public String teamForm(@ModelAttribute("team") Team team, HttpSession session) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {

			return "createTeamForm.jsp";
		}

	}

	@PostMapping("/teams")
	public String createTeam(@Valid @ModelAttribute("team") Team team, BindingResult result, HttpSession session) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {

			if (result.hasErrors()) {
				return "createTeamForm.jsp";
			} else {
				// Grab the logged in user Object
				User currentUser = userService.findById(userId);
				// Set the User id inside the car Object
				team.setUser(currentUser);

				Team newTeam = teamServ.createTeam(team);

				return "redirect:/home";
			}

		}

	}
	

	@GetMapping("/teams/{id}")
	public String getTeamById(Model model, @PathVariable("id") Long id, HttpSession session, @ModelAttribute("player") Player player) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {
			Team team = teamServ.findTeamById(id);
			model.addAttribute("team", team);
			
			session.setAttribute("team_id", team.getId());
			
			return "teamDetails.jsp";
		}

	}

	@GetMapping("/teams/{id}/edit")
	public String updateTeam(@PathVariable("id") Long id, Model model, @ModelAttribute("team") Team team, HttpSession session) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {

			Team foundedTeam = teamServ.findTeamById(id);
			model.addAttribute("team", foundedTeam);

			return "editTeamForm.jsp";
		}
		
	}

	@PutMapping("/teams/{id}")
	public String updateTeam(@Valid @ModelAttribute("team") Team team, BindingResult result, HttpSession session) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {
			if (result.hasErrors()) {
				return "editTeamForm.jsp";
			} else {
				// Grab the logged in user Object
				User currentUser = userService.findById(userId);
				// Set the User id inside the car Object
				team.setUser(currentUser);

				teamServ.updateTeam(team);
				return "redirect:/home";
			}
		}
	}

	@DeleteMapping("/teams/{id}")
	public String deleteTeam(@PathVariable("id") Long id, HttpSession session) {
		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {
			teamServ.deleteTeam(id);
			return "redirect:/home";
		}
	}
}
