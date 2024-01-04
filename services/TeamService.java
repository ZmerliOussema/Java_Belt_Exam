package com.codingdojo.beltexam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.beltexam.models.Team;
import com.codingdojo.beltexam.repositories.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepo;

	// Get all teams
	public List<Team> findAllTeams() {
		return teamRepo.findAll();
	}

	// Create a team
	public Team createTeam(Team t) {
		return teamRepo.save(t);
	}

	// Get a team by Id
	public Team findTeamById(Long id) {
		Optional<Team> maybeTeam = teamRepo.findById(id);
		if (maybeTeam.isPresent()) {
			return maybeTeam.get();
		} else {
			return null;
		}
	}

	// Update a team
	public Team updateTeam(Team t) {
		return teamRepo.save(t);
	}

	// Delete a team
	public void deleteTeam(Long id) {
		teamRepo.deleteById(id);
	}
}
