package com.codingdojo.beltexam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.beltexam.models.Player;
import com.codingdojo.beltexam.repositories.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepo;

	// Create a team
	public Player createPlayer(Player p) {
		return playerRepo.save(p);
	}
}
