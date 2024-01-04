package com.codingdojo.beltexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.beltexam.models.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

}
