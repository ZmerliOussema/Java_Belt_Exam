package com.codingdojo.beltexam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.beltexam.models.Player;
import com.codingdojo.beltexam.models.Team;
import com.codingdojo.beltexam.services.PlayerService;
import com.codingdojo.beltexam.services.TeamService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PlayerController {

	@Autowired
	private PlayerService playerServ;

	@Autowired
	private TeamService teamService;

	@PostMapping("/players")
	public String createPlayer(@Valid @ModelAttribute("player") Player player, BindingResult result, HttpSession session, RedirectAttributes flash) {

		// Grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// Route Guard
		if (userId == null) {
			return "redirect:/";
		} else {

			if (result.hasErrors()) {
				return "teamDetails.jsp";
			} else {
				Long teamId = (Long) session.getAttribute("team_id");
				// Grab the logged in user Object
				Team currentTeam = teamService.findTeamById(teamId);
				
				if(currentTeam.getPlayers().size() == 9) {
					flash.addFlashAttribute("error", "The team is full!");
					return "redirect:/teams/" + currentTeam.getId();
				}
				// Set the User id inside the car Object
				player.setTeam(currentTeam);

				Player newPlayer = playerServ.createPlayer(player);

				return "redirect:/home";
			}

		}
	}
}