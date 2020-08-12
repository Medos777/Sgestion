package com.gestion.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.projet.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}