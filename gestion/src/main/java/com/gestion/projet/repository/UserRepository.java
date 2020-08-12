package com.gestion.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.projet.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
